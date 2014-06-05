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
package de.swm.gwt.client.theme.components;

import com.google.gwt.resources.client.CssResource;

/**
 * Represents a css resource for a decoratedListItem.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2010-14, SWM Services GmbH
 */
public interface ButtonCss extends CssResource {


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("button")
	public String button();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("hidden")
	public String hidden();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("disabled")
	public String disabled();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("imageButton")
	public String imageButton();

}
