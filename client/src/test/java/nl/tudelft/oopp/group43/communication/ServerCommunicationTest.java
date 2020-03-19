package nl.tudelft.oopp.group43.communication;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.*;

import com.github.paweladamski.httpclientmock.HttpClientMock;
import javafx.application.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ServerCommunicationTest {

  //  @MockBean
  //  private


    private final String cURL = "http://localhost:8000/";


    @Test
    public void testGetBuildingsNull() {
        assertNotNull(ServerCommunication.getBuilding());
    }

   @Test
    public void testGetBuildings() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(cURL + "building")).build();
        String building  = "{\"building_number\":0,\"building_name\":\"Pathe Delft\",\"address\":\"Vesteplein 5 2611 WG Delft\",\"opening_hours\":\"{\\\"fr\\\": \\\"07:00-19:00\\\", \\\"mo\\\": \\\"07:00-22:00\\\", \\\"sa\\\": \\\"closed\\\", \\\"su\\\": \\\"closed\\\", \\\"th\\\": \\\"07:00-22:00\\\", \\\"tu\\\": \\\"07:00-22:00\\\", \\\"we\\\": \\\"07:00-22:00\\\"}\"}";
     //   System.out.println(building);
       //HttpClientMock  httpClientMock = new HttpClientMock();

       //httpClientMock.onGet(cURL + "building").doReturn(building);


      // httpClientMock.verify().get("/login?user=john").called();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse <String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
         Mockito.when(request).thenReturn((HttpRequest) response);
        System.out.println(ServerCommunication.getBuilding());

    }



}
