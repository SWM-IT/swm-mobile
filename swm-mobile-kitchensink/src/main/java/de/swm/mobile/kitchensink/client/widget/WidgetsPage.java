package de.swm.mobile.kitchensink.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.gwt.client.mobile.Direction;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.showcase.widgets.ButtonPage;
import de.swm.mobile.kitchensink.client.showcase.widgets.CheckBoxPage;
import de.swm.mobile.kitchensink.client.showcase.widgets.DropDownListPage;

public class WidgetsPage extends SimplePage {

	@UiField ListPanel list;
	@UiField SimpleHeaderPanel header;
	
	ButtonPage button = new ButtonPage();
	CheckBoxPage checkBox = new CheckBoxPage();
	FlipSwitchPage flipSwitch = new FlipSwitchPage();
	RadioButtonPage radioButton = new RadioButtonPage();
	SliderPage slider = new SliderPage();
	TextBoxPage textBox = new TextBoxPage(null);
	DropDownListPage dropDownList = new DropDownListPage();
	
	private static WidgetsPageUiBinder uiBinder = GWT
			.create(WidgetsPageUiBinder.class);

	interface WidgetsPageUiBinder extends UiBinder<Widget, WidgetsPage> {
	}

	
	public WidgetsPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
	}

    @UiHandler("list")
    void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		goTo(button, Direction.LEFT);
    		break;
    	case 1:
    		goTo(checkBox, Direction.LEFT);
    		break;
    	case 2:
    		goTo(dropDownList, Direction.LEFT);
    		break;
    	case 3:
    		goTo(flipSwitch, Direction.LEFT);
    		break;
    	case 4:
    		goTo(radioButton, Direction.LEFT);
    		break;
    	case 5:
    		goTo(slider, Direction.LEFT);
    		break;
    	case 6:
    		goTo(textBox, Direction.LEFT);
    		break;
    	}
    }

	@Override
	public String getName() {
		return WidgetsPage.class.getName();
	}

}
