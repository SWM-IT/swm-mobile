package de.swm.gwt.client.location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.swm.gwt.client.interfaces.ILocation;

/**
 * Erzeugt eine Location (Position am Bildschirm) innerhalb eiens auf der HTML
 * Seite definierten Div-Tags. Fuer Plain GWT Formulare
 * 
 * @author wiese.daniel
 * <br>copyright (C) 2010, SWM Services GmbH
 * 
 */
public class HtmlDivTagPlainGWTLocation implements ILocation {

	private final VerticalPanel contentPannel;

	/**
	 * Default contsructor.
	 * 
	 * @param divTagName
	 *            der name des Div-Tags
	 */
	public HtmlDivTagPlainGWTLocation(String divTagName) {
		contentPannel = createPannel();
		RootPanel.get(divTagName).add(contentPannel);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#add(com.google.gwt.user.client.ui.Widget)
	 */
	public void add(Widget toAdd) {
		contentPannel.add(toAdd);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addToLastLocation(Widget toAdd) {
		add(toAdd);
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#contains(com.google.gwt.user.client.ui.Widget)
	 */
	public boolean contains(Widget toCheck) {
		Iterator<Widget> iterator = contentPannel.iterator();
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
		Iterator<Widget> iterator = contentPannel.iterator();
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
		contentPannel.remove(toRemove);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#render()
	 */
	public void render() {
		contentPannel.setVisible(true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#asWidget()
	 */
	@Override
	public Widget asWidget() {
		return contentPannel;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see de.swm.gwt.client.interfaces.ILocation#removeAll()
	 */
	@Override
	public void removeAll() {
		for (Widget toRemove : getItems()) {
			this.contentPannel.remove(toRemove);
		}
	}

	/**
	 * Erzeugt einen default pannel.
	 * @return der dafault pannel
	 */
	public static VerticalPanel createPannel() {
		VerticalPanel contentPannel = new VerticalPanel();
		return contentPannel;
	}

}
