package de.swm.mobile.kitchensink.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.AccordionPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;

public class AccordionPanelPage extends SimplePage {

	@UiField SimpleHeaderPanel header;
	@UiField AccordionPanel accordion;
	
	private static AccordionPanelPageUiBinder uiBinder = GWT
			.create(AccordionPanelPageUiBinder.class);

	interface AccordionPanelPageUiBinder extends UiBinder<Widget, AccordionPanelPage> {
	}

	public AccordionPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));	
		Application.addDefaultBackButtonHanlder(header);
	}

	@Override
	public String getName() {
		return "AccordionPanelPage";
	}

}
