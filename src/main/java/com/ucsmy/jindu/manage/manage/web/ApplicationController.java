package com.ucsmy.jindu.manage.manage.web;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/15

 * Contributors:
 *      - initial implementation
 */
import com.mongodb.gridfs.GridFSDBFile;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Application;
import com.ucsmy.jindu.manage.manage.service.ApplicationService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/15
 */
@Controller
@RequestMapping("application")
public class ApplicationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService applicationService;

    @RequestMapping("query")
    @ResponseBody
    public PageResult<Application> query(String applyName,
                                         @RequestParam(required = true) int pageNum, @RequestParam(required = true) int pageSize)
    {
        return applicationService.query(applyName,pageNum,pageSize);
    }

    @RequestMapping("add")
    @ResponseBody
    public AosResult add(@RequestParam("entFile") MultipartFile entFile,
                         @RequestParam("movFile") MultipartFile movFile,
                         Application application
                         )
    {
        return applicationService.add(entFile,movFile, application);
    }

    @RequestMapping("update")
    @ResponseBody
    public AosResult update(@RequestParam(value = "entFile", required = false) MultipartFile entFile,
                         @RequestParam(value = "movFile", required = false) MultipartFile movFile,
                         Application application
    )
    {
        return applicationService.update(entFile,movFile, application);
    }

    @RequestMapping("delete")
    @ResponseBody
    public AosResult delete(String uuid)
    {
        return applicationService.del(uuid);
    }


    @RequestMapping(value = "getImage/{fileId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage(@PathVariable String fileId)  {

        GridFSDBFile gridFSDBFile = applicationService.getGridFSDBFile(fileId);
        byte[] result;
        try {
            result = IOUtils.toByteArray(gridFSDBFile.getInputStream());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            result = new byte[0];
        }

        return result;
    }

}