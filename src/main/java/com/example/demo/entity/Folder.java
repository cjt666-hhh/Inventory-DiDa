package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件夹
 * </p>
 *
 * @author author
 * @since 2023-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("folder")
@ApiModel(value="文件夹操作", description="文件夹")
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件夹名
     */
    @ApiModelProperty(value = "文件夹名")
    private String name;

    /**
     * 你到底属于谁
     */
    @ApiModelProperty(value = "所属用户ID")
    private Integer userId;


}
