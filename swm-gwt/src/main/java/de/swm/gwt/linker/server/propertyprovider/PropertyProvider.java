package de.swm.gwt.linker.server.propertyprovider;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 *
 */
public interface PropertyProvider extends Serializable {

        public String getPropertyName();

        public String getPropertyValue(HttpServletRequest req) throws PropertyProviderException;
}