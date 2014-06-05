package de.swm.gwt.server;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Fuegt no-cache header Feld hinzu.
 * 
 * @author fuchs.florian <br>
 *         copyright (C) 2010, SWM Services GmbH
 */
public class NoCacheFilter implements Filter {
	@Override
	public void destroy() {

	}



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
		ServletException {

		if (isDoFilter((HttpServletRequest)request, (HttpServletResponse)response)) {
			((HttpServletResponse) response).setHeader("Cache-Control", "no-cache");
		}

		chain.doFilter(request, response);
	}



	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}



	/**
	 * Gibt zurueck ob ein Filtering durchgefuehrt werden soll. default: false.
	 * 
	 * @param request
	 *            request.
	 * @param response
	 *            response.
	 * @return true/false.
	 */
	public boolean isDoFilter(HttpServletRequest request, HttpServletResponse response) {
		return false;
	}
}
