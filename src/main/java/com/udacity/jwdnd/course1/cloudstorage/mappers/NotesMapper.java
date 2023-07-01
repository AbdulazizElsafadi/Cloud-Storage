package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.NotesData;
import org.apache.ibatis.annotations.*;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM Notes where userid=#{userid}")
    NotesData[] getUserNotes(int userid);

    @Select("SELECT * FROM Notes WHERE noteid=#{noteid}")
    NotesData getNoteById(int noteid);

    @Insert("INSERT INTO Notes(notetitle, notedescription, userid) VALUES(#{notetitle}, #{notedescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int createNote(NotesData note);

    @Update("UPDATE Notes SET notetitle=#{notetitle}, notedescription=#{notedescription}, userid=#{userid} WHERE noteid=#{noteid}")
    int editNote(NotesData note);

    @Delete("DELETE FROM Notes WHERE noteid=#{noteid}")
    int deleteNote(int noteid);


}
