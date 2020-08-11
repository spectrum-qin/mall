package com.spectrum.mall.orm.db.imp;

import com.spectrum.mall.orm.db.MapCmd;
import com.spectrum.mall.orm.model.ColumnModel;
import com.spectrum.mall.orm.util.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlMapCmd implements MapCmd<ColumnModel> {
    public MySqlMapCmd() {
    }

    @Override
    public ColumnModel getObjecFromRs(ResultSet rs) throws SQLException {
        ColumnModel model = new ColumnModel();
        String colName = rs.getString("column_name");
        String humpColName = StringUtil.underLineToHump(colName.toLowerCase());
        String column_key = rs.getString("column_key");
        long character_length = rs.getLong("character_octet_length");
        String nullable = rs.getString("is_nullable");
        boolean is_nullable = nullable.equals("YES");
        int precision = rs.getInt("numeric_precision");
        int scale = rs.getInt("NUMERIC_scale");
        String comment = rs.getString("column_comment");
        comment = StringUtil.isEmpty(comment) ? colName : comment;
        boolean isPk = column_key.equals("PRI");
        String data_type = rs.getString("data_type");
        String javaType = this.getJavaType(data_type, precision, scale);
        String displayDbType = this.getDisplayDbType(data_type, character_length, precision, scale);
        String[] aryComment = comment.split("\n");
        model.setColumnName(colName);
        model.setHumpColumnName(humpColName);
        model.setIsNotNull(!is_nullable);
        model.setPrecision(precision);
        model.setScale(scale);
        model.setLength(character_length);
        model.setComment(aryComment[0]);
        model.setIsPK(isPk);
        model.setColDbType(data_type);
        model.setColType(javaType);
        model.setDisplayDbType(displayDbType);
        return model;
    }

    private String getDisplayDbType(String dbtype, long character_length, int precision, int scale) {
        if (dbtype.equals("varchar")) {
            return "varchar(" + character_length + ")";
        } else {
            return dbtype.equals("decimal") ? "decimal(" + precision + "," + scale + ")" : dbtype;
        }
    }

    private String getJavaType(String dbtype, int precision, int scale) {
        if (dbtype.equals("bigint")) {
            return "Long";
        } else if (dbtype.equals("int")) {
            return "Integer";
        } else if (!dbtype.equals("tinyint") && !dbtype.equals("smallint")) {
            if (!dbtype.equals("varchar") && !dbtype.endsWith("text") && !dbtype.equals("char")) {
                if (dbtype.equals("double")) {
                    return "Double";
                } else if (dbtype.equals("float")) {
                    return "Float";
                } else if (dbtype.endsWith("blob")) {
                    return "byte[]";
                } else if (dbtype.equals("decimal")) {
                    if (scale == 0) {
                        return precision <= 10 ? "Integer" : "Long";
                    } else {
                        return "BigDecimal";
                    }
                } else {
                    return dbtype.startsWith("date") ? "java.util.Date" : dbtype;
                }
            } else {
                return "String";
            }
        } else {
            return "Short";
        }
    }
}
