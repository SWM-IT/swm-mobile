package de.swm.gwt.client.mobile.keystore.impl;

import de.swm.gwt.client.mobile.keystore.IKeyStoreOperations;
import de.swm.gwt.client.mobile.keystore.IStorage;
import de.swm.gwt.client.mobile.keystore.KeyCollection;
import de.swm.gwt.client.mobile.keystore.KeySet;
import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import java.util.ArrayList;
import java.util.List;


/**
 * Encapsulates bulk operations storing objects in a HTML5 key value storage.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2012, SWM Services GmbH
 */
public class KeyStoreOperations implements IKeyStoreOperations {

	private final IStorage localStorage;
	private final JsonReader<KeySet> keyset_reader;
	private final JsonWriter<KeySet> keyset_writer;


	/**
	 * Default constructor.
	 *
	 * @param localStorage  the storage instance
	 * @param keyset_reader the reader for a keyset
	 * @param keyset_writer the reader for a keylist
	 */
	public KeyStoreOperations(IStorage localStorage, JsonReader<KeySet> keyset_reader,
							  JsonWriter<KeySet> keyset_writer) {
		this.localStorage = localStorage;
		this.keyset_reader = keyset_reader;
		this.keyset_writer = keyset_writer;

	}


	/**
	 * Reads a list of typed objects for a local storage.
	 *
	 * @param <T>        th objects class type
	 * @param userID     the user id (only objects stored for this user id will be retrieved)
	 * @param useCase    the use case id (only objects stored for this use case will be retrieved)
	 * @param forClass   the java type of the object to retrieve
	 * @param jsonReader the jeson reader for this object class type
	 * @return list of objects or empty list
	 */
	@Override
	public <T> List<T> getDTOList(String userID, String useCase, Class<T> forClass, JsonReader<T> jsonReader) {
		final String keysetAsJson = localStorage
				.getItem(new KeySet(forClass, userID, useCase).createKeyCollectionKey());
		if (keysetAsJson != null) {
			final KeySet keySet = keyset_reader.read(keysetAsJson);
			return getDTOList(keySet, jsonReader);
		} else {
			return new ArrayList<T>();
		}
	}


	/**
	 * Reads a list of typed objects for a local storage and deletes all objects after the retireval.
	 *
	 * @param <T>        th objects class type
	 * @param userID     the user id (only objects stored for this user id will be retrieved)
	 * @param useCase    the use case id (only objects stored for this use case will be retrieved)
	 * @param forClass   the java type of the object to retrieve
	 * @param jsonReader the jeson reader for this object class type
	 * @return list of objects or empty list
	 */
	@Override
	public <T> List<T> getDTOListAndDeleteIt(String userID, String useCase, Class<T> forClass, JsonReader<T> jsonReader) {
		String collectionKey = new KeySet(forClass, userID, useCase).createKeyCollectionKey();
		final String keysetAsJson = localStorage.getItem(collectionKey);
		localStorage.removeItem(collectionKey);
		if (keysetAsJson != null) {
			final KeySet keySet = keyset_reader.read(keysetAsJson);
			return getDTOListAndDeleteIt(keySet, jsonReader);
		} else {
			return new ArrayList<T>();
		}
	}


	/**
	 * Liest eine Liste von Objekten oder DTO's aus dem Local storage.
	 *
	 * @param <T>           .
	 * @param keyCollection .
	 * @param jsonReader    .
	 * @return .
	 */
	@Override
	public <T> List<T> getDTOList(KeyCollection keyCollection, JsonReader<T> jsonReader) {
		final List<T> result = new ArrayList<T>();
		for (String keyToRead : keyCollection.getKeys()) {
			result.add(jsonReader.read(localStorage.getItem(keyToRead)));
		}
		return result;
	}


	/**
	 * Removes objects implicitly stored inside the key collection from the local storage.
	 *
	 * @param keyCollection incuding the objects to remove
	 */
	@Override
	public void removeDTOList(KeyCollection keyCollection) {
		for (String keyToRemove : keyCollection.getKeys()) {
			localStorage.removeItem(keyToRemove);
		}
		localStorage.removeItem(keyCollection.createKeyCollectionKey());
	}


	/**
	 * Liest eine Liste von Objekten oder DTO's aus dem Local storage.
	 *
	 * @param <T>           .
	 * @param keyCollection .
	 * @param jsonReader    .
	 * @return .
	 */
	@Override
	public <T> List<T> getDTOListAndDeleteIt(KeyCollection keyCollection, JsonReader<T> jsonReader) {
		final List<T> result = new ArrayList<T>();
		for (String keyToRead : keyCollection.getKeys()) {
			result.add(jsonReader.read(localStorage.getItem(keyToRead)));
			localStorage.removeItem(keyToRead);
		}
		localStorage.removeItem(keyCollection.createKeyCollectionKey());
		return result;
	}


	/**
	 * Aktualisert ein einzelnes DTO im local storage.
	 *
	 * @param <T>        .
	 * @param toUpdate   .
	 * @param keySet     .
	 * @param oid        .
	 * @param jsonWriter .
	 */
	@Override
	public <T> void updateDTO(T toUpdate, KeySet keySet, String oid, JsonWriter<T> jsonWriter) {
		final String key = keySet.createKey(oid);
		localStorage.setItem(key, jsonWriter.toJson(toUpdate));
		localStorage.setItem(keySet.createKeyCollectionKey(), keyset_writer.toJson(keySet));
	}


	/**
	 * Loescht ein einzelnes DTO im local storage.
	 *
	 * @param <T>         .
	 * @param oidToDelete .
	 * @param keySet      .
	 * @param jsonWriter  .
	 */
	@Override
	public <T> void deleteDTO(KeySet keySet, String oidToDelete, JsonWriter<T> jsonWriter) {
		final String key = keySet.createKeyForRemoval(oidToDelete);
		localStorage.removeItem(key);
		localStorage.setItem(keySet.createKeyCollectionKey(), keyset_writer.toJson(keySet));
	}


	/**
	 * Schreibt eine liste von DTO's in den local storage oder fuehrt ein Update durch falls das objekt bereits
	 * vorhanden ist.
	 *
	 * @param <T>        .
	 * @param toUpdate   .
	 * @param keySet     .
	 * @param jsonWriter .
	 * @param oids       die lister der internen id's
	 */
	@Override
	public <T> void updateOrCreateDTOList(List<T> toUpdate, KeySet keySet, JsonWriter<T> jsonWriter, List<Long> oids) {
		int i = 0;
		for (T type : toUpdate) {
			final String key = keySet.createKey(String.valueOf(oids.get(i)));
			localStorage.setItem(key, jsonWriter.toJson(type));
			i++;
		}
		localStorage.setItem(keySet.createKeyCollectionKey(), keyset_writer.toJson(keySet));
	}


	/**
	 * Laedt einen keyset aus dem Local Storage oder erzeugt einen neuen leeren Keyset.
	 *
	 * @param forClass der typ der zu speichernden Objekte
	 * @param uuid     die uuid
	 * @param useCase  der use case
	 * @return der keyset.
	 */
	@Override
	public KeySet loadKeysetFormStoreOrCreateEmptyOne(Class<?> forClass, String uuid, String useCase) {
		KeySet emptyKeySet = new KeySet(forClass, uuid, useCase);
		String keysetFormStoreAsJsonString = localStorage.getItem(emptyKeySet.createKeyCollectionKey());
		if (keysetFormStoreAsJsonString != null) {
			return keyset_reader.read(keysetFormStoreAsJsonString);
		}

		return emptyKeySet;
	}


	/**
	 * Schreibt eine liste von beliebigen Objekten in den local storage. Es muss sichegestellt werden, dass eine
	 * eventuell vorhandene List geloescht worden ist.
	 *
	 * @param <T>        .
	 * @param toUpdate   .
	 * @param keySet     .
	 * @param jsonWriter .
	 */
	@Override
	public <T> void createList(List<T> toUpdate, KeySet keySet, JsonWriter<T> jsonWriter) {
		int id = 0;
		for (T type : toUpdate) {
			id++;
			final String key = keySet.createKey(String.valueOf(id));
			localStorage.setItem(key, jsonWriter.toJson(type));
		}
		localStorage.setItem(keySet.createKeyCollectionKey(), keyset_writer.toJson(keySet));
	}

}
