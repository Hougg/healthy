package com.houxy.security;

import com.houxy.pojo.Role;
import com.houxy.pojo.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

/**
 * 如果需要从数据库中查询用户信息,必须提供一个使用了UserDetailsService接口的实现类
 * @author ：houxy
 * @date ：Created in 2019/6/19
 * @description ：
 * @version: 1.0
 */
public class SpringSecurity implements UserDetailsService {

    //模拟数据库的数据
    private static List<SysUser> userList = new ArrayList<>();
    static{
        SysUser sysUser1 = new SysUser();
        sysUser1.setUsername("zhangsan");
        sysUser1.setPassword("123");

        SysUser sysUser2 = new SysUser();
        sysUser2.setUsername("lisi");
        sysUser2.setPassword("123");

        //创建角色1
        Role role1 = new Role();
        role1.setName("ROLE_ADMIN");
        sysUser1.getRoles().add(role1);

        //创建角色2
        Role role2 = new Role();
        role2.setName("ROLE_USER");
        sysUser2.getRoles().add(role2);

        userList.add(sysUser1);
        userList.add(sysUser2);


    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        //1.根据用户名从数据库中查询用户信息
        //userDao.findByUserName()
        for (SysUser sysUser : userList) {
            if (s!=null&&s.equals(sysUser.getUsername())){
                //创建角色的集合对象
                List<GrantedAuthority> list = new ArrayList<>();
                for (Role role : sysUser.getRoles()) {
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getName());
                    list.add(simpleGrantedAuthority);
                }
        //2.返回User(安全框架定义)对象即可
                User user = new User(sysUser.getUsername(), "{noop}" + sysUser.getPassword(), list);
                return user;
            }
        }


        return null;
    }
}
