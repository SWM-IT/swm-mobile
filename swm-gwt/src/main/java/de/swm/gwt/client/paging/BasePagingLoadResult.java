package de.swm.gwt.client.paging;

import java.util.List;

import de.swm.gwt.client.interfaces.IModelData;
import de.swm.gwt.client.interfaces.IPagingLoadResult;



/**
 * Base-Paging load implementierung..
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 * @param <T>
 */
public class BasePagingLoadResult<T extends IModelData> implements IPagingLoadResult<T> {

	private static final long serialVersionUID = 1L;
	
	private int offset;
	private int totalLength;
	private List<T> payload;



	/**
	 * GWT Contructor.
	 */
	public BasePagingLoadResult() {

	}



	/**
	 * 
	 * Default constructor.
	 * 
	 * @param payload
	 *            der payload.
	 */
	public BasePagingLoadResult(List<T> payload) {
		this.payload = payload;
	}



	@Override
	public int getOffset() {
		return offset;
	}



	@Override
	public int getTotalLength() {
		return totalLength;
	}



	@Override
	public List<T> getData() {
		return payload;
	}



	/**
	 * offset the offset to set.
	 * 
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}



	/**
	 * totalLength the totalLength to set.
	 * 
	 * @param totalLength
	 *            the totalLength to set
	 */
	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}

}
