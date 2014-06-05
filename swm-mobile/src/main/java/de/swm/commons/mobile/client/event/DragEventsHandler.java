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
 * Defines event handler for drag events.
 * 
 */
public interface DragEventsHandler extends EventHandler {

	/**
	 * When the drag starts.
	 * 
	 * @param e
	 *            drag event
	 */
	void onDragStart(DragEvent e);



	/**
	 * When the drag is ongoing.
	 * 
	 * @param e
	 *            drag event
	 */
	void onDragMove(DragEvent e);



	/**
	 * When the drag ends
	 * 
	 * @param e
	 *            drag event
	 */
	void onDragEnd(DragEvent e);



	/**
	 * The element
	 * 
	 * @return the element.
	 */
	Element getElement();
}
