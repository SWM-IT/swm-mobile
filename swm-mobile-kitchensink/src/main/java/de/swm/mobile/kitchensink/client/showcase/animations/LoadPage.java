package de.swm.mobile.kitchensink.client.showcase.animations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.widgets.DecoratedListItem;
import de.swm.commons.mobile.client.widgets.ListItem;
import de.swm.commons.mobile.client.widgets.PagingListPanel;
import de.swm.commons.mobile.client.widgets.PagingListPanel.ListItemProvider;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;
import de.swm.mobile.kitchensink.client.theme.bootstrap.extention.TestResources;

import java.util.ArrayList;
import java.util.List;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"LoadPage.ui.xml"})
public class LoadPage extends ShowcaseDetailPage {

    @UiField
    PagingListPanel list;

    @UiField
    SimpleHeaderPanel header;

    private static LoadPageUiBinder uiBinder = GWT.create(LoadPageUiBinder.class);


    interface LoadPageUiBinder extends UiBinder<Widget, LoadPage> {
    }

    private final TestResources resources;


    public LoadPage() {
        super(LoadPage.class);
        initWidget(uiBinder.createAndBindUi(this));
        resources = GWT.create(TestResources.class);
        list.setShowLoadingIndicator(true);
        list.setPageSize(5);
        list.setProvider(new ListItemProvider() {

            @Override
            public void provideItems(final int startIndex, final int numEntries, final AsyncCallback<List<ListItem>> callback) {
                Timer t = new Timer() {

                    @Override
                    public void run() {
                        List<ListItem> result = new ArrayList<ListItem>();
                        for (int i = 0; i < numEntries; i++) {
                            DecoratedListItem item = new DecoratedListItem(resources.info(), "Test Entry " + (startIndex + i), "Sub Label " + (startIndex + i));
                            result.add(item);
                        }
                        callback.onSuccess(result);
                    }

                };
                if (startIndex == 0) {
                    t.schedule(10);
                } else {
                    t.schedule(2000);
                }
            }
        });
    }


    @UiHandler("list")
    void onListSelectionChanged(SelectionChangedEvent e) {
        switch (e.getSelection()) {
            case 0:
                break;
            case 1:
                break;
        }
    }

    @Override
    public String getName() {
        return "Async loading";
    }

    @Override
    public SimpleHeaderPanel getHeaderPanel() {
        return header;
    }
}
