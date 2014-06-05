package de.swm.mobile.kitchensink.client.components;

import static de.swm.mobile.kitchensink.client.components.ConnectionListItem.TransportMeans.BUS;
import static de.swm.mobile.kitchensink.client.components.ConnectionListItem.TransportMeans.SBAHN;
import static de.swm.mobile.kitchensink.client.components.ConnectionListItem.TransportMeans.TRAM;
import static de.swm.mobile.kitchensink.client.components.ConnectionListItem.TransportMeans.UBAHN;
import static de.swm.mobile.kitchensink.client.components.ConnectionListItem.TransportMeans.WALK;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.mobile.kitchensink.client.components.ConnectionListItem.TransportMeans;
import de.swm.mobile.kitchensink.client.icon.IconResources;

public class ConnectionPage extends SimplePage {

	@UiField ListPanel list;
	
	private static ConnectionPageUiBinder uiBinder = GWT.create(ConnectionPageUiBinder.class);

	interface ConnectionPageUiBinder extends UiBinder<Widget, ConnectionPage> {}
	
	@SuppressWarnings("unused")
	private final IconResources icons;

	@SuppressWarnings("deprecation")
	public ConnectionPage() {
		initWidget(uiBinder.createAndBindUi(this));
		icons = GWT.create(IconResources.class);
		Date from = new Date();
		from.setHours(16);
		from.setMinutes(30);
		Date to = new Date();
		to.setHours(18);
		to.setMinutes(40);
		ConnectionListItem c1 = new ConnectionListItem(from, to, 3, new TransportMeans[] {UBAHN, BUS, TRAM, WALK});
		c1.setDisabled(true);
		list.add(c1);
		from.setHours(17);
		from.setMinutes(10);
		to.setHours(18);
		to.setMinutes(5);
		final ConnectionListItem c2 = new ConnectionListItem(from, to, 2, new TransportMeans[] {UBAHN, SBAHN, TRAM});
		c2.setDisabled(true);
		list.add(c2);
		from.setHours(17);
		from.setMinutes(30);
		to.setHours(19);
		to.setMinutes(45);
		ConnectionListItem c3 = new ConnectionListItem(from, to, 4, new TransportMeans[] {BUS, UBAHN, WALK, SBAHN, TRAM});
		list.add(c3);
		from.setHours(18);
		from.setMinutes(20);
		to.setHours(21);
		to.setMinutes(05);
		ConnectionListItem c4 = new ConnectionListItem(from, to, 2, new TransportMeans[] {UBAHN, WALK, SBAHN});
		list.add(c4);
		list.setShowArrow(true);
		
		// test enablement
		Timer t = new Timer() {
			@Override
			public void run() {
				c2.setDisabled(false);
			}
		};
		t.schedule(2000);
	}

    @UiHandler("list")
    void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		Window.alert("First item selected");
    		break;
    	case 1:
    		Window.alert("Second item selected");
    		break;
    	case 2:
    		Window.alert("Third item selected");
    		break;
    	}
    }

	@Override
	public String getName() {
		return "ConnectionPage";
	}

}
