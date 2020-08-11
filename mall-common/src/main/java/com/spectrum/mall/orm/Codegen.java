package com.spectrum.mall.orm;

import com.spectrum.mall.orm.db.IDbHelper;
import com.spectrum.mall.orm.exception.CodegenException;
import com.spectrum.mall.orm.model.ConfigModel;
import com.spectrum.mall.orm.model.TableModel;
import com.spectrum.mall.orm.util.FileHelper;
import com.spectrum.mall.orm.util.StringUtil;
import com.spectrum.mall.utils.text.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author oe_qinzuopu
 */
public class Codegen {
    private static final Logger log = LoggerFactory.getLogger(Codegen.class);
    private static String rootPath;

    public Codegen() {
    }

    public static void setRootPath(String path) {
        rootPath = path;
    }

    private static String getRootPath() {
        if (rootPath.isEmpty()) {
            rootPath = (new File("")).getAbsolutePath();
        }

        if (!rootPath.endsWith("/")) {
            rootPath = rootPath + "/";
        }

        return rootPath;
    }

    private static String getXmlPath() throws CodegenException {
        String configXml = getRootPath() + "codegenconfig.xml";
        File file = new File(configXml);
        if (!file.exists()) {
            throw new CodegenException("请确认配置文件：" + configXml + "是否存在!");
        } else {
            return configXml;
        }
    }

    private static String getXsdPath() throws CodegenException {
        String path = getRootPath() + "codegen.xsd";
        File file = new File(path);
        if (!file.exists()) {
            throw new CodegenException("schema文件" + path + "不存在");
        } else {
            return path;
        }
    }

    private static String getPropertiesPath() throws CodegenException {
        String path = getRootPath() + "codegen.properties";
        File file = new File(path);
        if (!file.exists()) {
            throw new CodegenException("代码生成配置文件" + path + "不存在");
        } else {
            return path;
        }
    }

    public ConfigModel loadXml(String configXml, String baseDir) throws Exception {
        ConfigModel cm = new ConfigModel();
        SAXReader saxReader = new SAXReader();
        Document document = null;

        try {
            InputStream is = this.getClass().getResourceAsStream("/" + configXml);
            document = saxReader.read(is);
        } catch (DocumentException var28) {
            log.error("读取XML出错!", var28);
            throw new CodegenException("读取XML出错!", var28);
        }

        Element root = document.getRootElement();
        Element templatesEl = root.element("templates");
        String basepath = getRootPath() + "template";
        cm.getClass();
        ConfigModel.Templates templates = new ConfigModel.Templates(cm, basepath);
        cm.setTemplates(templates);
        Iterator j = templatesEl.elementIterator("template");

        String tableNames;
        while(j.hasNext()) {
            Element templateEl = (Element)j.next();
            String id = templateEl.attributeValue("id");
            tableNames = templateEl.attributeValue("path");
            templates.getTemplate().put(id, tableNames);
        }

        Element filesEl = root.element("files");
        String template;
        String sub;
        String extName;
        String genMode;
        String insertTag;
        if (filesEl != null) {
            cm.getClass();
            ConfigModel.Files files = new ConfigModel.Files(cm);
            cm.setFiles(files);
            files.setBaseDir(baseDir);
            j = filesEl.elementIterator("file");

            while(j.hasNext()) {
                Element fileEl = (Element)j.next();
                String refTemplate = fileEl.attributeValue("refTemplate");
                String filename = fileEl.attributeValue("filename");
                String dir = fileEl.attributeValue("dir");
                template = (String)templates.getTemplate().get(refTemplate);
                if (template == null) {
                    throw new CodegenException("没有找到" + refTemplate + "对应的模板!");
                }

                sub = fileEl.attributeValue("sub");
                extName = fileEl.attributeValue("override");
                boolean isOverride = false;
                if (StringUtil.isNotEmpty(extName) && extName.equals("true")) {
                    isOverride = true;
                }

                genMode = fileEl.attributeValue("append");
                if (genMode != null && genMode.toLowerCase().equals("true")) {
                    insertTag = fileEl.attributeValue("insertTag");
                    String startTag = fileEl.attributeValue("startTag");
                    String endTag = fileEl.attributeValue("endTag");
                    if (insertTag == null) {
                        insertTag = "<!--insertbefore-->";
                    }

                    if (StringUtil.isEmpty(startTag)) {
                        startTag = "";
                    }

                    if (StringUtil.isEmpty(endTag)) {
                        endTag = "";
                    }

                    if (sub != null && sub.toLowerCase().equals("true")) {
                        files.addFile(template, filename, dir, true, isOverride, true, insertTag, startTag, endTag);
                    } else {
                        files.addFile(template, filename, dir, false, isOverride, true, insertTag, startTag, endTag);
                    }
                } else if (sub != null && sub.toLowerCase().equals("true")) {
                    files.addFile(template, filename, dir, true, isOverride, false, "", "", "");
                } else {
                    files.addFile(template, filename, dir, false, isOverride, false, "", "", "");
                }
            }
        }

        Iterator i = root.elementIterator("genAll");

        while(i.hasNext()) {
            Element tableEl = (Element)i.next();
            tableNames = tableEl.attributeValue("tableNames");
            cm.getClass();
            ConfigModel.GenAll genAll = new ConfigModel.GenAll(cm, tableNames);
            cm.setGenAll(genAll);
            j = tableEl.elementIterator("file");

            while(j.hasNext()) {
                Element fileEl = (Element)j.next();
                template = fileEl.attributeValue("refTemplate");
                sub = fileEl.attributeValue("filename");
                extName = fileEl.attributeValue("extName");
                String dir = fileEl.attributeValue("dir");
                genMode = fileEl.attributeValue("genMode");
                insertTag = (String)cm.getTemplates().getTemplate().get(template);
                if (insertTag == null) {
                    throw new CodegenException("找不到模板： " + template + "!");
                }

                if ("SingleFile".equals(genMode) && sub == null) {
                    throw new CodegenException("当SingleFile模式时，必须要填filename!");
                }

                if ("MultiFile".equals(genMode) && extName == null) {
                    throw new CodegenException("当MultiFile模式时，必须要填extName!");
                }

                genAll.getClass();
                ConfigModel.GenAll.File file = new ConfigModel.GenAll.File(genAll, insertTag, sub, extName, dir, genMode);
                genAll.getFile().add(file);
                Iterator k = fileEl.elementIterator("variable");

                while(k.hasNext()) {
                    Element variableEl = (Element)k.next();
                    String name = variableEl.attributeValue("name");
                    String value = variableEl.attributeValue("value");
                    file.getVariable().put(name, value);
                }
            }
        }

        return cm;
    }

    private List<TableModel> getTableModelList(IDbHelper dbHelper, ConfigModel configModel) throws CodegenException {
        List<ConfigModel.Table> tables = configModel.getTables();
        List<TableModel> tableModels = new ArrayList();
        Iterator var5 = tables.iterator();

        while(true) {
            while(var5.hasNext()) {
                ConfigModel.Table table = (ConfigModel.Table)var5.next();
                String tbName = table.getTableName();
                TableModel tableModel = dbHelper.getByTable(tbName);
                if (tableModel != null && tableModel.getColumnList() != null && tableModel.getColumnList().size() > 0) {
                    tableModel.setVariables(table.getVariable());
                    tableModel.setSub(false);
                    tableModel.setTableName(tbName.toLowerCase());
                    tableModels.add(tableModel);
                } else {
                    log.error("^^^^^^^^^表【" + tbName + "】不存在请确认！！！^^^^^^^^");
                }
            }

            return tableModels;
        }
    }

    private void genTableByConfig(IDbHelper dbHelper, ConfigModel configModel, Configuration cfg) throws CodegenException {
        try {
            List<TableModel> tableModels = this.getTableModelList(dbHelper, configModel);
            if (tableModels != null && tableModels.size() != 0) {
                ConfigModel.Files files = configModel.getFiles();
                String baseDir = files.getBaseDir();
                List<ConfigModel.Files.File> fileList = files.getFiles();
                if (fileList != null && fileList.size() != 0) {
                    log.info("*********开始生成**********");
                    Iterator var8 = tableModels.iterator();

                    label56:
                    while(var8.hasNext()) {
                        TableModel tableModel = (TableModel)var8.next();
                        String tbName = tableModel.getTableName();
                        Map<String, String> variables = tableModel.getVariables();
                        boolean isSub = tableModel.getSub();
                        Iterator var13 = fileList.iterator();

                        while(true) {
                            ConfigModel.Files.File file;
                            String filename;
                            String startTag;
                            String endTag;
                            boolean sub;
                            boolean override;
                            do {
                                if (!var13.hasNext()) {
                                    continue label56;
                                }

                                file = (ConfigModel.Files.File)var13.next();
                                filename = file.getFilename();
                                startTag = file.getStartTag();
                                endTag = file.getEndTag();
                                sub = file.isSub();
                                override = file.isOverride();
                            } while(isSub && !sub);

                            startTag = StringUtil.replaceVariable(startTag, tbName);
                            endTag = StringUtil.replaceVariable(endTag, tbName);
                            String dir = file.getDir();
                            variables.put("class", StringUtil.underLineToHump(tableModel.getTableName()));
                            filename = StringUtil.replaceVariable(filename, variables);
                            dir = StringUtil.replaceVariable(dir, variables);
                            dir = StringUtil.combilePath(baseDir, dir);
                            Map<String, Object> map = new HashMap();
                            map.put("model", tableModel);
                            map.put("vars", configModel.getVariables());
                            map.put("date", new Date());
                            if (file.getAppend()) {
                                this.appendFile(dir, filename, file.getTemplate(), configModel.getCharset(), cfg, map, file.getInsertTag(), startTag, endTag);
                            } else if (override) {
                                this.genFile(dir, filename, file.getTemplate(), configModel.getCharset(), cfg, map);
                            } else {
                                File f = new File(dir + "\\" + filename);
                                if (!f.exists()) {
                                    this.genFile(dir, filename, file.getTemplate(), configModel.getCharset(), cfg, map);
                                    log.info(dir + "路径下：" + filename + " 生成成功!");
                                } else {
                                    log.info(dir + "路径下：" + filename + " 已存在，停止生成!");
                                }
                            }
                        }
                    }

                    log.info("*********所有文件生成成功!**********");
                } else {
                    log.info("没有指定生成的文件!");
                }
            } else {
                log.info("没有指定生成的表!");
            }
        } catch (IOException var23) {
            throw new CodegenException(var23);
        } catch (TemplateException var24) {
            throw new CodegenException("freemarker执行出错!", var24);
        }
    }

    private void getAllTable(IDbHelper dbHelper, ConfigModel configModel, Configuration cfg) throws CodegenException, IOException, TemplateException {
        ConfigModel.GenAll genAll = configModel.getGenAll();
        if (genAll != null) {
            List<String> tableNames = null;
            if (genAll.getTableNames() == null) {
                tableNames = dbHelper.getAllTable();
            } else {
                tableNames = new ArrayList();
                String[] var6 = genAll.getTableNames().replaceAll(" ", "").split(",");
                int var7 = var6.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    String name = var6[var8];
                    if (name.length() > 0) {
                        ((List)tableNames).add(name);
                    }
                }
            }

            int size = genAll.getFile().size();
            if (size != 0) {
                log.info("----------------生成多表开始------------------");
                Iterator var14 = genAll.getFile().iterator();

                while(true) {
                    while(var14.hasNext()) {
                        ConfigModel.GenAll.File file = (ConfigModel.GenAll.File)var14.next();
                        if ("MultiFile".equals(file.getGenMode())) {
                            Iterator var17 = ((List)tableNames).iterator();

                            while(var17.hasNext()) {
                                String tableName = (String)var17.next();
                                TableModel tableModel = dbHelper.getByTable(tableName);
                                Map<String, Object> map = new HashMap();
                                map.put("model", tableModel);
                                map.put("date", new Date());
                                this.genFile(file.getDir(), tableName + "." + file.getExtName(), file.getTemplate(), configModel.getCharset(), cfg, map);
                                log.info(tableName + "." + file.getExtName() + " 生成成功!");
                            }
                        } else if ("SingleFile".equals(file.getGenMode())) {
                            List<TableModel> models = new ArrayList();
                            Iterator var10 = ((List)tableNames).iterator();

                            while(var10.hasNext()) {
                                String tableName = (String)var10.next();
                                TableModel tableModel = dbHelper.getByTable(tableName);
                                models.add(tableModel);
                            }

                            Map<String, Object> map = new HashMap();
                            map.put("models", models);
                            map.put("date", new Date());
                            this.genFile(file.getDir(), file.getFilename(), file.getTemplate(), configModel.getCharset(), cfg, map);
                            log.info(file.getFilename() + " 生成成功!");
                        }
                    }

                    log.info("----------------生成多表结束------------------");
                    return;
                }
            }
        }
    }

    private void genFile(String fileDir, String fileName, String templatePath, String charset, Configuration cfg, Map data) throws IOException, TemplateException {
        StringUtil.combilePath(fileDir, fileName);
        File newFile = new File(fileDir, fileName);
        if (!newFile.exists()) {
            if (!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }

            newFile.createNewFile();
        }

        Writer writer = new OutputStreamWriter(new FileOutputStream(newFile), charset);
        Template template = cfg.getTemplate(templatePath, charset);
        template.process(data, writer);
    }

    private void appendFile(String fileDir, String fileName, String templatePath, String charset, Configuration cfg, Map data, String insertTag, String startTag, String endTag) throws IOException, TemplateException {
        String path = StringUtil.combilePath(fileDir, fileName);
        File newFile = new File(fileDir, fileName);
        StringWriter out = new StringWriter();
        Template template = cfg.getTemplate(templatePath, charset);
        template.process(data, out);
        String str = out.toString();
        boolean exists = false;
        String content = "";
        if (newFile.exists()) {
            content = FileHelper.readFile(path, charset);
            if (StringUtil.isNotEmpty(startTag) && StringUtil.isNotEmpty(endTag) && StringUtil.isExist(content, startTag, endTag)) {
                content = StringUtil.replace(content, startTag, endTag, str);
                exists = true;
            }
        }

        if (!exists) {
            if (StringUtil.isNotEmpty(startTag) && StringUtil.isNotEmpty(endTag)) {
                str = startTag.trim() + "\r\n" + str + "\r\n" + endTag.trim();
            }

            if (content.indexOf(insertTag) != -1) {
                String[] aryContent = FileHelper.getBySplit(content, insertTag);
                content = aryContent[0] + str + "\r\n" + insertTag + aryContent[1];
            } else {
                content = content + "\r\n" + str;
            }
        }

        FileHelper.writeFile(newFile, charset, content);
    }

    private IDbHelper getDbHelper(ConfigModel configModel) throws CodegenException {
        IDbHelper dbHelper = null;
        String dbHelperClass = configModel.getDatabase().getDbHelperClass();

        try {
            dbHelper = (IDbHelper)Class.forName(dbHelperClass).newInstance();
        } catch (InstantiationException var5) {
            throw new CodegenException("指定的dbHelperClass：" + dbHelperClass + "无法实例化，可能该类是一个抽象类、接口、数组类、基础类型或 void, 或者该类没有默认构造方法!", var5);
        } catch (IllegalAccessException var6) {
            throw new CodegenException("指定的dbHelperClass" + dbHelperClass + "没有默认构造方法或不可访问!", var6);
        } catch (ClassNotFoundException var7) {
            throw new CodegenException("找不到指定的dbHelperClass:" + dbHelperClass + "!", var7);
        }

        dbHelper.setUrl(configModel.getDatabase().getUrl(), configModel.getDatabase().getUsername(), configModel.getDatabase().getPassword());
        return dbHelper;
    }

    public IDbHelper getDbHelper(String dbHelperClass, String url, String userName, String passWord) throws CodegenException {
        IDbHelper dbHelper = null;

        try {
            dbHelper = (IDbHelper)Class.forName(dbHelperClass).newInstance();
        } catch (InstantiationException var7) {
            throw new CodegenException("指定的dbHelperClass：" + dbHelperClass + "无法实例化，可能该类是一个抽象类、接口、数组类、基础类型或 void, 或者该类没有默认构造方法!", var7);
        } catch (IllegalAccessException var8) {
            throw new CodegenException("指定的dbHelperClass" + dbHelperClass + "没有默认构造方法或不可访问!", var8);
        } catch (ClassNotFoundException var9) {
            throw new CodegenException("找不到指定的dbHelperClass:" + dbHelperClass + "!", var9);
        }

        dbHelper.setUrl(url, userName, passWord);
        return dbHelper;
    }

    public void execute(String parentDir, String module, String developer, DataSourceConfig dbConfig, List<String> tableNameList) throws Exception {
        try {
            String baseDir = System.getProperty("user.dir");
            String dir = null;
            String jarWholePath = Codegen.class.getProtectionDomain().getCodeSource().getLocation().getFile();

            try {
                dir = URLDecoder.decode(jarWholePath, "UTF-8");
            } catch (UnsupportedEncodingException var22) {
                log.error("UnsupportedEncodingException", var22);
            }

            setRootPath(dir);
            String configXml = "codegenconfig.xml";
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(configXml);
            if (is == null) {
                throw new CodegenException("请确认配置文件：" + configXml + "是否存在!");
            }

            String projectPath = System.getProperty("user.dir");
            ConfigModel configModel = this.loadXml(configXml, baseDir);
            configModel.getVariables().put("parentDir", parentDir);
            String xmlParentDir = parentDir.replace(".", "/");
            configModel.getVariables().put("xmlParentDir", xmlParentDir);
            configModel.getVariables().put("projectPath", projectPath);
            String javaSubProject = StringUtils.isEmpty(projectPath) ? "" : "." + projectPath;
            configModel.getVariables().put("javaSubProject", javaSubProject);
            configModel.getVariables().put("module", module);
            configModel.getVariables().put("developer", developer);
            configModel.setCharset("utf-8");
            String url = dbConfig.getUrl();
            configModel.getClass();
            configModel.setDatabase(new ConfigModel.Database(configModel, dbConfig.getDbType(), url, dbConfig.getUsername(), dbConfig.getPassword()));
            List<ConfigModel.Table> tables = new ArrayList();
            Iterator var19 = tableNameList.iterator();

            while(var19.hasNext()) {
                String tableName = (String)var19.next();
                configModel.getClass();
                ConfigModel.Table table = new ConfigModel.Table(configModel, tableName);
                table.setVariable(configModel.getVariables());
                tables.add(table);
            }

            configModel.setTables(tables);
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
            cfg.setClassForTemplateLoading(this.getClass(), "/template");
            IDbHelper dbHelper = this.getDbHelper(configModel);
            this.genTableByConfig(dbHelper, configModel, cfg);
        } catch (Exception var23) {
            System.err.println(var23);
            log.error("execute异常", var23);
        }

    }
}
