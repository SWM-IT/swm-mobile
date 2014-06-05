package de.swm.commons.mobile.client.widgets.map;

import com.google.gwt.resources.client.ImageResource;

/**
 * Defines image resources of a map.
 * <p/>
 * Old Ressources:
 *
 *
 * @Source("images/map/pin_red.png") ImageResource mapPin_red();
 * @Source("images/map/station_pin_neutral.png") ImageResource mapStation_pin_normal();
 * @Source("images/map/station_pin_target.png") ImageResource mapStation_pin_target();
 * @Source("images/map/station_pin_origin.png") ImageResource mapStation_pin_origin();
 * @Source("images/map/go_to_station.png") ImageResource mapPopoupInnerImageOriginStation();
 * @Source("images/map/checkered_flag_32.png") ImageResource  mapPopoupInnerImageTargetStation();
 */
public interface IMapResources {


	/**
	 * Default marker image.
	 *
	 * @return the image representation, see {@link com.google.gwt.resources.client.ImageResource}
	 */
	ImageResource markerIconDefault();


	/**
	 * Map ressource.
	 * Used to be: mapPin_red
	 *
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource mapPinOwnPosition();


}
