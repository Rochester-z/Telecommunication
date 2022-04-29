package dao;

import entity.GroupMembers;

import java.util.List;

public interface GroupMembersDao {


    /**
     * 根据分组id查询GroMembers对应的所有行
     * @param gid 分组id
     * @return
     */
    List<GroupMembers> getListByGid(String gid);

    /**
     *  根据分组id和用户id查询GroupMembers对应所有行
     * @param gid 分组id
     * @param ownid 当前用户id
     * @return
     */
    List<GroupMembers> getListByGidAndOwnid(String gid, String ownid);
}
