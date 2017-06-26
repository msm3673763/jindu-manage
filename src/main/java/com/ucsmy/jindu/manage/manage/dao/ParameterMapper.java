package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.ParamType;
import com.ucsmy.jindu.manage.manage.entity.Parameter;
import com.ucsmy.jindu.manage.manage.ext.ParameterPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 
 * @author  ucs_xujunwei on 2017/6/13.
 */
@Mapper
public interface ParameterMapper {

    /**
     * 分页查询业务参数列表
     * @param paramValue 参数名
     * @param pageRequest 分页实体
     * @return
     */
    PageResult<ParameterPojo> queryParameterListByPage(@Param("paramValue")String paramValue,@Param("paramType")String paramType, PageRequest pageRequest);

    /**
     * 根据类别查询对应参数
     * @param type 参数类别
     * @return
     */
    List<Parameter> queryParameterByType(@Param("type")String type);



    /**
     * 根据类别和key查询对应参数
     * @param type 参数类别
     * @param key 参数key
     * @return
     */
    Parameter queryParameterByTypeAndKey(@Param("type")String type, @Param("key")String key);

    List<ParamType> getparamTypes();


    int addParameter(Parameter parameter);

    int updateParameter(Parameter parameter);

    int deleteParameter(String id);

    int isExitParam(Parameter parameter);
}
