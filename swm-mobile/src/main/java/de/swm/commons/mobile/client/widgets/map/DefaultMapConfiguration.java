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
package de.swm.commons.mobile.client.widgets.map;

import de.swm.commons.mobile.client.widgets.map.data.Bounds;
import de.swm.commons.mobile.client.widgets.map.data.LatLng;



/**
 * Default configuration settings of the map.
 * 
 * @author wiese.daniel, wimmel.guido <br>
 *         copyright (C) 2012, SWM Services GmbH
 * 
 */
public class DefaultMapConfiguration implements IMapConfiguration {

	private static final double DEFAULT_LATITUDE = 48.137048;
	private static final double DEFAULT_LONGITUDE = 11.575386;
	private static final int MAP_INITIAL_ZOOM = 16; 

	@Override
	public int getMapInitialZoom() {
		return MAP_INITIAL_ZOOM;
	}



	@Override
	public LatLng getDefaultPos() {
		return LatLng.newInstance(DEFAULT_LATITUDE, DEFAULT_LONGITUDE);
	}



	@Override
	public boolean isAttributionSupported() {
		return false;
	}



	@Override
	public Bounds restrictedExtent() {
		return null;
	}
}
