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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;



/**
 * Shows a popup panel which can contain a array of widgets like buttons.
 * 
 * <pre>
 * final CommandPopup commandPopup = new CommandPopup(&quot;Select a command&quot;, resources.information(), new Widget[] {
 * 		textLabel, okButton, cancelButton });
 * okButton.addClickHandler(new ClickHandler() {
 * 
 * 	&#064;Override
 * 	public void onClick(ClickEvent event) {
 * 		Utils.console(&quot;ok clicked&quot;);
 * 		commandPopup.hide();
 * 	}
 * });
 * cancelButton.addClickHandler(new ClickHandler() {
 * 
 * 	&#064;Override
 * 	public void onClick(ClickEvent event) {
 * 		Utils.console(&quot;cancel clicked&quot;);
 * 		commandPopup.hide();
 * 	}
 * });
 * commandPopup.showCentered(true);
 * </pre>
 * 
 */
public class CommandPopup extends PopupPanel {

	private static final double PADDING = 6.0;


/**
 * Default constructor.
 * @param caption the caption
 * @param image the image displayed in the left upper corner.
 * @param widgets list of widgets.
 */
	public CommandPopup(String caption, ImageResource image, Widget[] widgets) {
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().commandPopup());
		setGlassStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().commandGlassPanel());
		VerticalPanel vPanel = new VerticalPanel();
		if (caption != null) {
			Label captionLabel = new Label(caption);
			if (image != null) {
				HorizontalPanel hPanel = new HorizontalPanel();
				hPanel.add(new Image(image));
				captionLabel.getElement().getStyle().setPaddingLeft(PADDING, Unit.PX);
				hPanel.add(captionLabel);
				vPanel.add(hPanel);
			} else {
				vPanel.add(captionLabel);
			}
		}
		for (Widget w : widgets) {
			vPanel.add(w);
		}
		setWidget(vPanel);
	}



	/**
	 * Will display the command popup in the center of the display
	 * 
	 * @param glassEffect
	 *            true if glass effect should be applied.
	 */
	public void showCentered(boolean glassEffect) {
		setGlassEnabled(glassEffect);
		setPopupPositionAndShow(new PositionCallback() {

			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth) / 2;
				int top = (Window.getClientHeight() - offsetHeight) / 2;
				setPopupPosition(left, top);
			}
		});
	}

}
