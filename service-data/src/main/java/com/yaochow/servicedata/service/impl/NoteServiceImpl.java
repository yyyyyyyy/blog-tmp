package com.yaochow.servicedata.service.impl;

import com.yaochow.servicedata.common.base.BaseService;
import com.yaochow.servicedata.entity.Note;
import com.yaochow.servicedata.repository.NoteRepository;
import com.yaochow.servicedata.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl extends BaseService implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note insert(Note note) {
        setInsertParam(note);
        return noteRepository.insert(note);
    }

    @Override
    public Note getNoteById(String id) {
        Optional<Note> optional = noteRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Note updateNoteById(Note note) {
        setUpdateParam(note);
        return noteRepository.save(note);
    }

    @Override
    public Note deleteNoteById(String id) {
        Note note = new Note();
        note.setId(id);
        setDeleted(note);
        return noteRepository.save(note);
    }

    @Override
    public List<Note> listNoteNameByAccountId2ndCategory(String accountId, String category) {
        Note note = new Note();
        note.setAccountId(accountId);
        note.setCategory(category);
        setUnDeleted(note);
        return noteRepository.findAll(Example.of(note));
    }

    @Override
    public List<Note> listDeletedNoteNameByAccountId(String accountId) {
        Note note = new Note();
        note.setAccountId(accountId);
        setDeleted(note);
        return noteRepository.findAll(Example.of(note));
    }
}