package com.yaochow.servicecore.controller;

import com.yaochow.servicecore.common.base.BaseController;
import com.yaochow.servicecore.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
public class NoteController extends BaseController {

    private Logger log = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestBody String noteJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("insert, param : {}", noteJson);
        try {
            result = noteService.insert(noteJson);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("insert, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    public String updateById(@RequestBody String noteJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("update by accountId, param : {}", noteJson);
        try {
            result = noteService.updateNoteById(noteJson);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }

        log.info("update by accountId, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/getNoteById/{id}", method = RequestMethod.GET)
    public String getNoteById(@PathVariable String id) {
        long start = System.currentTimeMillis();
        String result;
        log.info("get note by id : {}", id);
        try {
            result = noteService.getNoteById(id);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("get note by id, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "deleteNoteById/{id}", method = RequestMethod.GET)
    public String deleteNoteById(@PathVariable String id) {
        long start = System.currentTimeMillis();
        String result;
        log.info("delete note by id : {}", id);
        try {
            result = noteService.deleteNoteById(id);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("delete note by id, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/listNoteNameByAccountId2ndCategory")
    public String listNoteNameByAccountId2ndCategory(@RequestParam("category") String category) {
        long start = System.currentTimeMillis();
        String result;
        log.info("list noteName by accountId 2nd category : {}", category);
        try {
            result = noteService.listNoteNameByAccountId2ndCategory(category);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("list noteName, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "listDeletedNoteNameByAccountId", method = RequestMethod.GET)
    public String listDeletedNoteNameByAccountId() {
        long start = System.currentTimeMillis();
        String result;
        log.info("list deleted noteName by accountId begin");
        try {
            result = noteService.listDeletedNoteNameByAccountId();
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("list deleted noteName, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "listNote", method = RequestMethod.POST)
    public String listNote(@RequestBody String noteJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("list note begin");
        try {
            result = noteService.listNote(noteJson);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("list note, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }
}
