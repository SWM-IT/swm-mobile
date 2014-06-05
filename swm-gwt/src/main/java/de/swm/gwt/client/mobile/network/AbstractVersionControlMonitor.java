package de.swm.gwt.client.mobile.network;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import de.swm.gwt.client.mobile.ApplicationCache;
import de.swm.gwt.client.mobile.ApplicationRedirecter;
import de.swm.gwt.client.mobile.keystore.IStorage;
import de.swm.gwt.client.progressbar.IProgressBarNoCancelWaitDialog;
import de.swm.gwt.client.utils.Utils;


/**
 * This abstract class handles the logic if a new version becomes available while
 * the current application is running.
 *
 * @author fuchs.florian, wiese.daniel <br>
 *         copyright (C) 2012, SWM Services GmbH
 */
public abstract class AbstractVersionControlMonitor implements IVersionControlMonitor {

	private static final int FORCE_RELOAD_AFTER_SECONDS = 20;

	private static final int MILISECONDS = 1000;

	/**
	 * Force update after 1.5 Minutes. *
	 */
	private int forceReloadInMs = (FORCE_RELOAD_AFTER_SECONDS * MILISECONDS);

	@Inject
	private IProgressBarNoCancelWaitDialog progressBarNoCancelWaitDialog;

	/**
	 * Flag: Versionupgrade is running right now.
	 */
	private boolean updateIsRunning = false;

	/**
	 * Flag: Der cache wurde im Hintegrund bereits aktualisert.
	 */
	private boolean cacheUpdated = false;

	private final ApplicationCache cache;

	private String currentVersion;

	private String newVersion;

	private final IStorage storage;

	/**
	 * Default constructor.
	 */
	public AbstractVersionControlMonitor() {
		this(null);
	}

	/**
	 * Default constructor.
	 */
	public AbstractVersionControlMonitor(IStorage storage) {
		this.storage = storage;
		if (ApplicationCache.isSupported()) {
			this.cache = ApplicationCache.getApplicationCache();
			registerCacheUpdateHandlers(cache);
		} else {
			this.cache = null;
		}
	}


	/**
	 * Wird stets uebermittelt wenn die aktuelle Serverversion bekanntgegeben wird.
	 *
	 * @param newVersion18NMessage the internationalized massage that a new version is available
	 * @param newVersion           the version id of the new version
	 * @param currentVersion       the version id of the installed version.
	 */
	public void updateHTML5Cache(String newVersion18NMessage, String newVersion, String currentVersion) {
		this.currentVersion = currentVersion;
		this.newVersion = newVersion;

		if (!updateIsRunning && GWT.isScript()) {
			updateIsRunning = true;
			Utils.console("Trying to update offline cache...");
			if (ApplicationCache.isSupported() && cache != null) {
				showNewVersionInfo(newVersion18NMessage, currentVersion, newVersion);
				showProgressBar();

				if (cache.getStatus() == ApplicationCache.IDLE) {
					Utils.console("Cache is idle - triggering Update");
					cache.update();
				} else if (cache.getStatus() == ApplicationCache.UPDATEREADY) {
					Utils.console("Cache is updateready - triggering swap of cache");
					onUpdateReady();
				}

				// Cache wurde im Hintergrund bereits upgedated.
				if (cacheUpdated) {
					updateIsRunning = false;
					ApplicationRedirecter.reloadApp();
				}


				//Auf Android wird der cache nicht getriggert > refresh nach x Minuten erzwingen.
				forceReload();
			} else {
				// nur reload
				updateIsRunning = false;
				ApplicationRedirecter.reloadApp();
			}
		}

	}


	/**
	 * Registriert die Handler fuer ein Cache update.
	 *
	 * @param cache der cache.
	 */
	protected void registerCacheUpdateHandlers(final ApplicationCache cache) {

		cache.addEventListener("updateready", new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				onUpdateReady();
			}
		}, false);

		cache.addEventListener("noupdate", new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				onNoUpdate();
			}
		}, false);

		cache.addEventListener("checking", new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				onChecking();
			}
		}, false);

		cache.addEventListener("error", new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				onError();
			}
		}, false);
	}


	/**
	 * Bei Cache Event "updateready". Swapt Cache und aktualisiert storage via updateStorage.
	 */
	protected void onUpdateReady() {
		Utils.console("Cache Update erfolgreich");
		// den neuen cache benutzen
		cache.swapCache();
		Utils.console("Swap cache erfolgreich - reload folgt");
		cacheUpdated = true;

		updateStorage(newVersion, currentVersion);

		// nur reload wenn Version-Meldung vom server bereits gekommen ist.
		if (updateIsRunning) {
			updateIsRunning = false;
			ApplicationRedirecter.reloadApp();
		}
	}


	/**
	 * Bei Cache Event "error". Default: Logging.
	 */
	protected void onError() {
		Utils.console("Cache Event error: Fehler beim Update des Caches");
	}


	/**
	 * Bei Cache Event "checking". Default: Logging.
	 */
	protected void onChecking() {
		Utils.console("Cache Event checking: Cache wird ueberprueft...");
	}


	/**
	 * Bei Cache Event "noupdate". Default: Logging.
	 */
	protected void onNoUpdate() {
		Utils.console("Cache Event noupdate: Kein Update erforderlich");
	}


	/**
	 * Default bahaviour: this will clean the current storage
	 * (via IStorage passed to the constructor)
	 */
	protected void updateStorage(String newVersion, String currentVersion) {
		this.newVersion = newVersion;
		this.currentVersion = currentVersion;
		if (storage != null) {
			storage.clear();
		}
	}


	/**
	 * Zeigt unendliche Progressbar bis zum Refresch der Anwendung.
	 *
	 * @return Callback.
	 */
	protected void showProgressBar() {
		AsyncCallback<Void> emptyCallback = new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {

			}


			@Override
			public void onSuccess(Void result) {

			}
		};

		emptyCallback = progressBarNoCancelWaitDialog.start(emptyCallback);
	}

	/**
	 * Zeigt dem User einen Updatehinweis.
	 *
	 * @param newVersionMessage String msg.
	 * @param currentVersion    aktuelle Version.
	 * @param newVersion        Neue Version.
	 */
	protected void showNewVersionInfo(String newVersionMessage, String currentVersion, String newVersion) {
		Window.alert(newVersionMessage + " - (" + currentVersion + ")/(" + newVersion + ") ");
	}

	/**
	 * Startet den Timer, der die Anwendung nach forceReloadInMs neu laedt.
	 */
	protected void forceReload() {
		Timer reloadTimer = new Timer() {
			@Override
			public void run() {
				updateIsRunning = false;
				Utils.console("Forcing cache update...");
				//Force update --> update rady laedt die Anwendung neu
				cache.update();
			}
		};
		reloadTimer.schedule(forceReloadInMs);
	}


	public int getForceReloadInMs() {
		return forceReloadInMs;
	}


	/**
	 * Nach dieser Zeit in ms wird die Anwendung zwangsweise neu geladen.
	 *
	 * @param forceReloadInMs Zeit in ms.
	 */
	public void setForceReloadInMs(int forceReloadInMs) {
		this.forceReloadInMs = forceReloadInMs;
	}
}
