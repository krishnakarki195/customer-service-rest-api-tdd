package com.galvanize.controllers;

import com.galvanize.entities.Note;
import com.galvanize.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class NoteController {

    @Autowired
    NoteService noteService;

    @PostMapping(value = "/notes")
    public Note save(@RequestBody Note note){
        return noteService.save(note);
    }

    @GetMapping(value = "/notes")
    public List<Note> getNotes(){
        return noteService.findAll();
    }

    @GetMapping(value = "/notes/{id}")
    public Optional<Note> getNote(@PathVariable Long id){
        return noteService.findById(id);
    }

    @PatchMapping(value = "/notes/{id}")
    public Note updateNoteById(@PathVariable Long id,@RequestBody String note) {
        Optional<Note> optionalNote = noteService.findById(id);
        Note newNote = optionalNote.get();
        newNote.setNote(note);
        return noteService.save(newNote);
    }

    @DeleteMapping(value = "/notes/{id}")
    public void deleteNoteById(@PathVariable Long id){
        noteService.deleteById(id);
    }


}
