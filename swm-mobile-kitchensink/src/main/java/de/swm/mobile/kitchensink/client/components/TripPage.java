package de.swm.mobile.kitchensink.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.icon.IconResources;



public class TripPage extends SimplePage {

	@UiField
	ListPanel list;
	
	@UiField SimpleHeaderPanel header;

	private static TripPageUiBinder uiBinder = GWT.create(TripPageUiBinder.class);

	interface TripPageUiBinder extends UiBinder<Widget, TripPage> {
	}

	private final IconResources icons;



	public TripPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		icons = GWT.create(IconResources.class);
		TripListItem start = new TripListItem(icons.start(), icons.bus(), "Start", "7 min");
		list.add(start);
		TripListItem past1 = new TripListItem(icons.past(), icons.bus(), "Haltestelle 1", "12 min");
		list.add(past1);
		TripListItem past2 = new TripListItem(icons.past(), icons.tram(), "Haltestelle 2", "15 min");
		list.add(past2);
		TripListItem current = new TripListItem(icons.current(), icons.ubahn(), "Haltestelle 3", "19 min");
		list.add(current);
		TripListItem future1 = new TripListItem(icons.future(), icons.ubahn(), "Haltestelle 4", "22 min");
		list.add(future1);
		TripListItem future2 = new TripListItem(icons.future(), icons.sbahn(), "Haltestelle 5", "27 min");
		list.add(future2);
		TripListItem finish = new TripListItem(icons.finish(), icons.bus(), "Ziel", "31 min");
		list.add(finish);
	}



	@UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
		switch (e.getSelection()) {
			case 0:
				break;
			case 1:
				break;
		}
	}



	@Override
	public String getName() {
		return "TripPage";
	}

}
