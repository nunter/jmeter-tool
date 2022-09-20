package com.zxy.jmeter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.FileSystemView;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 醉逍遥
 * @version v1.0
 */
public class Tool {
    public static final String DESKTOP = FileSystemView.getFileSystemView().getHomeDirectory().getPath();
    public static final String JMETER_HOME = System.getProperty("user.dir");

    private Tool() {
    }

    public static String getType(Object obj) {
        return obj.getClass().getName();
    }

    public static int getHashCode(String str) {
        return Math.abs(str.hashCode());
    }

    public static String getMD5(String str, boolean isCap) {
        MessageDigest md5 = null;

        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
        }

        byte[] b = md5.digest();
        StringBuffer buf = new StringBuffer("");

        for(int offset = 0; offset < b.length; ++offset) {
            int i = b[offset];
            if (i < 0) {
                i += 256;
            }

            if (i < 16) {
                buf.append("0");
            }

            buf.append(Integer.toHexString(i));
        }

        if (isCap) {
            return buf.toString().toUpperCase();
        } else {
            return buf.toString();
        }
    }

    public static String getMD5(String str) {
        return getMD5(str, true);
    }

    public static String getChineseName() {
        Random random = new Random(System.currentTimeMillis());
        String[] Surname = new String[]{"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "宇文"};
        int index = random.nextInt(Surname.length);
        String name = Surname[index];
        if (random.nextBoolean()) {
            name = name + getChineseWord() + getChineseWord();
        } else {
            name = name + getChineseWord();
        }

        return name;
    }

    private static String getChineseWord() {
        Random random = new Random();
        String[] firstName = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "丰", "王", "井", "开", "夫", "天", "无", "元", "专", "云", "扎", "艺", "木", "五", "支", "厅", "不", "太", "犬", "区", "历", "尤", "友", "匹", "车", "巨", "牙", "屯", "比", "互", "切", "瓦", "止", "少", "日", "中", "冈", "贝", "内", "水", "见", "午", "牛", "手", "毛", "气", "升", "长", "仁", "什", "片", "仆", "化", "仇", "币", "仍", "仅", "斤", "爪", "反", "介", "父", "从", "今", "凶", "分", "乏", "公", "仓", "月", "氏", "勿", "欠", "风", "丹", "匀", "乌", "凤", "勾", "文", "六", "方", "火", "为", "斗", "忆", "订", "计", "户", "认", "心", "尺", "引", "丑", "巴", "孔", "队", "办", "以", "允", "予", "劝", "双", "书", "幻", "千", "万", "亿", "星", "光"};
        int index = random.nextInt(firstName.length);
        return firstName[index];
    }

    public static void writeFile(String str, String filePath) throws Exception {
        writeFile(str, filePath, false);
    }

    public static void writeFile(String str, String filePath, boolean keep) throws Exception {
        createNewFile(filePath);
        String WRITE_TO_FILE = str;
        String FILE_PATH = filePath;

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
            }

            boolean isExist = false;
            if (!isExist) {
                FileOutputStream fos = new FileOutputStream(file.getAbsoluteFile(), keep);
                fos.write(WRITE_TO_FILE.getBytes());
                fos.write("\r\n".getBytes());
                fos.close();
            }
        } catch (IOException var8) {
            var8.printStackTrace();
        }

    }

    public static String JDBCResultToString(ArrayList list, String key) {
        ArrayList DBResultList = list;
        String DBResultToStr = "";

        for(int i = 0; i < DBResultList.size(); ++i) {
            if (DBResultList.get(i) instanceof HashMap) {
                DBResultToStr = DBResultToStr + ((HashMap)DBResultList.get(i)).get(key);
            } else {
                DBResultToStr = DBResultToStr + (String)DBResultList.get(i);
            }

            if (i + 1 < DBResultList.size()) {
                DBResultToStr = DBResultToStr + "\r\n";
            }
        }

        return DBResultToStr;
    }

    public static void JDBCResultWriteFile(ArrayList list, String key, String filePath) throws Exception {
        String str = JDBCResultToString(list, key);
        writeFile(str, filePath);
    }

    public static String JDBCResultToString(ArrayList list, String[] keyArray) {
        ArrayList DBResultList = list;
        String DBResultToStr = "";

        for(int i = 0; i < DBResultList.size(); ++i) {
            String key = "";

            for(int x = 0; x < keyArray.length; ++x) {
                if (DBResultList.get(i) instanceof HashMap) {
                    key = key + ((HashMap)DBResultList.get(i)).get(keyArray[x]);
                } else {
                    key = key + (String)DBResultList.get(i);
                }

                if (x + 1 < keyArray.length) {
                    key = key + ",";
                }
            }

            DBResultToStr = DBResultToStr + key;
            if (i + 1 < DBResultList.size()) {
                DBResultToStr = DBResultToStr + "\r\n";
            }
        }

        return DBResultToStr;
    }

    public static void JDBCResultWriteFile(ArrayList list, String[] keyArray, String filePath) throws Exception {
        String str = JDBCResultToString(list, keyArray);
        writeFile(str, filePath);
    }

    public static String getDate(int type) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, type);
        String date = (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
        return date;
    }

    public static String getDate(int type, String format) {
        Calendar cal = Calendar.getInstance();
        cal.add(5, type);
        String date = (new SimpleDateFormat(format)).format(cal.getTime());
        return date;
    }

    public static void createNewFile(String fileDir) throws Exception {
        File file = new File(fileDir);
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }

        file.createNewFile();
    }

    public static String JDBCResultToSql(ArrayList list, String key) {
        ArrayList DBResultList = list;
        String DBResultToStr = "";

        for(int i = 0; i < DBResultList.size(); ++i) {
            if (DBResultList.get(i) instanceof HashMap) {
                DBResultToStr = DBResultToStr + "'" + ((HashMap)DBResultList.get(i)).get(key) + "'";
            } else {
                DBResultToStr = DBResultToStr + "'" + (String)DBResultList.get(i) + "'";
            }

            if (i + 1 < DBResultList.size()) {
                DBResultToStr = DBResultToStr + ",";
            }
        }

        return DBResultToStr;
    }

    private static int getNum(int start, int end) {
        return (int)(Math.random() * (double)(end - start + 1) + (double)start);
    }

    public static String getMobile() {
        String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
        int index = getNum(0, telFirst.length - 1);
        String first = telFirst[index];
        String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
        String third = String.valueOf(getNum(1, 9100) + 10000).substring(1);
        return first + second + third;
    }

    public static String getEmail(int Min, int Max) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        String[] email_suffix = "@qq.com,@163.com,@gmail.com,@sina.com,@sohu.com,@yahoo.com.cn".split(",");
        int length = getNum(Min, Max);
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < length; ++i) {
            int number = (int)(Math.random() * (double)"abcdefghijklmnopqrstuvwxyz0123456789".length());
            sb.append("abcdefghijklmnopqrstuvwxyz0123456789".charAt(number));
        }

        sb.append(email_suffix[(int)(Math.random() * (double)email_suffix.length)]);
        return sb.toString();
    }

    public static List<String> getRegexResult(String regex, String source) {
        return getRegexResult(regex, source, 0);
    }

    public static List<String> getRegexResult(String regex, String source, int mode) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        List<String> list = new ArrayList();

        while(matcher.find()) {
            list.add(matcher.group(mode));
        }

        return list;
    }

    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean ifInclude(String Source, String str) {
        boolean ifInclude = false;
        if (Source.indexOf(str) != -1) {
            ifInclude = true;
        } else {
            ifInclude = false;
        }

        return ifInclude;
    }

    public static String dateJiSuan(String initDate, int number) {
        return dateJiSuan(initDate, "yyyy-MM-dd HH:mm:ss", number);
    }

    public static String dateJiSuan(String initDate, String dateFormat, int number) {
        String finalDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date tmpDate = null;

        try {
            tmpDate = sdf.parse(initDate);
        } catch (ParseException var9) {
            var9.printStackTrace();
        }

        new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.setTime(tmpDate);
        c.add(5, number);
        tmpDate = c.getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat(dateFormat);
        finalDate = sdf1.format(tmpDate);
        return finalDate;
    }

    public static String getFileMD5(String dirPath) {
        return getFileMD5(dirPath, true);
    }

    public static String getFileMD5(String dirPath, boolean cap) {
        String md5 = null;

        try {
            md5 = DigestUtils.md5Hex(new FileInputStream(dirPath));
        } catch (FileNotFoundException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return cap ? md5.toUpperCase() : md5;
    }
}

