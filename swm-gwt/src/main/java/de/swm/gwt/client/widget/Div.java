package de.swm.gwt.client.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Erzeugt ein Div Tag.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2014, Stadtwerke München GmbH
 */
public class Div extends ComplexPanel {

	/**
	 * Constructor.
	 */
	public Div() {
		setElement(Document.get().createDivElement());
	}

	/**
	 * Alls a child.
	 * @param child .
	 */
	@Override
	public void add(final Widget child) {
		add(child, getElement());
	}

	/**
	 * Sets the id of the html element.
	 * @param id the id
	 */
	public void setId(final String id) {
		getElement().setId(id);
	}

	/**
	 * Returns the id of the html element.
	 * @return the id.
	 */
	public String getId() {
		return getElement().getId();
	}

	/**
	 * Will set the class tag.
	 * @param clazz the class tag.
	 */
	public void setClass(final String clazz) {
		getElement().addClassName(clazz);
	}

}
