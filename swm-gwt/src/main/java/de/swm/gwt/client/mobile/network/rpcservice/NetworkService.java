package de.swm.gwt.client.mobile.network.rpcservice;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Muss als WGT Service mit einem eignen Servlet in der Anwenung gebunden werden.
 * @author wiese.daniel
 * <br>copyright (C) 2011, SWM Services GmbH
 *
 */
@RemoteServiceRelativePath("networkMonitoringServlet")
public interface NetworkService extends RemoteService {
	
	/**
	 * Liert true wenn der server und die Verbindng erreichbar ist.
	 * @return true wenn errreichbar.
	 */
	String checkNetwork();

}
