package nl.tudelft.oopp.group43.project.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
@AutoConfigureTestEntityManager
class UserControllerTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


//    @Test
//    void loginTest() throws Exception {
//
//        UserSource user = new UserSource("ziang.qiu@gmail.com", "12345678");
//
//        mockMvc.perform(post("/token")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isOk());
//
//    }
}