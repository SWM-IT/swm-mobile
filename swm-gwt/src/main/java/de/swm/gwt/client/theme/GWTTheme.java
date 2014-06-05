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
package de.swm.gwt.client.theme;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Defines a theme for an gwt application. A schema defines all resources (css, etc.) for a specific plattform.
 *
 * @author wiese.daniel <br>
 * copyright (C) 2010-14, SWM Services GmbH
 */
public interface GWTTheme {

	/**
	 * Returns the css styles of a concrete theme.
	 *
	 * @return css styles
	 */
	GWTCssBundle getCssBundle();

	/**
	 * Returns the images of a concrete theme.
	 *
	 * @return images
	 */
	GWTImageBundle getImageBundle();

	/**
	 * Defines a application specific css ressoruce.
	 * @return the application specific css resource
	 */
	<T extends CssResource> T getApplicationCss();

	/**
	 * Defines a application specific ClientBundle (images, etc.).
	 * @return the application specific ClientBundle
	 */
	<T extends ClientBundle> T getApplicationImages();
}
