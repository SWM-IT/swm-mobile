package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.TabPanel;
import de.swm.mobile.kitchensink.client.Application;

public class TabPanelPage extends SimplePage {

	@UiField SimpleHeaderPanel header;
	@UiField TabPanel tab;
	
	private static TabPanelPageUiBinder uiBinder = GWT
			.create(TabPanelPageUiBinder.class);

	interface TabPanelPageUiBinder extends UiBinder<Widget, TabPanelPage> {
	}

	public TabPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));	
		Application.addDefaultBackButtonHanlder(header);
	}

	@Override
	public String getName() {
		return "TabPanel";
	}

}
