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

import org.gwtopenmaps.openlayers.client.layer.Layer;

import java.util.List;

/**
 * Controller this is going to control the map.
 *
 * @author wiese.daniel
 *         <br>copyright (C) 2012, SWM Services GmbH
 */
public interface IMapController {

	/**
	 * Enables a configuration of the map's base layer outside the framework.
	 *
	 * @return the base layer
	 */
	Layer getBaseLayer();


	/**
	 * Returns the resources (images, i.e) of the map.
	 *
	 * @return map resources
	 */
	IMapResources getResources();
	
	/**
	 * Returns the map configuration.
	 * 
	 * @return map configuration
	 */
	IMapConfiguration getConfiguration();

	/**
	 * After the map view is initialized, it will register itself at the controller.
	 *
	 * @param mapView the map view
	 */
	void setMapView(IMapView mapView);

	/**
	 * Returns null or al list of other layers.
	 * @return  null or list of other layers.
	 */
	List<? extends Layer> getOtherLayers();
}
