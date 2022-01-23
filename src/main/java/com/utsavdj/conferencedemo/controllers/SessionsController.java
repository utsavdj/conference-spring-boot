package com.utsavdj.conferencedemo.controllers;

import com.utsavdj.conferencedemo.models.Session;
import com.utsavdj.conferencedemo.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionsController {
  @Autowired
  private SessionRepository sessionRepository;

  @GetMapping
  public List<Session> list() {
    return sessionRepository.findAll();
  }

  @GetMapping
  @RequestMapping("{id}")
  public Session set(@PathVariable Long id) {
    return sessionRepository.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Session create(@RequestBody final Session session){
    return sessionRepository.saveAndFlush(session);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable Long id){
    // Also need to check for children records before deleting
    sessionRepository.deleteById(id);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  public Session update(@PathVariable Long id, @RequestBody Session session){
    //Since it is a put all the attributes are expected to be passed in otherwise they will be updated as null,
    // if we want only certain attributes to be updated then we should use patch
    // All the attributes should be validated and 400 bad payload should be returned if they are not valid
    Session existingSession = sessionRepository.getById(id);
    BeanUtils.copyProperties(session, existingSession, "session_id");
    return sessionRepository.saveAndFlush(existingSession);
  }
}
