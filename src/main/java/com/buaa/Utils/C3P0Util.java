package com.buaa.Utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0Util {
    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    public static DataSource getDateSource()
    {
        return dataSource;
    }

    /*
    该函数尚未用到
     */
    public static Connection getConnection()
    {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  conn;
    }
}
