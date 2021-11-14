package com.example.demo;

import com.example.demo.entities.Exam;
import com.example.demo.entities.ExamCategory;
import com.example.demo.entities.QuestionType;
import com.example.demo.mapper.ExamCategoryMapper;
import com.example.demo.mapper.ExamMapper;
import com.example.demo.mapper.QuestionTypeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@SpringBootTest
class DemoApplicationTests {

    /**
     * ([一|二|三|四|五|六|七|八|九|十]{1,3})    匹配大题号,如:一
     * ([、.]{1})    匹配、或.
     * ([\u4E00-\u9FA5]*)    匹配多个汉字,如:常识判断
     * ([，,]{1})    匹配、或.
     * (共[0-9]*题)   匹配总题目数量,如:共20题
     * ([。.]{1})    匹配。或.
     * ([(]每题)      匹配 (每题 字符串
     * (([1-9]\d*\.?\d+)|(0\.\d*[1-9])|(\d+))   匹配整数或小数
     * (分[)])   匹配 分) 字符串
     */
//    private static String pattern = "([一|二|三|四|五|六|七|八|九|十]{1,3})([、.]{1})([\\u4E00-\\u9FA5]*)([，,]{1})(共[0-9]*题)([。.]{1})([(]每题)(([1-9]\\d*\\.?\\d+)|(0\\.\\d*[1-9])|(\\d+))(分[)])";
    private static String pattern = "([一二三四五六七八九十]{1,3})([、.]{1})(.+)([（(])(.+)(分[)）])";
    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    @Autowired
    private ExamCategoryMapper examCategoryMapper;

    private static String regex = "[0-9|-|+|.]";


    @Test
    void test2() {
        Pattern p = Pattern.compile(pattern);

        Matcher m = p.matcher("三、数量关系，共10题。(每题0.8分）");
        while (m.find()){
            Double num = getNum(m.group(),0);
            Double num1 = getNum(m.group(),1);
            System.out.println(num);
            System.out.println(num1);
        }
    }

    /**
     * 提取字符串中的关键信息
     *
     * @param str
     * @param index
     * @return
     */
    public static Double getNum(String str, int index) {
        List<Double> list = new ArrayList<>();
        String numRegex = "\\d+(\\.\\d+)?";
        Pattern p = Pattern.compile(numRegex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            list.add(Double.parseDouble(m.group()));
        }
        return list.get(index);
    }

    @Test
    void contextLoads() {
        examMapper.insert(new Exam().setExamTitle("2021公考行测卷").setCategoryId(0).setType(0).setLimitTime(60).setTotalScore(999.000000000));
    }

    @Test
    void contextLoads1() {
        List<Exam> exams = examMapper.selectList(null);
        for (Exam exam : exams) {
            System.out.println(exam);
        }
    }

    @Test
    void contextLoads2() {
        questionTypeMapper.insert(new QuestionType().setTypeName("常识判断").setParentId(0).setTypeLevel(1).setSort(1));
        questionTypeMapper.insert(new QuestionType().setTypeName("言语理解与表达").setParentId(0).setTypeLevel(1).setSort(2));
        questionTypeMapper.insert(new QuestionType().setTypeName("数量关系").setParentId(0).setTypeLevel(1).setSort(3));
        questionTypeMapper.insert(new QuestionType().setTypeName("判断推理").setParentId(0).setTypeLevel(1).setSort(4));
        questionTypeMapper.insert(new QuestionType().setTypeName("资料分析").setParentId(0).setTypeLevel(1).setSort(5));
    }

    @Test
    void contextLoads3() {
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("公务员").setParentId(0).setCategoryLevel(1).setSort(1));
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("事业单位").setParentId(0).setCategoryLevel(1).setSort(2));

//        examCategoryMapper.insert(new ExamCategory().setCategoryName("国考").setParentId(1).setCategoryLevel(2).setSort(1));
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("省考").setParentId(1).setCategoryLevel(2).setSort(2));
//
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("省考").setParentId(2).setCategoryLevel(2).setSort(1));

//        examCategoryMapper.insert(new ExamCategory().setCategoryName("笔试1").setParentId(3).setCategoryLevel(3).setSort(1));
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("面试1").setParentId(3).setCategoryLevel(3).setSort(2));
//
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("笔试2").setParentId(4).setCategoryLevel(3).setSort(1));
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("面试2").setParentId(4).setCategoryLevel(3).setSort(2));
//
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("笔试3").setParentId(5).setCategoryLevel(3).setSort(1));
//        examCategoryMapper.insert(new ExamCategory().setCategoryName("面试3").setParentId(5).setCategoryLevel(3).setSort(2));

        examCategoryMapper.insert(new ExamCategory().setCategoryName("综合").setParentId(10).setCategoryLevel(4).setSort(1));
        examCategoryMapper.insert(new ExamCategory().setCategoryName("写作").setParentId(10).setCategoryLevel(4).setSort(2));


    }


}
