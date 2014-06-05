package de.swm.gwt.client.mobile.keystore;

import java.io.Serializable;
import java.util.Collection;



/**
 * Abstrakte Klasse zur Verwaltung eindeutiger Schlüssel für
 * Objekte. Kann insbesondere als Index bei der Ablage der
 * Objekte im LocalStorage dienen.
 * 
 * Implementierungen stehen zur Verfügung für Mengen von
 * Schlüsseln ({@link KeySet}) und für Listen von Schlüsseln
 * ({@link KeyList}).
 * 
 * 
 * @author wiese.daniel, wimmel.guido <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public abstract class KeyCollection implements Serializable {

	private static final long serialVersionUID = 1L;
	private String clazzName;
	private String useCaseQualifier;
	private String userId;


	/**
	 * Constructor zur deserialisierung.
	 */
	public KeyCollection() {
	}

	/**
	 * Default constructor.
	 * 
	 * @param forClass
	 *            die Klasse, fuer die die Keys gehalten werden sollen.
	 * @param userId
	 *            der benutzer-id fuer den der Key generiert werden soll.
	 * @param useCaseQualifier
	 *            der Kontext (use-case) in welchem das Objekt vernedet wird. Erlaubt die Speicherung gleicher objekte
	 *            unter verschiedenen Use-Cases.
	 */
	public KeyCollection(Class<?> forClass, String userId, String useCaseQualifier) {
		this.userId = userId;
		this.useCaseQualifier = useCaseQualifier;
		this.clazzName = forClass.getName();
	}



	/**
	 * Returns the keys.
	 * 
	 * @return the keys
	 */
	public abstract Collection<String> getKeys();


	/**
	 * Returns the clazzName.
	 * 
	 * @return the clazzName
	 */
	public String getClazzName() {
		return clazzName;
	}



	/**
	 * clazzName the clazzName to set.
	 * 
	 * @param clazzName
	 *            the clazzName to set
	 */
	public void setClazzName(String clazzName) {
		this.clazzName = clazzName;
	}


	/**
	 * Returns the useCaseQualifier.
	 * 
	 * @return the useCaseQualifier
	 */
	public String getUseCaseQualifier() {
		return useCaseQualifier;
	}



	/**
	 * useCaseQualifier the useCaseQualifier to set.
	 * 
	 * @param useCaseQualifier
	 *            the useCaseQualifier to set
	 */
	public void setUseCaseQualifier(String useCaseQualifier) {
		this.useCaseQualifier = useCaseQualifier;
	}



	/**
	 * Returns the userId.
	 * 
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}



	/**
	 * userId the userId to set.
	 * 
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}



	/**
	 * Fuegt einen Key zur Struktur hinzu, ansonsten wird dieser ignoriert.
	 * 
	 * @param key
	 *            der Key
	 */
	public abstract void addKey(String key);


	/**
	 * Löscht einen Key aus der Struktur, falls vorhanden.
	 * 
	 * @param key
	 *            der Key
	 */
	public abstract void removeKey(String key);

	/**
	 * Erzeugt einen neuen eindeutigen Key fuer den jeweiligen benutzer und klasse und fuegt es zu der Menge der Keys
	 * hinzu.
	 * 
	 * @param objectKey
	 *            der interne Key des DTO's
	 * @return der erzeugte global eindeutige Key
	 */
	public String createKey(String objectKey) {
		final String key = createKeyCollectionKey() + "_" + objectKey;
		addKey(key);
		return key;
	}
	
	/**
	 * Erzeugt einen neuen eindeutigen Key fuer das objektwelches aus dem Storage entfernt werden soll.
	 * 
	 * @param objectKey
	 *            der interne Key des DTO's
	 * @return der erzeugte global eindeutige Key
	 */
	public String createKeyForRemoval(String objectKey) {
		final String key = createKeyCollectionKey() + "_" + objectKey;
		removeKey(key);
		return key;
	}

	/**
	 * Erzeugt einen neuen eindeutigen Key fuer ohne das Storage selbst zu manimulieren.
	 *
	 * @param objectKey
	 *            der interne Key des DTO's
	 * @return der erzeugte global eindeutige Key
	 */
	public String createKeyNoOp(String objectKey) {
		final String key = createKeyCollectionKey() + "_" + objectKey;
		return key;
	}



	/**
	 * Erzeugt einen neuen eindeutigen Key fuer den jeweiligen benutzer und diesen Keyset.
	 * 
	 * @return der erzeugte global eindeutige Key des keysets
	 */
	public String createKeyCollectionKey() {
		final String key = this.userId + "_" + clazzName + "_" + useCaseQualifier;
		return key;
	}

}
