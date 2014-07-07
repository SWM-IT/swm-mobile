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

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.map.*;
import de.swm.commons.mobile.client.widgets.map.data.IMarkerData;
import de.swm.commons.mobile.client.widgets.map.data.ImmutableMarkerData;
import de.swm.commons.mobile.client.widgets.map.data.LatLng;
import de.swm.commons.mobile.client.widgets.map.handlers.IMapMoveHandler;
import de.swm.commons.mobile.client.widgets.map.handlers.IMapZoomHandler;
import de.swm.commons.mobile.client.widgets.map.handlers.IMarkerSelectedHandler;
import org.gwtopenmaps.openlayers.client.*;
import org.gwtopenmaps.openlayers.client.Map;
import org.gwtopenmaps.openlayers.client.control.Attribution;
import org.gwtopenmaps.openlayers.client.event.MapClickListener;
import org.gwtopenmaps.openlayers.client.event.MapMoveEndListener;
import org.gwtopenmaps.openlayers.client.event.MapZoomListener;
import org.gwtopenmaps.openlayers.client.event.MarkerBrowserEventListener;
import org.gwtopenmaps.openlayers.client.feature.VectorFeature;
import org.gwtopenmaps.openlayers.client.geometry.LineString;
import org.gwtopenmaps.openlayers.client.geometry.Point;
import org.gwtopenmaps.openlayers.client.layer.Layer;
import org.gwtopenmaps.openlayers.client.layer.Markers;
import org.gwtopenmaps.openlayers.client.layer.Vector;
import org.gwtopenmaps.openlayers.client.util.JSObject;

import java.util.*;
import java.util.logging.Logger;

/**
 * Generelles Panel um eine Karte darzustellen.
 *
 * @author vilbig.alexander
 * @author wimmel.guido
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 */
public class MapPanel implements IMapView {

	private static final Logger LOGGER = Logger.getLogger(MapPanel.class.getName());


	private static final String EPSG_4326_PROJECTION = "EPSG:4326";
	private static final Pixel PIXEL_OFFSET = new Pixel(-14, -48);
	private static final double STROKE_WIDTH = 4.0;
	private static final int NUM_ZOOM_LEVELS = 16;
	/**
	 * Open map ID. *
	 */
	private static final String OPEN_LAYER_MAP_WIDGET_ID = "ol-map-widget";
	private static final String ROUTE_LAYER = "Route";
	private static final String PRIMARY_MARKER_LAYER = "Primary";
	private static final String SECONDARY_MARKER_LAYER = "Secondary";
	private static LatLng defaultPosition;

	private final ImageResource defaultMarkerImage;
	private final ImageResource ownPositionMarkerImage;

	private MapWidget mapWidget;
	private Map map;
	private ZoomControlPanel zoomControl;

	private Markers[] markerLayers;
	private java.util.Map<ImmutableMarkerData, Marker> displayedMarkersMap;
	private java.util.Map<ImmutableMarkerData, IMarkerData> originalMarkerDataMap;
	private java.util.Map<ImmutableMarkerData, MarkerPopup> markerPopupsMap;


	private Markers currentPositionMarkerLayer;
	private Vector routeLayer;
	private Marker positionMarker;
	private MarkerPopup ownPositionPopup;
	private IMarkerSelectedHandler markerSelectedHandler;
	private IMapMoveHandler mapMoveHandler;
	private IMapZoomHandler mapZoomHandler;
	private final IMapController controller;
	private Layer osmLayer;
	private boolean isWithZoomControl;

	/**
	 * Default constructor.
	 *
	 * @param controller .
	 */
	@Inject
	public MapPanel(IMapController controller) {
		isWithZoomControl = SWMMobile.getOsDetection().isDesktop();
		this.controller = controller;
		IMapResources resources = controller.getResources();
		IMapConfiguration configuration = (controller.getConfiguration() == null) ?
				new DefaultMapConfiguration() : controller.getConfiguration();
		this.defaultMarkerImage = resources.markerIconDefault();
		this.ownPositionMarkerImage = resources.mapPinOwnPosition();
		defaultPosition = configuration.getDefaultPos();

		MapOptions defaultMapOptions = new MapOptions();
		defaultMapOptions.removeDefaultControls();
		defaultMapOptions.setNumZoomLevels(NUM_ZOOM_LEVELS);
		defaultMapOptions.setProjection(MapPanel.EPSG_4326_PROJECTION);
		// prevent implicit loading of CSS map style; no appropriate API for this in gwt-openlayers
		setPropertyToNull(defaultMapOptions.getJSObject(), "theme");

		mapWidget = new MapWidget("100%", "100%", defaultMapOptions);
		DOM.setElementProperty(mapWidget.getElement(), "id",
				OPEN_LAYER_MAP_WIDGET_ID);
		mapWidget.getElement().getStyle().setPosition(Style.Position.RELATIVE);

		map = mapWidget.getMap();
		osmLayer = controller.getBaseLayer();
		osmLayer.setIsBaseLayer(true);
		map.addLayer(osmLayer);

		List<? extends Layer> otherLayers = controller.getOtherLayers();
		if (otherLayers != null) {
			for (Layer otherLayer : otherLayers) {
				otherLayer.setIsBaseLayer(false);
				map.addLayer(otherLayer);
			}
		}

		if (configuration.restrictedExtent() != null) {
			de.swm.commons.mobile.client.widgets.map.data.Bounds bounds = configuration.restrictedExtent();
			Bounds restrictedExtent = new Bounds(bounds.getMinLon(), bounds.getMinLat(), bounds.getMaxLon(), bounds.getMaxLat());
			Bounds transformedRestrictedExtent = restrictedExtent.transform(new Projection(MapPanel.EPSG_4326_PROJECTION),
					new Projection(map.getProjection()));
			map.setRestrictedExtent(transformedRestrictedExtent);
			GWT.log("Restricted bounds: " + transformedRestrictedExtent.toString());
		}

		LatLng geopos = defaultPosition;
		GWT.log("Center Map: " + geopos);
		LonLat pos = new LonLat(geopos.getLongitude(), geopos.getLatitude());
		pos.transform(MapPanel.EPSG_4326_PROJECTION, map.getProjection());
		map.setCenter(pos, configuration.getMapInitialZoom());

		map.addControl(new TouchNavigation());

		if (configuration.isAttributionSupported()) {
			map.addControl(new Attribution());
		}

		routeLayer = new Vector(ROUTE_LAYER);
		map.addLayer(routeLayer);

		markerLayers = new Markers[2];
		markerLayers[0] = new Markers(PRIMARY_MARKER_LAYER);
		markerLayers[1] = new Markers(SECONDARY_MARKER_LAYER);
		map.addLayer(markerLayers[1]);
		map.addLayer(markerLayers[0]);
		currentPositionMarkerLayer = markerLayers[0];

		displayedMarkersMap = new HashMap<ImmutableMarkerData, Marker>();
		originalMarkerDataMap = new HashMap<ImmutableMarkerData, IMarkerData>();
		markerPopupsMap = new HashMap<ImmutableMarkerData, MarkerPopup>();

		map.addMapZoomListener(new MapZoomListener() {

			@Override
			public void onMapZoom(MapZoomEvent eventObject) {
				if (mapZoomHandler != null) {
					mapZoomHandler.onMapZoomed(map.getZoom());
				}
			}
		});

		map.addMapMoveEndListener(new MapMoveEndListener() {

			@Override
			public void onMapMoveEnd(MapMoveEndEvent eventObject) {
				if (mapMoveHandler != null) {
					LonLat centerPos = map.getCenter();
					centerPos.transform(map.getProjection(),
							MapPanel.EPSG_4326_PROJECTION);
					mapMoveHandler.onMapMoved(LatLng.newInstance(
							centerPos.lat(), centerPos.lon()));
				}
			}
		});

		controller.setMapView(this);
	}

	/**
	 * Sets the default position of the map.
	 *
	 * @param defaultPosition the default position of the map.
	 */
	public static void setDefaultPosition(LatLng defaultPosition) {

		MapPanel.defaultPosition = defaultPosition;
	}

	@Override
	public void changeBaseLayer() {
		map.removeLayer(osmLayer);
		map.removeLayer(markerLayers[1]);
		map.removeLayer(markerLayers[0]);
		Layer osmLayer = controller.getBaseLayer();
		this.osmLayer.setIsBaseLayer(true);
		this.osmLayer = osmLayer;
		map.addLayer(this.osmLayer);
		map.addLayer(markerLayers[1]);
		map.addLayer(markerLayers[0]);

	}

	@Override
	public Widget asWidget() {
		if (zoomControl == null && isWithZoomControl) {
			zoomControl = new ZoomControlPanel(map);
			zoomControl.init();
		}
		return mapWidget;
	}

	@Override
	public void displayMarkers(Collection<? extends IMarkerData> markers) {
		LOGGER.info("displayMarkers called...");

		Set<ImmutableMarkerData> markersToBeRemoved = new HashSet<ImmutableMarkerData>(displayedMarkersMap.keySet());

		for (IMarkerData markerData : markers) {
			ImmutableMarkerData immMarkerData = new ImmutableMarkerData(markerData);

			if (displayedMarkersMap.containsKey(immMarkerData)) {
				// marker is already displayed, do not remove from map
				markersToBeRemoved.remove(immMarkerData);
			} else {
				// marker not yet displayed, add to map
				doAddMarker(immMarkerData, markerData);
			}
		}

		// remove remaining markers
		for (ImmutableMarkerData immMarkerData : markersToBeRemoved) {
			doRemoveMarker(immMarkerData);
		}
	}

	@Override
	public void displayMarker(IMarkerData markerData) {
		ImmutableMarkerData immMarkerData = new ImmutableMarkerData(markerData);
		if (!displayedMarkersMap.containsKey(immMarkerData)) {
			doAddMarker(immMarkerData, markerData);
		}
	}

	@Override
	public void removeMarker(IMarkerData markerData) {
		ImmutableMarkerData immMarkerData = new ImmutableMarkerData(markerData);
		if (displayedMarkersMap.containsKey(immMarkerData)) {
			doRemoveMarker(immMarkerData);
		}
	}

	private void doAddMarker(ImmutableMarkerData immMarkerData, IMarkerData originalMarkerData) {
		Marker marker = createMarker(immMarkerData);
		Markers currentMarkerLayer = markerLayers[immMarkerData.getMarkerLayer().getIndex()];
		currentMarkerLayer.addMarker(marker);
		displayedMarkersMap.put(immMarkerData, marker);
		originalMarkerDataMap.put(immMarkerData, originalMarkerData);
	}

	private void doRemoveMarker(ImmutableMarkerData immMarkerData) {
		Markers currentMarkerLayer = markerLayers[immMarkerData.getMarkerLayer().getIndex()];
		Marker marker = displayedMarkersMap.get(immMarkerData);
		currentMarkerLayer.removeMarker(marker);
		displayedMarkersMap.remove(immMarkerData);
		originalMarkerDataMap.remove(immMarkerData);
		MarkerPopup markerPopup = markerPopupsMap.get(immMarkerData);
		if (markerPopup != null) {
			markerPopup.destroy();
			markerPopupsMap.remove(immMarkerData);
		}
	}


	@Override
	public void displayRoute(List<? extends LatLng> gpsPoints) {

		routeLayer.destroyFeatures();

		Point[] linePoints = new Point[gpsPoints.size()];
		int i = 0;
		for (LatLng point : gpsPoints) {
			LonLat pos = new LonLat(point.getLongitude(), point.getLatitude());
			pos.transform(MapPanel.EPSG_4326_PROJECTION, map.getProjection());
			linePoints[i++] = new Point(pos.lon(), pos.lat());
		}

		if (linePoints.length > 1) {
			LineString line = new LineString(linePoints);
			org.gwtopenmaps.openlayers.client.Style lineStyle = new org.gwtopenmaps.openlayers.client.Style();
			lineStyle.setFillColor("red");
			lineStyle.setStrokeColor("red");
			lineStyle.setStrokeWidth(STROKE_WIDTH);
			VectorFeature lineFeature = new VectorFeature(line, lineStyle);
			routeLayer.addFeature(lineFeature);
			map.panTo(new LonLat(linePoints[0].getX(), linePoints[0].getY()));
		}
	}

	/**
	 * Clears a route (e.g. of a bus) on the map
	 */
	@Override
	public void clearRoute() {
		routeLayer.destroyFeatures();
	}

	@Override
	public void centerPosition(LatLng pos) {
		GWT.log("Center Map: " + pos);
		LonLat position = new LonLat(pos.getLongitude(), pos.getLatitude());
		position.transform(MapPanel.EPSG_4326_PROJECTION, map.getProjection());
		try {
			map.panTo(position);
		} catch (Exception e) {
			GWT.log("Warnung - Ausnahme bei centerPosition: " + e.getMessage());
			LOGGER.info("Warnung - Ausnahme bei centerPosition: "
					+ e.getMessage());
		}
	}

	@Override
	public void centerPosition(LatLng pos, int zoom) {
		GWT.log("Center Map: " + pos + " Zoom: " + zoom);
		LonLat position = new LonLat(pos.getLongitude(), pos.getLatitude());
		position.transform(MapPanel.EPSG_4326_PROJECTION, map.getProjection());
		try {
			map.setCenter(position, zoom);
		} catch (Exception e) {
			GWT.log("Warnung - Ausnahme bei centerPosition: " + e.getMessage());
			LOGGER.info("Warnung - Ausnahme bei centerPosition: "
					+ e.getMessage());
		}
	}

	@Override
	public void centerToDefaultPos() {
		final LatLng geopos = defaultPosition;
		centerPosition(geopos);
	}

	@Override
	public void setCurrentPosition(LatLng pos) {
		LonLat position = new LonLat(pos.getLongitude(), pos.getLatitude());
		position.transform(MapPanel.EPSG_4326_PROJECTION, map.getProjection());
		if (positionMarker != null) {
			currentPositionMarkerLayer.removeMarker(positionMarker);
			positionMarker = null;
		}
		if (positionMarker == null) {
			Size size = new Size(ownPositionMarkerImage.getWidth(),
					ownPositionMarkerImage.getHeight());
			Pixel offset = new Pixel(-(size.getWidth() / 2), -size.getHeight());
			Icon positionIcon = new Icon(ownPositionMarkerImage.getSafeUri()
					.asString(), size, offset);
			positionMarker = new Marker(position, positionIcon);
			String event = "touchstart";
			if (!GWT.isScript()) {
				event = "mousedown";
			}
			positionMarker.addBrowserEventListener(event,
					new MarkerBrowserEventListener() {

						public void onBrowserEvent(
								MarkerBrowserEvent markerBrowserEvent) {
							handlePositionMarker(markerBrowserEvent.getSource());
							positionMarker.getEvents().stop(markerBrowserEvent);
						}

					});
			currentPositionMarkerLayer.addMarker(positionMarker);
		}
	}


	@Override
	public void setMarkerLayerVisibility(MarkerLayer markerLayer,
										 boolean isVisible) {

		Markers currentMarkerLayer = markerLayers[markerLayer.getIndex()];

		if (isVisible && (!currentMarkerLayer.isVisible())) {
			currentMarkerLayer.setIsVisible(true);
		} else if ((!isVisible) && (currentMarkerLayer.isVisible())) {
			currentMarkerLayer.setIsVisible(false);
			clearMarkerPopups();
		}

		currentMarkerLayer.setIsVisible(isVisible);
	}

	private void clearMarkerPopups() {
		for (MarkerPopup p : markerPopupsMap.values()) {
			p.destroy();
		}
		markerPopupsMap.clear();
	}


	private Marker createMarker(final IMarkerData markerData) {
		LonLat position = new LonLat(markerData.getPos().getLongitude(), markerData
				.getPos().getLatitude());
		position.transform(MapPanel.EPSG_4326_PROJECTION, map.getProjection());
		Icon markerIcon = createIcon(markerData.getMarkerIcon());

		Marker result = null;
		try {
			result = new Marker(position, markerIcon);
			String event = "touchstart";
			if (!GWT.isScript()) {
				event = "mousedown";
			}
			result.addBrowserEventListener(event,
					new MarkerBrowserEventListener() {

						public void onBrowserEvent(
								MarkerBrowserEvent markerBrowserEvent) {

							if (markerSelectedHandler != null) {
								IMarkerData originalMarkerData = originalMarkerDataMap.get(markerData);
								markerSelectedHandler.onMarkerSelected(originalMarkerData);
							}
						}

					});
		} catch (Exception e) {
			LOGGER.info("Warning - exception while creating station marker: "
					+ e.getMessage());
		}
		return result;

	}

	private Icon createIcon(ImageResource imageResource) {
		if (imageResource == null) {
			imageResource = defaultMarkerImage;
		}
		Size size = new Size(imageResource.getWidth(), imageResource.getHeight());
		return new Icon(imageResource.getSafeUri().asString(), size, PIXEL_OFFSET);
	}


	/**
	 * Shows a popup for the position marker.
	 *
	 * @param m der marker
	 */
	private void handlePositionMarker(Marker m) {
		if (ownPositionPopup == null) {
			ownPositionPopup = createPositionPopup(m.getLonLat());
		}
		ownPositionPopup.toggleVisibility();
	}

	/**
	 * Erzeugt ein popup fuer einen Positionsmarker.
	 *
	 * @param pos die aktuelle position
	 * @return das popup.
	 */
	private MarkerPopup createPositionPopup(LonLat pos) {
		MarkerPopup result = new MarkerPopup(map, pos, 0,
				-(ownPositionMarkerImage.getHeight() + 2),
				MarkerPopup.Anchor.CENTER_BOTTOM);
		result.getContentArea().add(new Label("Aktuelle Position"));
		return result;
	}

	@Override
	public MarkerPopup createMarkerPopup(IMarkerData markerData) {
		ImmutableMarkerData immMarkerData = new ImmutableMarkerData(markerData);
		if (markerPopupsMap.get(immMarkerData) != null) {
			MarkerPopup markerPopup = markerPopupsMap.get(immMarkerData);
			markerPopup.destroy();
		}

		LonLat position = new LonLat(markerData.getPos().getLongitude(), markerData
				.getPos().getLatitude());
		position.transform(MapPanel.EPSG_4326_PROJECTION, map.getProjection());

		final MarkerPopup result = new MarkerPopup(map, position, 0,
				-((defaultMarkerImage.getHeight()) + 2),
				MarkerPopup.Anchor.CENTER_BOTTOM);
		markerPopupsMap.put(immMarkerData, result);

		return result;
	}

	@Override
	public MarkerPopup getMarkerPopup(IMarkerData markerData) {
		ImmutableMarkerData immMarkerData = new ImmutableMarkerData(markerData);
		return markerPopupsMap.get(immMarkerData);
	}

	@Override
	public void hideAllPopupMarker() {
		for (MarkerPopup popupToHide : markerPopupsMap.values()) {
			popupToHide.hide();
		}
	}


	@Override
	public void setMarkerSelectedHandler(IMarkerSelectedHandler handler) {
		markerSelectedHandler = handler;
	}

	@Override
	public void setMapMoveHandler(IMapMoveHandler handler) {
		mapMoveHandler = handler;
	}

	@Override
	public void setMapZoomHandler(IMapZoomHandler handler) {
		mapZoomHandler = handler;
	}

	@Override
	public void setMapClickHanlder(MapClickListener mapClickListener) {
		map.addMapClickListener(mapClickListener);
	}

	@Override
	public int getZoom() {
		return map.getZoom();
	}

	@Override
	public void setZoom(int zoom) {
		map.zoomTo(zoom);
	}

	private static native void setPropertyToNull(JSObject object, String name) /*-{
        object[name] = null;
    }-*/;

}
