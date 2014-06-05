package de.swm.mobile.kitchensink.client.transition;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;

public class TransitionDemoPage extends SimplePage {

	@UiField SimpleHeaderPanel header;
	@UiField ListPanel list;
	
	private static TransitionPageUiBinder uiBinder = GWT
			.create(TransitionPageUiBinder.class);

	interface TransitionPageUiBinder extends UiBinder<Widget, TransitionDemoPage> {
	}

	public TransitionDemoPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
    @UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
    }

	@Override
	public String getName() {
		return "transitionDemo";
	}

}
