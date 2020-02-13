package com.jasu.classloader;

import java.sql.*;

/*****************************************
 * @author: Jasu Wong
 * @Date: 2020-01-15 22:52
 *****************************************/
public class BreakParentLoad {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("...");
    }
}
