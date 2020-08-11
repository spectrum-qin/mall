package com.spectrum.mall.utils.time;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author oe_qinzuopu
 */
public class TimeUtil {
    public TimeUtil() {
    }

    public static String getCurTimeStamp(String format) {
        Date date = new Date();
        String strStamp = DateFormatUtils.format(date, format);
        return strStamp;
    }

    public static String ADD_DATE(int optype, String date, int num) {
        String st_return = "";

        try {
            DateFormat daf_date = DateFormat.getDateInstance(2, Locale.CHINA);
            daf_date.parse(date);
            Calendar calendar = daf_date.getCalendar();
            calendar.add(optype, num);
            if (optype == 2) {
                calendar.add(5, -1);
            }

            String st_m = "";
            String st_d = "";
            int y = calendar.get(1);
            int m = calendar.get(2) + 1;
            int d = calendar.get(5);
            if (m <= 9) {
                st_m = "0" + m;
            } else {
                st_m = "" + m;
            }

            if (d <= 9) {
                st_d = "0" + d;
            } else {
                st_d = "" + d;
            }

            st_return = y + "-" + st_m + "-" + st_d;
        } catch (ParseException var11) {
            var11.printStackTrace();
        }

        return st_return;
    }

    private static String ADD_DATE2(int optype, String date, int num) {
        String st_return = "";

        try {
            DateFormat daf_date = DateFormat.getDateInstance(2, Locale.CHINA);
            daf_date.parse(date);
            Calendar calendar = daf_date.getCalendar();
            calendar.add(optype, num);
            if (optype == 2) {
                calendar.add(5, 0);
            }

            String st_m = "";
            String st_d = "";
            int y = calendar.get(1);
            int m = calendar.get(2) + 1;
            int d = calendar.get(5);
            if (m <= 9) {
                st_m = "0" + m;
            } else {
                st_m = "" + m;
            }

            if (d <= 9) {
                st_d = "0" + d;
            } else {
                st_d = "" + d;
            }

            st_return = y + "-" + st_m + "-" + st_d;
        } catch (ParseException var11) {
            var11.printStackTrace();
        }

        return st_return;
    }

    public static String ADD_DAY(String date, int n) {
        return ADD_DATE(5, date, n);
    }

    public static String ADD_MONTH(String date, int n) {
        return ADD_DATE(2, date, n);
    }

    public static String ADD_MONTH2(String date, int n) {
        return ADD_DATE2(2, date, n);
    }

    public static String ADD_YEAR(String date, int n) {
        return ADD_DATE(1, date, n);
    }

    public static boolean validate(String dateString) {
        Pattern p = Pattern.compile("\\d{4}+[-]\\d{1,2}+[-]\\d{1,2}+");
        Matcher m = p.matcher(dateString);
        if (!m.matches()) {
            return false;
        } else {
            String[] array = dateString.split("-");
            int year = Integer.valueOf(array[0]);
            int month = Integer.valueOf(array[1]);
            int day = Integer.valueOf(array[2]);
            if (month >= 1 && month <= 12) {
                int[] monthLengths = new int[]{0, 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                if (isLeapYear(year)) {
                    monthLengths[2] = 29;
                } else {
                    monthLengths[2] = 28;
                }

                int monthLength = monthLengths[month];
                return day >= 1 && day <= monthLength;
            } else {
                return false;
            }
        }
    }

    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }
}
