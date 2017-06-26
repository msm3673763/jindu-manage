package com.ucsmy.jindu.manage.config.shiro.csrf;

/***
 * 
 * @author admin
 *
 */
public class InvalidCsrfTokenException extends CsrfException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1100626684920610533L;

	public InvalidCsrfTokenException(CsrfToken csrfToken, String actualToken) {
		super("Invalid CSRF Token \'" + actualToken + "\' was found on the request parameter \'"
				+ csrfToken.getParameterName() + "\' or header \'" + csrfToken.getHeaderName() + "\'.");
	}
}
