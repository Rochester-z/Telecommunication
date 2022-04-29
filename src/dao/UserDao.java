package dao;

import entity.User;

public interface UserDao {


    /**
     * 通过账号密码登录，返回user对象
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);


    /**
     * 通过查询uid返回user对象
     * @param uid 主键
     * @return
     */
    User findUserByUid(String uid);

    /**
     * 通过username查询user对象
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 通过昵称查询user对象
     * @param nickname
     * @return
     */
    User getByNickname(String nickname);




}
