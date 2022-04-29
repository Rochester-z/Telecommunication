package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


/**
 * 释放资源
 *
 */
public class JDBCUtil {
    static String driverClass = null;
    static String url = "null";
    static String name = "null";
    static String password = "null";
    static {

        try {
            //1.创建一个属性配置对象
            Properties properties = new Properties();
//            InputStream is = new FileInputStream("jdbc.properties");  //错误,对应文件位于工程根目录

            //把资源变成输入流
            InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");  //,对应文件位于src目录

            //使用classloader类加载器,加载bin文件夹下字节码时顺便加载资源
            //src中放源文件,编译好的class文件放bin文件夹下
            //jdbc.property放在src下,有Bin且下面有propertty,放根目录下,Bin下无property

            //导入输入流
            properties.load(is);  //加载某一段流,转化为keyvalue(property可读取的文件)
            //读取属性,需要将property和配置文件关联
            driverClass = properties.getProperty("driverClass");  //驱动类的名字
            url = properties.getProperty("url");
            name = properties.getProperty("name");
            password = properties.getProperty("password");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void release(Connection conn, Statement st, ResultSet rs){
        closeRs(rs);
        closeSt(st);
        closeConn(conn);

    }
    public static void release(Connection conn, Statement st){
        closeSt(st);
        closeConn(conn);

    }
    public static Connection getConn() {
        Connection conn = null;
        //1.注册驱动
        //静态代码块,类加载了,就执行. DriverManager.registerDriver(new Driver());
        //通过这个方法能把类的字节码加入JVM中
        /**
         * 获取连接对象
         */
        try {
            System.out.println("driverClass"+driverClass);

            //可以不注册驱动
//            Class.forName(driverClass);  //forname中放类的全路径地址 抓异常,万一找不到类


            //2.建立连接,IDEA怎么没有提示信息

//            DriverManager.getConnection("jdbc:mysql://localhost/comzq0914.JAVA_DAO.test?" +
//                    "user=monty&password=greatsqldb");
            //ctrl+F12查看类的方法
            conn = DriverManager.getConnection(url, name, password);
            //url 数据库地址 name:mysql名 password:mysql密码
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //协议 子协议  访问本地主机  数据库
        return conn;

    }


    private static void closeRs(ResultSet rs){
        try {
            //释放资源先要判空
            if(rs != null) {
                rs.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            rs = null;
        }
    }

    private static void closeSt(Statement st){
        try {
            //释放资源先要判空
            if(st != null) {
                st.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            st = null;
        }
    }

    private static void closeConn(Connection conn){
        try {
            //释放资源先要判空
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            conn = null;
        }
    }
}
