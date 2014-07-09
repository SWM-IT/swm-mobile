package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.accordion.AccordionPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

//TODO: Funktioniert nicht!
@ShowcaseSource
@ShowcaseUiXML({"AccordionPanelPage.ui.xml"})
public class AccordionPanelPage extends ShowcaseDetailPage {

    @UiField
    SimpleHeaderPanel header;
    @UiField
    AccordionPanel accordion;

    private static AccordionPanelPageUiBinder uiBinder = GWT
            .create(AccordionPanelPageUiBinder.class);


    interface AccordionPanelPageUiBinder extends UiBinder<Widget, AccordionPanelPage> {
    }

    public AccordionPanelPage() {
        super(AccordionPanelPage.class);
        initWidget(uiBinder.createAndBindUi(this));
        Application.addDefaultBackButtonHanlder(header);
    }

    @Override
    public SimpleHeaderPanel getHeaderPanel() {
        return header;
    }

    @Override
    public String getName() {
        return "Accordion panel page";
    }

}
