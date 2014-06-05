package de.swm.gwt.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Fuegt no-cache Response Header bei POST Requests hinzu.
 * 
 * @author fuchs.florian <br>
 *         copyright (C) 2010, SWM Services GmbH
 */
public class AddNoCacheHeadersFilter extends NoCacheFilter {

	/**
	 * Aktiviert no-cache Header, für POST Requests.
	 * 
	 * @param request
	 *            request.
	 * @param response
	 *            response.
	 * @return true/false
	 */
	@Override
	public boolean isDoFilter(HttpServletRequest request, HttpServletResponse response) {
		boolean filter = false;
		if ("POST".equals(request.getMethod().toUpperCase())) {
			filter = true;
		}

		return filter;
	}

}
