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

/**
 * Hilfklasse um laengen- und breitegrade zusammenzufassen.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 */
public class LatLng {

	private final double latitude;
	private final double longitude;


	/**
	 * Default constructor.
	 *
	 * @param latitude  .
	 * @param longitude .
	 */
	private LatLng(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}


	/**
	 * Factory method.
	 *
	 * @param latitude  .
	 * @param longitude .
	 * @return .
	 */
	public static LatLng newInstance(double latitude, double longitude) {
		return new LatLng(latitude, longitude);
	}


	/**
	 * Returns the latitude.
	 *
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}


	/**
	 * Returns the longitude.
	 *
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LatLng [latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LatLng)) return false;

		LatLng latLng = (LatLng) o;

		if (Double.compare(latLng.latitude, latitude) != 0) return false;
		if (Double.compare(latLng.longitude, longitude) != 0) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = latitude != +0.0d ? (int)latitude : 0L;
		result = (int) (temp ^ (temp >>> 32));
		temp = longitude != +0.0d ? (int)longitude : 0L;
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}
