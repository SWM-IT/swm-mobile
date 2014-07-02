package de.swm.mobile.kitchensink.client.showcase.about;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"AboutDetailPage.ui.xml"})
public class AboutDetailPage extends ShowcaseDetailPage {


	@UiField SimpleHeaderPanel header;
	
	private static AboutPageUiBinder uiBinder = GWT.create(AboutPageUiBinder.class);

    @Override
    public SimpleHeaderPanel getHeaderPanel() {
        return header;
    }

    interface AboutPageUiBinder extends UiBinder<Widget, AboutDetailPage> {}
	

	public AboutDetailPage() {
        super(AboutDetailPage.class);
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		

	}

	@Override
	public String getName() {
		return "About page";
	}
	

}
