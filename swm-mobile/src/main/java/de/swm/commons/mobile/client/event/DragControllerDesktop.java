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
import de.swm.commons.mobile.client.utils.Point;
import de.swm.commons.mobile.client.utils.Utils;

import java.util.logging.Logger;


/**
 * Drag controller for desktop devices.
 * 
 */
public class DragControllerDesktop extends DragController {

	private static final Logger LOGGER = Logger.getLogger(DragControllerDesktop.class.getName());

	/**
	 * Default constructor.
	 */
	public DragControllerDesktop() {
		super();
		LOGGER.info("New DragController-Desktop instance created");
	}



	@Override
	protected void registerEvents() {
		super.registerEvents();
		if (getDragStartListener() == null) {
			setDragStartListener(Utils.addEventListener(getSource().getElement(), "mousedown", true, this));
			setDragMoveListener(Utils.addEventListener(getSource().getElement(), "mousemove", true, this));
			setDragEndListener(Utils.addEventListener(getSource().getElement(), "mouseup", true, this));
		}
	}



	@Override
	protected void unregisterEvents() {
		super.unregisterEvents();
		if (getDragStartListener() != null) {
			Utils.removeEventListener(getSource().getElement(), "mousedown", true, getDragStartListener());
			Utils.removeEventListener(getSource().getElement(), "mousemove", true, getDragMoveListener());
			Utils.removeEventListener(getSource().getElement(), "mouseup", true, getDragEndListener());
			setDragStartListener(null);
			setDragMoveListener(null);
			setDragEndListener(null);
		}
	}



	/**
	 * Will be invoked on Mouse down event.
	 * 
	 * @param e
	 *            event
	 */
	public void onMouseDown(Event e) {
		EventTarget target = e.getEventTarget();
		boolean preventDefault = true;
		if (Element.is(target)) {
			Element ele = Element.as(target);
			// INPUT element will not get focus if default action is prevented.
			if (Utils.isHtmlFormControl(ele)) {
				ele.focus();
				preventDefault = false;
			}
		}
		if (preventDefault) {
			if (!getOptions().isEnableNativeEventPropagation()) {
				e.preventDefault(); // prevent default action of selecting text
				e.stopPropagation();
			}
			onStart(e, new Point(e.getClientX(), e.getClientY()));
		}
	}



	/**
	 * Will be invoked on Mouse move event.
	 * 
	 * @param e
	 *            event
	 */
	public void onMouseMove(Event e) {
		if (!getOptions().isEnableNativeEventPropagation()) {
			e.preventDefault(); // prevent default action of selecting text
			e.stopPropagation();
		}
		onMove(e, new Point(e.getClientX(), e.getClientY()));
	}



	/**
	 * Will be invoked on Mouse up event.
	 * @param e event.
	 */
	public void onMouseUp(Event e) {
		if (!getOptions().isEnableNativeEventPropagation()) {
			e.preventDefault(); // prevent default action of selecting text
			e.stopPropagation();
		}
		onEnd(e, new Point(e.getClientX(), e.getClientY()));
	}



	@Override
	public void onBrowserEvent(Event e) {
		String type = e.getType();
		if (type.equals("mousedown")) {
			onMouseDown(e);
		} else if (type.equals("mousemove")) {
			onMouseMove(e);
		} else if (type.equals("mouseup")) {
			onMouseUp(e);
		} else {
			super.onBrowserEvent(e);
		}
	}
}
