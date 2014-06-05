package de.swm.mobile.kitchensink.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.PaintableCanvas;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.icon.IconResources;

public class PaintableCanvasPage extends SimplePage {

	// canvas size, in px
	static final int height = 400;
	static final int width = 400;

	@UiField
	SimpleHeaderPanel header;

	@UiField
	HTMLPanel htmlPanel;

	@UiField
	HTMLPanel canvasHTML;

	@UiField
	ListBox dropdownColor;

	@UiField
	ListBox dropdownLineWidth;

	private PaintableCanvas canvas;

	private static PaintableCanvasPageUiBinder uiBinder = GWT
			.create(PaintableCanvasPageUiBinder.class);

	interface PaintableCanvasPageUiBinder extends UiBinder<Widget, PaintableCanvasPage> {
	}

	public PaintableCanvasPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		canvasHTML.add(initCanvas());

		populateLineWidthDropDown();
		populateColorDropDown();

		IconResources resources = GWT.create(IconResources.class);
		canvas.setImage(resources.bus());
	}

	private void populateLineWidthDropDown() {
		for (int i = 1; i <= 20; i++) {
			dropdownLineWidth.addItem("" + i);
		}

		dropdownLineWidth.setValue(PaintableCanvas.STANDARD_LINE_WIDTH, "" + PaintableCanvas.STANDARD_LINE_WIDTH);
		dropdownLineWidth.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				canvas.setLineWidth(dropdownLineWidth.getSelectedIndex() + 1);
			}
		});


	}

	private void populateColorDropDown() {
		dropdownColor.addItem("black");
		dropdownColor.addItem("green");
		dropdownColor.addItem("yellow");
		dropdownColor.addItem("blue");
		dropdownColor.addItem("pink");
		dropdownColor.addItem("red");
		dropdownColor.addItem("white");

		dropdownColor.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				canvas.setColor(dropdownColor.getValue(dropdownColor.getSelectedIndex()));
			}
		});
	}

	public Widget initCanvas() {
		canvas = new PaintableCanvas(height, width);
		if (canvas == null) {
			return new Label("HTML5-Canvas durch Browser nicht unterstützt.");
		}

		return canvas;
	}

	@Override
	public String getName() {
		return "PaintableCanvasPage";
	}
}
