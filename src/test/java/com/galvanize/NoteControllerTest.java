package com.galvanize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entities.Note;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    public String helperGetCurrentLocalDateTimeInString(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String DATE_FORMATTER= "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        String localDateTimeString = localDateTime.format(formatter);
        return localDateTimeString;
    }

    @Test
    public void createNoteTest() throws Exception {
        String url = "/api/notes";
        String body = mapper.writeValueAsString(new Note(1L,"Hello this is first note",helperGetCurrentLocalDateTimeInString()));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void getNotesTest() throws Exception {
        String url = "/api/notes";
        String body = mapper.writeValueAsString(new Note(1L,"Hello this is first note",helperGetCurrentLocalDateTimeInString()));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(jsonPath("$.note").exists())
                .andExpect(jsonPath("$.length()",is(1)))
                .andExpect(status().isOk());

    }

    @Test
    public void getNoteByIdTest() throws Exception {
        String url = "/api/notes";
        String body = mapper.writeValueAsString(new Note(1L,"Hello this is first note",helperGetCurrentLocalDateTimeInString()));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].id");
        url = url + "/" + id;
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value("Hello this is first note"))
                .andDo(print());
    }

    @Test
    public void updateNoteByIdTest() throws Exception {
        String url = "/api/notes";
        String body = mapper.writeValueAsString(new Note(1L,"Hello this is first note",helperGetCurrentLocalDateTimeInString()));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].id");
        url = url + "/" + id;
        String note = "Updated note";
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON).content(note))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value("Updated note"));

    }

    @Test
    public void deleteNoteByIdTest() throws Exception {
        String url = "/api/notes";
        String body = mapper.writeValueAsString(new Note(1L,"Hello this is first note",helperGetCurrentLocalDateTimeInString()));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].id");
        url = url + "/" + id;
        mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

}
