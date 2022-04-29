package dao.impl;

import entity.GroupMembers;
import dao.GroupMembersDao;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//1.通过friend表可以查询c_id=1的所有好友和对应的关系，建立一个新表
//            String sql1 = "select f_uid, g_id from friend where c_uid =" + c_uid + "group by f_uid";
//            rs = st.executeQuery(sql);
   /*
    根据c_uid查询所有的f_uid，在通过friend表的外键f_uid查询user表的主键uid，获取uid对应的信息
    根据friend表c_uid和f_uid对应的外键g_id查询group表的主键g_id，获取对应的group_name关系
   */

/*
    根据c_uid查询所有的f_uid，在通过friend表的外键f_uid查询user表的主键uid，获取uid对应的信息
     */
public class GroupMembersDaoImpl implements GroupMembersDao {

    /**
     * 根据分组id查询GroMembers对应的所有行
     * @param gid 分组id
     * @return
     */
    @Override
    public List<GroupMembers> getListByGid(String gid) {
        List<GroupMembers> list = new ArrayList<>();
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
            String sql = "select * from group_members where g_id = " + Integer.valueOf(gid);
            rs = st.executeQuery(sql);

            while (null != null && rs.next()){
                //遍历rs
                String gm_id = String.valueOf(rs.getInt("gm_id"));
                String g_id = String.valueOf(rs.getInt("g_id"));
                String own_id = rs.getString("own_id");
                String mem_id = rs.getString("mem_id");
                GroupMembers gm = new GroupMembers(gm_id,g_id,own_id,mem_id);
                list.add(gm);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.release(conn,st,rs);
        }
        return list;
    }


    /**
     *  根据分组id和用户id查询GroupMembers对应所有行
     * @param gid 分组id
     * @param ownid 当前用户id
     * @return
     */
    @Override
    public List<GroupMembers> getListByGidAndOwnid(String gid, String ownid) {
        List<GroupMembers> list = new ArrayList<>();
        //模板代码
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            //1.获取连接对象
            conn = JDBCUtil.getConn();

            //2.创建statement对象
            st = conn.createStatement();

            System.out.println("g_id"+gid+"own_id"+ownid);
            //根据分组id和用户id查询GroupMembers对应所有行  //多表查询返回bean对象
            String sql = "select * from group_members where g_id = "  + Integer.valueOf(gid) + " and own_id = " +Integer.valueOf(ownid);
            rs = st.executeQuery(sql);

            while (null != rs && rs.next()){
                //遍历rs
                String gm_id = String.valueOf(rs.getInt("gm_id"));
                String g_id = String.valueOf(rs.getInt("g_id"));
                String own_id = rs.getString("own_id");
                String mem_id = rs.getString("mem_id");
                GroupMembers gm = new GroupMembers(gm_id,g_id,own_id,mem_id);
                list.add(gm);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JDBCUtil.release(conn,st,rs);
        }
        System.out.println("list的总长度"+list.size());
        return list;
    }





    // 组装GroupMembers对象，该对象属于own_id
    private GroupMembers assembleGroupMembers(ResultSet result) {
        try {
            if (null != result && result.next()) {
                String gm_id = String.valueOf(result.getInt("gm_id"));
                String g_id = String.valueOf(result.getInt("g_id"));
                String own_id = result.getString("own_id");
                String mem_id = result.getString("mem_id");
                return new GroupMembers(gm_id,g_id,own_id,mem_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
