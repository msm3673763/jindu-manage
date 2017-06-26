package com.ucsmy.jindu.manage.manage.service;

import com.ucsmy.jindu.manage.commons.aop.exception.result.AosResult;
import com.ucsmy.jindu.manage.manage.entity.City;

import java.util.List;

/**
 * Created by ucs_mojiazhou on 2017/6/16.
 */
public interface CityService {

    AosResult getCitysByProvince(String provinceNo);

}
