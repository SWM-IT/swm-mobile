package de.swm.mobile.kitchensink.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.SWMMobileSettings;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.setup.StatusBarStyle;
import de.swm.commons.mobile.client.setup.ViewPort;
import de.swm.commons.mobile.client.widgets.HeaderPanel;
import de.swm.gwt.client.responsive.IMatchMedia;
import de.swm.gwt.client.responsive.IMatchMediaChangeHandler;
import de.swm.gwt.client.responsive.MatchMediaFacade;
import de.swm.gwt.client.utils.Utils;
import de.swm.mobile.kitchensink.client.theme.bootstrap.BootsrapSWMMobileTheme;

import java.util.logging.Logger;


/**
 * EntryPoint for the swm-mobile showcase.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2011-2014, SWM Services GmbH
 */
public class Application implements EntryPoint {

	public static ToolbarPage mainPage;

	private static final Logger LOGGER = Logger.getLogger(Application.class.getName());


	/**
	 * The type passed into the
	 * {@link de.swm.mobile.kitchensink.generator.ShowcaseGenerator}.
	 */
	private static final class GeneratorInfo {
	}


	@Override
	public void onModuleLoad() {
		// Generate the source code and css for the examples
		GWT.create(GeneratorInfo.class);

		SWMMobile.setTheme(new BootsrapSWMMobileTheme());
		//Test: Media query support
		if (MatchMediaFacade.getInstance().isSupported()) {
			IMatchMedia minWidth = MatchMediaFacade.getInstance().match("(min-width: 720px)");
			minWidth.addMatchMediaChangeHandler(new IMatchMediaChangeHandler() {

				public void onMatchMediaChange(IMatchMedia matchMedia) {
					LOGGER.info("Query (min-width: 720px) result " + matchMedia.getMedia());
					if (matchMedia.hasMatch()) {
						LOGGER.info("Media Query (min-width: 720px) is matching " + matchMedia.hasMatch());
					}


				}


			});
		}

		GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
			public void onUncaughtException(Throwable e) {
				Throwable unwrapped = unwrap(e);
				LOGGER.severe("uncaught exception: " + unwrapped.getMessage());
			}

			public Throwable unwrap(Throwable e) {
				if (e instanceof UmbrellaException) {
					UmbrellaException ue = (UmbrellaException) e;
					if (ue.getCauses().size() == 1) {
						return unwrap(ue.getCauses().iterator().next());
					}
				}
				return e;
			}
		});

		SWMMobileSettings settings = new SWMMobileSettings();
		settings.setFullscreen(true);
		settings.setPreventScrolling(false);
		settings.setStatusBarStyle(StatusBarStyle.BLACK);
		settings.setIconUrl("images/apple-touch-icon.png");
		settings.setStartUrl("images/startup.png");


		ViewPort viewPort = new ViewPort();
		viewPort.setWidthToDeviceWidth();
		viewPort.setInitialScale(1);
		viewPort.setMaximumScale(1);
		viewPort.setUserScaleAble(false);
		settings.setViewPort(viewPort);

		SWMMobile.applySettings(settings);

		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				mainPage = new ToolbarPage();
				SimplePage.load(mainPage);
			}
		});
	}


	public static ToolbarPage getStartPage() {
		return mainPage;
	}


	public static void addDefaultBackButtonHanlder(HeaderPanel header) {
		header.setLeftButtonClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Application.getStartPage().selectToolbar(1);
			}
		});
	}

}
