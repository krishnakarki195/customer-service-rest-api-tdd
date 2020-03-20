package com.galvanize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entities.Customer;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createCustomerTest() throws Exception {
        String url = "/api/customers";
        String body = mapper.writeValueAsString(new Customer(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333"));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    public void getCustomerTest() throws Exception {
        String url = "/api/customers";
        String body = mapper.writeValueAsString(new Customer(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333"));
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
    public void getCustomerByIdTest() throws Exception {
        String url = "/api/customers";
        String body = mapper.writeValueAsString(new Customer(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333"));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].id");
        url = url + "/" + id;
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Some Customer"))
                .andDo(print());
    }

    @Test
    public void updateCustomerByIdTest() throws Exception {
        String url = "/api/customers";
        String body = mapper.writeValueAsString(new Customer(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333"));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].id");
        url = url + "/" + id;
        String name = "Michael Clark";
        mockMvc.perform(patch(url)
                .contentType(MediaType.APPLICATION_JSON).content(name))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Michael Clark"));

    }

    @Test
    public void deleteCustomerByIdTest() throws Exception {
        String url = "/api/customers";
        String body = mapper.writeValueAsString(new Customer(
                "Some Customer",
                "123 Any Street, SomeCity, ST, 99999",
                "111-222-3333"));
        mockMvc.perform(post(url).content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
        MvcResult result = mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$[0].id");
        url = url + "/" + id;
        mockMvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
