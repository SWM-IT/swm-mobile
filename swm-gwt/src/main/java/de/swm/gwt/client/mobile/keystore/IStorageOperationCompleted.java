package de.swm.gwt.client.mobile.keystore;

/**
 * Callback to notify the client, that a storage operation is commleted.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2013, Stadtwerke München GmbH
 */
public interface IStorageOperationCompleted {

	/**
	 * Will be called after the  operation is completed.
	 * Before a operation is completed the storage state is undefined (data is may patially commited).
	 */
	void isCompleted();
}
