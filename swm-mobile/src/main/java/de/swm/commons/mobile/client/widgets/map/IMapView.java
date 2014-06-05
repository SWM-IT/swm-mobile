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

import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.widgets.map.data.IMarkerData;
import de.swm.commons.mobile.client.widgets.map.data.LatLng;
import de.swm.commons.mobile.client.widgets.map.handlers.IMapMoveHandler;
import de.swm.commons.mobile.client.widgets.map.handlers.IMapZoomHandler;
import de.swm.commons.mobile.client.widgets.map.handlers.IMarkerSelectedHandler;
import de.swm.commons.mobile.client.widgets.map.impl.MarkerPopup;
import org.gwtopenmaps.openlayers.client.event.MapClickListener;

/**
 * Map View.
 * @author wiese.daniel, wimmel.guido
 * <br>copyright (C) 2012, SWM Services GmbH
 *
 *
 */
public interface IMapView {


	/**
	 * Enumeration of possible marker layers.
	 */
	public enum MarkerLayer {
		PRIMARY(0),
		SECONDARY(1);
		
		private int index;
		
		private MarkerLayer(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return index;
		}
	}
	
	/**
	 * Map will ask the controller for a new layer.
	 */
	public void changeBaseLayer();

	/**
	 * Hides all visible popups.
	 */
	void hideAllPopupMarker();

	/**
	 * Returns the maps as a Widget.
	 * @return the map as widget.
	 */
	Widget asWidget();


	
	/**
	 * Display markers on the map. Markers that are already displayed (with same marker data) are not changed.
	 * Markers that are not yet displayed are added to the map. All other markers are removed.
	 * 
	 * @param markers collection of markers
	 */
	void displayMarkers(Collection<? extends IMarkerData> markers);
	
	/**
	 * Displays a route (e.g. of a bus) on the map
	 * @param points the points the route consists of
	 */
	void displayRoute(List<? extends LatLng> points);

	/**
	 * Clears a route (e.g. of a bus) on the map
	 */
	void clearRoute();

	/**
	 * Centers to the default position which is currently the city center of Munich.
	 */
	void centerToDefaultPos();
	
	/**
	 * Centers the map to the passed position
	 * @param pos the position to center
	 */
	void centerPosition(LatLng pos);
	
	/**
	 * Centers the map to the passed position
	 * @param pos the position to center
	 * @param zoom the zoom leve
	 */
	void centerPosition(LatLng pos, int zoom);
	
	/**
	 * Updated the current potions.
	 * @param pos the current pos
	 */
	void setCurrentPosition(LatLng pos);
	
	/**
	 * Set visibility of a marker layer.
	 * 
	 * @param markerLayer marker layer for which the visibility is to be set
	 * @param isVisible true if marker layer should be visible
	 */
	void setMarkerLayerVisibility(MarkerLayer markerLayer, boolean isVisible);
	
	
	/**
	 * Adds a map move handler.
	 * @param handler the handler
	 */
	void setMapMoveHandler(IMapMoveHandler handler);
	
	/**
	 * Add a map zoom handler
	 * @param handler the handler
	 */
	void setMapZoomHandler(IMapZoomHandler handler);

	/**
	 * Add a map click Handler
	 * @param mapClickListener map click Hanlder.
	 */
	void setMapClickHanlder(MapClickListener mapClickListener);

	/**
	 * Add a handler for selection of a marker.
	 * 
	 * @param handler the handler
	 */
	void setMarkerSelectedHandler(IMarkerSelectedHandler handler);

	/**
	 * Display a marker on the map. No effect if marker is already displayed.
	 * 
	 * @param markerData marker to be displayed
	 */
	void displayMarker(IMarkerData markerData);

	/**
	 * Remove marker from the map. No effect if marker is not displayed.
	 * 
	 * @param markerData marker to be removed
	 */
	void removeMarker(IMarkerData markerData);

	/**
	 * Create a marker popup.
	 * 
	 * @param markerData marker for which the popup is to be created.
	 * @return marker popup
	 */
	MarkerPopup createMarkerPopup(IMarkerData markerData);

	/**
	 * Gets a previously created marker popup.
	 * 
	 * @param markerData marker for which the popup was created.
	 * @return marker popup
	 */
	MarkerPopup getMarkerPopup(IMarkerData markerData);

	/**
	 * Returns the current zoom level of the map.
	 * @return the current zoom level.
	 */
	int getZoom();

	/**
	 * Sets the zoom level of the map.
	 *
	 * @param zoom the zoom level to be set.
	 */
	void setZoom(int zoom);

}