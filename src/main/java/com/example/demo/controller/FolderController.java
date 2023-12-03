package com.example.demo.controller;


import com.example.demo.entity.Folder;
import com.example.demo.pojo.Result;
import com.example.demo.service.impl.FolderServiceImpl;
import com.example.demo.service.impl.TasksServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文件夹 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-11-17
 */
@ApiOperation("操作文件夹")
@RestController
@RequestMapping("/folder")
public class FolderController {

    @Autowired
    FolderServiceImpl folderService;
    @Autowired
    TasksServiceImpl tasksService;

    @ApiOperation("删除该文件夹以及里面所有登西，前端发文件夹名称和userid过来")
    @DeleteMapping("/delete/{name}/{user_id}")
    public Result deleteFolder(@PathVariable("name") String name,@PathVariable("user_id") Integer userid){

        Integer id=folderService.getFolderIdByName(name,userid);

        folderService.deleteById(id,userid);
        tasksService.deleteByFolderId(id,userid);


        return Result.success();

    }
    @ApiOperation("创建文件夹,前端实体类发过来")
    @PostMapping("/add")
    public Result addFolder(@RequestBody Folder folder){
        folderService.addFolder(folder);

        return Result.success();

    }
    @ApiOperation("查询文件夹，前端文件夹名称和userid发过来")
    @GetMapping("/select/{name}/{userid}")
    public Result selectFolderByName(@PathVariable("name")String name,@PathVariable("userid")Integer userid){

        Integer folderId=folderService.getFolderIdByName(name,userid);

       return Result.success( tasksService.getByFolderId(folderId));

    }






}
