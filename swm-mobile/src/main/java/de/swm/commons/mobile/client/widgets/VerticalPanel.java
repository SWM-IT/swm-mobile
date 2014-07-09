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
package de.swm.commons.mobile.client.widgets;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.base.PanelBase;


/**
 * Vertical panel for mobile devices.
 * 
 */
public class VerticalPanel extends PanelBase {

	/**
	 * Default constructor.
	 */
	public VerticalPanel() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getHorizontalVerticalPanelCss().verticalPanel());
	}

}
