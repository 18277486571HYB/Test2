package com.hyb.test2.handler;

import com.alibaba.fastjson.JSON;
import com.hyb.test2.utils.JwtUtils;
import com.hyb.test2.utils.Msg;
import com.hyb.test2.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {


    @Autowired
    RedisUtils redisUtils;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        String[] users = username.split("-");
        // TODO 得到该用户全部角色
        //Collection<GrantedAuthority> authorities = principal.getAuthorities();

        //TODO 查询数据库得到该角色所能访问的全部路径
        //模拟:
        String[] s1=new String[]{"/login","/register","/serviceedu/front/listTeacher"};

        //将权限路径封装到redis中

        redisUtils.setCollectionSet(users[0],s1,24, TimeUnit.HOURS);
        String s= redisUtils.get("fromUrl");
        if (s==null)
            s="/";
        String jwtToken = JwtUtils.getJwtToken(users[1], users[0]);
        Msg msg = Msg.success().data("username", users[0]).data("fromUrl",s).data("token",jwtToken);
        redisUtils.del("fromUrl");
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(msg));

    }
}
