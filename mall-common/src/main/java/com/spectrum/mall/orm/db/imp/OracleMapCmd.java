package com.spectrum.mall.orm.db.imp;

import com.spectrum.mall.orm.db.MapCmd;
import com.spectrum.mall.orm.model.ColumnModel;
import com.spectrum.mall.orm.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author oe_qinzuopu
 */
public class OracleMapCmd implements MapCmd<ColumnModel> {
    public OracleMapCmd() {
    }

    public ColumnModel getObjecFromRs(ResultSet rs) throws SQLException {
        ColumnModel model = new ColumnModel();
        String name = rs.getString("NAME");
        String humpColName = StringUtil.underLineToHump(name.toLowerCase());
        String typename = rs.getString("TYPENAME");
        int length = Integer.parseInt(rs.getString("LENGTH"));
        int precision = rs.getInt("PRECISION");
        int scale = rs.getInt("SCALE");
        String description = rs.getString("DESCRIPTION");
        description = description == null ? name : description;
        String NULLABLE = rs.getString("NULLABLE");
        String displayDbType = this.getDisplayDbType(typename, (long)length, precision, scale);
        String javaType = this.getJavaType(typename, precision, scale);
        boolean isNotNull = "N".equals(NULLABLE);
        model.setColumnName(name);
        model.setHumpColumnName(humpColName);
        model.setColDbType(typename);
        model.setComment(description);
        model.setIsNotNull(isNotNull);
        model.setLength((long)length);
        model.setPrecision(precision);
        model.setScale(scale);
        model.setDisplayDbType(displayDbType);
        model.setColType(javaType);
        return model;
    }

    private String getDisplayDbType(String dbtype, long character_length, int precision, int scale) {
        if (!dbtype.equals("CHAR") && !dbtype.equals("VARCHAR2")) {
            if (dbtype.equals("NVARCHAR2")) {
                return "NVARCHAR2(" + character_length / 2L + ")";
            } else if (dbtype.equals("NUMBER")) {
                return scale == 0 && precision > 0 ? "NUMBER(" + precision + ")" : "NUMBER(" + precision + "," + scale + ")";
            } else {
                return dbtype;
            }
        } else {
            return dbtype + "(" + character_length + ")";
        }
    }

    private String getJavaType(String dbtype, int precision, int scale) {
        if (dbtype.equals("BLOB")) {
            return "byte[]";
        } else if (dbtype.indexOf("CHAR") == -1 && dbtype.indexOf("CLOB") == -1) {
            if (!dbtype.equals("DATE") && dbtype.indexOf("TIMESTAMP") == -1) {
                if (dbtype.equals("NUMBER")) {
                    if (scale > 0) {
                        return "Float";
                    } else {
                        return precision < 10 ? "Integer" : "Long";
                    }
                } else {
                    return "String";
                }
            } else {
                return "java.util.Date";
            }
        } else {
            return "String";
        }
    }
}
