package com.gyyst.demo.mapper;

import com.gyyst.demo.model.User;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * @author gyyst
 * @Description
 * @Create by 2025/5/28 13:12
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM User WHERE id = #{id}")
    User getUser(Long id);

    @Insert("INSERT INTO User (id, name) VALUES (#{id}, #{name})")
    Integer createUser(@Param("id") Long id, @Param("name") String name);

    @Delete("DELETE FROM User WHERE id = #{id}")
    Integer removeUser(Long id);
}
