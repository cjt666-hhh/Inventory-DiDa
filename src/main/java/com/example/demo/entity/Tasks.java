package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="待办操作", description="任务")
public class Tasks implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "主键Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务内容
     */
    @ApiModelProperty(value = "待办内容")
    private String name;//前端，待办内容（吃炸鸡）

    @ApiModelProperty(value = "待办id")
    private Integer folderId;

    @ApiModelProperty(value = "所属用户")
    private Integer isDelete;//0

    @ApiModelProperty(value = "是否完成")
    private Integer isFinished;//0

    @ApiModelProperty(value = "文件夹名")
    private String signName;//前端（文件夹名字）（吃饭）

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;//前端

    /**
     * 你到底属于谁
     */
    @ApiModelProperty(value = "所属用户ID")
    private Integer userId;//最重要，前端

    @ApiModelProperty(value = "重要程度")
    private Integer importance;//4，3，2，1（网页移动）优先级


    @ApiModelProperty(value = "是否按时完成")
    @TableField(value = "is_finish_on_time")
    private int isFinishOnTime;

    @ApiModelProperty(value = "完成时间")
    @TableField("finish_time")
    private LocalDateTime finishTime;







}
