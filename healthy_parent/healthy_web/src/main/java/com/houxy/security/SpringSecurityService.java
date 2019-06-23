package com.houxy.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.houxy.pojo.Permission;
import com.houxy.pojo.Role;
import com.houxy.pojo.SysUser;
import com.houxy.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：houxy
 * @date ：Created in 2019/6/20
 * @description ：
 * @version: 1.0
 */
@Component
public class SpringSecurityService implements UserDetailsService {
    //需要使用注解扫描,在spring-mvc.xml中设置
    @Reference//通过dubbo远程调用用户服务
    UserService userService;

    /**
     *
     * @param s 用户输入的username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //根据用户名(SysUser)查询数据库中用户信息
        //s是从表单中获取的username,与表单数据所设不一致时,
        // 需在安全框架配置文件中配置username-parameter="username",此时username为表单所设
        SysUser sysUser = userService.findByUsername(s);
        if (sysUser==null){
            return null;
        }
        //创建权限集合列表
        List<GrantedAuthority> list = new ArrayList<>();
        //创建权限对象(权限名从数据库中获取)
        for (Role role : sysUser.getRoles()) {
            for (Permission permission : role.getPermissions()) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission.getKeyword());
                list.add(authority);
            }
        }
        UserDetails userDetails = new User(sysUser.getUsername(), sysUser.getPassword(), list);
        return userDetails;
    }
}
