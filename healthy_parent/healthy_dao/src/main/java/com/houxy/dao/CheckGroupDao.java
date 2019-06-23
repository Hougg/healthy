package com.houxy.dao;

import com.github.pagehelper.Page;
import com.houxy.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
public interface CheckGroupDao {

    void add(CheckGroup checkGroup);

    void insert(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemId") Integer checkitemId);


    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsById(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(Integer id);

    int findCountById(Integer id);

    void delById(Integer id);

    List<CheckGroup> findAll();

    List<CheckGroup> findCheckGroupListBySetmealId(Integer id);

}
