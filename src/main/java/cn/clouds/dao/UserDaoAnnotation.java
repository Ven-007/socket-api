package cn.clouds.dao;

import cn.clouds.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author clouds
 * @version 1.0
 */
@Mapper
public interface UserDaoAnnotation {

    /**
     * 查询所有user
     *
     * @return
     */
    @Select("select * from user")
    List<User> selectALl();

    /**
     * 创建user
     *
     * @param user
     */
    @Insert("INSERT INTO user(name, age,money) VALUES(#{user.name}, #{user.age}, #{user.money})")
    void create(@Param("user") User user);
}
