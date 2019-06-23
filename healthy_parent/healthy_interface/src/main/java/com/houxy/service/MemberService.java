package com.houxy.service;

import com.houxy.pojo.Member;

import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/18
 * @description ：
 * @version: 1.0
 */
public interface MemberService {
    void reg(Member member);

    Member findByTelephone(String telephone);


    List<Integer> getReportMemberCount(List<String> months);
}
