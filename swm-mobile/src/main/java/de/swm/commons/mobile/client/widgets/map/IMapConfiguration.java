package de.swm.commons.mobile.client.widgets.map;

import de.swm.commons.mobile.client.widgets.map.data.Bounds;
import de.swm.commons.mobile.client.widgets.map.data.LatLng;

/**
 * Configuation settings of the map.
 * 
 * @author wiese.daniel, wimmel.guido <br>
 *         copyright (C) 2012, SWM Services GmbH
 * 
 */
public interface IMapConfiguration {
	
	/**
	 * Initial zoom level.
	 * 
	 * @return initial zoom level
	 */
	int getMapInitialZoom();
	
	/**
	 * Default map position.
	 * 
	 * @return default map position (projection EPSG:4326)
	 */
	LatLng getDefaultPos();
	
	/**
	 * Should the map support attribution? (display of copyright notice for layers).
	 * 
	 * @return {code}true{code} if attribution should be supported, {code}false{code} otherwise
	 */
	boolean isAttributionSupported();
	
	/**
	 * Restrict map panning to the given bounds.
	 * 
	 * @return bounds to restrict panning to (projection EPSG:4326); {code}null{code} if no restriction is to be applied.
	 */
	Bounds restrictedExtent();
}
