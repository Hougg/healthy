package com.houxy.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.houxy.dao.MemberDao;
import com.houxy.dao.OrderDao;
import com.houxy.dao.SetMealDao;
import com.houxy.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/22
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    @Autowired
    SetMealDao setMealDao;

    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        //创建统计数据的map集合
        Map<String,Object> resultMap = new HashMap<>();
        //reportDate 统计 时间 -- 今天
        String reportDate = DateUtils.parseDate2String(new Date());
        System.out.println("今天"+reportDate);
        //获取本周周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本月1号的日期
        String thisMonthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //todayNewMember  今日新增会员数
        long todayNewMember = memberDao.findNewMemberCount(reportDate);
        //totalMember  查询总会员数
        long totalMember = memberDao.findTotalCount();
        //thisWeekNewMember 查询本周新增会员数
        long thisWeekNewMember = memberDao.findMemberCountByAfterDate(thisWeekMonday);
        //thisMonthNewMember :0, 本月新增会员数
        long thisMonthNewMember = memberDao.findMemberCountByAfterDate(thisMonthFirstDay);


        //todayOrderNumber : 今日预约数  - 今天应该到诊数
        long todayOrderNumber = orderDao.findCountByOrderDate(reportDate);
        //todayVisitsNumber  今日到诊数  -- 今天实际到诊数
        long todayVisitsNumber = orderDao.findVisitsByOrderDate(reportDate);
        //thisWeekOrderNumber 本周的预约数
        long thisWeekOrderNumber = orderDao.findCountByAfterOrderDate(thisWeekMonday);
        //thisWeekVisitsNumber :本周的到诊数
        long thisWeekVisitsNumber = orderDao.findVisitsCountByAfterOrderDate(thisWeekMonday);
        //thisMonthOrderNumber :0, 本月的预约数
        long thisMonthOrderNumber = orderDao.findCountByAfterOrderDate(thisMonthFirstDay);
        //thisMonthVisitsNumber :0, 本月的到诊数
        long thisMonthVisitsNumber = orderDao.findVisitsCountByAfterOrderDate(thisMonthFirstDay);

        //hotSetmeal 热门套餐 -- 查询预约最多的套餐 （前三名）
        List<Map<String,Object>> hotSetmeal = setMealDao.findHotSetmeal();



        //添加统计数据到map集合
        resultMap.put("reportDate",reportDate);
        resultMap.put("todayNewMember",todayNewMember);
        resultMap.put("totalMember",totalMember);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);
        resultMap.put("todayOrderNumber",todayOrderNumber);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        resultMap.put("hotSetmeal",hotSetmeal);

        return resultMap;
    }
}
