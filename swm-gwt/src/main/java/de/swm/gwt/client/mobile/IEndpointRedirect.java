package de.swm.gwt.client.mobile;

/**
 * Is used for the decission if aen GWT RPC endpoint will be permanently redirected to an new server.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public interface IEndpointRedirect {

	/**
	 * Returns if the execution enviroment is an non standard (e.g. phonegap) environment.
	 * @return true if phonegap
	 */
	boolean isNonDefaultEnvironment();

	/**
	 * Der backendserver der im fall eines Redirects angesteuert werden soll.
	 * @return der backend server.
	 */
	String backendServer();
}
