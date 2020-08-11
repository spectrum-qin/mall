package com.spectrum.mall.orm.util;

import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;

/**
 * @author oe_qinzuopu
 */
public class XmlUtil {
    public XmlUtil() {
    }

    public static String validXmlBySchema(String xsdPath, String xmlPath) {
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        File schemaFile = new File(xsdPath);
        Schema schema = null;

        try {
            schema = schemaFactory.newSchema(schemaFile);
        } catch (SAXException var9) {
            var9.printStackTrace();
        }

        Validator validator = schema.newValidator();
        StreamSource source = new StreamSource(FileHelper.getInputStream(xmlPath));

        try {
            validator.validate(source);
            return "";
        } catch (Exception var8) {
            return var8.getMessage();
        }
    }
}
