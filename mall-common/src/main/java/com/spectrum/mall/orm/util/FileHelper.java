package com.spectrum.mall.orm.util;

import java.io.*;

public class FileHelper {
    public FileHelper() {
    }

    public static String readFile(String fileName, String charset) {
        try {
            new File(fileName);
            StringBuffer sb = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));

            String str;
            while((str = in.readLine()) != null) {
                sb.append(str + "\r\n");
            }

            in.close();
            return sb.toString();
        } catch (IOException var6) {
            var6.printStackTrace();
            return "";
        }
    }

    public static String getCharset(File file) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];

        try {
            boolean checked = false;
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset;
            }

            if (first3Bytes[0] == -1 && first3Bytes[1] == -2) {
                charset = "UTF-16LE";
                checked = true;
            } else if (first3Bytes[0] == -2 && first3Bytes[1] == -1) {
                charset = "UTF-16BE";
                checked = true;
            } else if (first3Bytes[0] == -17 && first3Bytes[1] == -69 && first3Bytes[2] == -65) {
                charset = "UTF-8";
                checked = true;
            }

            bis.reset();
            if (!checked) {
                int var6 = 0;

                label74:
                do {
                    do {
                        if ((read = bis.read()) == -1) {
                            break label74;
                        }

                        ++var6;
                        if (read >= 240 || 128 <= read && read <= 191) {
                            break label74;
                        }

                        if (192 <= read && read <= 223) {
                            read = bis.read();
                            continue label74;
                        }
                    } while(224 > read || read > 239);

                    read = bis.read();
                    if (128 <= read && read <= 191) {
                        read = bis.read();
                        if (128 <= read && read <= 191) {
                            charset = "UTF-8";
                        }
                    }
                    break;
                } while(128 <= read && read <= 191);
            }

            bis.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return charset;
    }

    public static void backupFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            String path = filePath + ".001";
            File tmp = new File(path);

            for(int i = 1; tmp.exists(); tmp = new File(path)) {
                ++i;
                int len = 3 - String.valueOf(i).length();
                String ext = String.valueOf(i);

                for(int k = 0; k < len; ++k) {
                    ext = "0" + ext;
                }

                path = filePath + "." + ext;
            }

            copyFile(filePath, path);
        }

    }

    public static void writeFile(File file, String charset, String content) throws IOException {
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), charset);
        writer.write(content);
        writer.close();
    }

    public static String[] getBySplit(String content, String splitTag) {
        String[] aryRtn = new String[2];
        int pos = content.lastIndexOf(splitTag);
        aryRtn[0] = content.substring(0, pos);
        aryRtn[1] = content.substring(pos + splitTag.length());
        return aryRtn;
    }

    public static boolean copyFile(String from, String to) {
        File fromFile = new File(from);
        File toFile = new File(to);
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(fromFile);
            fos = new FileOutputStream(toFile);
            byte[] buf = new byte[4096];

            int bytesRead;
            while((bytesRead = fis.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }

            fos.flush();
            fos.close();
            fis.close();
            return true;
        } catch (IOException var8) {
            System.out.println(var8);
            return false;
        }
    }

    public static boolean isExistFile(String dir) {
        boolean isExist = false;
        File fileDir = new File(dir);
        if (fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if (files != null && files.length != 0) {
                isExist = true;
            }
        }

        return isExist;
    }

    public static File[] getFiles(String path) {
        File file = new File(path);
        return file.listFiles();
    }

    public static InputStream getInputStream(String filepath) {
        File file = new File(filepath);
        String charset = getCharset(file);
        String str = readFile(filepath, charset);
        ByteArrayInputStream stream = null;

        try {
            stream = new ByteArrayInputStream(str.getBytes(charset));
        } catch (UnsupportedEncodingException var6) {
            var6.printStackTrace();
        }

        return stream;
    }
}
