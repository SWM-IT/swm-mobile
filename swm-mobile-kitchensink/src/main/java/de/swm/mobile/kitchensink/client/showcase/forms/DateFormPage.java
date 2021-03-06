package de.swm.mobile.kitchensink.client.showcase.forms;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.date.DateTextBox;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

//TODO: Refactoring neue Struktur

@ShowcaseSource
@ShowcaseUiXML({"DateFormPage.ui.xml"})
public class DateFormPage extends ShowcaseDetailPage {

    private static DatePageUiBinder uiBinder = GWT.create(DatePageUiBinder.class);


    interface DatePageUiBinder extends UiBinder<Widget, DateFormPage> {
    }

    @UiField
    DateTextBox dateBox;

    @UiField
	HeaderPanel header;

    public DateFormPage() {
        super(DateFormPage.class);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public HeaderPanel getHeaderPanel() {
        return header;
    }

    @Override
    public String getName() {
        return "Date form page";
    }
}
