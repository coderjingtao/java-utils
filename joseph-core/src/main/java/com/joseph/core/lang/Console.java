package com.joseph.core.lang;

import com.joseph.core.date.DateTime;
import com.joseph.core.util.ArrayUtil;
import com.joseph.core.util.CharUtil;
import com.joseph.core.util.StrUtil;

import static java.lang.System.out;

/**
 * Console tools : this is a wrapper class for System#out and System#error
 * @author Joseph.Liu
 */
public class Console {

    private static final String TEMPLATE_VAR = "{}";

    /**
     * 当传入template有"{}"时，被认为是模板，按顺序在模板中打印参数，舍弃多余的参数
     * 当传入template无"{}"时，被认为非模板，直接在template后打印多个参数（以空格分隔）
     * @param template 输出打印模板
     * @param values 模板中的值
     */
    public static void log(String template, Object... values){
        if(ArrayUtil.isEmpty(values) || StrUtil.contains(template,TEMPLATE_VAR)){
            printInternal(true,template,values);
        }else{
            printInternal(true,buildTemplateSplitBySpace(values.length + 1),ArrayUtil.insert(values,0,template));
        }
    }
    public static void print(String template, Object... values){
        if(ArrayUtil.isEmpty(values) || StrUtil.contains(template,TEMPLATE_VAR)){
            printInternal(false,template,values);
        }else{
            printInternal(false,buildTemplateSplitBySpace(values.length + 1),ArrayUtil.insert(values,0,template));
        }
    }
    private static void printInternal(boolean hasTimestamp, String template, Object... values){
        if(!hasTimestamp){
            out.println(StrUtil.format(template,values));
        }else{
            out.printf("[%s] %s%n", DateTime.now(),StrUtil.format(template,values));
        }
    }

    private static String buildTemplateSplitBySpace(int count){
        return StrUtil.repeatAndJoin(TEMPLATE_VAR,count,StrUtil.SPACE);
    }
    /**
     * print progress bar with a fixed length
     * @param showChar display character
     * @param len length of the progress bar
     */
    public static void printProgress(char showChar, int len){
        print("{}{}", CharUtil.CR,StrUtil.repeat(showChar,len));
    }
    public static void printProgress(char showChar, int totalLength, double ratio){
        Assert.isTrue(ratio >= 0 && ratio <=1, "Ratio must be in range of [0,1]");
        printProgress(showChar, (int)(totalLength * ratio) );
    }
    public static void progress(String task, int current, int total){
        double progress = 100 * (double) current / total;
        out.printf("[%s] Task [%s] current progress: %.2f%%%n",DateTime.now(),task,progress);
    }
}
