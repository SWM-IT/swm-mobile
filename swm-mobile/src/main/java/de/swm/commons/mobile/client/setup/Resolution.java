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
package de.swm.commons.mobile.client.setup;

/**
 * Defines the resolution aspect ratio.
 * 
 */
public enum Resolution {

	/**
	 * Resulution.
	 */
	HVGA(2),
	/**
	 * Resulution.
	 */
	WVGA(1.5),
	/**
	 * Resulution.
	 */
	QVGA(0.75);

	private final double ratio;



	/**
	 * Enum constructor.
	 * 
	 * @param ratio
	 *            ratio
	 */
	private Resolution(double ratio) {
		this.ratio = ratio;
	}



	/**
	 * Returns the ratio.
	 * 
	 * @return the ratio
	 */
	public double getRatio() {
		return ratio;
	}

}
