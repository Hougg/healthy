package com.houxy.dao;

import com.houxy.pojo.SysUser;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/20
 * @description ：
 * @version: 1.0
 */
public interface UserDao {
    SysUser findByUsername(String s);
}
