package com.ucsmy.jindu.manage.manage.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.ucsmy.commons.utils.StringUtils;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.StringUtils;
import com.ucsmy.jindu.manage.config.LoginTypeConfig;
import com.ucsmy.jindu.manage.config.shiro.ShiroRealmImpl;
import com.ucsmy.jindu.manage.config.shiro.ShiroUtils;
import com.ucsmy.jindu.manage.manage.entity.ManagePermission;
import com.ucsmy.jindu.manage.manage.ext.MainModulePojo;
import com.ucsmy.jindu.manage.manage.ext.UserProfilePojo;
import com.ucsmy.jindu.manage.manage.service.ManageCommonService;
import com.ucsmy.jindu.manage.manage.service.ManageModuleService;
import com.ucsmy.jindu.manage.manage.service.ManagePermissionService;
import com.ucsmy.jindu.manage.manage.service.ManageUserProfileService;
import com.ucsmy.jindu.manage.manage.service.SysSecretKeyManagerService;

/**
 * @author chenqilin
 * @version V1.0
 * @ClassName:
 * @Description:
 *
 */
@Controller
public class ManageLoginController {

    @Autowired
    private ManagePermissionService managePermissionService;
    @Autowired
    private ManageModuleService manageModuleService;
    @Autowired
    private ManageUserProfileService manageUserProfileService;
    @Autowired
    private SysSecretKeyManagerService sysSecretKeyManagerService;
    @Autowired
    private LoginTypeConfig loginTypeConfig;


    @Autowired
    private ManageCommonService manageCommonService;

    @RequestMapping(value = "login")
    public String login (HttpServletRequest httpRequest, Model model) {
        Map<String,String> map = new HashMap();

        String ctx = httpRequest.getScheme()+"://"+httpRequest.getServerName() //服务器地址
                + ":"
                + httpRequest.getServerPort()           //端口号
                + httpRequest.getContextPath() ;    //项目名称
        model.addAttribute("ctx", ctx + "/");
            map.put("loginType","local");

        if(SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:index";
        }
        return "login/index";
    }

    @RequestMapping(value = "outSys")
    public String outSys (HttpServletRequest httpRequest, Model model) {

        SecurityUtils.getSubject().logout();
        return "redirect:login";
//        Map<String,String> map = new HashMap();
//
//        String ctx = httpRequest.getScheme()+"://"+httpRequest.getServerName() //服务器地址
//                + ":"
//                + httpRequest.getServerPort()           //端口号
//                + httpRequest.getContextPath() ;    //项目名称
//        model.addAttribute("ctx", ctx + "/");
//        System.out.println("ctx:"+ctx);
//        ctx = ctx+"/"+ loginTypeConfig.getMainIndex();
//
//        if(loginTypeConfig.getLocalType())
//        {
//            return "redirect:index";
//        }else
//        {
//            return "redirect:"+manageCommonService.concantRootUrl(loginTypeConfig.getLogout())+loginTypeConfig.getClientId()+"&redirect_uri="+ctx;
//
//        }
    }


    @RequestMapping(value = "index")
    public String index(HttpServletRequest httpRequest, Model model) {
        String ctx = httpRequest.getScheme()+"://"+httpRequest.getServerName() //服务器地址
                + ":"
                + httpRequest.getServerPort()           //端口号
                + httpRequest.getContextPath() ;    //项目名称
        model.addAttribute("ctx", ctx + "/");
        System.out.println("ctx:"+ctx);
        // 获取用户基本信息
        ShiroRealmImpl.LoginUser user = ShiroUtils.getContextUser();
        List<ManagePermission> permissionList = managePermissionService.queryPermissionByUser(user);
        List<MainModulePojo> moduleList = manageModuleService.queryMainModuleByUser(user);
        // 权限
        Map<String, Boolean> roles = new HashMap<>();
        for(ManagePermission p : permissionList) {
            if(StringUtils.isEmpty(p.getSn())){
                continue;
            }
            roles.put(p.getSn(), true);
        }

        UserProfilePojo userProfile  = manageUserProfileService.queryUserProfileByUserId(user.getId());

        model.addAttribute("userProfile", userProfile);
        // 菜单
        model.addAttribute("user_modules", moduleList);
        // 登录信息
        model.addAttribute("loginUser", user);
        //用户角色
        model.addAttribute("user_roles", roles);
        return "main/index";
    }

    /**
     * 绑定网金帐号页面
     * @return
     */
    @RequestMapping(value = "bindAccount")
    public String bindAccount(HttpServletRequest httpRequest, Model model) {
        String ctx = httpRequest.getScheme()+"://"+httpRequest.getServerName() //服务器地址
                + ":"
                + httpRequest.getServerPort()           //端口号
                + httpRequest.getContextPath() ;    //项目名称
        model.addAttribute("ctx", ctx + "/");
        System.out.println("ctx:"+ctx);

        if(SecurityUtils.getSubject().isAuthenticated()){
            return "redirect:index";
        }
        return "bind/index";
    }


    @RequestMapping(value = "getRsa")
    @ResponseBody
    public AosResult getRsa(HttpSession httpSession){
        Map<String,String> map;
        map = sysSecretKeyManagerService.getRsaPubKey(httpSession);

        if (loginTypeConfig.getLocalType()){
            map.put("loginType","local");
         }else {
            map.put("loginType","other");
            map.put("oauth2Url",manageCommonService.concantRootUrl(loginTypeConfig.getOauth2Url()));
        }
        return AosResult.retSuccessMsg("success", map );
    }
}
