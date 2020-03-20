package com.galvanize.controllers;


import com.galvanize.entities.Customer;
import com.galvanize.entities.Note;
import com.galvanize.entities.Ticket;
import com.galvanize.utilities.LocalDateTimeHelper;
import com.galvanize.wrappers.*;
import com.galvanize.services.CustomerService;
import com.galvanize.services.NoteService;
import com.galvanize.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(value = "/api")
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    CustomerService customerService;

    @Autowired
    NoteService noteService;

    @PostMapping("/service")
    public CreateResponseWrapper createServiceRequest(@RequestBody ServiceRequestWrapper serviceRequestWrapper){

        Customer customer = new Customer();
        Ticket ticket = new Ticket();

        customer.setName(serviceRequestWrapper.getCustomerName());
        customer.setAddress(serviceRequestWrapper.getCustomerAddress());
        customer.setPhoneNumber(serviceRequestWrapper.getPhoneNumber());

        Customer savedCustomer = customerService.save(customer);

        ticket.setCustomerId(savedCustomer.getId());
        ticket.setDescription(serviceRequestWrapper.getDescription());
        ticket.setRequestDateTime(LocalDateTimeHelper.getLocalDateTimeInString());
        ticket.setTechnician("UNASSIGNED");

        Ticket savedTicket = ticketService.save(ticket);

        CreateResponseWrapper createServiceWrapper = new CreateResponseWrapper(
            String.valueOf(savedTicket.getId()),
            savedCustomer.getName(),
            savedCustomer.getAddress(),
            savedCustomer.getPhoneNumber(),
            serviceRequestWrapper.getDescription(),
            savedTicket.getTechnician());

        return createServiceWrapper;

    }

    @GetMapping("/service")
    public List<GetAllResponseWrapper> getAllServiceRequests(){
        List<GetAllResponseWrapper> holder = new ArrayList<>();
        ticketService.findAll().forEach(ticket ->
                        holder.add(new GetAllResponseWrapper(
                        String.valueOf(ticket.getId()),
                        ticket.getRequestDateTime(),
                        customerService.findById(ticket.getCustomerId()).get().getName(),
                        customerService.findById(ticket.getCustomerId()).get().getAddress(),
                        customerService.findById(ticket.getCustomerId()).get().getPhoneNumber(),
                        ticket.getDescription(),
                        ticket.getTechnician(),
                        ticket.getAppointmentDateTime(),
                        ticket.getStatus()))
                );
        return holder;
    }

    @GetMapping("/service/{requestNumber}")
    public GetOneResponseWrapper getServiceRequestById(@PathVariable Long requestNumber){
        Ticket ticket = ticketService.findById(requestNumber).get();
        Customer customer = customerService.findById(ticket.getCustomerId()).get();
        List<Note> notes = noteService.findByTicketId(ticket.getId());

        GetOneResponseWrapper getOneRequestWrapper = new GetOneResponseWrapper();

        getOneRequestWrapper.setRequestNumber(String.valueOf(ticket.getId()));
        getOneRequestWrapper.setRequestDateTIme(ticket.getRequestDateTime());
        getOneRequestWrapper.setCustomerName(customer.getName());
        getOneRequestWrapper.setCustomerAddress(customer.getAddress());
        getOneRequestWrapper.setPhoneNumber(customer.getPhoneNumber());
        getOneRequestWrapper.setDescription(ticket.getDescription());
        getOneRequestWrapper.setTechnician(ticket.getTechnician());
        getOneRequestWrapper.setAppointmentDateTime(ticket.getAppointmentDateTime());
        getOneRequestWrapper.setStatus(ticket.getStatus());
        getOneRequestWrapper.setNotes(notes);

        return getOneRequestWrapper;
    }

    @PutMapping("/service/{requestNumber}")
    public AssignResponseWrapper assignServiceRequest(@PathVariable Long requestNumber, @RequestBody AssignRequestWrapper assignRequestWrapper){

        Optional<Ticket> optionalTicket = ticketService.findById(requestNumber);
        Ticket ticket = optionalTicket.get();
        ticket.setTechnician(assignRequestWrapper.getTechnician());
        ticket.setStatus("ASSIGNED");
        ticket.setAppointmentDateTime(assignRequestWrapper.getAppointmentDate() + " " + assignRequestWrapper.getAppointmentTime());

        Optional<Customer> optionalCustomer = customerService.findById(ticket.getCustomerId());
        Customer customer = optionalCustomer.get();
        AssignResponseWrapper assignResponseWrapper = new AssignResponseWrapper(
            String.valueOf(requestNumber),
            customer.getName(),
            customer.getAddress(),
            customer.getPhoneNumber(),
            ticket.getDescription(),
            ticket.getStatus()
        );

        return assignResponseWrapper;
    }

    @PutMapping("/service/{requestNumber}/status")
    public AssignResponseWrapper updateServiceRequest(@PathVariable Long requestNumber, @RequestBody UpdateRequestWrapper updateRequestWrapper){

        Optional<Ticket> optionalTicket = ticketService.findById(requestNumber);
        Ticket ticket = optionalTicket.get();
        ticket.setStatus(updateRequestWrapper.getStatus());
        ticket.setTechnician(updateRequestWrapper.getTechnician());
        ticket.setAppointmentDateTime(updateRequestWrapper.getAppointmentDate() + " " + updateRequestWrapper.getAppointmentTime());

        Note note = new Note(ticket.getId(),updateRequestWrapper.getNote(),LocalDateTimeHelper.getLocalDateTimeInString());
        noteService.save(note);

        return new AssignResponseWrapper(
                String.valueOf(ticket.getId()),
                customerService.findById(ticket.getCustomerId()).get().getName(),
                customerService.findById(ticket.getCustomerId()).get().getAddress(),
                customerService.findById(ticket.getCustomerId()).get().getPhoneNumber(),
                ticket.getDescription(),
                ticket.getStatus()
        );
    }

}
