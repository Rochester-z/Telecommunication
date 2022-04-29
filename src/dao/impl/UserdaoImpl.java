package dao.impl;

import entity.User;
import dao.UserDao;
import util.JDBCUtil;

import java.sql.*;

public class UserdaoImpl implements UserDao {

    /**
     * 通过账号密码登录，返回user对象
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {//实现时自动传入参数
        //模板代码
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            //1.获取连接对象
            conn = JDBCUtil.getConn();

            //2.创建preparestatement对象

            //区别,先写sql语句,再创建prestatement对象同时检查sql语句 ?对应的内容,后面不管传递什么进来,都把它看成是字符串
            String sql = "select * from user where username = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            //?对应的索引从1开始
            System.out.println("ps"+ps);
            ps.setString(1,username);       //?对应内容
            ps.setString(2,password);
            rs = ps.executeQuery();

            //"和"匹配

            //区别,先创建st对象,在写sql语句,没有检查sql语句
//            st = conn.createStatement();
//            String sql2 = "select * from t_user where username = '"+username+"'and password = '"+password+"'";
//            rs = st.executeQuery(sql);
            return assembleUser(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
//            JDBCUtil.release(conn,st,rs);
        }
        return null;
    }


    /**
     * 通过查询uid返回user对象
     * @param uid
     * @return
     */
    @Override
    public User findUserByUid(String uid) {
        //模板代码
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            //1.获取连接对象
            conn = JDBCUtil.getConn();

            //2.创建statement对象
            st = conn.createStatement();

            String sql = "select * from user where uid = " + Integer.valueOf(uid);
            rs = st.executeQuery(sql);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
//            JDBCUtil.release(conn,st,rs);
        }
        return assembleUser(rs);
    }



    /**
     * 通过username查询user对象
     * @param username
     * @return
     */
    @Override
    public User getByUsername(String username) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;


        try {
            conn = JDBCUtil.getConn();
            st = conn.createStatement();

            String sql = "select * from user where username = " + Integer.valueOf(username);

            rs = st.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return assembleUser(rs);
    }


    /**
     * 通过昵称查询user对象
     * @param nickname
     * @return
     */
    @Override
    public User getByNickname(String nickname) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;


        try {
            conn = JDBCUtil.getConn();
            st = conn.createStatement();

            String sql = "select * from user where nickname = " + Integer.valueOf(nickname);

            rs = st.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return assembleUser(rs);
    }


    // 组装User对象
    private User assembleUser(ResultSet result) {
        try {
            if (null != result && result.next()) {
                String uid = String.valueOf(result.getInt("uid"));
                String username = String.valueOf(result.getInt("username"));
                String password = result.getString("password");
                String nickname = result.getString("nickname");
                String signature = result.getString("signature");
                String profile = result.getString("profile");
                return new User(uid, username, password, nickname, signature,profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
