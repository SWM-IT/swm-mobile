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
package de.swm.commons.mobile.client.widgets.command;

import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;


/**
 * Defines a command bar containing command widgets (CommandItems).
 * 
 * <pre>
 * <ui:with field='icon' type='de.swm....SomeIconResources'/>
 * 
 * <m:CommandPanel>
 * 		<m:CommandItem ui:field='command1' headerText='Home' normalIcon='{icon.home}' highlightIcon='{icon.home_selected}' />
 * 		<m:CommandItem ui:field='command2' headerText='Download' normalIcon='{icon.download}' highlightIcon='{icon.download_selected}' />
 * 		<m:CommandItem ui:field='command3' headerText='Info' normalIcon='{icon.information}' highlightIcon='{icon.information_selected}' />
 * 	</m:CommandPanel>
 * </pre>
 * 
 */
public class CommandPanel extends HorizontalPanel {

	/**
	 * Default Constructor.
	 */
	public CommandPanel() {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getCommandPanelCss().commandPanel());
	}



	@Override
	public void add(Widget w) {
		assert (w instanceof CommandItem) : "Only CommandItem allowed as child of CommandPanel";
		super.add(w);
	}

}
