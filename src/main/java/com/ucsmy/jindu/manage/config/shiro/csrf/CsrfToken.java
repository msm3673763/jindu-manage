package com.ucsmy.jindu.manage.config.shiro.csrf;

import java.io.Serializable;
/***
 * 
 * @author admin
 */
public interface CsrfToken extends Serializable {
	String getHeaderName();

    String getParameterName();

    String getToken();
    
    String getTokenType();
}
