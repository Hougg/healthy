package com.houxy.service;

import com.houxy.entity.PageResult;
import com.houxy.entity.QueryPageBean;
import com.houxy.pojo.CheckGroup;

import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult queryPage(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsById(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);


    void delById(Integer id);

    List<CheckGroup> findAll();
}
