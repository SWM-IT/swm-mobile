package de.swm.mobile.kitchensink.client.showcase.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"TextBoxPage.ui.xml"})
public class TextBoxPage extends ShowcaseDetailPage {

	@UiField
	SimpleHeaderPanel header;

	private static TextBoxPageUiBinder uiBinder = GWT.create(TextBoxPageUiBinder.class);

	@Override
	public SimpleHeaderPanel getHeaderPanel() {
		return header;
	}

	interface TextBoxPageUiBinder extends UiBinder<Widget, TextBoxPage> {
	}


	public TextBoxPage() {
		super(TextBoxPage.class);
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public String getName() {
		return "Text box page";
	}
}
