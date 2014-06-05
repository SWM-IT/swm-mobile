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
 * Representation of a bounding box.
 *
 * @author wimmel.guido <br>
 *         copyright (C) 2012, SWM Services GmbH
 */
public class Bounds {

	private final double minLat;
	private final double maxLat;
	private final double minLon;
	private final double maxLon;

	public Bounds(double minLat, double maxLat, double minLon, double maxLon) {
		this.minLat = minLat;
		this.maxLat = maxLat;
		this.minLon = minLon;
		this.maxLon = maxLon;
	}

	public double getMinLat() {
		return minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public double getMinLon() {
		return minLon;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bounds [latitude=");
		builder.append(minLat);
		builder.append("..");
		builder.append(maxLat);
		builder.append(", longitude=");
		builder.append(minLon);
		builder.append("..");
		builder.append(maxLon);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(maxLat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(maxLon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minLat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minLon);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bounds other = (Bounds) obj;
		if (Double.doubleToLongBits(maxLat) != Double
				.doubleToLongBits(other.maxLat))
			return false;
		if (Double.doubleToLongBits(maxLon) != Double
				.doubleToLongBits(other.maxLon))
			return false;
		if (Double.doubleToLongBits(minLat) != Double
				.doubleToLongBits(other.minLat))
			return false;
		if (Double.doubleToLongBits(minLon) != Double
				.doubleToLongBits(other.minLon))
			return false;
		return true;
	}
	
}
