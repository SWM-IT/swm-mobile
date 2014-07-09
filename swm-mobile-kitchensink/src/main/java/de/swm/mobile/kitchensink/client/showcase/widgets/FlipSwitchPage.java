package de.swm.mobile.kitchensink.client.showcase.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"FlipSwitchPage.ui.xml"})
public class FlipSwitchPage extends ShowcaseDetailPage {


	@UiField
	HeaderPanel header;

	private static FlipSwitchPageUiBinder uiBinder = GWT.create(FlipSwitchPageUiBinder.class);

	@Override
	public HeaderPanel getHeaderPanel() {
		return header;
	}

	interface FlipSwitchPageUiBinder extends UiBinder<Widget, FlipSwitchPage> {
	}


	public FlipSwitchPage() {
		super(FlipSwitchPage.class);
		initWidget(uiBinder.createAndBindUi(this));
	}


	@UiHandler("iphone")
	void onValueChangeIPhone(ValueChangeEvent<Boolean> e) {
		Utils.console("Flip switch iPhone " + e.getValue());
	}


	@UiHandler("android")
	void onValueChangeAndroid(ValueChangeEvent<Boolean> e) {
		Utils.console("Flip switch android " + e.getValue());
	}


	@UiHandler("blackberry")
	void onValueChangeBlackBerry(ValueChangeEvent<Boolean> e) {
		Utils.console("Flip switch blackberry " + e.getValue());
	}


	@UiHandler("webos")
	void onValueChangeWebOS(ValueChangeEvent<Boolean> e) {
		Utils.console("Flip switch webos " + e.getValue());
	}


	@UiHandler("wp7")
	void onValueChangeWP7(ValueChangeEvent<Boolean> e) {
		Utils.console("Flip switch wp7 " + e.getValue());
	}


	@Override
	public String getName() {
		return "Flip switch";
	}
}
