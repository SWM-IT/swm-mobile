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
package de.swm.commons.mobile.client.theme.base;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import de.swm.commons.mobile.client.theme.SWMMobileImageBundle;



/**
 * Defines images used inside the framework (in an BaseStyle) Details see {@link SWMMobileImageBundle}.
 * 
 */
public interface BaseSWMMobileImageRessources extends ClientBundle, SWMMobileImageBundle {


	@Override
	@Source("images/information.png")
	ImageResource information();

	@Override
	@Source("images/loading.gif")
	ImageResource loading();

	@Override
	@Source("images/down.png")
	ImageResource down();


	@Override
	@Source("images/arrowdown2.png")
	ImageResource arrowdown();

	@Override
	@Source("images/arrowup2.png")
	ImageResource arrowup();
	
	@Override
	@Source("images/arrowleft.png")
	ImageResource arrowleft();
	
	@Override
	@Source("images/checkmark.png")
	ImageResource checkmark();
	
	@Override
	@Source("images/checkmark_gray.png")
	ImageResource checkmark_gray();
	
	//Transport types
	@Override
	@Source("images/transport/bus.png")
	ImageResource bus();
		
	@Override
	@Source("images/transport/bus_small.png")
	ImageResource bus_small();
		
	@Override
	@Source("images/transport/tram.png")
	ImageResource tram();
		
	@Override
	@Source("images/transport/tram_small.png")
	ImageResource tram_small();

	@Override
	@Source("images/transport/s-bahn.png")
	ImageResource sbahn();
	
	@Override
	@Source("images/transport/unknown.png")
	ImageResource unknown();
	
	@Override
	@Source("images/transport/unknown_small.png")
	ImageResource unknownSmall();
	
	@Override
	@Source("images/transport/boat.png")
	ImageResource boat();

	@Override
	@Source("images/transport/boat_small.png")
	ImageResource boat_small();

	@Override
	@Source("images/transport/s-bahn_small.png")
	ImageResource sbahn_small();
		
	@Override
	@Source("images/transport/u-bahn.png")
	ImageResource ubahn();
		
	@Override
	@Source("images/transport/u-bahn_small.png")
	ImageResource ubahn_small();
		
	@Override
	@Source("images/transport/walk.png")
	ImageResource walk();

	@Override
	@Source("images/transport/walk_small.png")
	ImageResource walk_small();
	
	@Override
	@Source("images/transport/unknown.png")
	ImageResource cable();
	
	@Override
	@Source("images/transport/unknown_small.png")
	ImageResource cable_small();
		
	@Override
	@Source("images/transport/haltestelle.png")
	ImageResource haltestelle();

}
