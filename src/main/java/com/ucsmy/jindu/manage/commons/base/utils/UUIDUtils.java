package com.ucsmy.jindu.manage.commons.base.utils;

import java.util.UUID;

/**
 * Created by ucs_xujunwei on 2017/6/14.
 */
public class UUIDUtils {

    /**
     * 获得36位uuid
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    /**
     * 去除分隔符，返回32位uuid
     * @return
     */
    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
