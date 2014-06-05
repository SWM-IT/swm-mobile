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
import com.google.gwt.resources.client.CssResource.Shared;



/**
 * FIXME: Aufspalten in OsCss und PageCss Represents a css resource for a Page. <br>
 * It's Shared because other components may used the OS style tag.
 * 
 */
@Shared
public interface PageCss extends CssResource {
	/** CSS Style name. @return style name. **/
	@ClassName("page")
	public String page();



	/** CSS Style name. @return style name. **/
	@ClassName("android")
	public String adroid();



	/** CSS Style name. @return style name. **/
	@ClassName("iOS")
	public String iOs();



	/** CSS Style name. @return style name. **/
	@ClassName("desktop")
	public String desktop();

}
