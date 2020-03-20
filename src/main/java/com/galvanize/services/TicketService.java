package com.galvanize.services;

import com.galvanize.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketService extends JpaRepository<Ticket, Long> {

}
