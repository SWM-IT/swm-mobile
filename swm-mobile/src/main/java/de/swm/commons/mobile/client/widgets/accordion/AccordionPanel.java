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
package de.swm.commons.mobile.client.widgets.accordion;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.base.PanelBase;


/**
 * An AccordionPanel is an widget that can be expanded or collapsed.
 * 
 * <pre>
 * <m:AccordionPanel ui:field="accordion">
 * 		<m:AccordionStack>
 * 			<m:AccordionHeader>
 * 				<g:Label>Accordion Stack 1</g:Label>
 * 			</m:AccordionHeader>
 * 			<m:AccordionContent>
 * 				<g:Label>Accordion Stack 1 Content</g:Label>
 * 				<g:Label>Accordion Stack 4 Content</g:Label>
 * 			</m:AccordionContent>
 * 		</m:AccordionStack>
 * 	</m:AccordionPanel>
 * </pre>
 * 
 */
public class AccordionPanel extends PanelBase {

	/**
	 * Default constructor.
	 */
	public AccordionPanel() {
		addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getAccordionPanelCss().accordion());
	}



	/**
	 * Returns the accoordion stack.
	 * 
	 * @param index
	 *            the index.
	 * @return the stack
	 */
	public AccordionStack getStack(int index) {
		return (AccordionStack) myFlowPanel.getWidget(index);
	}
}
