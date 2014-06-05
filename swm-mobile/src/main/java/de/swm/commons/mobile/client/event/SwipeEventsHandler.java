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
import com.google.gwt.event.shared.EventHandler;



/**
 * Event handler for swipe events.
 */
public interface SwipeEventsHandler extends EventHandler {

	/**
	 * When the user swipes vertical.
	 * 
	 * @param e
	 *            the swipe event
	 */
	void onSwipeVertical(SwipeEvent e);



	/**
	 * When the user swipes horizontal
	 * 
	 * @param e
	 *            the swipe event.
	 */
	void onSwipeHorizontal(SwipeEvent e);



	/**
	 * The element.
	 * 
	 * @return element.
	 */
	Element getElement();
}
