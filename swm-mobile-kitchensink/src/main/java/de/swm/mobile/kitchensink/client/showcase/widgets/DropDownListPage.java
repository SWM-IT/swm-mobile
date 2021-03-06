package de.swm.mobile.kitchensink.client.showcase.widgets;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.DropDownItem;
import de.swm.commons.mobile.client.widgets.DropDownList;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.commons.mobile.client.widgets.itf.IProvidesKeyAndValue;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"DropDownListPage.ui.xml"})
public class DropDownListPage extends ShowcaseDetailPage {


	@UiField
	DropDownList<String> cars;
	@UiField
	HeaderPanel header;

	private static DropDownListPageUiBinder uiBinder = GWT.create(DropDownListPageUiBinder.class);

	@Override
	public HeaderPanel getHeaderPanel() {
		return header;
	}

	interface DropDownListPageUiBinder extends UiBinder<Widget, DropDownListPage> {
	}



	public DropDownListPage() {
		super(DropDownListPage.class);
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		cars.setKeyValueProvider(new IProvidesKeyAndValue<String>() {

			@Override
			public String getValue(String key) {
				return "Car " + key;
			}



			@Override
			public String getKey(String item) {
				String[] parts = item.split(" ");
				if (parts.length == 2) {
					return parts[1];
				}
				return "Unknown";
			}
		});
		cars.setRenderer(new Renderer<String>() {

			@Override
			public void render(String object, Appendable appendable) throws IOException {
				appendable.append(object);
			}



			@Override
			public String render(String object) {
				return object;
			}
		});
		for (int i = 0; i < 5; i++) {
			cars.add(new DropDownItem((i + 1) + ""));
		}
	}



	@UiHandler("cars")
	public void onValueChangeCar(ValueChangeEvent<String> e) {
		Utils.console(e.getValue());
	}



	@Override
	public String getName() {
		return "Drop down example";
	}

}
