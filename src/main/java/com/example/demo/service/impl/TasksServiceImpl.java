package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Folder;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.TbLogin;
import com.example.demo.mapper.FolderMapper;
import com.example.demo.mapper.TasksMapper;
import com.example.demo.pojo.TurnoverReportVO;
import com.example.demo.service.ITasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 待办 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-11-17
 */


@Service
public class TasksServiceImpl extends ServiceImpl<TasksMapper, Tasks> implements ITasksService {
    @Autowired
    TasksMapper tasksMapper;
    @Autowired
    FolderServiceImpl folderService;
    @Autowired
    FolderMapper folderMapper;
//看到所有待办


    public List<Tasks> selectByTime(Integer userid) {

        QueryWrapper<Tasks> queryWrapper = new QueryWrapper<Tasks>()
                .eq("user_id", userid)
                .orderByAsc("create_time").eq("is_delete", 0);

        List<Tasks> tasksList = tasksMapper.selectList(queryWrapper);

        return tasksList;

    }

    public List<Tasks> tbSelectByNameLike(String namePart ,Integer userid) {


        QueryWrapper<Tasks> wrapper = new QueryWrapper<Tasks>()
                .like("name", namePart)
                .orderByDesc("create_time").eq("user_id",userid)
                .eq("is_delete",0).eq("is_finished",0);


        return tasksMapper.selectList(wrapper);

    }
    public List<Tasks>selectByImportance(Integer userid){
        QueryWrapper<Tasks> queryWrapper = new QueryWrapper<Tasks>()
                .eq("user_id", userid)
                .orderByAsc("create_time").eq("is_delete", 0)
                .orderByAsc("importance");

        List<Tasks> tasksList = tasksMapper.selectList(queryWrapper);

        return tasksList;

    }
    public void deleteByFolderId(Integer folderId){
        QueryWrapper queryWrapper=new QueryWrapper<Tasks>()
                .eq("folder_id",folderId);

        tasksMapper.delete(queryWrapper);


    }





    public List<Tasks> groupBySignName(Integer userId ,String signName) {
        QueryWrapper queryWrapper = new QueryWrapper<Tasks>().eq("user_id", userId)
                .eq("sign_name", signName);
        List<Tasks> tasksList = tasksMapper.selectList(queryWrapper);

        return tasksList;


    }
    public void addTask(Tasks task){


        task.setCreateTime(LocalDateTime.now());
        tasksMapper.insert(task);


    }




    public void addTask(Tasks task,String folderName){
        task.setCreateTime(LocalDateTime.now());

        Integer userid=task.getUserId();


        if (folderService.getByFolderNameAndUserid(userid,folderName)!=null){

            task.setFolderId(folderService.getFolderIdByName(folderName,userid));
            tasksMapper.insert(task);

        }else{



            task.setFolderId(folderService.getFolderIdMax().getId()+1);
            tasksMapper.insert(task);
            Folder folder=new Folder();
            folder.setName(folderName);
            folder.setUserId(task.getUserId());
            folderMapper.insert(folder);

        }

    }


    public List<Tasks> getByFolderId(Integer folderId){

        QueryWrapper<Tasks>queryWrapper=new QueryWrapper<Tasks>().eq("folder_id",folderId)
                .orderByAsc("create_time").eq("is_delete",0);

        return tasksMapper.selectList(queryWrapper);

    }
    public  List<Tasks>getByDate(LocalDate date, Integer userid){
        LocalDateTime starOfDay=date.atStartOfDay();
        LocalDateTime endOfDay=date.atTime(LocalTime.MAX);

        QueryWrapper<Tasks>queryWrapper=new QueryWrapper<Tasks>().between("create_time",starOfDay,endOfDay)
                .eq("user_id",userid).eq("is_delete",0);
        List<Tasks>tasksList=tasksMapper.selectList(queryWrapper);
        return tasksList;

    }
    public long getCountByDate(String day, Integer userid){
        LocalDate date=LocalDate.parse(day);
        LocalDateTime starOfDay=date.atStartOfDay();
        LocalDateTime endOfDay=date.atTime(LocalTime.MAX);

        QueryWrapper<Tasks>queryWrapper=new QueryWrapper<Tasks>().between("create_time",starOfDay,endOfDay)
                .eq("user_id",userid).eq("is_delete",0);

        return tasksMapper.selectCount(queryWrapper) ;}
    public List<Tasks>getOnTime(String day,Integer userid){
        LocalDate date=LocalDate.parse(day);
        LocalDateTime atStart=date.atStartOfDay();
        LocalDateTime deadLine=date.atTime(LocalTime.MAX);

        QueryWrapper<Tasks>queryWrapper=new QueryWrapper<Tasks>().eq("user_id",userid)
                .between("finish_time",atStart,deadLine).orderByAsc("finish_time")
                .eq("is_delete",0);

        List<Tasks>tasksList=tasksMapper.selectList(queryWrapper);

        return tasksList;
    }
    public double getCountOnTime(LocalDate date,Integer userid){

        LocalDateTime atStart=date.atStartOfDay();
        LocalDateTime deadLine=date.atTime(LocalTime.MAX);

        QueryWrapper<Tasks>queryWrapper=new QueryWrapper<Tasks>().eq("user_id",userid)
                .between("finish_time",atStart,deadLine).orderByAsc("finish_time").eq("is_delete",0);

       return tasksMapper.selectCount(queryWrapper);

    }

    public List<Tasks> getNotFinished(Integer userId){

        QueryWrapper<Tasks>queryWrapper=new QueryWrapper<Tasks>().eq("user_id",userId)
                .eq("is_finished",0).eq("is_delete",0);

        return  tasksMapper.selectList(queryWrapper);

    }
    public List<Tasks> getFinished(Integer userId){

        QueryWrapper<Tasks>queryWrapper=new QueryWrapper<Tasks>().eq("user_id",userId)
                .eq("is_finished",1).eq("is_delete",0);

        return  tasksMapper.selectList(queryWrapper);

    }
    public List<Tasks> getDeleted(Integer userId){

        QueryWrapper<Tasks>queryWrapper=new QueryWrapper<Tasks>().eq("user_id",userId)
               .eq("is_delete",1);

        return  tasksMapper.selectList(queryWrapper);

    }
    public List<Tasks> getNotDeleted(Integer userId) {

        QueryWrapper<Tasks> queryWrapper = new QueryWrapper<Tasks>().eq("user_id", userId)
                .eq("is_delete", 0);

        return tasksMapper.selectList(queryWrapper);


    }
    public TurnoverReportVO getTurnover(LocalDate begin, LocalDate end,Integer userId) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        while (!begin.equals(end)){
            begin = begin.plusDays(1);//日期计算，获得指定日期后1天的日期
            dateList.add(begin);
        }

        List<Double> turnoverList = new ArrayList<>();
        for (LocalDate date : dateList) {
            Double turnover = getCountOnTime(date,userId);
            turnover = turnover == null ? 0.0 : turnover;
            turnoverList.add(turnover);
        }
        List<String> turnoverStrList = turnoverList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        List<String> dateStrList = dateList.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());

// 数据封装
        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateStrList, ','))
                .turnoverList(StringUtils.join(turnoverStrList, ','))
                .build();

        //数据封装

    }
}


