package com.ucsmy.jindu.manage.manage.service.impl;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.StringAndNumberUtil;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDUtils;
import com.ucsmy.jindu.manage.manage.dao.ParameterMapper;
import com.ucsmy.jindu.manage.manage.entity.ParamType;
import com.ucsmy.jindu.manage.manage.entity.Parameter;
import com.ucsmy.jindu.manage.manage.ext.ParameterPojo;
import com.ucsmy.jindu.manage.manage.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ucs_xujunwei on 2017/6/14.
 */
@Service
public class ParameterServiceImpl implements ParameterService{

    @Autowired
    private ParameterMapper parameterMapper;

    public static final int PAGENUM = 1;
    public static final int PAGESIZE = 10;

    /**
     * 分页查询参数
     * @param paramValue 参数名
     * @return
     */
    public AosResult queryParameterList(String paramValue,String paramType, int pageNum, int pageSize){
        if(pageNum <= 0){
            pageNum = PAGENUM;
        }
        if(pageSize <= 0){
            pageSize = PAGESIZE;
        }
        PageResult<ParameterPojo> parameterList = this.parameterMapper.queryParameterListByPage(paramValue,paramType, new PageRequest(pageNum, pageSize));
        return AosResult.retSuccessMsg("", parameterList);
    }

    /**
     * 查询指定类型的参数
     * @param type 参数类别
     * @return
     */
    public AosResult queryParameterByType(String type){
        if(StringAndNumberUtil.isNullAfterTrim(type)){
            return AosResult.retFailureMsg("参数错误");
        }
        List<Parameter> parameterList = this.parameterMapper.queryParameterByType(type);
        return AosResult.retSuccessMsg("",parameterList);
    }

    @Override
    public AosResult getparamTypes() {

        List<ParamType> list = parameterMapper.getparamTypes();
        if(null==list && list.size()==0)
        {
            return AosResult.retFailureMsg("没有数据");
        }
        List<Map> maps = new ArrayList<>();
        for (ParamType paramType: list) {
            Map map = new HashMap();
            map.put("value", paramType.getParamType());
            map.put("option", paramType.getName());
            maps.add(map);
        }
        return AosResult.retSuccessMsg("success",maps);
    }

    @Override
    public AosResult addParameter(Parameter parameter) {

        if(this.isExitParam(parameter))
           return AosResult.retFailureMsg("当前类型下，已存在参数代码");
        parameter.setId(UUIDUtils.getUUID32());
       int i = parameterMapper.addParameter(parameter);
        if(i>0)
            return AosResult.retSuccessMsg("添加成功");
        return AosResult.retFailureMsg("添加失败");

    }

    @Override
    public AosResult updateParameter(Parameter parameter) {
        if(this.isExitParam(parameter))
            return AosResult.retFailureMsg("当前类型下，已存在参数代码");
        int i = parameterMapper.updateParameter(parameter);
        if(i>0)
            return AosResult.retSuccessMsg("更新成功");
        return AosResult.retFailureMsg("更新失败");
    }

    @Override
    public AosResult delParameter(String id) {
        int i = parameterMapper.deleteParameter(id);
        if(i>0)
            return AosResult.retSuccessMsg("删除成功");
        return AosResult.retFailureMsg("删除失败");
    }

    private boolean isExitParam(Parameter parameter)
    {
         return parameterMapper.isExitParam(parameter)>0;
    }
}
