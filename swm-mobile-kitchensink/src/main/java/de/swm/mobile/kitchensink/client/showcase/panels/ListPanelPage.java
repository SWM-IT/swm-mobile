package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"ListPanelPage.ui.xml"})
public class ListPanelPage extends ShowcaseDetailPage {

    @UiField
    ListPanel list1, list2;
    @UiField
	HeaderPanel header;

    private static ListPanelPageUiBinder uiBinder = GWT.create(ListPanelPageUiBinder.class);


    interface ListPanelPageUiBinder extends UiBinder<Widget, ListPanelPage> {
    }


    public ListPanelPage() {
        super(ListPanelPage.class);
        initWidget(uiBinder.createAndBindUi(this));
        Application.addDefaultBackButtonHanlder(header);
    }

    @Override
    public HeaderPanel getHeaderPanel() {
        return header;
    }


    @UiHandler("list1")
    void onList1SelectionChanged(SelectionChangedEvent e) {
        Window.alert("You select item " + e.getSelection() + " on list 1.");
    }


    @UiHandler("list2")
    void onList2SelectionChanged(SelectionChangedEvent e) {
        Window.alert("You select item " + e.getSelection() + " on list 2.");
    }


    @Override
    public String getName() {
        return "List page";
    }

}
