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

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.*;
import de.swm.commons.mobile.client.utils.Utils;

/**
 * Scroll panel - has the ability to keep the {@link IHeaderPanel} implementations always on the top.
 */
public class ScrollPanel extends PanelBase implements HasWidgets, DragEventsHandler, SwipeEventsHandler {

	private static final int TIME_FACTOR = 3000;
	private static final double DISTANCE_FACTOR = 0.25;
	private static final int DEFAULT_TRANSITION_DURATION = 500;
	private static final int OFFSET = 2;
	private boolean myHasTextBox = false;
	private IScrollMonitor scrollMonitor;

	private ScrollPanelEventsHandler scrollPanelEventsHandler;
	private int offsetHeight = -1;


	/**
	 * Default constructor.
	 */
	public ScrollPanel() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().scrollPanel());
	}

	public void setScrollMonitor(IScrollMonitor scrollMonitor) {

		this.scrollMonitor = scrollMonitor;
	}


	public void setHasTextBox(boolean hasTextBox) {
		myHasTextBox = hasTextBox && SWMMobile.getOsDetection().isAndroid();
	}


	public boolean getHasTextBox() {
		return myHasTextBox;
	}


	@Override
	public void onLoad() {
		DragController.get().addDragEventsHandler(this);
		DragController.get().addSwipeEventsHandler(this);
	}


	@Override
	public void onUnload() {
		DragController.get().removeDragEventsHandler(this);
		DragController.get().removeSwipeEventHandler(this);
	}


	@Override
	public Widget getWidget() {
		return myFlowPanel.getWidget(0);
	}


	public ScrollPanelEventsHandler getScrollPanelEventsHandler() {
		return scrollPanelEventsHandler;
	}

	/**
	 * Neuen Handler fuer Scroll Panel Events registrieren.
	 *
	 * @param scrollPanelEventsHandler eventhandler
	 */
	public void addScrollPanelEventsHandler(ScrollPanelEventsHandler scrollPanelEventsHandler) {
		this.scrollPanelEventsHandler = scrollPanelEventsHandler;
	}


	/**
	 * Scrolls to the default position.
	 */
	public void reset() {
		Utils.setTransitionDuration(getWidget().getElement(), 0);
		Utils.setTranslateY(getWidget().getElement(), 0);
	}


	/**
	 * Scrolls to the top.
	 */
	public void setPostionToTop() {
		Utils.setTransitionDuration(getWidget().getElement(), 0);
		Utils.setTranslateY(getWidget().getElement(), 0);
	}


	/**
	 * Scrolls to the button.
	 */
	public void setPositionToBottom() {
		Utils.setTransitionDuration(getWidget().getElement(), 0);
		Utils.setTranslateY(getWidget().getElement(), this.getElement().getClientHeight()
				- this.getElement().getScrollHeight());
	}


	/**
	 * Sets the croll position
	 *
	 * @param pos the x axis pos
	 */
	public void setScrollPosition(int pos) {
		if (myHasTextBox) {
			setStyleTop(pos);
		} else {
			Element element = getWidget().getElement();
			Utils.setTranslateY(element, pos);
		}
	}


	/**
	 * Returns the current scroll position.
	 *
	 * @return the position
	 */
	public int getScrollPosition() {
		if (myHasTextBox) {
			return getStyleTop();
		} else {
			Element element = getWidget().getElement();
			return Utils.getTranslateY(element);
		}
	}


	/**
	 * Returns the next scroll position
	 *
	 * @return then next scroll position
	 */
	public int getScrollToPosition() {
		if (myHasTextBox) {
			return getStyleTop();
		} else {
			Element element = getWidget().getElement();
			return Utils.getMatrixY(element);
		}
	}


	@Override
	public void onDragStart(DragEvent e) {
		if (scrollMonitor != null) {
			scrollMonitor.onScrollStart();
		}
		int matrix = getScrollToPosition();
		int current = getScrollPosition();
		Utils.setTransitionDuration(getWidget().getElement(), 0);
		if (current != matrix) { // scroll on going
			int diff = current - matrix;
			int offset = diff > OFFSET ? OFFSET : diff > -OFFSET ? diff : -OFFSET;
			setScrollPosition(matrix + offset);
			DragController.get().suppressNextClick();
		}
	}


	@Override
	public void onDragMove(DragEvent e) {
		Element widgetEle = getWidget().getElement();
		int panelHeight = Utils.getHeight(this.getElement());
		int widgetHeight = calcOffsetHeight(widgetEle);
		int current = getScrollPosition();
		if (current > 0) {
			// exceed top boundary
			if (e.getOffsetY() > 0) { // resist scroll down.
				current += (int) (e.getOffsetY() / OFFSET); // need the cast for production mode.
			} else {
				current += e.getOffsetY() * OFFSET;
			}
		} else if (-current + panelHeight > widgetHeight) { // exceed bottom boundary
			if (e.getOffsetY() < 0) { // resist scroll up.
				current += (int) (e.getOffsetY() / OFFSET);
			} else {
				current += e.getOffsetY() * OFFSET;
			}
		} else {
			current += e.getOffsetY();
		}
		setScrollPosition(current);
	}

	private int calcOffsetHeight(Element widgetEle) {
		return ((this.offsetHeight > 0) ? this.offsetHeight : widgetEle.getOffsetHeight());
	}

	/**
	 * A value greater zero will set the offset height of the panel manually.
	 * Otherwise it will be calculated automatically.
	 *
	 * @param offsetHeight the offset height.
	 */
	public void setOffsetHeight(int offsetHeight) {
		this.offsetHeight = offsetHeight;
	}


	@Override
	public void onDragEnd(DragEvent e) {
		Element widgetEle = getWidget().getElement();
		if (scrollMonitor != null) {
			scrollMonitor.onScrollEnd();
		}
		int current = getScrollPosition();
		if (current == 0) {
			return;
		}
		int panelHeight = Utils.getHeight(this.getElement());
		int widgetHeight = getHeightOfElementOrChildElements(widgetEle);

		if (current > 0 // exceed top boundary
				|| panelHeight > widgetHeight) {

			// fire eventshandler for top boundary
			if (getScrollPanelEventsHandler() != null) {
				getScrollPanelEventsHandler().onTop(e);
			}

			Utils.setTransitionDuration(widgetEle, DEFAULT_TRANSITION_DURATION);
			setScrollPosition(0);
		} else if (-current + panelHeight > widgetHeight) { // exceed bottom boundary

			// fire eventshandler for bottom boundary
			if (getScrollPanelEventsHandler() != null) {
				getScrollPanelEventsHandler().onBottom(e);
			}

			Utils.setTransitionDuration(widgetEle, DEFAULT_TRANSITION_DURATION);
			setScrollPosition(panelHeight - widgetHeight);
		}
	}


	@Override
	public void onSwipeVertical(SwipeEvent e) {
		Element widgetEle = getWidget().getElement();
		int panelHeight = Utils.getHeight(this.getElement());
		int widgetHeight = widgetEle.getOffsetHeight();
		long current = getScrollPosition();
		if ((current >= 0) // exceed top boundary
				|| (-current + panelHeight >= widgetHeight)) { // exceed bottom boundary
			return;
		}

		double speed = e.getSpeed();
		double timeFactor = TIME_FACTOR;
		long time = (long) Math.abs(speed * timeFactor);
		double dicstanceFactor = DISTANCE_FACTOR;
		long distance = (long) (speed * time * dicstanceFactor);
		// Utils.Console("speed " + speed + " time " + time + " distance " + distance + " current " + current);
		current += distance;
		// exceed top boundary?
		if (current > 0) {
			double timeAdj = 1 - (double) current / distance;
			time = (long) (time * timeAdj);
			current = 0;
		} else if (-current + panelHeight > widgetHeight) { // exceed bottom boundary
			long bottom = panelHeight - widgetHeight;
			double timeAdj = 1 - (double) (current - bottom) / distance;
			time = (long) (time * timeAdj);
			current = bottom;
		}
		Utils.setTransitionDuration(widgetEle, time);
		setScrollPosition((int) current);
	}


	@Override
	public void onSwipeHorizontal(SwipeEvent e) {
	}


	@Override
	public void add(Widget w) {
		//assert myFlowPanel.getWidgetCount() == 0 : "Can only add one widget to ScrollPanel.";
		super.add(w);
		if (SWMMobile.getOsDetection().isIOs()) {
			Utils.setTranslateY(w.getElement(), 0); // anti-flickering on iOS.
		}
	}


	/**
	 * Returns the height of the given element. If the element has a height of 0 it searches
	 * recursively for the height of the next visible child (e.g. the next visible slide of a
	 * SliderPanel).
	 *
	 * @param element parent element
	 * @return height of the element of a visible child
	 */
	private int getHeightOfElementOrChildElements(Element element) {
		if (this.offsetHeight > 0) {
			return this.offsetHeight;
		}

		// current element has height? Return this height.
		if (element.getOffsetHeight() != 0) {
			return element.getOffsetHeight();
		}

		// current element is hidden? no height for this element.
		if (element.getStyle().getVisibility().equals("hidden") == true
				|| element.getStyle().getDisplay().equals("none") == true) {
			return 0;
		}

		// search children for height
		NodeList<Node> children = element.getChildNodes();
		if (children.getLength() == 0) {
			return 0;
		}

		int height = 0;
		for (int i = 0; i < children.getLength(); i++) {
			Node currentNode = children.getItem(i);
			if (currentNode instanceof Element) {
				height = getHeightOfElementOrChildElements((Element) currentNode);
			}

			if (height != 0) {
				return height;
			}
		}

		return 0;
	}


	/**
	 * Returns the top style
	 *
	 * @return the top style in px
	 */
	private int getStyleTop() {
		Style style = getWidget().getElement().getStyle();
		String top = style.getTop();
		if (top.isEmpty()) {
			return 0;
		} else {
			return Integer.parseInt(top.replace("px", ""));
		}
	}


	/**
	 * Sets the top stype in pixel
	 *
	 * @param top the top syle
	 */

	private void setStyleTop(int top) {
		Style style = getWidget().getElement().getStyle();
		style.setTop(top, Unit.PX);
	}

	/**
	 * Enables reactions to scroll gestures.
	 */
	public static interface IScrollMonitor {

		void onScrollStart();

		void onScrollEnd();
	}

}