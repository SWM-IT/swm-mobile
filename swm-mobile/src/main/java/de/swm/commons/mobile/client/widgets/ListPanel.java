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
package de.swm.commons.mobile.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.base.PanelBase;
import de.swm.commons.mobile.client.event.*;
import de.swm.commons.mobile.client.utils.Utils;



/**
 * List panel which contains list items an is typically placed inside a {@link de.swm.commons.mobile.client.widgets.scroll.ScrollPanel}.
 * 
 */
public class ListPanel extends PanelBase implements ClickHandler, DragEventsHandler {

	private static final int DRAG_DELAY = 75;
	private static final int DRAG_MOVE_DELAY = 1000;
	private boolean myShowArrow;
	protected int mySelected = -1; // allow access in derived classes
	private boolean mySelectable = true;
	/** applies the flex style if set **/
	private boolean isFlex = false;



	/**
	 * Default constructor.
	 */
	public ListPanel() {
		addDomHandler(this, ClickEvent.getType());
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().listPanel());
	}



	/**
	 * Adds a selection handler.
	 * 
	 * @param handler
	 *            selection handler
	 * @return handle to remove this handler.
	 */
	public HandlerRegistration addSelectionChangedHandler(SelectionChangedHandler handler) {
		return this.addHandler(handler, SelectionChangedEvent.TYPE);
	}



	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addDragEventsHandler(this);
	}



	@Override
	public void onUnload() {
		DragController.get().removeDragEventsHandler(this);
	}



	@Override
	public void add(Widget w) {
		if (w instanceof ListItem) {
			super.add(w);
			if (SWMMobile.getOsDetection().isIOs()) {
				Utils.setTranslateY(w.getElement(), 0); // anti-flickering on iOS.
			}
		} else {
			ListItem listItem = new ListItem();
			super.add(listItem);
			listItem.add(w);
			if (myShowArrow) {
				Chevron chevron = new Chevron();
				listItem.add(chevron);
			}
		}
	}



	@Override
	public void onClick(ClickEvent e) {
		if (mySelected >= 0 && getWidgetCount() > mySelected) {
			ListItem item = (ListItem) getWidget(mySelected);
			if (!item.getDisabled()) {
				SelectionChangedEvent selectionChangedEvent = new SelectionChangedEvent(mySelected, e.getNativeEvent()
						.getEventTarget());
				this.fireEvent(selectionChangedEvent);
				item.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().pressed());
			}
			mySelected = -1;
		}
	}



	/**
	 * Global visibility of the right arrow
	 * 
	 * @param show
	 *            true if shown
	 */
	public void setShowArrow(boolean show) {
		myShowArrow = show;
		for (int i = 0; i < getWidgetCount(); i++) {
			ListItem listItem = (ListItem) getWidget(i);
			listItem.setShowArrowFromParent(show);
		}
	}



	public boolean getShowArrow() {
		return myShowArrow;
	}



	public void setSelectable(boolean selectable) {
		mySelectable = selectable;
	}



	public boolean getSelectable() {
		return mySelectable;
	}



	/**
	 * Returns true is the css rendering is flexible.
	 * 
	 * @return the isFlex
	 */
	public boolean isFlex() {
		return isFlex;
	}



	/**
	 * Enabled/disables flexible css rendering.
	 * 
	 * @param isFlex
	 *            the isFlex to set
	 */
	public void setFlex(boolean isFlex) {
		this.isFlex = isFlex;
		if (isFlex) {
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelFlexCss().flex());
			addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelFlexCss().modify());

		} else {
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelFlexCss().flex());
			removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelFlexCss().modify());

		}
	}



	@Override
	public void onDragStart(DragEvent e) {
		if (mySelectable) {
			mySelected = Utils.getTargetItemIndex(getElement(), e.getNativeEvent().getEventTarget());
			if (mySelected >= 0) {
				final Timer timer = new Timer() {
					@Override
					public void run() {
						if (mySelected >= 0 && getWidgetCount() > 0 && mySelected < getWidgetCount()) {
							ListItem item = (ListItem) getWidget(mySelected);
							if (!item.getDisabled()) {
								getWidget(mySelected).addStyleName(
										SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().pressed());
							}
						}
					}
				};
				timer.schedule(DRAG_DELAY);
			}
		}
	}



	@Override
	public void onDragMove(DragEvent e) {
		if (mySelected >= 0) {
			getWidget(mySelected).removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().pressed());
			final Timer timer = new Timer() {
				@Override
				public void run() {
					mySelected = -1;
				}
			};
			timer.schedule(DRAG_MOVE_DELAY);
		}
	}



	@Override
	public void onDragEnd(DragEvent e) {
		if (mySelected >= 0) {
			getWidget(mySelected).removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().pressed());
			// _selected = -1; need to keep the selected value for click event.
		}
	}



	/**
	 * Returns th list item on index position
	 * 
	 * @param index
	 *            the index
	 * @return the position
	 */
	public ListItem getItem(int index) {
		return (ListItem) getWidget(index);
	}

	/**
	 * Html code for the right arrow called chevron.
	 * 
	 * 
	 * 
	 * 
	 */
	static class Chevron extends HTML {
		/**
		 * Default constructor.
		 */
		public Chevron() {
			super("<div class=\"" + SWMMobile.getTheme().getMGWTCssBundle().getListPanelCss().chevron()
					+ "\"><span></span><span></span></div>");
		}
	}

}
