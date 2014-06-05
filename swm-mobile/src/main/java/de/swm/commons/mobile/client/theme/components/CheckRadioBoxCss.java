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

/**
 * Represents check and radio boxes.
 * 
 */
public interface CheckRadioBoxCss extends DisplayCss, PageCss {
	/** CSS Style name. @return style name. **/
	@ClassName("checkBoxGroup")
	public String checkBoxGroup();



	/** CSS Style name. @return style name. **/
	@ClassName("radioButtonGroup")
	public String radioButtonGroup();



	/** CSS Style name. @return style name. **/
	@ClassName("radioButton")
	public String radioButton();



	/** CSS Style name. @return style name. **/
	@ClassName("checkBoxIndicator")
	public String checkBoxIndicator();



	/** CSS Style name. @return style name. **/
	@ClassName("horizontal")
	public String horizontal();



	/** CSS Style name. @return style name. **/
	@ClassName("vertical")
	public String vertical();



	/** CSS Style name. @return style name. **/
	@ClassName("selected")
	public String selected();



	/** CSS Style name. @return style name. **/
	@ClassName("pressed")
	public String pressed();

}
