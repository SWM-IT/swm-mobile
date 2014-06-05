package de.swm.gwt.client.pk;

import java.io.Serializable;

/**
 * Liefert den Primaerschluessel.
 * @author wiese.daniel
 * copyright (C) 2010, SWM Services GmbH
 *
 */
public interface IPrimaryKey extends Serializable {

	/**
	 * Liefert die innere ID der Datenbank.
	 * @return die innere PK
	 */
	long getInnerPk();

	/**
	 * Wenn der Primary key nioch nicht in der DB abgelegt wurde.
	 * @return true wenn neu
	 */
	boolean isNew();

	/**
	 * Die Correlation ID wird fuer das tracken eine Benutzer-Konversation
	 * verwendet.
	 * 
	 * @param id
	 *            die korrelations ID
	 */
	void setCorrelationID(String id);

	/**
	 * Liefer true wenn bereits einen Korrelations ID gesetzt ist. Die
	 * Correlation ID wird fuer das tracken eine Benutzer-Konversation
	 * verwendet.
	 * 
	 * @return true wenn gesetzt
	 */
	boolean hasCorrelationID();

	/**
	 * Liefert die Korrelations ID.
	 * 
	 * @return id
	 *            die Korrelations-ID
	 */
	String getCorrelationID();

}
