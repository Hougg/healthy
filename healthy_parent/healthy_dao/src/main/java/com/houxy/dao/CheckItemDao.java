package com.houxy.dao;

import com.github.pagehelper.Page;
import com.houxy.pojo.CheckItem;

import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/10
 * @description ：
 * @version: 1.0
 */
public interface CheckItemDao {
    /**
     * 添加
     * @param checkItem
     */
    void add(CheckItem checkItem);

    Page<CheckItem> queryPage(String queryString);

    List<CheckItem> findAll();

    int findCountByCheckItemId(Integer id);

    void deleteById(Integer id);

    List<CheckItem> findCheckItemListByCheckGroupId(Integer id);

    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);
}
