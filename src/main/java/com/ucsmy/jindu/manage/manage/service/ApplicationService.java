package com.ucsmy.jindu.manage.manage.service;

import com.mongodb.gridfs.GridFSDBFile;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Application;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ucs_mojiazhou on 2017/6/15.
 */
public interface ApplicationService {

    PageResult<Application> query(String applyName, int page, int size);

    AosResult add(MultipartFile entFile,MultipartFile movFile,Application application);

    AosResult update(MultipartFile entFile,MultipartFile movFile,Application application);

    AosResult del(String uuid);

    Application queryDetail(String uuid);

    /**
     * 返回 mongodb GridFS 对象
     * @param fileId
     * @return
     */
    GridFSDBFile getGridFSDBFile(String fileId);
}
