package com.example.demo.controller;


import com.example.demo.entity.Folder;
import com.example.demo.pojo.Result;
import com.example.demo.service.impl.FolderServiceImpl;
import com.example.demo.service.impl.TasksServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 文件夹 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-11-17
 */
@CrossOrigin
@ApiOperation("操作文件夹")
@RestController
@RequestMapping("/folder")
public class FolderController {

    @Autowired
    FolderServiceImpl folderService;
    @Autowired
    TasksServiceImpl tasksService;

    @ApiOperation("删除该文件夹以及里面所有登西，前端发文件夹名称过来")
    @DeleteMapping("/delete/{name}")
    public Result deleteFolder(@PathVariable("name") String name, ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);

        Integer id=folderService.getFolderIdByName(name,userId);

        folderService.deleteById(id);
        tasksService.deleteByFolderId(id);


        return Result.success();

    }
    @ApiOperation("创建文件夹,前端实体类发过来")
    @PostMapping("/add")
    public Result addFolder(@RequestBody Folder folder,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);
        folder.setUserId(userId);
        folderService.addFolder(folder);

        return Result.success();

    }
    @ApiOperation("查询文件夹，前端文件夹名称发过来")
    @GetMapping("/select/{name}")
    public Result selectFolderByName(@PathVariable("name")String name,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);


        Integer folderId=folderService.getFolderIdByName(name,userId);

       return Result.success( tasksService.getByFolderId(folderId));

    }






}
