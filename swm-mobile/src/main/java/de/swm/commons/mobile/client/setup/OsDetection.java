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

/**
 * This interface provides information on which os platform swm-mobile is currently running.
 * 
 */
public interface OsDetection {

	/**
	 * Are we running on an Android device
	 * 
	 * @return true if running on Android, otherwise false
	 */
	boolean isAndroid();



	/**
	 * Returns true if Ios5 is detected.
	 * 
	 * @return true if ios5
	 */
	boolean isIOS5();

	/**
	 * Returns true if Ios6 is detected.
	 *
	 * @return true if ios6
	 */
	boolean isIOS6();

	/**
	 * Returns true if Ios7 is detected.
	 *
	 * @return true if ios7
	 */
	boolean isIOS7();



	/**
	 * Are we running on an iphone or ipod touch
	 * 
	 * @return true if running on iphone or ipod touch, otherwise false
	 */
	boolean isIPhone();



	/**
	 * Are we running on an ipad
	 * 
	 * @return true if running on ipad, otherwise false
	 */
	boolean isIPad();



	/**
	 * Are we running on an ios device
	 * 
	 * @return true if running on ios device, otherwise false
	 */
	boolean isIOs();



	/**
	 * Are we running on the iphone retina display
	 * 
	 * @return true if runnning on retina display
	 */
	public boolean isRetina();



	/**
	 * Are we running in a desktop browser (chrome, safari or similiar)
	 * 
	 * @return true if running on desktop browser
	 */
	boolean isDesktop();



	/**
	 * Are we running on a tablet device
	 * 
	 * @return true if we are on a tablet device (ipad, android tablet, desktop), otherwise false
	 */
	boolean isTablet();



	/**
	 * Are we running on an android tablet
	 * 
	 * @return true if we are running on an android tablet, otherwise false
	 */
	boolean isAndroidTablet();



	/**
	 * Are we running on an android phone.
	 * 
	 * @return true if we are running on an android phone, otherwise false
	 */
	public boolean isAndroidPhone();



	/**
	 * Are we running on a phone.
	 * 
	 * @return true if we are running on any kind of phone (iphone, ipod touch, android phone, blackberry), otherwise
	 *         false
	 */
	boolean isPhone();



	/**
	 * Are we running on a blackberry device.
	 * 
	 * @return true if running on a blackberry device, otherwise false
	 */
	boolean isBlackBerry();
	
	/**
	 * Are we running on a webkit device/browser.
	 * 
	 * @return true if running on a webkit device, otherwise false
	 */
	boolean isWebkit();
}
