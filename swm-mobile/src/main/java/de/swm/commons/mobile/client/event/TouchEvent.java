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

import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Event;



/**
 * Low level the touch event wrapper to use native browser touch events. http://www.html5rocks.com/en/mobile/touch.html
 * 
 */
public class TouchEvent extends Event {

	/**
	 * Default constructor.
	 */
	protected TouchEvent() {
	}



	/**
	 * Changed Touches: a list of fingers involved in the current event. For example, in a touchend event, this will be the finger that
	 * was removed.
	 * 
	 * @return the touch js object
	 */
	public final native JsArray<Touch> changedTouches() /*-{
														return this.changedTouches;
														}-*/;



	/**
	 * Target Touches - a list of fingers on the current DOM element.
	 * 
	 * @return the touch js object
	 */
	public final native JsArray<Touch> targetTouches() /*-{
														return this.targetTouches;
														}-*/;



	/**
	 * All Touches -  a list of all fingers currently on the screen.
	 * 
	 * @return the touch js object
	 */
	public final native JsArray<Touch> touches() /*-{
													return this.touches;
													}-*/;
}
