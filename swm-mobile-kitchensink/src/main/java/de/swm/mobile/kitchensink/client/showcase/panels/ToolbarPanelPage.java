package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.ScrollPanel;
import de.swm.commons.mobile.client.widgets.toolbar.ToolbarContent;
import de.swm.commons.mobile.client.widgets.toolbar.ToolbarElement;
import de.swm.commons.mobile.client.widgets.toolbar.ToolbarPanel;
import de.swm.commons.mobile.client.widgets.toolbar.ToolbarPanel.ToolbarSelectionHandler;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"ToolbarPanelPage.ui.xml"})
public class ToolbarPanelPage extends ShowcaseDetailPage {

	@UiField
	HeaderPanel header;
	@UiField ToolbarPanel toolbar;
	
	private static ToolbarPanelPageUiBinder uiBinder = GWT.create(ToolbarPanelPageUiBinder.class);


    interface ToolbarPanelPageUiBinder extends UiBinder<Widget, ToolbarPanelPage> {}

	public ToolbarPanelPage() {
        super(ToolbarPanelPage.class);
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		toolbar.addSelectionHandler(new ToolbarSelectionHandler() {

			@Override
			public void toolSelected(int index, ToolbarElement te) {
				ToolbarContent content = te.getContent();
				if (content != null) {
					ScrollPanel scroll = new ScrollPanel();
					ListPanel list = new ListPanel();
					content.clear();
					content.add(scroll);
					scroll.add(list);
					for (int i = 0; i < 20; i++) {
						ListItem item = new ListItem();
						item.add(new Label("Item " + i));
						list.add(item);
					}
				}
			}
		});

	}

    @Override
    public HeaderPanel getHeaderPanel() {
        return header;
    }

	@Override
	public String getName() {
		return "Toolbar panel";
	}

}
