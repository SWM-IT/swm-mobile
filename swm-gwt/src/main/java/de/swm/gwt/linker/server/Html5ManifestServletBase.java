package de.swm.gwt.linker.server;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.swm.gwt.linker.ManifestWriter;
import de.swm.gwt.linker.PermutationMapLinker;
import de.swm.gwt.linker.XMLPermutationProvider;
import de.swm.gwt.linker.XMLPermutationProviderException;
import de.swm.gwt.linker.server.propertyprovider.MgwtOsPropertyProvider;
import de.swm.gwt.linker.server.propertyprovider.PropertyProvider;
import de.swm.gwt.linker.server.propertyprovider.PropertyProviderException;

/**
 * Base class for servlet which delivers application cache manifest for a specific GWT permutation.
 * Modified by Alexander Vilbig (SWM Services GmbH) from original MGWT version to improve matching of user agent and locale properties.
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 *
 */
public class Html5ManifestServletBase extends HttpServlet {

	private static final long serialVersionUID = -2540671294104865306L;
	private XMLPermutationProvider permutationProvider;

	private Map<String, PropertyProvider> propertyProviders = new HashMap<String, PropertyProvider>();
	Map<String, List<BindingProperty>> permutationMap = null;

	public Html5ManifestServletBase() {
		permutationProvider = new XMLPermutationProvider();

	}

	protected void addPropertyProvider(PropertyProvider propertyProvider) {
		propertyProviders.put(propertyProvider.getPropertyName(), propertyProvider);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String moduleName = getModuleName(req);

		String baseUrl = getBaseUrl(req);

		initPermutationMap(baseUrl, moduleName);

		Set<BindingProperty> computedBindings = calculateBindinPropertiesForClient(req);

		// remove locale and user agent property bindings if not present in permutation map
		Set<BindingProperty> filteredBindings = computedBindings;
		if (!containsPermutationProperty("locale")) {
			log("Removing locale from matched properties...");
			filteredBindings = filterProperty("locale", filteredBindings);
		}
		if (!containsPermutationProperty("user.agent")) {			
			log("Removing user agent from matched properties...");
			filteredBindings = filterProperty("user.agent", filteredBindings);
		}

		log("Matching following binding properties: " + filteredBindings);
		String strongName = determinePermutationStrongName(filteredBindings);
		
		if (strongName == null) {
			log("Setting locale to default in second round of matching...");
			BindingProperty localeProperty = null;
			for (BindingProperty property : filteredBindings) {
				if (property.getName().equals("locale")) {
					localeProperty = property;
				}
			}
			if (localeProperty != null) {
				filteredBindings.remove(localeProperty);
				filteredBindings.add(new BindingProperty("locale", "default"));
			}
			strongName = determinePermutationStrongName(filteredBindings);
		}

		if (strongName != null) {
			String manifest = readManifest(baseUrl + moduleName + "/" + strongName
					+ PermutationMapLinker.PERMUTATION_MANIFEST_FILE_ENDING);
			serveStringManifest(req, resp, manifest);
			return;
		}

		boolean isIPhoneWithoutCookie = isIphoneWithoutCookie(computedBindings);
		boolean isIPadWithoutCookie = isIpadWithoutCookie(computedBindings);

		if (isIPhoneWithoutCookie || isIPadWithoutCookie) {
			Set<BindingProperty> nonRetinaMatch = new HashSet<BindingProperty>();
			Set<BindingProperty> retinaMatch = new HashSet<BindingProperty>();

			if (isIPhoneWithoutCookie) {
				computedBindings.remove(MgwtOsPropertyProvider.iPhone_undefined);
				nonRetinaMatch.add(MgwtOsPropertyProvider.iPhone);
				retinaMatch.add(MgwtOsPropertyProvider.retina);
			}

			if (isIPadWithoutCookie) {
				computedBindings.remove(MgwtOsPropertyProvider.iPad_undefined);
				nonRetinaMatch.add(MgwtOsPropertyProvider.iPad);
				retinaMatch.add(MgwtOsPropertyProvider.iPad_retina);
			}

			nonRetinaMatch.addAll(computedBindings);
			retinaMatch.addAll(computedBindings);

			String moduleNameNonRetina = determinePermutationStrongName(nonRetinaMatch);
			String moduleNameRetina = determinePermutationStrongName(retinaMatch);

			if (moduleNameNonRetina != null && moduleNameRetina != null) {

				// load files for both permutations
				Set<String> filesForPermutation = getFilesForPermutation(baseUrl, moduleName, moduleNameNonRetina);
				filesForPermutation.addAll(getFilesForPermutation(baseUrl, moduleName, moduleNameRetina));

				// dynamically write a new manifest..
				ManifestWriter manifestWriter = new ManifestWriter();
				String writeManifest = manifestWriter.writeManifest(new HashSet<String>(), filesForPermutation);
				serveStringManifest(req, resp, writeManifest);
				return;
			}
		}

		// if we got here we just don`t know the device react with 500 -> no
		// manifest...

		throw new ServletException("unkown device for bindings " + computedBindings);

	}

	protected String getBaseUrl(HttpServletRequest req) {
		String base = req.getServletPath();
		// cut off module
		return base.substring(0, base.lastIndexOf("/") + 1);
	}

	public boolean isIphoneWithoutCookie(Set<BindingProperty> bps) {
		for (BindingProperty bp : bps) {
			if ("mgwt.os".equals(bp.getName())) {
				if ("iphone_undefined".equals(bp.getValue())) {
					// oh shit this is an iphone
					// so now we need to serve two manifests
					// retina...
					// non retina

					return true;

				}
			}
		}
		return false;
	}

	public boolean isIpadWithoutCookie(Set<BindingProperty> bps) {
		for (BindingProperty bp : bps) {
			if ("mgwt.os".equals(bp.getName())) {
				if ("ipad_undefined".equals(bp.getValue())) {
					// oh shit this is an ipad
					// so now we need to serve two manifests
					// retina...
					// non retina

					return true;

				}
			}
		}
		return false;
	}

	public Set<String> getFilesForPermutation(String baseUrl, String moduleName, String permutation)
			throws ServletException {
		String fileName = baseUrl + moduleName + "/" + permutation + PermutationMapLinker.PERMUTATION_FILE_ENDING;
		XMLPermutationProvider xmlPermutationProvider = new XMLPermutationProvider();

		try {
			File file = new File(getServletContext().getRealPath(fileName));
			InputStream inputStream = new FileInputStream(file);
			return xmlPermutationProvider.getPermutationFiles(inputStream);
		} catch (XMLPermutationProviderException e) {
			log("can not read permutation file");
			throw new ServletException("can not read permutation file", e);
		} catch (FileNotFoundException e) {
			log("can not read permutation file");
			throw new ServletException("can not read permutation file", e);
		}
	}

	public String readManifest(String filePath) throws ServletException {
		File manifestFile = new File(getServletContext().getRealPath(filePath));

		StringWriter manifestWriter = new StringWriter();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(manifestFile));
			String line = null;

			while ((line = br.readLine()) != null) {

				manifestWriter.append(line + "\n");
			}

			return manifestWriter.toString();
		} catch (FileNotFoundException e) {
			log("could not find manifest file", e);
			throw new ServletException("can not find manifest file", e);
		} catch (IOException e) {
			log("error while reading manifest file", e);
			throw new ServletException("error while reading manifest file", e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {

				}
			}
		}
	}

	/**
	 * @param req
	 * @return
	 * @throws PropertyProviderException
	 */
	public Set<BindingProperty> calculateBindinPropertiesForClient(HttpServletRequest req) throws ServletException {

		try {
			Set<BindingProperty> computedBindings = new HashSet<BindingProperty>();

			Set<Entry<String, PropertyProvider>> set = propertyProviders.entrySet();
			for (Entry<String, PropertyProvider> entry : set) {
				String varValue = entry.getValue().getPropertyValue(req);
				BindingProperty bindingProperty = new BindingProperty(entry.getKey(), varValue);
				computedBindings.add(bindingProperty);
			}
			return computedBindings;
		} catch (PropertyProviderException e) {
			log("can not calculate properties for client", e);
			throw new ServletException("can not calculate properties for client", e);
		}

	}

	public void serveStringManifest(HttpServletRequest req, HttpServletResponse resp, String manifest)
			throws ServletException {
		resp.setHeader("Cache-Control", "no-cache");
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", new Date().getTime());

		resp.setContentType("text/cache-manifest");

		try {
			InputStream is = new ByteArrayInputStream(manifest.getBytes("UTF-8"));
			ServletOutputStream os = resp.getOutputStream();
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			return;
		} catch (UnsupportedEncodingException e) {
			log("can not write manifest to output stream", e);
			throw new ServletException("can not write manifest to output stream", e);
		} catch (IOException e) {
			log("can not write manifest to output stream", e);
			throw new ServletException("can not write manifest to output stream", e);
		}

	}
	
	private String determinePermutationStrongName(Set<BindingProperty> computedBindings) throws ServletException {
		if (permutationMap == null) {
			throw new IllegalStateException("permutationMap can not be null");
		}
		
		if (computedBindings == null) {
			throw new IllegalArgumentException("computedBindings can not be null");
		}
		
		for (Entry<String, List<BindingProperty>> entry : permutationMap.entrySet()) {
			List<BindingProperty> value = entry.getValue();
			if (value.containsAll(computedBindings)) {
				log("Matching to permutation with strong name " + entry.getKey());
				return entry.getKey();
			}
		}
		return null;
	}
		
	private boolean containsPermutationProperty(String name) {
		for (Entry<String, List<BindingProperty>> entry : permutationMap.entrySet()) {
			List<BindingProperty> value = entry.getValue();
			for (BindingProperty property : value) {
				if (property.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;		
	}

	private void initPermutationMap(String baseUrl, String moduleName) throws ServletException {

		if (permutationMap != null) {
			return;
		}

		if (moduleName == null) {
			throw new IllegalArgumentException("moduleName can not be null");
		}

		String realPath = getServletContext().getRealPath(
				baseUrl + moduleName + "/" + PermutationMapLinker.MANIFEST_MAP_FILE_NAME);

		try {
			FileInputStream fileInputStream = new FileInputStream(realPath);
			permutationMap = permutationProvider.getBindingProperties(fileInputStream);
		} catch (FileNotFoundException e) {
			log("can not find file: '" + realPath + "'", e);
			throw new ServletException("can not find permutation file", e);
		} catch (XMLPermutationProviderException e) {
			log("can not read xml file", e);
			throw new ServletException("can not read permutation information", e);
		}

	}

	// utility method to remove a specific binding property from a set of bindings
	private Set<BindingProperty> filterProperty(String name, Set<BindingProperty> computedBindings) {
		Set<BindingProperty> result = new HashSet<BindingProperty>();
		for (BindingProperty property : computedBindings) {
			if (!(property.getName().equals(name))) {
				result.add(property);
			}
		}
		return result;
	}

	public String getModuleName(HttpServletRequest req) throws ServletException {
		if (req == null) {
			throw new IllegalArgumentException("request can not be null");
		}

		// request url should be something like .../modulename.manifest" within
		// the same folder of your host page...
		Pattern pattern = Pattern.compile("/([a-zA-Z0-9]+)\\.manifest$");
		Matcher matcher = pattern.matcher(req.getServletPath());
		if (!matcher.find()) {
			log("can not calculate module base from url: '" + req.getServletPath() + "'");
			throw new ServletException("can not calculate module base from url: '" + req.getServletPath() + "'");
		}

		String module = matcher.group(1);
		return module;

	}
}