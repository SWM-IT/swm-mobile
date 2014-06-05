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
package de.swm.commons.mobile.client.utils;

/**
 * Defines a Point on a Screen.
 * 
 */
public class Point {

	private double x, y;



	/**
	 * Default constructor.
	 * 
	 * @param c
	 *            other point
	 */
	public Point(Point c) {
		x = c.x;
		y = c.y;
	}



	/**
	 * Constructor.
	 * 
	 * @param x
	 *            x pos
	 * @param y
	 *            y pos
	 */
	public Point(double x, double y) {
		setPosition(x, y);
	}



	/**
	 * Will change the postion o this point.
	 * 
	 * @param x
	 *            the new x pos
	 * @param y
	 *            the new y pos
	 * @return the this reference
	 */
	public Point setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}



	/**
	 * Will return the x position.
	 * 
	 * @return the x position.
	 */
	public double xPos() {
		return x;
	}



	/**
	 * Will return the y position.
	 * 
	 * @return the y position.
	 */
	public double yPos() {
		return y;
	}



	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Point)) {
			return false;
		}
		Point c = (Point) obj;
		return (x == c.x) && (y == c.y);
	}



	@Override
	public int hashCode() {
		return (int) x ^ (int) y;
	}



	/**
	 * Will subtract a point
	 * 
	 * @param c
	 *            another point
	 * @return this reference
	 */
	public Point minus(Point c) {
		this.x -= c.x;
		this.y -= c.y;
		return this;
	}



	/**
	 * Will add a point.
	 * 
	 * @param c
	 *            the new point
	 * @return this reference
	 */
	public Point plus(Point c) {
		this.x += c.x;
		this.y += c.y;
		return this;
	}



	/**
	 * Will clone the point.
	 * 
	 * @param c
	 *            the point to clone
	 * @return this reference
	 */
	public Point clone(Point c) {
		this.x = c.x;
		this.y = c.y;
		return this;
	}



	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
