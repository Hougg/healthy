package com.houxy.service;

import com.alibaba.dubbo.config.annotation.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.houxy.dao.CheckGroupDao;
import com.houxy.entity.PageResult;
import com.houxy.entity.QueryPageBean;
import com.houxy.pojo.CheckGroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;


/**
 * @author ：houxy
 * @date ：Created in 2019/6/11
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional//添加事务以后,若不添加interfaceClass,会导致注册不成功
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

    @Override
    //添加检查组合,同时需要设置检查组合和检查项的关联关系
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {

            //添加检查组,必须拿到检查组的id(mybatis中的主键回显操作)

            checkGroupDao.add(checkGroup);
        System.out.println("serviceimpl"+checkGroup.getId()+ Arrays.toString(checkitemIds));
            setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);


    }


    /**
     * 维护检查组和检查项的关系
     * @param checkGroupId
     * @param checkitemIds
     */
    private void setCheckGroupAndCheckItem(Integer checkGroupId,Integer[] checkitemIds){
        if (checkGroupId!=null&&checkitemIds!=null&&checkitemIds.length>0){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.insert(checkGroupId,checkitemId);
            }
        }

    }

//分页
    @Override
    public PageResult queryPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
       Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal() , page);
    }

    @Override
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS )
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public List<Integer> findCheckItemIdsById(Integer id) {
        return checkGroupDao.findCheckItemIdsById(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //修改检查组
        checkGroupDao.edit(checkGroup);
        //删除该检查组与检查项的关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //维护新的关系
        this.setCheckGroupAndCheckItem(checkGroup.getId(),checkitemIds);

    }
    /**
     * 如果检查组被套餐关联，不能删除
     *  0. 删除检查项与检查组的关系
     *  1. 判断是否被关联
     *  2. 如果没有关联，删除
     *  3. 如果关联，抛出运行时异常
     * @param id
     */
    @Override
    public void delById(Integer id) {
        checkGroupDao.deleteAssociation(id);
        int count = checkGroupDao.findCountById(id);
        if (count>0){
            throw new RuntimeException("该检查组被套餐关联");
        }
            checkGroupDao.delById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

}
