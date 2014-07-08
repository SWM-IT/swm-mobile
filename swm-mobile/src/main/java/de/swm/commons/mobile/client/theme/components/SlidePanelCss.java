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
import de.swm.commons.mobile.client.widgets.SlidePanel;



/**
 * Represents a css resource for a {@link SlidePanel}. <br>
 * 
 */
public interface SlidePanelCss extends CssResource {
	/** CSS Style name. @return style name. **/
	@ClassName("slide")
	public String slide();



	/** CSS Style name. @return style name. **/
	@ClassName("slidePanel")
	public String slidePanel();

}
