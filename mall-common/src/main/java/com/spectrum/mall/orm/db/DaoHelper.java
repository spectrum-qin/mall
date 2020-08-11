package com.spectrum.mall.orm.db;

import com.spectrum.mall.orm.exception.CodegenException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oe_qinzuopu
 */
public class DaoHelper<T> {
    private String url = "";
    private String userName = "";
    private String pwd = "";

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public DaoHelper(String url, String username, String password) {
        this.url = url;
        this.userName = username;
        this.pwd = password;
    }

    public T queryForObject(String sql, MapCmd<T> cmd) throws CodegenException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        Object var6;
        try {
            conn = DriverManager.getConnection(this.url, this.userName, this.pwd);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                System.out.println("没有到数据:" + sql);
                var6 = null;
                return (T) var6;
            }

            var6 = cmd.getObjecFromRs(rs);
        } catch (SQLException var16) {
            throw new CodegenException(var16);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var15) {
                throw new CodegenException(var15);
            }

        }

        return (T) var6;
    }

    public List<T> queryForList(String sql, MapCmd<T> cmd) throws CodegenException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList list = new ArrayList();

        try {
            conn = DriverManager.getConnection(this.url, this.userName, this.pwd);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                list.add(cmd.getObjecFromRs(rs));
            }
        } catch (SQLException var15) {
            throw new CodegenException(var15);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }

                if (stmt != null) {
                    stmt.close();
                }

                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException var14) {
                throw new CodegenException(var14);
            }

        }

        return list;
    }
}
