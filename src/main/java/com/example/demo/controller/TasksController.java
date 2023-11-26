package com.example.demo.controller;


import com.example.demo.entity.Tasks;
import com.example.demo.pojo.Result;
import com.example.demo.service.impl.TasksServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 待办 前端控制器
 * </p>
 *
 * @author author
 * @since 2023-11-17
 */
@ApiOperation("处理单个待办的接口")
@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    TasksServiceImpl tasksService;


    @ApiOperation("点击输入框的时候，输入关键字跳出所有信息")
    @GetMapping("/autoSearch/{name}/{user_id}")//
    public Result autoSearch(@PathVariable( "name")String namePart,@PathVariable("user_id")Integer userid){

        List<Tasks>tasksList=tasksService.tbSelectByNameLike(namePart,userid);

        return Result.success(tasksList);

    }
    @ApiOperation("根据重要程度排序")
    @GetMapping ("/olderByImportAsc/{user_id}")
    public Result olderByImportAsc(@PathVariable("user_id") Integer userid){

        List<Tasks>tasksList=tasksService.selectByImportance(userid);

        return Result.success(tasksList);

    }

    @GetMapping("/hello")
    public Result hello(){
        System.out.println("hello");
        return Result.success("hello");

    }
    @ApiOperation("前端实体类发过来，可以把备忘录放在垃圾桶里，也可以改变文件夹分类")
    @PutMapping("/modify folder")//
    public Result modifyFolder(@RequestBody Tasks task ){
        tasksService.modifyFolder(task);
        return Result.success();
    }
    @ApiOperation("添加待办")
    @PostMapping("/add")
    public Result addTask(@RequestBody Tasks task){
        tasksService.addTask(task);
        return Result.success();
    }
    @ApiOperation("添加待办顺便加入文件夹")
    @PostMapping("/add/{name}")
    public Result addTask(@RequestBody Tasks task,@PathVariable("name") String name){
        tasksService.addTask(task,name);
        return Result.success();
    }

}
