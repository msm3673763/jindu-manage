package com.ucsmy.jindu.manage.manage.web;

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Parameter;
import com.ucsmy.jindu.manage.manage.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_xujunwei on 2017/6/13.
 */
@Controller
@RequestMapping("/parameter")
public class ParameterController {

    @Autowired
    private ParameterService parameterService;

    @RequestMapping("/queryParameterList")
    @ResponseBody
    public AosResult queryParameterList(String paramValue,String paramType, int pageNum, int pageSize){
        return this.parameterService.queryParameterList(paramValue,paramType, pageNum, pageSize);
    }

    @RequestMapping("/getparamTypes")
    @ResponseBody
    public AosResult getparamTypes()
    {
        return parameterService.getparamTypes();
    }

    @RequestMapping("/addParameter")
    @ResponseBody
    public AosResult addParameter(Parameter parameter)
    {
        return parameterService.addParameter(parameter);
    }

    @RequestMapping("/updateParameter")
    @ResponseBody
    public AosResult updateParameter(Parameter parameter)
    {
        return parameterService.updateParameter(parameter);
    }

    @RequestMapping("/delParameter")
    @ResponseBody
    public AosResult delParameter(String id)
    {
        return parameterService.delParameter(id);
    }

    @RequestMapping("/queryParameter")
    @ResponseBody
    public AosResult queryParameter(String type){
        return this.parameterService.queryParameterByType(type);
    }

}
