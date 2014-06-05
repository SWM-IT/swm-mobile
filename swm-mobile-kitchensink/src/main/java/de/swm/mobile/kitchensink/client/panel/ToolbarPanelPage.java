package de.swm.mobile.kitchensink.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.ScrollPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.ToolbarContent;
import de.swm.commons.mobile.client.widgets.ToolbarElement;
import de.swm.commons.mobile.client.widgets.ToolbarPanel;
import de.swm.commons.mobile.client.widgets.ToolbarPanel.ToolbarSelectionHandler;
import de.swm.mobile.kitchensink.client.Application;

public class ToolbarPanelPage extends SimplePage {

	@UiField SimpleHeaderPanel header;
	@UiField ToolbarPanel toolbar;
	
	private static ToolbarPanelPageUiBinder uiBinder = GWT.create(ToolbarPanelPageUiBinder.class);

	interface ToolbarPanelPageUiBinder extends UiBinder<Widget, ToolbarPanelPage> {}

	public ToolbarPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		toolbar.addSelectionHandler(new ToolbarSelectionHandler() {
			
			@Override
			public void toolSelected(int index, ToolbarElement te) {
				ToolbarContent content = te.getContent();
				if (content != null) {
					ScrollPanel scroll = new ScrollPanel();
					ListPanel list = new ListPanel();
					content.clear();
					content.add(scroll);
					scroll.add(list);
					for (int i = 0; i < 20; i++) {
						ListItem item = new ListItem();
						item.add(new Label("Item " + i));
						list.add(item);
					}
				}				
			}
		});
		
	}

	@Override
	public String getName() {
		return "ToolbarPanle";
	}

}
