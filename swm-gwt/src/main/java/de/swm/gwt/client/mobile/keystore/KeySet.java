package de.swm.gwt.client.mobile.keystore;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;



/**
 * Konkrete Implementierung von {@link KeyCollection} als Menge
 * von Schlüsseln (ohne Ordnung, nur ein Vorkommen eines Schlüssels).
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class KeySet extends KeyCollection {

	private static final long serialVersionUID = 1L;
	private Set<String> keySet = new HashSet<String>();

	/**
	 * Constructor zur deserialisierung.
	 */
	public KeySet() {
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
	public KeySet(Class<?> forClass, String userId, String useCaseQualifier) {
		super(forClass, userId, useCaseQualifier);
	}
	
	/**
	 * Returns the keys.
	 * 
	 * @return the keys
	 */
	@Override
	public Collection<String> getKeys() {
		return getKeySet();
	}
	
	/**
	 * Returns the keys as a set.
	 * 
	 * @return the keys
	 */
	public Set<String> getKeySet() {
		return keySet;
	}

	/**
	 * Sets the key set.
	 * 
	 * @param keys
	 *            the keys to set
	 */
	public void setKeySet(Set<String> keys) {
		this.keySet = keys;
	}


	/**
	 * Fuegt einen Key wenn nicht vorhanden zur Struktur hinzu, ansonsten wird dieser ignoriert.
	 * 
	 * @param key
	 *            der Key
	 */
	@Override
	public void addKey(String key) {
		if (!keySet.contains(key)) {
			keySet.add(key);
		}
	}
	
	/**
	 * Entfernt einen Key (falls vorhanden).
	 * 
	 * @param key
	 *            der Key
	 */
	@Override
	public void removeKey(String key) {
		keySet.remove(key);
	}
}
