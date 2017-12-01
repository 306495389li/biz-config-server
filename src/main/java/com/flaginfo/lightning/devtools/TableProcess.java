package com.flaginfo.lightning.devtools;

import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class TableProcess {
    public static MysqlTableEntity getTableInf(String tableName) {

        String queryTableSql = "select table_name from information_schema.`TABLES` t where t.TABLE_SCHEMA = 'boost' and t.table_name='" + tableName + "'; ";// SQL语句
        String queryColumnSql = "select t.COLUMN_NAME,t.COLUMN_DEFAULT,t.IS_NULLABLE,t.DATA_TYPE,t.CHARACTER_MAXIMUM_LENGTH,t.NUMERIC_PRECISION,t.NUMERIC_SCALE,t.COLUMN_KEY,t.COLUMN_COMMENT from information_schema.columns  t where t.TABLE_SCHEMA = 'boost' and t.table_name='" + tableName + "'; ";
        MysqlTableEntity tableEntity = new MysqlTableEntity();

        DBHelper db1 = new DBHelper();// 创建DBHelper对象

        try {
            db1.setSql(queryTableSql);

            ResultSet ret = db1.pst.executeQuery();// 执行语句，得到结果集
            if (ret.next()) {
                System.out.println(tableName + "is exist!!!");
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
            List<MysqlColumnEntity> columns = new ArrayList<MysqlColumnEntity>();
            while (ret.next()) {
                MysqlColumnEntity columnEntity = new MysqlColumnEntity();
                columnEntity.setColumnName(ret.getString("COLUMN_NAME"));
                columnEntity.setDefaultValue(ret.getString("COLUMN_DEFAULT"));
                columnEntity.setNullable(ret.getString("IS_NULLABLE"));
                columnEntity.setCharMaxLength(ret.getInt("CHARACTER_MAXIMUM_LENGTH"));
                columnEntity.setNumericPricision(ret.getInt("NUMERIC_PRECISION"));
                columnEntity.setNumericScale(ret.getInt("NUMERIC_SCALE"));
                columnEntity.setPri(ret.getString("COLUMN_KEY").equals("PRI"));
                columnEntity.setComm(ret.getString("COLUMN_COMMENT"));
                columnEntity.setDatatype(ret.getString("DATA_TYPE"));
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
