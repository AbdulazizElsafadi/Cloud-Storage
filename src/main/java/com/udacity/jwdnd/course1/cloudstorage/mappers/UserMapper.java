package com.udacity.jwdnd.course1.cloudstorage.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import com.udacity.jwdnd.course1.cloudstorage.models.UserData;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM Users")
    UserData[] getUsers();

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    UserData getUserByUsername(String username);

    @Insert("INSERT INTO Users(firstname, lastname, username, password, salt ) VALUES(#{firstname}, #{lastname}, #{username}, #{password}, #{salt})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int createUser(UserData user);
}
