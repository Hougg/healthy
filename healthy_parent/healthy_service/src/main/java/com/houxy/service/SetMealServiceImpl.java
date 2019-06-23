package com.houxy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.houxy.constant.RedisConstant;
import com.houxy.dao.SetMealDao;
import com.houxy.entity.PageResult;
import com.houxy.entity.QueryPageBean;
import com.houxy.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/14
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {

    @Autowired
    SetMealDao setMealDao;

    @Autowired
    JedisPool jedisPool;

    @Override
    public void add(Integer[] checkgroupIds, Setmeal setmeal) {
        setMealDao.add(setmeal);
        setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        //把图片名称写入redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
    }

    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Setmeal> page = setMealDao.findByCondition(queryPageBean);
        return new PageResult(page.getTotal(),page);

    }

    @Override
    public List<Setmeal> findAll() {
        return setMealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }

    @Override
    public List<Map<String, String>> findSetmealCount() {
        return setMealDao.findSetmealCount();
    }

    //维护套餐和检查组的关系
    private void setSetmealAndCheckGroup(Integer setMealId,Integer[] checkgroupIds){
        if (setMealId!=null&&checkgroupIds!=null&&checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                setMealDao.insert(setMealId,checkgroupId);
            }
        }
    }

}
