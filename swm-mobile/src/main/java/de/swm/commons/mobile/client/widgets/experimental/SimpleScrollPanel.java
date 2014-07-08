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
package de.swm.commons.mobile.client.widgets.experimental;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.PanelBase;

/**
 * Improved scroll panel (still experimental)
 */
public class SimpleScrollPanel extends PanelBase implements HasWidgets, EventListener  {
	
	JavaScriptObject scrollListener;
	JavaScriptObject touchListener;
	int scrollTop = 0;

	public SimpleScrollPanel() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getScrollPanelCss().simpleScrollPanel());
		sinkEvents(Event.TOUCHEVENTS);
		sinkEvents(Event.ONSCROLL);
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().getOptions().setEnableNativeEventPropagation(true);
		if (scrollListener == null) {
			scrollListener = Utils.addEventListener(getElement(), "onscroll", true, this);
		}
		if (touchListener == null) {
			touchListener = Utils.addEventListener(getElement(), "touchmove", true, this);
		}
	}
	
	@Override
	protected void onUnload() {
		if (touchListener != null) {
			Utils.removeEventListener(getElement(), "touchmove", true, touchListener);
			touchListener = null;
		}
		if (scrollListener != null) {
			Utils.removeEventListener(getElement(), "onscroll", true, scrollListener);
			scrollListener = null;
		}
		DragController.get().getOptions().setEnableNativeEventPropagation(false);		
		super.onUnload();
	}
	
	@Override
	public Widget getWidget() {
		return myFlowPanel.getWidget(0);
	}
	
	/**
	 * Scrolls to the default position.
	 */
	public void reset() {
		setPostionToTop();
	}

	/**
	 * Scrolls to the top.
	 */
	public void setPostionToTop() {
		scrollTop = 0;
		getElement().setScrollTop(0);
	}

	/**
	 * Scrolls to the bottom.
	 */
	public void setPositionToBottom() {
		scrollTop = getElement().getScrollHeight() - getElement().getClientHeight();
		getElement().setScrollTop(scrollTop);
	}

	/**
	 * Sets the croll position
	 * 
	 * @param pos
	 *            the y axis pos
	 */
	public void setScrollPosition(int pos) {
		scrollTop = pos;
		getElement().setScrollTop(pos);
	}

	/**
	 * Returns the current scroll position.
	 * 
	 * @return the position
	 */
	public int getScrollPosition() {
		return getElement().getScrollTop();
	}
	
	public void restoreScrollPosition() {
		getElement().setScrollTop(scrollTop);
	}

	@Override
	public void add(Widget w) {
		assert myFlowPanel.getWidgetCount() == 0 : "Can only add one widget to SimpleScrollPanel.";
		super.add(w);
		if (SWMMobile.getOsDetection().isIOs()) {
			Utils.setTranslateY(w.getElement(), 0); // anti-flickering on iOS.
		}
	}
	
	private int lastTouchY = 0;
	private int eventSample = 0;
	private static final int NUM_SAMPLES = 3; // sample every third touchmove event to get better direction signal

	@Override
	public void onBrowserEvent(Event event) {		
		String type = event.getType();
		
		if ("touchmove".equals(type)) {
			eventSample++;
			if (eventSample == NUM_SAMPLES) {
				eventSample = 0;
			
				boolean limitTop = false;
				boolean limitBottom = false;
				int newTouchY = 0;
				
				JsArray<Touch> touches = event.getTouches();
				if (touches != null && touches.length() > 0) {
					newTouchY = touches.get(0).getClientY();
				}
				int delta = newTouchY - lastTouchY;
				int newScrollTop = getElement().getScrollTop();
	
				if (delta < 0) {
					limitTop = (newScrollTop == 0);
					limitBottom = false;
				} else if (delta > 0) {
					limitBottom = (newScrollTop == (getElement().getScrollHeight() - getElement().getClientHeight()));
					limitTop = false;
				}
								
				if (limitTop || limitBottom) {
					event.preventDefault();
				}
				
				lastTouchY = newTouchY;
			}
		}
		
		Element target = event.getEventTarget().cast();
		if (getElement().equals(target)) {
			if ("scroll".equals(type)) {
				scrollTop = getElement().getScrollTop();
			}
		}
		
		super.onBrowserEvent(event);
	}
}
