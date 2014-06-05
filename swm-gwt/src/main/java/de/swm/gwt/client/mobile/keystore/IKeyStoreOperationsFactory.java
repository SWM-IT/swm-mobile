package de.swm.gwt.client.mobile.keystore;

/**
 * Factory zur Erzeugung von KeyStoreOperations-Instanzen.
 *
 * @author steuer.konstantin
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public interface IKeyStoreOperationsFactory {

	/**
	 * Erstellt eine neue {@link de.swm.gwt.client.mobile.keystore.IKeyStoreOperations}-Instanz.
	 * @return {@link de.swm.gwt.client.mobile.keystore.IKeyStoreOperations}-Instanz
	 */
	IKeyStoreOperations getNewInstance();
}
