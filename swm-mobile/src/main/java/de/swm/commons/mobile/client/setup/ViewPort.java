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
package de.swm.commons.mobile.client.setup;

import de.swm.commons.mobile.client.SWMMobile;

/**
 * Defines the viewport inside the HTML Page.
 * <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
 */
public class ViewPort {

	private String width;
	private String height;

	private double initialScale = -1;
	private double minimumScale = -1;
	private double maximumScale = -1;
	private boolean userScaleAble = false;

	private String targetDensity;

	/**
	 * Set the width of the viewport
	 *
	 * @param value the width in px
	 * @return the viewport instance
	 */
	public ViewPort setWidth(int value) {
		this.width = "" + value;
		return this;
	}

	/**
	 * Set the height of the viewport
	 *
	 * @param value the height in px
	 * @return the viewport instance
	 */
	public ViewPort setHeight(int value) {
		this.height = "" + value;
		return this;
	}

	/**
	 * Set width to device width
	 * <p/>
	 * Most common for most apps
	 *
	 * @return the viewport instance
	 */
	public ViewPort setWidthToDeviceWidth() {
		this.width = "device-width";
		return this;
	}

	/**
	 * Set the height to device height
	 *
	 * @return the viewport instance
	 */
	public ViewPort setHeightToDeviceHeight() {
		this.height = "device-height";
		return this;
	}

	/**
	 * <p>
	 * android only
	 * </p>
	 * set the target density in dpi
	 *
	 * @param value the target density in dpi
	 * @return the viewport instance
	 */
	public ViewPort setTargetDensity(int value) {
		targetDensity = "" + value;
		return this;
	}

	/**
	 * <p>
	 * android only
	 * </p>
	 * <p/>
	 * set the target density
	 *
	 * @param d the density to use
	 * @return the viewport instance
	 */
	public ViewPort setTargetDensity(Density d) {
		targetDensity = d.getValue();
		return this;
	}

	/**
	 * Should the viewport be scalable by the user
	 *
	 * @param userScaleAble ture to allow scaling
	 * @return the viewport instance
	 */
	public ViewPort setUserScaleAble(boolean userScaleAble) {
		this.userScaleAble = userScaleAble;
		return this;
	}

	/**
	 * set the minimum scaling of the viewport
	 *
	 * @param minimumScale the scale to use
	 * @return the viewport instance
	 */
	public ViewPort setMinimumScale(double minimumScale) {
		this.minimumScale = minimumScale;
		return this;
	}

	/**
	 * Set the maximum scale of the viewport
	 *
	 * @param maximumScale the scale to use
	 * @return the viewport instance
	 */
	public ViewPort setMaximumScale(double maximumScale) {
		this.maximumScale = maximumScale;
		return this;
	}

	/**
	 * set the initial scale of the viewport
	 *
	 * @param initialScale the scale to use
	 * @return the viewport instance
	 */
	public ViewPort setInitialScale(double initialScale) {
		this.initialScale = initialScale;
		return this;
	}

	/**
	 * get the content for the viewport meta tag
	 *
	 * @return content of the viewport meta tag
	 */
	public String getContent() {
		StringBuilder buffer = new StringBuilder();

		// initial scale
		if (initialScale > -1) {
			buffer.append("initial-scale=").append(numberToString(initialScale));
		}
		// minimum scale
		if (minimumScale > -1) {
			buffer.append(",minimum-scale=").append(numberToString(minimumScale));
		}
		if (maximumScale > -1) {
			// maximum scale
			buffer.append(",maximum-scale=").append(numberToString(maximumScale));
		}

		// width
		if (width != null) {
			buffer.append(",width=").append(width);
		}

		// height
		if (height != null) {
			buffer.append(",height=").append(height);
		}

		// user scaleable
		if (!userScaleAble) {
			buffer.append(",user-scalable=no");
		}
		if (targetDensity != null && SWMMobile.getOsDetection().isAndroid()) {
			buffer.append(",target-densityDpi=").append(targetDensity);
		}
		return buffer.toString();
	}


	public String numberToString(double number) {
		if (number % 1 == 0) {
			return String.valueOf((int) number);
		}
		return String.valueOf(number);
	}

}