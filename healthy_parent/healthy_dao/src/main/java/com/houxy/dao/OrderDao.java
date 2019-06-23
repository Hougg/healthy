package com.houxy.dao;

import com.houxy.pojo.Order;

import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/20
 * @description ：
 * @version: 1.0
 */
public interface OrderDao {
    Long findByCondition(Order order);

    void add(Order order);

    Map<String, Object> findById(Integer id);

    /**
     * 今日应该到诊数
     * @param reportDate
     * @return
     */
    long findCountByOrderDate(String reportDate);

    /**
     * 今日实际到诊数
     * @param reportDate
     * @return
     */
    long findVisitsByOrderDate(String reportDate);

    /**
     * 查询指定日期后的预约数
     * @param date
     * @return
     */
    long findCountByAfterOrderDate(String date);

    /**
     * 查询指定日期之后的到诊数
     * @param date
     * @return
     */
    long findVisitsCountByAfterOrderDate(String date);
}
