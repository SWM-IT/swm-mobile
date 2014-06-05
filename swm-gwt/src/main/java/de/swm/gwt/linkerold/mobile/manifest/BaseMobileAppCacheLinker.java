package de.swm.gwt.linkerold.mobile.manifest;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.AbstractLinker;
import com.google.gwt.core.ext.linker.Artifact;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.EmittedArtifact;
import com.google.gwt.core.ext.linker.LinkerOrder;
import com.google.gwt.core.ext.linker.LinkerOrder.Order;
import com.google.gwt.core.ext.linker.Shardable;
import com.google.gwt.core.ext.linker.impl.SelectionInformation;

import java.util.Arrays;
import java.util.Date;

/**
 * Genierier das HTML5 manifest fuer mobile Anwendungen (damit diese offline
 * verfuegbar sind).
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
@Shardable
@LinkerOrder(Order.POST)
public abstract class BaseMobileAppCacheLinker extends AbstractLinker {

	private static final String MANIFEST = "appcache.nocache.manifest";
	private final String warName;
	private final String landingpageName;
	private final boolean isRoot;

	/**
	 * Default constructor.
	 * 
	 * @param warName
	 *            der name der finalen war datei.
	 * @param landingpageName
	 *            der name der start html datei (Landingpage)
	 */
	public BaseMobileAppCacheLinker(String warName, String landingpageName) {
		this.isRoot = false;
		this.warName = warName;
		this.landingpageName = landingpageName;
	}

	/**
	 * Default constructor.
	 * 
	 * @param warName
	 *            der name der finalen war datei.
	 * @param landingpageName
	 *            der name der start html datei (Landingpage)
	 */
	public BaseMobileAppCacheLinker(boolean isRoot, String warName,
			String landingpageName) {
		this.isRoot = isRoot;
		this.warName = null;
		this.landingpageName = landingpageName;
	}

	@Override
	public String getDescription() {
		return "AppCacheLinker";
	}

	@Override
	public ArtifactSet link(TreeLogger logger, LinkerContext context,
			ArtifactSet artifacts, boolean onePermutation)
			throws UnableToCompleteException {
		logger.log(TreeLogger.INFO, "Starte SWM HTML5 App-Cache Linker");

		ArtifactSet toReturn = new ArtifactSet(artifacts);

		if (onePermutation) {
			return toReturn;
		}

		if (toReturn.find(SelectionInformation.class).isEmpty()) {
			logger.log(TreeLogger.INFO, "DevMode warning: Clobbering "
					+ MANIFEST + " to allow debugging. "
					+ "Recompile before deploying your app!");
			artifacts = null;
		}

		// Create the general cache-manifest resource for the landing page:
		toReturn.add(emitLandingPageCacheManifest(context, logger, artifacts));
		return toReturn;
	}

	/**
	 * Andere zu cachende Ressourcen.
	 * 
	 * @return andere Ressorucen
	 */
	protected abstract String[] otherCachedFiles();

	/**
	 * Creates the cache-manifest resource specific for the landing page.
	 * 
	 * @param context
	 *            the linker environment
	 * @param logger
	 *            the tree logger to record to
	 * @param artifacts
	 *            {@code null} to generate an empty cache manifest
	 * @return lister der dateien die ge-cached werden sollen.
	 * @throws UnableToCompleteException .
	 */
	private Artifact<?> emitLandingPageCacheManifest(LinkerContext context,
			TreeLogger logger, ArtifactSet artifacts)
			throws UnableToCompleteException {
		StringBuilder publicSourcesSb = new StringBuilder();
		StringBuilder staticResoucesSb = new StringBuilder();

		if (artifacts != null) {
			// Iterate over all emitted artifacts, and collect all cacheable
			// artifacts
			for (@SuppressWarnings("rawtypes")
			Artifact artifact : artifacts) {
				if (artifact instanceof EmittedArtifact) {
					EmittedArtifact ea = (EmittedArtifact) artifact;
					String pathName = ea.getPartialPath();
					if (pathName.endsWith("symbolMap")
							|| pathName.endsWith(".xml.gz")
							|| pathName.endsWith("rpc.log")
							|| pathName.endsWith("gwt.rpc")
							|| pathName.endsWith("manifest.txt")
							|| pathName.startsWith("rpcPolicyManifest")
							|| pathName.endsWith("hosted.html")
							|| pathName.startsWith("soycReport/")) {
						// skip these resources
					} else {
						publicSourcesSb.append(pathName.replace('\\', '/')
								+ "\n");
					}
				}
			}

			String[] cacheExtraFiles = getCacheExtraFiles();
			if (isRoot) {
				staticResoucesSb.append("/" + landingpageName);
			} else {
				staticResoucesSb.append("/" + warName + "/" + landingpageName);
			}
			for (int i = 0; i < cacheExtraFiles.length; i++) {
				staticResoucesSb.append(cacheExtraFiles[i]);
				staticResoucesSb.append("\n");
			}
		}

		// build cache list
		StringBuilder sb = new StringBuilder();
		sb.append("CACHE MANIFEST\n");
		sb.append("# Unique id #" + (new Date()).getTime() + "."
				+ Math.random() + "\n");
		// we have to generate this unique id because the resources can change
		// but
		// the hashed cache.html files can remain the same.
		sb.append("# Note: must change this every time for cache to invalidate\n");
		sb.append("\n");
		sb.append("CACHE:\n");
		sb.append("# Static app files\n");
		sb.append(staticResoucesSb.toString());
		sb.append("\n# Generated app files\n");
		sb.append(publicSourcesSb.toString());
		sb.append("\n\n");
		sb.append("# All other resources require the user to be online.\n");
		sb.append("NETWORK:\n");
		sb.append("*\n");

		logger.log(TreeLogger.INFO,
				"Bitte sicherstellen, dass die html Start-Seite das <html> Tag :"
						+ " <html manifest=" + context.getModuleFunctionName()
						+ "/" + MANIFEST + "\"> definiert!");

		// Create the manifest as a new artifact and return it:
		return emitString(logger, sb.toString(), MANIFEST);
	}

	/**
	 * Obtains the extra files to include in the manifest. Ensures the returned
	 * array is not null.
	 * 
	 * @return never null
	 */
	private String[] getCacheExtraFiles() {
		String[] cacheExtraFiles = otherCachedFiles();
		return cacheExtraFiles == null ? new String[0] : Arrays.copyOf(
				cacheExtraFiles, cacheExtraFiles.length);
	}
}
