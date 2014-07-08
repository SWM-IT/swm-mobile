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
package de.swm.commons.mobile.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.*;
import de.swm.commons.mobile.client.constants.I18NConstants;
import de.swm.commons.mobile.client.setup.OsDetection;
import de.swm.commons.mobile.client.setup.OsDetectionImpl;
import de.swm.commons.mobile.client.setup.Resolution;
import de.swm.commons.mobile.client.setup.ViewPort;
import de.swm.commons.mobile.client.theme.SWMMobileTheme;
import de.swm.commons.mobile.client.utils.Utils;
import de.swm.gwt.client.exception.ApplicationRedirecter;

import java.util.logging.Logger;


/**
 * Base class of the framework. Contains all global settings of the underlying application.
 */
public final class SWMMobile {

    private static final Logger LOGGER = Logger.getLogger(SWMMobile.class.getName());

    private static SWMMobileTheme theme;
    private static final OsDetection OS_DETECTION = new OsDetectionImpl();
	private static final I18NConstants i18n = GWT.create(I18NConstants.class);


    /**
     * Utility constructor.
     */
    private SWMMobile() {

    }


    /**
     * Returns the internationalization constants.
     *
     * @return the i18n constants
     */
    public static I18NConstants getI18N() {
        return i18n;
    }


    /**
     * Return the current theme of the Application. By default, this will be the base theme.
     *
     * @return the current theme.
     */
    public static SWMMobileTheme getTheme() {
        if (theme == null) {
            LOGGER.info("No Theme ist set");
        }
        return theme;
    }


    /**
     * Sets the current theme of the Application.
     *
     * @param toSet the theme to set.
     */
    public static void setTheme(SWMMobileTheme toSet) {
        theme = toSet;
    }


    public static OsDetection getOsDetection() {
        return OS_DETECTION;
    }


    /**
     * Sets the css matching the screen resolution.
     */
    public static void setPageResolutionCSS() {
        int ratio = Utils.getDevicePixelRatio();
        if (ratio == Resolution.HVGA.getRatio()) {
            Document.get().getDocumentElement().setClassName(getTheme().getMGWTCssBundle().getDisplayCss().hvga());
        } else if (ratio == Resolution.WVGA.getRatio()) {
            Document.get().getDocumentElement().setClassName(getTheme().getMGWTCssBundle().getDisplayCss().wvga());
        } else if (ratio == Resolution.QVGA.getRatio()) {
            Document.get().getDocumentElement().setClassName(getTheme().getMGWTCssBundle().getDisplayCss().qvga());
        } else {
            Document.get().getDocumentElement().setClassName(getTheme().getMGWTCssBundle().getDisplayCss().hvga());
        }
    }


    /**
     * Apply settings for this mgwt app. Every app should call this method with the options its wants for their app
     * <p/>
     * The options are documented in @link {@link SWMMobileSettings}
     *
     * @param settings the settings for this app
     */
    public static void applySettings(SWMMobileSettings settings) {

        Element head = getHead();

        if (settings.getIconUrl() != null) {
            LinkElement iconUrlLinkElement = Document.get().createLinkElement();
            iconUrlLinkElement.setRel("apple-touch-icon");
            iconUrlLinkElement.setHref(settings.getIconUrl());
            head.appendChild(iconUrlLinkElement);

        }

        if (getOsDetection().isIOs()) {
            MetaElement numberDetection = Document.get().createMetaElement();
            numberDetection.setName("format-detection");
            numberDetection.setContent("telephone=no");
            head.appendChild(numberDetection);

        }

        if (settings.getStartUrl() != null) {
            LinkElement startUrlLinkElement = Document.get().createLinkElement();
            startUrlLinkElement.setRel("apple-touch-startup-image");
            startUrlLinkElement.setHref(settings.getStartUrl());
            head.appendChild(startUrlLinkElement);
        }

        ViewPort viewPort = settings.getViewPort();

        if (viewPort != null) {
            MetaElement fixViewPortElement = Document.get().createMetaElement();
            fixViewPortElement.setName("viewport");
            fixViewPortElement.setContent(viewPort.getContent());
            head.appendChild(fixViewPortElement);

        }

        if (settings.isFullscreen()) {
            MetaElement fullScreenMetaTag = Document.get().createMetaElement();
            fullScreenMetaTag.setName("apple-mobile-web-app-capable");
            fullScreenMetaTag.setContent("yes");
            head.appendChild(fullScreenMetaTag);
        }

		boolean scrollingDisabled = settings.isPreventScrolling();
        if (settings.isPreventScrolling() && getOsDetection().isIOs()) {
            BodyElement body = Document.get().getBody();
            setupPreventScrolling(body);

        }

        if (settings.isDisablePhoneNumberDetection()) {
            MetaElement fullScreenMetaTag = Document.get().createMetaElement();
            fullScreenMetaTag.setName("format-detection");
            fullScreenMetaTag.setContent("telephone=no");
            head.appendChild(fullScreenMetaTag);
        }

        if (settings.getStatusBarStyle() != null) {
            MetaElement statusBarTag = Document.get().createMetaElement();
            statusBarTag.setName("apple-mobile-web-app-status-bar-style");
            switch (settings.getStatusBarStyle()) {
                case BLACK:
                    statusBarTag.setContent("black");
                    break;
                case BLACK_TRANSLUCENT:
                    statusBarTag.setContent("black-translucent");
                    break;
                case DEFAULT:
                default:
                    statusBarTag.setContent("default");
                    break;
            }

            head.appendChild(statusBarTag);
        }

    }

    /**
     * Checks if client is supported by the application. If the client is not supported, performs redirect.
     *
     * @param clientSupportedAsserter class that performs the support check. Default implementation {@link ClientSupportedAsserter}.
     * @param redirectUrl             URL to redirect to if client is not supported
     */
    public static void redirectIfClientUnsupported(ClientSupportedAsserter clientSupportedAsserter, String redirectUrl) {
        if (!clientSupportedAsserter.isSupported(getOsDetection())) {
            LOGGER.info("Unsupported client - redirecting to " + redirectUrl);
            ApplicationRedirecter.redirect(redirectUrl);
        }
    }

    /**
     * Returns the head.
     *
     * @return the head of the host page.
     */
    private static Element getHead() {
        NodeList<Element> elementsByTagName = Document.get().getElementsByTagName("head");

        if (elementsByTagName.getLength() != 1) {
            throw new RuntimeException("there is no head element");
        }

        return elementsByTagName.getItem(0);
    }


    /**
     * Prevents defaults of js event propagation.
     *
     * @param el the element for which the default will be prevented.
     */
    private static native void setupPreventScrolling(Element el)/*-{
        var func = function (event) {
            event.preventDefault();
            return false;
        };

        el.ontouchmove = func;

    }-*/;

    /**
     * Checks if client is supported by SWM-mobile.
     *
     * @author wimmel.guido
     */
    public static class ClientSupportedAsserter {
        public boolean isSupported(OsDetection osDetection) {
            return osDetection.isWebkit();
        }
    }

}
