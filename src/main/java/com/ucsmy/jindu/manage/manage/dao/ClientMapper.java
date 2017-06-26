package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ucs_zhengfucheng on 2017/6/14.
 */
@Mapper
public interface ClientMapper {


    /**
     * 根据编号查询指定模板
     * @param name 客户端名称
     * @param pageRequest 分页实体
     * @return
     */
    PageResult<Client> queryClientListByPage(@Param("name")String name, PageRequest pageRequest);

    /**
     * 添加客户端文件
     * @param client
     * @return
     */
    int addClient(Client client);

    /**
     * 删除客户端文件
     * @param id
     * @return
     */
    int deleteClient(String id);

    /**
     * 获取客户端文件信息
     * @param id
     * @return
     */
    Client getClient(String id);
}
