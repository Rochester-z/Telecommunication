package dao;

import entity.Groups;

public interface GroupsDao {

    /**
     * 根据分组id查询对应分组行
     */
    Groups getGroupsByGid(String g_id);

}
