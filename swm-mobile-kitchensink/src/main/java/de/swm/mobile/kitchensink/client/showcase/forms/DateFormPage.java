package de.swm.mobile.kitchensink.client.showcase.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.DateTextBox;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

//TODO: Refactoring neue Struktur

@ShowcaseSource
@ShowcaseUiXML({"DateFormPage.ui.xml"})
public class DateFormPage extends ShowcaseDetailPage {

    private static SpinPageUiBinder uiBinder = GWT.create(SpinPageUiBinder.class);


    interface SpinPageUiBinder extends UiBinder<Widget, DateFormPage> {
    }

    @UiField
    DateTextBox dateBox;

    @UiField
    SimpleHeaderPanel header;

    public DateFormPage() {
        super(DateFormPage.class);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public SimpleHeaderPanel getHeaderPanel() {
        return header;
    }

    @Override
    public String getName() {
        return "Date form page";
    }
}
