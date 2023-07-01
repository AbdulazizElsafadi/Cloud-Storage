package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.NotesData;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NotesMapper notesMapper;

    public NoteService(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public int createNote(NotesData note) {
        return notesMapper.createNote(note);
    }

    public int editNote(NotesData note) {
        return notesMapper.editNote(note);
    }

    public int deleteNote(int noteid) {
        return notesMapper.deleteNote(noteid);
    }

    public NotesData[] getUserNotes(int userid) {
        return notesMapper.getUserNotes(userid);
    }
}
