package de.swm.mobile.kitchensink.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;



public class FlipSwitchPage extends SimplePage {
	
	@UiField SimpleHeaderPanel header;

	private static FlipSwitchPageUiBinder uiBinder = GWT.create(FlipSwitchPageUiBinder.class);

	interface FlipSwitchPageUiBinder extends UiBinder<Widget, FlipSwitchPage> {
	}



	public FlipSwitchPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
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
		return FlipSwitchPage.class.getName();
	}
}
