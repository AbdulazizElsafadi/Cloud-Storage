package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialsData;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM Credentials")
    CredentialsData[] getAllCredentials();

    @Select("SELECT * FROM Credentials WHERE userid = #{userid}")
    CredentialsData[] getUserCredentials(int userid);

    @Insert("INSERT INTO Credentials (url, username, password, userid, key) " +
            "VALUES (#{url}, #{username}, #{password}, #{userid}, #{key})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int createCredential(CredentialsData credential);

    @Update("UPDATE Credentials SET url = #{url}, username = #{username}, " +
            "password = #{password}, userid = #{userid}, key = #{key} " +
            "WHERE credentialid = #{credentialid}")
    int updateCredential(CredentialsData credential);

    @Delete("DELETE FROM Credentials WHERE credentialid = #{credentialid}")
    int deleteCredential(int credentialId);
}
