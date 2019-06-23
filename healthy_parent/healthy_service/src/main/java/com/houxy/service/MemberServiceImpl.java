package com.houxy.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.houxy.dao.MemberDao;
import com.houxy.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/18
 * @description ：
 * @version: 1.0
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberDao memberDao;

    @Override
    public void reg(Member member) {
        memberDao.reg(member);
    }

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByPhoneNumber(telephone);
    }

    @Override
    public List<Integer> getReportMemberCount(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month : months) {
            month +="-31";
            //查询该月份之前的会员数量
            int count = memberDao.findMemberCountByBeforeMonth(month);
            memberCount.add(count);
        }
        return memberCount;
    }


}
