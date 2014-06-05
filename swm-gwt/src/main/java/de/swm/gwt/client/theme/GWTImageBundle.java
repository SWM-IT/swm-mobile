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
package de.swm.gwt.client.theme;

import com.google.gwt.resources.client.ImageResource;


/**
 * Base class for a ClientBundle image bundle implementation. A concrete ClientBudle e.g.
 * {@link de.swm.gwt.client.theme.base.BaseGWTImageRessources}
 * represents a Theme instance (including image resources).
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2010-14, SWM Services GmbH
 */
public interface GWTImageBundle {

	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image resources
	 *
	 * @return the image representation, see {@link com.google.gwt.resources.client.ImageResource}
	 */
	ImageResource information();

	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image resources
	 *
	 * @return the image representation, see {@link com.google.gwt.resources.client.ImageResource}
	 */
	ImageResource loading();

	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image resources
	 *
	 * @return the image representation, see {@link com.google.gwt.resources.client.ImageResource}
	 */
	ImageResource swmLogo();

}
