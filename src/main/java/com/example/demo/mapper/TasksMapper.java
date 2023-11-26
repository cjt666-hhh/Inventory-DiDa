package com.example.demo.mapper;

import com.example.demo.entity.Tasks;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 待办 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-11-17
 */
@Mapper
public interface TasksMapper extends BaseMapper<Tasks> {

}
