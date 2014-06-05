package de.swm.gwt.client.mobile.network;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Callback das einen Tiemout besitzt.
 *
 * @param <T> der Typ
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 */
public class AsyncCallbackWithTimeout<T> implements AsyncCallback<T> {
	/**
	 * Timeout in ms. *
	 */
	private static final int DEFAULT_TIMEOUT = 60000;
	private boolean complete;
	private Timer timer;
	private Error timeout;
	private final AsyncCallback<T> wrapppedCallback;


	/**
	 * Default constructor.
	 *
	 * @param callback der gewrappte callback
	 */
	public AsyncCallbackWithTimeout(AsyncCallback<T> callback) {
		this(callback, DEFAULT_TIMEOUT);
	}


	/**
	 * Default constructor.
	 *
	 * @param callback      der gewrappte callback
	 * @param timeoutMillis der timeout
	 */
	public AsyncCallbackWithTimeout(AsyncCallback<T> callback, int timeoutMillis) {
		this.wrapppedCallback = callback;
		this.complete = false;
		timer = new Timer() {
			@Override
			public void run() {
				onTimeout();
			}
		};
		timer.schedule(timeoutMillis);
		timeout = new TimeOutException();
	}


	@Override
	public void onFailure(Throwable caught) {
		if (!complete) {
			complete = true;
			timer.cancel();
		}
		wrapppedCallback.onFailure(caught);
	}


	@Override
	public void onSuccess(T result) {
		if (!complete) {
			complete = true;
			timer.cancel();
			wrapppedCallback.onSuccess(result);
		}
	}


	/**
	 * Methode um den Timeout zu erhoehen.
	 *
	 * @param expectedTimeNeeded die erwartete Zeit die der call noch brauchen wird.
	 */
	public void resetTimeout(Integer expectedTimeNeeded) {
		if (!complete) {
			timer.schedule(expectedTimeNeeded == null ? DEFAULT_TIMEOUT : expectedTimeNeeded.intValue());
		}
	}

	/**
	 * Exception um Timeouts zu bemerken.
	 *
	 * @author wiese.daniel <br>
	 *         copyright (C) 2011, SWM Services GmbH
	 */
	public static class TimeOutException extends Error {
		private static final long serialVersionUID = 1L;
		private long startTime = System.currentTimeMillis();


		/**
		 * Default constructor.
		 */
		public TimeOutException() {
		}


		public String getMessage() {
			return "An operation timed out; this may be caused by a slow or unreliable network connection, a server outage, or a bug. (waited "
					+ (System.currentTimeMillis() - startTime) + " ms)";
		}
	}


	/**
	 * Wird vom Timer aufgerufen, wenn der call nicht wie erwartet zurueckkommt.
	 */
	public void onTimeout() {
		if (!complete) {
			complete = true;
			this.onFailure(timeout);
		}
	}
}