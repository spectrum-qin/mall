package com.spectrum.mall.utils.idcard;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author oe_qinzuopu
 */
public class OCRUtil {
    /**
     * 识别图片信息
     * @param img
     * @return
     */
    public static String getImageMessage(BufferedImage img,String language,boolean hasLanguage){

        String result="end";
        try{
            ITesseract instance = new Tesseract();
            File tessDataFolder = LoadLibs.extractTessResources("tessdata");
           /* if(hasLanguage){
            	 instance.setLanguage(language);
            } */ 
            instance.setDatapath(tessDataFolder.getAbsolutePath()); 
            
            instance.setTessVariable("digits", "0123456789X");
            instance.setTessVariable("user_defined_dpi", "300");
            instance.setTessVariable("fonts_dir", tessDataFolder.getAbsolutePath()+File.separator+"fonts");
            result = instance.doOCR(img);
            //System.out.println(result);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {
        File file = new File("D:/xx4.jpg");
        Tesseract instance = new Tesseract();
        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        instance.setDatapath(tessDataFolder.getAbsolutePath());
        try {

            String code = instance.doOCR(file);
            System.out.println("验证码===》"+code);
        } catch (TesseractException e) {
            System.out.println("===>解析验证码失败");
        }
    }
}