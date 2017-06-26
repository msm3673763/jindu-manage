package com.ucsmy.jindu.manage.manage.ext;

import java.io.Serializable;

/** 
 * @ClassName: UcasClientUserProfileWithOrganization
 * @Description: 
 * @author zhengfucheng 
 * 
 * @version V1.0 
 */
public class UcasClientProfileOrganization implements Serializable {

	
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = -168271519057235086L;

	private String userId;
	
	private String name;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
