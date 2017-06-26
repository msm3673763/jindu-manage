package com.ucsmy.jindu.manage.manage.web;

import com.ucsmy.component.mybatis.page.PageResult;
import com.ucsmy.jindu.manage.manage.entity.Feedback;
import com.ucsmy.jindu.manage.manage.service.FeedbackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ucs_zhengfucheng on 2017/6/15.
 */
@Controller
@RequestMapping("feedback")
public class FeedbackController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;


    @RequestMapping("/query")
    @ResponseBody
    public PageResult<Feedback> query(String orderNumber, int pageNum, int pageSize) {
        PageResult<Feedback> pageResult;
        try {
            pageResult = feedbackService.queryFeedbackListByPage(orderNumber, pageNum, pageSize);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            pageResult = new PageResult<>();
        }

        return pageResult;
    }

}
