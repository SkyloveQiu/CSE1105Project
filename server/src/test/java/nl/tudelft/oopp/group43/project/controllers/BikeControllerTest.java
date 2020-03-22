package nl.tudelft.oopp.group43.project.controllers;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(BikeController.class)
@EnableAutoConfiguration
class BikeControllerTest {

    private String token;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init() throws Exception {
        MvcResult result = mockMvc.perform(post("/token")
                .contentType("application/json")
                .param("username","ziang.qiu@gmail.com")
                .param("password","12345678"))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        token = JsonPath.read(response,"$.token");
    }

    @Test
    void getBikeTest() throws Exception {
        mockMvc.perform(get("/bike/0")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    void getReservations() throws Exception {
        mockMvc.perform(post("/bikeReservation/user")
                .contentType("application/json")
                .param("token",token))
                .andExpect(status().isOk());
    }
}