package de.swm.gwt.linker.server.propertyprovider;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 *
 */
public class PhoneGapPropertyProvider implements PropertyProvider {

        private static final long serialVersionUID = -411058962729141969L;

        @Override
        public String getPropertyName() {
                return "phonegap.env";
        }

        @Override
        public String getPropertyValue(HttpServletRequest req) throws PropertyProviderException {
                return "no";
        }

}