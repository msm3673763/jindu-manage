package com.ucsmy.jindu.manage.manage.web;

import com.mongodb.gridfs.GridFSDBFile;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.Client;
import com.ucsmy.jindu.manage.manage.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by ucs_zhengfucheng on 2017/6/13.
 */
@Controller
@RequestMapping("client")
public class ClientController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    //返回的信息
    private static final String UPLOAD_ERROR_MESSAGE = "上传错误";
    private static final String UPLOAD_SUCCESS_MESSAGE = "上传成功";
    private static final String DELETE_ERROR_MESSAGE = "删除失败";
    private static final String DELETE_SUCCESS_MESSAGE = "删除成功";

    @Autowired
    private ClientService clientService;


    @RequestMapping("/query")
    @ResponseBody
    public PageResult<Client> queryClient(String name, int pageNum, int pageSize){
        PageResult<Client> pageResult;
        try {
            pageResult = clientService.queryClientListByPage(name, pageNum, pageSize);
        }catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            pageResult = new PageResult<>();
        }
        return pageResult;
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    @ResponseBody
    public AosResult upload(@RequestParam("file") MultipartFile file, Client client) {
        try {
            this.clientService.save(file, client);
            return AosResult.retSuccessMsg(UPLOAD_SUCCESS_MESSAGE);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return AosResult.retFailureMsg(UPLOAD_ERROR_MESSAGE);
        }
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AosResult delete(String id) {
        try {
            clientService.delete(id);
            return AosResult.retSuccessMsg(DELETE_SUCCESS_MESSAGE);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return AosResult.retFailureMsg(DELETE_ERROR_MESSAGE);
        }
    }

    @RequestMapping(value = "download/{id}", method = RequestMethod.GET)
    public String download(@PathVariable String id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        OutputStream outputStream = null;
        try {
            GridFSDBFile gridFSDBFile = clientService.getGridFSDBFile(id);
            httpServletResponse.setContentType("application/force-download");// 设置强制下载不打开
            httpServletResponse.addHeader("Content-Disposition",
                    "attachment;fileName=jindu.zip");
            outputStream = httpServletResponse.getOutputStream();
            gridFSDBFile.writeTo(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if(outputStream != null){
                    outputStream.close();
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return null;
    }

}
