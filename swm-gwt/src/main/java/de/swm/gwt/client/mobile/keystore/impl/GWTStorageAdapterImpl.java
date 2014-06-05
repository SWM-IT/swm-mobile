package de.swm.gwt.client.mobile.keystore.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.storage.client.StorageEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.swm.gwt.client.mobile.keystore.IStorageOperationCompleted;
import de.swm.gwt.client.mobile.keystore.IStorage;
import de.swm.gwt.client.mobile.keystore.ITransaction;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implements the GWT HTML5 Storage implementation.
 *
 * @author karsunke.franziskus
 *         <br> copyright (C) 2011, SWM Services GmbH
 */
public class GWTStorageAdapterImpl implements IStorage {


	private final Storage delegate;
	private GWT.UncaughtExceptionHandler uncaughtExceptionHanlder;

	/**
	 * Standardkonstruktor.
	 */
	public GWTStorageAdapterImpl() {
		this.delegate = Storage.getLocalStorageIfSupported();
	}


	@Override
	public ITransaction beginTransaction() {
		throw new IllegalArgumentException("Usupported Operation Use: TransactionStorage");
	}

	/**
	 * Hanlder for uncought exceptions.
	 *
	 * @param handler
	 */
	@Override
	public void addUncaughtExceptionHanlder(GWT.UncaughtExceptionHandler handler) {
		uncaughtExceptionHanlder = handler;
	}

	/**
	 * Will initialize the local storage implementation.
	 *
	 * @param afterInitHanlder
	 */
	@Override
	public void initialize(final AsyncCallback<Void> afterInitHanlder) {
		if (Storage.getLocalStorageIfSupported() != null) {
			afterInitHanlder.onSuccess(null);
		} else {
			afterInitHanlder.onFailure(new IllegalArgumentException("Local Storage not supported"));
		}
	}

	/**
	 * Registers an event handler for StorageEvents.
	 *
	 * @param handler .
	 * @return {@link com.google.gwt.event.shared.HandlerRegistration} used to remove this handler
	 * @see <a href="http://www.w3.org/TR/webstorage/#the-storage-event">W3C Web
	 *      Storage - the storage event</a>
	 */
	@Override
	public HandlerRegistration addStorageEventHandler(StorageEvent.Handler handler) {
		return Storage.addStorageEventHandler(handler);
	}

	/**
	 * Removes all items in the Storage.
	 *
	 * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-clear">W3C Web
	 *      Storage - Storage.clear()</a>
	 */
	@Override
	public void clear() {
		delegate.clear();
	}

	/**
	 * Returns the item in the Storage associated with the specified key.
	 *
	 * @param key the key to a value in the Storage
	 * @return the value associated with the given key
	 * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-getitem">W3C Web
	 *      Storage - Storage.getItem(k)</a>
	 */
	@Override
	public String getItem(String key) {
		return delegate.getItem(key);
	}

	/**
	 * Returns the number of items in this Storage.
	 *
	 * @return number of items in this Storage
	 * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-l">W3C Web
	 *      Storage - Storage.length()</a>
	 */
	@Override
	public int getLength() {
		return delegate.getLength();
	}

	/**
	 * Returns a Local Storage.
	 * <p/>
	 * <p>
	 * The returned storage is associated with the <a
	 * href="http://www.w3.org/TR/html5/browsers.html#origin">origin</a> of the
	 * Document.
	 * </p>
	 *
	 * @return the localStorage instance, or <code>null</code> if Web Storage is
	 *         NOT supported.
	 * @see <a href="http://www.w3.org/TR/webstorage/#dom-localstorage">W3C Web
	 *      Storage - localStorage</a>
	 */
	@Override
	public IStorage getLocalStorageIfSupported() {
		if (delegate != null) {
			return this;
		} else {
			return null;
		}
	}

	/**
	 * Returns <code>true</code> if the <code>localStorage</code> part of the
	 * Storage API is supported on the running platform.
	 *
	 * @return .
	 */
	@Override
	public boolean isLocalStorageSupported() {
		return Storage.isLocalStorageSupported();
	}

	/**
	 * Returns the key at the specified index.
	 *
	 * @param index the index of the key
	 * @return the key at the specified index in this Storage
	 * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-key">W3C Web
	 *      Storage - Storage.key(n)</a>
	 */
	@Override
	public String key(int index) {
		return delegate.key(index);
	}

	/**
	 * Removes the item in the Storage associated with the specified key.
	 *
	 * @param key the key to a value in the Storage
	 * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-removeitem">W3C
	 *      Web Storage - Storage.removeItem(k)</a>
	 */
	@Override
	public void removeItem(String key) {
		delegate.removeItem(key);
	}

	/**
	 * De-registers an event handler for StorageEvents.
	 *
	 * @param handler .
	 * @see <a href="http://www.w3.org/TR/webstorage/#the-storage-event">W3C Web
	 *      Storage - the storage event</a>
	 */
	@Override
	public void removeStorageEventHandler(StorageEvent.Handler handler) {
		Storage.removeStorageEventHandler(handler);
	}

	/**
	 * Sets the value in the Storage associated with the specified key to the
	 * specified data.
	 * <p/>
	 * Note: The empty string may not be used as a key.
	 *
	 * @param key  the key to a value in the Storage
	 * @param data the value associated with the key
	 * @see <a href="http://www.w3.org/TR/webstorage/#dom-storage-setitem">W3C Web
	 *      Storage - Storage.setItem(k,v)</a>
	 */
	@Override
	public void setItem(String key, String data) {
		delegate.setItem(key, data);
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
		Set<String> keys = new HashSet<String>();
		for (int i = 0; i < delegate.getLength(); i++) {
			keys.add(delegate.key(i));
		}
		return keys;
	}


}
