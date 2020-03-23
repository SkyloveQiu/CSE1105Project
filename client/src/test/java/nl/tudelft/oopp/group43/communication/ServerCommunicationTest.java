package nl.tudelft.oopp.group43.communication;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.github.paweladamski.httpclientmock.HttpClientMock;
import javafx.application.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
public class ServerCommunicationTest {

    private final String cURL = "http://localhost:8000/";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBuildingsNull() {
        assertNotNull(ServerCommunication.getBuilding());
    }

   @Test
    public void testGetBuildings() throws Exception {
        String buildings = "[{\"building_number\":0,\"building_name\":\"Pathe Delft\",\"address\":\"Vesteplein 5 2611 WG Delft\",\"opening_hours\":\"{\\\"fr\\\": \\\"07:00-19:00\\\", \\\"mo\\\": \\\"07:00-22:00\\\", \\\"sa\\\": \\\"closed\\\", \\\"su\\\": \\\"closed\\\", \\\"th\\\": \\\"07:00-22:00\\\", \\\"tu\\\": \\\"07:00-22:00\\\", \\\"we\\\": \\\"07:00-22:00\\\"}\"}]";

        // HttpClient client = mock(HttpClient.class);
        // HttpRequest req = mock(HttpRequest.class);
        // this.mockMvc.perform(get(cURL + "building")).andExpect(content().string(buildings));

        String buildingRegex = "\\[(?:\\{\\\"building_number\\\":[0-9]+,\\\"building_name\\\":\\\".*?\\\",\\\"address\\\":\\\".*?\\\",\\\"opening_hours\\\":\\\"\\{(?:\\\\\\\"[a-z]{2}\\\\\\\": \\\\\\\".*?\\\\\\\"(?:, )?){7}\\}\\\"\\}(?:, )?)*\\]";

        assertTrue(ServerCommunication.getBuilding().matches(buildingRegex));
    }

    @Test
    public void testGetRooms() {
        String buildingRegex = "\\{\\\"building_number\\\":[0-9]+,\\\"building_name\\\":\\\".*?\\\",\\\"address\\\":\\\".*?\\\",\\\"opening_hours\\\":\\\"\\{(?:\\\\\\\"[a-z]{2}\\\\\\\": \\\\\\\".*?\\\\\\\"(?:, )?){7}\\}\\\"\\}";
        String attributeRegex = "\\{(?:\\\\\\\"(?:(?!\\\\).)*?\\\\\\\": (?:true|false|[0-9]+|\\\\\\\"(?:(?!\\\\).)*?\\\\\\\")(?:, )?)+\\}";
        String roomRegex = "\\[\\{\"building\":" + buildingRegex + ",\\\"room_name\":\".*?\\\",\"attributes\":\"" + attributeRegex + "\\\",\\\"id\\\":[0-9]+\\},?.*\\]";

        assertTrue(ServerCommunication.getRooms().matches(roomRegex));
    }
}
