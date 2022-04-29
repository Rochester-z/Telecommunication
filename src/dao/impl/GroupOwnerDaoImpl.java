package dao.impl;

import entity.GroupOwner;
import dao.GroupOwnerDao;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupOwnerDaoImpl implements GroupOwnerDao {

    /**根据go_id查询对应的行*/
    @Override
    public GroupOwner getByGoId(String go_id) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;


        try {
            conn = JDBCUtil.getConn();
            st = conn.createStatement();

            String sql = "select * from groups_owner where go_id = " + Integer.valueOf(go_id);
            rs = st.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return assembleGroupOwner(rs);
    }


    /**根据用户id查询对应所有的行*/
    @Override
    public List<GroupOwner> getListByOwnid(String ownid) {
        List<GroupOwner> list = new ArrayList<>();
        //模板代码
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            //1.获取连接对象
            conn = JDBCUtil.getConn();

            //2.创建statement对象
            st = conn.createStatement();


            //根据分组id和用户id查询GroupMembers对应所有行
            String sql = "select * from group_owner where own_id = " + Integer.valueOf(ownid);
            rs = st.executeQuery(sql);

            System.out.println("正常启动了");
            while (null != rs && rs.next()){
                System.out.println("正常启动了");
                //遍历rs
                String go_id = String.valueOf(rs.getInt("go_id"));
                String own_id = String.valueOf(rs.getInt("own_id"));
                String g_id = rs.getString("g_id");
                GroupOwner groups = new GroupOwner(go_id,own_id,g_id);
                list.add(groups);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.release(conn,st,rs);
        }
        return list;
    }


    // 组装GroupOwner对象
    private GroupOwner assembleGroupOwner(ResultSet result) {
        try {
            if (null != result && result.next()) {
                String go_id = String.valueOf(result.getInt("go_id"));
                String own_id = String.valueOf(result.getInt("own_id"));
                String g_id = String.valueOf(result.getInt("g_id"));
                return new GroupOwner(go_id, own_id, g_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
