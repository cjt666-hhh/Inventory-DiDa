package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.Folder;
import com.example.demo.entity.Tasks;
import com.example.demo.mapper.FolderMapper;
import com.example.demo.service.IFolderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件夹 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-11-17
 */
@Service
public class FolderServiceImpl extends ServiceImpl<FolderMapper, Folder> implements IFolderService {

    @Autowired
    FolderMapper folderMapper;

    public void deleteById(Integer id,Integer userid){

        QueryWrapper<Folder>queryWrapper=new QueryWrapper<Folder>().eq("id",id)
                .eq("user_id",userid);
       folderMapper.delete(queryWrapper);



    }
    public void addFolder(Folder folder){
        folderMapper.insert(folder);
    }

    public Folder getByFolderNameAndUserid(int userid,String folderName){

        QueryWrapper<Folder>queryWrapper=new QueryWrapper<Folder>()
                .eq("user_id",userid).eq("name",folderName);
        return folderMapper.selectOne(queryWrapper);

    }

    public Boolean isFolderExit(Tasks task,String folderName){
        int userid=task.getUserId();
        String name=folderName;

        Folder folder=getByFolderNameAndUserid(userid,name);
        if (folder!=null){
            return true;
        }
        else return false;





    }
    public long getCountOfFolders(){
        QueryWrapper<Folder>queryWrapper=new QueryWrapper<Folder>().ge("id",0);

       return folderMapper.selectCount(queryWrapper);
    }
    public Integer getFolderIdByName(String name,Integer userid){

        QueryWrapper<Folder>queryWrapper=new QueryWrapper<Folder>()
                .eq("name",name).eq("user_id",userid);

        return folderMapper.selectOne(queryWrapper).getId();
    }



}
