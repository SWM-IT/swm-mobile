/*
 * Copyright 2011 SWM Services GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.client.page;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.SWMMobileWidgetBase;
import de.swm.gwt.client.mobile.Direction;
import de.swm.gwt.client.mobile.IPage;
import de.swm.gwt.client.mobile.ITransitionCompletedCallback;

import java.util.logging.Logger;


/**
 * A <code>Page</code> describes the current screen content. Currently swm-mobile applications are based on a Page concept,
 * means a {@link SimplePage} contains the whole UI content of a screen.
 * 
 */
public abstract class SimplePage extends SWMMobileWidgetBase implements IPage {

	private static final Logger LOGGER = Logger.getLogger(SimplePage.class.getName());

	private Transition transition;
	private static Transition defaultTransition = Transition.SLIDE;
	private HasWidgets parent = RootLayoutPanel.get();
	private ITransitionCompletedCallback transitionEndCallback;



	/**
	 * Defines the parent object of a page (by default this is the root object).
	 * 
	 * @param p
	 *            parent slideUpPanel to place the page in
	 */
	public void setParent(HasWidgets p) {
		parent = p;
	}



	@Override
	protected void initWidget(Widget widget) {
		super.initWidget(widget);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPageCss().page());
		if (SWMMobile.getOsDetection().isAndroid()) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPageCss().adroid());
		} else if (SWMMobile.getOsDetection().isIOs()) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPageCss().iOs());
		} else {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPageCss().desktop());
		}
	}



	/**
	 * Returns the name of the page.
	 * 
	 * @return the name of the page
	 */
	public abstract String getName();




	@Override
	public void onTransitionEnd() {
		if (this.transitionEndCallback != null) {
			LOGGER.info("goto completed");
			this.transitionEndCallback.isCompleted();
			this.transitionEndCallback = null;
		}
	}



	/**
	 * {@inheritDoc}
	 */
	public void setTransitionEndCallback(ITransitionCompletedCallback callback) {
		this.transitionEndCallback = callback;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void goTo(final IPage toPage, Direction direction) {
		goTo(toPage, defaultTransition, direction, null);
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void goTo(final IPage toPage, Direction direction, ITransitionCompletedCallback callback) {
		goTo(toPage, defaultTransition, direction, callback);
	}



	/**
	 * Starts a transition for one page to another page.
	 * 
	 * @param toPage
	 *            the target page
	 * @param transition
	 *            the transition
	 * @param direction
	 *            the direction
	 * @param callback
	 *            the callback when copleted.
	 */
	public void goTo(final IPage toPage, final Transition transition, final Direction direction,
		final ITransitionCompletedCallback callback) {
		if (callback != null) {
			toPage.setTransitionEndCallback(callback);
		}
		if (toPage instanceof SimplePage) {
			LOGGER.info("Going from " + this.getName() + " to " + toPage.getName());
			final SimplePage toPageC = (SimplePage) toPage;
			Element focus = Utils.getActiveElement();
			if (focus != null) {
				focus.blur();
			}
			toPageC.setTransition(transition);
			this.beforeLeaving();
			toPage.beforeEnter();
			if (transition != null) {
				transition.start(this, toPageC, parent, ((direction.equals(Direction.RIGHT)) ? false : true));
			} else {
				Transition.start(this, toPageC, parent);
			}
		}
	}



	/**
	 * Will overwrite the standard transition.
	 * 
	 * @param transition
	 *            the transition when a page will change.
	 */
	void setTransition(Transition transition) {
		this.transition = transition;
	}



	/**
	 * Returns the current transition.
	 * 
	 * @return the transition
	 */
	public Transition getTransition() {
		return transition;
	}



	/**
	 * Will load the page {@link IPage}.
	 * 
	 * @param mainPage
	 *            the main page
	 */
	public static void load(IPage mainPage) {
		setPageResolution();
		mainPage.beforeEnter();
		RootLayoutPanel.get().add(mainPage.asComposite());
	}



	/**
	 * Will load the page {@link SimplePage}.
	 * 
	 * @param mainPage
	 *            the main page
	 */
	public static void load(SimplePage mainPage) {
		setPageResolution();
		mainPage.beforeEnter();
		RootLayoutPanel.get().add(mainPage);
	}



	/**
	 * nabeld to overwrite the default tration (see goto(..))
	 * 
	 * @param transition the default tratione to set.
	 */
	public static void setDefaultTransition(Transition transition) {
		defaultTransition = transition;
	}



	@Override
	public Widget getWidget() { // make getWidget() public
		return super.getWidget();
	}



	/**
	 * Returns the page itself as a {@link Composite}
	 * 
	 * @return the page itself as a {@link Composite}
	 */
	@Override
	public Composite asComposite() {
		return this;
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeEnter() {
		// Intetially left empty

	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeLeaving() {
		// Intetially left empty
	}



	/**
	 * Will sets the CSS Styles matching the current resolution.
	 */
	private static void setPageResolution() {
		SWMMobile.setPageResolutionCSS();
	}

}
