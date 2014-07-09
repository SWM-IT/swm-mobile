/*
 * Copyright 2011 SWM Services GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.client.widgets.date;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.widgets.*;

import java.util.Date;


/**
 * Popup to select date and time.
 */
public class DatePopup extends PopupPanel {

	/**
	 * Additional style: margin.
	 */
	private static final double MARGIN_YEAR = 12.0;

	/**
	 * Additional style: margin.
	 */
	private static final double MARGIN_PIXEL_SIZE_4_0 = 4.0;

	/**
	 * Number Formatter for two-digit number strings.
	 */
	private final NumberFormat numFormat = NumberFormat.getFormat("00");

	/**
	 * Date selection handler.
	 */
	public interface DateSelectionHandler {

		/**
		 * Sets the selected date.
		 *
		 * @param selectedDate the selected date
		 */
		void dateSelected(Date selectedDate);


		/**
		 * Called, if date selection was canceled.
		 */
		void dateSelectionCancelled();

	}

	/**
	 * Defines the different fields in the popup.
	 */
	private enum DateField {
		DAY, MONTH, YEAR, HOUR, MINUTE
	}

	/**
	 * The underlying selection handler.
	 */
	protected final DateSelectionHandler selectionHandler;

	/**
	 * Label displaying the day.
	 */
	private TextBox dayLabel;

	/**
	 * Label displaying the month.
	 */
	private TextBox monthLabel;

	/**
	 * Label displaying the year.
	 */
	private TextBox yearLabel;

	/**
	 * Label displaying the hour.
	 */
	private TextBox hourLabel;

	/**
	 * Label displaying the minute.
	 */
	private TextBox minuteLabel;

	/**
	 * Delegate for date calculations.
	 */
	private DateCalculation dateCalc;

	private final VerticalPanel mainPanel;

	private final DateStyle dateStyle;

	private TextBox dateTextBox;

	private final Label dateTimeSelectionCaption;

	private DropDownList<String> dropDown;


	/**
	 * Default constructor.
	 *
	 * @param givenDate date to start with
	 * @param dateStyle der date style
	 * @param handler   date selection handler
	 */
	public DatePopup(Date givenDate, final DateStyle dateStyle, DateSelectionHandler handler) {

		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().datePopup());
		setGlassStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().dateGlassPanel());

		selectionHandler = handler;
		if (givenDate == null) {
			givenDate = new Date();
		}

		this.dateStyle = dateStyle;
		dateCalc = new DateCalculation(givenDate);

		mainPanel = new VerticalPanel();
		dateTimeSelectionCaption = new Label(SWMMobile.getI18N().dateTimeSelectionCaption());
		mainPanel.add(dateTimeSelectionCaption);
		dateTimeSelectionCaption.setVisible(false);

		if (SWMMobile.getOsDetection().isIOs() && (SWMMobile.getOsDetection().isIOS5() || SWMMobile.getOsDetection()
				.isIOS6()) || SWMMobile.getOsDetection().isIOS7()) {
			renderIOS5DateBox(givenDate, dateStyle);
		} else {
			renderDateBox(dateStyle);
		}

		setWidget(mainPanel);
	}

	/**
	 * Adds another user-defined widget to the date popup, e.g. for additional options when choosing the date.
	 *
	 * @param widget   additional widget
	 * @param position of the widget in the main vertical panel (0=first widget)
	 */
	public void addWidget(Widget widget, int position) {
		mainPanel.insert(widget, position);
	}

	/**
	 * Adds a drop down list to choose times relative to the current time (e.g., "in 10 Minutes").
	 *
	 * @param choiceTitles          titles of the available choices
	 * @param choiceRelativeMinutes for each choice, difference to the current time in minutes
	 * @param initialChoice         initially selected choice - must be one of choiceRelativeMinutes, or null if no selection
	 * @param includeNowButton      whether a "now" button should be included to choose the current time
	 * @param closeOnChoice         whether the date popup should be immediately closed when a relative choice is made
	 */
	public void addRelativeTimeChooser(String[] choiceTitles, int[] choiceRelativeMinutes,
									   Integer initialChoice, final boolean includeNowButton, final boolean closeOnChoice) {
		if (choiceTitles.length != choiceRelativeMinutes.length) {
			throw new IllegalArgumentException("Must supply same number of choiceTitles and choiceRelativeMilliseconds!");
		}

		HorizontalPanel relativeTimeChooserPanel = new HorizontalPanel();
		relativeTimeChooserPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().dateRelativeTimeChooserPanel());
		if (includeNowButton) {
			Button nowButton = new Button(SWMMobile.getI18N().nowButton(), new ClickHandler() {

				/**
				 * {@inheritDoc}
				 */
				@Override
				public void onClick(ClickEvent event) {
					Date currentDate = new Date();
					if (closeOnChoice) {
						hide();
						selectionHandler.dateSelected(currentDate);
					} else {
						updateDate(currentDate);
					}
				}

			});
			relativeTimeChooserPanel.add(nowButton);
		}

		Label label = new Label(SWMMobile.getI18N().relativeTimeChooserLabel());
		relativeTimeChooserPanel.add(label);

		dropDown = new DropDownList<String>();
		dropDown.add(new DropDownItem(null, ""));
		for (int i = 0; i < choiceTitles.length; i++) {
			dropDown.add(new DropDownItem(new Integer(choiceRelativeMinutes[i]).toString(), choiceTitles[i]));
		}

		if (initialChoice != null) {
			// TODO: possibly update date selector time for initial choice?
			dropDown.setValue(initialChoice.toString());
		}

		dropDown.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if ((event.getValue() == null) || event.getValue().equals("") || event.getValue().equals("null")) {
					return;
				}
				int relativeMinutes = Integer.parseInt(event.getValue().toString());
				Date newDate = new Date(new Date().getTime() + relativeMinutes * 60000);
				if (closeOnChoice) {
					hide();
					selectionHandler.dateSelected(newDate);
				} else {
					updateDate(newDate);
				}
			}

		});

		relativeTimeChooserPanel.add(dropDown);
		mainPanel.insert(relativeTimeChooserPanel, 0);
	}

	/**
	 * Clears the relative time displayed in the relative time chooser.
	 */
	private void clearRelativeTime() {

		if (dropDown != null) {
			dropDown.setValue(null);
		}
	}

	/**
	 * Sets the visibility of the caption for the date/time selection (default: caption is not visible).
	 *
	 * @param visible visibility of caption for date/time selection
	 */
	public void setDateTimeSelectionCaptionVisible(boolean visible) {
		dateTimeSelectionCaption.setVisible(visible);
	}

	/**
	 * Renders the date box for devices where native date input field is not avalilable.
	 *
	 * @param givenDate
	 * @param dateStyle
	 */
	private void renderIOS5DateBox(final Date givenDate, final DateStyle dateStyle) {

		final InputElement dateInput = createNumberInputElement(dateStyle.getHtmlInputType());

		dateInput.focus();
		dateTextBox = TextBox.wrap(dateInput);
		dateTextBox.setSize("95%", "20px");

		dateTextBox.setValue(dateCalc.formatToRfc3339(givenDate, dateStyle, true));
		// TODO: on value change, call clearRelativeTime() (special handling necessary)

		HorizontalPanel dateInputPanel = new HorizontalPanel();
		dateInputPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().dateInputPanel());
		dateInputPanel.add(dateTextBox);
		mainPanel.add(dateInputPanel);

		Widget commandPanel = createCommandPanel(new ClickHandler() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onClick(ClickEvent event) {
				hide();
				selectionHandler.dateSelected(dateCalc.parseRfc3339(dateTextBox.getText().trim(), dateStyle, true));
			}

		});
		mainPanel.add(commandPanel);

		dateTextBox.setFocus(true);
	}


	/**
	 * Renders the date box for devices where native date input field is not avalilable.
	 */
	private void renderDateBox(DateStyle dateStyle) {
		HorizontalPanel spinnerPanel = new HorizontalPanel();
		spinnerPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().dateSpinnerPanel());

		VerticalPanel dayPanel = createSpinner(DateField.DAY);
		dayPanel.getElement().getStyle().setMarginRight(MARGIN_PIXEL_SIZE_4_0, Unit.PX);
		spinnerPanel.add(dayPanel);

		VerticalPanel monthPanel = createSpinner(DateField.MONTH);
		monthPanel.getElement().getStyle().setMarginRight(MARGIN_PIXEL_SIZE_4_0, Unit.PX);
		spinnerPanel.add(monthPanel);

		VerticalPanel yearPanel = createSpinner(DateField.YEAR);
		yearPanel.getElement().getStyle().setMarginRight(MARGIN_YEAR, Unit.PX);
		spinnerPanel.add(yearPanel);

		VerticalPanel hourPanel = createSpinner(DateField.HOUR);
		spinnerPanel.add(hourPanel);

		Label separatorLabel = new Label(":");
		separatorLabel.setVisible(false);
		separatorLabel.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		spinnerPanel.add(separatorLabel);

		VerticalPanel minutePanel = createSpinner(DateField.MINUTE);
		spinnerPanel.add(minutePanel);

		if (DateStyle.DATE.equals(dateStyle) || DateStyle.DATETIME.equals(dateStyle)) {
			dayPanel.setVisible(true);
			monthPanel.setVisible(true);
			yearPanel.setVisible(true);
		}

		if (DateStyle.TIME.equals(dateStyle) || DateStyle.DATETIME.equals(dateStyle)) {
			hourPanel.setVisible(true);
			separatorLabel.setVisible(true);
			minutePanel.setVisible(true);
		}

		mainPanel.add(spinnerPanel);

		Widget commandPanel = createCommandPanel(new ClickHandler() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onClick(ClickEvent event) {
				hide();
				Date selectedDate = dateCalc.getDate();
				selectionHandler.dateSelected(selectedDate);
			}

		});
		mainPanel.add(commandPanel);
	}

	/**
	 * Creates the command panel (with the buttons to confirm or cancel the date choice).
	 * Can be overridden if command panel should contain different widgets.
	 *
	 * @param okClickHandler ClickHandler that is called when the confirm button is pressed and which parses the date.
	 * @return Widget for Command Panel
	 */
	protected Widget createCommandPanel(final ClickHandler okClickHandler) {
		HorizontalPanel commandPanel = new HorizontalPanel();
		commandPanel.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().dateCommandPanel());
		Button okButton = new Button(SWMMobile.getI18N().confirmButton(), okClickHandler);
		okButton.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().dateOkButton());
		commandPanel.add(okButton);
		Button cancelButton = new Button(SWMMobile.getI18N().cancelButton(), new ClickHandler() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onClick(ClickEvent event) {
				hide();
				selectionHandler.dateSelectionCancelled();
			}

		});
		cancelButton.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().dateCancelButton());
		commandPanel.add(cancelButton);
		return commandPanel;
	}

	/**
	 * Will display the popup centered.
	 *
	 * @param glassEffect true if glass efect
	 */
	public void showCentered(boolean glassEffect) {
		setGlassEnabled(glassEffect);
		setPopupPositionAndShow(new PositionCallback() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				int left = (Window.getClientWidth() - offsetWidth) / 2;
				int top = (Window.getClientHeight() - offsetHeight) / 2;
				setPopupPosition(left, top);
			}
		});
	}


	/**
	 * Will create the spinner buttons.
	 *
	 * @param df the date field
	 * @return spinner button panel
	 */
	private VerticalPanel createSpinner(final DateField df) {
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setVisible(false);
		ImageButton upButton = new ImageButton(SWMMobile.getTheme().getMGWTImageBundle().arrowup(), new ClickHandler() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void onClick(ClickEvent event) {
				switch (df) {
					case DAY:
						dateCalc.incrementDay();
						break;
					case MONTH:
						dateCalc.incrementMonth();
						break;
					case YEAR:
						dateCalc.incrementYear();
						break;
					case HOUR:
						dateCalc.incrementHour();
						break;
					case MINUTE:
						dateCalc.incrementMinute();
						break;
					default:
						break;
				}
				updateLabels();
				clearRelativeTime();
			}

		});
		vPanel.add(upButton);

		final TextBox label;
		switch (df) {
			case DAY:
				dayLabel = new TextBox();
				dayLabel.setWidth("2rem");
				dayLabel.setText(dateCalc.getDay() + ".");
				label = dayLabel;
				break;
			case MONTH:
				monthLabel = new TextBox();
				monthLabel.setWidth("2rem");
				monthLabel.setText(dateCalc.getMonth() + ".");
				label = monthLabel;
				break;
			case YEAR:
				yearLabel = new TextBox();
				yearLabel.setWidth("4rem");
				yearLabel.setText(dateCalc.getYear());
				label = yearLabel;
				break;
			case HOUR:
				hourLabel = new TextBox();
				hourLabel.setWidth("2rem");
				hourLabel.setText(numFormat.format(dateCalc.getHour()));
				label = hourLabel;
				break;
			case MINUTE:
				minuteLabel = new TextBox();
				minuteLabel.setWidth("2rem");
				label = minuteLabel;
				minuteLabel.setText(numFormat.format(dateCalc.getMinute()));
				break;
			default:
				label = new TextBox();
				break;
		}
		label.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				label.setText("");
			}
		});
		label.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				switch (df) {
					case DAY:
						dateCalc.setDay(label.getText());
						updateLabels();
						clearRelativeTime();
						break;
					case MONTH:
						dateCalc.setMonth(label.getText());
						updateLabels();
						clearRelativeTime();
						break;
					case YEAR:
						dateCalc.setYear(label.getText());
						updateLabels();
						clearRelativeTime();
						break;
					case HOUR:
						dateCalc.setHour(label.getText());
						updateLabels();
						clearRelativeTime();
						break;
					case MINUTE:
						dateCalc.setMinute(label.getText());
						updateLabels();
						clearRelativeTime();
						break;
					default:
						break;
				}

			}
		});

		label.setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getPopupsCss().dateValueLabel());
		vPanel.add(label);

		ImageButton downButton = new ImageButton(SWMMobile.getTheme().getMGWTImageBundle().arrowdown(),
				new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						switch (df) {
							case DAY:
								dateCalc.decrementDay();
								break;
							case MONTH:
								dateCalc.decrementMonth();
								break;
							case YEAR:
								dateCalc.decrementYear();
								break;
							case HOUR:
								dateCalc.decrementHour();
								break;
							case MINUTE:
								dateCalc.decrementMinute();
								break;
							default:
								break;
						}
						updateLabels();
						clearRelativeTime();
					}
				});
		vPanel.add(downButton);

		return vPanel;
	}

	/**
	 * Update the date displayed in the date popup.
	 *
	 * @param newDate date to be displayed
	 */
	private void updateDate(Date newDate) {
		dateCalc = new DateCalculation(newDate);
		if (dateTextBox != null) {
			dateTextBox.setValue(dateCalc.formatToRfc3339(newDate, dateStyle, true));
		} else {
			updateLabels();
		}
	}


	/**
	 * Update the labels with internal state.
	 */
	private void updateLabels() {
		yearLabel.setText(dateCalc.getYear());
		monthLabel.setText(dateCalc.getMonth() + ".");
		dayLabel.setText(dateCalc.getDay() + ".");
		hourLabel.setText(numFormat.format(dateCalc.getHour()));
		minuteLabel.setText(numFormat.format(dateCalc.getMinute()));
	}


	/**
	 * Create the HTML input element.
	 *
	 * @param type the type
	 * @return the input element.
	 */
	private static native InputElement createNumberInputElement(String type) /*-{
																				var e = $doc.createElement("INPUT");
																				e.type = type;
																				return e;
																				}-*/;

}
