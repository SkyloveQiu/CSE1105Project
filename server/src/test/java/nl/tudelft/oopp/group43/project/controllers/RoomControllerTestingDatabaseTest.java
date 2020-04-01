package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Room;
import nl.tudelft.oopp.group43.project.repositories.BuildingRepository;
import nl.tudelft.oopp.group43.project.repositories.RoomRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@WebMvcTest(RoomController.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
@Transactional
public class RoomControllerTestingDatabaseTest {


    public static String token;

    private Building building;

    private Room room;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomRepository roomRepository;

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
    }

    /**
     * Deletes the created building and room.
     *
     * @throws Exception throws an exception based on the error
     */
    @AfterEach
    public void uninit() throws Exception {
        if (roomRepository.existsRoomById(9999)) {
            mockMvc.perform(RestDocumentationRequestBuilders.delete("/room/9999")
                .contentType(MediaType.APPLICATION_JSON)
                .param("token", token));
        }
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

    @Test
    public void postNewRoomWithNoAdminRights() throws Exception {
        mockMvc.perform(post("/room")
            .contentType(MediaType.APPLICATION_JSON).content("{\"building\":{\"building_number\":0},\"room_name\":\"32\",\"attributes\":\"32\",\"id\":9999}")
            .param("token", "invalid"))
            .andExpect(status().is4xxClientError());
    }

    //    @Test
    //    public void addAndUpdateNewRoom() throws Exception {
    //        if (buildingRepository.existsBuildingByBuildingNumber(0)) {
    //            mockMvc.perform(post("/room?token=" + token)
    //                .contentType(MediaType.APPLICATION_JSON).content("{\"building\":{\"building_number\":0},\"room_name\":\"32\",\"attributes\":\"32\",\"id\":9999}")
    //            )
    //                .andExpect(status().isOk());
    //
    //            room.setRoomName("EXTREMELY NICE ROOM");
    //
    //            mockMvc.perform(post("/room?token=" + token)
    //                .contentType(MediaType.APPLICATION_JSON).content("{\"building\":{\"building_number\":0},\"room_name\":\"nice room\",\"attributes\":\"32\",\"id\":9999}")
    //            )
    //                .andExpect(status().isOk());
    //
    //            room.setRoomName("nice room");
    //        } else {
    //            assertTrue(true);
    //        }
    //    }
    //
    //    @Test
    //    public void addAndThenDeleteRoom() throws Exception {
    //        if (buildingRepository.existsBuildingByBuildingNumber(0)) {
    //
    //            mockMvc.perform(post("/room")
    //                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(room))
    //                .param("token", token))
    //                .andExpect(status().isOk());
    //
    //            assertEquals(true, roomRepository.existsRoomById(9999));
    //
    //            mockMvc.perform(RestDocumentationRequestBuilders.delete("/room/9999")
    //                .contentType(MediaType.APPLICATION_JSON)
    //                .param("token", token));
    //
    //            assertEquals(false, roomRepository.existsRoomById(9999));
    //
    //        } else {
    //            assertTrue(true);
    //        }
    //
    //
    //    }


}
