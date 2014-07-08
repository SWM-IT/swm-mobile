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
package de.swm.commons.mobile.client.theme;

import com.google.gwt.resources.client.ImageResource;
import de.swm.commons.mobile.client.theme.base.BaseSWMMobileImageRessources;



/**
 * Base class for a ClientBundle image bundle implementation. A concrete ClientBudle e.g.
 * {@link BaseSWMMobileImageRessources} represents a Theme instance (including image resources).
 * 
 */
public interface SWMMobileImageBundle {

	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image resources
	 *
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource information();

	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image resources
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource loading();



	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image ressources
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource down();



	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image ressources
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */

	ImageResource arrowdown();



	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image ressources
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource arrowup();

	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image ressources
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource arrowleft();


	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image ressources
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource checkmark();

	/**
	 * Defines one part of the SWMMobile widget system. Contains all Image ressources
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource checkmark_gray();

	
	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource bus();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource bus_small();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource tram();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource tram_small();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource sbahn();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource unknown();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource unknownSmall();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource sbahn_small();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource ubahn();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource ubahn_small();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource walk();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource walk_small();
	
	
	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource boat();



	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource boat_small();
	
	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource cable();
	
	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource cable_small();


	/**
	 * Transportation ressource.
	 * 
	 * @return the image representation, see {@link ImageResource}
	 */
	ImageResource haltestelle();


}
