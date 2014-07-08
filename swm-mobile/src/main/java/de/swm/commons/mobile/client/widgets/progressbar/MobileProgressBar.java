/*
 * Copyright 2011 SWM Services GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.client.widgets.progressbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import de.swm.commons.mobile.client.widgets.LoadingIndicatorPopup;
import de.swm.gwt.client.progressbar.IProgressBarNoCancelWaitDialog;

import java.util.ArrayList;
import java.util.List;



/**
 * Progress bar for mobile applications.
 * 
 */
public class MobileProgressBar implements IProgressBarNoCancelWaitDialog {

	private final List<CancelMonitor> callbacksToCancel = new ArrayList<CancelMonitor>();
	private final List<RunAsyncCallback> runAsyncCallbacks = new ArrayList<RunAsyncCallback>();
	private final List<AsyncCallback<?>> asyncCallbacks = new ArrayList<AsyncCallback<?>>();
	private boolean isStopped;
	private final LoadingIndicatorPopup loadingPanel;



	/**
	 * Default constructor.
	 */
	public MobileProgressBar() {
		isStopped = true;
		this.loadingPanel = new LoadingIndicatorPopup();
	}
	
	/**
	 * Constructor with possibility to enable info text.
	 * 
	 * @param showInfoText whether an info text should be shown inside the loading indicator popup
	 */
	public MobileProgressBar(boolean showInfoText) {
		isStopped = true;
		this.loadingPanel = new LoadingIndicatorPopup(showInfoText);
	}

	/**
	 * Sets the information text that is shown inside the loading indicator popup.
	 * 
	 * @param loadingText
	 */
	public void setLoadingText(String loadingText) {
		this.loadingPanel.setLoadingText(loadingText);
	}
	
	/**
	 * Sets the subtitle that is shown inside the loading indicator popup.
	 * 
	 * @param loadingText
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
	 * @see de.swm.issng.gkpk.client.utils.progressbar.IProgressBarWaitDialog#start()
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
	 * @see de.swm.issng.gkpk.client.utils.progressbar.IProgressBarWaitDialog#start()
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
