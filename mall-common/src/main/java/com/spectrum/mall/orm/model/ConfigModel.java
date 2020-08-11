package com.spectrum.mall.orm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author oe_qinzuopu
 */
public class ConfigModel {
    private String charset;
    private ConfigModel.Database database;
    private Map<String, String> variables = new HashMap();
    private ConfigModel.Templates templates;
    private List<ConfigModel.Table> tables = new ArrayList();
    private ConfigModel.Files files = new ConfigModel.Files();
    private ConfigModel.GenAll genAll;

    public ConfigModel() {
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public ConfigModel.Database getDatabase() {
        return this.database;
    }

    public void setDatabase(ConfigModel.Database database) {
        this.database = database;
    }

    public Map<String, String> getVariables() {
        return this.variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public ConfigModel.Templates getTemplates() {
        return this.templates;
    }

    public void setTemplates(ConfigModel.Templates templates) {
        this.templates = templates;
    }

    public List<ConfigModel.Table> getTables() {
        return this.tables;
    }

    public void setTables(List<ConfigModel.Table> tables) {
        this.tables = tables;
    }

    public ConfigModel.Files getFiles() {
        return this.files;
    }

    public void setFiles(ConfigModel.Files files) {
        this.files = files;
    }

    public ConfigModel.GenAll getGenAll() {
        return this.genAll;
    }

    public void setGenAll(ConfigModel.GenAll genAll) {
        this.genAll = genAll;
    }

    public static class GenAll {
        private String tableNames;
        private List<ConfigModel.GenAll.File> file = new ArrayList();

        public GenAll(ConfigModel cm, String tableNames) {
            this.tableNames = tableNames;
        }

        public String getTableNames() {
            return this.tableNames;
        }

        public void setTableNames(String tableNames) {
            this.tableNames = tableNames;
        }

        public List<ConfigModel.GenAll.File> getFile() {
            return this.file;
        }

        public void setFile(List<ConfigModel.GenAll.File> file) {
            this.file = file;
        }

        public static class File {
            private String template;
            private String filename;
            private String extName;
            private String dir;
            private String genMode;
            private String sub;
            private Map<String, String> variable = new HashMap();

            public File(GenAll genAll, String template, String filename, String extName, String dir, String genMode) {
                this.template = template;
                this.filename = filename;
                this.extName = extName;
                this.dir = dir;
                this.genMode = genMode;
            }

            public String getSub() {
                return this.sub;
            }

            public void setSub(String sub) {
                this.sub = sub;
            }

            public String getTemplate() {
                return this.template;
            }

            public void setTemplate(String template) {
                this.template = template;
            }

            public String getFilename() {
                return this.filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getExtName() {
                return this.extName;
            }

            public void setExtName(String extName) {
                this.extName = extName;
            }

            public String getDir() {
                return this.dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getGenMode() {
                return this.genMode;
            }

            public void setGenMode(String genMode) {
                this.genMode = genMode;
            }

            public Map<String, String> getVariable() {
                return this.variable;
            }

            public void setVariable(Map<String, String> variable) {
                this.variable = variable;
            }
        }
    }

    public static class Table {
        private String tableName;
        private Map<String, String> variable = new HashMap();
        private List<ConfigModel.Table.SubTable> subtable = new ArrayList();

        public Table(ConfigModel configModel, String tableName) {
            this.tableName = tableName;
        }

        public String getTableName() {
            return this.tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public void addSubTable(String tableName, String foreignKey, Map<String, String> vars) {
            ConfigModel.Table.SubTable sb = new ConfigModel.Table.SubTable(tableName, foreignKey, vars);
            this.subtable.add(sb);
        }

        public Map<String, String> getVariable() {
            return this.variable;
        }

        public void setVariable(Map<String, String> variable) {
            this.variable = variable;
        }

        public List<ConfigModel.Table.SubTable> getSubtable() {
            return this.subtable;
        }

        public void setSubtable(List<ConfigModel.Table.SubTable> subtable) {
            this.subtable = subtable;
        }

        public class SubTable {
            private String tableName;
            private String foreignKey;
            private Map<String, String> vars = new HashMap();

            public SubTable(String tableName, String foreignKey, Map<String, String> vars) {
                this.tableName = tableName;
                this.foreignKey = foreignKey;
                this.vars = vars;
            }

            public String getTableName() {
                return this.tableName;
            }

            public void setTableName(String tableName) {
                this.tableName = tableName;
            }

            public String getForeignKey() {
                return this.foreignKey;
            }

            public void setForeignKey(String foreignKey) {
                this.foreignKey = foreignKey;
            }

            public Map<String, String> getVars() {
                return this.vars;
            }

            public void setVars(Map<String, String> vars) {
                this.vars = vars;
            }
        }
    }

    public static class Files {
        private String baseDir = "";
        private List<ConfigModel.Files.File> files = new ArrayList();

        public Files(ConfigModel cm) {
        }

        public Files() {

        }

        public String getBaseDir() {
            return this.baseDir;
        }

        public void setBaseDir(String baseDir) {
            this.baseDir = baseDir;
        }

        public void addFile(String template, String fileName, String dir, boolean sub, boolean override, boolean append, String insertTag, String startTag, String endTag) {
            ConfigModel.Files.File file = new ConfigModel.Files.File(template, fileName, dir, sub, override, append, insertTag, startTag, endTag);
            this.files.add(file);
        }

        public List<ConfigModel.Files.File> getFiles() {
            return this.files;
        }

        public void setFiles(List<ConfigModel.Files.File> file) {
            this.files = file;
        }

        public class File {
            private String template;
            private String filename;
            private String dir;
            private boolean sub = false;
            private boolean override = false;
            private boolean append = false;
            private String insertTag = "";
            private String startTag = "start{tabname}";
            private String endTag = "end{tabname}";

            public File(String template, String filename, String dir, boolean sub, boolean override, boolean append, String insertTag, String startTag, String endTag) {
                this.template = template;
                this.filename = filename;
                this.dir = dir;
                this.sub = sub;
                this.append = append;
                this.insertTag = insertTag;
                this.startTag = startTag;
                this.endTag = endTag;
                this.override = override;
            }

            public String getTemplate() {
                return this.template;
            }

            public void setTemplate(String template) {
                this.template = template;
            }

            public boolean isSub() {
                return this.sub;
            }

            public void setSub(boolean sub) {
                this.sub = sub;
            }

            public boolean isOverride() {
                return this.override;
            }

            public void setOverride(boolean sub) {
                this.override = this.override;
            }

            public String getFilename() {
                return this.filename;
            }

            public void setFilename(String filename) {
                this.filename = filename;
            }

            public String getDir() {
                return this.dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public boolean getAppend() {
                return this.append;
            }

            public void setAppend(boolean append) {
                this.append = append;
            }

            public String getInsertTag() {
                return this.insertTag;
            }

            public void setInsertTag(String insertTag) {
                this.insertTag = insertTag;
            }

            public String getStartTag() {
                return this.startTag;
            }

            public void setStartTag(String startTag) {
                this.startTag = startTag;
            }

            public String getEndTag() {
                return this.endTag;
            }

            public void setEndTag(String endTag) {
                this.endTag = endTag;
            }
        }
    }

    public static class Templates {
        private String basepath;
        private Map<String, String> template = new HashMap();

        public Templates(ConfigModel cm, String basepath) {
            this.basepath = basepath;
        }

        public String getBasepath() {
            return this.basepath;
        }

        public void setBasepath(String basepath) {
            this.basepath = basepath;
        }

        public Map<String, String> getTemplate() {
            return this.template;
        }

        public void setTemplate(Map<String, String> template) {
            this.template = template;
        }
    }

    public static class Database {
        private String dbHelperClass = "com.spectrum.mall.orm.db.impl.OracleHelper";
        private String url;
        private String username;
        private String password;

        public Database(ConfigModel configModel, String dbType, String url, String username, String password) {
            if ("mysql".equals(dbType)) {
                this.dbHelperClass = "com.spectrum.mall.orm.db.imp.MySqlHelper";
            }

            this.url = url;
            this.username = username;
            this.password = password;
        }

        public String getDbHelperClass() {
            return this.dbHelperClass;
        }

        public void setDbHelperClass(String dbHelperClass) {
            this.dbHelperClass = dbHelperClass;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
