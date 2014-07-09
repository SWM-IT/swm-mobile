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
package de.swm.commons.mobile.client.widgets.tab;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.page.Transition;
import de.swm.commons.mobile.client.widgets.LoadingIndicatorPopup;
import de.swm.commons.mobile.client.widgets.SWMMobileWidgetBase;
import de.swm.commons.mobile.client.widgets.itf.ISpinnerStarted;

import java.util.Iterator;
import java.util.logging.Logger;


/**
 * A Tab panel containts several {@link Tab}s. Each Tab has a {@link TabHeader} and a {@link TabContent}.
 */
public class TabPanel extends SWMMobileWidgetBase implements HasWidgets, ClickHandler {

	private static final Logger LOGGER = Logger.getLogger(TabPanel.class.getName());


	public static final int SPINNER_DELAY_MILLIS = 500;
	private final FlowPanel myPanel = new FlowPanel();
	private final FlowPanel myTabHeaderPanel = new FlowPanel();
	private final FlowPanel myTabContentPanel = new FlowPanel();
	private int mySelectedTabIndex = -1;
	private Transition transition = Transition.SLIDE;
	/**
	 * True if every tab transition should have an spinner page.
	 */
	private boolean showSpinnerBetweenTransitions = false;
	private boolean isSpinnerStarted = false;
	private final LoadingIndicatorPopup spinner = new LoadingIndicatorPopup();


	/**
	 * Default constructor.
	 */
	public TabPanel() {
		initWidget(myPanel);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTabPanelCss().tabPanel());
		myPanel.add(myTabHeaderPanel);
		myPanel.add(myTabContentPanel);
		myTabHeaderPanel.addDomHandler(this, ClickEvent.getType());
	}

	public Transition getTransition() {
		return transition;
	}


	/**
	 * Sets the transition effect shown when switching between the tabs. Default is Transition.SLIDE.
	 *
	 * @param transition transition effect; if set to {code}null{code}, no transition effect is shown.
	 */
	public void setTransition(Transition transition) {
		this.transition = transition;
	}


	@Override
	public void add(Widget w) {
		assert w instanceof Tab : "Can only place Tab widgets inside a Tab Panel.";
		myTabHeaderPanel.add(w);
	}


	@Override
	public void onInitialLoad() {
		if (myTabHeaderPanel.getWidgetCount() > 0) {
			// FIXME:allow a different default tab to be set?
			selectTab(0);
		}
	}


	/**
	 * Selects a tab by his index position.
	 *
	 * @param index the index
	 */
	public void selectTab(int index) {
		if (mySelectedTabIndex == index) {
			LOGGER.info("same tab");
			return;
		}
		Tab from = unselectCurrentTab();
		Tab to = (Tab) myTabHeaderPanel.getWidget(index);
		to.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTabPanelCss().selected());

		if (from == null) {
			myTabContentPanel.add(to.getContent());
		} else if (transition == null) {
			myTabContentPanel.remove(from.getContent());
			myTabContentPanel.add(to.getContent());
		} else {
			transition.start(from.getContent(), to.getContent(), myTabContentPanel, index < mySelectedTabIndex);
		}
		mySelectedTabIndex = index;
	}


	public int getSelectedTabIndex() {
		return mySelectedTabIndex;
	}


	@Override
	public void onClick(ClickEvent event) {
		if (this.showSpinnerBetweenTransitions) {
			if (!isSpinnerStarted) {
				final int index = getClickedTabHeaderIndex(event);
				this.startPopup(new ISpinnerStarted() {
					@Override
					public void spinnerStarted() {
						isSpinnerStarted = true;
						if (index != -1) {
							selectTab(index);
						}

						Timer timer = new Timer(){
							@Override
							public void run() {
								stopSpinnerIfRunning();
							}
						};
						timer.schedule(SPINNER_DELAY_MILLIS);


					}
				});
			}
		} else {
			int index = getClickedTabHeaderIndex(event);
			if (index != -1) {
				selectTab(index);
			}
		}
	}

	/**
	 * True, if a spinner will be shown betreen transisitions.
	 * @param showSpinnerBetweenTransitions true if spinner wil be shown between transitions.
	 */
	public void setShowSpinnerBetweenTransitions(boolean showSpinnerBetweenTransitions) {
		this.showSpinnerBetweenTransitions = showSpinnerBetweenTransitions;
	}

	/**
	 * Will hide a loading indicator popup.
	 */
	private void stopSpinnerIfRunning() {
		if (showSpinnerBetweenTransitions && isSpinnerStarted) {
			isSpinnerStarted = false;
			this.spinner.setVisible(false);
		}
	}


	@Override
	public void clear() {
		myPanel.clear();
	}


	@Override
	public Iterator<Widget> iterator() {
		return myPanel.iterator();
	}


	@Override
	public boolean remove(Widget w) {
		return myPanel.remove(w);
	}


	/**
	 * Un-selects the current Tab.
	 *
	 * @return the tab previously selected
	 */
	private Tab unselectCurrentTab() {
		if (mySelectedTabIndex == -1) {
			return null;
		}
		Tab tab = (Tab) myTabHeaderPanel.getWidget(mySelectedTabIndex);
		tab.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTabPanelCss().selected());
		return tab;
	}


	/**
	 * Returns the index of clicked tab panel.
	 *
	 * @param e the click event
	 * @return the index
	 */
	private int getClickedTabHeaderIndex(ClickEvent e) {
		Element div = Element.as(e.getNativeEvent().getEventTarget());
		if (div == myTabHeaderPanel.getElement()) {
			LOGGER.info("TabClicked: " + e.toString());
			return -1;
		}
		while (div.getParentElement() != myTabHeaderPanel.getElement()) {
			div = div.getParentElement();
		}
		return DOM.getChildIndex((com.google.gwt.user.client.Element) myTabHeaderPanel.getElement(),
				(com.google.gwt.user.client.Element) div);
	}

	/**
	 * Starts a propgress bar.
	 *
	 * @param spinnerStarted spinner is started
	 * @return the loading indicator.
	 */
	private LoadingIndicatorPopup startPopup(final ISpinnerStarted spinnerStarted) {
		this.spinner.showCentered(true);
		final Timer timer = new Timer() {

			@Override
			public void run() {
				isSpinnerStarted = true;
				spinnerStarted.spinnerStarted();
			}
		};
		timer.schedule(50);
		return this.spinner;
	}


}
