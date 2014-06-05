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
package de.swm.gwt.client.theme;

import com.google.gwt.user.cellview.client.DataGrid;
import de.swm.gwt.client.theme.components.*;


/**
 * Base class for a ClientBundle implementation. A concrete ClientBudle e.g.
 * {@link de.swm.gwt.client.theme.base.BaseGWTCssBundle} represents a
 * Theme instance (including css and other resources).
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2010-14, SWM Services GmbH
 */
public interface GWTCssBundle extends DataGrid.Resources {


	/**
	 * General css resources for text.
	 *
	 * @return css resources for text
	 */
	DatePickerCss getDatePickerCss();

	/**
	 * General css resources for text.
	 *
	 * @return css resources for text
	 */
	TextCss getTextCss();


	/**
	 * Represents a css resource for different kinds of error messages.
	 *
	 * @return the css representation, see {@link com.google.gwt.resources.client.CssResource}
	 */
	ErrorCss getErrorCss();


	/**
	 * Defines one part of the widget system.
	 *
	 * @return the css representation, see {@link com.google.gwt.resources.client.CssResource}
	 */
	PageCss getPageCss();


	/**
	 * Defines one part of the widget system.
	 *
	 * @return the css representation, see {@link com.google.gwt.resources.client.CssResource}
	 */
	ButtonCss getButtonCss();

	/**
	 * Onw data grid styling extentions which are nor part of gwt default style implementation.
	 *
	 * @return grid styling extentions.
	 */
	GridCss getGridCss();

	/**
	 * Extends / overrides the gwt data grid styling.
	 * @return grid styling extentions.
	 */
	DataGrid.Style dataGridStyle();

}
