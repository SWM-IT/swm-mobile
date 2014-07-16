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
@ShowcaseUiXML({"ScrollPanelPageExperimental.ui.xml"})
public class ScrollPanelPageExperimental extends ShowcaseDetailPage {

    @UiField
	HeaderPanel header;

    private static ScrollPanelPageUiBinderExperimental uiBinder = GWT
            .create(ScrollPanelPageUiBinderExperimental.class);


    interface ScrollPanelPageUiBinderExperimental extends UiBinder<Widget, ScrollPanelPageExperimental> {
    }

    public ScrollPanelPageExperimental() {
        super(ScrollPanelPageExperimental.class);
        initWidget(uiBinder.createAndBindUi(this));
        Application.addDefaultBackButtonHanlder(header);
    }

    @Override
    public String getName() {
        return "Scrollpanel (Experimental)";
    }

    @Override
    public HeaderPanel getHeaderPanel() {
        return header;
    }

}