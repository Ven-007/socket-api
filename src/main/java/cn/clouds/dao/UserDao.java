package cn.clouds.dao;

import cn.clouds.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author clouds
 * @version 1.0
 */
@Mapper
public interface UserDao {

    /**
     * 查询所有用户
     *
     * @return List<User>
     */
    List<User> selectALl();
}
