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

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import de.swm.commons.mobile.client.theme.SWMMobileCssBundle;
import de.swm.commons.mobile.client.theme.components.*;


/**
 * Bright SWM Mobile style. Details see {@link de.swm.commons.mobile.client.theme.SWMMobileCssBundle}.
 * @author karsunke.franziskus
 * 			copyright (C) 2012, SWM Services GmbH
 */
public interface BrightSWMMobileCssBundle extends SWMMobileCssBundle, ClientBundle {

	@Override
	@Source("css/notificationbox.css")
	NotificationBoxCss getNotificationBox();

	@Override
	@Source("css/text.css")
	TextCss getTextCss();
	
	@Override
	@Source("css/connectionListItem.css")
	ConnectionListItemCss getConnectionListItemCss();



	@Override
	@Source("css/fliptimepanel.css")
	FlipTimePanelCss getFlipTimePanelCss();



	@Override
	@Source("css/error.css")
	ErrorCss getErrorCss();



	@Override
	@Source("css/header.css")
	HeaderCss getHeaderCss();



	@Override
	@Source("css/page.css")
	PageCss getPageCss();



	@Override
	@Source("css/decoratedListItem.css")
	DecoratedListItemCss getDecoratedListItemCss();



	@Override
	@Source("css/button.css")
	ButtonCss getButtonCss();



	@Override
	@Source({ "css/scrollpanel.css", "css/page.css" })
	ScrollPanelCss getScrollPanelCss();



	@Override
	@Source({ "css/listpanel.css", "css/scrollpanel.css", "css/page.css" })
	ListPanelCss getListPanelCss();



	@Override
	@Source({ "css/listpanel-flex.css", "css/listpanel.css", "css/scrollpanel.css", "css/page.css" })
	ListPanelFlexCss getListPanelFlexCss();



	@Override
	@Source("css/slide.css")
	SlidePanelCss getSlidePanelCss();



	@Override
	@Source("css/slider.css")
	SliderCss getSliderCss();



	@Override
	@Source({ "css/tabpanel.css", "css/page.css" })
	TabPanelCss getTabPanelCss();



	@Override
	@Source("css/accordion.css")
	AccordionPanelCss getAccordionPanelCss();



	@Override
	@Source("css/textbox.css")
	TextBoxCss getTextBoxCss();



	@Override
	@Source("css/display.css")
	DisplayCss getDisplayCss();



	@Override
	@Source({ "css/check-radio-box.css", "css/page.css", "css/display.css" })
	CheckRadioBoxCss getCheckRadioBoxCss();



	@Override
	@Source("css/flipswitch.css")
	FlipSwitchCss getFlipSwitchCss();



	@Override
	@Source({ "css/dropdown.css", "css/page.css" })
	DropDownCss getDropDownCss();



	@Override
	@Source("css/horizontal-vertical-panel.css")
	HorizontalVerticalPanelCss getHorizontalVerticalPanelCss();



	@Override
	@Source("css/toolbarpanel.css")
	ToolbarPanelCss getToolbarPanelCss();



	@Override
	@Source("css/popups.css")
	PopupsCss getPopupsCss();



	@Override
	@Source("css/indexpanel.css")
	IndexPanelCss getIndexPanelCss();



	@Override
	@Source("css/commandpanel.css")
	CommandPanelCss getCommandPanelCss();



	@Override
	@Source("css/transitions.css")
	TransitionsCss getTransitionsCss();



	@Override
	@Source("css/flexstyle.css")
	FlexCss getFlexCss();



	@Override
	@Source("css/map.css")
	MapCss getMapCss();

	@Override
	@Source("css/treepanel.css")
	TreePanelCss getTreePanelCss();


	@Override
	@Source("css/trip-list-item.css")
	TripListItemCss getTripListItemCss();



	@Override
	@Source("css/searchbox.css")
	SearchBoxCss getSearchBoxCss();



	@Override
	@Source("images/search/glass.png")
	DataResource searchSearchImage();



	@Override
	@Source("images/search/search_clear.png")
	DataResource searchClearImage();

	@Source("images/checkbox.png")
	DataResource checkBox();

	@Override
	@Source("images/search/search_clear_touched.png")
	DataResource searchClearTouchedImage();

	@Override
	@Source("css/selectpanel.css")
	SelectPanelCss getSelectPanelCss();

	@Override
	@Source("css/animation.css")
	AnimationCss getAnimationCss();

}
