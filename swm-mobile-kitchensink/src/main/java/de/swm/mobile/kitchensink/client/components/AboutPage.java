package de.swm.mobile.kitchensink.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.theme.base.BaseSWMMobileTheme;
import de.swm.commons.mobile.client.theme.bright.BrightSWMMobileTheme;
import de.swm.commons.mobile.client.widgets.DropDownList;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;

public class AboutPage extends SimplePage {

	@UiField SimpleHeaderPanel header;
	
	private static AboutPageUiBinder uiBinder = GWT.create(AboutPageUiBinder.class);

	interface AboutPageUiBinder extends UiBinder<Widget, AboutPage> {}
	

	public AboutPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		

	}

	@Override
	public String getName() {
		return "AboutPage";
	}
	

}
