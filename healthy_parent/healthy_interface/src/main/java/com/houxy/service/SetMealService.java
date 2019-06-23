package com.houxy.service;

import com.houxy.entity.PageResult;
import com.houxy.entity.QueryPageBean;
import com.houxy.pojo.CheckGroup;
import com.houxy.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/14
 * @description ：
 * @version: 1.0
 */
public interface SetMealService {

    void add(Integer[] checkgroupIds, Setmeal setmeal);

    PageResult findByPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, String>> findSetmealCount();
}
