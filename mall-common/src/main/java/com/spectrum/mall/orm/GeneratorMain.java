package com.spectrum.mall.orm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author oe_qinzuopu
 */
public class GeneratorMain {

    public static void main(String[] args) {

        Codegen codegen = new Codegen();

        /*** 配置数据库信息 */
        DataSourceConfig dbConfig = new DataSourceConfig();
        dbConfig.setDbType("mysql");
        dbConfig.setUrl(
                "jdbc:mysql://118.25.4.178:3306/mall?useUnicode=true&useSSL=false&characterEncoding=utf8");
        dbConfig.setUsername("root");
        dbConfig.setPassword("spectrum@866");

        /** 指定表名,list 可以多个 **/
        List<String> tableNameList = new ArrayList<String>();
        tableNameList.add("user");


        /** 存放目录路径 */
        String parentDir = "com.spectrum.mall.core";
        /** 模块名称 存放目录为 com.spectrum.{module} */
        String module = "user";
        // 开发人姓名
        String author = "qinzp";

        try {
            codegen.execute(parentDir, module, author, dbConfig, tableNameList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
