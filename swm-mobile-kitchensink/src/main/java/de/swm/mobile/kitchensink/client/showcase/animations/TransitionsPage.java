package de.swm.mobile.kitchensink.client.showcase.animations;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.Transition;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.gwt.client.mobile.Direction;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;
import de.swm.mobile.kitchensink.client.showcase.animations.util.TransitionDemoPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"TransitionsPage.ui.xml"})
public class TransitionsPage extends ShowcaseDetailPage {

    @UiField
    SimpleHeaderPanel header;

	@UiField
	ListPanel list;


	private final TransitionDemoPage demo = new TransitionDemoPage();

	private static TransitionPageUiBinder uiBinder = GWT.create(TransitionPageUiBinder.class);


    interface TransitionPageUiBinder extends UiBinder<Widget, TransitionsPage> {
	}



	public TransitionsPage() {
        super(TransitionsPage.class);
		initWidget(uiBinder.createAndBindUi(this));
	}



	@UiHandler("list")
	void onListSelectionChanged(SelectionChangedEvent e) {
		switch (e.getSelection()) {
			case 0:
				this.goTo(demo, Transition.SLIDE, Direction.RIGHT, null);
                demo.setParentPage(this);
				break;
			case 1:
				this.goTo(demo, Transition.SLIDEUP, Direction.RIGHT, null);
                demo.setParentPage(this);
				break;
			case 2:
				this.goTo(demo, Transition.SLIDEDOWN, Direction.RIGHT, null);
                demo.setParentPage(this);
				break;
			case 3:
				this.goTo(demo, Transition.FADE, Direction.RIGHT, null);
                demo.setParentPage(this);
				break;
			case 4:
				this.goTo(demo, Transition.POP, Direction.RIGHT, null);
                demo.setParentPage(this);
				break;
			case 5:
				this.goTo(demo, Transition.SWAP, Direction.RIGHT, null);
                demo.setParentPage(this);
				break;
			case 6:
                demo.setParentPage(this);
				this.goTo(demo, Transition.FLIP, Direction.RIGHT, null);
				break;
		}
	}



	@Override
	public String getName() {
		return "Transitions";
	}

    @Override
    public SimpleHeaderPanel getHeaderPanel() {
        return header;
    }
}
