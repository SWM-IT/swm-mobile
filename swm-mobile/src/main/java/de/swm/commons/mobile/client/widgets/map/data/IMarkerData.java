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
package de.swm.commons.mobile.client.widgets.map.data;

import com.google.gwt.resources.client.ImageResource;
import de.swm.commons.mobile.client.widgets.map.IMapView.MarkerLayer;

/**
 * Inteface which defines a marker that will be displayed on the map.
 * @author wiese.daniel, wimmel.guido
 * <br>copyright (C) 2012, SWM Services GmbH
 *
 */
public interface IMarkerData {
	
	/**
	 * Returns the GEO Position of the station. 
	 * @return the geo position
	 */
	LatLng getPos();
	
	/**
	 * Returns the icon to be displayed inside the marker.
	 * 
	 * @return the marker icon
	 */
	ImageResource getMarkerIcon();
	
	/**
	 * Returns the layer on which the marker is to be displayed.
	 * 
	 * @return the marker layer
	 */
	MarkerLayer getMarkerLayer();

}
