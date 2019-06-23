package com.houxy.dao;

import com.github.pagehelper.Page;
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
public interface SetMealDao {
    void add(Setmeal setmeal);

    void insert(Integer setMealId, Integer checkgroupId);

    Page<Setmeal> findByCondition(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, String>> findSetmealCount();

    List<Map<String, Object>> findHotSetmeal();
}
