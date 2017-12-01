package com.flaginfo.lightning.devtools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class OracleDBHelper {
    public static final String url = "jdbc:oracle:thin:@10.0.0.100:1521/nation";
    public static final String name = "oracle.jdbc.driver.OracleDriver";
    public static final String user = "im_crm_0324";
    public static final String password = "im_crm_0324";

    public Connection conn = null;
    public PreparedStatement pst = null;
    private String sql;

    public OracleDBHelper() {
        try {
            Class.forName(name);// 指定连接类型
            conn = DriverManager.getConnection(url, user, password);// 获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) throws SQLException {
        this.sql = sql;
        pst = conn.prepareStatement(this.sql);// 准备执行语句
        // System.out.println(sql);
    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
