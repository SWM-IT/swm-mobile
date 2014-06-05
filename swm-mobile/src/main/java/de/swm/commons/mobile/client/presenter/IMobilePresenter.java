package de.swm.commons.mobile.client.presenter;

import de.swm.commons.mobile.client.page.Transition;
import de.swm.commons.mobile.client.widgets.page.IPageWithoutHeader;
import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.mobile.Direction;
import de.swm.gwt.client.mobile.IPage;

/**
 * Base interface for all mobile presenter.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public interface IMobilePresenter {

	/**
	 * Will return the dispatcher.
	 *
	 * @return the dispatcher.
	 */
	IDispatcher getDispatcher();

	/**
	 * Will return the decission to leave this Page. The dafault beahviour is to leave the current Page.
	 *
	 * @return the decission to leave the Page.
	 */
	LeavePageResponse leavePageRequest();

	/**
	 * True is one the presenters owning views is visible. More precisely if this presenter had already executed
	 * the <code>gotoPage</code> method.
	 *
	 * @return true if visible.
	 */
	boolean isOwningViewVisible();


	/**
	 * Will display a view.
	 *
	 * @param originator the currently active view.
	 * @param targetView the target view.
	 */
	void gotoPage(IPage originator, IPageWithoutHeader targetView);

	/**
	 * Will display a view.
	 *
	 * @param originator          the currently active view.
	 * @param targetView          the target view.
	 * @param automaticTransition executes a default transition.
	 * @param transition          transition effect
	 * @param transitionDirection direction
	 */
	void gotoPage(IPage originator, IPageWithoutHeader targetView, boolean automaticTransition,
				  Transition transition, Direction transitionDirection);

	/**
	 * Will display a view but with root page as Cotent area.
	 * @param targetView the target view to display
	 */
	void gotoPageWithRootAsContentArea(IPageWithoutHeader targetView);
}
