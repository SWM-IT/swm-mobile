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
 * Defines a Drag event.
 * 
 */
public class DragEvent {

	/**
	 * States of a Drag.
	 * 
	 * 
	 *         
	 * 
	 */
	public enum Type {
		/** Drag state. **/
		Start,
		/** Drag state. **/
		Move,
		/** Drag state. **/
		End
	}

	private final double x, y;
	private final double offsetX, offsetY;
	private Event myNnativeEvent;
	private boolean myStopPropagation = false;
	private Type myType;



	/**
	 * Default constructor.
	 * 
	 * @param nativeEvent
	 *            .
	 * @param type
	 *            .
	 * @param x
	 *            .
	 * @param y
	 *            .
	 * @param offsetX
	 *            .
	 * @param offsetY
	 *            .
	 */
	public DragEvent(Event nativeEvent, Type type, double x, double y, double offsetX, double offsetY) {
		myNnativeEvent = nativeEvent;
		myType = type;
		this.x = x;
		this.y = y;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}



	/**
	 * Will stop the event propagation.
	 */
	public void stopPropagation() {
		myNnativeEvent.stopPropagation();
		myStopPropagation = true;
	}



	public boolean getStopPropagation() {
		return myStopPropagation;
	}



	public Event getNativeEvent() {
		return myNnativeEvent;
	}



	/**
	 * Returns the x.
	 * 
	 * @return the x
	 */
	public double getX() {
		return x;
	}



	/**
	 * Returns the y.
	 * 
	 * @return the y
	 */
	public double getY() {
		return y;
	}



	/**
	 * Returns the offsetX.
	 * 
	 * @return the offsetX
	 */
	public double getOffsetX() {
		return offsetX;
	}



	/**
	 * Returns the offsetY.
	 * 
	 * @return the offsetY
	 */
	public double getOffsetY() {
		return offsetY;
	}



	/**
	 * Returns the myNnativeEvent.
	 * 
	 * @return the myNnativeEvent
	 */
	public Event getMyNnativeEvent() {
		return myNnativeEvent;
	}



	/**
	 * Returns the myStopPropagation.
	 * 
	 * @return the myStopPropagation
	 */
	public boolean isMyStopPropagation() {
		return myStopPropagation;
	}



	/**
	 * Returns the myType.
	 * 
	 * @return the myType
	 */
	public Type getMyType() {
		return myType;
	}



	/**
	 * Will dispatch this event.
	 * 
	 * @param handler
	 *            handler
	 */
	public void dispatch(DragEventsHandler handler) {
		switch (myType) {
			case Start:
				handler.onDragStart(this);
				break;
			case Move:
				handler.onDragMove(this);
				break;
			case End:
				handler.onDragEnd(this);
				break;
		}
	}

}
