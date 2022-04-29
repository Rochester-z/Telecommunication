package dao;

import entity.GroupOwner;

import java.util.List;

public interface GroupOwnerDao {

    /**根据go_id查询对应的行*/
    GroupOwner getByGoId(String go_id);

    /**根据用户id查询对应所有的行*/
    List<GroupOwner> getListByOwnid(String own_id);
}
