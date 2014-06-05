/*
 * Copyright 2010 Daniel Kurka
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
package com.google.gwt.user.client.impl;

import com.google.gwt.user.client.Element;

/**
 * <p>DOMImplSafariWithTransistion class.</p>
 *
 * @author Daniel Kurka
 * @version $Id: $
 */
public class DOMImplSafariWithTransistion extends DOMImplWebkit {

	/**
	 * <p>Constructor for DOMImplSafariWithTransistion.</p>
	 */
	public DOMImplSafariWithTransistion() {

	}

	
	protected native int eventGetTypeInt0(String eventType)/*-{
		switch (eventType) {
    	//added dom transistionend event
		case "webkitTransitionEnd": return 0x8000000;
		//added dom transistionend event
		case "webkitAnimationEnd": return 0x10000000;
    	default: return -1;
		}
	}-*/;
	
	/** {@inheritDoc} */
	@Override
	public  int eventGetTypeInt(String eventType){
		int type = super.eventGetTypeInt(eventType);
		if(type != -1)
			return type;
		return eventGetTypeInt0(eventType);
			
	}

	/** {@inheritDoc} */
	protected   void sinkEventsImpl(Element elem, int bits){
		sinkEventsImpl0(elem, bits);
	}
	
	protected  native void sinkEventsImpl0(Element elem, int bits) /*-{
	var chMask = (elem.__eventBits || 0) ^ bits;
    elem.__eventBits = bits;
    if (!chMask) return;
   
    if (chMask & 0x00001) elem.onclick       = (bits & 0x00001) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00002) elem.ondblclick    = (bits & 0x00002) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00004) elem.onmousedown   = (bits & 0x00004) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00008) elem.onmouseup     = (bits & 0x00008) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00010) elem.onmouseover   = (bits & 0x00010) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00020) elem.onmouseout    = (bits & 0x00020) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00040) elem.onmousemove   = (bits & 0x00040) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00080) elem.onkeydown     = (bits & 0x00080) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00100) elem.onkeypress    = (bits & 0x00100) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00200) elem.onkeyup       = (bits & 0x00200) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00400) elem.onchange      = (bits & 0x00400) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x00800) elem.onfocus       = (bits & 0x00800) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x01000) elem.onblur        = (bits & 0x01000) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x02000) elem.onlosecapture = (bits & 0x02000) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x04000) elem.onscroll      = (bits & 0x04000) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x08000) elem.onload        = (bits & 0x08000) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchUnhandledEvent : null;
    if (chMask & 0x10000) elem.onerror       = (bits & 0x10000) ?
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x20000) elem.onmousewheel  = (bits & 0x20000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x40000) elem.oncontextmenu = (bits & 0x40000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x80000) elem.onpaste       = (bits & 0x80000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x100000) elem.ontouchstart = (bits & 0x100000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x200000) elem.ontouchmove  = (bits & 0x200000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x400000) elem.ontouchend   = (bits & 0x400000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x800000) elem.ontouchcancel= (bits & 0x800000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x1000000) elem.ongesturestart  =(bits & 0x1000000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x2000000) elem.ongesturechange =(bits & 0x2000000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    if (chMask & 0x4000000) elem.ongestureend    = (bits & 0x4000000) ? 
        @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent : null;
    
	//transistion end
	if (chMask & 0x8000000) {
	if(bits & 0x8000000){
	elem.addEventListener('webkitTransitionEnd', @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent, false);
	}
	}


	//animation end
	if (chMask & 0x10000000) {
	if(bits & 0x10000000){
	elem.addEventListener('webkitAnimationEnd', @com.google.gwt.user.client.impl.DOMImplStandard::dispatchEvent, false);
	}
	}
}-*/;

}
