package de.swm.gwt.linker.server.propertyprovider;

import javax.servlet.http.HttpServletRequest;

/**
 * Modified by Alexander Vilbig (SWM Services GmbH) to adapt property name for mobile user agent.
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 *
 */
public class MobileUserAgentProvider extends PropertyProviderBaseImpl {

        private static final long serialVersionUID = -7478122098836802106L;

        @Override
        public String getPropertyName() {
                return "swmmobile.user.agent";
        }

        @Override
        public String getPropertyValue(HttpServletRequest req) throws PropertyProviderException {
                String ua = getUserAgent(req);

                if (ua.contains("android")) {
                        return "mobilesafari";
                }
                if (ua.contains("iphone")) {
                        return "mobilesafari";
                }
                if (ua.contains("ipad")) {
                        return "mobilesafari";
                }
                if (ua.contains("webkit") && ua.contains("mobile")) {
                        return "mobilesafari";
                }
                return "none";
        }
}