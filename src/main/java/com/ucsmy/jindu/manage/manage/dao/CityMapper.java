package com.ucsmy.jindu.manage.manage.dao;/*
 * Copyright (c) 2017 UCSMY.
 * All rights reserved.
 * Created on 2017/6/16

 * Contributors:
 *      - initial implementation
 */

import com.ucsmy.jindu.manage.manage.entity.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 暂无描述
 *
 * @author ucs_mojiazhou
 * @since 2017/6/16
 */
@Mapper
public interface CityMapper {

    List<City> getCitys(@Param("provinceNo")String provinceNo);

}