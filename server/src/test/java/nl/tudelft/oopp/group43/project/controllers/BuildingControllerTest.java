package nl.tudelft.oopp.group43.project.controllers;


import static org.junit.Assert.assertEquals;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@RunWith(SpringRunner.class)
@WebMvcTest(Building.class)
@EnableAutoConfiguration
public class BuildingControllerTest {

    private static String token;

    private Building building;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BuildingRepository buildingRepository;


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

    @AfterEach
    public void uninit() throws Exception {
        if (buildingRepository.existsBuildingByBuildingNumber(9999)) {
            mockMvc.perform(RestDocumentationRequestBuilders.delete("/building/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", token));
        }
    }

    @Test
    void getBuildingTest() throws Exception {
        mockMvc.perform(get("/building")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

    // {"building_number":33,"building_name":"EWI","address":"32","opening_hours":"32"}
    @Test
    void postNewBuildingWithNoAdminRights() throws Exception {
        mockMvc.perform(post("/building")
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(building))
            .param("token", "invalid"))
            .andExpect(status().is4xxClientError());
    }

    @Test
    void addAndUpdateNewBuilding() throws Exception {
        mockMvc.perform(post("/building")
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(building))
            .param("token", token))
            .andExpect(status().isOk());

        building.setBuildingName("EXTREMELY NICE BUILDING");

        mockMvc.perform(post("/building/update")
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(building))
            .param("token", token))
            .andExpect(status().isOk());
    }

    @Test
    void addAndThenDeleteBuilding() throws Exception {
        mockMvc.perform(post("/building")
            .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(building))
            .param("token", token))
            .andExpect(status().isOk());

        assertEquals(true, buildingRepository.existsBuildingByBuildingNumber(9999));

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/building/9999")
            .contentType(MediaType.APPLICATION_JSON)
            .param("token", token));

        assertEquals(false, buildingRepository.existsBuildingByBuildingNumber(9999));


    }

}
