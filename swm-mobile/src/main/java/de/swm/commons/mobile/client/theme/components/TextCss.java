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



/**
 * Represents a css resource for general Text formatting. <br>
 * 
 */
public interface TextCss extends CssResource {
	/** CSS Style name. @return style name. **/
	@ClassName("title")
	public String title();



	/** CSS Style name. @return style name. **/
	@ClassName("subtitle")
	public String subtitle();



	/** CSS Style name. @return style name. **/
	@ClassName("sub-subtitle")
	public String subSubtitle();

}
