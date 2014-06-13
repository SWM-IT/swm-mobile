package de.swm.mobile.kitchensink.generator;


import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import de.swm.mobile.kitchensink.client.ShowcaseConstants;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;

/**
 * Generate the source code, css styles, and raw source used in the Showcase
 * examples.
 */
public class ShowcaseGenerator extends Generator {
	/**
	 * The paths to the CSS style sheets used in Showcase. The paths are relative
	 * to the root path of the {@link ClassLoader}. The variable $THEME will be
	 * replaced by the current theme. The variable $RTL will be replaced by "_rtl"
	 * for RTL locales.
	 */
	private static final String[] SRC_CSS = {
			"com/google/gwt/user/theme/$THEME/public/gwt/$THEME/$THEME$RTL.css",
			"com/google/gwt/sample/showcase/client/Showcase.css"};

	/**
	 * The class loader used to get resources.
	 */
	private ClassLoader classLoader = null;

	/**
	 * The generator context.
	 */
	private GeneratorContext context = null;

	/**
	 * The {@link TreeLogger} used to log messages.
	 */
	private TreeLogger logger = null;

	/**
	 * The set of raw files that have already been generated. Raw files can be
	 * reused by different examples, but we only generate them once.
	 */
	private Set<String> rawFiles = new HashSet<String>();

	@Override
	public String generate(
			TreeLogger logger, GeneratorContext context, String typeName)
			throws UnableToCompleteException {
		logger.log(TreeLogger.Type.INFO, "Generaaating....");
		this.logger = logger;
		this.context = context;
		this.classLoader = Thread.currentThread().getContextClassLoader();

		// Only generate files on the first permutation
		if (!isFirstPass()) {
			return null;
		}

		// Get the Showcase ContentWidget subtypes to examine
		JClassType cwType = null;
		try {
			cwType = context.getTypeOracle().getType(ShowcaseDetailPage.class.getName());
		} catch (NotFoundException e) {
			logger.log(TreeLogger.ERROR, "Cannot find ContentWidget class", e);
			throw new UnableToCompleteException();
		}
		JClassType[] types = cwType.getSubtypes();

		// Generate the source and raw source files
		for (JClassType type : types) {
			generateSourceFiles(type);
		}


		return null;
	}

	/**
	 * Set the full contents of a resource in the public directory.
	 *
	 * @param partialPath the path to the file relative to the public directory
	 * @param contents    the file contents
	 */
	private void createPublicResource(String partialPath, String contents)
			throws UnableToCompleteException {
		try {
			OutputStream outStream = context.tryCreateResource(logger, partialPath);
			if (outStream == null) {
				String message = "Attempting to generate duplicate public resource: "
						+ partialPath
						+ ".\nAll generated source files must have unique names.";
				logger.log(TreeLogger.ERROR, message);
				throw new UnableToCompleteException();
			}
			outStream.write(contents.getBytes());
			context.commitResource(logger, outStream);
		} catch (IOException e) {
			logger.log(TreeLogger.ERROR, "Error writing file: " + partialPath, e);
			throw new UnableToCompleteException();
		}
	}

	/**
	 * Generate the formatted source code for a {@link com.google.gwt.user.client.ui.Widget}.
	 *
	 * @param type the {@link com.google.gwt.user.client.ui.Widget}.
	 */
	private void generateSourceFiles(JClassType type)
			throws UnableToCompleteException {
		// Get the file contents
		String filename = type.getQualifiedSourceName().replace('.', '/') + ".java";
		String fileContents = getResourceContents(filename);

		// Get each data code block
		String formattedSource = "";
		String sourceTag = "@" + ShowcaseSource.class.getSimpleName();
		int srcTagIndex = fileContents.indexOf(sourceTag);
		if (srcTagIndex >= 0) {
			// Add to the formatted source
			String srcCode = fileContents;
			formattedSource += srcCode + "\n\n";

		}

		// Make the source pretty
		formattedSource = formattedSource.replace("<", "&lt;");
		formattedSource = formattedSource.replace(">", "&gt;");
		formattedSource = formattedSource.replace("* \n   */\n", "*/\n");
		formattedSource = "<pre>" + formattedSource + "</pre>";

		// Save the source code to a file
		String dstPath = ShowcaseConstants.DST_SOURCE_EXAMPLE
				+ type.getSimpleSourceName() + ".html";
		createPublicResource(dstPath, formattedSource);
	}


	/**
	 * Get the full contents of a resource.
	 *
	 * @param path the path to the resource
	 * @return the contents of the resource
	 */
	private String getResourceContents(String path)
			throws UnableToCompleteException {
		InputStream in = classLoader.getResourceAsStream(path);
		if (in == null) {
			logger.log(TreeLogger.ERROR, "Resource not found: " + path);
			throw new UnableToCompleteException();
		}

		StringBuffer fileContentsBuf = new StringBuffer();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			String temp;
			while ((temp = br.readLine()) != null) {
				fileContentsBuf.append(temp).append('\n');
			}
		} catch (IOException e) {
			logger.log(TreeLogger.ERROR, "Cannot read resource", e);
			throw new UnableToCompleteException();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}

		// Return the file contents as a string
		return fileContentsBuf.toString();
	}

	/**
	 * Load the contents of all CSS files used in the Showcase for a given
	 * theme/RTL mode, concatenated into one string.
	 *
	 * @param theme the style theme
	 * @param isRTL true if RTL mode
	 * @return the concatenated styles
	 */
	private String getStyleDefinitions(String theme, boolean isRTL)
			throws UnableToCompleteException {
		String cssContents = "";
		for (String path : SRC_CSS) {
			path = path.replace("$THEME", theme);
			if (isRTL) {
				path = path.replace("$RTL", "_rtl");
			} else {
				path = path.replace("$RTL", "");
			}
			cssContents += getResourceContents(path) + "\n\n";
		}
		return cssContents;
	}

	/**
	 * Ensure that we only generate files once by creating a placeholder file,
	 * then looking for it on subsequent generates.
	 *
	 * @return true if this is the first pass, false if not
	 */
	private boolean isFirstPass() {
		String placeholder = ShowcaseConstants.DST_SOURCE + "generated";
		try {
			OutputStream outStream = context.tryCreateResource(logger, placeholder);
			if (outStream == null) {
				return false;
			} else {
				context.commitResource(logger, outStream);
			}
		} catch (UnableToCompleteException e) {
			logger.log(TreeLogger.ERROR, "Unable to generate", e);
			return false;
		}
		return true;
	}
}