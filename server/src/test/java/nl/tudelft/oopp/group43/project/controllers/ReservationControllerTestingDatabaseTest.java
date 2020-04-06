package nl.tudelft.oopp.group43.project.controllers;

import static org.junit.Assert.assertNotNull;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import java.text.SimpleDateFormat;
import java.util.Date;
import nl.tudelft.oopp.group43.project.models.Building;
import nl.tudelft.oopp.group43.project.models.Reservation;
import nl.tudelft.oopp.group43.project.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@EnableAutoConfiguration
class ReservationControllerTestingDatabaseTest {

    @Autowired
    MockMvc mockMvc;

    private static String token;


    @Test
    void getAllReservation() throws Exception {
        mockMvc.perform(get("/reservation")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

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


    @Test
    void testReservationAddAndEdit() throws Exception {
        ReservationController b = new ReservationController();
        ResultActions a = mockMvc.perform(post("/reservation?token=" + token)
            .contentType(MediaType.APPLICATION_JSON).content("{\"first_name\":\"admin\",\"last_name\":\"admin\",\"username\":\"admin@tudelft.nl\",\"role\":\"admin\"},\"room_id\":46,\"starting_date\":\"2020-04-14T18:00:00.000+0000\",\"end_date\":\"2020-04-14T19:00:00.000+0000\",\"reservationId\":23}")
            .contentType("application/json"));
        assertNotNull(a);



    }

}