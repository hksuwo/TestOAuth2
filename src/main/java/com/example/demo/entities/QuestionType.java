package com.example.demo.entities;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class QuestionType {

    /**
     * 题目类型id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 类型名
     */
    private String typeName;

    /**
     * 父结点id
     */
    private Integer parentId;

    /**
     * 结点等级
     */
    private Integer typeLevel;

    /**
     * 逻辑删除位【0删除，1存在】
     */
    private Integer showStatus;

    /**
     * 结点位置
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
