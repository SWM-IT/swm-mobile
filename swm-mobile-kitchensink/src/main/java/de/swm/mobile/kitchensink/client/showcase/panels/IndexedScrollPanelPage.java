package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.IndexedScrollPanelWithPager;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import java.util.ArrayList;
import java.util.List;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"IndexedScrollPanelPage.ui.xml"})
public class IndexedScrollPanelPage extends ShowcaseDetailPage {

    private static String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private static IndexedScrollPanelPageUiBinder uiBinder = GWT.create(IndexedScrollPanelPageUiBinder.class);

    interface IndexedScrollPanelPageUiBinder extends UiBinder<Widget, IndexedScrollPanelPage> {
    }

    @UiField
    HTMLPanel content;
    @UiField
	HeaderPanel header;
    @UiField
	IndexedScrollPanelWithPager list;

    public IndexedScrollPanelPage() {
        super(IndexedScrollPanelPage.class);
        initWidget(uiBinder.createAndBindUi(this));
        Application.addDefaultBackButtonHanlder(header);
        for (int i = 0; i < 26; i++) {
            List<ListItem> items = new ArrayList<ListItem>();
            for (int j = 0; j < 5; j++) {
                ListItem item = new ListItem();
                item.add(new Label(letters[i] + " List Item " + j));
                items.add(item);
            }
            list.setIndexedItems(i, items);
        }
        list.updateIndex();
    }

    @Override
    public String getName() {
        return "Index panel";
    }


    @Override
    public HeaderPanel getHeaderPanel() {
        return header;
    }
}
