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

import com.google.gwt.resources.client.CssResource.Shared;

import de.swm.commons.mobile.client.widgets.ScrollPanel;



/**
 * Represents a css resource for {@link ScrollPanel} <br>
 * Uses Ressources from Page (iOS)
 * 
 */
@Shared
public interface ScrollPanelCss extends PageCss {
	/** CSS Style name. @return style name. **/
	@ClassName("scrollPanel")
	public String scrollPanel();

	/** CSS Style name. @return style name. **/
	@ClassName("simpleScrollPanel")
	public String simpleScrollPanel();

	/** CSS Style name. @return style name. **/
	@ClassName("scrollbarRail")
	public String scrollbarRail();

	/** CSS Style name. @return style name. **/
	@ClassName("scrollbarIndicator")
	public String scrollbarIndicator();

	/** CSS Style name. @return style name. **/
	@ClassName("scrollbar")
	public String scrollbar();

	/** CSS Style name. @return style name. **/
	@ClassName("scrollbarTable")
	public String scrollbarTable();

	/** CSS Style name. @return style name. **/
	@ClassName("scrollbarFiller")
	public String scrollbarFiller();

	/** CSS Style name. @return style name. **/
	@ClassName("scrollbarPanel")
	public String scrollbarPanel();
}
