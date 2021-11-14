package com.example.demo.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entities.*;
import com.example.demo.mapper.ExamCategoryMapper;
import com.example.demo.mapper.QuestionTypeMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author qiaoh
 */
@SuppressWarnings({"AlibabaCommentsMustBeJavadocFormat", "AlibabaRemoveCommentedCode"})
public class WordTest {

//    private static String pattern = "([一|二|三|四|五|六|七|八|九|十]{1,3})([、.]{1})([\\u4E00-\\u9FA5\\s])([，.]{1})共([0-9]*)题。[(]每题(^([0-9]{1,}[.][0-9]*)$)分[)]";


    private static String pattern = "([一二三四五六七八九十]{1,3})([、.]{1})(.+)([（(])(.+)(分[)）])";


    public static void main(String[] args) throws IOException {
        ArrayList<Question> list = new ArrayList<>();

        //读取试卷
        XWPFDocument doc = new XWPFDocument(POIXMLDocument.openPackage("D:\\others\\test04.docx"));
        List<IBodyElement> paragraphs = doc.getBodyElements();

        // 获取试卷标题，如:2021年福建省公务员录用考试《行测》卷
        String title = ((XWPFParagraph) paragraphs.get(0)).getParagraphText();

        for (int i = 1; i < paragraphs.size(); i++) {
            //首先匹配大题(列如:一、常识判断，共20题。（每题0.8分)
            String paragraphText = ((XWPFParagraph) paragraphs.get(i)).getParagraphText();

//            Pattern compile = Pattern.compile(pattern);
//            Matcher matcher = compile.matcher(paragraphText);
//            // 表示匹配到了大题，例如： 一、常识判断，共20题。（每题0.8分）
//            if (matcher.find()) {
//                i = getChoiceQuestionList(i, matcher.group(), paragraphs, list);
//            }
            boolean match = matchPattern(paragraphText);
            // 表示匹配到了大题，例如： 一、常识判断，共20题。（每题0.8分）
            if (match) {
                Double score = getScore(paragraphText, 1);
                String name = getQuestionTypeName(paragraphText);
                System.out.println(name);
                Integer typeId = null;
                if ("常识判断".equals(name)){
                    typeId = 1;
                }
                i = getChoiceQuestionList(i, score, typeId, paragraphs, list);
            }
        }
        for (Question question : list) {
            System.out.println(question);
        }


    }

    public static int getChoiceQuestionList(int i, Double score, Integer typeId, List<IBodyElement> paragraphs, List<Question> list) throws IOException {
        i++;
        for (i = i; i < paragraphs.size(); i++) {

//            //首先匹配大题(列如:一、常识判断，共20题。（每题0.8分)
//            Pattern compile = Pattern.compile(pattern);
//            String contentType = getContent(paragraphs.get(i), new StringBuilder());
//            // 如果匹配到大题则返回
//            if (compile.matcher(contentType).find()) {
//                i--;
//                return i;
//            }
            //首先匹配大题(列如:一、常识判断，共20题。（每题0.8分)
            String contentType = getContent(paragraphs.get(i), new StringBuilder());
            // 如果匹配到大题则返回
            boolean match = matchPattern(contentType);
            if (match) {
                i--;
                return i;
            }

            //获取题目中的【题文】信息
            String questionTopic = getContent(paragraphs.get(i), new StringBuilder());
            if (questionTopic.contains(QuestionTypeEnum.TOPIC.getType())) {
                i++;
                //截取掉【题文】标识符
                questionTopic = questionTopic.replaceAll(QuestionTypeEnum.TOPIC.getType(), "");
                for (i = i; i < paragraphs.size(); i++) {
                    String content = getContent(paragraphs.get(i), new StringBuilder());
                    //如果读取到【选项】标识符表示【题文】部分结束,跳出循环
                    if (content.contains(QuestionTypeEnum.CHOICE.getType())) {
                        break;
                    }
                    //否则继续拼接【题文】内容
                    questionTopic += content;
                }
            }

            //获取题目中的【选项】信息
            String choice = getContent(paragraphs.get(i), new StringBuilder());
            if (choice.contains(QuestionTypeEnum.CHOICE.getType())) {
                i++;
                //截取掉【选项】标识符
                choice = choice.replaceAll(QuestionTypeEnum.CHOICE.getType(), "");
                //如果标签不是以【答案】结尾，拼接多行【选项】信息
                while (!((XWPFParagraph) paragraphs.get(i)).getParagraphText().contains(QuestionTypeEnum.ANSWER.getType())) {
                    String content = getContent(paragraphs.get(i), new StringBuilder());
                    choice += content;
                    i++;
                }
            }

            //获取题目的【答案】信息
            String answer = getContent(paragraphs.get(i), new StringBuilder());
            if (answer.contains(QuestionTypeEnum.ANSWER.getType())) {
                i++;
                //截取掉【答案】标识符
                answer = answer.replaceAll(QuestionTypeEnum.ANSWER.getType(), "");
                //如果标签不是以【解析】结尾，拼接多行【答案】信息
                while (!((XWPFParagraph) paragraphs.get(i)).getParagraphText().contains(QuestionTypeEnum.ANALYSIS.getType())) {
                    String content = getContent(paragraphs.get(i), new StringBuilder());
                    answer += content;
                    i++;
                }
            }

            //获取题目的【解析】信息
            String analysis = getContent(paragraphs.get(i), new StringBuilder());
            if (analysis.contains(QuestionTypeEnum.ANALYSIS.getType())) {
                i++;
                //截取掉【解析】标识符
                analysis = analysis.replaceAll(QuestionTypeEnum.ANALYSIS.getType(), "");
                //如果标签不是以【结束】结尾，拼接多行【解析】信息
                while (!((XWPFParagraph) paragraphs.get(i)).getParagraphText().contains(QuestionTypeEnum.FINISH.getType())) {
                    String content = getContent(paragraphs.get(i), new StringBuilder());
                    analysis += content;
                    i++;
                }
            }

            if (!StringUtils.isBlank(questionTopic)
                    && !StringUtils.isBlank(analysis)
                    && !StringUtils.isBlank(answer)
                    && !StringUtils.isBlank(choice)
                    && score != null
                    && typeId != null) {
                Question q = new Question()
                        .setQuestionTopic(questionTopic)
                        .setChoice(choice)
                        .setAnswer(answer)
                        .setSort(list.size() + 1)
                        .setAnalysis(analysis)
                        .setScore(score)
                        .setTypeId(typeId);
                list.add(q);
//                q.setQuestionType(questionType);
//                q.setQuestion(question);
//                q.setAnalysis(analysis);
//                q.setRightAnswer(answer);
//                q.setOption(divideOption(option));
//                list.add(q);

            }
        }
        return i;
    }


    public static String getContent(IBodyElement body, StringBuilder content) {
        //将word中的图片存储到本地磁盘中
        ImageParse imageParse = new ImageParse("D:/others/test/", "D:/others/test/");
        //拿到所有的段落的表格，这两个属于同级无素
        if (body.getElementType().equals(BodyElementType.PARAGRAPH)) {
            //处理段落中的文本以及公式图片
            handleParagraph(content, body, imageParse);
        }
        return content.toString();
    }

    public static void handleParagraph(StringBuilder content, IBodyElement body, ImageParse imageParser) {
        XWPFParagraph p = (XWPFParagraph) body;
        if (p.isEmpty() || p.isWordWrap() || p.isPageBreak()) {
            return;
        }
//        String tagName = "p";
//        content.append("<" + tagName + ">");
      /*
        XWPFParagraph 有两个方法可以分别提出XWPFRun和CTOMath，但是不知道位置
        ParagraphChildOrderManager这个类是专门解决这个问题的
      */
        ParagraphChildOrderManager runOrMaths = new ParagraphChildOrderManager(p);
        List<Object> childList = runOrMaths.getChildList();

        for (Object child : childList) {
            if (child instanceof XWPFRun) {
                //处理段落中的文本以及图片

                handleParagraphRun(content, (XWPFRun) child, imageParser);
            }
        }
//        content.append("</" + tagName + ">");
    }

    /**
     * 提取字符串中的数字信息
     *
     * @param str
     * @param index
     * @return
     */
    public static Double getScore(String str, int index) {
        List<Double> list = new ArrayList<>();
        String numRegex = "\\d+(\\.\\d+)?";
        Pattern p = Pattern.compile(numRegex);
        Matcher m = p.matcher(str);
        while (m.find()) {
            list.add(Double.parseDouble(m.group()));
        }
        return list.get(index);
    }

    /**
     * 提取字符串中的类型信息
     *
     * @param str
     * @return
     */
    public static String getQuestionTypeName(String str) {
        String typeRegex = "常识判断|言语理解与表达|数量关系|判断推理|资料分析";
        Pattern p = Pattern.compile(typeRegex);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return m.group();
        }
        return null;
    }

    /**
     * 判断大题信息是否符合格式
     *
     * @param str
     * @return
     */
    public static boolean matchPattern(String str) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    private static void handleParagraphRun(StringBuilder content, XWPFRun run, ImageParse imageParser) {
        // 有内嵌的图片
        List<XWPFPicture> pics = run.getEmbeddedPictures();
        if (pics != null && pics.size() > 0) {

            handleParagraphRunsImage(content, pics, imageParser);
        } else {
            //纯文本直接获取
            content.append(run.toString());
        }
    }


    private static void handleParagraphRunsImage(StringBuilder content, List<XWPFPicture> pics, ImageParse imageParser) {

        for (XWPFPicture pic : pics) {
            //这里已经获取好了
            String path = imageParser.parse(pic.getPictureData().getData(),
                    pic.getPictureData().getFileName());

            content.append(String.format("<img src=\"%s\" />", path));
//            CTPicture ctPicture = pic.getCTPicture();
//            Node domNode = ctPicture.getDomNode();
//
//            Node extNode = W3cNodeUtil.getChildChainNode(domNode, "pic:spPr", "a:ext");
//            NamedNodeMap attributes = extNode.getAttributes();
//            if (attributes != null && attributes.getNamedItem("cx") != null) {
//                int width = WordMyUnits.emuToPx(new Double(attributes.getNamedItem("cx").getNodeValue()));
//                int height = WordMyUnits.emuToPx(new Double(attributes.getNamedItem("cy").getNodeValue()));
//                content.append(String.format("<img src=\"%s\" width=\"%d\" height=\"%d\" />", path, width, height));
//            } else {
//                content.append(String.format("<img src=\"%s\" />", path));
//            }
        }
    }


    /**
     * 用\n分隔答案信息
     *
     * @param option
     * @return
     */
    public static String divideOption(String option) {
        String optionA = option.substring(option.indexOf("A、"), option.indexOf("B、"));
        String optionB = option.substring(option.indexOf("B、"), option.indexOf("C、"));
        String optionC = option.substring(option.indexOf("C、"), option.indexOf("D、"));
        String optionD = option.substring(option.indexOf("D、"));
        return optionA + "\n" + optionB + "\n" + optionC + "\n" + optionD;
    }

}
