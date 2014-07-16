package de.swm.mobile.kitchensink.client.showcase;

import com.google.gwt.user.client.ui.HasWidgets;
import de.swm.mobile.kitchensink.client.base.ShowcaseOverviewPage;
import de.swm.mobile.kitchensink.client.theme.bootstrap.extention.TestResources;
import de.swm.mobile.kitchensink.client.showcase.panels.*;


public class PanelPage extends ShowcaseOverviewPage {



	public PanelPage(TestResources resources, HasWidgets toolbarContentArea, int toolbarIndex) {
		super(resources, toolbarContentArea, toolbarIndex);
		//Die showcase komponenten
//		addPage(new AccordionPanelPage()); //Funktioniert nicht
		addPage(new CanvasPanelPage());
		addPage(new CommandPanelPage());
		addPage(new HeaderPanelPage());
		addPage(new IndexedScrollPanelPage());
		addPage(new ListPanelPage());
		addPage(new PaintableCanvasPage());
		addPage(new ScrollPanelPage());
		addPage(new ScrollPanelPageExperimental());
		addPage(new SlidePanelPage());
		addPage(new TabPanelPage());
		addPage(new ToolbarPanelPage());
		addPage(new TreePanelPage());
		addPage(new WideTreePanelPage());
	}



	@Override
	public String getName() {
		return "Pannels";
	}
}
