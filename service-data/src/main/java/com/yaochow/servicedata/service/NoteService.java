package com.yaochow.servicedata.service;

import com.yaochow.servicedata.entity.Note;

import java.util.List;

public interface NoteService {

    Note insert(Note note);

    Note getNoteById(String id);

    Note updateNoteById(Note note);

    Note deleteNoteById(String id);

    List<Note> listNoteNameByAccountId2ndCategory(String accountId, String category);

    List<Note> listDeletedNoteNameByAccountId(String accountId);

}
