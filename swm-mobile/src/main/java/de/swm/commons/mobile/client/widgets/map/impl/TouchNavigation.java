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
package de.swm.commons.mobile.client.widgets.map.impl;

import org.gwtopenmaps.openlayers.client.control.Control;
import org.gwtopenmaps.openlayers.client.util.JSObject;

/**
 * Creates a touch navigation control.
 * @author wiese.daniel
 *
 */
public class TouchNavigation extends Control {

	/**
	 * Protected constructor.
	 * @param element the map element
	 */
	protected TouchNavigation(JSObject element) {
		super(element);
	}

	/**
	 * Default constructor.
	 */
	public TouchNavigation() {
		this(TouchNavigationImpl.create());
	}
}
