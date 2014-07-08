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

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.event.DragController;
import de.swm.commons.mobile.client.event.SwipeEvent;
import de.swm.commons.mobile.client.event.SwipeEventsHandler;
import de.swm.commons.mobile.client.page.Transition;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * * A slide panel reacts to finger slide gestures (sliding events).
 */
public class SlidePanel extends SWMMobileWidgetBase implements HasWidgets, SwipeEventsHandler, HasValueChangeHandlers<Boolean> {

	private final int firstSlideOnLoad;
	protected final FlowPanel contentPanel = new FlowPanel();
	protected int myCount = 0;
	protected int myCurrent = 0;
	protected SlideProvider mySlideProvider = null;
	protected final ArrayList<Widget> mySlides = new ArrayList<Widget>();
	protected boolean isRotate = false;


	/**
	 * Default constructor.
	 */
	public SlidePanel() {
		this(0);
	}

	/**
	 * Slide constructor.
	 *
	 * @param firstSlideToLoad first slide which will be loaded.
	 */
	public SlidePanel(int firstSlideToLoad) {
		this.firstSlideOnLoad = firstSlideToLoad;
		initWidget(contentPanel);
		setStyleName(SWMMobile.getTheme().getMGWTCssBundle().getSlidePanelCss().slidePanel());
	}


	public void setSlideCount(int count) {
		myCount = count;
	}


	public int getSlideCount() {
		return myCount > 0 ? myCount : mySlides.size();
	}


	public void setSlideProvider(SlideProvider provider) {
		mySlideProvider = provider;
	}


	public SlideProvider getSlideProvider() {
		return mySlideProvider;
	}


	@Override
	public void onInitialLoad() {
		super.onInitialLoad();
		myCurrent = firstSlideOnLoad;
		Slide slide = getSlide(myCurrent);
		if (slide != null) {
			contentPanel.add(slide);
		}
	}


	/**
	 * Returns the {@link Slide} on the <code>index</code> position.
	 *
	 * @param index index.
	 * @return the slide or null
	 */
	public Slide getSlide(int index) {
		Slide slide = null;
		if (mySlideProvider != null) {
			slide = mySlideProvider.loadSlide(index);
		}
		if (slide == null && index < mySlides.size()) {
			slide = (Slide) mySlides.get(index);
		}
		return slide;
	}


	@Override
	public void onLoad() {
		super.onLoad();
		DragController.get().addSwipeEventsHandler(this);
	}


	@Override
	protected void onUnload() {
		DragController.get().removeSwipeEventHandler(this);
		super.onUnload();
	}


	@Override
	public void onSwipeHorizontal(SwipeEvent e) {
		if (e.getSpeed() < 0) { // swipe to next
			next();
		} else { // swipe to previous
			previous();
		}
	}


	@Override
	public void onSwipeVertical(SwipeEvent e) {
	}


	/**
	 * Goes to the next slide.
	 */
	public void next() {
		if (myCurrent >= getSlideCount() - 1) {
			if (!isRotate) {
				return;
			} else {
				myCurrent = -1;
			}
		}
		myCurrent++;
		moveNext();
	}


	/**
	 * Moves to the next slide without boundary checks.
	 */
	protected void moveNext() {
		Slide to = getSlide(myCurrent);
		Slide from = (Slide) contentPanel.getWidget(0);
		Transition transition = Transition.SLIDE;
		ValueChangeEvent.fire(this, true);
		transition.start(from, to, contentPanel, false);
	}


	/**
	 * Goes to the previous slide.
	 */
	public void previous() {
		if (myCurrent <= 0) {
			if (!isRotate) {
				return;
			} else {
				myCurrent = getSlideCount();
			}
		}
		myCurrent--;
		movePrevious();
	}


	/**
	 * Moves to the previous slide without boundary checks.
	 */
	protected void movePrevious() {
		Slide to = getSlide(myCurrent);
		Slide from = (Slide) contentPanel.getWidget(0);
		Transition transition = Transition.SLIDE;
		ValueChangeEvent.fire(this, false);
		transition.start(from, to, contentPanel, true);
	}


	@Override
	public void onTransitionEnd() {
		super.onTransitionEnd();
		contentPanel.remove(0);
	}


	public int getCurrentSlideIndex() {
		return myCurrent;
	}


	@Override
	public void add(Widget w) {
		assert (w instanceof Slide) : "Can only add Slide widgets to SlidePanel.";
		mySlides.add(w);
	}


	@Override
	public void clear() {
		mySlides.clear();
		contentPanel.clear();
	}


	@Override
	public Iterator<Widget> iterator() {
		return mySlides.iterator();
	}


	@Override
	public boolean remove(Widget w) {
		return mySlides.remove(w);
	}


	public void setRotate(boolean rotate) {
		isRotate = rotate;
	}


	public boolean isRotate() {
		return isRotate;
	}


	@Override
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
		return this.addHandler(handler, ValueChangeEvent.getType());
	}

	/**
	 * Slide Provider provides {@link Slide}s for the {@link SlidePanel}.
	 */
	public interface SlideProvider {

		/**
		 * Loads the slide on the index position
		 *
		 * @param index ths index
		 * @return the slide
		 */
		Slide loadSlide(int index);
	}
}