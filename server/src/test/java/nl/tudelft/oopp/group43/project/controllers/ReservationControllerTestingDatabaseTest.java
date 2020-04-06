package nl.tudelft.oopp.group43.project.controllers;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@EnableAutoConfiguration
class ReservationControllerTestingDatabaseTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void getAllReservation() throws Exception {
        mockMvc.perform(get("/reservation")
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

}