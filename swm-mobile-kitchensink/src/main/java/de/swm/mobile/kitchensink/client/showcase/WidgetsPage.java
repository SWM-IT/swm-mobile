package de.swm.mobile.kitchensink.client.showcase;

import com.google.gwt.user.client.ui.HasWidgets;
import de.swm.mobile.kitchensink.client.base.ShowcaseOverviewPage;
import de.swm.commons.mobile.client.theme.bootstrap.extention.TestResources;
import de.swm.mobile.kitchensink.client.showcase.widgets.*;


public class WidgetsPage extends ShowcaseOverviewPage {
	


	public WidgetsPage(TestResources resources, HasWidgets toolbarContentArea, int toolbarIndex) {
		super(resources, toolbarContentArea, toolbarIndex);
		//Die showcase komponenten
		addPage(new ButtonPage());
		addPage(new CheckBoxPage());
		addPage(new DropDownListPage());
		addPage(new FlipSwitchPage());
		addPage(new RadioButtonPage());
		addPage(new SliderPage());
		addPage(new TextBoxPage());
	}



	@Override
	public String getName() {
		return "Widgets";
	}
}
