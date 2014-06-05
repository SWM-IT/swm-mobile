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
public class ImmutableMarkerData implements IMarkerData {
	
	private final LatLng pos;
	private final ImageResource markerIcon;
	private final MarkerLayer markerLayer;
	
	public ImmutableMarkerData(IMarkerData markerData) {
		this.pos = markerData.getPos();
		this.markerIcon = markerData.getMarkerIcon();
		this.markerLayer = markerData.getMarkerLayer();
	}

	@Override
	public LatLng getPos() {
		return pos;
	}
	
	@Override
	public ImageResource getMarkerIcon() {
		return markerIcon;
	}
	
	@Override
	public MarkerLayer getMarkerLayer() {
		return markerLayer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((markerIcon == null) ? 0 : markerIcon.getSafeUri().hashCode());
		result = prime * result
				+ ((markerLayer == null) ? 0 : markerLayer.hashCode());
		result = prime * result + ((pos == null) ? 0 : pos.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmutableMarkerData other = (ImmutableMarkerData) obj;
		if (markerIcon == null) {
			if (other.markerIcon != null)
				return false;
		} else if (!markerIcon.getSafeUri().equals(other.markerIcon.getSafeUri()))
			return false;
		if (markerLayer != other.markerLayer)
			return false;
		if (pos == null) {
			if (other.pos != null)
				return false;
		} else if (!pos.equals(other.pos))
			return false;
		return true;
	}

}
