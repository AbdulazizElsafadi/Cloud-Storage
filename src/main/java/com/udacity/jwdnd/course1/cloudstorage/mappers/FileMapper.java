package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.FileData;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM Files WHERE userid = #{userid}")
    FileData[] getUserFiles(int userid);

    @Select("SELECT * FROM Files WHERE fileId = #{fileId}")
    FileData getFileById(int fileId);

    @Insert("INSERT INTO Files (filename, contenttype, filesize, userid, filedata) " +
            "VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int createFile(FileData file);

    @Select("SELECT * FROM Files")
    FileData[] getAllFiles();

    @Delete("DELETE FROM Files WHERE fileId = #{fileId}")
    int deleteFile(int fileId);
}
