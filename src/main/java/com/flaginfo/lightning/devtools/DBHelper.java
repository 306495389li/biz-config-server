package com.flaginfo.lightning.devtools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBHelper {
    public static final String url = "jdbc:mysql://127.0.0.1/boost";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "boost";
    public static final String password = "boost";

    public Connection conn = null;
    public PreparedStatement pst = null;
    private String sql;

    public DBHelper() {
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
