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
import de.swm.commons.mobile.client.widgets.map.IMapView;



/**
 * Represents a css resource for a {@link IMapView}. <br>
 * 
 */
public interface MapCss extends CssResource {

	
	/** CSS Style name. @return style name. **/
	@ClassName("marker-popup-panel-parent")
	public String markerPopupPanelParent();
	
	/** CSS Style name. @return style name. **/
	@ClassName("marker-popup-panel-content")
	public String markerPopupPanelContent();
	
	/** CSS Style name. @return style name. **/
	@ClassName("marker-popup-panel-indicator-down")
	public String markerPopupPanelIndicatorDown();
	
	/** CSS Style name. @return style name. **/
	@ClassName("marker-popup-panel-indicator-up")
	public String markerPopupPanelIndicatorUp();
	
	/** CSS Style name. @return style name. **/
	@ClassName("marker-popup-image")
	public String markerPopupImage();
	
	/** CSS Style name. @return style name. **/
	@ClassName("pressed")
	public String pressed();
	
	/** CSS Style name. @return style name. **/
	@ClassName("zoom-control-panel")
	public String zoomControlPanel();
	
	/** CSS Style name. @return style name. **/
	@ClassName("shadow-top")
	public String shadowTop();
	
	/** CSS Style name. @return style name. **/
	@ClassName("shadow-bottom")
	public String shadowBottom();
		
	/** CSS Style name. @return style name. **/
	@ClassName("gwt-Label")
	public String gwtLabel();
	
	/** CSS Style name. @return style name. **/
	@ClassName("gwt-HTML")
	public String gwtHTML();

}
