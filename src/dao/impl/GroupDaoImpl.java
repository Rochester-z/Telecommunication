package dao.impl;

import entity.Groups;
import dao.GroupsDao;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupDaoImpl implements GroupsDao {

    /**
     * 根据id查询对应行
     * @param g_id
     * @return
     */
    @Override
    public Groups getGroupsByGid(String g_id) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;


        try {
            conn = JDBCUtil.getConn();
            st = conn.createStatement();

            String sql = "select * from groups where g_id = " + Integer.valueOf(g_id);
            rs = st.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return assembleGroup(rs);
    }



    // 组装Group对象
    private Groups assembleGroup(ResultSet result) {
        try {
            if (null != result && result.next()) {
                String g_id = String.valueOf(result.getInt("g_id"));
                String group_name = result.getString("group_name");
                return new Groups(g_id, group_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
