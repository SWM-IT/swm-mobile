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
package de.swm.commons.mobile.client.theme.components;

import com.google.gwt.resources.client.CssResource;
import de.swm.commons.mobile.client.widgets.ToolbarPanel;



/**
 * Represents a css resource for a {@link ToolbarPanel}.
 * 
 */
public interface ToolbarPanelCss extends CssResource {
	/** CSS Style name. @return style name. **/
	@ClassName("toolbarPanel")
	public String toolbarPanel();



	/** CSS Style name. @return style name. **/
	@ClassName("selected")
	public String selected();


	/** CSS Style name. @return style name. **/
	@ClassName("badge")
	public String badge();
	
	

	/** CSS Style name. @return style name. **/
	@ClassName("gwt-Image")
	public String gwtImage();

}
