package com.example.demo.entities;

/**
 * @author qiaoh
 */

@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
public enum QuestionTypeEnum {
    COMMON_SENSE_JUDGEMENT(1, "常识判断"),
    UNDERSTANDING_AND_EXPRESSION(2, "言语理解与表达"),
    QUANTITY_RELATIONSHIP(3, "数量关系"),
    JUDGEMENT_AND_REASONING(4, "判断推理"),
    DATA_ANALYSIS(5, "资料分析"),
    TOPIC(6, "【题文】"),
    CHOICE(7, "【选项】"),
    ANSWER(8, "【答案】"),
    ANALYSIS(9, "【解析】"),
    FINISH(10, "【结束】");


    private Integer code;
    private String type;

    QuestionTypeEnum(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}
