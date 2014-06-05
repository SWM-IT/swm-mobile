package de.swm.gwt.linker.server.propertyprovider;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 *
 */
public abstract class PropertyProviderBaseImpl implements PropertyProvider {

        private static final long serialVersionUID = 8038603215822600098L;

        protected String getUserAgent(HttpServletRequest req) {
                return req.getHeader("User-Agent").toLowerCase();
        }

}