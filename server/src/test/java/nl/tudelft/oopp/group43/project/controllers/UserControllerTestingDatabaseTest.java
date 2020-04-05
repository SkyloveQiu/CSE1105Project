package nl.tudelft.oopp.group43.project.controllers;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTestingDatabaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/token")
            .contentType("application/json")
            .param("username", "ziang.qiu@gmail.com")
            .param("password", "123456789"))
            .andExpect(status().isOk())
            .andReturn();
        String response = result.getResponse().getContentAsString();
        String token = JsonPath.read(response, "$.token");
        mockMvc.perform(post("/api/hello")
            .header("authorization", "Bearer" + token)
            .contentType("application/json"))
            .andExpect(status().isOk());

    }

    @Test
    void getNameTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/token")
            .contentType("application/json")
            .param("username", "ziang.qiu@gmail.com")
            .param("password", "123456789"))
            .andExpect(status().isOk())
            .andReturn();
        String response = result.getResponse().getContentAsString();
        String token = JsonPath.read(response, "$.token");
        mockMvc.perform(post("/name")
            .param("token", token)
            .contentType("application/json"))
            .andExpect(status().isOk());
    }

    @Test
    void getWrongPassword() throws Exception {
        MvcResult result = mockMvc.perform(post("/token")
            .contentType("application/json")
            .param("username", "ziang.qiu@gmail.com")
            .param("password", "12345678"))
            .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
            .andReturn();
    }

    @Test
    void registerDupicateUser() throws Exception {
        MvcResult result = mockMvc.perform(post("/registration")
            .contentType("application/json")
            .param("username", "ziang.qiu@gmail.com")
            .param("password", "12345678")
            .param("firstName", "Ziang")
            .param("lastName", "Qiu")
            .param("role", "student"))
            .andExpect(status().is(HttpStatus.FORBIDDEN.value()))
            .andReturn();
    }

}