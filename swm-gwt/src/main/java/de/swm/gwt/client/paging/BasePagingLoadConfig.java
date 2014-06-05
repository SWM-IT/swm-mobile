package de.swm.gwt.client.paging;

import de.swm.gwt.client.interfaces.IPagingLoadConfig;


/**
 * Basis implementierung von {@link IPagingLoadConfig}.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 *
 */
public class BasePagingLoadConfig implements IPagingLoadConfig {

	private static final long serialVersionUID = 1L;

	private int limit;
	private int offset;
	private SortDirection sortDir;
	private String sortField;



	@Override
	public int getLimit() {
		return limit;
	}



	@Override
	public int getOffset() {
		return offset;
	}



	/**
	 * limit the limit to set.
	 *
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
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



	@Override
	public SortDirection getSortDirection() {
		return sortDir;
	}



	@Override
	public String getSortField() {
		return sortField;
	}



	/**
	 * Setter for the property sortDir.
	 * @param sortDir the direction for sorting.
	 */
	public void setSortDir(SortDirection sortDir) {
		this.sortDir = sortDir;
	}



	/**
	 * Sets the property 'sortDir' to ASCENDING if the flag isAscending is true, DESCENDING if false.
	 * @param isAscending true for ASCENDING.
	 */
	public void setSortDirAscending(boolean isAscending) {
		this.sortDir = isAscending ? SortDirection.ASCENDING : SortDirection.DESCENDING;
	}



	/**
	 * Setter for the property sortField.
	 * @param sortField the field for sorting.
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}


}
