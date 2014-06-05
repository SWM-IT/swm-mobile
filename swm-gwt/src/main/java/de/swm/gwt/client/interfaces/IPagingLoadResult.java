package de.swm.gwt.client.interfaces;

import java.io.Serializable;
import java.util.List;



/**
 * Interface um Paging zu ermoeglichen.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 * @param <T>
 */
public interface IPagingLoadResult<T extends IModelData> extends Serializable {

	/**
	 * Returns the current offset of the results.
	 * 
	 * @return the offset
	 */
	int getOffset();



	/**
	 * Returns the total count. This value will not equal the number of records being returned when paging is used.
	 * 
	 * @return the total count
	 */
	int getTotalLength();



	/**
	 * Liefert den Payload.
	 * 
	 * @return der payload.
	 */
	List<T> getData();

}
