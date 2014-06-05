package de.swm.gwt.linker.server;

import de.swm.gwt.linker.server.propertyprovider.LanguagePropertyProvider;
import de.swm.gwt.linker.server.propertyprovider.MobileUserAgentProvider;

/**
 * Modified by Alexander Vilbig (SWM Services GmbH) to adapt language and user agent property selection.
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 *
 */
public class MobileHtml5ManifestServlet extends Html5ManifestServletBase {

        private static final long serialVersionUID = 3480215265307651028L;

        public MobileHtml5ManifestServlet() {
        	addPropertyProvider(new LanguagePropertyProvider());
            addPropertyProvider(new MobileUserAgentProvider());
        }

}