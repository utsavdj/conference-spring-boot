package com.utsavdj.conferencedemo.controllers;

import com.utsavdj.conferencedemo.models.Speaker;
import com.utsavdj.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
  @Autowired
  private SpeakerRepository speakerRepository;

  @GetMapping
  public List<Speaker> list() {
    return speakerRepository.findAll();
  }

  @GetMapping
  @RequestMapping("{id}")
  public Speaker get(@PathVariable Long id){
    return speakerRepository.getById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Speaker create(@RequestBody final Speaker speaker) {
    return speakerRepository.saveAndFlush(speaker);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable Long id){
    // Also need to check for children records before deleting
    speakerRepository.deleteById(id);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  public Speaker update(@PathVariable Long id, @RequestBody final Speaker speaker){
    //Since it is a put all the attributes are expected to be passed in otherwise they will be updated as null,
    // if we want only certain attributes to be updated then we should use patch
    // All the attributes should be validated and 400 bad payload should be returned if they are not valid
    Speaker existingSpeaker = speakerRepository.getById(id);
    BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
    return speakerRepository.saveAndFlush(existingSpeaker);
  }
}
