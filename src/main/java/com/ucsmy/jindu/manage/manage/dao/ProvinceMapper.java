package com.ucsmy.jindu.manage.manage.dao;

import com.ucsmy.jindu.manage.manage.entity.Province;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by ucs_mojiazhou on 2017/6/16.
 */
@Mapper
public interface ProvinceMapper {

    List<Province> getProvince();

}
