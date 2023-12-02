package com.example.demo.controller;

import com.example.demo.entity.TbLogin;
import com.example.demo.pojo.Result;
import com.example.demo.service.impl.TbLoginServiceImpl;
import com.example.demo.utils.JwtUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ApiOperation("登录注册捏")
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    TbLoginServiceImpl tbLoginService;

    @ApiOperation("登录账号")
    @PostMapping("/userLogin/{username}/{password}")
    public Result login(@PathVariable("username")String username,@PathVariable("password")String password){



        TbLogin user=tbLoginService.getByPasswordAndUserName(username,password);
        //判断：登录用户是否存在
        if(user !=null ){
//自定义信息
            Map<String , Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username",user.getUsername());
            claims.put("name",user.getName());
//使用JWT工具类，生成身份令牌
            String token = JwtUtils.generateJwt(claims);
            return Result.success(token);
        }
        return Result.error("用户名或密码错误");
    }



    @ApiOperation("注册账号")
    @PostMapping("/userRegiser")
    public Result register(@RequestBody TbLogin tbLogin){

        tbLogin.setCreateTime(LocalDateTime.now());
        tbLoginService.register(tbLogin);

        return  Result.success();
    }






}
