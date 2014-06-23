package de.swm.gwt.client.responsive;


/**
 * @author Ed Bras
 */
public interface IMatchMedia extends IHasMediaMatchChangeHandlers {

	boolean hasMatch();

	String getMedia();

}
