/*
 * Beispielhafte Seite mit Canvas.
 * 
 * @author wimmel.guido
 * @copyright (C) 2011, SWM Services GmbH
 */

package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.mobile.kitchensink.client.Application;

public class CanvasPanelPage extends SimplePage {

	 // canvas size, in px
	 static final int height = 400;
	 static final int width = 400;
	
	@UiField SimpleHeaderPanel header;
	@UiField HTMLPanel htmlPanel;
	
	private Canvas canvas;
	private Context2d context;
	
	private static CanvasPanelPageUiBinder uiBinder = GWT
			.create(CanvasPanelPageUiBinder.class);

	interface CanvasPanelPageUiBinder extends UiBinder<Widget, CanvasPanelPage> {
	}

	public CanvasPanelPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
		htmlPanel.add(initCanvas());
		updateCanvas();
	}
	
	public Widget initCanvas() {
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			return new Label("HTML5-Canvas durch Browser nicht unterstützt.");
		}
		
		 // init the canvases
	    canvas.setWidth(width + "px");
	    canvas.setHeight(height + "px");
	    canvas.setCoordinateSpaceWidth(width);
	    canvas.setCoordinateSpaceHeight(height);
	    context = canvas.getContext2d();

	    canvas.addTouchStartHandler(new TouchStartHandler() {

			@Override
			public void onTouchStart(TouchStartEvent event) {
				if (event.getTouches().length() > 0) {
			          Touch touch = event.getTouches().get(0);
			          int mouseX = touch.getRelativeX(canvas.getElement());
			          int mouseY = touch.getRelativeY(canvas.getElement());
			          
			          context.setFillStyle("red");
			          context.fillRect(mouseX, mouseY, 10, 10);
			          
			          context.setStrokeStyle("blue");
			          context.strokeRect(mouseX, mouseY, 10, 10);
			        }
			}
	    	
	    });
	    
	    canvas.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int mouseX = event.getRelativeX(canvas.getElement());
				int mouseY = event.getRelativeY(canvas.getElement());
	    
				  context.setFillStyle("blue");
		          context.fillRect(mouseX, mouseY, 10, 10);
		          
		          context.setStrokeStyle("red");
		          context.strokeRect(mouseX, mouseY, 10, 10);
			}
	    	
	    });


		return canvas;
	}
	
	public void updateCanvas() {
		context.setFillStyle("yellow");
		context.fillRect(0,0,300,300);
	
		context.setFillStyle("blue");
		GWT.log("Font: " + context.getFont());
		context.fillText("Hallo", 50, 50);
		
		context.setFont("30px sans-serif");
		context.fillText("Hallihallo", 100, 100);
	}

	@Override
	public String getName() {
		return "CanvasPage";
	}

}
