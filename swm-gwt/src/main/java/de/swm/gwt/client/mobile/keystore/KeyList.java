package de.swm.gwt.client.mobile.keystore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



/**
 * Konkrete Implementierung von {@link KeyCollection} als Liste
 * von Schlüsseln (geordnet, mehrere Vorkommen des gleichen Schlüssels
 * möglich).
 * 
 * @author wimmel.guido <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class KeyList extends KeyCollection {

	private static final long serialVersionUID = 1L;
	private List<String> keyList = new ArrayList<String>();

	/**
	 * Constructor zur deserialisierung.
	 */
	public KeyList() {
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
	public KeyList(Class<?> forClass, String userId, String useCaseQualifier) {
		super(forClass, userId, useCaseQualifier);
	}
	
	/**
	 * Returns the keys.
	 * 
	 * @return the keys
	 */
	@Override
	public Collection<String> getKeys() {
		return getKeyList();
	}
	
	/**
	 * Returns the keys as a list.
	 * 
	 * @return the keys
	 */
	public List<String> getKeyList() {
		return keyList;
	}

	/**
	 * Sets the key list.
	 * 
	 * @param keys
	 *            the keys to set
	 */
	public void setKeyList(List<String> keys) {
		this.keyList = keys;
	}


	/**
	 * Fuegt einen Key zur Liste hinzu.
	 * 
	 * @param key
	 *            der Key
	 */
	@Override
	public void addKey(String key) {
		keyList.add(key);
	}
	
	/**
	 * Entfernt einen Key (falls vorhanden).
	 * 
	 * @param key
	 *            der Key
	 */
	@Override
	public void removeKey(String key) {
		keyList.remove(key);
	}

	/**
	 * Erzeugt einen neuen eindeutigen Key fuer den jeweiligen Benutzer und Klasse und fuegt ihn zu der Liste der Keys
	 * an einer bestimmten Position hinzu.
	 * 
	 * @param pos Position in der Liste
	 * @param objectKey
	 *            der interne Key des DTO's
	 * @return der erzeugte global eindeutige Key
	 */
	public String createKeyAt(int pos, String objectKey) {
		final String key = createKeyCollectionKey() + "_" + objectKey;
		keyList.add(pos, key);
		return key;
	}
}
