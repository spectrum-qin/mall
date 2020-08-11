package com.spectrum.mall.orm.db.imp;

import com.spectrum.mall.orm.db.DaoHelper;
import com.spectrum.mall.orm.db.IDbHelper;
import com.spectrum.mall.orm.db.MapCmd;
import com.spectrum.mall.orm.exception.CodegenException;
import com.spectrum.mall.orm.model.ColumnModel;
import com.spectrum.mall.orm.model.TableModel;
import com.spectrum.mall.orm.util.StringUtil;
import com.spectrum.mall.utils.text.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

/**
 * @author oe_qinzuopu
 */
public class OracleHelper implements IDbHelper {
    private String sqlPk = "select column_name from user_constraints c,user_cons_columns col where c.constraint_name=col.constraint_name and c.constraint_type='P' and c.table_name='%s'";
    private String sqlTableComment = "select * from user_tab_comments  where table_type='TABLE' AND table_name ='%s'";
    private String sqlColumn = "select    A.column_name NAME,A.data_type TYPENAME,A.data_length LENGTH,A.data_precision PRECISION,    A.Data_Scale SCALE,A.Data_default, A.NULLABLE, B.comments DESCRIPTION  from  user_tab_columns A,user_col_comments B where a.COLUMN_NAME=b.column_name and    A.Table_Name = B.Table_Name and A.Table_Name='%s' order by A.column_id";
    private String sqlAllTables = "select table_name from user_tables where status='VALID'";
    private String url;
    private String username;
    private String password;

    public OracleHelper() throws CodegenException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException var2) {
            throw new CodegenException("找不到oracle驱动!", var2);
        }
    }

    @Override
    public void setUrl(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private List<ColumnModel> getColumnsByTable(String tableName) throws CodegenException {
        tableName = tableName.toUpperCase();
        DaoHelper<ColumnModel> dao = new DaoHelper(this.url, this.username, this.password);
        String sql = String.format(this.sqlColumn, tableName);
        List<ColumnModel> list = dao.queryForList(sql, new OracleMapCmd());
        return list;
    }

    private String getTableComment(String tableName) throws CodegenException {
        tableName = tableName.toUpperCase();
        String sql = String.format(this.sqlTableComment, tableName);
        DaoHelper<String> dao = new DaoHelper(this.url, this.username, this.password);
        String comment = (String)dao.queryForObject(sql, new MapCmd<String>() {
            @Override
            public String getObjecFromRs(ResultSet rs) throws SQLException {
                return rs.getString("COMMENTS");
            }
        });
        if (comment == null) {
            comment = tableName;
        }

        String[] aryComment = comment.split("\n");
        return aryComment[0];
    }

    private String getPk(String tableName) throws CodegenException {
        tableName = tableName.toUpperCase();
        String sql = String.format(this.sqlPk, tableName);
        DaoHelper<String> dao = new DaoHelper(this.url, this.username, this.password);
        String pk = "";

        try {
            pk = (String)dao.queryForObject(sql, new MapCmd<String>() {
                @Override
                public String getObjecFromRs(ResultSet rs) throws SQLException {
                    return rs.getString("COLUMN_NAME");
                }
            });
            return pk;
        } catch (Exception var6) {
            throw new CodegenException("从表中取得主键出错，请检查表是否设置主键");
        }
    }

    private void setPk(List<ColumnModel> list, String pk) {
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            ColumnModel model = (ColumnModel)var3.next();
            if (pk.toLowerCase().equals(model.getColumnName().toLowerCase())) {
                model.setIsPK(true);
            }
        }

    }

    @Override
    public TableModel getByTable(String tableName) throws CodegenException {
        tableName = tableName.toUpperCase();
        String tabComment = this.getTableComment(tableName);
        String pk = this.getPk(tableName);
        TableModel tableModel = new TableModel();
        tableModel.setTableName(tableName);
        tableModel.setTabComment(tabComment);
        List<ColumnModel> list = this.getColumnsByTable(tableName);
        if (StringUtil.isNotEmpty(pk)) {
            this.setPk(list, pk);
        }

        tableModel.setColumnList(list);
        return tableModel;
    }

    @Override
    public List<String> getAllTable(String tableName) throws CodegenException {
        DaoHelper<String> dao = new DaoHelper(this.url, this.username, this.password);
        if (!StringUtils.isEmpty(tableName)) {
            this.sqlAllTables = this.sqlAllTables + " AND Table_Name like '%" + tableName + "%'";
        }

        return dao.queryForList(this.sqlAllTables, new MapCmd<String>() {
            @Override
            public String getObjecFromRs(ResultSet rs) throws SQLException {
                return rs.getString("TABLE_NAME");
            }
        });
    }

    @Override
    public List<String> getAllTable() throws CodegenException {
        return this.getAllTable((String)null);
    }
}
