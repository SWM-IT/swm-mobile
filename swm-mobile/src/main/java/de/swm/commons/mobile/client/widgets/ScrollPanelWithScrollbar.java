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

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.*;
import de.swm.commons.mobile.client.utils.Utils;


/**
 * Scroll panel - has the ability to keep the {@link de.swm.commons.mobile.client.widgets.IHeaderPanel} implementations always on the top.
 */
public class ScrollPanelWithScrollbar extends PanelBase implements HasWidgets, DragEventsHandler, SwipeEventsHandler {

	private static final int TIME_FACTOR = 3000;
	private static final double DISTANCE_FACTOR = 0.25;
	public static final int DEFAULT_TRANSITION_DURATION = 500;
	private static final int OFFSET = 2;
	private boolean myHasTextBox = false;
	private IScrollMonitor scrollMonitor;
	private final ScrollBar scrollBar;

	private boolean showScrollBar = true;

	/**
	 * Default constructor.
	 */
	public ScrollPanelWithScrollbar() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().scrollPanel());
		this.scrollBar = new ScrollBar();
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
		boolean isDesktop = SWMMobile.getOsDetection().isDesktop();
		
		DragController.get().addDragEventsHandler(this);
		DragController.get().addSwipeEventsHandler(this);

		if (isDesktop) {
			addMouseWheelHandler();
		}
	}


	@Override
	public void onUnload() {
		DragController.get().removeDragEventsHandler(this);
		DragController.get().removeSwipeEventHandler(this);
	}


	@Override
	public Widget getWidget() {
		return myFlowPanel.getWidget(1);
	}


	/**
	 * Scrolls to the default position.
	 */

	private void addMouseWheelHandler() {
		this.addDomHandler(new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent mouseWheelEvent) {
				int newScrollPosition = getScrollPosition() - mouseWheelEvent.getDeltaY() * 10;

				if (newScrollPosition > 0) {
					newScrollPosition = 0;
				} else if (-newScrollPosition + Utils.getHeight(getElement()) > getWidget().getElement().getOffsetHeight()) {
					newScrollPosition = Utils.getHeight(getElement()) - getWidget().getElement().getOffsetHeight();
				}

				int screeHeight = Utils.getHeight(getElement());
				int widgetHeight = getWidget().getElement().getOffsetHeight();
				if (screeHeight > widgetHeight) {
					newScrollPosition = 0;
				}

				setScrollPosition(newScrollPosition);
			}
		}, MouseWheelEvent.getType());
	}

	public void reset() {
		Utils.setTransitionDuration(getWidget().getElement(), 0);
		Utils.setTranslateY(getWidget().getElement(), 0);
	}


	/**
	 * Scrolls to the top.
	 */
	public void setPostionToTop() {
		setScrollPosition(0, 0);
	}


	/**
	 * Scrolls to the button.
	 */
	public void setPositionToBottom() {
		setScrollPosition(this.getElement().getClientHeight() - this.getElement().getScrollHeight(), 0);
	}


	public void setScrollPosition(int pos) {
		setScrollPosition(pos, 0);
	}

	/**
	 * Sets the scroll position
	 *
	 * @param pos the y axis pos
	 */
	public void setScrollPosition(int pos, int transitionDuration) {
		Element element = getWidget().getElement();
		Utils.setTransitionDuration(element, transitionDuration);

		if (myHasTextBox) {
			setStyleTop(pos);
		} else {
			Utils.setTranslateY(element, pos);
		}


		if (showScrollBar) {
			int panelHeight = Utils.getHeight(this.getElement());
			int widgetHeight = getWidget().getElement().getOffsetHeight();
			this.scrollBar.renderScrollbar(widgetHeight, panelHeight, -pos, transitionDuration);
			this.scrollBar.fadeOut((int) transitionDuration + DEFAULT_TRANSITION_DURATION * 2);
		} else {
			this.scrollBar.hide();
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
		if (current != matrix) { // scroll on going
			int diff = current - matrix;
			int offset = diff > OFFSET ? OFFSET : diff > -OFFSET ? diff : -OFFSET;
			setScrollPosition(matrix + offset, 0);
			DragController.get().suppressNextClick();
		}
		e.stopPropagation();
	}


	@Override
	public void onDragMove(DragEvent e) {
		Element widgetEle = getWidget().getElement();
		int panelHeight = Utils.getHeight(this.getElement());
		int widgetHeight = widgetEle.getOffsetHeight();
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
		setScrollPosition(current, 0);
		e.stopPropagation();
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
		int widgetHeight = widgetEle.getOffsetHeight();
		if (current > 0 // exceed top boundary
				|| panelHeight > widgetHeight) {
			setScrollPosition(0, DEFAULT_TRANSITION_DURATION);
		} else if (-current + panelHeight > widgetHeight) { // exceed bottom boundary
			setScrollPosition(panelHeight - widgetHeight, DEFAULT_TRANSITION_DURATION);
		}
		e.stopPropagation();
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
		setScrollPosition((int) current, (int) time);
	}


	@Override
	public void onSwipeHorizontal(SwipeEvent e) {
	}


	@Override
	public void add(Widget w) {
		assert myFlowPanel.getWidgetCount() == 0 : "Can only add one widget to ScrollPanel.";
		super.add(this.scrollBar);

		super.add(w);
		if (SWMMobile.getOsDetection().isIOs()) {
			Utils.setTranslateY(w.getElement(), 0); // anti-flickering on iOS.
		}
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

	/**
	 * Indicates if the scroll bar should be shown or not.
	 *
	 * @return .
	 */
	public boolean isShowScrollBar() {
		return showScrollBar;
	}

	/**
	 * Indicates if the scroll bar should be shown or not.
	 *
	 * @param showScrollBar true if it should be shown, false if not.
	 */
	public void setShowScrollBar(boolean showScrollBar) {
		this.showScrollBar = showScrollBar;
	}
}
