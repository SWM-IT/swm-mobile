package de.swm.commons.mobile.client.utils;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Ansync call back with empty onError method.
 *
 * @param <T> the type
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public abstract class AsyncCallbackSuccess<T> implements AsyncCallback<T> {
	/**
	 * Called when an asynchronous call fails to complete normally.
	 * {@link com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException}s, {@link com.google.gwt.user.client.rpc.InvocationException}s,
	 * or checked exceptions thrown by the service method are examples of the type
	 * of failures that can be passed to this method.
	 * <p/>
	 * <p>
	 * If <code>caught</code> is an instance of an
	 * {@link com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException} the application should try to
	 * get into a state where a browser refresh can be safely done.
	 * </p>
	 *
	 * @param caught failure encountered while executing a remote procedure call
	 */
	@Override
	public void onFailure(Throwable caught) {

	}

}
