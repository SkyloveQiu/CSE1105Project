package nl.tudelft.oopp.group43.project.controllers;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
@EnableAutoConfiguration
class RoomControllerTestingDatabaseTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetAllRoom() throws Exception {
        mockMvc.perform(get("/room")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

}