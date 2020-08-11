package com.spectrum.mall.utils.time;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.spectrum.mall.utils.text.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);
    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;
    private static final int[] MONTH_LENGTH = new int[]{0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
    public static final String PATTERN_ISO_ON_SECOND = "yyyy-MM-dd'T'HH:mm:ssZZ";
    public static final String PATTERN_ISO_ON_DATE = "yyyy-MM-dd";
    public static final String PATTERN_ISO_ON_MONTH = "yyyy-MM";
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String PATTERN_DEFAULT_ON_SECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_NO_SEP_ON_SECOND = "yyyyMMddHHmmss";
    public static final String PATTERN_NO_SEP_ON_DAY = "yyyyMMdd";
    public static final FastDateFormat ISO_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
    public static final FastDateFormat ISO_ON_SECOND_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ssZZ");
    public static final FastDateFormat ISO_ON_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat ISO_ON_MONTH_FORMAT = FastDateFormat.getInstance("yyyy-MM");
    public static final FastDateFormat DEFAULT_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
    public static final FastDateFormat DEFAULT_ON_SECOND_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat FORMAT_NO_SEP_ON_SECOND = FastDateFormat.getInstance("yyyyMMddHHmmss");
    public static final FastDateFormat FORMAT_NO_SEP_ON_DAY = FastDateFormat.getInstance("yyyyMMdd");

    public DateUtils() {
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameTime(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    public static boolean isBetween(Date date, Date start, Date end) {
        if (date != null && start != null && end != null && !start.after(end)) {
            return !date.before(start) && !date.after(end);
        } else {
            throw new IllegalArgumentException("some date parameters is null or dateBein after dateEnd");
        }
    }

    public static int getDayOfWeek(Date date) {
        return get(date, 7);
    }

    public static int getDayOfYear(Date date) {
        return get(date, 6);
    }

    public static int getWeekOfMonth(Date date) {
        return get(date, 4);
    }

    public static int getWeekOfYear(Date date) {
        return get(date, 3);
    }

    public static int getCurWeekOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(2);
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(3);
    }

    public static int getCurMonthOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(2) + 1;
    }

    private static int get(Date date, int field) {
        Validate.notNull(date, "The date must not be null", new Object[0]);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(field);
    }

    public static boolean isLeapYear(Date date) {
        return isLeapYear(get(date, 1));
    }

    public static boolean isLeapYear(int y) {
        boolean result = false;
        if (y % 4 == 0 && (y < 1582 || y % 100 != 0 || y % 400 == 0)) {
            result = true;
        }

        return result;
    }

    public static int getMonthLength(Date date) {
        int year = get(date, 1);
        int month = get(date, 2);
        return getMonthLength(year, month);
    }

    public static int getMonthLength(int year, int month) {
        if (month >= 1 && month <= 12) {
            if (month == 2) {
                return isLeapYear(year) ? 29 : 28;
            } else {
                return MONTH_LENGTH[month];
            }
        } else {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    public static Timestamp getCurTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String getCurDT() {
        return getCurTime("yyyyMMdd");
    }

    public static String getCurTM() {
        return getCurTime("HHmmss");
    }

    public static String getCurMonth() {
        return getCurTime("yyyyMM");
    }

    public static String getCurTime(String format) {
        StringBuilder str = new StringBuilder();
        Date ca = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        str.append(sdf.format(ca));
        return str.toString();
    }

    public static Date getCurDate() {
        return new Date();
    }

    public static java.sql.Date getCurSqlDate() {
        try {
            Date date = getCurDate();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            return sqlDate;
        } catch (Exception var2) {
            log.error("转换异常", var2);
            return null;
        }
    }

    public static Date getCurYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateStr = sdf.format(date);
        return stringToDate(dateStr);
    }

    public static Timestamp getCurYMDSql() {
        Timestamp dateSQL = new Timestamp(getCurYMD().getTime());
        return dateSQL;
    }

    public static Timestamp getCurYMDSql(Date date) {
        Timestamp dateSQL = new Timestamp(date.getTime());
        return dateSQL;
    }

    public static Date getCurYYMMDD(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            return sdf.parse(date);
        } catch (ParseException var3) {
            log.error("异常", var3);
            return null;
        }
    }

    public static java.sql.Date getCurSqlYMD() {
        try {
            Date date = getCurYMD();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            return sqlDate;
        } catch (Exception var2) {
            log.error("转换异常", var2);
            return null;
        }
    }

    public static Date getCurYMD(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return stringToDate(dateStr);
    }

    public static Date getCurYMDHM(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateStr = sdf.format(date);
        return stringToDate(dateStr);
    }

    public static Date getCurYMDH(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00");
        String dateStr = sdf.format(date);
        return stringToDate(dateStr);
    }

    public static String getSysOptDate() {
        Calendar date = Calendar.getInstance();
        Date sysDate = date.getTime();
        String optDate = dateToString(sysDate, "yyyy-MM-dd HH:mm:ss");
        return optDate;
    }

    public static String getOptDate(Date date) {
        String optDate = dateToString(date, "yyyy-MM-dd HH:mm:ss");
        return optDate;
    }

    public static String getSysOptDate(String strFormat) {
        Calendar date = Calendar.getInstance();
        Date sysDate = date.getTime();
        String optDate = dateToString(sysDate, strFormat);
        return optDate;
    }

    public static String dateToString(Date dteValue, String strFormat) {
        if (StringUtils.isEmpty(dteValue)) {
            return null;
        } else {
            SimpleDateFormat clsFormat = new SimpleDateFormat(strFormat);
            return clsFormat.format(dteValue);
        }
    }

    public static Date stringToDate(String strValue) {
        if (StringUtils.isEmptyAll(strValue)) {
            return null;
        } else {
            SimpleDateFormat clsFormat = null;
            if (strValue.length() > 19) {
                strValue = strValue.substring(0, 19);
            }

            if (strValue.length() == 19) {
                clsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else if (strValue.length() == 16) {
                clsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            } else if (strValue.length() == 13) {
                clsFormat = new SimpleDateFormat("yyyy-MM-dd HH");
            } else if (strValue.length() == 10) {
                clsFormat = new SimpleDateFormat("yyyy-MM-dd");
            } else if (strValue.length() == 14) {
                clsFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            } else if (strValue.length() == 8) {
                clsFormat = new SimpleDateFormat("yyyyMMdd");
            }

            ParsePosition pos = new ParsePosition(0);
            return clsFormat.parse(strValue, pos);
        }
    }

    public static boolean kuaYue(String startAdt, String endAdt) {
        boolean his = false;

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(stringToDate(startAdt));
            cal.set(5, 1);
            Calendar endCal = Calendar.getInstance();
            endCal.setTime(stringToDate(endAdt));
            endCal.set(5, 1);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
            String startMonth = format.format(cal.getTime());
            String endMonth = format.format(endCal.getTime());
            his = !startMonth.equals(endMonth);
        } catch (Exception var8) {
            log.error("异常", var8);
        }

        return his;
    }

    public static String getCurDate(Date date) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        return strDate;
    }

    public static String getCurDateForNull(Date date) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = sdf.format(date);
            return strDate;
        }
    }

    public static String getCurTime(Date date) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strStamp = sdf.format(date);
        return strStamp;
    }

    public static String getHHMM(Date date) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public static String getHHMMSS(Date date) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static Date addTime(Date date, String unit, int length) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if ("01".equals(unit)) {
            calendar.add(11, length);
        } else if ("02".equals(unit)) {
            calendar.add(5, length);
        } else if ("03".equals(unit)) {
            calendar.add(4, length);
        } else if ("04".equals(unit)) {
            calendar.add(2, length);
        } else if ("05".equals(unit)) {
            calendar.add(12, length);
        }

        return calendar.getTime();
    }

    public static Date addTime(String unit, int length) {
        Date date = new Date();
        return addTime(date, unit, length);
    }

    public static String getCostCyc(String costCyc) {
        Date date = new Date();
        String dateStr = "";
        if ("01".equals(costCyc)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            dateStr = sdf.format(date) + "01";
        } else {
            int month;
            Calendar calendar;
            if ("02".equals(costCyc)) {
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                month = calendar.get(2);
                int year = calendar.get(1);
                if (month <= 2) {
                    dateStr = year + "0101";
                } else if (month <= 5) {
                    dateStr = year + "0401";
                } else if (month <= 8) {
                    dateStr = year + "0701";
                } else {
                    dateStr = year + "1001";
                }
            } else if ("03".equals(costCyc)) {
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                month = calendar.get(1);
                dateStr = month + "0101";
            }
        }

        return dateStr;
    }

    public static String getCostCycDate(String costCyc) {
        Date date = new Date();
        String dateStr = "";
        if ("01".equals(costCyc)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            dateStr = sdf.format(date) + "-01";
        } else {
            int month;
            Calendar calendar;
            if ("02".equals(costCyc)) {
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                month = calendar.get(2);
                int year = calendar.get(1);
                if (month <= 2) {
                    dateStr = year + "-01-01";
                } else if (month <= 5) {
                    dateStr = year + "-04-01";
                } else if (month <= 8) {
                    dateStr = year + "-07-01";
                } else {
                    dateStr = year + "-10-01";
                }
            } else if ("03".equals(costCyc)) {
                calendar = Calendar.getInstance();
                calendar.setTime(date);
                month = calendar.get(1);
                dateStr = month + "-01-01";
            }
        }

        return dateStr;
    }

    public static Date getSuccCyc(Date date, BigDecimal succCycLong, String succCyc) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int num = 0 - succCycLong.intValue();
        if ("01".equals(succCyc)) {
            calendar.add(12, num);
        } else if ("02".equals(succCyc)) {
            calendar.add(11, num);
        } else if ("03".equals(succCyc)) {
            calendar.add(5, num);
        }

        return calendar.getTime();
    }

    public static String interval(Date beginDate, Date endDate) {
        return changeDateTimeToDHMS(endDate.getTime() - beginDate.getTime());
    }

    public static String changeDateTimeToDHMS(long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;
        Long day = ms / (long)dd;
        Long hour = (ms - day * (long)dd) / (long)hh;
        Long minute = (ms - day * (long)dd - hour * (long)hh) / (long)mi;
        Long second = (ms - day * (long)dd - hour * (long)hh - minute * (long)mi) / (long)ss;
        Long milliSecond = ms - day * (long)dd - hour * (long)hh - minute * (long)mi - second * (long)ss;
        StringBuffer sb = new StringBuffer();
        if (day > 0L) {
            sb.append(day + "天");
        }

        if (hour > 0L) {
            sb.append(hour + "小时");
        }

        if (minute > 0L) {
            sb.append(minute + "分");
        }

        if (second > 0L) {
            sb.append(second + "秒");
        }

        if (milliSecond > 0L) {
            sb.append(milliSecond + "毫秒");
        }

        return sb.toString();
    }

    public static String getCurDateTimeNoSep() {
        return FORMAT_NO_SEP_ON_SECOND.format(new Date());
    }

    public static String DateToDayStr(Date date) {
        return ISO_ON_DATE_FORMAT.format(date);
    }

    public static String DateToDayTimeStr(Date date) {
        return DEFAULT_ON_SECOND_FORMAT.format(date);
    }
}
