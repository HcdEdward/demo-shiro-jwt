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

@RestController
public class IndexController {

    @GetMapping("/helloworld")
    public Result helloWorld() {
        return ResultGenerator.genSuccessResult("helloworld");
    }
//添加shiro验证
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

    //没有添加shiro验证
    @PostMapping("/login")
    public String login(String username, String password) throws ServletException {
        if (!"admin".equals(username)) {
            throw new ServletException("no such user");
        }
        if (!"123".equals(password)) {
            throw new ServletException("wrong password");
        }
        return JwtUtil.getToken(username);
    }

}
