package com.example.demo.entities;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@ToString
public class Exam {
    /**
     * 试卷id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 试卷名称
     */
    private String examTitle;

    /**
     * 试卷分类id
     */
    private Integer categoryId;

    /**
     * 试卷类型(行测0，申论1)
     */
    private Integer type;

    /**
     * 考试时限(单位:minute)
     */
    private Integer limitTime;

    /**
     * 总分
     */
    private Double totalScore;



    /**
     * 逻辑删除位【0删除，1存在】
     */
    private Integer showStatus;

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
