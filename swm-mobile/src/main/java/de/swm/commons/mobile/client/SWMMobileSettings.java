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
package de.swm.commons.mobile.client;

import de.swm.commons.mobile.client.setup.StatusBarStyle;
import de.swm.commons.mobile.client.setup.ViewPort;

/**
 * Enables mobile applications to define settings programatically.
 */
public class SWMMobileSettings {
	
	private ViewPort viewPort;

	private String iconUrl;

	private String startUrl;

	private boolean fullscreen;

	private boolean preventScrolling;

	private boolean disablePhoneNumberDetection;

	private StatusBarStyle statusBarStyle;

	/**
	 * Get the viewport for the mgwt app
	 * 
	 * @return the viewport
	 */
	public ViewPort getViewPort() {
		return viewPort;
	}

	/**
	 * Set the viewport the the mgwt app
	 * 
	 * @param viewPort
	 *            the viewport to use
	 */
	public void setViewPort(ViewPort viewPort) {
		this.viewPort = viewPort;
	}


	/**
	 * The icon url to use on the home screen on ios
	 * 
	 * @return the icon url
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * Set the icon url to use on the home screen on ios
	 * 
	 * @param url
	 *            the url of the icon to use
	 */
	public void setIconUrl(String url) {
		this.iconUrl = url;
	}

	/**
	 * Get the url to the image to use at startup if running on home screen
	 * 
	 * @return the url of the image
	 */
	public String getStartUrl() {
		return startUrl;
	}

	/**
	 * Set the url to the image to use at startup if running on home screen
	 * 
	 * @param startUrl
	 *            the url to use
	 */
	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	/**
	 * Can the app be used in full screen mode
	 * 
	 * @return true if the app can be used in full screen mode
	 */
	public boolean isFullscreen() {
		return fullscreen;
	}

	/**
	 * Can the app be used in full screen
	 * 
	 * @param fullscreen
	 *            true if app can be run in full screen
	 */
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	/**
	 * Should swm-mobile prevent default scrolling behaviour
	 * 
	 * @return true if swm-mobile should prevent default scrolling behaviour
	 */
	public boolean isPreventScrolling() {
		return preventScrolling;
	}

	/**
	 * Should swm-mobile prevent default scrolling behaviour
	 * 
	 * @param preventScrolling
	 *            true if swm-mobile should prevent default scrolling behaviour
	 */
	public void setPreventScrolling(boolean preventScrolling) {
		this.preventScrolling = preventScrolling;
	}

	/**
	 * <p>
	 * ios only
	 * </p>
	 * disable the auto detection of phonenumbers in your app
	 * 
	 * @param disablePhoneNumberDetection
	 *            true to disable
	 */
	public void setDisablePhoneNumberDetection(boolean disablePhoneNumberDetection) {
		this.disablePhoneNumberDetection = disablePhoneNumberDetection;
	}

	/**
	 * 
	 * <p>
	 * ios only
	 * </p>
	 * disable the auto detection of phonenumbers in your app
	 * 
	 * @return true to disable
	 */
	public boolean isDisablePhoneNumberDetection() {
		return disablePhoneNumberDetection;
	}

	/**
	 * <p>
	 * ios only
	 * </p>
	 * 
	 * set the style of the status bar if the app is running in full screen
	 * 
	 * @param statusBarStyle
	 *            the style to use
	 */
	public void setStatusBarStyle(StatusBarStyle statusBarStyle) {
		this.statusBarStyle = statusBarStyle;
	}

	/**
	 * <p>
	 * ios only
	 * </p>
	 * 
	 * get the style of the status bar if the app is running in full screen
	 * 
	 * @return statusBarStyle the style to use
	 */
	public StatusBarStyle getStatusBarStyle() {
		return statusBarStyle;
	}

}
