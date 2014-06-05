package de.swm.mobile.kitchensink.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.components.TestResources;



public class TextBoxPage extends SimplePage {
	
	@UiField SimpleHeaderPanel header;

	private static TextBoxPageUiBinder uiBinder = GWT.create(TextBoxPageUiBinder.class);

	interface TextBoxPageUiBinder extends UiBinder<Widget, TextBoxPage> {
	}



	public TextBoxPage(TestResources resources) {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
	}



	@Override
	public String getName() {
		return TextBoxPage.class.getName();
	}
}
