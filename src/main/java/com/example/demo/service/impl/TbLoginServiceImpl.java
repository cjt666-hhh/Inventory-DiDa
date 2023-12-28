package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Folder;
import com.example.demo.entity.TbLogin;
import com.example.demo.mapper.TbLoginMapper;
import com.example.demo.service.ITbLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户注册 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-11-16
 */
@Service
public class TbLoginServiceImpl extends ServiceImpl<TbLoginMapper, TbLogin> implements ITbLoginService {
@Autowired
TbLoginMapper tbLoginMapper;

    public TbLogin getByPasswordAndUserName(@Param("username") String username, @Param("password") String password){

        QueryWrapper<TbLogin> wrapper = new QueryWrapper<TbLogin>()
                .select("id","username","name","password","create_time","gender","image")
                .eq("username", username).eq("password",password);

        return tbLoginMapper.selectOne(wrapper);


    }






    public void register(TbLogin tbLogin){

        tbLoginMapper.insert(tbLogin);

    }






}


