package de.swm.gwt.storage;

import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.ListCallback;
import com.google.code.gwt.database.client.service.VoidCallback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.StorageEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.swm.gwt.client.mobile.keystore.IStorage;
import de.swm.gwt.client.mobile.keystore.IStorageOperationCompleted;
import de.swm.gwt.client.mobile.keystore.ITransaction;

import java.util.*;
import java.util.logging.Logger;

/**
 * This class implements all operation specified in http://www.w3.org/TR/webstorage/.  Under the hood
 * webdatabase is used which allows store bigger amounts of data.
 * In IOs 6 applications are allowed to use up to 70 Mb limit. Because of the asynchronous nature of
 * http://www.w3.org/TR/webdatabase/ all date are loaded into memory and changes are persisted
 * asynchronously.
 *
 * @author wiese.daniel
 *         <br> copyright (C) 2012, SWM Services GmbH
 * @see <code>http://www.w3.org/TR/webstorage/</code>
 * but avoiding the <b>5 mb</b> limit.
 */
public class BigLocalStorage implements IStorage {

	private static final Logger LOGGER = Logger.getLogger(BigLocalStorage.class.getName());


	public static final String NOT_SUPPORTED = "Not supported";
	private final SQLStorageLayer sqlLayer = GWT.create(SQLStorageLayer.class);
	private Map<String, String> inMemoryStorage = new HashMap<String, String>();

	private boolean isSupported = false;
	private GWT.UncaughtExceptionHandler uncaughtExceptionHandler;

	public BigLocalStorage() {

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
		this.uncaughtExceptionHandler = handler;
	}

	/**
	 * Will initialize the local storage implementation.
	 *
	 * @param afterInitHanlder
	 */
	@Override
	public void initialize(final AsyncCallback<Void> afterInitHanlder) {
		LOGGER.info("initializing SQL-Lite-Datatbase ...");
		sqlLayer.initTable(new VoidCallback() {
			@Override
			public void onSuccess() {
				sqlLayer.getAllValues(new ListCallback<GenericRow>() {
					@Override
					public void onSuccess(List<GenericRow> result) {
						for (GenericRow genericRow : result) {
							inMemoryStorage.put(genericRow.getString("id"), genericRow.getString("value"));
						}
						LOGGER.info("SQL-Lite-Datatbase initialized");
						afterInitHanlder.onSuccess(null);
					}

					@Override
					public void onFailure(DataServiceException error) {
						LOGGER.info("Can't read storage");
						afterInitHanlder.onFailure(error);
					}
				});
				isSupported = true;
			}

			@Override
			public void onFailure(DataServiceException e) {
				LOGGER.info("SQL Lite datatbase not supported");
				afterInitHanlder.onFailure(e);
			}
		});
	}

	/**
	 * Registriert einen Handler für die StorageEvents.
	 *
	 * @param handler .
	 * @return .
	 */
	@Override
	public HandlerRegistration addStorageEventHandler(StorageEvent.Handler handler) {
		throw new IllegalArgumentException("Not supported");
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
		return isSupported;
	}

	/**
	 * Löscht einen bestimmten Handler, der auf StorageEvents horcht.
	 *
	 * @param handler .
	 */
	@Override
	public void removeStorageEventHandler(StorageEvent.Handler handler) {
		throw new IllegalArgumentException(NOT_SUPPORTED);
	}

	/**
	 * Löscht den Inhalt der Storage.
	 */
	@Override
	public void clear() {
		inMemoryStorage.clear();
		this.sqlLayer.deleteAll(new VoidCallback() {
			@Override
			public void onSuccess() {
				LOGGER.info("All rows deleted");
			}

			@Override
			public void onFailure(DataServiceException e) {
				LOGGER.info("Can't delete rowns");
				if (uncaughtExceptionHandler != null) {
					uncaughtExceptionHandler.onUncaughtException(e);
				}
			}
		});
	}

	/**
	 * Holt die Daten für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 * @return .
	 */
	@Override
	public String getItem(String key) {
		//will be always returnd from memory
		return inMemoryStorage.get(key);
	}

	/**
	 * Gibt die Anzahl der gespeicherten Elemente zurück.
	 *
	 * @return .
	 */
	@Override
	public int getLength() {
		//will be always returnd from memory
		return inMemoryStorage.size();
	}

	/**
	 * Liefert den Schlüssel an einer bestimmten Position zurück.
	 *
	 * @param index index position
	 * @return .
	 */
	@Override
	public String key(int index) {
		throw new IllegalArgumentException(NOT_SUPPORTED);
	}

	/**
	 * Löscht den Wert für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 */
	@Override
	public void removeItem(String key) {
		final VoidCallback callback = new VoidCallback() {

			@Override
			public void onSuccess() {
				//nothing to do
			}

			@Override
			public void onFailure(DataServiceException e) {
				if (uncaughtExceptionHandler != null) {
					uncaughtExceptionHandler.onUncaughtException(e);
				}
			}

		};
		inMemoryStorage.remove(key);
		this.sqlLayer.deleteKeyAndValue(key, callback);


	}

	/**
	 * Löscht den Wert für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 */
	private void removeItemInternal(String key, VoidCallback callback) {
		inMemoryStorage.remove(key);
		this.sqlLayer.deleteKeyAndValue(key, callback);

	}


	/**
	 * Speichert einen String für einen bestimmten Schlüssel (überschreibt falls vorhanden).
	 *
	 * @param key  the key
	 * @param data the value
	 */
	@Override
	public void setItem(final String key, final String data) {
		final VoidCallback callback = new VoidCallback() {

			@Override
			public void onSuccess() {
				//nothing to do
			}

			@Override
			public void onFailure(DataServiceException e) {
				if (uncaughtExceptionHandler != null) {
					uncaughtExceptionHandler.onUncaughtException(e);
				}
			}
		};
		setItemInternal(key, data, callback);
	}

	public void setItemInternal(final String key, final String data, final VoidCallback callback) {
		if (inMemoryStorage.containsKey(key)) {
			inMemoryStorage.put(key, data);
			//update value
			sqlLayer.updateValue(key, data, callback);
		} else {
			inMemoryStorage.put(key, data);
			//insert value
			sqlLayer.insertValue(key, data, callback);
		}
	}

	/**
	 * Will delete the underlying storage asynchronously.
	 *
	 * @param callback callback when completed
	 */
	@Override
	public void clearAsync(final IStorageOperationCompleted callback) {
		inMemoryStorage.clear();
		this.sqlLayer.deleteAll(new VoidCallback() {
			@Override
			public void onSuccess() {
				LOGGER.info("All rows deleted");
				callback.isCompleted();
			}

			@Override
			public void onFailure(DataServiceException e) {
				LOGGER.info("Can't delete rows");
				if (uncaughtExceptionHandler != null) {
					uncaughtExceptionHandler.onUncaughtException(e);
				} else {
					callback.isCompleted();
				}
			}
		});
	}

	/**
	 * @param keys     the keys to delete.
	 * @param callback callback when completed
	 */
	@Override
	public void removeItemsAsync(Set<String> keys, final IStorageOperationCompleted callback) {
		final Queue<String> keysToInsert = new LinkedList<String>(keys);
		final VoidCallback afterOneRowIsDeletedCallback = new VoidCallback() {

			@Override
			public void onSuccess() {
				final String nextKey = keysToInsert.poll();
				if (nextKey != null) {
					removeItemInternal(nextKey, this);
				} else {
					callback.isCompleted();
				}
			}

			@Override
			public void onFailure(DataServiceException e) {
				if (uncaughtExceptionHandler != null) {
					uncaughtExceptionHandler.onUncaughtException(e);
				} else {
					final String nextKey = keysToInsert.poll();
					if (nextKey != null) {
						removeItemInternal(nextKey, this);
					} else {
						callback.isCompleted();
					}
				}
			}
		};

		//Chain starten
		final String nextKey = keysToInsert.poll();
		if (nextKey != null) {
			removeItemInternal(nextKey, afterOneRowIsDeletedCallback);
		} else {
			callback.isCompleted();
		}
	}

	/**
	 * @param values   the values to store.
	 * @param callback callback when completed
	 */
	@Override
	public void setItemsAsync(final Map<String, String> values, final IStorageOperationCompleted callback) {
		final Queue<String> keysToInsert = new LinkedList<String>(values.keySet());
		final VoidCallback afterOneRowIsInsertedCallback = new VoidCallback() {

			@Override
			public void onSuccess() {
				final String nextKey = keysToInsert.poll();
				if (nextKey != null) {
					setItemInternal(nextKey, values.get(nextKey), this);
				} else {
					callback.isCompleted();
				}
			}

			@Override
			public void onFailure(DataServiceException e) {
				if (uncaughtExceptionHandler != null) {
					uncaughtExceptionHandler.onUncaughtException(e);
				} else {
					final String nextKey = keysToInsert.poll();
					if (nextKey != null) {
						setItemInternal(nextKey, values.get(nextKey), this);
					} else {
						callback.isCompleted();
					}
				}
			}
		};

		//Chain starten
		final String nextKey = keysToInsert.poll();
		if (nextKey != null) {
			setItemInternal(nextKey, values.get(nextKey), afterOneRowIsInsertedCallback);
		} else {
			callback.isCompleted();
		}
	}

	@Override
	public Set<String> getKeys() {
		return this.inMemoryStorage.keySet();
	}

}
