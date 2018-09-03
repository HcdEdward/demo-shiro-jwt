package com.example.control;

import com.example.util.JwtUtil;
import com.example.vo.Result;
import com.example.vo.ResultGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.Serializable;

@RestController
public class IndexController {

    @PostMapping ("/helloworld")
    public Result helloWorld() {
        Subject subject = SecurityUtils.getSubject();
        return ResultGenerator.genSuccessResult("helloworld"+":"+subject.getPrincipal());
    }

    @GetMapping ("/noLogin")
    public Result nologin() {
        Subject subject = SecurityUtils.getSubject();
        return ResultGenerator.genSuccessResult("未登陆！！");
    }

    @PostMapping("/noauthor")
    public Result noauthor() {
        Subject subject = SecurityUtils.getSubject();
        return ResultGenerator.genSuccessResult("没权限！！");
    }

    @PostMapping("/logout")
    public Result logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();//自定删除session和redis缓存
        return ResultGenerator.genSuccessResult("登出！！");
    }

//没有添加JWT验证
    @PostMapping("/doLogin")
    public Result doLogin(String username, String password) {
        boolean rememberMe = true;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            token.clear();
            return ResultGenerator.genFailResult("登录失败，用户名或密码错误！");
        }
        return ResultGenerator.genSuccessResult("登录成功");
    }

    //添加JWT验证
    @PostMapping("/logins")
    public String login(String username, String password) throws ServletException {
        boolean rememberMe = true;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            token.clear();
            throw new ServletException("wrong password and wrong password");
        }
        return JwtUtil.getToken(username);
    }

    //没有添加JWT验证
    @GetMapping("/tokenLogin")
    public Serializable tokenLogin(String username, String password)throws ServletException {
        boolean rememberMe = true;
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            token.clear();
            throw new ServletException("wrong password and wrong password");
        }
        return subject.getSession().getId();
    }

}
