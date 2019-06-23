package com.houxy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.houxy.dao.CheckItemDao;
import com.houxy.entity.PageResult;
import com.houxy.entity.QueryPageBean;
import com.houxy.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/10
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass =CheckItemService.class )
@Transactional
public class CheckItemServiceImpl implements CheckItemService{

    @Autowired
    CheckItemDao checkItemDao;




    /**
     * 添加
     * @param checkItem
     */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.queryPage(queryString);
        return new PageResult(page.getTotal(),page);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void delete(Integer id) {
        int count = checkItemDao.findCountByCheckItemId(id);
        if (count>0){
            //当前检查项被引用,不能删除
            throw new RuntimeException("当前检查项被引用,不能删除");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

}
