package com.ucsmy.jindu.manage.manage.service;


import com.mongodb.gridfs.GridFSDBFile;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.Client;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ucs_zhengfucheng on 2017/6/13.
 */
public interface ClientService {

    void save(MultipartFile file, Client client);

    PageResult<Client> queryClientListByPage(String name, int pageNum, int pageSize);

    void delete(String id);

    GridFSDBFile getGridFSDBFile(String id);
}
