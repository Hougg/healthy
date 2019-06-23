package com.houxy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.houxy.dao.UserDao;
import com.houxy.pojo.SysUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/20
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public SysUser findByUsername(String s) {
        return userDao.findByUsername(s);
    }
}
