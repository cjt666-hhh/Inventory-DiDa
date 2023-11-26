package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 待办
 * </p>
 *
 * @author author
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tasks")
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务内容
     */
    private String name;//前端，待办内容（吃炸鸡）

    private Integer folderId;

    private Integer isDelete;//0

    private Integer isFinished;//0

    private String signName;//前端（文件夹名字）（吃饭）

    private LocalDateTime createTime;//前端

    /**
     * 你到底属于谁
     */
    private Integer userId;//最重要，前端

    private Integer importance;//4，3，2，1（网页移动）优先级


}
