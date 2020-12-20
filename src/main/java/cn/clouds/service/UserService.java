package cn.clouds.service;

import cn.clouds.entity.User;

import java.util.List;

/**
 * @author clouds
 * @version 1.0
 */
public interface UserService {
    /**
     * 增加用户
     *
     * @param user
     * @return
     */
    void create(User user);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    User update(User user);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    int delete(int id);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> selectAll();
}
