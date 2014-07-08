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
package de.swm.commons.mobile.client.theme.bright;

import com.google.gwt.core.client.GWT;
import de.swm.commons.mobile.client.theme.SWMMobileCssBundle;
import de.swm.commons.mobile.client.theme.SWMMobileImageBundle;
import de.swm.commons.mobile.client.theme.SWMMobileTheme;


/**
 * Defines all resources belonging to this bright style. Details see {@link de.swm.commons.mobile.client.theme.SWMMobileTheme}.
 * @author karsunke.franziskus
 * 			copyright (C) 2012, SWM Services GmbH
 */
public class BrightSWMMobileTheme implements SWMMobileTheme {

	private final BrightSWMMobileCssBundle cssBundle;
	private final BrightSWMMobileImageRessources imageBundle;

	/**
	 * Default constructor.
	 */
	public BrightSWMMobileTheme() {
		imageBundle = GWT.create(BrightSWMMobileImageRessources.class);
		cssBundle = GWT.create(BrightSWMMobileCssBundle.class);
		cssBundle.getNotificationBox().ensureInjected();
		cssBundle.getTextCss().ensureInjected();
		cssBundle.getDisplayCss().ensureInjected();
		cssBundle.getHeaderCss().ensureInjected();
		cssBundle.getPageCss().ensureInjected();
		cssBundle.getDecoratedListItemCss().ensureInjected();
		cssBundle.getButtonCss().ensureInjected();
		cssBundle.getScrollPanelCss().ensureInjected();
		cssBundle.getListPanelCss().ensureInjected();
		cssBundle.getListPanelFlexCss().ensureInjected();
		cssBundle.getSlidePanelCss().ensureInjected();
		cssBundle.getTabPanelCss().ensureInjected();
		cssBundle.getAccordionPanelCss().ensureInjected();
		cssBundle.getTextBoxCss().ensureInjected();
		cssBundle.getCheckRadioBoxCss().ensureInjected();
		cssBundle.getFlipSwitchCss().ensureInjected();
		cssBundle.getSliderCss().ensureInjected();
		cssBundle.getDropDownCss().ensureInjected();
		cssBundle.getHorizontalVerticalPanelCss().ensureInjected();
		cssBundle.getToolbarPanelCss().ensureInjected();
		cssBundle.getPopupsCss().ensureInjected();
		cssBundle.getIndexPanelCss().ensureInjected();
		cssBundle.getCommandPanelCss().ensureInjected();
		cssBundle.getTransitionsCss().ensureInjected();
		cssBundle.getFlexCss().ensureInjected();
		cssBundle.getErrorCss().ensureInjected();
		cssBundle.getFlipTimePanelCss().ensureInjected();
		cssBundle.getConnectionListItemCss().ensureInjected();
		cssBundle.getMapCss().ensureInjected();
		cssBundle.getTripListItemCss().ensureInjected();
		cssBundle.getSearchBoxCss().ensureInjected();
		cssBundle.getSelectPanelCss().ensureInjected();
		cssBundle.getTreePanelCss().ensureInjected();
	}



	@Override
	public SWMMobileCssBundle getMGWTCssBundle() {
		return cssBundle;
	}



	@Override
	public SWMMobileImageBundle getMGWTImageBundle() {
		return imageBundle;
	}

}
