package com.yaochow.servicecore.service;

import com.yaochow.servicecore.service.impl.NoteServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "service-data", fallback = NoteServiceHystrix.class)
public interface NoteService {

    @RequestMapping(value = "/note/insert", method = RequestMethod.POST)
    String insert(@RequestBody String noteJson);

    @RequestMapping(value = "/note/getNoteById/{id}", method = RequestMethod.GET)
    String getNoteById(@PathVariable("id") String id);

    @RequestMapping(value = "/note/updateNoteById", method = RequestMethod.POST)
    String updateNoteById(@RequestBody String noteJson);

    @RequestMapping(value = "/note/deleteNoteById/{id}", method = RequestMethod.GET)
    String deleteNoteById(@PathVariable("id") String id);

    @RequestMapping(value = "/note/ListNoteNameByAccountId2ndCategory", method = RequestMethod.GET)
    String listNoteNameByAccountId2ndCategory(@RequestParam("accountId") String accountId, @RequestParam("category") String category);

    @RequestMapping(value = "/note/listDeletedNoteNameByAccountId/{accountId}", method = RequestMethod.GET)
    String listDeletedNoteNameByAccountId(@PathVariable String accountId);

}
