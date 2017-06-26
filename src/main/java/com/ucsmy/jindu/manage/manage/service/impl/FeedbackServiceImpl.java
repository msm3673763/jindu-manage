package com.ucsmy.jindu.manage.manage.service.impl;

import com.ucsmy.component.mybatis.page.PageRequest;
import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.dao.FeedbackMapper;
import com.ucsmy.jindu.manage.manage.entity.Feedback;
import com.ucsmy.jindu.manage.manage.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ucs_zhengfucheng on 2017/6/15.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final int PAGENUM = 1;

    private static final int PAGESIZE = 10;

    @Autowired
    private FeedbackMapper feedbackMapper;


    @Override
    public PageResult<Feedback> queryFeedbackListByPage(String orderNumber, int pageNum, int pageSize) {
        if(pageNum <= 0){
            pageNum = PAGENUM;
        }
        if(pageSize <= 0){
            pageSize = PAGESIZE;
        }
        return feedbackMapper.queryFeedbackListByPage(orderNumber, new PageRequest(pageNum, pageSize));
    }
}
