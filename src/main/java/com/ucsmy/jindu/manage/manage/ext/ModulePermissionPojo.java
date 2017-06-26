package com.ucsmy.jindu.manage.manage.ext;

import java.io.Serializable;
import java.util.List;

import com.ucsmy.jindu.manage.commons.base.interceptor.tree.BaseTreeNode;

//import com.ucsmy.commons.tree.BaseTreeNode;

 /**
 * 
 *
 * @author xiongs
 * 
 * 
 * 
 * 
 */
public class ModulePermissionPojo extends BaseTreeNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/*private String permission1_id;
    private String permission1_name;
    private String permission1_sn;
    private boolean permission1_cheched = false;
    private String permission2_id;
    private String permission2_name;
    private String permission2_sn;
    private boolean permission2_cheched = false;
    private String permission3_id;
    private String permission3_name;
    private String permission3_sn;
    private boolean permission3_cheched = false;
    private String permission4_id;
    private String permission4_name;
    private String permission4_sn;
    private boolean permission4_cheched = false;*/
	
	private List<PermissionPojo> permissionList;
	
	public List<PermissionPojo> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<PermissionPojo> permissionList) {
		this.permissionList = permissionList;
	}
}
