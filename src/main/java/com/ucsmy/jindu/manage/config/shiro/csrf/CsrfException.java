package com.ucsmy.jindu.manage.config.shiro.csrf;

/***
 * 
 * @author admin
 *
 */
public class CsrfException extends RuntimeException {
	public CsrfException(String msg) {
        super(msg);
    }

    public CsrfException(String msg, Throwable t) {
        super(msg, t);
    }
}
