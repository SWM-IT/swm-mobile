package de.swm.gwt.client.testhelper;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.StorageEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.swm.gwt.client.mobile.keystore.IStorageOperationCompleted;
import de.swm.gwt.client.mobile.keystore.IStorage;
import de.swm.gwt.client.mobile.keystore.ITransaction;
import org.junit.Ignore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Testimplementierung das Local Storage.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
@Ignore
public class SwmTestStorage implements IStorage {


	private Map<String, String> storage = new HashMap<String, String>();


	@Override
	public ITransaction beginTransaction() {
		throw new IllegalArgumentException("Usupported Operation Use: TransactionStorage");
	}

	@Override
	public void addUncaughtExceptionHanlder(GWT.UncaughtExceptionHandler uncaughtExceptionHandler) {
	}

	@Override
	public void initialize(final AsyncCallback<Void> voidAsyncCallback) {
		voidAsyncCallback.onSuccess(null);
	}

	/**
	 * Registriert einen Handler für die StorageEvents.
	 *
	 * @param handler .
	 * @return .
	 */
	@Override
	public HandlerRegistration addStorageEventHandler(StorageEvent.Handler handler) {
		return null;
	}

	/**
	 * Liefert den local Storage zurück.
	 *
	 * @return .
	 */
	@Override
	public IStorage getLocalStorageIfSupported() {
		return this;
	}

	/**
	 * Prüft ob local storage vorhanden ist.
	 *
	 * @return true - wenn ja,false - wenn nein
	 */
	@Override
	public boolean isLocalStorageSupported() {
		return true;
	}

	/**
	 * Löscht einen bestimmten Handler, der auf StorageEvents horcht.
	 *
	 * @param handler .
	 */
	@Override
	public void removeStorageEventHandler(StorageEvent.Handler handler) {
	}

	/**
	 * Löscht den Inhalt der Storage.
	 */
	@Override
	public void clear() {
		storage.clear();
	}

	/**
	 * Holt die Daten für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 * @return .
	 */
	@Override
	public String getItem(String key) {
		return storage.get(key);
	}

	/**
	 * Gibt die Anzahl der gespeicherten Elemente zurück.
	 *
	 * @return .
	 */
	@Override
	public int getLength() {
		return storage.keySet().size();
	}

	/**
	 * Liefert den Schlüssel an einer bestimmten Position zurück.
	 *
	 * @param index .
	 * @return .
	 */
	@Override
	public String key(int index) {
		throw new IllegalArgumentException("Not supported");
	}

	/**
	 * Löscht den Wert für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 */
	@Override
	public void removeItem(String key) {
		storage.remove(key);
	}

	/**
	 * Speichert einen String für einen bestimmten Schlüssel (überschreibt falls vorhanden).
	 *
	 * @param key  .
	 * @param data .
	 */
	@Override
	public void setItem(String key, String data) {
		storage.put(key, data);
	}

	/**
	 * Will delete the underlying storage asynchronously.
	 *
	 * @param callback callback when completed
	 */
	@Override
	public void clearAsync(IStorageOperationCompleted callback) {
		clear();
		callback.isCompleted();
	}

	/**
	 * @param keys     the keys to delete.
	 * @param callback callback when completed
	 */
	@Override
	public void removeItemsAsync(Set<String> keys, IStorageOperationCompleted callback) {
		for (String key : keys) {
			removeItem(key);
		}
		callback.isCompleted();
	}

	/**
	 * @param values   the values to store.
	 * @param callback callback when completed
	 */
	@Override
	public void setItemsAsync(Map<String, String> values, IStorageOperationCompleted callback) {
		for (String key : values.keySet()) {
			setItem(key, values.get(key));
		}
		callback.isCompleted();
	}

	@Override
	public Set<String> getKeys() {
		return storage.keySet();
	}
}
