package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Folder;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.TbLogin;
import com.example.demo.mapper.FolderMapper;
import com.example.demo.mapper.TasksMapper;
import com.example.demo.service.ITasksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
                .orderByDesc("create_time").eq("user_id",userid);


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
    public void deleteByFolderId(Integer folderId,Integer userId){
        QueryWrapper queryWrapper=new QueryWrapper<Tasks>()
                .eq("folder_id",folderId)
                .eq("user_id",userId);
        tasksMapper.delete(queryWrapper);


    }



    public void modifyFolder(Tasks tasks){
        tasksMapper.updateById(tasks);
    } //可以把特定备忘录扔垃圾桶里，

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


        if (folderService.isFolderExit(task,folderName)){

            task.setFolderId(folderService.getFolderIdByName(folderName));
            tasksMapper.insert(task);

        }else{
            int i=(int)folderService.getCountOfFolders();
            task.setFolderId(i+1);
            tasksMapper.insert(task);
            Folder folder=new Folder();
            folder.setName(folderName);
            folder.setUserId(task.getUserId());
            folderMapper.insert(folder);
        }

    }

}