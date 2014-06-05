/**
 * 
 */
package de.swm.gwt.client.eventbus;

import de.swm.gwt.client.mobile.IPage;



/**
 * Definiert ein Objekt, das zu einem Event optional uebergeben wird fuer mobile Plattformen.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2010-2011, SWM Services GmbH
 * 
 */
public interface IMobileCustomData extends ICustomData {

	/**
	 * Kann ein benutzerdefiniertes Object an ein Formular/Liste uebermitteln.
	 * 
	 * @return das user objekt
	 */
	Object userObject();
	
	
	/**
	 * Liefert null oder ein Typsicheres Benutzerobjekt.
	 * @param <T> der typ
	 * @param forType die Klasse des Typs
	 * @return das typsichere Benutzerobjekt.
	 */
	<T> T nullAndTypeSaveUserObject(Class<T> forType);



	/**
	 * Die seite (mobile Plattformen sind Seitenbasiert) die das Event ausgeloest hat.
	 * 
	 * @return die Seite die das event ausgeloest hat oder null
	 */
	IPage originatorPage();



	/**
	 * Ermoeglicht das setzen eines benuterobjektes.
	 * 
	 * @param toSet
	 *            das benutzerobjekt.
	 */
	void setUserObject(Object toSet);

}
