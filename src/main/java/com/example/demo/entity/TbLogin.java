package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户注册
 * </p>
 *
 * @author author
 * @since 2023-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_login")
@ApiModel(value="用户登陆注册", description="用户登录注册")
public class TbLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "用户主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户(相当于账号)）")
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "用户密码")
    private String password;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "用户名称")
    private String name;

    /**
     * 性别, 说明: 1 男, 2 女
     */
    @ApiModelProperty(value = "性别")
    private Integer gender;

    /**
     * 图像
     */
    @ApiModelProperty(value = "头像")
    private String image;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
