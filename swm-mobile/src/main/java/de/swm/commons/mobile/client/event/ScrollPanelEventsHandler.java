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

/**
 * This class is handling the events on scrolling to top or bottom of a scroll panel.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public interface ScrollPanelEventsHandler extends EventHandler {

	/**
	 * Fires if top boundary will be exceeded.
	 *
	 * @param event SwipeEvent.
	 */
	void onTop(DragEvent event);


	/**
	 * Fires if bottom boundary will be exceeded.
	 *
	 * @param event SwipeEvent.
	 */
	void onBottom(DragEvent event);

}
