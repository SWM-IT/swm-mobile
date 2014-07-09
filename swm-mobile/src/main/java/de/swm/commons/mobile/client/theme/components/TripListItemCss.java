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
import de.swm.commons.mobile.client.widgets.accordion.AccordionPanel;



/**
 * Represents a css resource for a {@link AccordionPanel}. <br>
 * 
 */
public interface TripListItemCss extends CssResource {

	/** CSS Style name. @return style name. **/
	@ClassName("trip-transport-image")
	public String tripTransportImage();
	
	/** CSS Style name. @return style name. **/
	@ClassName("trip-list-item-title")
	public String tripListItemTitle();
	
	/** CSS Style name. @return style name. **/
	@ClassName("trip-list-item-subtitle")
	public String tripListItemSubtitle();
	
	@ClassName("trip-list-item-sub-subtitle")
	public String tripListItemSubSubtitle();
	
	/** CSS Style name. @return style name. **/
	@ClassName("trip-list-item-time")
	public String tripListItemTime();
	
	/** CSS Style name. @return style name. **/
	@ClassName("trip-punctual")
	public String tripPunctual();
	
	/** CSS Style name. @return style name. **/
	@ClassName("trip-early")
	public String tripEarly();
	
	/** CSS Style name. @return style name. **/
	@ClassName("trip-late")
	public String tripLate();
	
	/** CSS Style name. @return style name. **/
	@ClassName("trip-list-main-panel")
	public String tripListMainPanel();
	
	/** CSS Style name. @return style name. **/
	@ClassName("trip-list-hpanel")
	public String tripHPanel();
	
	@ClassName("trip-list-vpanel")
	public String tripVPanel();
	
	@ClassName("trip-list-title-panel")
	public String tripListTitlePanel();
	
	@ClassName("trip-list-time-panel")
	public String tripListTimePanel();
	
	@ClassName("trip-list-time-label")
	public String tripListTimeLabel();
	

}
