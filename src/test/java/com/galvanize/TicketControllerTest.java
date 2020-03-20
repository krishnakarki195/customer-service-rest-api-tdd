package com.galvanize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entities.Note;
import com.galvanize.utilities.LocalDateTimeHelper;
import com.galvanize.wrappers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import javax.transaction.Transactional;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createRequestTest() throws Exception{
        String url = "/api/service";
        ServiceRequestWrapper body = new ServiceRequestWrapper(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333",
                "it's broke and I need it fixed!"
        );
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Some Customer"))
                .andExpect(jsonPath("$.customerAddress").value("123 Any Street, SomeCity, ST, 99999"))
                .andExpect(jsonPath("$.phoneNumber").value("111-222-3333"))
                .andExpect(jsonPath("$.description").value("it's broke and I need it fixed!"))
                .andExpect(jsonPath("$.technician").value("UNASSIGNED"))
                .andDo(print());
    }

    @Test
    public void getAllRequestTest() throws Exception{
        String url = "/api/service";
        ServiceRequestWrapper request1 = new ServiceRequestWrapper(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333",
                "it's broke and I need it fixed!"
        );
        ServiceRequestWrapper request2 = new ServiceRequestWrapper(
                "Another Customer",
                "123 Some Other St, SomeCity, AK, 99999",
                "111-222-3333",
                "it's broke and I need it fixed!"
        );
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request1)))
                .andExpect(status().isOk());
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request2)))
                .andExpect(status().isOk());
        mockMvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(2)))
                .andDo(print());
    }


    @Test
    public void getOneRequestTest() throws Exception{
        String serviceUrl = "/api/service";
        ServiceRequestWrapper serviceRequest = new ServiceRequestWrapper(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333",
                "it's broke and I need it fixed!"
        );
        CreateResponseWrapper createRequestWrapper = mapper.readValue(
                mockMvc.perform(post(serviceUrl).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(serviceRequest)))
                .andExpect(status().isOk())
                .andDo(print()).andReturn().getResponse().getContentAsString(),
                CreateResponseWrapper.class);

        String noteUrl = "/api/notes";
        String note1 = mapper.writeValueAsString(
                new Note(Long.parseLong(createRequestWrapper.getRequestNumber()),
                        "Hello this is first note", LocalDateTimeHelper.getLocalDateTimeInString()));
        String note2 = mapper.writeValueAsString(
                new Note(Long.parseLong(createRequestWrapper.getRequestNumber()),
                        "Hello this is second note", LocalDateTimeHelper.getLocalDateTimeInString()));
        mockMvc.perform(post(noteUrl).content(note1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(Long.parseLong(createRequestWrapper.getRequestNumber())))
                .andDo(print());
        mockMvc.perform(post(noteUrl).content(note2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketId").value(Long.parseLong(createRequestWrapper.getRequestNumber())))
                .andDo(print());

        serviceUrl = serviceUrl + "/" + createRequestWrapper.getRequestNumber();

        GetOneResponseWrapper getOneRequestWrapper = mapper.readValue(
                mockMvc.perform(get(serviceUrl)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print()).andReturn().getResponse().getContentAsString(), GetOneResponseWrapper.class);
        assertEquals(getOneRequestWrapper.getNotes().size(),2);

    }

    @Test
    public void assignRequestTest() throws Exception{
        String serviceUrl = "/api/service";
        ServiceRequestWrapper serviceRequest = new ServiceRequestWrapper(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333",
                "it's broke and I need it fixed!"
        );
        CreateResponseWrapper createRequestWrapper = mapper.readValue(
                mockMvc.perform(post(serviceUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(serviceRequest)))
                        .andExpect(status().isOk())
                        .andDo(print()).andReturn().getResponse().getContentAsString(),
                CreateResponseWrapper.class);

        serviceUrl = serviceUrl + "/" + createRequestWrapper.getRequestNumber();
        AssignRequestWrapper assignRequestWrapper = new AssignRequestWrapper(
               "Bob Builder", "01/20/2020", "08:30AM");

        AssignResponseWrapper assignResponseWrapper = mapper.readValue(
                mockMvc.perform(put(serviceUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(assignRequestWrapper)))
                        .andExpect(status().isOk())
                        .andDo(print()).andReturn().getResponse().getContentAsString(),
                AssignResponseWrapper.class);
        System.out.println(assignResponseWrapper);

        assertEquals(assignResponseWrapper.getCustomerName(), "Some Customer");
        assertEquals(assignResponseWrapper.getStatus(), "ASSIGNED");
        assertEquals(assignResponseWrapper.getDescription(), "it's broke and I need it fixed!");

    }

    @Test
    public void updateRequestTest() throws Exception{
        String serviceUrl = "/api/service";
        ServiceRequestWrapper serviceRequest = new ServiceRequestWrapper(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333",
                "it's broke and I need it fixed!"
        );
        CreateResponseWrapper createRequestWrapper = mapper.readValue(
                mockMvc.perform(post(serviceUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(serviceRequest)))
                        .andExpect(status().isOk())
                        .andDo(print()).andReturn().getResponse().getContentAsString(),
                CreateResponseWrapper.class);

        serviceUrl = serviceUrl + "/" + createRequestWrapper.getRequestNumber() + "/status";
        UpdateRequestWrapper updateRequestWrapper = new UpdateRequestWrapper(
                "Bob Builder",
                "01/20/2020",
                "08:30AM",
                "RESOLVED",
                "Customer called to say that the unit was not plugged in.  He plugged it in and now it works"
        );

        AssignResponseWrapper assignResponseWrapper = mapper.readValue(
                mockMvc.perform(put(serviceUrl).contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateRequestWrapper)))
                        .andExpect(status().isOk())
                        .andDo(print()).andReturn().getResponse().getContentAsString(),
                AssignResponseWrapper.class);

        assertEquals(assignResponseWrapper.getStatus(), "RESOLVED");
        assertEquals(assignResponseWrapper.getDescription(), "it's broke and I need it fixed!");

    }


}
