package de.swm.mobile.kitchensink.client.showcase.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import de.swm.commons.mobile.client.widgets.SimpleHeaderPanel;
import de.swm.commons.mobile.client.widgets.Slide;
import de.swm.commons.mobile.client.widgets.SlidePanel;
import de.swm.commons.mobile.client.widgets.SlidePanel.SlideProvider;
import de.swm.gwt.client.mobile.Direction;
import de.swm.mobile.kitchensink.client.Application;
import de.swm.mobile.kitchensink.client.base.ShowcaseDetailPage;

import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseSource;
import static de.swm.mobile.kitchensink.client.ShowcaseAnnotations.ShowcaseUiXML;

@ShowcaseSource
@ShowcaseUiXML({"SlidePanelPage.ui.xml"})
public class SlidePanelPage extends ShowcaseDetailPage implements SlideProvider {

    @UiField
    SimpleHeaderPanel header;
    @UiField
    SlidePanel slider;

    private static SlidePanelPageUiBinder uiBinder = GWT
            .create(SlidePanelPageUiBinder.class);


    interface SlidePanelPageUiBinder extends UiBinder<Widget, SlidePanelPage> {
    }

    public SlidePanelPage() {
        super(SlidePanelPage.class);
        initWidget(uiBinder.createAndBindUi(this));
        Application.addDefaultBackButtonHanlder(header);

        slider.setSlideCount(10);
        slider.setSlideProvider(this);

        header.setLeftButtonClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (slider.getCurrentSlideIndex() > 0) {
                    slider.previous();
                } else {
                    goTo(null, Direction.RIGHT);
                }
            }
        });

        header.setRightButtonClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                slider.next();
            }
        });
    }

    @Override
    public Slide loadSlide(int index) {
        if (index < 2) {
            return null;
        }
        Slide slide = new Slide();
        slide.addStyleName("Slide-Content");
        slide.add(new HTML("Slide Me!"));
        slide.add(new HTML("Dynamic Slide " + index));
        return slide;
    }

    @Override
    public SimpleHeaderPanel getHeaderPanel() {
        return header;
    }

    @Override
    public String getName() {
        return "SlidePanel";
    }

}
