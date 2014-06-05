package de.swm.mobile.kitchensink.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.RadioButton;
import de.swm.commons.mobile.client.widgets.RadioButtonGroup;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;



public class RadioButtonPage extends SimplePage {

	@UiField
	RadioButtonGroup radiogroup1;
	@UiField
	RadioButtonGroup radiogroup2;
	@UiField SimpleHeaderPanel header;

	private static RadioButtonPageUiBinder uiBinder = GWT.create(RadioButtonPageUiBinder.class);

	interface RadioButtonPageUiBinder extends UiBinder<Widget, RadioButtonPage> {
	}



	public RadioButtonPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
	}



	@UiHandler("radiogroup1")
	void onRadioGroup1SelectionChanged(SelectionChangedEvent e) {
		RadioButton radio = (RadioButton) radiogroup1.getWidget(e.getSelection());
		Utils.console("group1 " + e.getSelection() + " " + radio.getText());
	}



	@UiHandler("radiogroup2")
	void onRadioGroup2SelectionChanged(SelectionChangedEvent e) {
		RadioButton radio = (RadioButton) radiogroup2.getWidget(e.getSelection());
		Utils.console("group2 " + e.getSelection() + " " + radio.getText());
	}



	@Override
	public String getName() {
		return RadioButtonPage.class.getName();
	}
}
