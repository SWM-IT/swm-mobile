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
package de.swm.commons.mobile.client.page;

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasWidgets;

import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.commons.mobile.client.widgets.SWMMobileWidgetBase;



/**
 * Defines a Transition.
 * 
 */
public class Transition implements EventListener {

	private static final int DEFAULT_TRANSITION_DELAY = 20;
	protected String transitionStyleName;
	protected SWMMobileWidgetBase myFrom, myTo;
	protected boolean myReverse;
	protected HasWidgets myParent;

	/** Defines a transition. **/
	public static final Transition SLIDE = new Transition(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss()
			.slide());

	/** Defines a transition. **/
	public static final Transition SLIDEUP = new Transition(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss()
			.slideup());

	/** Defines a transition. **/
	public static final Transition SLIDEDOWN = new Transition(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss()
			.slidedown());
	/** Defines a transition. **/
	public static final Transition FADE = new Transition(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss()
			.fade());
	/** Defines a transition. **/
	public static final Transition POP = new Transition(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().pop());
	/** Defines a transition. **/
	public static final Transition FLIP = new FlipTransition();
	/** Defines a transition. **/
	public static final Transition SWAP = new SwapTransition();



	/**
	 * Default constructor.
	 * 
	 * @param transitionStyleName
	 *            the style name.
	 */
	public Transition(String transitionStyleName) {
		this.transitionStyleName = transitionStyleName;
	}



	/**
	 * Will perform a transition.
	 * 
	 * @param from
	 *            .
	 * @param to
	 *            .
	 * @param parent
	 *            the parent widget containing form sand to widgets.
	 */
	public static void start(final SWMMobileWidgetBase from, final SWMMobileWidgetBase to, final HasWidgets parent) {
		final Timer timer = new Timer() {
			@Override
			public void run() {
				parent.remove(from);
				parent.add(to);
				to.onTransitionEnd();
			}

		};
		timer.schedule(1);
	}



	/**
	 * Will perform a transition.
	 * 
	 * @param from .
	 * @param to .
	 * @param parent
	 *            the parent widget containing form sand to widgets.
	 * @param reverse
	 *            will be executer in the reverse direction.
	 */
	public void start(SWMMobileWidgetBase from, SWMMobileWidgetBase to, HasWidgets parent, boolean reverse) {
		myFrom = from;
		myTo = to;
		myParent = parent;
		myReverse = reverse;
		prepare();
		start();
	}



	/**
	 * Will prepare the next transition.
	 */
	protected void prepare() {
		myFrom.addStyleName(transitionStyleName + " " + SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().out());
		myTo.addStyleName(transitionStyleName + " " + SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().in());
		if (myReverse) {
			myFrom.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().reverse());
			myTo.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().reverse());
		}
		myParent.add(myTo);
	}



	/**
	 * Will start the next transition.
	 */
	protected void start() {
		registerTransitionEndEvent();
		final Timer timer = new Timer() {
			@Override
			public void run() {
				myFrom.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().start());
				myTo.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().start());
			}
		};
		timer.schedule(DEFAULT_TRANSITION_DELAY); // xxms instead of 1ms, to give iOS/Android enough time to set the
													// starting state.

	}



	/**
	 * Will remove the transition styles.
	 */
	protected void removeTransitionStyles() {
		myFrom.removeStyleName(transitionStyleName);
		myFrom.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().start());
		myFrom.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().out());
		myFrom.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().reverse());
		myTo.removeStyleName(transitionStyleName);
		myTo.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().in());
		myTo.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().start());
		myTo.removeStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().reverse());
	}



	@Override
	public void onBrowserEvent(Event e) {
		String type = e.getType();
		if (type.equals("webkitTransitionEnd") || type.equals("webkitAnimationEnd")) {
			onTransitionEnd();
		}
	}



	/**
	 * Will be called after the transition is completed.
	 */
	protected void onTransitionEnd() {
		if (myFrom != null && myTo != null) {
			myParent.remove(myFrom);
			removeTransitionStyles();
			myTo.onTransitionEnd();
			myFrom = null;
			myTo = null;
			myParent = null;
		}
	}



	/**
	 * Event which will be fired, after the transition is completed.
	 */
	protected void registerTransitionEndEvent() {
		if (!myReverse) {
			Utils.addEventListenerOnce(myTo.getElement(), "webkitTransitionEnd", false, this);
		} else {
			Utils.addEventListenerOnce(myFrom.getElement(), "webkitTransitionEnd", false, this);
		}
	}

	/**
	 * Flip-Transion.
	 * 
	 * 
	 *         
	 * 
	 */
	private static class FlipTransition extends Transition {

		private int myPhase = 0;



		/**
		 * Default constructor.
		 */
		public FlipTransition() {
			super(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().flip0());
		}



		@Override
		protected void registerTransitionEndEvent() {
			if (myPhase == 0) {
				Utils.addEventListenerOnce(myFrom.getElement(), "webkitTransitionEnd", false, this);
			} else {
				Utils.addEventListenerOnce(myTo.getElement(), "webkitTransitionEnd", false, this);
			}
		}



		@Override
		protected void onTransitionEnd() {
			removeTransitionStyles();
			if (myPhase == 0) {
				myParent.remove(myFrom);
				myParent.add(myTo);
				myPhase++;
				transitionStyleName = SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().flip1();
				prepare();
				start();
			} else {
				myTo.onTransitionEnd();
				myFrom = null;
				myTo = null;
				myPhase = 0;
				transitionStyleName = SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().flip0();
			}
		}



		@Override
		protected void prepare() {
			myFrom.addStyleName(transitionStyleName + " "
					+ SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().out());
			myTo.addStyleName(transitionStyleName + " "
					+ SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().in());
			if (myReverse) {
				myFrom.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().reverse());
				myTo.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().reverse());
			}
		}

	}

	/**
	 * Swap-Transition.
	 * 
	 * 
	 *         
	 * 
	 */
	private static class SwapTransition extends Transition {

		private int myPhase = 0;



		/**
		 * D Default constructor.
		 */
		public SwapTransition() {
			super(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().swap0());
		}



		@Override
		protected void onTransitionEnd() {
			removeTransitionStyles();
			if (myPhase == 0) {
				myPhase++;
				transitionStyleName = SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().swap1();
				prepare();
				start();
			} else {
				myParent.remove(myFrom);
				myTo.onTransitionEnd();
				myFrom = null;
				myTo = null;
				myPhase = 0;
				transitionStyleName = SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().swap0();
			}
		}



		@Override
		protected void prepare() {
			myFrom.addStyleName(transitionStyleName + " "
					+ SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().out());
			myTo.addStyleName(transitionStyleName + " "
					+ SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().in());
			if (myReverse) {
				myFrom.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().reverse());
				myTo.addStyleName(SWMMobile.getTheme().getMGWTCssBundle().getTransitionsCss().reverse());
			}
			if (myPhase == 0) {
				myParent.add(myTo);
			}
		}
	}

}
