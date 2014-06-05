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
 * Defines options for event propagation. E.g. google maps needs native event propagation to work.
 * 
 */
public class DragControllerOptions {

	/**
	 * Native event propagation is disabled by default.
	 **/
	private boolean enableNativeEventPropagation = false;

	/**
	 * Radius in which a move event ist still interpreted as click in the end.
	 */
	private int suppressClickRadius = 15;

	/**
	 * The minimum speed of a gesture which is interpreted as swiping.
	 */
	private double minSwipeSpeed = 0.2;

	/**
	 * Lower boundary of the speed of a gesture.
	 */
	private int minSpeed = -8;

	/**
	 * Upper boundary of the speed of a gesture.
	 */
	private int maxSpeed = 8;

	/**
	 * Returns the enableNativeEventPropagation.
	 * 
	 * @return the enableNativeEventPropagation
	 */
	public boolean isEnableNativeEventPropagation() {
		return enableNativeEventPropagation;
	}



	/**
	 * enableNativeEventPropagation the enableNativeEventPropagation to set.
	 * 
	 * @param enableNativeEventPropagation
	 *            the enableNativeEventPropagation to set
	 */
	public void setEnableNativeEventPropagation(boolean enableNativeEventPropagation) {
		this.enableNativeEventPropagation = enableNativeEventPropagation;
	}

	/**
	 * REturns the minimum speed of a gesture which is interpreted as swiping.
	 *
	 * @return the radius.
	 */
	public int getSuppressClickRadius() {
		return suppressClickRadius;
	}

	/**
	 * Sets the minimum speed of a gesture which is interpreted as swiping.
	 *
	 * @param suppressClickRadius the radius.
	 */
	public void setSuppressClickRadius(int suppressClickRadius) {
		this.suppressClickRadius = suppressClickRadius;
	}

	/**
	 * Returns the minimum speed of a gesture which is interpreted as swiping.
	 * @return the speed.
	 */
	public double getMinSwipeSpeed() {
		return minSwipeSpeed;
	}

	/**
	 * Sets the minimum speed of a gesture which is interpreted as swiping.
	 * @param minSwipeSpeed the speed.
	 */
	public void setMinSwipeSpeed(double minSwipeSpeed) {
		this.minSwipeSpeed = minSwipeSpeed;
	}

	/**
	 * Returns the lower boundary of the speed of a gesture.
	 *
	 * @return the speed.
	 */
	public int getMinSpeed() {
		return minSpeed;
	}

	/**
	 * Sets the lower boundary of the speed of a gesture.
	 *
	 * @param minSpeed the speed.
	 */
	public void setMinSpeed(int minSpeed) {
		this.minSpeed = minSpeed;
	}

	/**
	 * Returns the Upper boundary of the speed of a gesture.
	 *
	 * @return the speed.
	 */
	public int getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * Sets the upper boundary of the speed of a gesture.
	 *
	 * @param maxSpeed .
	 */
	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
}
