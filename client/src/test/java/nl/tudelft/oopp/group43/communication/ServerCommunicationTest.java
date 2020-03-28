package nl.tudelft.oopp.group43.communication;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.pgssoft.httpclient.HttpClientMock;
import javafx.application.Application;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.BeforeClass;
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
import java.net.http.HttpResponse;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ServerCommunicationTest {

    private final String cURL = "http://localhost:8000/";

   @Test
    public void testGetBuildings() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(cURL + "building").doReturn("buildings");

        assertEquals("buildings", ServerCommunication.getBuilding());
        httpClientMock.verify().get(cURL + "building").called();
    }

    @Test
    public void testGetRooms() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(cURL + "room").doReturn("rooms");

        assertEquals("rooms", ServerCommunication.getRooms());
        httpClientMock.verify().get(cURL + "room").called();
    }

    @Test
    public void testConfirmRegistrationOK() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "registration?firstName=a&lastName=b&username=c&password=d&role=e").doReturnStatus(200);

        assertEquals("OK", ServerCommunication.confirmRegistration("a", "b", "c", "d", "e"));
        httpClientMock.verify().post(cURL + "registration?firstName=a&lastName=b&username=c&password=d&role=e").called();
    }

    @Test
    public void testConfirmRegistrationExists() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "registration?firstName=a&lastName=b&username=c&password=d&role=e").doReturnStatus(201);

        assertEquals("Exists", ServerCommunication.confirmRegistration("a", "b", "c", "d", "e"));
        httpClientMock.verify().post(cURL + "registration?firstName=a&lastName=b&username=c&password=d&role=e").called();
    }

    @Test
    public void testLoginToken() throws ParseException {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "token?username=a&password=b").doReturn("{\"token\":\"c\"}").doReturnStatus(200);

        assertEquals("OK", ServerCommunication.loginToken("a", "b"));
        httpClientMock.verify().post(cURL + "token?username=a&password=b");
    }

    @Test
    public void testGetUserInformation() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "name?token=a").doReturn("{\"first_name\":\"b\",\"last_name\":\"c\"}").doReturnStatus(200);

        ServerCommunication.setToken("a");
        assertEquals("OK", ServerCommunication.getUserInformation());
        httpClientMock.verify().post(cURL + "name?token=a").called();
    }

    @Test
    public void testGetRoomsFromBuilding() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(cURL + "room/1").doReturn("rooms");

        assertEquals("rooms", ServerCommunication.getRoomsFromBuilding("1"));
        httpClientMock.verify().get(cURL + "room/1").called();
    }

    @Test
    public void testSendDeleteBuilding() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onDelete(cURL + "building/1").doReturnStatus(200);

        assertEquals("OK", ServerCommunication.sendDeleteBuilding("1"));
        httpClientMock.verify().delete(cURL + "building/1").called();
    }

    @Test
    public void testSendEditBuilding() throws ParseException {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        String jsonBuildingString = "{\"a\":\"aaa\",\"b\":\"bbb\"}";
        JSONObject jsonBuildingObj = (JSONObject) new JSONParser().parse(jsonBuildingString);

        httpClientMock.onPost(cURL + "building/update").doReturnStatus(200);

        assertEquals("OK", ServerCommunication.sendEditBuilding(jsonBuildingObj));
        httpClientMock.verify().post(cURL + "building/update");
    }

    @Test
    public void testGetAvailableRoomHours() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        String jsonResponse = "[{\"starting_date\":\"2020-01-01T12:00:00.000+0000\",\"end_date\":\"2020-01-01T13:00:00.000+0000\"},{\"starting_date\":\"2020-01-01T16:00:00.000+0000\",\"end_date\":\"2020-01-01T17:00:00.000+0000\"}]";
        httpClientMock.onGet(cURL + "reservation/1/2020-01-01/2020-01-01").doReturn(jsonResponse);

        String[] expected = new String[24];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = "free";
        }
        expected[12] = expected[16] = "booked";

        assertArrayEquals(expected, ServerCommunication.getAvailableRoomHours(1, "2020-01-01", "2020-01-01"));
        httpClientMock.verify().get(cURL + "reservation/1/2020-01-01/2020-01-01").called();
    }

    @Test
    public void testReserveRoomForHour() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "reservation").doReturn("response");

        assertEquals("response", ServerCommunication.reserveRoomForHour("2020-01-01T12:00:00.000+0000", "2020-01-01T13:00:00.000+0000"));
        httpClientMock.verify().post(cURL + "reservation").called();
    }

    @Test
    public void testSendAddBuildingOK() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "building").doReturn("NEW BUILDING: <building_name>");

        JSONObject obj = new JSONObject();
        assertEquals("OK", ServerCommunication.sendAddBuilding(obj));
        httpClientMock.verify().post(cURL + "building").called();
    }

    @Test
    public void testSendAddBuildingNOTOK() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "building").doReturn("BUILDING WITH NUMBER: <building_number> ALREADY EXISTS.");

        JSONObject obj = new JSONObject();
        assertEquals("NOT OK", ServerCommunication.sendAddBuilding(obj));
        httpClientMock.verify().post(cURL + "building").called();
    }

    @Test
    public void testGetRoomFilter() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(cURL + "filter?" +
                "blinds=a&desktop=b&projector=c&chalkBoard=d&microphone=e&smartBoard=f&" +
                "whiteBoard=g&powerSupply=h&soundInstallation=i&wheelChair=j&minSpace=k").doReturn("rooms");

        assertEquals("rooms", ServerCommunication.getRoomFilter("a", "b", "c", "d","e","f", "g", "h", "i", "j", "k"));
        httpClientMock.verify().get(cURL + "filter?" +
                "blinds=a&desktop=b&projector=c&chalkBoard=d&microphone=e&smartBoard=f&" +
                "whiteBoard=g&powerSupply=h&soundInstallation=i&wheelChair=j&minSpace=k").called();
    }

    @Test
    public void testGetBikeRenting() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(cURL + "bike/1").doReturn("bike");

        assertEquals("bike", ServerCommunication.getBikeRenting("1"));
        httpClientMock.verify().get(cURL + "bike/1").called();
    }

    @Test
    public void testSendBikeRenting() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "bikeReservation/create?BikeId=1&token=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendBikeRenting("1"));
        httpClientMock.verify().post(cURL + "bikeReservation/create?BikeIde=1&token=1");
    }

    @Test
    public void testSendChangePassword() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(cURL + "changePassword?oldPassword=a&newPassword=b&token=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendChangePassword("a", "b"));
        httpClientMock.verify().post(cURL + "changePassword?oldPassword=a&newPassword=b&token=1");
    }

    /*
    @Test
    public void testTemplate() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);
    }
    */
}
