package com.ucsmy.jindu.manage.manage.service.impl;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/15

 * Contributors:
 *      - initial implementation
 */

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDUtils;
import com.ucsmy.jindu.manage.manage.dao.ApplicationMapper;
import com.ucsmy.jindu.manage.manage.entity.Application;
import com.ucsmy.jindu.manage.manage.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/15
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private GridFsTemplate gridFsTemplate;

    private static final String MONGODB_ID_COLUMN = "_id";

    @Override
    public PageResult<Application> query(String applyName, int page, int size) {
        return applicationMapper.query(applyName,new PageRequest(page,size));
    }

    @Override
    public AosResult add(MultipartFile entFile, MultipartFile movFile, Application application) {

        if(applicationMapper.isNameExist(application.getApplyName()) > 0) {

            return AosResult.retFailureMsg("应用名称已存在");
        }

        application.setUuid(UUIDUtils.getUUID32());
        try {
            if(null!=entFile) {
                application.setImgEnter(this.saveFile(entFile));
            }
            if(null!=movFile) {
                application.setImgMove(this.saveFile(movFile));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return AosResult.retFailureMsg("添加失败"+e.toString());
        }
        int recordCount = applicationMapper.add(application);
        if(recordCount > 0) {
            return AosResult.retSuccessMsg("添加成功");
        }
        return AosResult.retFailureMsg("添加失败");
    }

    @Override
    public AosResult update(MultipartFile entFile,MultipartFile movFile,Application application) {


        if(applicationMapper.isNameExistWithId(application.getApplyName(), application.getUuid()) > 0) {
            return AosResult.retFailureMsg("修改后的应用名称已存在");
        }

        Application original = applicationMapper.queryDetail(application.getUuid());

        try {
            if(null!=entFile) {
                this.delFile(application.getImgEnter());
                application.setImgEnter(this.saveFile(entFile));
            } else {
                application.setImgEnter(original.getImgEnter());
            }
            if (null!=movFile){
                this.delFile(application.getImgMove());
                application.setImgMove(this.saveFile(movFile));
            } else {
                application.setImgMove(original.getImgMove());
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return AosResult.retFailureMsg("更新失败"+e.toString());
        }

        int recordCount = applicationMapper.update(application);
        if(recordCount > 0) {
            return AosResult.retSuccessMsg("更新成功");
        }
        return AosResult.retFailureMsg("更新失败");
    }

    @Override
    public AosResult del(String uuid) {
        Application application =  this.queryDetail(uuid);
        if(null== application)
        {
            return AosResult.retFailureMsg("此数据不存在，不能删除");
        }
        this.delFile(application.getImgEnter());
        this.delFile(application.getImgMove());
        int i = applicationMapper.del(uuid);
        if(i>0)
        {
            return AosResult.retSuccessMsg("删除成功");
        }
        return AosResult.retFailureMsg("删除失败");
    }

    @Override
    public Application queryDetail(String uuid) {
        return applicationMapper.queryDetail(uuid);
    }


    @Override
    public GridFSDBFile getGridFSDBFile(String fileId) {
        Query query = Query.query(Criteria.where(MONGODB_ID_COLUMN).is(fileId));
        return this.gridFsTemplate.findOne(query);
    }


    public OutputStream getFile(String id) throws IOException {
        OutputStream outputStream = null;
        Query query = Query.query(Criteria.where(MONGODB_ID_COLUMN).is(id));
        GridFSDBFile gridFSDBFile = this.gridFsTemplate.findOne(query);

        if(null!=gridFSDBFile)
        {
            outputStream = new FileOutputStream("file");
            gridFSDBFile.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        }
        return outputStream;
    }

    /**
     * 保存文件到mogodb
     * @param file
     * @return
     * @throws IOException
     */
    private String saveFile(MultipartFile file) throws IOException {
        GridFSFile gridFSFile = null;
            gridFSFile = this.gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        return gridFSFile.getId().toString();
    }

    /****
     * 删除
     * @param fileId
     */
    private void delFile(String fileId)
    {
        //2. 删除mongodb
        Query query = new Query(Criteria.where(MONGODB_ID_COLUMN).is(fileId));
        this.gridFsTemplate.delete(query);
    }


}