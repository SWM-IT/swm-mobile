package de.swm.mobile.kitchensink.client.showcase;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseOverviewPage;
import de.swm.mobile.kitchensink.client.components.TestResources;
import de.swm.mobile.kitchensink.client.showcase.widgets.ButtonPage;
import de.swm.mobile.kitchensink.client.showcase.widgets.CheckBoxPage;
import de.swm.mobile.kitchensink.client.showcase.widgets.DropDownListPage;


public class WidgetsPage extends ShowcaseOverviewPage {
	


	public WidgetsPage(TestResources resources, HasWidgets toolbarContentArea, int toolbarIndex) {
		super(resources, toolbarContentArea, toolbarIndex);
		//Die showcase komponenten
		addPage(new ButtonPage());
		addPage(new CheckBoxPage());
		addPage(new DropDownListPage());
	}



	@Override
	public String getName() {
		return "Widgets";
	}
}
