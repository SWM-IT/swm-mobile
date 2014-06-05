package de.swm.gwt.client.interfaces;

import java.io.Serializable;



/**
 * Definiert beim Paging welche teile des Results geladen werden sollen.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface IPagingLoadConfig extends Serializable {

	/**
	 * The number of records being requested.
	 * 
	 * @return die anzahle der Daten
	 */
	int getLimit();



	/**
	 * The offset for the first record to retrieve.
	 * 
	 * @return der offset.
	 */
	int getOffset();

	/**
	 * the direction for sorting.
	 * @return the direction
	 */
	SortDirection getSortDirection();

	/**
	 * the field for sorting or null if no sorting required.
	 * @return the field.
	 */
	String getSortField();


	enum SortDirection{
		/** standard order, i.e. A-Z. */
		ASCENDING,
		/** reverse order, i.e. Z-A. */
		DESCENDING;
	}
}