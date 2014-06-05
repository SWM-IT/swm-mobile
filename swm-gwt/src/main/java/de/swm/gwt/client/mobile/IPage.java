package de.swm.gwt.client.mobile;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;



/**
 * A <code>IPage</code> describes the current screen content.
 * 
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 * 
 */
public interface IPage {

	/**
	 * Starts a transition form one page to another.
	 * 
	 * @param toPage
	 *            the target page which will be displayed next
	 * @param direction
	 *            die richtung des ueberganges
	 */
	void goTo(final IPage toPage, Direction direction);



	/**
	 * Starts a transition form one page to another.
	 * 
	 * @param toPage
	 *            the target page which will be displayed next
	 * @param direction
	 *            die richtung des ueberganges
	 * @param callback
	 *            callback fuer Benachrichtigungen ueber beendete Transisitionen.
	 */
	void goTo(final IPage toPage, Direction direction, ITransitionCompletedCallback callback);



	/**
	 * Setzt den callback der nach dem vorendeten uebergang zu dieser Seite aufgerufen wird.
	 * 
	 * @param callback
	 *            der calkback.
	 */
	void setTransitionEndCallback(ITransitionCompletedCallback callback);



	/**
	 * Liefert einen Namen der Seite (zum Debugging).
	 * 
	 * @return der Name
	 */
	String getName();



	/**
	 * Will be called before the page gets rendered on the screen.
	 */
	void beforeEnter();



	/**
	 * Will be called leaving the current page before the next page will be rendered on the screen.
	 */
	void beforeLeaving();



	/**
	 * Returns the page as a widget.
	 * 
	 * @return the page as a widget.
	 */
	Widget getWidget();



	/**
	 * Returns the page itself as a {@link Composite}.
	 * 
	 * @return the page itself as a {@link Composite}.
	 */
	Composite asComposite();



	/**
	 * Sets another widget as parent widget. Transitions from one page to another will not change the parent widget.
	 * 
	 * @param parent
	 *            the parent widget.
	 */
	void setParent(HasWidgets parent);

}
