package com.houxy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.houxy.constant.MessageConstant;
import com.houxy.dao.MemberDao;
import com.houxy.dao.OrderDao;
import com.houxy.dao.OrderSettingDao;
import com.houxy.entity.Result;
import com.houxy.pojo.Member;
import com.houxy.pojo.Order;
import com.houxy.pojo.OrderSetting;
import com.houxy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/16
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;


    /**
     * 判断该日期是否进行了预约设置,如果设置,继续,否则,返回错误结果
     * 判断该日期是否预约人数已满,预约人数大于等于可预约人数,预约已满,返回错误结果
     * 判断是否是会员,如果不是,自动注册为会员;是会员,继续
     * 判断该会员是否在该日期预约了该套餐,如果预约了,返回错误结果,如果没有预约,继续
     * 无论是否是会员,都需要预约,开始预约
     * 更新预约的人数
     *
     * @param map
     * @return
     */
    @Override
    public Result addOrder(Map<String, Object> map) {
        //获取日期
        Object orderDateObj = map.get("orderDate");
        String orderDateStr = String.valueOf(orderDateObj);
        Date orderDate = null;
        try {
            orderDate = DateUtils.parseString2Date(orderDateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取手机号码
        Object telephoneObj = map.get("telephone");
        String telephoneStr = String.valueOf(telephoneObj);
        //获取套餐id
        Object setmealIdObj = map.get("setmealId");
        int setmealId = Integer.parseInt(String.valueOf(setmealIdObj));
        //判断该日期是否进行了预约设置,如果设置,继续,否则,返回
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if (orderSetting==null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if (orderSetting.getReservations()>=orderSetting.getNumber()){
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        Member member = memberDao.findByPhoneNumber(telephoneStr);
        if (member==null){
           member = new Member();
            String name = map.get("name").toString();
            member.setName(name);
            member.setSex(map.get("sex").toString());
            member.setIdCard(String.valueOf(map.get("idCard")));
            member.setPhoneNumber(telephoneStr);
            member.setRegTime(new Date());
            memberDao.reg(member);
        }else {
            Integer memberId = member.getId();
            Order order = new Order();
            order.setMemberId(memberId);
            order.setSetmealId(setmealId);
            order.setOrderDate(orderDate);
            Long count = orderDao.findByCondition(order);
            if (count>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(orderDate);
        order.setOrderType(String.valueOf(map.get("orderType")));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(setmealId);
        //添加预约信息
        orderDao.add(order);
        //更新预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        //更新预约设置对象
        orderSettingDao.edit(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map<String, Object> findById(Integer id) {
        return orderDao.findById(id);
    }
}
