package de.swm.gwt.client.mobile.network;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;

/**
 * Will fix a current io6 bug: Ios6 is chaching http request.
 */
public class MobileRequestBuilder extends RpcRequestBuilder {


	/**
	 * Called by {@link #finish()} prior to returning the RequestBuilder to the
	 * caller.
	 * <p/>
	 * The default implementation sets the {@value #STRONG_NAME_HEADER} header to
	 * the value returned by {@link com.google.gwt.core.client.GWT#getPermutationStrongName()}.
	 *
	 * @param rb The RequestBuilder that is currently being configured
	 */
	@Override
	protected void doFinish(RequestBuilder rb) {
		rb.setHeader("Cache-Control", "no-cache");
		super.doFinish(rb);
	}
}