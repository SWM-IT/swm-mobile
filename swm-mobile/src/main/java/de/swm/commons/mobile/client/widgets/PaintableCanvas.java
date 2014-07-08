package de.swm.commons.mobile.client.widgets;

import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.DragEvent;
import de.swm.commons.mobile.client.event.DragEventsHandler;

/**
 * Canvas element which is able to be painted with different colors and line widths.
 *
 * @author karsunke.franziskus
 *         <br> copyright (C) 2011, SWM Services GmbH
 */
public class PaintableCanvas extends Widget implements DragEventsHandler {

	public static final String STANDARD_COLOR = "black";
	public static final int STANDARD_LINE_WIDTH = 1;

	private static final String PIXEL_CSS = "px";

	private final CanvasElement canvasElement;
	private final Context2d context2d;

	private int lastX;
	private int lastY;

	private boolean inPaintMode = false;

	private String color;
	private int lineWidth;

	private int width;
	private int height;

	public PaintableCanvas(int height, int width) {
		super();

		canvasElement = Document.get().createCanvasElement();
		canvasElement.setId("paintableCanvas");

		this.setElement(canvasElement);

		this.setHeight(height);
		this.setWidth(width);
		this.color = STANDARD_COLOR;
		this.lineWidth = STANDARD_LINE_WIDTH;

		this.context2d = canvasElement.getContext2d();
		this.context2d.fill();


		this.context2d.setFillStyle("white");
		this.context2d.setLineCap(Context2d.LineCap.ROUND);
		this.context2d.fillRect(0, 0, this.width, this.height);
		this.context2d.setShadowBlur(3.0);

		this.addDragHandler();
	}

	private void addDragHandler() {
		DragController.get().addDragEventsHandler(this);
	}

	private void onStart(int x, int y) {
		lastX = x;
		lastY = y;
		inPaintMode = true;
		context2d.beginPath();
		context2d.moveTo(x, y);
	}

	private void onMove(int x, int y) {
		if (inPaintMode) {
			paintLine(lastX, lastY, x, y);
			lastX = x;
			lastY = y;
		}
	}

	public void paintLine(int fromX, int fromY, int toX, int toY) {
		context2d.lineTo(toX, toY);
		context2d.setLineWidth(lineWidth);
		context2d.setStrokeStyle(this.color);
		context2d.stroke();
	}

	private void onEnd(int x, int y) {
		inPaintMode = false;
	}

	public void setHeight(int height) {
		this.setHeight(height + PIXEL_CSS);
		canvasElement.setHeight(height);
		this.height = height;
	}

	public void setWidth(int width) {
		this.setWidth(width + PIXEL_CSS);
		canvasElement.setWidth(width);
		this.width = width;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}

	@Override
	public void onDragStart(DragEvent dragEvent) {
		dragEvent.stopPropagation();

		onStart((int) dragEvent.getX() - this.getElement().getAbsoluteLeft(),
				(int) dragEvent.getY() - this.getElement().getAbsoluteTop());
	}

	@Override
	public void onDragMove(DragEvent dragEvent) {
		dragEvent.stopPropagation();
		onMove((int) dragEvent.getX() - this.getElement().getAbsoluteLeft(),
			   (int) dragEvent.getY() - this.getElement().getAbsoluteTop());
	}

	@Override
	public void onDragEnd(DragEvent dragEvent) {
		dragEvent.stopPropagation();
		onEnd((int) dragEvent.getX() - this.getElement().getAbsoluteLeft(),
			  (int) dragEvent.getY() - this.getElement().getAbsoluteTop());
	}

	public String getImageAsBase64PNG() {
		return canvasElement.toDataUrl("image/png");
	}

	public void setImage(ImageResource resource) {
		this.drawImageStretched(resource);
	}

	public void drawImage(ImageResource resource, final int offsetX, final int offsetY) {
		drawImage(resource, offsetX, offsetY, 1.0, 1.0);
	}

	public void drawImage(ImageResource resource, final double offsetX, final double offsetY, final double scaleX, final double scaleY) {
		// Create Image
		SafeUri uri = resource.getSafeUri();
		final Image img = new Image(uri);

		drawImage(img, offsetX, offsetY, scaleX, scaleY);
	}

	public void drawImageStretched(ImageResource resource) {
		// Create Image
		SafeUri uri = resource.getSafeUri();
		final Image img = new Image(uri);

		drawImageStretched(img);
	}

	public void drawImageStretched(final Image image) {

		// The image has to be added to the canvas after the image has been loaded.
		// Otherwise the bounding box of the image is 0,0,0,0 and nothing will be drawn.
		image.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				// Create the image element.
				ImageElement imgElement = ImageElement.as(image.getElement());

				double imageWidth = imgElement.getWidth();
				double imageHeight = imgElement.getHeight();

				double canvasWidth = canvasElement.getOffsetWidth();
				double canvasHeight = canvasElement.getOffsetHeight();

				double scaleX = canvasWidth / imageWidth;
				double scaleY = canvasHeight / imageHeight;

				double scale = scaleY;
				if (scaleX < scaleY) {
					scale = scaleX;
				}

				// Render the image on the canvas.
				context2d.drawImage(imgElement, 0, 0, scale * imgElement.getWidth(), scale * imgElement.getHeight());

				// Remove the image from the rootpanel
				RootPanel.get().remove(image);
			}
		});

		// Force the image to be loaded. This is probably one of the dirtiest things
		// i have ever done in HTML/Javascript/GWT. FK
		RootPanel.get().add(image);
	}


	public void drawImage(final Image image, final double offsetX, final double offsetY, final double scaleX, final double scaleY) {

		// The image has to be added to the canvas after the image has been loaded.
		// Otherwise the bounding box of the image is 0,0,0,0 and nothing will be drawn.
		image.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				// Create the image element.
				ImageElement imgElement = ImageElement.as(image.getElement());

				// Render the image on the canvas.
				context2d.drawImage(imgElement, offsetX, offsetY, scaleX * imgElement.getWidth(), scaleY * imgElement.getHeight());

				// End of the dirty part :-)
				RootPanel.get().remove(image);
			}
		});

		// Force the image to be loaded. This is probably one of the dirtiest things
		// i have ever done in HTML/Javascript/GWT. FK
		RootPanel.get().add(image);
	}
}
