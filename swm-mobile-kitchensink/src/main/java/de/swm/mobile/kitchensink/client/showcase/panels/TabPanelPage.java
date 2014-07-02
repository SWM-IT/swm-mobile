package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.TabPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"TabPanelPage.ui.xml"})
public class TabPanelPage extends ShowcaseDetailPage {

    @UiField
    SimpleHeaderPanel header;
    @UiField
    TabPanel tab;

    private static TabPanelPageUiBinder uiBinder = GWT
            .create(TabPanelPageUiBinder.class);


    interface TabPanelPageUiBinder extends UiBinder<Widget, TabPanelPage> {
    }

    public TabPanelPage() {
        super(TabPanelPage.class);
        initWidget(uiBinder.createAndBindUi(this));
        Application.addDefaultBackButtonHanlder(header);
    }

    @Override
    public SimpleHeaderPanel getHeaderPanel() {
        return header;
    }

    @Override
    public String getName() {
        return "Tab panel";
    }

}
