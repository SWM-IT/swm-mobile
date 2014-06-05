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
package de.swm.gwt.client.theme.base;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import de.swm.gwt.client.theme.GWTImageBundle;


/**
 * Defines images used inside the framework (in an BaseStyle) Details see {@link GWTImageBundle}.
 * @author wiese.daniel <br>
 * copyright (C) 2010-14, SWM Services GmbH
 */
public interface BaseGWTImageRessources extends ClientBundle, GWTImageBundle {


	@Override
	@Source("images/information.png")
	ImageResource information();

	@Override
	@Source("images/loading.gif")
	ImageResource loading();

	@Override
	@Source("images/logo-swm.gif")
	ImageResource swmLogo();

}
