package de.swm.mobile.kitchensink.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;



public class SliderPage extends SimplePage {

	@UiField
	Label value1;
	@UiField SimpleHeaderPanel header;

	private static FlipSwitchPageUiBinder uiBinder = GWT.create(FlipSwitchPageUiBinder.class);

	interface FlipSwitchPageUiBinder extends UiBinder<Widget, SliderPage> {
	}



	public SliderPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
	}



	@UiHandler("slider1")
	public void onValueChangeSlider1(ValueChangeEvent<Integer> e) {
		value1.setText(e.getValue() + "");
	}



	@Override
	public String getName() {
		return SliderPage.class.getName();
	}
}
