package com.houxy.service;

import com.houxy.entity.Result;

import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/16
 * @description ：
 * @version: 1.0
 */
public interface OrderService {
    Result addOrder(Map<String, Object> map);

    Map<String, Object> findById(Integer id);
}
