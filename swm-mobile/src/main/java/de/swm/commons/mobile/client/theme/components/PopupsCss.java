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
import de.swm.commons.mobile.client.widgets.LoadingIndicatorPopup;



/**
 * Represents a css resource for a various Popup-Pannels e.g. {@link LoadingIndicatorPopup}. <br>
 * 
 */
public interface PopupsCss extends CssResource {
	/** CSS Style name. @return style name. **/
	@ClassName("datePopup")
	public String datePopup();


	/** CSS Style name. @return style name. **/
	@ClassName("dateGlassPanel")
	public String dateGlassPanel();


	/** CSS Style name. @return style name. **/
	@ClassName("dateValueLabel")
	public String dateValueLabel();


	/** CSS Style name. @return style name. **/
	@ClassName("dateCommandPanel")
	public String dateCommandPanel();
	
	/** CSS Style name. @return style name. **/
	@ClassName("dateRelativeTimeChooserPanel")
	public String dateRelativeTimeChooserPanel();
	
	/** CSS Style name. @return style name. **/
	@ClassName("dateSpinnerPanel")
	public String dateSpinnerPanel();
	
	/** CSS Style name. @return style name. **/
	@ClassName("dateInputPanel")
	public String dateInputPanel();

	/** CSS Style name. @return style name. **/
	@ClassName("dateOkButton")
	public String dateOkButton();


	/** CSS Style name. @return style name. **/
	@ClassName("dateCancelButton")
	public String dateCancelButton();


	/** CSS Style name. @return style name. **/
	@ClassName("loadingIndicatorPopup")
	public String loadingIndicatorPopup();


	/** CSS Style name. @return style name. **/
	@ClassName("loadingIndicatorGlassPanel")
	public String loadingIndicatorGlassPanel();
	
	/** CSS Style name. @return style name. **/
	@ClassName("loadingIndicatorHeader")
	public String loadingIndicatorHeader();
	
	/** CSS Style name. @return style name. **/
	@ClassName("loadingIndicatorSubheader")
	public String loadingIndicatorSubheader();


	/** CSS Style name. @return style name. **/
	@ClassName("commandPopup")
	public String commandPopup();

	/** CSS Style name. @return style name. **/
	@ClassName("simplePopup")
	public String simplePopup();

	/** CSS Style name. @return style name. **/
	@ClassName("simplePopupGlass")
	public String simplePopupGlass();

	/** CSS Style name. @return style name. **/
	@ClassName("selectPopup")
	public String selectPopup();

	/** CSS Style name. @return style name. **/
	@ClassName("commandGlassPanel")
	public String commandGlassPanel();

	/** CSS Style name. @return style name. **/
	@ClassName("slidePopup")
	public String slidePopup();
}
