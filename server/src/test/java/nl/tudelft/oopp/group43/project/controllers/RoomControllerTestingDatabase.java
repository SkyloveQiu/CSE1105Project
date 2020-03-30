package nl.tudelft.oopp.group43.project.controllers;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@Transactional
public class RoomControllerTestingDatabase {


    private static String token;

    private Building building;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void init() throws Exception {
        MvcResult result = mockMvc.perform(post("/token")
            .contentType("application/json")
            .param("username", "admin@tudelft.nl")
            .param("password", "12345678"))
            .andExpect(status().isOk())
            .andReturn();
        String response = result.getResponse().getContentAsString();

        token = JsonPath.read(response, "$.token");


        building = new Building(9999, "NICE BUILDING", "straat", "9999");

    }

    @Test
   public void getFilteredRoom() throws Exception {
        mockMvc.perform(get("/filter?blinds=true")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

    @Test
    public void getFilteredRoom2() throws Exception {
        mockMvc.perform(get("/filter?blinds=true&desktop=true&projector=true&chalkBoard=true&microphone=true&smartBoard=true&whiteBoard=false&powerSupply=true&soundInstallation=true&wheelChair=true")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }
}
