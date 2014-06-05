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
 * Represents a css resource for a {@link TextBox} and {@link TextArea}.
 */
public interface TextBoxCss extends CssResource {
	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("textBox")
	public String textBox();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("error")
	public String error();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("textArea")
	public String textArea();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("focus")
	public String focus();


	/**
	 * TODO: Remove, this style is currently unused.
	 * /** CSS Style name. @return style name. *
	 */
	@ClassName("decoratedTextBox")
	public String decoratedTextBox();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("decoratedTextBoxCaption")
	public String decoratedTextBoxCaption();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("decoratedTextBoxVPanel")
	public String decoratedTextBoxVPanel();

	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("decoratedTextBoxHPanel")
	public String decoratedTextBoxHPanel();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("decoratedTextBoxIcon")
	public String decoratedTextBoxIcon();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("decoratedTextBoxInput")
	public String decoratedTextBoxInput();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("decoratedSuggestBox")
	public String decoratedSuggestBox();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("decoratedSuggestBoxIcon")
	public String decoratedSuggestBoxIcon();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("decoratedSuggestBoxInput")
	public String decoratedSuggestBoxInput();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("suggestPopupContent")
	public String suggestPopupContent();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("item")
	public String item();


	/**
	 * CSS Style name. @return style name. *
	 */
	@ClassName("item-selected")
	public String itemSelected();

}
