package com.galvanize.services;

import com.galvanize.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteService extends JpaRepository<Note, Long> {

    List<Note> findByTicketId(Long ticketId);

}
