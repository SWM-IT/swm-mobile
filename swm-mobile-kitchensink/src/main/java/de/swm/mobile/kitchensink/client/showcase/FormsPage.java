package de.swm.mobile.kitchensink.client.showcase;

import com.google.gwt.user.client.ui.HasWidgets;
import de.swm.mobile.kitchensink.client.base.ShowcaseOverviewPage;
import de.swm.mobile.kitchensink.client.components.TestResources;
import de.swm.mobile.kitchensink.client.showcase.forms.DateFormPage;
import de.swm.mobile.kitchensink.client.showcase.forms.EditorFormPage;
import de.swm.mobile.kitchensink.client.showcase.panels.*;


public class FormsPage extends ShowcaseOverviewPage {



	public FormsPage(TestResources resources, HasWidgets toolbarContentArea, int toolbarIndex) {
		super(resources, toolbarContentArea, toolbarIndex);
		addPage(new DateFormPage());
		addPage(new EditorFormPage());

	}



	@Override
	public String getName() {
		return "Forms";
	}
}
