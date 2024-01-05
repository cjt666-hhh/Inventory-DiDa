package com.example.demo.controller;


import com.example.demo.entity.Tasks;
import com.example.demo.mapper.TasksMapper;
import com.example.demo.pojo.Result;
import com.example.demo.service.impl.FolderServiceImpl;
import com.example.demo.service.impl.TasksServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
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
@CrossOrigin
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

    @ApiOperation("点击输入框的时候，输入关键字跳出所有信息,前端把用户输入的关键词给我")
    @GetMapping("/autoSearch/{name}")//
    public Result autoSearch(@PathVariable( "name")String namePart,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);


        List<Tasks>tasksList=tasksService.tbSelectByNameLike(namePart,userId);

        return Result.success(tasksList);

    }
    @ApiOperation("把所有待办根据重要程度排序")
    @GetMapping ("/olderByImportAsc")
    public Result olderByImportAsc(ServletRequest servletRequest){

        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);


        List<Tasks>tasksList=tasksService.selectByImportance(userId);

        return Result.success(tasksList);

    }

    @GetMapping("/hello")
    public Result hello(){
        System.out.println("hello");
        return Result.success("hello");

    }
    @ApiOperation("前端实体类发过来，可以把备忘录放在垃圾桶里")
    @PutMapping("/deleteTask")//
    public Result deleteTask(@RequestBody Tasks task ,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);
        task.setIsDelete(1);
        task.setUserId(userId);


        tasksMapper.updateById(task);

        return Result.success();
    }
    @ApiOperation("前端实体类发过来，完成待办")
    @PutMapping("/finishTask")//
    public Result finishTask(@RequestBody Tasks task,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);
        task.setUserId(userId);
        task.setFinishTime(LocalDateTime.now());

        tasksMapper.updateById(task);

        return Result.success();
    }
    @ApiOperation("前端把和日期发过来，我给前端按时完成的待办")
    @GetMapping("/getOnTime/{day}")
    public Result getOnTime(@PathVariable("day")String day,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);
        return Result.success(tasksService.getOnTime(day,userId));
    }
    @ApiOperation("前端把日期发过来，我给前端当天按时完成的待办的数量")
    @GetMapping("/getCountOnTime/{day}")
    public Result getCountOnTime(@PathVariable("day")String day,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LocalDate date=LocalDate.parse(day);
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);
        return Result.success(tasksService.getCountOnTime(date,userId));
    }
    @ApiOperation("前端把日期发过来，我给前端近7天按时完成的待办的数量")//echart
    @GetMapping("/getSevenDays/{day}")
    public Result getSevenDays(@PathVariable("day")String day,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LocalDate date=LocalDate.parse(day);
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);

        return Result.success(tasksService.getTurnover(date.minusDays(6),date,userId));

    }

    @ApiOperation("前端把待办实体类发给我，添加待办")
    @PostMapping("/add")
    public Result addTask(@RequestBody Tasks task,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);
        task.setUserId(userId);
        tasksService.addTask(task);
        return Result.success();
    }
    @ApiOperation("前端把目标文件夹名和实体类发给我，添加待办顺便加入文件夹")
    @PostMapping("/add/{name}")
    public Result addTask(@RequestBody Tasks task,@PathVariable("name") String name,ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);

        task.setUserId(userId);
        tasksService.addTask(task,name);
        return Result.success();
    }
    @ApiOperation("日历功能的基本思路，前端把当天日期发给我，我返还数据")
    @GetMapping("/getByDay/{day}")
    public Result getByDay(ServletRequest servletRequest, @PathVariable("day")String day) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);


        LocalDate date=LocalDate.parse(day);
        List<Tasks>tasksList=tasksService.getByDate(date,userId);
        return  Result.success(tasksList);
    }
    @ApiOperation("统计功能的基本思路，前端把当天日期发给我，我返还当日待办总量")
    @GetMapping("/countByDay/{day}")
    public Result getCountByDate(ServletRequest servletRequest,@PathVariable("day")String day) {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);

        return  Result.success(tasksService.getCountByDate(day,userId));
    }
//    @ApiOperation("前端把userid和日期发过来，我返还当日完成率")
//    @GetMapping("/finishRate/{day}")
//    public Result getFinishRateByDay(ServletRequest servletRequest,@PathVariable("day")String day){
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
////
//        String token = request.getHeader("token");
//
//        // 解析JWT令牌
//        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();
//
//        // 从JWT令牌中提取用户ID
//        Integer userId = claims.get("id", Integer.class);
//        LocalDate.parse(day)
//
//        double finishRate=tasksService.getCountOnTime(day,userId)/tasksService.getCountByDate(day,userId);
//        return Result.success(finishRate);
//    }
    @ApiOperation("前端把目标分类的文件夹名和实体类发给我，我修改待办的分类")
    @PutMapping("/modifyFolder/{name}")
    public Result modifyFolder(ServletRequest servletRequest,@RequestBody Tasks task,@PathVariable("name")String name){

        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);


        Integer folderId= folderService.getFolderIdByName("学校",userId);

        task.setFolderId(folderId);
        tasksMapper.updateById(task);
        return  Result.success();
    }
    @ApiOperation("我返还已删除，前端把它们丢垃圾桶")
    @GetMapping("/getDeleted")
    public Result getDeleted(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);

        return Result.success(tasksService.getDeleted(userId));
    }
    @ApiOperation("我返还未删除，就是所有完成与未完成的待办")
    @GetMapping("/getNotDeleted")
    public Result getNotDeleted(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);

        return Result.success(tasksService.getNotDeleted(userId));
    }
    @ApiOperation("我返还未完成的待办")
    @GetMapping("/getNotFinished")
    public Result getNotFinished(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);

        return Result.success(tasksService.getNotFinished(userId));
    }
    @ApiOperation("我返还已完成的待办")
    @GetMapping("/getFinished")
    public Result getFinished(ServletRequest servletRequest){
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//
        String token = request.getHeader("token");

        // 解析JWT令牌
        Claims claims = Jwts.parser().setSigningKey("cjt666").parseClaimsJws(token).getBody();

        // 从JWT令牌中提取用户ID
        Integer userId = claims.get("id", Integer.class);


        return Result.success(tasksService.getFinished(userId));
    }




}
