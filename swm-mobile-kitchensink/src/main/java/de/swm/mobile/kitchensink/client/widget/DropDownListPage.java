package de.swm.mobile.kitchensink.client.widget;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.DropDownItem;
import de.swm.commons.mobile.client.widgets.DropDownList;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.itf.IProvidesKeyAndValue;
import de.swm.mobile.kitchensink.client.Application;



public class DropDownListPage extends SimplePage {

	@UiField
	DropDownList<String> cars;
	@UiField SimpleHeaderPanel header;

	private static DropDownListPageUiBinder uiBinder = GWT.create(DropDownListPageUiBinder.class);

	interface DropDownListPageUiBinder extends UiBinder<Widget, DropDownListPage> {
	}



	public DropDownListPage() {
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
		return DropDownListPage.class.getName();
	}

}
