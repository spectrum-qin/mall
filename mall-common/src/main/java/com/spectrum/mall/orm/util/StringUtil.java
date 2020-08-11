package com.spectrum.mall.orm.util;

import com.spectrum.mall.orm.exception.CodegenException;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public StringUtil() {
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            return str.trim().equals("");
        }
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String replaceVariable(String template, Map<String, String> map) throws CodegenException {
        Pattern regex = Pattern.compile("\\{(.*?)\\}");

        String toReplace;
        String value;
        for(Matcher regexMatcher = regex.matcher(template); regexMatcher.find(); template = template.replace(toReplace, value)) {
            String key = regexMatcher.group(1);
            toReplace = regexMatcher.group(0);
            value = (String)map.get(key);
            if (value == null) {
                throw new CodegenException("" + key + "]");
            }
        }

        return template;
    }

    public static String replaceVariable(String template, String tableName) {
        Pattern regex = Pattern.compile("\\{(.*?)\\}");
        Matcher regexMatcher = regex.matcher(template);
        if (regexMatcher.find()) {
            String toReplace = regexMatcher.group(0);
            template = template.replace(toReplace, tableName);
        }

        return template;
    }

    public static String trimPrefix(String toTrim, String trimStr) {
        while(toTrim.startsWith(trimStr)) {
            toTrim = toTrim.substring(trimStr.length());
        }

        return toTrim;
    }

    public static String trimSufffix(String toTrim, String trimStr) {
        while(toTrim.endsWith(trimStr)) {
            toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
        }

        return toTrim;
    }

    public static String trim(String toTrim, String trimStr) {
        return trimSufffix(trimPrefix(toTrim, trimStr), trimStr);
    }

    public static String combilePath(String baseDir, String dir) {
        baseDir = trimSufffix(baseDir, File.separator);
        dir = trimPrefix(dir, File.separator);
        return baseDir + File.separator + dir;
    }

    public static String replace(String content, String startTag, String endTag, String repalceWith) {
        String tmp = content.toLowerCase();
        String tmpStartTag = startTag.toLowerCase();
        String tmpEndTag = endTag.toLowerCase();
        StringBuffer sb = new StringBuffer();
        int beginIndex = tmp.indexOf(tmpStartTag);
        int endIndex = tmp.indexOf(tmpEndTag);

        while(beginIndex != -1 && endIndex != -1 && beginIndex < endIndex) {
            String pre = content.substring(0, tmp.indexOf(tmpStartTag) + tmpStartTag.length());
            String suffix = content.substring(tmp.indexOf(tmpEndTag));
            tmp = suffix.toLowerCase();
            content = suffix.substring(endTag.length());
            beginIndex = tmp.indexOf(tmpStartTag);
            endIndex = tmp.indexOf(tmpEndTag);
            String replaced = pre + "\r\n" + repalceWith + "\r\n" + endTag;
            sb.append(replaced);
        }

        sb.append(content);
        return sb.toString();
    }

    public static boolean isExist(String content, String begin, String end) {
        String tmp = content.toLowerCase();
        begin = begin.toLowerCase();
        end = end.toLowerCase();
        int beginIndex = tmp.indexOf(begin);
        int endIndex = tmp.indexOf(end);
        return beginIndex != -1 && endIndex != -1 && beginIndex < endIndex;
    }

    public static String subString(String content, String start, String end) {
        String str = content;
        if (content.indexOf(start) != -1) {
            if (content.indexOf(end) != -1) {
                str = content.substring(content.indexOf(start) + start.length(), content.lastIndexOf(end));
            } else {
                str = content.substring(content.indexOf(start) + start.length());
            }
        }

        return str;
    }

    public static String firstLetterToUpper(String str) {
        Pattern regex = Pattern.compile("^[A-Z].*?");
        Matcher regexMatcher = regex.matcher(str);
        if (regexMatcher.matches()) {
            return str;
        } else {
            char[] array = str.toCharArray();
            array[0] = (char)(array[0] - 32);
            return String.valueOf(array);
        }
    }

    public static String underLineToHump(String str) {
        String[] strs = str.split("_");
        String hStr = "";

        for(int i = 0; i < strs.length; ++i) {
            hStr = hStr + firstLetterToUpper(strs[i]);
        }

        return hStr;
    }
}
