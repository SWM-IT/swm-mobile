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
package de.swm.commons.mobile.client.event;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.user.client.Event;

import de.swm.commons.mobile.client.utils.GhostClickPreventer;
import de.swm.commons.mobile.client.utils.Point;
import de.swm.commons.mobile.client.utils.Utils;

import java.util.logging.Logger;


/**
 * Drag controller for mobile devices
 * 
 */
public class DragControllerMobile extends DragController {

	private static final Logger LOGGER = Logger.getLogger(DragControllerMobile.class.getName());

	protected Element touchTarget = null;
	private GhostClickPreventer ghostClickPreventer;


	/**
	 * Default constructor.
	 */
	public DragControllerMobile() {
		super();
		ghostClickPreventer = new GhostClickPreventer();
		LOGGER.info("New DragController-Mobile instance created");
	}



	@Override
	protected void registerEvents() {
		super.registerEvents();
		if (getDragStartListener() == null) {
			setDragStartListener(Utils.addEventListener(getSource().getElement(), "touchstart", true, this));
			setDragMoveListener(Utils.addEventListener(getSource().getElement(), "touchmove", true, this));
			setDragEndListener(Utils.addEventListener(getSource().getElement(), "touchend", true, this));
		}
	}



	@Override
	protected void unregisterEvents() {
		super.unregisterEvents();
		if (getDragStartListener() != null) {
			Utils.removeEventListener(getSource().getElement(), "touchstart", true, getDragStartListener());
			Utils.removeEventListener(getSource().getElement(), "touchmove", true, getDragMoveListener());
			Utils.removeEventListener(getSource().getElement(), "touchend", true, getDragEndListener());
			setDragStartListener(null);
			setDragMoveListener(null);
			setDragEndListener(null);
		}
	}



	/**
	 * Will be invoked on touch start.
	 * 
	 * @param e
	 *            event
	 */
	public void onTouchStart(TouchEvent e) {
		EventTarget target = e.getEventTarget();
		boolean preventDefault = true;
		if (Element.is(target)) {
			Element ele = Element.as(target);
			
			// INPUT element will not get focus if default action is prevented.
			if (Utils.isHtmlFormControl(ele)) {
				ele.focus();
				preventDefault = false;
			}
			touchTarget = ele;
		}
		else {
			touchTarget = null;
		}
		
		if (preventDefault) {
			if (!getOptions().isEnableNativeEventPropagation()) {
				e.preventDefault(); // prevent default action of selecting text
				e.stopPropagation();
			}
		}
		// FIXME: for multi-touch platforms.
		onStart(e, new Point(e.touches().get(0).getClientX(), e.touches().get(0).getClientY()));
	}



	/**
	 * Will be invoked on touch move.
	 * 
	 * @param e
	 *            event
	 */
	public void onTouchMove(TouchEvent e) {
		if (!getOptions().isEnableNativeEventPropagation()) {
			e.preventDefault(); // prevent default action of selecting text
			e.stopPropagation();
		}
		onMove(e, new Point(e.touches().get(0).getClientX(), e.touches().get(0).getClientY()));
	}



	/**
	 * Will be invoked on touch end.
	 * 
	 * @param e
	 *            event
	 */
	public void onTouchEnd(TouchEvent e) {
		if (!getOptions().isEnableNativeEventPropagation()) {
			e.preventDefault(); // prevent default action of selecting text
			e.stopPropagation();
		}
		
		if ((!isNextClickSuppressed()) && (touchTarget != null)) {
			fireClick(touchTarget);
			ghostClickPreventer.rememberGhostClick(new Point(e.changedTouches()
					.get(0).getClientX(), e.changedTouches().get(0)
					.getClientY()));
		}
		onEnd(e, new Point(e.changedTouches().get(0).getClientX(), e.changedTouches().get(0).getClientY()));
	}



	@Override
	public void onBrowserEvent(Event e) {
		String type = e.getType();
		if (type.equals("touchstart")) {
			onTouchStart((TouchEvent) e);
		} else if (type.equals("touchmove")) {
			onTouchMove((TouchEvent) e);
		} else if (type.equals("touchend")) {
			onTouchEnd((TouchEvent) e);
		} else if (type.equals("click")) {
			if (!ghostClickPreventer.preventGhostClick(e, new Point(e.getClientX(), e.getClientY()))) {
				super.onBrowserEvent(e);
			}
		} else {
			super.onBrowserEvent(e);
		}
	}



	/**
	 * Will fire a click event on touch end.
	 * 
	 * @param theTarget
	 */
	private native void fireClick(Element theTarget) /*-{
		if (theTarget.nodeType == 3) theTarget = theTarget.parentNode;
		
		var theEvent = $doc.createEvent('MouseEvents');
		theEvent.initEvent('click', true, true);
		theTarget.dispatchEvent(theEvent);
	}-*/;

}
