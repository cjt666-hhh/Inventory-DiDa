package com.example.demo.controller;


import com.example.demo.entity.Tasks;
import com.example.demo.mapper.TasksMapper;
import com.example.demo.pojo.Result;
import com.example.demo.service.impl.FolderServiceImpl;
import com.example.demo.service.impl.TasksServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Autowired
    TasksMapper tasksMapper;
    @Autowired
    FolderServiceImpl folderService;

    @ApiOperation("点击输入框的时候，输入关键字跳出所有信息,前端把userid和用户输入的关键词给我")
    @GetMapping("/autoSearch/{name}/{user_id}")//
    public Result autoSearch(@PathVariable( "name")String namePart,@PathVariable("user_id")Integer userid){

        List<Tasks>tasksList=tasksService.tbSelectByNameLike(namePart,userid);

        return Result.success(tasksList);

    }
    @ApiOperation("把所有待办根据重要程度排序，前端发用户id给我")
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
    @ApiOperation("前端实体类发过来，可以把备忘录放在垃圾桶里")
    @PutMapping("/deleteTask")//
    public Result deleteTask(@RequestBody Tasks task ){
        task.setIsDelete(1);
        tasksMapper.updateById(task);

        return Result.success();
    }
    @ApiOperation("前端实体类发过来，完成待办")
    @PutMapping("/finishTask")//
    public Result finishTask(@RequestBody Tasks task){
        task.setFinishTime(LocalDateTime.now());

        tasksMapper.updateById(task);

        return Result.success();
    }
    @ApiOperation("前端把userid和日期发过来，我给前端按时完成的待办")
    @GetMapping("/getOnTime/{day}/userid")
    public Result getOnTime(@PathVariable("day")String day,@PathVariable("userid")Integer userid){
        return Result.success(tasksService.getOnTime(day,userid));
    }
    @ApiOperation("前端把userid和日期发过来，我给前端当天按时完成的待办的数量")
    @GetMapping("/getCountOnTime/{day}/userid")
    public Result getCountOnTime(@PathVariable("day")String day,@PathVariable("userid")Integer userid){
        return Result.success(tasksService.getCountOnTime(day,userid));
    }

    @ApiOperation("前端把待办实体类发给我，添加待办")
    @PostMapping("/add")
    public Result addTask(@RequestBody Tasks task){
        tasksService.addTask(task);
        return Result.success();
    }
    @ApiOperation("前端把目标文件夹名和实体类发给我，添加待办顺便加入文件夹")
    @PostMapping("/add/{name}")
    public Result addTask(@RequestBody Tasks task,@PathVariable("name") String name){
        tasksService.addTask(task,name);
        return Result.success();
    }
    @ApiOperation("日历功能的基本思路，前端把userid和当天日期发给我，我返还数据")
    @GetMapping("/getByDay/{userid}/{day}")
    public Result getByDay(@PathVariable("userid")Integer userid, @PathVariable("day")String day) {


        LocalDate date=LocalDate.parse(day);
        List<Tasks>tasksList=tasksService.getByDate(date,userid);
        return  Result.success(tasksList);
    }
    @ApiOperation("日历功能的基本思路，前端把userid和当天日期发给我，我返还当日待办总量")
    @GetMapping("/countByDay/{userid}/{day}")
    public Result getCountByDate(@PathVariable("userid")Integer userid, @PathVariable("day")String day) {




        return  Result.success(tasksService.getCountByDate(day,userid));
    }
    @ApiOperation("前端把userid和日期发过来，我返还当日完成率")
    @GetMapping("/finishRate/{userid}/{day}")
    public Result getFinishRateByDay(@PathVariable("userid")Integer userid,@PathVariable("day")String day){



        double finishRate=tasksService.getCountOnTime(day,userid)/tasksService.getCountByDate(day,userid);
        return Result.success(finishRate);
    }
    @ApiOperation("前端把目标分类的文件夹名和实体类发给我，我修改待办的分类")
    @PutMapping("/modifyFolder/{name}")
    public Result modifyFolder(@RequestBody Tasks task,@PathVariable("name")String name){



        Integer folderId= folderService.getFolderIdByName("学校",task.getUserId());

        task.setFolderId(folderId);
        tasksMapper.updateById(task);
        return  Result.success();
    }




}
