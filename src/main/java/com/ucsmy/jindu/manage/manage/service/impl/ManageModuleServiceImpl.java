package com.ucsmy.jindu.manage.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
//import com.ucsmy.commons.tree.TreeTool;
import com.ucsmy.jindu.manage.commons.aop.annotation.Logger;
import com.ucsmy.jindu.manage.commons.base.interceptor.tree.TreeTool;
import com.ucsmy.jindu.manage.config.shiro.ShiroRealmImpl;
import com.ucsmy.jindu.manage.manage.constant.ManageConfigConstant;
import com.ucsmy.jindu.manage.manage.dao.ManageConfigMapper;
import com.ucsmy.jindu.manage.manage.dao.ManageModuleMapper;
import com.ucsmy.jindu.manage.manage.dao.ManagePermissionMapper;
import com.ucsmy.jindu.manage.manage.dao.ManageRoleModuleMapper;
import com.ucsmy.jindu.manage.manage.entity.ManageModule;
import com.ucsmy.jindu.manage.manage.ext.MainModulePojo;
import com.ucsmy.jindu.manage.manage.ext.ModuleTreePojo;
import com.ucsmy.jindu.manage.manage.service.ManageModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * module service Created by
 * 
 * @author chenqilin on 2017/4/13.
 */
@Service
public class ManageModuleServiceImpl implements ManageModuleService {

	@Autowired
	private ManageModuleMapper manageModuleMapper;
	@Autowired
	private ManagePermissionMapper managePermissionMapper;
	@Autowired
	private ManageRoleModuleMapper manageRoleModuleMapper;
	@Autowired
	private ManageConfigMapper manageConfigMapper;

	@Override
	public List<ModuleTreePojo> getModuleListByCondition(String name, String parentId, String excludeId) {
		Map<String, String> map = new HashMap<>();
		map.put("name", name);
		map.put("parentId", parentId);
		map.put("excludeId", excludeId);
		return manageModuleMapper.getModuleListByMap(map);
	}

	@Override
	@SuppressWarnings("unchecked")
	@Logger(operationName = "查询菜单", printSQL = true)
	public List<ModuleTreePojo> getModuleListByCondition(String name) {
		List<ModuleTreePojo> list = manageModuleMapper.getModuleList(name, null);
		return (List<ModuleTreePojo>) TreeTool.getTreeList(list);
	}

	@Override
	@Logger(operationName = "查询菜单详情", printSQL = true)
	public ModuleTreePojo getModuleDetail(String id) {
		return manageModuleMapper.getModuleDetail(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addModule(ManageModule module) {
		return manageModuleMapper.addModule(module);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@Logger(operationName = "更新菜单", printSQL = true)
	public int updateModule(ManageModule module) {
		return manageModuleMapper.updateModule(module);
	}

	@Override
	@Logger(operationName = "查询子菜单", printSQL = true)
	public int getChildrenNum(String id) {
		return manageModuleMapper.getChildrenNum(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@Logger(operationName = "删除菜单", printSQL = true)
	public int deleteModule(String id) {
		managePermissionMapper.deletePermissionByModule(id);
		manageRoleModuleMapper.deleteRoleByModuleId(id);
		return manageModuleMapper.deleteModule(id);
	}

	@Override
	@Logger(operationName = "根据用户查询菜单", printSQL = true)
	public List<MainModulePojo> queryMainModuleByUser(ShiroRealmImpl.LoginUser user) {
		List<MainModulePojo> rawList;
		if ("admin".equals(user.getUserName())) {
			rawList = manageModuleMapper.queryMainAllModule();
		} else {
			rawList = manageModuleMapper.queryMainModuleByUserId(user.getId());
		}
		List<MainModulePojo> nodeList = new ArrayList<>();
		for (MainModulePojo node1 : rawList) {
			boolean mark = false;
			if (node1.getIcon() == null) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("left", "iconfont menu-left-span " + node1.getImage());
				node1.setIcon(jsonObject);
			}
			node1.setHref("javascript:;");

			String excludeName = manageConfigMapper.getValueByKey(ManageConfigConstant.EXCLUDE_MODULE_NAME);
			if (node1.getName().equals(excludeName)) {
				continue;
			}

			for (MainModulePojo node2 : rawList) {
				if (node1.getParentId() != null && node1.getParentId().equals(node2.getId())) {
					mark = true;
					node2.setHref("");
					node2.setChildShow(true);
					JSONObject iconJson = new JSONObject();
					iconJson.put("left", "iconfont menu-left-span " + node2.getImage());
					iconJson.put("right", "iconfont menu-right-span");
					node2.setIcon(iconJson);
					if (node2.getChildren() == null) {
						node2.setChildren(new ArrayList<MainModulePojo>());
					}
					node2.getChildren().add(node1);
					node2.setClassName("icon-project");
					break;
				}
			}
			if (!mark) {
				if (node1.getParentId() == null || "".equals(node1.getParentId().trim())
						|| "0".equals(node1.getParentId().trim())) {
					nodeList.add(node1);
				}
			}
		}
		return nodeList.get(0).getChildren();
	}
}
