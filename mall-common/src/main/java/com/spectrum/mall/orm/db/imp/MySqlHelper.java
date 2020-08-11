package com.spectrum.mall.orm.db.imp;

import com.spectrum.mall.orm.db.DaoHelper;
import com.spectrum.mall.orm.db.IDbHelper;
import com.spectrum.mall.orm.db.MapCmd;
import com.spectrum.mall.orm.exception.CodegenException;
import com.spectrum.mall.orm.model.ColumnModel;
import com.spectrum.mall.orm.model.TableModel;
import com.spectrum.mall.orm.util.StringUtil;
import com.spectrum.mall.utils.text.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author oe_qinzuopu
 */
public class MySqlHelper implements IDbHelper {
    private static final Logger log = LoggerFactory.getLogger(MySqlHelper.class);
    String sqlColumns = "select * from information_schema.columns where table_schema=DATABASE() and table_name='%s' ";
    String sqlComment = "select table_name,table_comment  from information_schema.tables t where t.table_schema=DATABASE() and table_name='%s' ";
    String sqlAllTable = "select table_name,table_comment from information_schema.tables t where t.table_schema=DATABASE()";
    private String url = "";
    private String username = "";
    private String password = "";

    public MySqlHelper() throws CodegenException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException var2) {
            throw new CodegenException("找不到mysql驱动!", var2);
        }
    }

    @Override
    public void setUrl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public TableModel getByTable(String tableName) throws CodegenException {
        TableModel tableModel = this.getTableModel(tableName);
        List<ColumnModel> colList = this.getColumnsByTable(tableName);
        tableModel.setColumnList(colList);
        return tableModel;
    }

    private List<ColumnModel> getColumnsByTable(String tableName) throws CodegenException {
        DaoHelper<ColumnModel> dao = new DaoHelper(this.url, this.username, this.password);
        String sql = String.format(this.sqlColumns, tableName);
        List<ColumnModel> list = dao.queryForList(sql, new MySqlMapCmd());
        return list;
    }

    private TableModel getTableModel(String tableName) throws CodegenException {
        TableModel tablemodel = new TableModel();
        DaoHelper<String> dao = new DaoHelper(this.url, this.username, this.password);
        log.info(this.url);
        String sql = String.format(this.sqlComment, tableName);
        log.info(sql);
        String comment = (String)dao.queryForObject(sql, new MapCmd<String>() {
            @Override
            public String getObjecFromRs(ResultSet rs) throws SQLException {
                String comment = rs.getString("table_comment");
                return comment;
            }
        });
        tablemodel.setTableName(tableName);
        if (StringUtil.isEmpty(comment)) {
            comment = tableName;
        }

        if (comment.startsWith("InnoDB free")) {
            tablemodel.setTabComment(tableName);
        } else {
            if (comment.indexOf(";") != -1) {
                int pos = comment.indexOf(";");
                comment = comment.substring(0, pos);
            }

            String[] aryComment = comment.split("\n");
            tablemodel.setTabComment(aryComment[0]);
        }

        return tablemodel;
    }

    @Override
    public List<String> getAllTable(String tableName) throws CodegenException {
        DaoHelper<String> dao = new DaoHelper(this.url, this.username, this.password);
        if (!StringUtils.isEmpty(tableName)) {
            this.sqlAllTable = this.sqlAllTable + " AND  table_name like '%" + tableName + "%'";
        }

        List<String> list = dao.queryForList(this.sqlAllTable, new MapCmd<String>() {
            public String getObjecFromRs(ResultSet rs) throws SQLException {
                return rs.getString("table_name");
            }
        });
        return list;
    }

    @Override
    public List<String> getAllTable() throws CodegenException {
        return this.getAllTable((String)null);
    }
}

