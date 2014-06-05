package de.swm.mobile.kitchensink.client.panel;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.Button;
import de.swm.commons.mobile.client.widgets.CommandDropDown;
import de.swm.commons.mobile.client.widgets.CommandItem;
import de.swm.commons.mobile.client.widgets.DateStyle;
import de.swm.commons.mobile.client.widgets.SimpleDatePopup;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.SimplePopup;
import de.swm.mobile.kitchensink.client.Application;

public class CommandPanelPage extends SimplePage {

	@UiField SimpleHeaderPanel header;
	@UiField CommandItem command1;
	@UiField CommandItem command2;
	@UiField CommandDropDown commandDropDown;
	@UiField CommandItem command21;
	@UiField CommandItem command22;
	@UiField CommandItem command23;
	
	private static CommandPanelPageUiBinder uiBinder = GWT.create(CommandPanelPageUiBinder.class);

	interface CommandPanelPageUiBinder extends UiBinder<Widget, CommandPanelPage> {}

	public CommandPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		command1.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Button[] buttons = new Button[5];
				for (int i = 0; i < 5; i++) {
					buttons[i] = new Button("Option_" + i);
					if (i > 0) {
						buttons[i].getElement().getStyle().setMarginTop(0.3, Unit.EM);
					}
				}
				final SimplePopup popup = new SimplePopup(buttons);
				popup.setAutoHide(true);
				for (int i = 0; i < 5; i++) {
					final int num = i;
					buttons[i].addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							Utils.console("Option " + num + " selected");
							popup.hide();
						}
					});
				}
				popup.showRelativeTo(command1);
			}
		});
		command2.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				SimpleDatePopup simpleDateTimePopup = new SimpleDatePopup(new Date(), DateStyle.DATE, new SimpleDatePopup.DateSelectionHandler() {

					@Override
					public void dateSelectionCancelled() {
						// do nothing
					}

					@Override
					public void dateSelected(Date selectedDate) {
						Utils.console("selected date: " + selectedDate);
					}
				});
				simpleDateTimePopup.showCentered(true);
			}
		});
		commandDropDown.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				Utils.console("selected item: " + event.getValue());
			}
			
		});
		
		command21.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Button[] buttons = new Button[5];
				for (int i = 0; i < 5; i++) {
					buttons[i] = new Button("Option_" + i);
					if (i > 0) {
						buttons[i].getElement().getStyle().setMarginTop(0.3, Unit.EM);
					}
				}
				final SimplePopup popup = new SimplePopup(buttons);
				popup.setAutoHide(true);
				for (int i = 0; i < 5; i++) {
					final int num = i;
					buttons[i].addClickHandler(new ClickHandler() {
						
						@Override
						public void onClick(ClickEvent event) {
							Utils.console("Option " + num + " selected");
							popup.hide();
						}
					});
				}
				popup.showRelativeTo(command21);
			}
		});
		command22.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Command 2 clicked");
			}
		});
		command23.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.alert("Command 3 clicked");
			}
		});
	}

	@Override
	public String getName() {
		return "CommandPanel";
	}

}
