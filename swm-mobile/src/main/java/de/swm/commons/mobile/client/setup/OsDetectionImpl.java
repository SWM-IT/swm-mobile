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

import com.google.gwt.user.client.Window;
import de.swm.commons.mobile.client.utils.Utils;

import java.util.logging.Logger;


/**
 * The {@link OsDetection} implementation can either be generated be GWT Compiler using code>OSDetectionGenerator</code>
 * or this implementation can be used. GWT Generation blows up the permutation Rate by factor 7.
 * 
 */
public class OsDetectionImpl implements OsDetection {

	private static final Logger LOGGER = Logger.getLogger(OsDetectionImpl.class.getName());


	private final String mmobileProperty;



	/**
	 * Default constructor.
	 */
	public OsDetectionImpl() {
		// get value of mmobile.os
		mmobileProperty = parseOs();
		LOGGER.info("Mobile-System: " + mmobileProperty);
	}



	/**
	 * Default constructor.
	 * 
	 * @param agent
	 *            the browsers user agent.
	 */
	public OsDetectionImpl(String agent) {
		// get value of mmobile.os
		mmobileProperty = parseOs(agent);
	}



	/**
	 * This method must be equivalent to the property-provider code in m-module.gwt.xml.
	 * 
	 * @return the OS.
	 */
	private String parseOs() {
		String userAgent = Window.Navigator.getUserAgent();
		LOGGER.info("User Agent: " + userAgent);
		return parseOs(userAgent);
	}



	/**
	 * This method must be equivalent to the property-provider code in m-module.gwt.xml.
	 * 
	 * @param myUa
	 *            user agent
	 * @return the OS.
	 */
	private String parseOs(final String myUa) {
		final String ua = myUa.toLowerCase();
		if (ua.indexOf("android") != -1) {
			if (ua.indexOf("mobile") != -1) {
				return "android";
			} else {
				return "android_tablet";
			}

		}

		if (ua.indexOf("ipad") != -1) {
			return "ipad";
		}

		if (ua.indexOf("iphone") != -1) {
			if (Utils.getDevicePixelRatio() >= 2) {
				return "retina";
			}
			return "iphone";
		}

		if (ua.indexOf("blackberry") != -1) {
			return "blackberry";
		}

		return "desktop";

	}



	@Override
	public boolean isAndroid() {
		return isAndroidTablet() || isAndroidPhone();
	}



	@Override
	public boolean isIPhone() {
		return mmobileProperty.equals("iphone") || mmobileProperty.equals("retina");
	}



	@Override
	public boolean isIPad() {
		return mmobileProperty.equals("ipad");
	}



	@Override
	public boolean isIOs() {
		return isIPhone() || isIPad();
	}



	public boolean isIOS5() {
		return ((isIPhone() || isIPad()) && 
				(Window.Navigator.getUserAgent().toLowerCase().indexOf("os 5") != -1));

	}

	public boolean isIOS6() {
		return ((isIPhone() || isIPad()) &&
				(Window.Navigator.getUserAgent().toLowerCase().indexOf("os 6") != -1));

	}

	public boolean isIOS7() {
		return ((isIPhone() || isIPad()) &&
				(Window.Navigator.getUserAgent().toLowerCase().indexOf("os 7") != -1));

	}



	@Override
	public boolean isRetina() {
		return mmobileProperty.equals("retina");
	}



	@Override
	public boolean isDesktop() {
		return mmobileProperty.equals("desktop");
	}



	@Override
	public boolean isTablet() {
		return isDesktop() || isIPad() || isAndroidTablet();
	}



	@Override
	public boolean isAndroidTablet() {
		return mmobileProperty.equals("android_tablet");
	}



	@Override
	public boolean isAndroidPhone() {
		return mmobileProperty.equals("android");
	}



	@Override
	public boolean isPhone() {
		return isIPhone() || isAndroidPhone() || isBlackBerry();
	}



	@Override
	public boolean isBlackBerry() {
		return mmobileProperty.equals("blackberry");
	}
	
	@Override
	public boolean isWebkit() {
		// same check as GWT performs for user agent "safari" (see GWT class UserAgentPropertyGenerator)
		return Window.Navigator.getUserAgent().toLowerCase().contains("webkit"); 
	}


}
