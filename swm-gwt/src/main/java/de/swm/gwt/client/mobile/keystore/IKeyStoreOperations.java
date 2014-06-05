package de.swm.gwt.client.mobile.keystore;

import name.pehl.piriti.json.client.JsonReader;
import name.pehl.piriti.json.client.JsonWriter;

import java.util.List;

/**
 * Encapsulates bulk operations storing objects in a HTML5 key value storage.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public interface IKeyStoreOperations {
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
	<T> List<T> getDTOList(String userID, String useCase, Class<T> forClass, JsonReader<T> jsonReader);

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
	<T> List<T> getDTOListAndDeleteIt(String userID, String useCase, Class<T> forClass, JsonReader<T> jsonReader);

	/**
	 * Liest eine Liste von Objekten oder DTO's aus dem Local storage.
	 *
	 * @param <T>           .
	 * @param keyCollection .
	 * @param jsonReader    .
	 * @return .
	 */
	<T> List<T> getDTOList(KeyCollection keyCollection, JsonReader<T> jsonReader);

	/**
	 * Removes objects implicitly stored inside the key collection from the local storage.
	 *
	 * @param keyCollection incuding the objects to remove
	 */
	void removeDTOList(KeyCollection keyCollection);

	/**
	 * Liest eine Liste von Objekten oder DTO's aus dem Local storage.
	 *
	 * @param <T>           .
	 * @param keyCollection .
	 * @param jsonReader    .
	 * @return .
	 */
	<T> List<T> getDTOListAndDeleteIt(KeyCollection keyCollection, JsonReader<T> jsonReader);

	/**
	 * Aktualisert ein einzelnes DTO im local storage.
	 *
	 * @param <T>        .
	 * @param toUpdate   .
	 * @param keySet     .
	 * @param oid        .
	 * @param jsonWriter .
	 */
	<T> void updateDTO(T toUpdate, KeySet keySet, String oid, JsonWriter<T> jsonWriter);

	/**
	 * Loescht ein einzelnes DTO im local storage.
	 *
	 * @param <T>         .
	 * @param oidToDelete .
	 * @param keySet      .
	 * @param jsonWriter  .
	 */
	<T> void deleteDTO(KeySet keySet, String oidToDelete, JsonWriter<T> jsonWriter);

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
	<T> void updateOrCreateDTOList(List<T> toUpdate, KeySet keySet, JsonWriter<T> jsonWriter, List<Long> oids);

	/**
	 * Laedt einen keyset aus dem Local Storage oder erzeugt einen neuen leeren Keyset.
	 *
	 * @param forClass der typ der zu speichernden Objekte
	 * @param uuid     die uuid
	 * @param useCase  der use case
	 * @return der keyset.
	 */
	KeySet loadKeysetFormStoreOrCreateEmptyOne(Class<?> forClass, String uuid, String useCase);

	/**
	 * Schreibt eine liste von beliebigen Objekten in den local storage. Es muss sichegestellt werden, dass eine
	 * eventuell vorhandene List geloescht worden ist.
	 *
	 * @param <T>        .
	 * @param toUpdate   .
	 * @param keySet     .
	 * @param jsonWriter .
	 */
	<T> void createList(List<T> toUpdate, KeySet keySet, JsonWriter<T> jsonWriter);
}
