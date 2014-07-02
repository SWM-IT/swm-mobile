package de.swm.mobile.kitchensink.client.showcase;

import com.google.gwt.user.client.ui.HasWidgets;
import de.swm.mobile.kitchensink.client.base.ShowcaseOverviewPage;
import de.swm.mobile.kitchensink.client.components.TestResources;
import de.swm.mobile.kitchensink.client.showcase.about.AboutDetailPage;
import de.swm.mobile.kitchensink.client.showcase.widgets.*;


public class AboutPage extends ShowcaseOverviewPage {



	public AboutPage(TestResources resources, HasWidgets toolbarContentArea, int toolbarIndex) {
		super(resources, toolbarContentArea, toolbarIndex);
		//Die showcase komponenten
		addPage(new AboutDetailPage());
	}



	@Override
	public String getName() {
		return "About";
	}
}
