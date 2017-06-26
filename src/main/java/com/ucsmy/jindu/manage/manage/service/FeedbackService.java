package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.Feedback;

/**
 * Created by ucs_zhengfucheng on 2017/6/15.
 */
public interface FeedbackService {

    PageResult<Feedback> queryFeedbackListByPage(String orderNumber, int pageNum, int pageSize);
}
