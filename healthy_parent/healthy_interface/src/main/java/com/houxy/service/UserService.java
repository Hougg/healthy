package com.houxy.service;

import com.houxy.pojo.SysUser;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/20
 * @description ：
 * @version: 1.0
 */
public interface UserService {
    SysUser findByUsername(String s);
}
