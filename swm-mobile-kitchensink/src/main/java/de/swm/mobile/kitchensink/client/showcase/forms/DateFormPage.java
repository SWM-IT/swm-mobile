package de.swm.mobile.kitchensink.client.showcase.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.DateTextBox;


public class DateFormPage extends SimplePage {
	
	private static SpinPageUiBinder uiBinder = GWT.create(SpinPageUiBinder.class);

	interface SpinPageUiBinder extends UiBinder<Widget, DateFormPage> {}
	
	@UiField DateTextBox dateBox;
		
	public DateFormPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getName() {
		return "SimplePage";
	}        
}
