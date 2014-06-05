package de.swm.gwt.client.progressbar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Die ProgressBar in PlainGWT.
 * @author Steiner.Christian<br>
 * copyright 2014 SWM Service GmbH 
 */
//Angepasste LoadingIndicatorPopup aus swm-mobile
public class ProgressBarPopup extends PopupPanel {

	private static final double PADDING = 6.0;
	private static final double PADDING_SUBTITLE = 1.0;
	private String loadingText = ".... Loading ....";
	private final Label loadingLabel;
	private final Label loadingSubLabel;



	/**
	 * Default constructor.
	 */
	public ProgressBarPopup() {
		this(false, null);
	}



	/**
	 * Default constructor.
	 *
	 * @param showInfoText true the loading indicator will display info messages
	 */
	public ProgressBarPopup(boolean showInfoText) {
		this(showInfoText, null);
	}



	/**
	 * Default constructor.
	 *
	 * @param loadingImage das Bild, das beim Laden angezeigt wird.
	 */
	public ProgressBarPopup(ImageResource loadingImage) {
		this(false, loadingImage);

	}



	/**
	 * Default constructor.
	 *
	 * @param showInfoText true the loading indicator will display info messages
	 * @param loadingImage das Bild, das beim Laden angezeigt wird.
	 */
	public ProgressBarPopup(boolean showInfoText, ImageResource loadingImage) {
		if (!showInfoText && loadingImage == null) {
			GWT.log("Warnung: Kein Text/Image fuer Progressbar definiert!");
		}
		loadingLabel = new Label(loadingText);
		loadingLabel.addStyleName("pbheader");
		loadingSubLabel = new Label();
		loadingSubLabel.addStyleName("pblabel");

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.addStyleName("progressbar-popup");

		if (showInfoText) {
			vPanel.add(loadingLabel);
			loadingSubLabel.getElement().getStyle().setPaddingLeft(PADDING_SUBTITLE, Style.Unit.PCT);
			vPanel.add(loadingSubLabel);
		}
		if (loadingImage != null) {
			final Image image = new Image(loadingImage);
			image.addStyleName("pbimage");
			vPanel.add(image);
		}

		setWidget(vPanel);


		this.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (event != null) {
					event.stopPropagation();
				}
			}
		}, ClickEvent.getType());

		this.addDomHandler(new DragStartHandler() {
			@Override
			public void onDragStart(DragStartEvent event) {
				if (event != null) {
					event.stopPropagation();
				}
			}
		}, DragStartEvent.getType());

		this.addDomHandler(new DragEndHandler() {
			@Override
			public void onDragEnd(DragEndEvent event) {
				if (event != null) {
					event.stopPropagation();
				}
			}
		}, DragEndEvent.getType());
	}



	/**
	 * Shows the indicator.
	 *
	 * @param glassEffect the glass effect
	 */
	public void showCentered(boolean glassEffect) {
		setGlassEnabled(glassEffect);
		setPopupPositionAndShow(new PositionCallback() {

			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth) / 2;
				int top = (Window.getClientHeight() - offsetHeight) / 2;
				setPopupPosition(left, top);
			}
		});
	}



	/**
	 * Sets the loading label.
	 *
	 * @param loadingText the text to set.
	 */
	public void setLoadingText(String loadingText) {
		this.loadingLabel.setText(loadingText);
	}



	/**
	 * Sets the loading sub label.
	 *
	 * @param loadingSubtitleText the text to set
	 */
	public void setLoadingSubtitleText(String loadingSubtitleText) {
		this.loadingSubLabel.setText(loadingSubtitleText);
	}


}
