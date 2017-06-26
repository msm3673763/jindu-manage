package com.ucsmy.jindu.manage.commons.constant;

/**
 * Created by ucs_mojiazhou on 2017/6/20.
 */
public enum IntegrationCode {

    SUCCESS("成功",1), FAILURE("失败", -1), PARAMETER_ERROR("参数错误", -2),
    OTHER_ERROR("其它错误", -3),
    NULL_TICKET("TICKET为空", -400),
    INVAIL_TICKET("失效的TICKET", -401),
    CREATE_FAILER("创建操作员失败", -402),
    QUERY_NULL("查询无结果", -403)
    ;

    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private IntegrationCode(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
