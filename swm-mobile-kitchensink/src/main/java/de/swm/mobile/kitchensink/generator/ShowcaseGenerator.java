package de.swm.mobile.kitchensink.generator;


import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import de.swm.mobile.kitchensink.client.ShowcaseAnnotations;
import de.swm.mobile.kitchensink.client.ShowcaseConstants;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;

/**
 * Generate the source code, ui.xml source used in the Showcase
 * examples.
 */
public class ShowcaseGenerator extends Generator {

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
			generateUiXMLFiles(type);
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
	 * Erzeugt die UI.xml dateien.
	 *
	 */
	private void generateUiXMLFiles(JClassType type)
			throws UnableToCompleteException {
		// Look for annotation
		if (!type.isAnnotationPresent(ShowcaseAnnotations.ShowcaseUiXML.class)) {
			return;
		}

		// Get the package info
		String pkgName = type.getPackage().getName();
		String pkgPath = pkgName.replace('.', '/') + "/";

		// Generate each raw source file
		String[] filenames = type.getAnnotation(ShowcaseAnnotations.ShowcaseUiXML.class).value();
		for (String filename : filenames) {
			// Check if the file has already been generated.
			String path = pkgName + filename;
			if (rawFiles.contains(path)) {
				continue;
			}
			rawFiles.add(path);

			// Get the file contents
			String fileContents = getResourceContents(pkgPath + filename);

			// Make the source pretty
			fileContents = fileContents.replace("<", "&lt;");
			fileContents = fileContents.replace(">", "&gt;");
			fileContents = fileContents.replace("* \n   */\n", "*/\n");
			fileContents = "<pre>" + fileContents + "</pre>";

			// Save the raw source in the public directory
			String dstPath = ShowcaseConstants.DST_SOURCE_UIXML + filename + ".html";
			createPublicResource(dstPath, fileContents);
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