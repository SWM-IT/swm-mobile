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

import com.google.gwt.user.client.Event;



/**
 * Represents a swipe event.
 * 
 */
@SuppressWarnings("ALL")
public class SwipeEvent {

	/**
	 * Swipe direction.
	 * 
	 * 
	 *         
	 * 
	 */
	public enum Type {
		/** Swipe direction. **/
		Vertical,
		/** Swipe direction. **/
		Horizontal;
	}

	private double mySpeed;
	private Event myNativeEvent;
	private boolean myStopPropagation = false;
	private Type myType;



	/**
	 * Default constructor.
	 * 
	 * @param nativeEvent
	 *            .
	 * @param type
	 *            .
	 * @param speed
	 *            .
	 */
	public SwipeEvent(Event nativeEvent, Type type, double speed) {
		myNativeEvent = nativeEvent;
		myType = type;
		mySpeed = speed;
	}



	/**
	 * Will stop the event propagation.
	 */
	public void stopPropagation() {
		myNativeEvent.stopPropagation();
		myStopPropagation = true;
	}



	public boolean getStopPropagation() {
		return myStopPropagation;
	}



	public double getSpeed() {
		return mySpeed;
	}



	public Event getNativeEvent() {
		return myNativeEvent;
	}



	/**
	 * Will dispatch this event.
	 * 
	 * @param handler
	 *            the event handler.
	 */
	public void dispatch(SwipeEventsHandler handler) {
		switch (myType) {
			case Vertical:
				handler.onSwipeVertical(this);
				break;
			case Horizontal:
				handler.onSwipeHorizontal(this);
		}
	}
}
