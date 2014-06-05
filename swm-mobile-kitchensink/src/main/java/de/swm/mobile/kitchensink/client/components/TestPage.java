package de.swm.mobile.kitchensink.client.components;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.Button;
import de.swm.commons.mobile.client.widgets.CommandPopup;
import de.swm.commons.mobile.client.widgets.DatePopup;
import de.swm.commons.mobile.client.widgets.DatePopup.DateSelectionHandler;
import de.swm.commons.mobile.client.widgets.DateStyle;
import de.swm.commons.mobile.client.widgets.DecoratedListItem;
import de.swm.commons.mobile.client.widgets.ImageButton;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.SimpleDatePopup;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.SimpleImageButton;
import de.swm.commons.mobile.client.widgets.VerticalPanel;
import de.swm.mobile.kitchensink.client.Application;



public class TestPage extends SimplePage {

	@UiField
	SimpleHeaderPanel header;
	@UiField
	ListPanel list;
	@UiField
	DecoratedListItem specialItem;

	private static TestPageUiBinder uiBinder = GWT.create(TestPageUiBinder.class);
	private Date datePopupDate;
	private Date dateTimePopupDate;
	private Date timePopupDate;

	interface TestPageUiBinder extends UiBinder<Widget, TestPage> {
	}

	private final TestResources resources;



	public TestPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		resources = GWT.create(TestResources.class);
		for (int i = 0; i < 10; i++) {
			DecoratedListItem item = null;
			if (i == 0) {
				item = new DecoratedListItem(resources.gear3(), "Command Panel", "Sub Label " + i);
			} else if (i == 1) {
				item = new DecoratedListItem(resources.gear3(), "Popup Test", "Sub Label " + i);
			} else if (i == 2) {
				item = new DecoratedListItem(resources.gear3(), "Date Popup DATETIME", "Sub Label " + i);
			} else if (i == 3) {
				item = new DecoratedListItem(resources.gear3(), "Date Popup DATE", "Sub Label " + i);
			} else if (i == 4) {
				item = new DecoratedListItem(resources.gear3(), "Date Popup TIME", "Sub Label " + i);
			} else if (i == 5) {
				item = new DecoratedListItem(resources.triangle60b(), "Simple Date Popup DATETIME", "Sub Label " + i);
			} else {
				item = new DecoratedListItem(resources.gear3(), "Test Entry " + i, "Sub Label " + i);
			}
			list.add(item);
		}
		list.setShowArrow(true);
		specialItem.addImageClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Utils.console("image clicked");
				event.stopPropagation();
			}
		});
		SimpleImageButton button = new SimpleImageButton(resources.info(), new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.alert("image button clicked");
			}
		});
		header.setRightWidget(button);
	}



	@UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
		switch (e.getSelection()) {
			case 0:
				specialItem.setImage(resources.success2());
				specialItem.setTitle("New Title");
				specialItem.setSubtitle("New Subtitle");
				break;
			case 1:
				Label textLabel = new Label("Some dummy text");
				Button okButton = new Button();
				okButton.setText("Ok");
				Button cancelButton = new Button();
				cancelButton.setText("Cancel");
				final CommandPopup commandPopup = new CommandPopup("Select a command", resources.information(),
						new Widget[] { textLabel, okButton, cancelButton });
				okButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						Utils.console("ok clicked");
						commandPopup.hide();
					}
				});
				cancelButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						Utils.console("cancel clicked");
						commandPopup.hide();
					}
				});
				commandPopup.showCentered(true);
				break;
			case 2:
				final PopupPanel popup = new PopupPanel();
				VerticalPanel vPanel = new VerticalPanel();
				vPanel.add(new Label("Test Popup with a long title"));
				ImageButton imgButton = new ImageButton(resources.globe_24(), new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						GWT.log("image button clicked");
						popup.hide();
					}
				});
				vPanel.add(imgButton);
				Button closeButton = new Button("Close", new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						popup.hide();
					}
				});
				vPanel.add(closeButton);
				vPanel.getElement().getStyle().setBackgroundColor("black");
				vPanel.getElement().getStyle().setPadding(0.4, Unit.EM);
				popup.setWidget(vPanel);
				popup.show();
				break;
			case 3:
				DatePopup dateTimePopup = new DatePopup(this.dateTimePopupDate, DateStyle.DATETIME, new DateSelectionHandler() {

					@Override
					public void dateSelectionCancelled() {
						// do nothing
					}

					@Override
					public void dateSelected(Date selectedDate) {
						Window.alert("Parsed Date DATETIME: " + selectedDate);
						DecoratedListItem item = (DecoratedListItem) list.getWidget(3);
						item.setTitle(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(selectedDate));
						dateTimePopupDate = (Date) selectedDate.clone();
					}
				});
				dateTimePopup.addRelativeTimeChooser(new String[] {"5 minutes", "10 minutes", "15 minutes", "30 minutes"},
						new int[] {5, 10, 15, 30}, null, true, true);
				
				dateTimePopup.showCentered(true);
				break;
			case 4:
				DatePopup datePopup = new DatePopup(this.datePopupDate, DateStyle.DATE, new DateSelectionHandler() {

					@Override
					public void dateSelectionCancelled() {
						// do nothing
					}

					@Override
					public void dateSelected(Date selectedDate) {
						Window.alert("Parsed Date DATE: " + selectedDate);
						DecoratedListItem item = (DecoratedListItem) list.getWidget(4);
						item.setTitle(DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM).format(selectedDate));
						datePopupDate = (Date) selectedDate.clone();
					}
				});
				datePopup.showCentered(true);
				break;
			case 5:
				DatePopup timePopup = new DatePopup(this.timePopupDate, DateStyle.TIME, new DateSelectionHandler() {

					@Override
					public void dateSelectionCancelled() {
						// do nothing
					}



					@Override
					public void dateSelected(Date selectedDate) {
						Window.alert("Parsed Date TIME: " + selectedDate);
						DecoratedListItem item = (DecoratedListItem) list.getWidget(5);
						item.setTitle(DateTimeFormat.getFormat(PredefinedFormat.TIME_MEDIUM).format(selectedDate));
						timePopupDate = (Date) selectedDate.clone();
					}
				});
				timePopup.showCentered(true);
				break;
			case 6:
				SimpleDatePopup simpleDateTimePopup = new SimpleDatePopup(this.dateTimePopupDate, DateStyle.DATETIME, new SimpleDatePopup.DateSelectionHandler() {

					@Override
					public void dateSelectionCancelled() {
						// do nothing
					}

					@Override
					public void dateSelected(Date selectedDate) {
						Window.alert("Parsed Date DATETIME: " + selectedDate);
						DecoratedListItem item = (DecoratedListItem) list.getWidget(6);
						item.setTitle(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(selectedDate));
						dateTimePopupDate = (Date) selectedDate.clone();
					}
				});
				simpleDateTimePopup.showCentered(true);
				break;

		}
	}



	@Override
	public String getName() {
		return "TestPage";
	}

}
