package com.ucsmy.jindu.manage.commons.base.utils;

import java.util.Random;

/**
 * Created by 钟廷员 on 2016/12/9.
 * @author  钟廷员 on 2016/12/9.
 */
public class StringUtils extends org.springframework.util.StringUtils {
    private final static String CHARLIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static Number formatNumber(Object obj) {
        if(obj == null){
            return null;
        }
        if(obj instanceof Number) {
            return (Number)obj;
        }
        if(!(obj instanceof String)) {
            return null;
        }
        String s = (String)obj;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
        }
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
        }
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
        }
        return null;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 首字母大写
     * @param name
     * @return
     */
    public static String firstLetterUpperCase(String name) {
        char[] cs= name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 首字母小写
     * @param name
     * @return
     */
    public static String firstLetterLowerCase(String name) {
        char[] cs= name.toCharArray();
        cs[0] += 32;
        return String.valueOf(cs);
    }

    /**
     * 获取 lenght位数的随机数值和大小写字母组合字符串
     * @param length 随机位数
     * @return
     */
    public static String getRandom(int length){
        Random random = new Random();
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < length; i++){
            str.append(CHARLIST.charAt(random.nextInt(CHARLIST.length())));
        }
        return str.toString();
    }

    /**
     * 密码加密
     * @param userName 用户名
     * @param password 密码
     * @param salt 盐
     * @return MD5加密字符串
     */
    public static String passwordEncrypt(String userName, String password, String salt) {
        return EncryptUtils.mD5(EncryptUtils.sHA256(EncryptUtils.sHA512(userName.concat(password).concat(salt))));
    }

    public static void main(String[] args) {
        System.out.println(passwordEncrypt("钟廷员", "35240552", "123456"));
    }
}
