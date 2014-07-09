package de.swm.mobile.kitchensink.client.showcase.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.RadioButton;
import de.swm.commons.mobile.client.widgets.RadioButtonGroup;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"RadioButtonPage.ui.xml"})
public class RadioButtonPage extends ShowcaseDetailPage {

	@UiField
	RadioButtonGroup radiogroup1;

	@UiField
	RadioButtonGroup radiogroup2;

	@UiField
	HeaderPanel header;

	private static RadioButtonPageUiBinder uiBinder = GWT.create(RadioButtonPageUiBinder.class);

	@Override
	public HeaderPanel getHeaderPanel() {
		return header;
	}

	interface RadioButtonPageUiBinder extends UiBinder<Widget, RadioButtonPage> {
	}


	public RadioButtonPage() {
		super(RadioButtonPage.class);
		initWidget(uiBinder.createAndBindUi(this));
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
		return "Radio buttons";
	}
}
