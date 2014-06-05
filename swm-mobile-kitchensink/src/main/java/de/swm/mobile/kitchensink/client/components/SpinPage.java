package de.swm.mobile.kitchensink.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.DateTextBox;


public class SpinPage extends SimplePage {
	
	private static SpinPageUiBinder uiBinder = GWT.create(SpinPageUiBinder.class);

	interface SpinPageUiBinder extends UiBinder<Widget, SpinPage> {}
	
	@UiField DateTextBox dateBox;
		
	public SpinPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getName() {
		return "SimplePage";
	}        
}
