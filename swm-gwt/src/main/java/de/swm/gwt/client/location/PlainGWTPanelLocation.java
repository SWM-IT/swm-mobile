package de.swm.gwt.client.location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.swm.gwt.client.interfaces.ILocation;



/**
 * Enthaelt ein Plain-GWT Panel als Content Pannel.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public class PlainGWTPanelLocation implements ILocation {

	private final Panel inner;



	/**
	 * Default contsructor.
	 * 
	 * @param inner
	 *            der name des Div-Tags
	 */
	public PlainGWTPanelLocation(Panel inner) {
		this.inner = inner;
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget toAdd) {
		this.inner.add(toAdd);

	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#contains(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean contains(Widget toCheck) {
		Iterator<Widget> iterator = this.inner.iterator();
		while (iterator.hasNext()) {
			if (toCheck.equals(iterator.next())) {
				return true;
			}
		}
		return false;
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#getItems()
	 */
	public List<Widget> getItems() {
		List<Widget> result = new ArrayList<Widget>();
		Iterator<Widget> iterator = this.inner.iterator();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#remove(com.google.gwt.user.client.ui.Widget)
	 */
	public void remove(Widget toRemove) {
		this.inner.remove(toRemove);
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#render()
	 */
	public void render() {
		this.inner.setVisible(true);
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#asWidget()
	 */
	@Override
	public Widget asWidget() {
		return this.inner;
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#removeAll()
	 */
	@Override
	public void removeAll() {
		for (Widget toRemove : getItems()) {
			this.inner.remove(toRemove);
		}
	}



	/**
	 * Erzeugt einen default pannel.
	 * 
	 * @return der dafault pannel
	 */
	public static VerticalPanel createPannel() {
		VerticalPanel contentPannel = new VerticalPanel();
		return contentPannel;
	}



	@Override
	public void addToLastLocation(Widget toAdd) {
		add(toAdd);
	}

}
