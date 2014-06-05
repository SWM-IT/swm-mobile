package de.swm.gwt.client.mobile.keystore;

/**
 * Implementierung von einem zwei Phasen commit protokoll.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public interface ITransaction {

	/**
	 * Fuehrt ein Rollback aus (alle in einer Transaktion geschriebenen Daten werden geloescht)
	 */
	void rollback();

	/**
	 * Commits the transaction.
	 *
	 * @param callback callback when the transaction is comleted.
	 */
	void commit(IStorageOperationCompleted callback);
}
