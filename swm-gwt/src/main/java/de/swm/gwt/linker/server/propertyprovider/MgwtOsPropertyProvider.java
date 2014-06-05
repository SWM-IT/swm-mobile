package de.swm.gwt.linker.server.propertyprovider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import de.swm.gwt.linker.server.BindingProperty;


/**
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 *
 */
public class MgwtOsPropertyProvider extends PropertyProviderBaseImpl {

        private static final long serialVersionUID = -3624651858511204668L;
        public static final BindingProperty iPhone = new BindingProperty("mgwt.os", "iphone");
        public static final BindingProperty retina = new BindingProperty("mgwt.os", "retina");
        public static final BindingProperty iPhone_undefined = new BindingProperty("mgwt.os", "iphone_undefined");

        public static final BindingProperty iPad = new BindingProperty("mgwt.os", "ipad");
        public static final BindingProperty iPad_retina = new BindingProperty("mgwt.os", "ipad_retina");
        public static final BindingProperty iPad_undefined = new BindingProperty("mgwt.os", "ipad_undefined");

        @Override
        public String getPropertyName() {
                return "mgwt.os";
        }

        @Override
        public String getPropertyValue(HttpServletRequest req) throws PropertyProviderException {
                String userAgent = getUserAgent(req);

                // android
                if (userAgent.contains("android")) {
                        if (userAgent.contains("mobile")) {
                                return "android";
                        } else {
                                return "android_tablet";
                        }

                }

                if (userAgent.contains("ipad")) {
                        String value = getRetinaCookieValue(req);
                        if (value == null) {
                                return "ipad_undefined";
                        }

                        if ("0".equals(value)) {
                                return "ipad";
                        }

                        if ("1".equals(value)) {
                                return "ipad_retina";
                        }

                }

                if (userAgent.contains("iphone")) {
                        String value = getRetinaCookieValue(req);
                        if (value == null) {
                                return "iphone_undefined";
                        }

                        if ("0".equals(value)) {
                                return "iphone";
                        }

                        if ("1".equals(value)) {
                                return "retina";
                        }

                }

                if (userAgent.contains("blackberry")) {
                        return "blackberry";
                }

                return "desktop";

        }

        public String getRetinaCookieValue(HttpServletRequest req) {

                Cookie[] cookies = req.getCookies();
                if (cookies == null)
                        return null;

                for (int i = 0; i < cookies.length; i++) {
                        Cookie cookie = cookies[i];
                        if ("mgwt_ios_retina".equals(cookie.getName()))
                                return (cookie.getValue());
                }
                return null;
        }
}