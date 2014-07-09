package de.swm.mobile.kitchensink.client.showcase.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"ButtonPage.ui.xml"})
public class ButtonPage extends ShowcaseDetailPage {

	private static ButtonPageUiBinder uiBinder = GWT.create(ButtonPageUiBinder.class);

	@UiField
	HeaderPanel header;


	interface ButtonPageUiBinder extends UiBinder<Widget, ButtonPage> {
	}


	public ButtonPage() {
		super(ButtonPage.class);
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Initialize this example.
	 */
	@Override
	public HeaderPanel getHeaderPanel() {
		StringBuilder sb = new StringBuilder();
		sb.append("halooo showcase");
		GWT.log(sb.toString());
		return header;
	}

	/**
	 * Handler.
	 */
	@UiHandler("ok1")
	public void onClickOk1(ClickEvent e) {
		Window.alert("You clicked OK1");
	}

	/**
	 * Handler.
	 */
	@UiHandler("ok2")
	public void onClickOk2(ClickEvent e) {
		Window.alert("You clicked OK2");
	}

	/**
	 * Handler.
	 */
	@UiHandler("cancel2")
	public void onClickCancel2(ClickEvent e) {
		Window.alert("You clicked Cancel2");
	}

	/**
	 * Handler.
	 */
	@UiHandler("yes3")
	public void onClickYes3(ClickEvent e) {
		Window.alert("You clicked Yes3");
	}

	/**
	 * Handler.
	 */
	@UiHandler("no3")
	public void onClickNo3(ClickEvent e) {
		Window.alert("You clicked No3");
	}

	/**
	 * Handler.
	 */
	@UiHandler("cancel3")
	public void onClickCancel(ClickEvent e) {
		Window.alert("You clicked Cancel3");
	}

	/**
	 * Handler.
	 */
	@UiHandler("img")
	public void onClickImageButton(ClickEvent e) {
		Window.alert("You clicked Image Button");
	}


	@Override
	public String getName() {
		return "Buttons";
	}
}
