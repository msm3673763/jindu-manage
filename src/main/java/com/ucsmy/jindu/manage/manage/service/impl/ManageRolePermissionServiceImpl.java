package com.ucsmy.jindu.manage.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.base.interceptor.tree.TreeTool;
import com.ucsmy.jindu.manage.commons.base.utils.JsonUtils;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDGenerator;
import com.ucsmy.jindu.manage.manage.constant.ManageConfigConstant;
import com.ucsmy.jindu.manage.manage.dao.ManageConfigMapper;
import com.ucsmy.jindu.manage.manage.dao.ManageModuleMapper;
import com.ucsmy.jindu.manage.manage.dao.ManageRoleMapper;
import com.ucsmy.jindu.manage.manage.dao.ManageRolePermissionMapper;
import com.ucsmy.jindu.manage.manage.entity.ManageRole;
import com.ucsmy.jindu.manage.manage.entity.ManageRoleModule;
import com.ucsmy.jindu.manage.manage.entity.ManageRolePermission;
import com.ucsmy.jindu.manage.manage.ext.ModulePermissionPojo;
import com.ucsmy.jindu.manage.manage.ext.ModuleTreePojo;
import com.ucsmy.jindu.manage.manage.ext.PermissionPojo;
import com.ucsmy.jindu.manage.manage.service.ManageRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//import com.ucsmy.commons.tree.TreeTool;
//import com.ucsmy.commons.utils.JsonUtils;
//import com.ucsmy.commons.utils.UUIDGenerator;

/**
 * 
 * @author ucs_hexuejun
 *
 */
@Service
public class ManageRolePermissionServiceImpl implements ManageRolePermissionService {

	@Autowired
	protected ManageRolePermissionMapper manageRolePermissionMapper;
	@Autowired
	protected ManageModuleMapper manageModuleMapper;
	@Autowired
	protected ManageRoleMapper manageRoleMapper;
	@Autowired
	protected ManageConfigMapper manageConfigMapper;

	@Override
	@Logger(printSQL = true)
	public String queryAllModulePermission(String role_id) throws JsonProcessingException {
		return getTreeTable(role_id);
	}

	@Override
	@Logger(printSQL = true)
	public ManageRolePermission queryRolePermissionByRoleID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Logger(printSQL = true)
	public int insertRolePermissionByBatch() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Logger(printSQL = true)
	public int insertRoleModuleByBatch() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Logger(printSQL = true)
	public int deleteRolePermissionByRoleID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	@Logger(printSQL = true)
	public int deleteRoleModuleByRoleID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Logger(printSQL = true)
	@Transactional(rollbackFor = Exception.class)
	public String addRolePermission(String role_id, String permissions_id, String name) {

		boolean success = false;
		StringBuilder msg = new StringBuilder();

		if (role_id != null && !"".equals(role_id.trim())) {
			if (permissions_id != null && !"".equals(permissions_id.trim())) {
				// 删除角色功能
				manageRolePermissionMapper.deleteRolePermissionByRoleID(role_id);
				// logger.debug("->AddRolePermissionStep", "删除角色："+role_id+"的权限数量：" + del_permission);
				// 删除角色菜单
				manageRolePermissionMapper.deleteRoleModuleByRoleID(role_id);
				// logger.debug("->AddRolePermissionStep", "删除角色："+role_id+"的菜单数量：" + del_module);

				// [0]:module_id;[1]:permission_id
				String[] modulePermissionID = splitModuleIDAndPermissionID(permissions_id);
				// 查询所有菜单
				List<ModuleTreePojo> mudulelist = manageModuleMapper.getModuleList(null, null);
				// 迭代所有菜单的父菜单编号
				// List<String> moduleId_list = getParentIdByID(mudule_list,
				// module_permission_ID[0].substring(0,module_permission_ID[0].length()-1));

				String sModuleIdList = getParentIdByID(mudulelist, modulePermissionID[0].substring(0, modulePermissionID[0].length() - 1));

				List<ManageRoleModule> roleModulelist = setRoleModule(role_id, sModuleIdList.substring(0, sModuleIdList.length() - 1));

				List<ManageRolePermission> rolePermissionlist = setRolePermission(role_id,
						modulePermissionID[1].substring(0, modulePermissionID[1].length() - 1));

				manageRolePermissionMapper.insertRoleModuleByBatch(roleModulelist);
				// logger.debug("->AddRolePermissionStep", "添加角色："+role_id+"的菜单数量：" + module_count);
				int permissionCount = manageRolePermissionMapper.insertRolePermissionByBatch(rolePermissionlist);
				// logger.debug("->AddRolePermissionStep", "添加角色："+role_id+"的功能数量：" + permission_count);
				if (permissionCount > 0) {
					success = true;
					msg.append("权限分配成功！");
				} else {
					msg.append("权限分配失败！");
				}
			} else {
				msg.append("请选择需要分配的权限！");
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("role_id", role_id);
		jsonObject.put("success", success);
		jsonObject.put("msg", msg);
		jsonObject.put("name", name);
		return jsonObject.toString();
	}

	@Logger(printSQL = true)
	public String getTreeTable(String role_id) throws JsonProcessingException {

		ManageRole role = manageRoleMapper.queryRoleById(role_id);

		// 查询菜单列表
		List<ModulePermissionPojo> modulePermissionlist = manageRolePermissionMapper.queryAllModulePermission();
		// 查询角色已经分配的权限

		List<ManageRolePermission> rolePermissionlist = manageRolePermissionMapper.queryRolePermissionByRoleID(role_id);

		String name = manageConfigMapper.getValueByKey(ManageConfigConstant.EXCLUDE_MODULE_NAME);

		//String rolePermissions = JsonUtils.formatObjectToJson(TreeTool.getTreeList2(setSortList(modulePermissionlist, rolePermissionlist), role.getType(), name));
		String rolePermissions = JsonUtils.formatObjectToJson(TreeTool.getTreeList(setSortList(modulePermissionlist, rolePermissionlist)));
		return rolePermissions;
	}

	/**
	 * 判断角色所拥有的功能权限
	 * 
	 * @param modulePermission_list
	 *            所有功能权限列表
	 * @param rolePermission_list
	 *            角色功能列表
	 * @return
	 */
	@Logger(printSQL = true)
	public static List<ModulePermissionPojo> setSortList(List<ModulePermissionPojo> modulePermission_list,
			List<ManageRolePermission> rolePermission_list) {
		for (ModulePermissionPojo modulePermissionBean : modulePermission_list) {
			List<PermissionPojo> permissionList = modulePermissionBean.getPermissionList();
			if (permissionList != null && !permissionList.isEmpty()) {
				for (PermissionPojo permissionVo : permissionList) {
					if (rolePermission_list != null && !rolePermission_list.isEmpty()) {
						for (ManageRolePermission rolePermissionBean : rolePermission_list) {
							if (permissionVo.getPermissionId().equals(rolePermissionBean.getPermissionId())) {
								permissionVo.setCheched(true);
								break;
							}
						}
					}
				}
				LinkedList<PermissionPojo> linkedList = setLinkedList(permissionList);
				modulePermissionBean.getPermissionList().clear();
				modulePermissionBean.setPermissionList(linkedList);
			}
		}
		return modulePermission_list;
	}

	/**
	 * 安装{查询，新增，修改，删除}的方式排序
	 * 
	 * @param permissionList
	 * @return
	 */
	@Logger(printSQL = true)
	public static LinkedList<PermissionPojo> setLinkedList(List<PermissionPojo> permissionList) {

		LinkedList<PermissionPojo> linkedList = new LinkedList<PermissionPojo>();

		WeakHashMap<String, PermissionPojo> map = new WeakHashMap<String, PermissionPojo>();
		int count = 5;
		for (PermissionPojo bean : permissionList) {
			if ("新增".equals(bean.getPermissionName().trim())) {
				map.put("2", bean);
			} else if ("查询".equals(bean.getPermissionName().trim())) {
				// bean.setCheched(true);
				map.put("1", bean);
			} else if ("修改".equals(bean.getPermissionName().trim())) {
				map.put("3", bean);
			} else if ("删除".equals(bean.getPermissionName().trim())) {
				map.put("4", bean);
			} else {
				map.put(String.valueOf(count), bean);
				count++;
			}
		}
		Object[] key = map.keySet().toArray();
		Arrays.sort(key);
		for (int i = 0; i < key.length; i++) {
			linkedList.add(map.get(key[i]));
		}
		return linkedList;
	}

	// ------------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 拆分菜单编号和功能编号
	 * 
	 * @param permissions_id
	 * @return [0]:菜单编号；[1]:功能编号
	 */
	@Logger(printSQL = true)
	protected String[] splitModuleIDAndPermissionID(String permissions_id) {
		String[] permissionid = permissions_id.split(",");

		Set<String> moduleSet = new HashSet<>();
		Set<String> permissionSet = new HashSet<>();

		for (String mpID : permissionid) {
			String moduleID = mpID.substring(0, mpID.indexOf('_'));
			String permissionID = mpID.substring(mpID.indexOf('_') + 1);

			permissionSet.add(permissionID);
			if (!moduleSet.contains(moduleID)) {
				moduleSet.add(moduleID);
			}

		}
		StringBuilder smoduleID = new StringBuilder();
		for (String moduleId : moduleSet) {
			smoduleID.append(moduleId);
			smoduleID.append(",");
		}
		StringBuilder sPermissionID = new StringBuilder();
		for (String permissionId : permissionSet) {
			sPermissionID.append(permissionId);
			sPermissionID.append(",");
		}

		return new String[] { smoduleID.toString(), sPermissionID.toString() };
	}

	/**
	 * 遍历页面流传来的所有菜单ID的父菜单
	 * 
	 * @param mudule_list
	 *            菜单集合
	 * @param s_moduleID
	 *            菜单编号组合
	 * @return
	 */
	@Logger(printSQL = true)
	protected String getParentIdByID(List<ModuleTreePojo> mudule_list, String s_moduleID) {
		String[] sModuleIDs = s_moduleID.split(",");
		Set<String> set = new HashSet<>();
		for (String moduleID : sModuleIDs) {
			set.add(moduleID);
			iterationModuleID(mudule_list, moduleID, set);
		}

		StringBuilder str = new StringBuilder();
		for (String id : set) {
			str.append(id);
			str.append(",");
		}

		return str.toString();
	}

	/**
	 * 根据菜单ID迭代出该菜单的所有父菜单
	 * 
	 * @param modulelist
	 *            菜单集合
	 * @param moduleID
	 *            菜单编号
	 * @param ids
	 *            返回数据
	 * @return
	 */
	@Logger(printSQL = true)
	public void iterationModuleID(List<ModuleTreePojo> modulelist, String moduleID, Set<String> ids) {
		for (ModuleTreePojo bean : modulelist) {
			if (moduleID.equals(bean.getId().trim())) {
				if (bean.getParentId() != null) {
					String parentId = bean.getParentId().trim();
					if (!"".equals(parentId) && !"0".equals(parentId)) {
						if (!ids.contains(parentId)) {
							ids.add(parentId);
						}
						iterationModuleID(modulelist, bean.getParentId().trim(), ids);
					}
				}
			}
		}
	}

	/**
	 * 封装角色添加的功能权限
	 * 
	 * @param role_id
	 *            角色编号
	 * @param permissions_id
	 *            功能编号
	 * @return
	 */
	@Logger(printSQL = true)
	protected List<ManageRolePermission> setRolePermission(String role_id, String permissions_id) {
		String[] permissionid = permissions_id.split(",");
		List<ManageRolePermission> list = new ArrayList<ManageRolePermission>();
		for (String str : permissionid) {
			ManageRolePermission bean = new ManageRolePermission();
			bean.setId(UUIDGenerator.generate());
			bean.setRoleId(role_id);
			bean.setPermissionId(str);
			list.add(bean);
		}
		return list;
	}

	/**
	 * 封装角色菜单对于关系
	 * 
	 * @param role_id
	 *            角色编号
	 * @param moduleIdlist
	 *            菜单编号
	 * @return
	 */
	@Logger(printSQL = true)
	protected List<ManageRoleModule> setRoleModule(String role_id, List<String> moduleIdlist) {
		List<ManageRoleModule> list = new ArrayList<ManageRoleModule>();
		for (String moduleids : moduleIdlist) {
			String[] moduleid = moduleids.substring(0, moduleids.length() - 1).split(",");
			for (String str : moduleid) {
				ManageRoleModule bean = new ManageRoleModule();
				bean.setId(UUIDGenerator.generate());
				bean.setRoleId(role_id);
				bean.setModuleId(str);
				list.add(bean);
			}
		}
		return list;
	}

	@Logger(printSQL = true)
	protected List<ManageRoleModule> setRoleModule(String role_id, String moduleId_list) {
		List<ManageRoleModule> list = new ArrayList<ManageRoleModule>();
		// for(String module_ids : moduleId_list){
		String[] moduleid = moduleId_list.split(",");
		for (String str : moduleid) {
			ManageRoleModule bean = new ManageRoleModule();
			bean.setId(UUIDGenerator.generate());
			bean.setRoleId(role_id);
			bean.setModuleId(str);
			list.add(bean);
		}
		// }
		return list;
	}

}
