package de.swm.mobile.kitchensink.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.ToolbarElement;
import de.swm.commons.mobile.client.widgets.ToolbarPanel;
import de.swm.commons.mobile.client.widgets.ToolbarPanel.ToolbarSelectionHandler;
import de.swm.mobile.kitchensink.client.components.AboutPage;
import de.swm.mobile.kitchensink.client.components.EditorPage;
import de.swm.mobile.kitchensink.client.components.TestPage;
import de.swm.mobile.kitchensink.client.components.TestResources;
import de.swm.mobile.kitchensink.client.panel.PanelsPage;
import de.swm.mobile.kitchensink.client.showcase.WidgetsPage;
import de.swm.mobile.kitchensink.client.showcase.widgets.TextBoxPage;


/**
 * Main entry page.
 *
 * @author wiese.daniel
 *         <br>copyright (C) 2011, SWM Services GmbH
 */
public class ToolbarPage extends SimplePage {

	@UiField
	ToolbarPanel toolbar;

	private static MainPage2UiBinder uiBinder = GWT.create(MainPage2UiBinder.class);

	interface MainPage2UiBinder extends UiBinder<Widget, ToolbarPage> {
	}


	///NEU
	private WidgetsPage widgetsPage;
	//NEU ENDE

	private TestPage testPage;
	private PanelsPage panelsPage;
	private TextBoxPage textboxPage;
	private EditorPage editorPage;
	private AboutPage aboutPage;


	public ToolbarPage() {
		initWidget(uiBinder.createAndBindUi(this));
		TestResources res = GWT.create(TestResources.class);
		widgetsPage = new WidgetsPage(res, toolbar.getContentArea(), 0);
		toolbar.addSelectionHandler(new ToolbarSelectionHandler() {

			@Override
			public void toolSelected(int index, ToolbarElement te) {
				HasWidgets content = toolbar.getContentArea();
				if (content != null) {
					switch (index) {
						case 0:
							content.clear();
							content.add(widgetsPage);
							break;
						case 1:

							break;
						case 2:

							break;
						case 3:

							break;
						case 4:

							break;
						case 5:
							if (aboutPage == null) {
								aboutPage = new AboutPage();
								aboutPage.setParent(toolbar.getContentArea());
							}
							content.clear();
							content.add(aboutPage);
							break;
						default:
							break;
					}
				}
			}
		});

		toolbar.setBadgeValue(3, "2");

	}

	public void selectToolbar(int index) {
		toolbar.selectTool(index, true);
	}


	@Override
	public String getName() {
		return ToolbarPage.class.getName();
	}

}
