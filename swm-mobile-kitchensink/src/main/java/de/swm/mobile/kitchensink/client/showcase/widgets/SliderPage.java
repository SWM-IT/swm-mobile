package de.swm.mobile.kitchensink.client.showcase.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;


import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"SliderPage.ui.xml"})
public class SliderPage extends ShowcaseDetailPage {

	@UiField
	Label value1;
	@UiField
	HeaderPanel header;

	private static FlipSwitchPageUiBinder uiBinder = GWT.create(FlipSwitchPageUiBinder.class);

	@Override
	public HeaderPanel getHeaderPanel() {
		return header;
	}

	interface FlipSwitchPageUiBinder extends UiBinder<Widget, SliderPage> {
	}



	public SliderPage() {
		super(SliderPage.class);
		initWidget(uiBinder.createAndBindUi(this));
	}



	@UiHandler("slider1")
	public void onValueChangeSlider1(ValueChangeEvent<Integer> e) {
		value1.setText(e.getValue() + "");
	}



	@Override
	public String getName() {
		return "Slider page";
	}
}
