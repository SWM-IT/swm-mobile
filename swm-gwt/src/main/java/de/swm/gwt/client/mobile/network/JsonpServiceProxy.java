package de.swm.gwt.client.mobile.network;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.URL;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;
import com.google.gwt.user.client.rpc.impl.Serializer;

import de.swm.gwt.client.utils.Utils;



/**
 * An extension of {@link RemoteServiceProxy} that uses JSONP as a transport mechanism. Makes use of
 * {@link JsonpRequestBuilder} to make JSONP requests
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class JsonpServiceProxy extends RemoteServiceProxy {

	/**
	 * The request parameter for the gwt-rpc payload.
	 */
	public static final String RPC_PAYLOAD_PARAM = "rpcpayload";

	/**
	 * The callback parameter for the JSONP callback.
	 */
	public static final String CALLBACK_PARAM = "callback";



	/**
	 * Protected constructor of {@code JsonpServiceProxy}.
	 * 
	 * @param moduleBaseURL
	 *            the base URL of the module
	 * @param remoteServiceRelativePath
	 *            the relative path to this remote service
	 * @param serializationPolicyName
	 *            the serialization policy name
	 * @param serializer
	 *            the serializer
	 */
	protected JsonpServiceProxy(String moduleBaseURL, String remoteServiceRelativePath, String serializationPolicyName,
			Serializer serializer) {
		super(moduleBaseURL, remoteServiceRelativePath, serializationPolicyName, serializer);
	}



	/**
	 * Wenn Ok kommt.
	 * 
	 * @param encodedResponse
	 *            .
	 * @return .
	 */
	static boolean isReturnValue(String encodedResponse) {
		return encodedResponse.startsWith("//OK");
	}



	/**
	 * Return <code>true</code> if the encoded response contains a checked exception that was thrown by the method
	 * invocation.
	 * 
	 * @param encodedResponse
	 *            .
	 * @return <code>true</code> if the encoded response contains a checked exception that was thrown by the method
	 *         invocation
	 */
	static boolean isThrownException(String encodedResponse) {
		return encodedResponse.startsWith("//EX");
	}



	@Override
	protected <T> Request doInvoke(final ResponseReader responseReader, String methodName,
		RpcStatsContext statsContext, String requestData, final AsyncCallback<T> tAsyncCallback) {

		try {

			// create a JsonpRequestBuilder to make hte call with
			JsonpRequestBuilder jprb = new JsonpRequestBuilder();
			jprb.setCallbackParam(CALLBACK_PARAM);

			// request an Object using the JsonpRequestBuilder using the GWT RPC
			// payload
			String serviceEntryPoint = getServiceEntryPoint();
			//int indexOf = serviceEntryPoint.indexOf("mvgfahrplan");
			//serviceEntryPoint = serviceEntryPoint.substring(indexOf, serviceEntryPoint.length());
			//serviceEntryPoint = "http://mo-swm-i.intra.swm.de/mvg/" + serviceEntryPoint;
			Utils.console("Seding RPC Request:" + serviceEntryPoint);
			// serviceEntryPoint = "http://localhost:8090/mvg/" + serviceEntryPoint;

			String encode = URL.encode(requestData);
			//encode = encode.replace("file:///android_asset/www/", "http://mo-swm-i.intra.swm.de/mvg/");
			Utils.console("Payload:" + encode);
			jprb.requestObject(serviceEntryPoint + "?" + RPC_PAYLOAD_PARAM + "=" + encode,
					new AsyncCallback<JsonpResponseJso>() {

						public void onFailure(Throwable throwable) {
							Utils.console("RPC Fehler: " + throwable.getMessage());
							tAsyncCallback
									.onFailure(new InvocationException(
											"Unable to initiate the asynchronous service invocation -- check the network connection"));
							throwable.printStackTrace();
						}



						/**
						 * On success, the JSONP callback will send back a StringJso object, which contains one field: a
						 * GWT-RPC encoded string. We'll decode the GWT-RPC response and return control back to the
						 * onSuccess/onFailure of the {@link AsyncCallback} of the initial GWT-RPC call
						 * 
						 * @param encodedResponse
						 */
						@SuppressWarnings("unchecked")
						public void onSuccess(JsonpResponseJso encodedResponse) {
							try {

								// the encoded response is returned by calling
								// 'getEntry()' in the StringJso. We'll clean up
								// the escaping of quotes though
								String rpcString = encodedResponse.getEntry().replaceAll("\\\\\"", "\"");
								Utils.console("RCP -Success: " + rpcString);
								if (isReturnValue(rpcString)) {
									tAsyncCallback.onSuccess((T) responseReader.read(createStreamReader(rpcString)));
								} else if (isThrownException(encodedResponse.getEntry())) {

									// using createStreamReader(..).readObject();
									tAsyncCallback.onFailure((Throwable) createStreamReader(rpcString).readObject());

								} else {
									tAsyncCallback.onFailure(new InvocationException("Unknown return value type"));
								}
							} catch (SerializationException e) {
								tAsyncCallback.onFailure(new InvocationException("Failure deserializing object " + e));
							}
						}
					});
		} catch (Exception ex) {
			InvocationException iex = new InvocationException(
					"Unable to initiate the asynchronous service invocation -- check the network connection", ex);
			tAsyncCallback.onFailure(iex);
		} finally {
			if (statsContext.isStatsAvailable()) {
				statsContext.stats(statsContext.bytesStat(methodName, requestData.length(), "requestSent"));
			}
		}
		return null;
	}

	/**
	 * Jsonp response object.
	 * 
	 * @author wiese.daniel <br>
	 *         copyright (C) 2011, SWM Services GmbH
	 * 
	 */
	public static class JsonpResponseJso extends JavaScriptObject {

		/**
		 * Default constructor.
		 */
		protected JsonpResponseJso() {
		}



		/**
		 * Liefert den Json string vom Server.
		 * 
		 * @return der Json String.
		 */
		public final native String getEntry() /*-{
												return this.entry;
												}-*/;
	}

}
