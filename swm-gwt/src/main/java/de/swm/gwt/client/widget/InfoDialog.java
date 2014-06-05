package de.swm.gwt.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Dialog to display information messages.
 * 
 * copyright (C) 2014, SWM Services GmbH
 */
public class InfoDialog extends DialogBox {
	
	/**
	 * Default constructor.
	 * 
	 * @param header title of dialog
	 * @param lines lines to display
	 */
	public InfoDialog(String header, String... lines) {
		super(true, true);
		
		setAnimationEnabled(true);
		setGlassEnabled(true);
		
		setText(header);

		FlowPanel panel = new FlowPanel();
		
		for (String line : lines) {
			panel.add(new Label(line));
		}
		
		FlowPanel buttonPanel = new FlowPanel();
		
		Button okButton = new Button("OK");
		okButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				InfoDialog.this.hide();
			}
		});
		
		buttonPanel.add(okButton);
		
		panel.add(buttonPanel);
		setWidget(panel);
		
	}
}
