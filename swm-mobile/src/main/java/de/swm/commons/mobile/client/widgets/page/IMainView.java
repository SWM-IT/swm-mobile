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
package de.swm.commons.mobile.client.widgets.page;

import de.swm.commons.mobile.client.widgets.toolbar.ToolbarPanel;


/**
 * Defines the main view of an application.
 * 
 * <br>
 *
 */
public interface IMainView extends IPageWithoutHeader {

	/**
	 * Liefert das zentrale Toolbar pannel der Hauptseite.
	 * 
	 * @return das toolbar pannel der Hauptseite.
	 */
	ToolbarPanel toolbarPannel();

}
