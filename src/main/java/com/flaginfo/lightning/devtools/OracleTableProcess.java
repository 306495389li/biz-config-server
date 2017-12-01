package com.flaginfo.lightning.devtools;

import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class OracleTableProcess {
    public static OracleTableEntity getTableInf(String tableName) {
        String queryTableSql = "SELECT table_name FROM user_tables WHERE table_name='" + tableName + "'";// SQL语句
        String queryColumnSql = "select tc.COLUMN_NAME,tc.DATA_DEFAULT,tc.NULLABLE,tc.DATA_TYPE,tc.DATA_LENGTH,tc.DATA_PRECISION,tc.DATA_SCALE,css.CONSTRAINT_TYPE,ccs.COMMENTS from user_tab_cols tc left join user_cons_columns cc on tc.TABLE_NAME=cc.TABLE_NAME and tc.COLUMN_NAME=cc.COLUMN_NAME and cc.POSITION is not null left join user_constraints css on css.CONSTRAINT_NAME=cc.CONSTRAINT_NAME and css.TABLE_NAME=cc.TABLE_NAME left join user_col_comments ccs on ccs.TABLE_NAME=tc.TABLE_NAME and ccs.COLUMN_NAME=tc.COLUMN_NAME where tc.TABLE_NAME='" + tableName + "'";
        OracleTableEntity tableEntity = new OracleTableEntity();

        OracleDBHelper db1 = new OracleDBHelper();// 创建DBHelper对象

        try {
            db1.setSql(queryTableSql);

            ResultSet ret = db1.pst.executeQuery();// 执行语句，得到结果集
            if (ret.next()) {
                System.out.println(tableName + " is exist!!!");
                tableEntity.setTableName(tableName);
                System.out.println("tableName is " + tableEntity.getTableName());
                System.out.println("entityName is " + tableEntity.getEntityName());
            } else {
                ret.close();
                db1.close();// 关闭连接
                throw new Exception(tableName + "is not exist!!");
            }

            db1.setSql(queryColumnSql);
            ret = db1.pst.executeQuery();// 执行语句，得到结果集
            List<OracleColumnEntity> columns = new ArrayList<OracleColumnEntity>();
            while (ret.next()) {
                OracleColumnEntity columnEntity = new OracleColumnEntity();
                columnEntity.setColumnName(ret.getString("COLUMN_NAME"));
                columnEntity.setDefaultValue(ret.getString("DATA_DEFAULT"));
                columnEntity.setNullable(ret.getString("NULLABLE"));
                columnEntity.setDatatype(ret.getString("DATA_TYPE"));
                columnEntity.setCharMaxLength(ret.getInt("DATA_LENGTH"));
                columnEntity.setNumericPricision(ret.getInt("DATA_PRECISION"));
                columnEntity.setNumericScale(ret.getInt("DATA_SCALE"));
                columnEntity.setColumnKey(ret.getString("CONSTRAINT_TYPE"));
                columnEntity.setPri("P".equals(ret.getString("CONSTRAINT_TYPE")));
                columnEntity.setComm(ret.getString("COMMENTS"));
                columns.add(columnEntity);
            }
            tableEntity.setColumns(columns);
            ret.close();
            db1.close();// 关闭连接

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tableEntity;
    }
    
}
