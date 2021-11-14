package com.example.demo.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author qiaoh
 */
@Data
@Accessors(chain = true)
public class Question {
    /**
     * 试题id
     */
    private Integer id;

    /**
     * 试题标题
     */
    private String questionTopic;

    /**
     * 试题选项
     */
    private String choice;

    /**
     * 答案
     */
    private String answer;

    /**
     * 试题类型id
     */
    private Integer typeId;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 逻辑删除位【0删除，1存在】
     */
    private Integer showStatus;

    /**
     * 解析
     */
    private String analysis;

    /**
     * 分数
     */
    private Double score;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 试卷id
     */
    private Integer examId;
}
