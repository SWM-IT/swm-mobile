package de.swm.gwt.linker.server.propertyprovider;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Daniel Kurka (see http://code.google.com/p/mgwt/)
 * 
 */
public class UserAgentPropertyProvider extends PropertyProviderBaseImpl {

	private static final long serialVersionUID = 7773351123106881463L;

	@Override
	public String getPropertyValue(HttpServletRequest req) throws PropertyProviderException {

		String userAgent = getUserAgent(req);

		if (userAgent.contains("opera")) {
			return "opera";
		}

		if (userAgent.contains("safari") || userAgent.contains("iphone") || userAgent.contains("ipad")) {
			return "safari";
		}

		if (userAgent.contains("gecko")) {
			return "gecko1_8";
		}

		throw new PropertyProviderException("Can not find user agent property for userAgent: '" + userAgent + "'");

	}

	@Override
	public String getPropertyName() {
		return "user.agent";
	}

}
