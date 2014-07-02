package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;

public class HeaderPanelPage extends SimplePage {

	@UiField SimpleHeaderPanel header;
	
	private static HeaderPanelPageUiBinder uiBinder = GWT
			.create(HeaderPanelPageUiBinder.class);

	interface HeaderPanelPageUiBinder extends UiBinder<Widget, HeaderPanelPage> {
	}

	public HeaderPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
	}

	@Override
	public String getName() {
		return "HeaderPanel";
	}

}
