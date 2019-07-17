package com.douya.webdao;


import com.douya.base.BaseDao;
import com.douya.model.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface WeixinDao extends BaseDao{

    public List login(Map map);

}