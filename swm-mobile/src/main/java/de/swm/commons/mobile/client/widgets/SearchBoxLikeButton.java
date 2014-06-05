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
package de.swm.commons.mobile.client.widgets;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasTouchStartHandlers;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.theme.components.SearchBoxCss;
import de.swm.commons.mobile.client.utils.IsSWMMobileWidgetHelper;

/**
 * A button which looks like a search widget.
 *
 * @author Dnaiel.Wiese
 */
public class SearchBoxLikeButton extends Composite implements IsSWMMobileWidget, HasTouchStartHandlers, HasClickHandlers {

	private FlowPanel roundDiv;
	protected final SearchBoxCss css;
	private Label box;
	private IsSWMMobileWidgetHelper myWidgetHelper = new IsSWMMobileWidgetHelper();

	/**
	 * Construct a search box
	 */
	public SearchBoxLikeButton() {
		this(SWMMobile.getTheme().getMGWTCssBundle().getSearchBoxCss());
	}

	/**
	 * Construct a search box with a given css
	 *
	 * @param css the css to use
	 */
	public SearchBoxLikeButton(SearchBoxCss css) {
		this.css = css;
		this.css.ensureInjected();
		FlowPanel main = new FlowPanel();

		main.addStyleName(css.searchBox());

		initWidget(main);

		roundDiv = new FlowPanel();
		roundDiv.addStyleName(css.round());

		box = new Label();
		box.addStyleName(css.inputAsLabel());
		box.getElement().setAttribute("autocapitalize", "off");
		box.getElement().setAttribute("autocorrect", "off");
		box.getElement().setAttribute("type", "search");

		roundDiv.add(box);
		main.add(roundDiv);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onAttach() {
		super.onAttach();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
	}


	/**
	 * <p>
	 * getText
	 * </p>
	 *
	 * @return a {@link String} object.
	 */
	public String getText() {
		return box.getText();
	}

	/**
	 * {@inheritDoc}
	 */
	public void setText(String text) {
		box.setText(text);
	}

	/**
	 * When the widget is loaded first time.
	 */
	@Override
	public void onInitialLoad() {

	}

	/**
	 * When an transition containing this widget ends.
	 */
	@Override
	public void onTransitionEnd() {

	}

	/**
	 * Will set the secondary style.
	 *
	 * @param style the style to set.
	 */
	@Override
	public void setSecondaryStyle(String style) {
		myWidgetHelper.setSecondaryStyle(this, style);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return box.addClickHandler(handler);
	}

	@Override
	public HandlerRegistration addTouchStartHandler(TouchStartHandler handler) {
		return box.addTouchStartHandler(handler);
	}
}
