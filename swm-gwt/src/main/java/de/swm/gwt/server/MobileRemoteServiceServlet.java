package de.swm.gwt.server;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RPCServletUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.user.server.rpc.SerializationPolicy;



/**
 * Implementierung des {@link RemoteServiceServlet} der sowohl JsonP Requests handeln kann (cross domain RPC) als auch
 * GWT RPC vom lokalen Dateisystem z.B. mit PhoneGap.
 * 
 * 
 * <pre>
 * Beispiel zur Nutzung (wenn JsonP angeschaltet ist): 
 * Legende: 
 * __ wird ersetzt als - 
 * _ wird ersetzt als . 
 * X wird ersetzt als /
 * </pre>
 * 
 * <pre>
 * 	<generate-with class="com.google.gwt.user.rebind.rpc.JsonpServiceInterfaceProxyGenerator">
 * 		<when-type-assignable class="com.google.gwt.user.client.rpc.RemoteService" />
 * 	</generate-with>
 * 	<!-- http://mo$swm$i_intra_swm_de/mvg/-->
 * 	<define-property name="gwt.rpc.proxySuperclass"
 * 		values="de_swm_gxt_client_mobile_network_JsonpServiceProxy" />
 * 	<set-property name="gwt.rpc.proxySuperclass"
 * 		value="de_swm_gxt_client_mobile_network_JsonpServiceProxy" />
 * 	<define-property name="gwt.rpc.jsonp.host"
 * 		values="mo__swm__i_intra_swm_deXmvgXmvgfahrplanX" />
 * 	<set-property name="gwt.rpc.jsonp.host"
 * 		value="mo__swm__i_intra_swm_deXmvgXmvgfahrplanX" />
 * </pre>
 * 
 * 
 * Adapted from: http://timepedia.blogspot.com/2009/04/gwt-rpc-over-arbitrary-transports -uber.html , resp.
 * http://blog.daniel-kurka.de/2011/01/gwt-rpc-with-phonegap.html
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class MobileRemoteServiceServlet extends RemoteServiceServlet {

	private static final int HTTPS_PORT = 443;

	private static final int HTTP_PORT = 80;

	private static final String IS_POST = "POST";

	private static final long serialVersionUID = 1L;

	/**
	 * The request parameter for the gwt-rpc payload.
	 */
	public static final String RPC_PAYLOAD_PARAM = "rpcpayload";

	/**
	 * The callback parameter for the JSONP callback.
	 */
	public static final String CALLBACK_PARAM = "callback";

	/**
	 * The actual callback function name that will be retrieved from the {@link HttpServletRequest}.
	 */
	private String callbackFunction;

	private final boolean isJsonp;


	/**
	 * Default constructor - jsonp disabled.
	 * 
	 */
	public MobileRemoteServiceServlet() {
		this(false);
	}

	/**
	 * Default constructor.
	 * 
	 * @param isJsonp
	 *            true wenn JsonP, ansonsten GWT RPC
	 */
	public MobileRemoteServiceServlet(boolean isJsonp) {
		this.isJsonp = isJsonp;
	}



	@Override
	protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
		throws ServletException, IOException {
		if (isJsonp) {
			doPost(httpServletRequest, httpServletResponse);
		}
	}



	/**
	 * Override this method in order to control the parsing of the incoming request.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected String readContent(HttpServletRequest httpServletRequest) throws ServletException, IOException {
		if (isJsonp) {
			// grab the GWT-RPC payload string from the request
			String str = httpServletRequest.getMethod().equals(IS_POST) ? RPCServletUtils
					.readContentAsGwtRpc(httpServletRequest) : httpServletRequest.getParameter(RPC_PAYLOAD_PARAM);

			// decode it
			String ustr = URLDecoder.decode(str, "UTF-8");

			// retrieve the callback parameters from the request as well. This will
			// be used when padding the JSON response
			callbackFunction = httpServletRequest.getParameter(CALLBACK_PARAM);
			return ustr;
		} else {
			return super.readContent(httpServletRequest);
		}
	}



	/**
	 * Hilfsmethode für {@see #doGetSerializationPolicy(HttpServletRequest, String, String)},
	 * um den Aufruf von GWT-RPC aus Webanwendungen zu ermöglichen, die im Client im Filesystem
	 * abgelegt sind. Berechnet die korrekte Modul-Basis-Url auf dem Server.
	 * 
	 * @param request
	 *            der HTTP-Request
	 * @param moduleName
	 *            der Modulname
	 * @return die Basis URL auf dem Server
	 */
	private String getBaseUrl(HttpServletRequest request, String moduleName) {
		if (request.getServerPort() == HTTP_PORT || request.getServerPort() == HTTPS_PORT) {
			return request.getScheme() + "://" + "127.0.0.1" + request.getContextPath() + "/" + moduleName + "/";
		} else {
			return request.getScheme() + "://" + "127.0.0.1" + ":" + request.getServerPort() + request.getContextPath()
					+ "/" + moduleName + "/";
		}

	}



	/**
	 * Ermöglicht den Aufruf von GWT-RPC aus Webanwendungen, die im Client im Filesystem abgelegt sind
	 * (insb. PhoneGap).
	 * Ohne diese Methodenüberschreibung sucht GWT die benötigten Steuerdateien für
	 * die Serialisierung (xxx.gwt.rpc) an der falschen Stelle (an der Stelle, an der sie im Client
	 * im Filesystem abgelegt sind).
	 * 
	 * @param request
	 *            der HTTP-request
	 * @param moduleBaseURL
	 *            base-URL des Moduls
	 * @param strongName
	 *            a strong name that uniquely identifies a serialization policy file
	 * @return SerializationPolicy-Objekt
	 */
	@Override
	protected SerializationPolicy doGetSerializationPolicy(HttpServletRequest request, String moduleBaseURL,
		String strongName) {
		// moduleBaseURL: z.B. "file:///android_asset/www/mvgfahrplan/"
		// request.contextPath(): z.B. "/mvg"
		if (moduleBaseURL.startsWith("file:")) {

			String baseUrl = getBaseUrl(request, extractModuleName(moduleBaseURL));
			return super.doGetSerializationPolicy(request, baseUrl, strongName);
		} else {
			return super.doGetSerializationPolicy(request, moduleBaseURL, strongName);
		}
	}



	/**
	 * Extrahiert den Modul-Namen aus der module base url.
	 * 
	 * @param moduleBaseURL
	 *            den Modulnamen.
	 * @return die Module base URL
	 */
	public static final String extractModuleName(String moduleBaseURL) {
		if (moduleBaseURL == null) {
			return null;
		} else if (moduleBaseURL.length() == 0) {
			return "";
		} else {
			if (moduleBaseURL.endsWith("/")) {
				moduleBaseURL = moduleBaseURL.substring(0, moduleBaseURL.length() - 1);
			}

			if (moduleBaseURL.lastIndexOf("/") > 0) {
				final int lastSlash = moduleBaseURL.lastIndexOf("/");
				return moduleBaseURL.substring(lastSlash + 1, moduleBaseURL.length());
			}

			return moduleBaseURL;
		}

	}



	@Override
	public String processCall(String payload) throws SerializationException {
		if (isJsonp) {
			try {
				RPCRequest rpcRequest = RPC.decodeRequest(payload, this.getClass(), this);
				onAfterRequestDeserialized(rpcRequest);
				String encodedResponse = RPC.invokeAndEncodeResponse(this, rpcRequest.getMethod(),
						rpcRequest.getParameters(), rpcRequest.getSerializationPolicy(), rpcRequest.getFlags());

				// the responseStr by default is a GWT-RPC string. We'll need to
				// turn it into a JSON object, and pad it with the
				// callback function.
				String responseStr = callbackFunction + "({\"entry\":\"" + encodedResponse.replaceAll("\"", "\\\\\"")
						+ "\"});";
				return responseStr;

			} catch (IncompatibleRemoteServiceException ex) {
				log("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
				return RPC.encodeResponseForFailure(null, ex);
			}
		} else {
			return super.processCall(payload);
		}
	}
}
