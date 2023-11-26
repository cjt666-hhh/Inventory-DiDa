package com.example.demo.controller;

import com.example.demo.entity.TbLogin;
import com.example.demo.pojo.Result;
import com.example.demo.service.impl.TbLoginServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
@ApiOperation("登录注册捏")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    TbLoginServiceImpl tbLoginService;

    @ApiOperation("登录账号")
    @PostMapping("/userLogin")
    public Result login(@RequestBody TbLogin tbLogin){

        String username=tbLogin.getUsername();
        String password=tbLogin.getPassword();

        TbLogin user=tbLoginService.getByPasswordAndUserName(username,password);

        return  user != null ? Result.success(user):Result.error("用户名或密码错误");

    }
    @ApiOperation("注册账号")
    @PostMapping("/userRegiser")
    public Result register(@RequestBody TbLogin tbLogin){

        tbLoginService.register(tbLogin);

        return  Result.success();
    }






}
