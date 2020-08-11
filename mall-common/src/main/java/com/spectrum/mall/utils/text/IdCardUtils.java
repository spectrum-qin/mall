package com.spectrum.mall.utils.text;

import com.spectrum.mall.utils.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IdCardUtils {
    private static final Logger log = LoggerFactory.getLogger(IdCardUtils.class);
    public static final int CHINA_ID_MIN_LENGTH = 15;
    public static final int CHINA_ID_MAX_LENGTH = 18;
    public static final String[] cityCode = new String[]{"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};
    public static final int[] power = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    public static final String[] verifyCode = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    public static final int MIN = 1930;
    private static final String ID_CARD_IS_EMPTY = "身份证号不能为空！";
    private static final String ILLEGAL_ID_CARD = "非法的身份证号：";
    private static final String ILLEGAL_ID_CARD_AFTER_THAN_NOW = "非法的身份证号，大于当前日期：";
    private static final Map<String, Integer> twFirstCode = new HashMap();
    private static final Map<String, Integer> hkFirstCode = new HashMap();
    private static Map<String, String> identityNoList = null;

    public IdCardUtils() {
    }

    public static String conver15CardTo18(String idCard) {
        String idCard18 = "";
        if (idCard.length() != 15) {
            return null;
        } else if (isNum(idCard)) {
            String birthday = idCard.substring(6, 12);
            Date birthDate = null;

            try {
                birthDate = (new SimpleDateFormat("yyMMdd")).parse(birthday);
            } catch (ParseException var10) {
                log.error(var10.getMessage(), var10);
            }

            Calendar cal = Calendar.getInstance();
            if (birthDate != null) {
                cal.setTime(birthDate);
            }

            String sYear = String.valueOf(cal.get(1));
            idCard18 = idCard.substring(0, 6) + sYear + idCard.substring(8);
            char[] cArr = idCard18.toCharArray();
            if (cArr != null) {
                int[] iCard = converCharToInt(cArr);
                int iSum17 = getPowerSum(iCard);
                String sVal = getCheckCode18(iSum17);
                if (sVal.length() <= 0) {
                    return null;
                }

                idCard18 = idCard18 + sVal;
            }

            return idCard18;
        } else {
            return null;
        }
    }

    public static boolean validateCard(String idCard) {
        String card = idCard.trim();
        if (validateIdCard18(card)) {
            return true;
        } else {
            String[] cardval = validateIdCard10(card);
            return cardval != null && cardval[2].equals("true");
        }
    }

    public static boolean validateIdCard18(String idCard) {
        boolean bTrue = false;
        if (idCard.length() != 18) {
            return bTrue;
        } else if (!validArea(idCard)) {
            return false;
        } else if (!validBirthDay(idCard)) {
            return false;
        } else {
            String code17 = idCard.substring(0, 17);
            String code18 = idCard.substring(17, 18);
            if (isNum(code17)) {
                char[] cArr = code17.toCharArray();
                if (cArr != null) {
                    int[] iCard = converCharToInt(cArr);
                    int iSum17 = getPowerSum(iCard);
                    String val = getCheckCode18(iSum17);
                    if (val.length() > 0 && val.equalsIgnoreCase(code18)) {
                        bTrue = true;
                    }
                }
            }

            return bTrue;
        }
    }

    public static boolean validateIdCard15(String idCard) {
        if (idCard.length() != 15) {
            return false;
        } else if (!validArea(idCard)) {
            return false;
        } else if (!validBirthDay(idCard)) {
            return false;
        } else if (isNum(idCard)) {
            String birthCode = idCard.substring(6, 12);
            Date birthDate = null;

            try {
                birthDate = (new SimpleDateFormat("yy")).parse(birthCode.substring(0, 2));
            } catch (ParseException var4) {
                log.error(var4.getMessage(), var4);
            }

            Calendar cal = Calendar.getInstance();
            if (birthDate != null) {
                cal.setTime(birthDate);
            }

            return valiDate(cal.get(1), Integer.valueOf(birthCode.substring(2, 4)), Integer.valueOf(birthCode.substring(4, 6)));
        } else {
            return false;
        }
    }

    public static String[] validateIdCard10(String idCard) {
        String[] info = new String[3];
        String card = idCard.replaceAll("[\\(|\\)]", "");
        if (card.length() != 8 && card.length() != 9 && idCard.length() != 10) {
            return null;
        } else {
            if (idCard.matches("^[a-zA-Z][0-9]{9}$")) {
                info[0] = "台湾";
                System.out.println("11111");
                String char2 = idCard.substring(1, 2);
                if (char2.equals("1")) {
                    info[1] = "M";
                    System.out.println("MMMMMMM");
                } else {
                    if (!char2.equals("2")) {
                        info[1] = "N";
                        info[2] = "false";
                        System.out.println("NNNN");
                        return info;
                    }

                    info[1] = "F";
                    System.out.println("FFFFFFF");
                }

                info[2] = validateTWCard(idCard) ? "true" : "false";
            } else if (idCard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$")) {
                info[0] = "澳门";
                info[1] = "N";
            } else {
                if (!idCard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) {
                    return null;
                }

                info[0] = "香港";
                info[1] = "N";
                info[2] = validateHKCard(idCard) ? "true" : "false";
            }

            return info;
        }
    }

    public static boolean validateTWCard(String idCard) {
        String start = idCard.substring(0, 1);
        String mid = idCard.substring(1, 9);
        String end = idCard.substring(9, 10);
        Integer iStart = (Integer)twFirstCode.get(start);
        Integer sum = iStart / 10 + iStart % 10 * 9;
        char[] chars = mid.toCharArray();
        Integer iflag = 8;
        char[] var8 = chars;
        int var9 = chars.length;

        for(int var10 = 0; var10 < var9; ++var10) {
            char c = var8[var10];
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag = iflag - 1;
        }

        return (sum % 10 == 0 ? 0 : 10 - sum % 10) == Integer.valueOf(end);
    }

    public static boolean validateHKCard(String idCard) {
        String card = idCard.replaceAll("[\\(|\\)]", "");
        Integer sum = 0;
        if (card.length() == 9) {
            sum = (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 9 + (Integer.valueOf(card.substring(1, 2).toUpperCase().toCharArray()[0]) - 55) * 8;
            card = card.substring(1, 9);
        } else {
            sum = 522 + (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 55) * 8;
        }

        String mid = card.substring(1, 7);
        String end = card.substring(7, 8);
        char[] chars = mid.toCharArray();
        Integer iflag = 7;
        char[] var7 = chars;
        int var8 = chars.length;

        for(int var9 = 0; var9 < var8; ++var9) {
            char c = var7[var9];
            sum = sum + Integer.valueOf(c + "") * iflag;
            iflag = iflag - 1;
        }

        if (end.toUpperCase().equals("A")) {
            sum = sum + 10;
        } else {
            sum = sum + Integer.valueOf(end);
        }

        return sum % 11 == 0;
    }

    private static int[] converCharToInt(char[] ca) {
        int len = ca.length;
        int[] iArr = new int[len];

        try {
            for(int i = 0; i < len; ++i) {
                iArr[i] = Integer.parseInt(String.valueOf(ca[i]));
            }
        } catch (NumberFormatException var4) {
            log.error(var4.getMessage(), var4);
        }

        return iArr;
    }

    private static int getPowerSum(int[] iArr) {
        int iSum = 0;
        if (power.length == iArr.length) {
            for(int i = 0; i < iArr.length; ++i) {
                for(int j = 0; j < power.length; ++j) {
                    if (i == j) {
                        iSum += iArr[i] * power[j];
                    }
                }
            }
        }

        return iSum;
    }

    private static String getCheckCode18(int iSum) {
        String sCode = "";
        switch(iSum % 11) {
            case 0:
                sCode = "1";
                break;
            case 1:
                sCode = "0";
                break;
            case 2:
                sCode = "x";
                break;
            case 3:
                sCode = "9";
                break;
            case 4:
                sCode = "8";
                break;
            case 5:
                sCode = "7";
                break;
            case 6:
                sCode = "6";
                break;
            case 7:
                sCode = "5";
                break;
            case 8:
                sCode = "4";
                break;
            case 9:
                sCode = "3";
                break;
            case 10:
                sCode = "2";
        }

        return sCode;
    }

    public static int getAgeByIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            throw new IllegalArgumentException("身份证号不能为空！");
        } else {
            boolean iAge = false;
            String tmp;
            if (idCard.length() == 15) {
                tmp = conver15CardTo18(idCard);
                if (null != tmp && !tmp.isEmpty()) {
                    idCard = tmp;
                }
            }

            tmp = null;

            Date bornDate;
            try {
                bornDate = DateUtils.FORMAT_NO_SEP_ON_DAY.parse(idCard.substring(6, 14));
            } catch (ParseException var6) {
                throw new IllegalArgumentException("非法的身份证号：" + idCard);
            }

            Calendar bornCal = Calendar.getInstance();
            bornCal.setTime(bornDate);
            Calendar nowCal = Calendar.getInstance();
            if (bornCal.after(nowCal)) {
                throw new IllegalArgumentException("非法的身份证号，大于当前日期：" + idCard);
            } else {
                int age = nowCal.get(1) - bornCal.get(1);
                return nowCal.get(6) < bornCal.get(6) ? age - 1 : age;
            }
        }
    }

    public static String getBirthByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len < 15) {
            return null;
        } else {
            if (len == 15) {
                String tmp = conver15CardTo18(idCard);
                if (null != tmp && !tmp.isEmpty()) {
                    idCard = tmp;
                }
            }

            return idCard.substring(6, 14);
        }
    }

    public static Short getYearByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len < 15) {
            return null;
        } else {
            if (len == 15) {
                String tmp = conver15CardTo18(idCard);
                if (null != tmp && !tmp.isEmpty()) {
                    idCard = tmp;
                }
            }

            return Short.valueOf(idCard.substring(6, 10));
        }
    }

    public static Short getMonthByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len < 15) {
            return null;
        } else {
            if (len == 15) {
                String tmp = conver15CardTo18(idCard);
                if (null != tmp && !tmp.isEmpty()) {
                    idCard = tmp;
                }
            }

            return Short.valueOf(idCard.substring(10, 12));
        }
    }

    public static Short getDateByIdCard(String idCard) {
        Integer len = idCard.length();
        if (len < 15) {
            return null;
        } else {
            if (len == 15) {
                String tmp = conver15CardTo18(idCard);
                if (null != tmp && !tmp.isEmpty()) {
                    idCard = tmp;
                }
            }

            return Short.valueOf(idCard.substring(12, 14));
        }
    }

    public static String getGenderByIdCard(String idCard) {
        String sGender = "N";
        String tmp;
        if (idCard.length() == 15) {
            tmp = conver15CardTo18(idCard);
            if (null != tmp && !tmp.isEmpty()) {
                idCard = tmp;
            }
        }

        tmp = idCard.substring(16, 17);
        if (Integer.parseInt(tmp) % 2 != 0) {
            sGender = "M";
        } else {
            sGender = "F";
        }

        return sGender;
    }

    private static boolean isNum(String val) {
        return val != null && !"".equals(val) ? val.matches("^[0-9]*$") : false;
    }

    private static boolean valiDate(int iYear, int iMonth, int iDate) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(1);
        if (iYear >= 1930 && iYear < year) {
            if (iMonth >= 1 && iMonth <= 12) {
                int datePerMonth;
                switch(iMonth) {
                    case 2:
                        boolean dm = (iYear % 4 == 0 && iYear % 100 != 0 || iYear % 400 == 0) && iYear > 1930 && iYear < year;
                        datePerMonth = dm ? 29 : 28;
                        break;
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    default:
                        datePerMonth = 31;
                        break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        datePerMonth = 30;
                }

                return iDate >= 1 && iDate <= datePerMonth;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean validArea(String idCard) {
        String provinceid = idCard.substring(0, 2);
        String[] var2 = cityCode;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String id = var2[var4];
            if (id.equals(provinceid)) {
                return true;
            }
        }

        return false;
    }

    private static boolean validBirthDay(String idCard) {
        Short year = null;
        Short month = null;
        Short day = null;

        try {
            year = getYearByIdCard(idCard);
            month = getMonthByIdCard(idCard);
            day = getDateByIdCard(idCard);
        } catch (NumberFormatException var5) {
            log.info("身份证年月日转换数字失败");
            return false;
        }

        boolean isRunYear = year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
        if (month >= 1 && month <= 12) {
            if (month != 1 && month != 3 && month != 5 && month != 7 && month != 8 && month != 10 && month != 12) {
                if (month == 2) {
                    if (isRunYear) {
                        if (day < 1 || day > 29) {
                            return false;
                        }
                    } else if (day < 1 || day > 28) {
                        return false;
                    }
                } else if ((month == 4 || month == 6 || month == 9 || month == 11) && (day < 1 || day > 30)) {
                    return false;
                }
            } else if (day < 1 || day > 31) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    private static Map<String, String> loadIndetityNoList() {
        final Map<String, String> words = new HashMap();
        String path = "classpath:config/zsf/boot/util/identity.txt";
        ResourceLoaderUtils.load(new ResourceLoader() {
            public void clear() {
            }

            public void load(List<String> lines) {
                Iterator var2 = lines.iterator();

                while(var2.hasNext()) {
                    String str = (String)var2.next();
                    words.put(str, str);
                }

            }
        }, path);
        return words;
    }

    static {
        identityNoList = loadIndetityNoList();
        twFirstCode.put("A", 10);
        twFirstCode.put("B", 11);
        twFirstCode.put("C", 12);
        twFirstCode.put("D", 13);
        twFirstCode.put("E", 14);
        twFirstCode.put("F", 15);
        twFirstCode.put("G", 16);
        twFirstCode.put("H", 17);
        twFirstCode.put("J", 18);
        twFirstCode.put("K", 19);
        twFirstCode.put("L", 20);
        twFirstCode.put("M", 21);
        twFirstCode.put("N", 22);
        twFirstCode.put("P", 23);
        twFirstCode.put("Q", 24);
        twFirstCode.put("R", 25);
        twFirstCode.put("S", 26);
        twFirstCode.put("T", 27);
        twFirstCode.put("U", 28);
        twFirstCode.put("V", 29);
        twFirstCode.put("X", 30);
        twFirstCode.put("Y", 31);
        twFirstCode.put("W", 32);
        twFirstCode.put("Z", 33);
        twFirstCode.put("I", 34);
        twFirstCode.put("O", 35);
        hkFirstCode.put("A", 1);
        hkFirstCode.put("B", 2);
        hkFirstCode.put("C", 3);
        hkFirstCode.put("R", 18);
        hkFirstCode.put("U", 21);
        hkFirstCode.put("Z", 26);
        hkFirstCode.put("X", 24);
        hkFirstCode.put("W", 23);
        hkFirstCode.put("O", 15);
        hkFirstCode.put("N", 14);
    }
}
