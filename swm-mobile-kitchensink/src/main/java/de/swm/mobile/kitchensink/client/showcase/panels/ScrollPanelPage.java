package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"ScrollPanelPage.ui.xml"})
public class ScrollPanelPage extends ShowcaseDetailPage {

    @UiField
	HeaderPanel header;

    private static ScrollPanelPageUiBinder uiBinder = GWT
            .create(ScrollPanelPageUiBinder.class);


    interface ScrollPanelPageUiBinder extends UiBinder<Widget, ScrollPanelPage> {
    }

    public ScrollPanelPage() {
        super(ScrollPanelPage.class);
        initWidget(uiBinder.createAndBindUi(this));
        Application.addDefaultBackButtonHanlder(header);
    }

    @Override
    public String getName() {
        return "Scroll pannel";
    }

    @Override
    public HeaderPanel getHeaderPanel() {
        return header;
    }

}