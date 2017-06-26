package com.ucsmy.jindu.manage.manage.service;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by 
 * @author  ucs_panwenbo on 2017/4/14.
 */
public interface SysSecretKeyManagerService {


    Map<String,String> getRsaPubKey(HttpSession httpsesssion);

    Map<String,String> getAesKey(HttpSession httpsesssion);
}


