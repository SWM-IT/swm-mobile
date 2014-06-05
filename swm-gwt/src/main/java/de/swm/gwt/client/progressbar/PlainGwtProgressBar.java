package de.swm.gwt.client.progressbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Steuert eine ProgressBar fuer PlainGWT.
 * @author Steiner.Christian<br>
 * copyright 2014 SWM Service GmbH 
 */
 //Angepasste MobileProgressBar aus swm-mobile
	//TODO Unterscheidung der ProgressBar Mit Buttons - Ohne Buttons    erforderlich ?
public class PlainGwtProgressBar implements IProgressBarNoCancelWaitDialog {
	private final List<CancelMonitor> callbacksToCancel = new ArrayList<CancelMonitor>();
	private final List<RunAsyncCallback> runAsyncCallbacks = new ArrayList<RunAsyncCallback>();
	private final List<AsyncCallback<?>> asyncCallbacks = new ArrayList<AsyncCallback<?>>();
	private boolean isStopped;
	private final ProgressBarPopup loadingPanel;



	/**
	 * Default constructor.
	 */
	public PlainGwtProgressBar() {
		this(new ProgressBarPopup(true));
	}

	/**
	 * Constructor with possibility to enable info text.
	 *
	 * @param progressBarPopup  das zu verwendende Popup.
	 */
	public PlainGwtProgressBar(ProgressBarPopup progressBarPopup) {
		isStopped = true;
		loadingPanel = progressBarPopup;
	}





	/**
	 * Sets the information text that is shown inside the loading indicator popup.
	 *
	 * @param loadingText der Titel.
	 */
	public void setLoadingText(String loadingText) {
		this.loadingPanel.setLoadingText(loadingText);
	}

	/**
	 * Sets the subtitle that is shown inside the loading indicator popup.
	 *
	 * @param loadingSubtitleText der untertitel.
	 */
	public void setLoadingSubtitleText(String loadingSubtitleText) {
		this.loadingPanel.setLoadingSubtitleText(loadingSubtitleText);
	}


	/**
	 * Zeigt den Wartebalken an.
	 *
	 *
	 */
	public void showDialog() {
		loadingPanel.showCentered(true);
	}



	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public <T> AsyncCallback<T> start(final AsyncCallback<T> callbackToExecute) {
		if (isStopped || !this.loadingPanel.isVisible()) {
			showDialog();
		}
		isStopped = false;
		final CancelMonitor monitor = new CancelMonitor();
		AsyncCallback<T> wrappedCallback = new AsyncCallback<T>() {

			@Override
			public void onFailure(Throwable error) {
				if (!monitor.isCanceled()) {
					callbackToExecute.onFailure(error);
				}
				removeFromQueue(this);
				// Falls es sich um eine Unchecked Exception auf dem Server handelt
				// oder falls ein anderer unerwarteter Fehler auftritt (Netzwerk etc)
				// oder falls der RemoteService nicht kompatibel ist
				// werden alle Callbacks abgebrochen und der Dialog geschlossen.
				if (error instanceof InvocationException || error instanceof IncompatibleRemoteServiceException) {
					cancelCallbacksAndForceStop();
				}
			}



			@Override
			public void onSuccess(T success) {
				if (!monitor.isCanceled()) {
					callbackToExecute.onSuccess(success);
				}
				removeFromQueue(this);
			}

		};
		callbacksToCancel.add(monitor);
		asyncCallbacks.add(wrappedCallback);
		return wrappedCallback;
	}



	/**
	 * {@inheritDoc}
	 *
	 */
	@Override
	public void start(final RunAsyncCallback callbackToExecute) {

		if (isStopped || !this.loadingPanel.isVisible()) {
			showDialog();
		}
		isStopped = false;
		final CancelMonitor monitor = new CancelMonitor();
		RunAsyncCallback wrappedCallback = new RunAsyncCallback() {

			@Override
			public void onFailure(Throwable error) {
				if (!monitor.isCanceled()) {
					callbackToExecute.onFailure(error);
				}
				cancelCallbacksAndForceStop();
			}



			@Override
			public void onSuccess() {
				if (!monitor.isCanceled()) {
					callbackToExecute.onSuccess();
				}
				removeFromQueue(this);
			}

		};

		callbacksToCancel.add(monitor);
		runAsyncCallbacks.add(wrappedCallback);
		GWT.runAsync(wrappedCallback);
	}



	/**
	 * Canceled alles und entfernt alle wartenden callbacks.
	 */
	private void cancelCallbacksAndForceStop() {
		this.runAsyncCallbacks.clear();
		this.asyncCallbacks.clear();
		for (CancelMonitor toCancel : callbacksToCancel) {
			toCancel.setCanceled(true);
		}
		this.callbacksToCancel.clear();
		isStopped = true;

		this.loadingPanel.setVisible(false);
	}



	/**
	 * Entfernt den callback und canceled den Dialog, wenn es die letzte Aktion ist.
	 *
	 * @param toRemove
	 *            der callbck der zu entfernen ist.
	 */
	private void removeFromQueue(RunAsyncCallback toRemove) {
		this.runAsyncCallbacks.remove(toRemove);
		if (asyncCallbacks.isEmpty() && runAsyncCallbacks.isEmpty()) {
			cancelCallbacksAndForceStop();
		}
	}



	/**
	 * Entfernt den callback und canceled den Dialog, wenn es die letzte Aktion ist.
	 *
	 * @param toRemove
	 *            der callbck der zu entfernen ist.
	 */
	private void removeFromQueue(AsyncCallback<?> toRemove) {
		this.asyncCallbacks.remove(toRemove);
		if (asyncCallbacks.isEmpty() && runAsyncCallbacks.isEmpty()) {
			cancelCallbacksAndForceStop();
		}
	}

	/**
	 * Hilfsklasse um einen laufenden callback zu canceln.
	 *
	 * @author Wiese.Daniel <br>
	 *         copyright (C) 2010-2011, SWM Services GmbH
	 *
	 */
	public static class CancelMonitor {
		private boolean canceled = false;



		public boolean isCanceled() {
			return canceled;
		}



		public void setCanceled(boolean canceled) {
			this.canceled = canceled;
		}

	}

}
