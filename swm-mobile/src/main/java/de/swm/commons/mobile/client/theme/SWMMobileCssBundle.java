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
package de.swm.commons.mobile.client.theme;

import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.DataResource;

import de.swm.commons.mobile.client.theme.base.BaseSWMMobileCssBundle;
import de.swm.commons.mobile.client.theme.components.*;


/**
 * Base class for a ClientBundle implementation. A concrete ClientBudle e.g. {@link BaseSWMMobileCssBundle} represents a
 * Theme instance (including css and other resources).
 * 
 */
public interface SWMMobileCssBundle {
	
	/**
	 * General css resources for text
	 * @return  css resources for text
	 */
	TextCss getTextCss();

	/**
	 * Represents a css resource for different kinds of error messages.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	ConnectionListItemCss getConnectionListItemCss();



	/**
	 * Represents a css resource for different kinds of error messages.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	FlipTimePanelCss getFlipTimePanelCss();



	/**
	 * Represents a css resource for different kinds of error messages.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	ErrorCss getErrorCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	HeaderCss getHeaderCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	PageCss getPageCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	DecoratedListItemCss getDecoratedListItemCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	ButtonCss getButtonCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	ScrollPanelCss getScrollPanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	ListPanelCss getListPanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	SlidePanelCss getSlidePanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	TabPanelCss getTabPanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	AccordionPanelCss getAccordionPanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	TextBoxCss getTextBoxCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	DisplayCss getDisplayCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	CheckRadioBoxCss getCheckRadioBoxCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	FlipSwitchCss getFlipSwitchCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	SliderCss getSliderCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	DropDownCss getDropDownCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	ToolbarPanelCss getToolbarPanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	HorizontalVerticalPanelCss getHorizontalVerticalPanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	PopupsCss getPopupsCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	IndexPanelCss getIndexPanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	CommandPanelCss getCommandPanelCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	TransitionsCss getTransitionsCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	ListPanelFlexCss getListPanelFlexCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	FlexCss getFlexCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	MapCss getMapCss();


	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	TreePanelCss getTreePanelCss();

	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	TripListItemCss getTripListItemCss();



	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	SearchBoxCss getSearchBoxCss();

	/**
	 * Defines one part of the SWMMobile widget system.
	 *
	 * @return the css representation, see {@link CssResource}
	 */
	NotificationBoxCss getNotificationBox();



	/**
	 * Resource used inside a CSS Bundle.
	 * 
	 * @return trhe ressource.
	 */
	DataResource searchSearchImage();



	/**
	 * Resource used inside a CSS Bundle.
	 * 
	 * @return trhe ressource.
	 */
	DataResource searchClearImage();



	/**
	 * Resource used inside a CSS Bundle.
	 * 
	 * @return trhe ressource.
	 */
	DataResource searchClearTouchedImage();

	/**
	 * Defines one part of the SWMMobile widget system.
	 * 
	 * @return the css representation, see {@link CssResource}
	 */
	SelectPanelCss getSelectPanelCss();

	AnimationCss getAnimationCss();
}
