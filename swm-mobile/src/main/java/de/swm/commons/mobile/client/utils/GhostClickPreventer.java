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
package de.swm.commons.mobile.client.utils;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import de.swm.commons.mobile.client.event.DragControllerMobile;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to prevent 'ghost clicks' (extra click events that occur when a touch end event is converted to a click event).
 * Used in {code}DragControllerMobile{code}.
 * 
 * See https://developers.google.com/mobile/articles/fast_buttons?hl=fi-FI
 * 
 * @see DragControllerMobile
 */
public class GhostClickPreventer {
	
	private static  final int GHOST_CLICK_DELAY_MILLIS = 2500;
	private static final int GHOST_CLICK_RADIUS = 25;
	
	List<Point> clickedPoints = new ArrayList<Point>();
	
	public void rememberGhostClick(Point point) {
		clickedPoints.add(point);
		
		Timer timer = new Timer() {

			@Override
			public void run() {
				Point removedP = clickedPoints.remove(0);
			}
			
		};
		timer.schedule(GHOST_CLICK_DELAY_MILLIS);
	}
	
	public boolean preventGhostClick(Event event, Point point) {
		for (Point clickedPoint : clickedPoints) {
			if ( (Math.abs(clickedPoint.xPos() - point.xPos()) < GHOST_CLICK_RADIUS) &&
				 (Math.abs(clickedPoint.yPos() - point.yPos()) < GHOST_CLICK_RADIUS) ) {
				
				event.stopPropagation();
				event.preventDefault();
				
				return true;
			}
		}
		
		return false;
	}

}