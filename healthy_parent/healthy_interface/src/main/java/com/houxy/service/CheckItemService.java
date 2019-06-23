package com.houxy.service;

import com.houxy.entity.PageResult;
import com.houxy.pojo.CheckItem;

import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/10
 * @description ：
 * @version: 1.0
 */

public interface CheckItemService {
    /**
     * 添加
     * @param checkItem
     */
    void add(CheckItem checkItem);


    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @param queryString
     * @return
     */
    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    List<CheckItem> findAll();

    void delete(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);
}
