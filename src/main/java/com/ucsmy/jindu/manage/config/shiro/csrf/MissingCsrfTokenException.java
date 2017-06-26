package com.ucsmy.jindu.manage.config.shiro.csrf;

/***
 * 
 * @author admin
 *
 */
public class MissingCsrfTokenException extends CsrfException {
	public MissingCsrfTokenException(String actualToken) {
        super("Could not verify the provided CSRF token because your session was not found.");
    }
}
