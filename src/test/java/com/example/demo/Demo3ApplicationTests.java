package com.example.demo;

import com.example.demo.entity.Folder;
import com.example.demo.entity.Tasks;
import com.example.demo.entity.TbLogin;

import com.example.demo.mapper.TasksMapper;
import com.example.demo.service.impl.FolderServiceImpl;
import com.example.demo.service.impl.TasksServiceImpl;
import com.example.demo.service.impl.TbLoginServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class Demo3ApplicationTests {


    @Test
    void contextLoads() {



    }

    //    @Test
//    void testSelectById() {
//        Dept dept = deptMapper.selectById(5L);
//        System.out.println("user = " + dept);
//    }
//@Test
//void testUpdateByQueryWrapper() {
//    // 1.构建查询条件 where name = "Jack"
//    QueryWrapper<Dept> wrapper = new QueryWrapper<Dept>().eq("name", "教研部");
//    // 2.更新数据，user中非null字段都会作为set语句
//    Dept dept = new Dept();
//    dept.setUpdateTime(LocalDateTime.now());
//    deptMapper.update(dept, wrapper);
//}
//@Test
//void testSelectByQueryWrapper(){
//        QueryWrapper<Dept>wrapper=new QueryWrapper<Dept>();
//                wrapper.lambda().select(Dept::getId,Dept::getCreateTime)
//                        .like(Dept::getName,"h");
//       Dept  dept=deptMapper.selectOne(wrapper);
//    System.out.println(dept.toString());
//
//
//}
    @Autowired
    TasksServiceImpl tasksService;
    @Autowired
    TbLoginServiceImpl tbLoginService;

    @Autowired
    FolderServiceImpl folderService;
    @Test
    void testLogin() {
        System.out.println(tbLoginService.getByPasswordAndUserName("cjt666", "123456").toString());


    }

    @Test
    public void genJwt() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "Tom");

        String jwt = Jwts.builder()
                .setClaims(claims) //自定义内容(载荷)
                .signWith(SignatureAlgorithm.HS256, "itheima") //签名算法
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 3600 * 1000)) //有效期
                .compact();

        System.out.println(jwt);
    }

    @Test
    public void testDelete(Integer folderId,Integer userId){



    }

    @Test
    public void testRegister(){

        TbLogin tbLogin=new TbLogin();
        tbLogin.setName("糖糖糖");
        tbLogin.setPassword("12345567");
        tbLogin.setUsername("ccccc");
        tbLogin.setGender(1);

        tbLoginService.register(tbLogin);

    }

    @Test
    public void testAddFolder(){

        Folder folder=new Folder();

        folder.setName("学校");
        folder.setUserId(2);
        folderService.addFolder(folder);


    }
    @Test
    public void testSelect(){
        List<Tasks>tasksList=tasksService.selectByImportance(1);

        for(Tasks tasks:tasksList){

            System.out.println(tasks.toString());
        }


    }
    @Test
    public void testSearch(){
        List<Tasks>tasksList=tasksService.tbSelectByNameLike("j",2);


        for (Tasks tasks:tasksList){
            System.out.println(tasks.toString());
        }

    }

    @Test
    public void testModify(){
        Tasks tasks=new Tasks();
        tasks.setId(1);
        tasks.setFolderId(3);
        tasks.setIsDelete(0);


        tasksService.modifyFolder(tasks);

    }
    @Test
    public void test(){


        Tasks tasks=new Tasks();
        tasks.setName("jjj");
        tasks.setFolderId(2);
        tasks.setCreateTime(LocalDateTime.now());
        tasks.setUserId(2);
        tasks.setImportance(4);
        tasksService.addTask(tasks,"学校");



    }


}