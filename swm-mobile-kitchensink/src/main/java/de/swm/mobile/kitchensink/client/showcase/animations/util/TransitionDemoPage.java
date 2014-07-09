package de.swm.mobile.kitchensink.client.showcase.animations.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.gwt.client.mobile.Direction;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

public class TransitionDemoPage extends SimplePage {

    @UiField
	HeaderPanel header;

    @UiField
    ListPanel list;

    private static TransitionPageUiBinder uiBinder = GWT
            .create(TransitionPageUiBinder.class);

    interface TransitionPageUiBinder extends UiBinder<Widget, TransitionDemoPage> {
    }

    public TransitionDemoPage() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("list")
    void onListSelectionChanged(SelectionChangedEvent e) {
    }

    @Override
    public String getName() {
        return "transitionDemo";
    }

    public void setParentPage(final ShowcaseDetailPage previousPage) {
        setParent(previousPage.getParentPage());
        header.setLeftButtonClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                goTo(previousPage, Direction.LEFT);
            }
        });
    }

}
