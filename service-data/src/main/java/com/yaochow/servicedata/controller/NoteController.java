package com.yaochow.servicedata.controller;

import com.alibaba.fastjson.JSONObject;
import com.yaochow.servicedata.common.base.BaseController;
import com.yaochow.servicedata.entity.Note;
import com.yaochow.servicedata.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/note")
@Slf4j
public class NoteController extends BaseController {

    @Autowired
    private NoteService noteServiceImpl;
    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestBody String noteJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("insert, param : {}", noteJson);
        try {
            Note noteReq = JSONObject.parseObject(noteJson, Note.class);
            if (checkSessionLost(request)) {
                log.info("session lost");
                result = doSessionError();
                return result;
            }
            String accountId = (String) request.getSession().getAttribute("uid");
            noteReq.setAccountId(accountId);
            Note noteRes = noteServiceImpl.insert(noteReq);
            result = doSuccess(noteRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("insert, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/updateNoteById", method = RequestMethod.POST)
    public String updateNoteById(@RequestBody String noteJson) {
        long start = System.currentTimeMillis();
        String result;
        log.info("update by id, param : {}", noteJson);
        try {
            Note noteReq = JSONObject.parseObject(noteJson, Note.class);
            Note noteRes = noteServiceImpl.updateNoteById(noteReq);
            result = doSuccess(noteRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }

        log.info("update by id, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/getNoteById/{id}", method = RequestMethod.GET)
    public String getNoteById(@PathVariable String id) {
        long start = System.currentTimeMillis();
        String result;
        log.info("get note by id : {}", id);
        try {
            Note noteRes = noteServiceImpl.getNoteById(id);
            result = doSuccess(noteRes);
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
            Note noteRes = noteServiceImpl.deleteNoteById(id);
            result = doSuccess(noteRes);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("delete note by id, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }

    @RequestMapping(value = "/ListNoteNameByAccountId2ndCategory")
    public String listNoteNameByAccountId2ndCategory(@RequestParam("category") String category) {
        long start = System.currentTimeMillis();
        String result;
        try {
            if (checkSessionLost(request)) {
                log.info("session lost");
                result = doSessionError();
                return result;
            }
            String accountId = (String) request.getSession().getAttribute("uid");
            if (Objects.equals(",", category)) {
                category = null;
            }
            log.info("list noteName by accountId : {} 2nd category : {}", accountId, category);
            List<Note> noteReq = noteServiceImpl.listNoteNameByAccountId2ndCategory(accountId, category);
            log.info(JSONObject.toJSONString(noteReq));
            result = doSuccess(noteReq);
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
        try {
            if (checkSessionLost(request)) {
                log.info("session lost");
                result = doSessionError();
                return result;
            }
            String accountId = (String) request.getSession().getAttribute("uid");
            log.info("list deleted noteName by accountId : {}", accountId);
            List<Note> noteReq = noteServiceImpl.listDeletedNoteNameByAccountId(accountId);
            result = doSuccess(noteReq);
        } catch (Exception e) {
            log.info(e.getMessage(), e);
            result = doError();
        }
        log.info("list deleted noteName, result : {}, cost : {}ms", result, System.currentTimeMillis() - start);
        return result;
    }
}
