package de.swm.commons.local.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.resources.client.ImageResource;
import de.swm.commons.mobile.client.SWMMobile;
import de.swm.commons.mobile.client.SWMMobileSettings;
import de.swm.commons.mobile.client.setup.Density;
import de.swm.commons.mobile.client.setup.StatusBarStyle;
import de.swm.commons.mobile.client.setup.ViewPort;
import de.swm.commons.mobile.client.theme.base.BaseSWMMobileTheme;
import de.swm.commons.mobile.client.theme.bright.BrightSWMMobileTheme;
import de.swm.commons.mobile.client.widgets.*;
import de.swm.commons.mobile.client.widgets.accordion.AccordionContent;
import de.swm.commons.mobile.client.widgets.accordion.AccordionHeader;
import de.swm.commons.mobile.client.widgets.accordion.AccordionPanel;
import de.swm.commons.mobile.client.widgets.accordion.AccordionStack;
import de.swm.commons.mobile.client.widgets.command.CommandDropDown;
import de.swm.commons.mobile.client.widgets.command.CommandItem;


/**
 * EntryPoint for the swm-mobile showcase.
 *
 * @author wiese.daniel <br>
 *         copyright (C) 2011, SWM Services GmbH
 */
public class GwtTestcompile implements EntryPoint {


	@Override
	public void onModuleLoad() {


		SWMMobile.setTheme(new BaseSWMMobileTheme());
		SWMMobileSettings settings = new SWMMobileSettings();
		settings.setFullscreen(true);
		settings.setPreventScrolling(false);
		settings.setStatusBarStyle(StatusBarStyle.BLACK);
		settings.setIconUrl("images/apple-touch-icon.png");
		settings.setStartUrl("images/startup.png");

		ViewPort viewPort = new ViewPort();
		viewPort.setTargetDensity(Density.DEVICE);
		viewPort.setInitialScale(1);
		viewPort.setMaximumScale(1);
		viewPort.setMinimumScale(1);
		viewPort.setUserScaleAble(false);
		settings.setViewPort(viewPort);
		SWMMobile.applySettings(settings);

		SWMMobile.setTheme(new BrightSWMMobileTheme());

		//INstantiante Widgets
		final AccordionContent t1 = new AccordionContent();
		final AccordionHeader t2 = new AccordionHeader();
		final AccordionPanel t3 = new AccordionPanel();
		final AccordionStack t4 = new AccordionStack();
		final BigButton t5 = new BigButton();
		//final BoxBase<String> t6 = new BoxBase<String>();
		final Button t7 = new Button();
		final CheckBox t8 = new CheckBox();
		final CheckBoxGroup t9 = new CheckBoxGroup();
		final CommandDropDown t10 = new CommandDropDown("test", null, null);
		final CommandItem t11 = new CommandItem("test", (ImageResource) null, (ImageResource) null);


	}


}
