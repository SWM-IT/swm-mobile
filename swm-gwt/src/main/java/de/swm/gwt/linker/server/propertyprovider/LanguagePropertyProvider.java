package de.swm.gwt.linker.server.propertyprovider;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author Alexander Vilbig (SWM Services GmbH)
 *
 */
public class LanguagePropertyProvider extends PropertyProviderBaseImpl {

	private static final long serialVersionUID = -6110705818816155976L;

	@Override
	public String getPropertyName() {
		return "locale";
	}

	@Override
	public String getPropertyValue(HttpServletRequest req) throws PropertyProviderException {
		// Check potential URL argument
		String languageParam = req.getParameter("locale");
		if (languageParam != null && languageParam.length() > 0) {
			return languageParam;
		}
		
		// TODO: Check meta-tag in host page (probably not a common use case in swm-mobile, though)
		
		// Check client browser language
		Locale loc = req.getLocale();
		if (loc != null) {
			return loc.getLanguage() + "_" + loc.getCountry();
		}
		
		// Return default locale
		return "default";
	}

}
