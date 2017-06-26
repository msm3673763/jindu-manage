package com.ucsmy.jindu.manage.manage.service;


import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Parameter;

/**
 * Created by 
 * @author ucs_xujunwei on 2017/6/13.
 */
public interface ParameterService {

    /**
     * 分页查询参数
     * @param paramValue 参数名
     * @return
     */
    AosResult queryParameterList(String paramValue,String paramType, int pageNum, int pageSize);

    /**
     * 查询指定类型的参数
     * @param type 参数类别
     * @return
     */
    AosResult queryParameterByType(String type);

    /**
     * 获取类型列表
     * @return
     */
    AosResult getparamTypes();

    /**
     * 新增业务类型
     * @param parameter
     * @return
     */
    AosResult addParameter(Parameter parameter);

    /**
     * 更新业务类型
     * @param parameter
     * @return
     */
    AosResult updateParameter(Parameter parameter);

    /**
     * 删除业务类型
     * @param id
     * @return
     */
    AosResult delParameter(String id);
}
