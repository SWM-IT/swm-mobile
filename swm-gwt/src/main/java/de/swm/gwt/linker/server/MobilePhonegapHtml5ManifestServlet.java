package de.swm.gwt.linker.server;

import de.swm.gwt.linker.server.propertyprovider.MgwtOsPropertyProvider;
import de.swm.gwt.linker.server.propertyprovider.MobileUserAgentProvider;
import de.swm.gwt.linker.server.propertyprovider.PhoneGapPropertyProvider;
import de.swm.gwt.linker.server.propertyprovider.UserAgentPropertyProvider;

/**
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 *
 */
public class MobilePhonegapHtml5ManifestServlet extends Html5ManifestServletBase {

        private static final long serialVersionUID = 3480215265307651028L;

        public MobilePhonegapHtml5ManifestServlet() {
                addPropertyProvider(new MgwtOsPropertyProvider());
                addPropertyProvider(new PhoneGapPropertyProvider());
                addPropertyProvider(new UserAgentPropertyProvider());
                addPropertyProvider(new MobileUserAgentProvider());
        }

}