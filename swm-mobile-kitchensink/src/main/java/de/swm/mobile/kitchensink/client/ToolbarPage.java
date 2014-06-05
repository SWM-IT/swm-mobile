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
import de.swm.mobile.kitchensink.client.components.*;
import de.swm.mobile.kitchensink.client.panel.PanelsPage;
import de.swm.mobile.kitchensink.client.widget.TextBoxPage;
import de.swm.mobile.kitchensink.client.widget.WidgetsPage;


/**
 * Main entry page.
 * @author wiese.daniel
 * <br>copyright (C) 2011, SWM Services GmbH
 *
 */
public class ToolbarPage extends SimplePage {

	@UiField
	ToolbarPanel toolbar;

	private static MainPage2UiBinder uiBinder = GWT.create(MainPage2UiBinder.class);

	interface MainPage2UiBinder extends UiBinder<Widget, ToolbarPage> {
	}

	private TestPage testPage;
	private WidgetsPage widgetsPage;
	private PanelsPage panelsPage;
	private TextBoxPage textboxPage;
	private EditorPage editorPage;
	private AboutPage aboutPage;



	public ToolbarPage() {
		initWidget(uiBinder.createAndBindUi(this));

		toolbar.addSelectionHandler(new ToolbarSelectionHandler() {

			@Override
			public void toolSelected(int index, ToolbarElement te) {
				HasWidgets content = toolbar.getContentArea();
				if (content != null) {
					switch (index) {
						case 0:
							if (textboxPage == null) {
								TestResources res = GWT.create(TestResources.class);
								textboxPage = new TextBoxPage(res);
								textboxPage.setParent(toolbar.getContentArea());
							}
							content.clear();
							content.add(textboxPage);
							break;
						case 1:
							if (widgetsPage == null) {
								widgetsPage = new WidgetsPage();
								widgetsPage.setParent(toolbar.getContentArea());
							}
							content.clear();
							content.add(widgetsPage);
							break;
						case 2:
							if (panelsPage == null) {
								panelsPage = new PanelsPage();
								panelsPage.setParent(toolbar.getContentArea());
							}
							content.clear();
							content.add(panelsPage);
							break;
						case 3:
							if (testPage == null) {
								testPage = new TestPage();
								testPage.setParent(toolbar.getContentArea());
							}
							content.clear();
							content.add(testPage);
							break;
						case 4:
							if (editorPage == null) {
								editorPage = new EditorPage();
								editorPage.setParent(toolbar.getContentArea());
							}
							content.clear();
							content.add(editorPage);
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

	public void selectToolbar(int index){
		toolbar.selectTool(index, true);
	}


	@Override
	public String getName() {
		return ToolbarPage.class.getName();
	}

}
