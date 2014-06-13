package de.swm.mobile.kitchensink.client.showcase.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.CheckBox;
import de.swm.commons.mobile.client.widgets.CheckBoxGroup;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.ShowcaseAnnotations;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"CheckBoxPage.ui.xml"})
public class CheckBoxPage extends ShowcaseDetailPage {

	@UiField
	CheckBoxGroup group1;
	@UiField
	CheckBoxGroup group2;
	@UiField
	SimpleHeaderPanel header;

	private static CheckBoxPageUiBinder uiBinder = GWT
			.create(CheckBoxPageUiBinder.class);

	@Override
	public SimpleHeaderPanel getHeaderPanel() {
		return header;
	}

	interface CheckBoxPageUiBinder extends UiBinder<Widget, CheckBoxPage> {
	}

	public CheckBoxPage() {
		super(CheckBoxPage.class);
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
	}

	@UiHandler("group1")
	void onGroup1SelectionChanged(SelectionChangedEvent e) {
		CheckBox radio = (CheckBox) group1.getWidget(e.getSelection());
		Utils.console("group1 " + e.getSelection() + " " + radio.getText());
	}

	@UiHandler("group2")
	void onGroup2SelectionChanged(SelectionChangedEvent e) {
		CheckBox radio = (CheckBox) group2.getWidget(e.getSelection());
		Utils.console("group2 " + e.getSelection() + " " + radio.getText());
	}

	@Override
	public String getName() {
		return "Checkboxes";
	}
}
