package de.swm.gwt.client.mobile.keystore;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.StorageEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Abstract implementation for local storage.
 *
 * @author karsunke.franziskus
 * @author wiese.daniel
 *         <br> copyright (C) 2012, SWM Services GmbH
 * @see <code>http://www.w3.org/TR/webstorage/</code>
 */
public interface IStorage {

	/**
	 * Startet auf der unterliegenden Storage-Implementierung eine Transaktion.
	 * Geht von einemn Single-Thread Client-Model aus.
	 *
	 * @return das commit / rollback Objekt
	 */
	ITransaction beginTransaction();

	/**
	 * Hanlder for uncought exceptions.
	 *
	 * @param handler
	 */
	public void addUncaughtExceptionHanlder(GWT.UncaughtExceptionHandler handler);

	/**
	 * Will initialize the local storage implementation.
	 *
	 * @param afterInitHanlder
	 */
	public void initialize(AsyncCallback<Void> afterInitHanlder);

	/**
	 * Registriert einen Handler für die StorageEvents.
	 *
	 * @param handler .
	 * @return .
	 */
	public HandlerRegistration addStorageEventHandler(StorageEvent.Handler handler);

	/**
	 * Liefert den local Storage zurück.
	 *
	 * @return .
	 */
	public IStorage getLocalStorageIfSupported();

	/**
	 * Prüft ob local storage vorhanden ist.
	 *
	 * @return true - wenn ja,false - wenn nein
	 */
	public boolean isLocalStorageSupported();

	/**
	 * Löscht einen bestimmten Handler, der auf StorageEvents horcht.
	 *
	 * @param handler .
	 */
	public void removeStorageEventHandler(StorageEvent.Handler handler);

	/**
	 * Löscht den Inhalt der Storage.
	 */
	public void clear();

	/**
	 * Holt die Daten für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 * @return .
	 */
	public String getItem(String key);

	/**
	 * Gibt die Anzahl der gespeicherten Elemente zurück.
	 *
	 * @return .
	 */
	public int getLength();

	/**
	 * Liefert den Schlüssel an einer bestimmten Position zurück.
	 *
	 * @param index .
	 * @return .
	 */
	public String key(int index);

	/**
	 * Löscht den Wert für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 */
	public void removeItem(String key);

	/**
	 * Speichert einen String für einen bestimmten Schlüssel (überschreibt falls vorhanden).
	 *
	 * @param key  .
	 * @param data .
	 */
	public void setItem(String key, String data);

	/**
	 * Will delete the underlying storage asynchronously.
	 * @param callback callback when completed
	 */
	void clearAsync(IStorageOperationCompleted callback);

	/**
	 *
	 * @param keys the keys to delete.
	 * @param callback callback when completed
	 */
	void removeItemsAsync(Set<String> keys, IStorageOperationCompleted callback);

	/**
	 *
	 * @param values the values to store.
	 * @param callback callback when completed
	 */
	void setItemsAsync(Map<String, String> values, IStorageOperationCompleted callback);

	/**
	 * Returns all keys of the local storage.
	 *
	 * @return the list of keys.
	 */
	public Set<String> getKeys();
}
