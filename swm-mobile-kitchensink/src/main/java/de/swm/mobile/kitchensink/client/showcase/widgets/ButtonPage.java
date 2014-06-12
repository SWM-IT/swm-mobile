package de.swm.mobile.kitchensink.client.showcase.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.ShowcaseAnnotations;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;


public class ButtonPage extends ShowcaseDetailPage {

	private static ButtonPageUiBinder uiBinder = GWT.create(ButtonPageUiBinder.class);

	@UiField SimpleHeaderPanel header;


	interface ButtonPageUiBinder extends UiBinder<Widget, ButtonPage> {
	}



	public ButtonPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
	}

	@ShowcaseAnnotations.ShowcaseSource
	@Override
	public SimpleHeaderPanel getHeaderPanel() {
		return header;
	}


	@UiHandler("ok1")
	public void onClickOk1(ClickEvent e) {
		Window.alert("You clicked OK1");
	}



	@UiHandler("ok2")
	public void onClickOk2(ClickEvent e) {
		Window.alert("You clicked OK2");
	}



	@UiHandler("cancel2")
	public void onClickCancel2(ClickEvent e) {
		Window.alert("You clicked Cancel2");
	}



	@UiHandler("yes3")
	public void onClickYes3(ClickEvent e) {
		Window.alert("You clicked Yes3");
	}



	@UiHandler("no3")
	public void onClickNo3(ClickEvent e) {
		Window.alert("You clicked No3");
	}



	@UiHandler("cancel3")
	public void onClickCancel(ClickEvent e) {
		Window.alert("You clicked Cancel3");
	}

	
	@UiHandler("img")
	public void onClickImageButton(ClickEvent e) {
		Window.alert("You clicked Image Button");
	}


	@Override
	public String getName() {
		return "Buttons";
	}
}
