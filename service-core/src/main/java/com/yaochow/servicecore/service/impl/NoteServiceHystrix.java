package com.yaochow.servicecore.service.impl;

import com.yaochow.servicecore.common.base.BaseHystrix;
import com.yaochow.servicecore.service.NoteService;
import org.springframework.stereotype.Component;

@Component
public class NoteServiceHystrix extends BaseHystrix implements NoteService {
    @Override
    public String insert(String noteJson) {
        return doMsg();
    }

    @Override
    public String getNoteById(String id) {
        return doMsg();
    }

    @Override
    public String updateNoteById(String noteJson) {
        return doMsg();
    }

    @Override
    public String deleteNoteById(String id) {
        return doMsg();
    }

    @Override
    public String listNoteNameByAccountId2ndCategory(String accountId, String category) {
        return doMsg();
    }

    @Override
    public String listDeletedNoteNameByAccountId(String accountId) {
        return doMsg();
    }
}