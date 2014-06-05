package de.swm.gwt.client.mobile.network;

/**
 * Definiert ein Interface, mit dem Eine Versionskontrolle fuer eine HTML5 Anweung durchgefuehrt werden kann.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface IVersionControlMonitor {

	/**
	 * Sendet die serverseitige Version zum Server.
	 * 
	 * @param serverVersion
	 *            die Serverseitige version.
	 */
	void publishServerSideVersion(String serverVersion);

}
