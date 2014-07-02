package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Widget;

import de.swm.commons.mobile.client.event.SelectionChangedEvent;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.widgets.ListPanel;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.gwt.client.mobile.Direction;
import de.swm.mobile.kitchensink.client.Application;

public class PanelsPage extends SimplePage {

	@UiField ListPanel list;
	@UiField SimpleHeaderPanel header;
	
	private static PanelsPageUiBinder uiBinder = GWT
			.create(PanelsPageUiBinder.class);

	interface PanelsPageUiBinder extends UiBinder<Widget, PanelsPage> {
	}

	public PanelsPage() {
		initWidget(uiBinder.createAndBindUi(this));
		Application.addDefaultBackButtonHanlder(header);
	}

    @UiHandler("list")
    void onListSelectionChanged(SelectionChangedEvent e) {
    	switch (e.getSelection()) {
    	case 0:
    		AccordionPanelPage accordionPanelPage = new AccordionPanelPage();
    		goTo(accordionPanelPage, Direction.RIGHT);
    		break;
    	case 1:
    		HeaderPanelPage headerPanelPage = new HeaderPanelPage();
    		goTo(headerPanelPage, Direction.RIGHT);
    		break;
    	case 2:
    		ListPanelPage listPanelPage = new ListPanelPage();
    		goTo(listPanelPage, Direction.RIGHT);
    		break;
    	case 3:
    		ScrollPanelPage scrollPanelPage = new ScrollPanelPage();
    		goTo(scrollPanelPage, Direction.RIGHT);
    		break;
    	case 4:
    		SlidePanelPage slidePanelPage = new SlidePanelPage();
    		goTo(slidePanelPage, Direction.RIGHT);;
    		break;
    	case 5:
    		TabPanelPage tabPanelPage = new TabPanelPage();
    		goTo(tabPanelPage, Direction.RIGHT);
    		break;
    	case 6:
    		ToolbarPanelPage toolbarPanelPage = new ToolbarPanelPage();
    		goTo(toolbarPanelPage, Direction.RIGHT);
    		break;
    	case 7:
    		CanvasPanelPage canvasPanelPage = new CanvasPanelPage();
    		goTo(canvasPanelPage, Direction.RIGHT);
    		break;
		case 8:
			PaintableCanvasPage paintableCanvasPage = new PaintableCanvasPage();
			goTo(paintableCanvasPage, Direction.RIGHT);
			break;
    	case 9:
    		IndexedScrollPanelPage indexedScrollPanelPage = new IndexedScrollPanelPage();
    		goTo(indexedScrollPanelPage, Direction.RIGHT);
    		break;
    	case 10:
    		CommandPanelPage commandPanelPage = new CommandPanelPage();
    		goTo(commandPanelPage, Direction.RIGHT);
    		break;
    	case 11:
    		TreePanelPage treePanelPage = new TreePanelPage();
    		goTo(treePanelPage, Direction.RIGHT);
    		break;
    	case 12:
    		WideTreePanelPage wideTreePanelPage = new WideTreePanelPage();
    		goTo(wideTreePanelPage, Direction.RIGHT);
    		break;
    	}
    }

	@Override
	public String getName() {
		return "PannelsPage";
	}

}
