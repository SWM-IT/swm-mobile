package de.swm.commons.mobile.client.utils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.constants.I18NConstants;
import de.swm.commons.mobile.client.event.FastClickHelper;
import de.swm.commons.mobile.client.theme.SWMMobileImageBundle;
import de.swm.commons.mobile.client.widgets.Button;
import de.swm.commons.mobile.client.widgets.*;
import de.swm.commons.mobile.client.widgets.HorizontalPanel;
import de.swm.gwt.client.interfaces.ITypedAction;

import java.util.*;

/**
 * Hilfsklasse um ein popup fenser anzuzeigen.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2012, SWM Services GmbH
 */
public class SwmPopupUtilImpl implements ISwmPopupUtil {

	/**
	 * Erstellt ein HTML-Widget, welches Freizeichen zwischen Komponenten erstellt.
	 */
	private final SWMMobileImageBundle images = SWMMobile.getTheme().getMGWTImageBundle();


	private final I18NConstants appConstants = GWT.create(I18NConstants.class);


	@Override
	public void showPopup(String header, String text) {
		showPopup(false, header, text, null);
	}

	@Override
	public void showPopup(boolean hasCancelButton, String header, String text, final ClickHandler okClickHanlder) {
		List<String> texts = new ArrayList<String>();
		texts.add(text);
		showPopup(hasCancelButton, header, texts, okClickHanlder, null);
	}

	@Override
	public void showPopup(boolean hasCancelButton, String header, List<String> texts,
						  final ClickHandler okClickHanlder) {
		showPopup(hasCancelButton, header, texts, okClickHanlder, null);
	}

	@Override
	public void showPopup(boolean hasCancelButton, String header, String text, final ClickHandler okClickHanlder,
						  final ClickHandler cancelClickHanlder) {
		List<String> texts = new ArrayList<String>();
		texts.add(text);
		showPopup(hasCancelButton, header, texts, appConstants.confirmButton(), appConstants.cancelButton(),
				okClickHanlder, cancelClickHanlder);
	}

	@Override
	public void showPopup(boolean hasCancelButton, String header, List<String> texts, final ClickHandler okClickHanlder,
						  final ClickHandler cancelClickHanlder) {
		showPopup(hasCancelButton, header, texts, appConstants.confirmButton(), appConstants.cancelButton(),
				okClickHanlder, cancelClickHanlder);
	}

	@Override
	public void showPopupYesNo(boolean hasCancelButton, String header, String text, final ClickHandler okClickHanlder,
							   final ClickHandler cancelClickHanlder) {
		showPopup(hasCancelButton, header, text, appConstants.yesButton(), appConstants.noButton(), okClickHanlder,
				cancelClickHanlder);
	}

	@Override
	public void showPopup(boolean hasCancelButton, String header, String text,
						  String popupOkText, String popupCancelText, final ClickHandler okClickHanlder,
						  final ClickHandler cancelClickHanlder) {
		List<String> texts = new ArrayList<String>();
		texts.add(text);
		showPopup(hasCancelButton, header, texts, popupOkText, popupCancelText, okClickHanlder, cancelClickHanlder);
	}

	@Override
	public <T> void showMultiButtonPopup(String header, List<String> texts, List<ItemDesciptor<T>> buttonDesciptors) {

		List<Label> textlabels = new ArrayList<Label>();
		if (texts != null) {
			for (String text : texts) {
				textlabels.add(new Label(text));
			}
		}

		// Sorieren der Buttons
		Collections.sort(buttonDesciptors);

		HorizontalPanel hp = new HorizontalPanel();
		final CommandPopup commandPopup = new CommandPopup(header, this.images.information(),
				getWidgetArray(textlabels, hp));

		for (final ItemDesciptor buttonDesciptor : buttonDesciptors) {
			Button button = new Button();
			button.setText(buttonDesciptor.getText());
			FastClickHelper.addClickHandler(button, new FastClickHelper.FastClickHandler() {
				@Override
				public void onFastClick(FastClickHelper.FastClickEvent event) {
					commandPopup.hide();
					buttonDesciptor.getAction().execute(buttonDesciptor.getType());
				}
			});

			hp.add(button);
			hp.add(createHtmlSpacer());
		}

		commandPopup.showCentered(true);
	}

	@Override
	public CommandPopup buildMultiWidgetPopup(String header, Widget... widgets) {
		return new CommandPopup(header, this.images.information(), widgets);
	}


	@Override
	public void showListPopup(String header, final List<ItemDesciptor> listItemDesciptors) {
		final DropDownList<String> dropDownList = new DropDownList<String>();
		dropDownList.getElement().getStyle().setProperty("margin", "10px");
		final Map<String, ITypedAction> keyValueMap = new HashMap<String, ITypedAction>();

		for (ItemDesciptor descriptor : listItemDesciptors) {
			keyValueMap.put(descriptor.getText(), descriptor.getAction());
			dropDownList.add(new DropDownItem(descriptor.getText(), descriptor.getText()));
		}


		HTMLPanel panel = new HTMLPanel("");
		panel.add(dropDownList);

		HorizontalPanel hp = new HorizontalPanel();

		Button cancel = new Button(appConstants.cancelButton());
		cancel.getElement().getStyle().setProperty("margin", "10px");
		hp.add(cancel);
		hp.getElement().getStyle().setTextAlign(Style.TextAlign.CENTER);

		Widget[] widgets = new Widget[2];
		widgets[0] = panel;
		widgets[1] = hp;

		final CommandPopup popup = new CommandPopup(header, this.images.information(), widgets);
		FastClickHelper.addClickHandler(cancel, new FastClickHelper.FastClickHandler() {

			@Override
			public void onFastClick(FastClickHelper.FastClickEvent event) {
				popup.hide();
			}
		});

		dropDownList.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> stringValueChangeEvent) {
				String value = stringValueChangeEvent.getValue();
				keyValueMap.get(value).execute(value);
				popup.hide();
			}
		});

		popup.showCentered(true);
	}

	/**
	 * Erzeugt immer einen nuen HTML-Spacer.
	 *
	 * @return .
	 */
	private HTML createHtmlSpacer() {
		return new HTML("&nbsp;&nbsp;&nbsp;");
	}

	@Override
	public StatusPopupHandler showStatusPopup(String header, List<String> texts) {

		List<Label> textlabels = new ArrayList<Label>();
		for (String text : texts) {
			textlabels.add(new Label(text));
		}

		HorizontalPanel statusPanel = new HorizontalPanel();
		statusPanel.add(new Image(SWMMobile.getTheme().getMGWTImageBundle().loading()));
		statusPanel.add(this.createHtmlSpacer());
		final Label label = new Label();
		statusPanel.add(label);

		final CommandPopup commandPopup = new CommandPopup(header, this.images.information(),
				getWidgetArray(textlabels, statusPanel));


		commandPopup.showCentered(true);

		return new StatusPopupHandler() {
			@Override
			public void changeStatus(String statusDescription) {
				label.setText(statusDescription);
			}

			@Override
			public void cancelStatusPopup() {
				commandPopup.hide();
			}
		};
	}


	/**
	 * Die Defauls-showPopap-Implementierung.
	 *
	 * @param hasCancelButton    .
	 * @param header             .
	 * @param texts              .
	 * @param popupOkText        .
	 * @param popupCancelText    .
	 * @param okClickHanlder     .
	 * @param cancelClickHanlder .
	 */
	private void showPopup(boolean hasCancelButton, String header, List<String> texts,
						   String popupOkText, String popupCancelText, final ClickHandler okClickHanlder,
						   final ClickHandler cancelClickHanlder) {

		List<Label> textlabels = new ArrayList<Label>();
		for (String text : texts) {
			textlabels.add(new Label(text));
		}
		Button okButton = new Button();
		Button cancelButton = new Button();
		okButton.setText(popupOkText);
		HorizontalPanel hp = new HorizontalPanel();
		hp.add(okButton);

		if (hasCancelButton) {
			hp.add(createHtmlSpacer());
			cancelButton.setText(popupCancelText);
			hp.add(cancelButton);
		}

		final Widget[] widgets = getWidgetArray(textlabels, hp);
		final CommandPopup commandPopup = new CommandPopup(header, images.information(), widgets);

		FastClickHelper.addClickHandler(okButton, new FastClickHelper.FastClickHandler() {

			@Override
			public void onFastClick(FastClickHelper.FastClickEvent event) {
				commandPopup.hide();
				if (okClickHanlder != null) {
					okClickHanlder.onClick(null);
				}
			}
		});

		if (hasCancelButton) {
			FastClickHelper.addClickHandler(cancelButton, new FastClickHelper.FastClickHandler() {

				@Override
				public void onFastClick(FastClickHelper.FastClickEvent event) {
					commandPopup.hide();
					if (cancelClickHanlder != null) {
						cancelClickHanlder.onClick(null);
					}
				}
			});
		}
		commandPopup.showCentered(true);
	}

	/**
	 * Erzeugt ein Widget Array aus den übergebenen Widgets.
	 *
	 * @param labels eine liste von Labels
	 * @param panel  das Panel für die Buttons
	 * @return alle zusammen als Array
	 */
	private Widget[] getWidgetArray(List<Label> labels, PanelBase panel) {
		Widget[] result = new Widget[labels.size() + 1];
		int i;
		for (i = 0; i < labels.size(); i++) {
			result[i] = labels.get(i);
		}
		result[i] = panel;
		return result;
	}

}
