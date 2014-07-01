package de.swm.mobile.kitchensink.client.showcase;

import com.google.gwt.user.client.ui.HasWidgets;
import de.swm.mobile.kitchensink.client.base.ShowcaseOverviewPage;
import de.swm.mobile.kitchensink.client.components.TestResources;
import de.swm.mobile.kitchensink.client.showcase.animations.LoadPage;
import de.swm.mobile.kitchensink.client.showcase.animations.TransitionsPage;
import de.swm.mobile.kitchensink.client.showcase.widgets.*;


public class AnimationsPage extends ShowcaseOverviewPage {



	public AnimationsPage(TestResources resources, HasWidgets toolbarContentArea, int toolbarIndex) {
		super(resources, toolbarContentArea, toolbarIndex);
		//Die showcase komponenten
		addPage(new LoadPage());
		addPage(new TransitionsPage());
	}



	@Override
	public String getName() {
		return "Animations";
	}
}
