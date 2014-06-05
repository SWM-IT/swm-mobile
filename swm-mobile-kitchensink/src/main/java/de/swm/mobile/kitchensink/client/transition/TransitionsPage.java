package de.swm.mobile.kitchensink.client.transition;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.page.Transition;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.gwt.client.mobile.Direction;



public class TransitionsPage extends SimplePage {

	@UiField
	ListPanel list;
	TransitionDemoPage demo = new TransitionDemoPage();

	private static TransitionPageUiBinder uiBinder = GWT.create(TransitionPageUiBinder.class);

	interface TransitionPageUiBinder extends UiBinder<Widget, TransitionsPage> {
	}



	public TransitionsPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}



	@UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
		switch (e.getSelection()) {
			case 0:
				demo.header.setCaption("Slide");
				this.goTo(demo, Transition.SLIDE, Direction.RIGHT, null);
				break;
			case 1:
				demo.header.setCaption("Slide Up");
				this.goTo(demo, Transition.SLIDEUP, Direction.RIGHT, null);
				break;
			case 2:
				demo.header.setCaption("Slide Down");
				this.goTo(demo, Transition.SLIDEDOWN, Direction.RIGHT, null);
				break;
			case 3:
				demo.header.setCaption("Fade");
				this.goTo(demo, Transition.FADE, Direction.RIGHT, null);
				break;
			case 4:
				demo.header.setCaption("Pop");
				this.goTo(demo, Transition.POP, Direction.RIGHT, null);
				break;
			case 5:
				demo.header.setCaption("Swap");
				this.goTo(demo, Transition.SWAP, Direction.RIGHT, null);
				break;
			case 6:
				demo.header.setCaption("Flip");
				this.goTo(demo, Transition.FLIP, Direction.RIGHT, null);
				break;
			// case 7:
			// demo.header.setCaption("Cube");
			// this.goTo(demo, Transition.CUBE);
			// break;
		}
	}



	@Override
	public String getName() {
		return "TransitionPage";
	}

}
