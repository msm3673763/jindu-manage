package com.ucsmy.jindu.manage.manage.service.impl;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;
import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.base.utils.UUIDUtils;
import com.ucsmy.jindu.manage.manage.dao.ClientMapper;
import com.ucsmy.jindu.manage.manage.entity.Client;
import com.ucsmy.jindu.manage.manage.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by ucs_zhengfucheng on 2017/6/13.
 */
@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);
    private static final int PAGENUM = 1;
    private static final int PAGESIZE = 10;
    private static final String MONGODB_ID_COLUMN = "_id";


    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private ClientMapper clientMapper;


    @Override
    public void save(MultipartFile file, Client client) {
        //1. 文件数据保存到 mongodb
        Client updatedClient = this.saveFileWithMongoDB(file, client);
        //2. 文件信息保存到 mysql
        this.saveFileWithMysql(updatedClient);
    }

    @Override
    @com.ucsmy.jindu.manage.commons.aop.annotation.Logger(printSQL = true)
    public PageResult<Client> queryClientListByPage(String name, int pageNum, int pageSize) {
        if(pageNum <= 0){
            pageNum = PAGENUM;
        }
        if(pageSize <= 0){
            pageSize = PAGESIZE;
        }
        return this.clientMapper.queryClientListByPage(name, new PageRequest(pageNum, pageSize));
    }

    @Override
    public void delete(String id) {
        //0. 获取
        Client client = clientMapper.getClient(id);
        //1. 删除mysql
        this.clientMapper.deleteClient(id);
        //2. 删除mongodb
        Query query = new Query(Criteria.where(MONGODB_ID_COLUMN).is(client.getFileId()));
        this.gridFsTemplate.delete(query);
    }

    @Override
    public GridFSDBFile getGridFSDBFile(String id) {
        Client client = clientMapper.getClient(id);
        Query query = Query.query(Criteria.where(MONGODB_ID_COLUMN).is(client.getFileId()));
        return this.gridFsTemplate.findOne(query);
    }


    private Client saveFileWithMongoDB(MultipartFile file, Client client) {
        GridFSFile gridFSFile = null;
        try {
            //保存到mongodb
            gridFSFile = this.gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
            //从mongodb读出fileId, md5, createDate
            client.setFileId(gridFSFile.getId().toString());
            client.setMd5(gridFSFile.getMD5());
            client.setCreateDate(gridFSFile.getUploadDate());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    private void saveFileWithMysql(Client client) {
        client.setId(UUIDUtils.getUUID32());
        clientMapper.addClient(client);
    }
}
