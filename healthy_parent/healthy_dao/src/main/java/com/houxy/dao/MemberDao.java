package com.houxy.dao;

import com.houxy.pojo.Member;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/18
 * @description ：
 * @version: 1.0
 */
public interface MemberDao {
    Member findByPhoneNumber(String telephoneStr);

    void reg(Member member);

    int findMemberCountByBeforeMonth(String month);

    /**
     * 新增会员数
     * @param reportDate
     * @return
     */
    long findNewMemberCount(String reportDate);

    /**
     * 查询总会员数
     * @return
     */
    long findTotalCount();

    /**
     * 新增会员数
     * @param date
     * @return
     */
    long findMemberCountByAfterDate(String date);
}
