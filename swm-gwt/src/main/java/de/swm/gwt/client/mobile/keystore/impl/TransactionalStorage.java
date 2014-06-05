package de.swm.gwt.client.mobile.keystore.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.StorageEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.swm.gwt.client.mobile.keystore.IStorage;
import de.swm.gwt.client.mobile.keystore.IStorageOperationCompleted;
import de.swm.gwt.client.mobile.keystore.ITransaction;

import java.util.*;

/**
 * Wrapper um eine <code>IStroage</code> implementirung transkational zu behandeln.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public class TransactionalStorage implements IStorage {

	/**
	 * contains a set of new keys (entries). *
	 */
	private Map<String, String> storage = new HashMap<String, String>();
	/**
	 * contains a set of all removed keys (entries). *
	 */
	private Map<String, String> removedItems = new HashMap<String, String>();
	/**
	 * contains a set of all changed keys (entries). *
	 */
	private Set<String> changedKeys = new HashSet<String>();
	public boolean clearWasCalled = false;
	private final IStorage wrappedStorage;
	private boolean isInTransaction = false;

	/**
	 * Default Konstructor.
	 */
	public TransactionalStorage(IStorage storage) {
		wrappedStorage = storage;
	}


	@Override
	public void clear() {
		if (isInTransaction) {
			clearWasCalled = true;
			storage.clear();
			changedKeys.clear();
		} else {
			wrappedStorage.clear();
		}
	}

	/**
	 * Holt die Daten für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 * @return .
	 */
	@Override
	public String getItem(String key) {
		if (isInTransaction) {
			if (storage.containsKey(key)) {
				return storage.get(key);
			} else {
				if (!clearWasCalled) {
					if (!removedItems.containsKey(key)) {
						return wrappedStorage.getItem(key);
					}
				}
			}

			return null;
		} else {
			return wrappedStorage.getItem(key);
		}
	}

	/**
	 * Gibt die Anzahl der gespeicherten Elemente zurück.
	 *
	 * @return .
	 */
	@Override
	public int getLength() {
		if (isInTransaction) {
			int length = 0;
			if (!clearWasCalled) {
				length += wrappedStorage.getLength();
				length -= changedKeys.size();
			}
			return length += storage.size();
		} else {
			return wrappedStorage.getLength();
		}
	}

	/**
	 * Löscht den Wert für einen bestimmten Schlüssel.
	 *
	 * @param key .
	 */
	@Override
	public void removeItem(String key) {
		if (isInTransaction) {
			final String itemToRemove = wrappedStorage.getItem(key);
			if (itemToRemove != null) {
				removedItems.put(key, itemToRemove);
			}
		} else {
			wrappedStorage.removeItem(key);
		}
	}

	/**
	 * Speichert einen String für einen bestimmten Schlüssel (überschreibt falls vorhanden).
	 *
	 * @param key  .
	 * @param data .
	 */
	@Override
	public void setItem(String key, String data) {
		if (isInTransaction) {
			storage.put(key, data);
			if (wrappedStorage.getItem(key) != null) {
				changedKeys.add(key);

			}
		} else {
			wrappedStorage.setItem(key, data);
		}
	}

	@Override
	public ITransaction beginTransaction() {
		isInTransaction = true;
		if (wrappedStorage != null && wrappedStorage.isLocalStorageSupported()) {
			return new ITransaction() {
				@Override
				public void rollback() {
					storage.clear();
					removedItems.clear();
					clearWasCalled = false;
					isInTransaction = false;
				}

				@Override
				public void commit(IStorageOperationCompleted callback) {
					final CommitChain chain = new CommitChain(callback);
					chain.addPart(new IChainPart() {
						@Override
						public void startPart(IStorageOperationCompleted callback) {
							if (clearWasCalled) {
								wrappedStorage.clearAsync(callback);
							} else {
								callback.isCompleted();
							}
						}
					});
					chain.addPart(new IChainPart() {
						@Override
						public void startPart(IStorageOperationCompleted callback) {
							wrappedStorage.removeItemsAsync(removedItems.keySet(), callback);
						}
					});
					chain.addPart(new IChainPart() {
						@Override
						public void startPart(IStorageOperationCompleted callback) {
							wrappedStorage.setItemsAsync(storage, callback);
						}
					});
					chain.addPart(new IChainPart() {
						@Override
						public void startPart(IStorageOperationCompleted callback) {
							storage.clear();
							removedItems.clear();
							clearWasCalled = false;
							isInTransaction = false;
							callback.isCompleted();
						}
					});

					chain.executeChain();
				}
			};
		} else {
			throw new IllegalArgumentException("Transactions are not supported by the underlying storage");
		}
	}

	/**
	 * Hanlder for uncought exceptions.
	 *
	 * @param handler
	 */
	@Override
	public void addUncaughtExceptionHanlder(GWT.UncaughtExceptionHandler handler) {
		wrappedStorage.addUncaughtExceptionHanlder(handler);
	}

	/**
	 * Will initialize the local storage implementation.
	 *
	 * @param afterInitHanlder
	 */
	@Override
	public void initialize(AsyncCallback<Void> afterInitHanlder) {
		wrappedStorage.initialize(afterInitHanlder);
	}

	/**
	 * Registriert einen Handler für die StorageEvents.
	 *
	 * @param handler .
	 * @return .
	 */
	@Override
	public HandlerRegistration addStorageEventHandler(StorageEvent.Handler handler) {
		return wrappedStorage.addStorageEventHandler(handler);
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
		return wrappedStorage.isLocalStorageSupported();
	}

	/**
	 * Löscht einen bestimmten Handler, der auf StorageEvents horcht.
	 *
	 * @param handler .
	 */
	@Override
	public void removeStorageEventHandler(StorageEvent.Handler handler) {
		wrappedStorage.removeStorageEventHandler(handler);
	}

	/**
	 * Returns a key corresponding to the index position.
	 *
	 * @param index .
	 * @return .
	 */
	@Override
	public String key(int index) {
		throw new IllegalArgumentException("Unsupported Operation");
	}

	/**
	 * Will delete the underlying storage asynchronously.
	 *
	 * @param callback callback when completed
	 */
	@Override
	public void clearAsync(IStorageOperationCompleted callback) {
		throw new IllegalArgumentException("Unsupported Operation");
	}

	/**
	 * @param keys     the keys to delete.
	 * @param callback callback when completed
	 */
	@Override
	public void removeItemsAsync(Set<String> keys, IStorageOperationCompleted callback) {
		throw new IllegalArgumentException("Unsupported Operation");
	}

	/**
	 * @param values   the values to store.
	 * @param callback callback when completed
	 */
	@Override
	public void setItemsAsync(Map<String, String> values, IStorageOperationCompleted callback) {
		throw new IllegalArgumentException("Unsupported Operation");
	}

	@Override
	public Set<String> getKeys() {
		if (isInTransaction) {
			throw new IllegalArgumentException("Keyset not deterministic whilst in transaction.");
		} else {
			return wrappedStorage.getKeys();
		}
	}

	/**
	 * This class is responsible for:
	 * Chaning callbacks - each chained callback is execuded afer the previous callback will complete.
	 */
	public static class CommitChain {

		private final IStorageOperationCompleted clientNotification;
		private final Queue<IChainPart> parts = new LinkedList<IChainPart>();

		public CommitChain(IStorageOperationCompleted clientNotification) {
			this.clientNotification = clientNotification;
		}

		public void addPart(IChainPart part) {
			parts.add(part);
		}

		public void executeChain() {
			IChainPart part = parts.poll();
			if (part != null) {
				final IStorageOperationCompleted callback = new IStorageOperationCompleted() {
					@Override
					public void isCompleted() {
						executeChain();
					}
				};
				part.startPart(callback);
			} else {
				clientNotification.isCompleted();
			}

		}

	}

	/**
	 * Dafined a part of a cllabck chain.
	 */
	public static interface IChainPart {
		/**
		 * Will start a part.
		 * @param callback the complete callback.
		 */
		void startPart(IStorageOperationCompleted callback);
	}


}
