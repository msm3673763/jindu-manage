package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ucs_zhengfucheng on 2017/6/15.
 */
@Mapper
public interface FeedbackMapper {

    /**
     * 反馈意见的分页
     * @param orderNumber 工单号
     * @param pageRequest
     * @return
     */
    PageResult<Feedback> queryFeedbackListByPage(@Param("orderNumber") String orderNumber, PageRequest pageRequest);


}
