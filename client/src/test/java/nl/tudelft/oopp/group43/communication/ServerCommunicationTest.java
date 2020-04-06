package nl.tudelft.oopp.group43.communication;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pgssoft.httpclient.HttpClientMock;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class ServerCommunicationTest {

    private final String curl = "http://localhost:8000/";

    @Test
    public void testGetBuildings() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "building").doReturn("buildings");

        assertEquals("buildings", ServerCommunication.getBuilding());
        httpClientMock.verify().get(curl + "building").called();
    }

    @Test
    public void testGetBuildingsError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "building").doReturnStatus(201);

        assertEquals("Communication with server failed", ServerCommunication.getBuilding());
        httpClientMock.verify().get(curl + "building").called();
    }

    @Test
    public void testGetRooms() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "room").doReturn("rooms");

        assertEquals("rooms", ServerCommunication.getRooms());
        httpClientMock.verify().get(curl + "room").called();
    }

    @Test
    public void testGetRoomsError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "room").doReturnStatus(201);

        assertEquals("Communication with server failed", ServerCommunication.getRooms());
        httpClientMock.verify().get(curl + "room").called();
    }

    @Test
    public void testConfirmRegistrationOk() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "registration?firstName=a&lastName=b&username=c&password=d&role=e").doReturnStatus(200);

        assertEquals("OK", ServerCommunication.confirmRegistration("a", "b", "c", "d", "e"));
        httpClientMock.verify().post(curl + "registration?firstName=a&lastName=b&username=c&password=d&role=e").called();
    }

    @Test
    public void testConfirmRegistrationExists() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "registration?firstName=a&lastName=b&username=c&password=d&role=e").doReturnStatus(201);

        assertEquals("Exists", ServerCommunication.confirmRegistration("a", "b", "c", "d", "e"));
        httpClientMock.verify().post(curl + "registration?firstName=a&lastName=b&username=c&password=d&role=e").called();
    }

    @Test
    public void testLoginTokenOk() throws ParseException {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "token?username=a&password=b").doReturn("{\"token\":\"c\"}").doReturnStatus(200);

        assertEquals("OK", ServerCommunication.loginToken("a", "b"));
        httpClientMock.verify().post(curl + "token?username=a&password=b");
    }

    @Test
    public void testLoginTokenWrong() throws ParseException {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "token?username=a&password=b").doReturnStatus(201);

        assertEquals("WRONG", ServerCommunication.loginToken("a", "b"));
        httpClientMock.verify().post(curl + "token?username=a&password=b");
    }

    @Test
    public void testGetUserInformationOk() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "name?token=a").doReturn("{\"first_name\":\"b\",\"last_name\":\"c\"}").doReturnStatus(200);

        ServerCommunication.setToken("a");
        assertEquals("OK", ServerCommunication.getUserInformation());
        httpClientMock.verify().post(curl + "name?token=a").called();
    }

    @Test
    public void testGetUserInformationStatusError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "name?token=a").doReturnStatus(201);

        ServerCommunication.setToken("a");
        assertEquals("ERROR OBTAINING INFO", ServerCommunication.getUserInformation());
        httpClientMock.verify().post(curl + "name?token=a").called();
    }

    @Test
    public void testGetUserInformationStatusException() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "name?token=a").doReturn("-");

        ServerCommunication.setToken("a");
        assertEquals("ERROR OBTAINING INFO", ServerCommunication.getUserInformation());
        httpClientMock.verify().post(curl + "name?token=a").called();
    }

    @Test
    public void testGetRoomsFromBuilding() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "room/1").doReturn("rooms");

        assertEquals("rooms", ServerCommunication.getRoomsFromBuilding("1"));
        httpClientMock.verify().get(curl + "room/1").called();
    }

    @Test
    public void testGetRoomsFromBuildingError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "room/1").doReturnStatus(201);

        assertEquals("Communication with server failed", ServerCommunication.getRoomsFromBuilding("1"));
        httpClientMock.verify().get(curl + "room/1").called();
    }

    @Test
    public void testSendDeleteBuilding() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onDelete(curl + "building/1?token=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendDeleteBuilding("1"));
        httpClientMock.verify().delete(curl + "building/1?token=1").called();
    }

    @Test
    public void testSendDeleteBuildingError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onDelete(curl + "building/1?token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.sendDeleteBuilding("1"));
        httpClientMock.verify().delete(curl + "building/1?token=1").called();
    }

    @Test
    public void testSendEditBuilding() throws ParseException {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        String jsonBuildingString = "{\"a\":\"aaa\",\"b\":\"bbb\"}";
        JSONObject jsonBuildingObj = (JSONObject) new JSONParser().parse(jsonBuildingString);

        httpClientMock.onPost(curl + "building/update?token=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendEditBuilding(jsonBuildingObj));
        httpClientMock.verify().post(curl + "building/update?token=1");
    }

    @Test
    public void testSendEditBuildingError() throws ParseException {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        String jsonBuildingString = "{\"a\":\"aaa\",\"b\":\"bbb\"}";
        JSONObject jsonBuildingObj = (JSONObject) new JSONParser().parse(jsonBuildingString);

        httpClientMock.onPost(curl + "building/update?token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.sendEditBuilding(jsonBuildingObj));
        httpClientMock.verify().post(curl + "building/update?token=1");
    }

    @Test
    public void testGetAvailableRoomHours() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        String jsonResponse = "[{\"starting_date\":\"2020-01-01T12:00:00.000+0000\",\"end_date\":\"2020-01-01T13:00:00.000+0000\"},{\"starting_date\":\"2020-01-01T16:00:00.000+0000\",\"end_date\":\"2020-01-01T17:00:00.000+0000\"}]";
        httpClientMock.onGet(curl + "reservation/1/2020-01-01/2020-01-01").doReturn(jsonResponse);

        String[] expected = new String[24];
        for (int i = 0; i < expected.length; i++) {
            expected[i] = "free";
        }
        expected[12] = expected[16] = "booked";

        assertArrayEquals(expected, ServerCommunication.getAvailableRoomHours(1, "2020-01-01", "2020-01-01"));
        httpClientMock.verify().get(curl + "reservation/1/2020-01-01/2020-01-01").called();
    }

    @Test
    public void testGetAvailableRoomHoursException() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "reservation/1/2020-01-01/2020-01-01").doReturn("nope, trying not to parse this");

        assertNull(ServerCommunication.getAvailableRoomHours(1, "2020-01-01", "2020-01-01"));
        httpClientMock.verify().get(curl + "reservation/1/2020-01-01/2020-01-01").called();
    }

    @Test
    public void testReserveRoomForHour() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "reservation?token=1&username=name").doReturn("response");

        final String tempToken = ServerCommunication.getToken();
        final String tempName = ServerCommunication.getUsername();
        ServerCommunication.setToken("1");
        ServerCommunication.setUsername("name");
        assertEquals("response", ServerCommunication.reserveRoomForHour("2020-01-01T12:00:00.000+0000", "2020-01-01T13:00:00.000+0000"));
        httpClientMock.verify().post(curl + "reservation?token=1&username=name").called();
        ServerCommunication.setToken(tempToken);
        ServerCommunication.setUsername(tempName);
    }

    @Test
    public void testReserveRoomForHourError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "reservation?token=1&username=name").doReturnStatus(201);

        final String tempToken = ServerCommunication.getToken();
        final String tempName = ServerCommunication.getUsername();
        ServerCommunication.setToken("1");
        ServerCommunication.setUsername("name");
        assertEquals("Communication with server failed", ServerCommunication.reserveRoomForHour("2020-01-01T12:00:00.000+0000", "2020-01-01T13:00:00.000+0000"));
        httpClientMock.verify().post(curl + "reservation?token=1&username=name").called();
        ServerCommunication.setToken(tempToken);
        ServerCommunication.setUsername(tempName);
    }

    @Test
    public void testSendAddBuildingOk() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "building?token=1").doReturn("NEW BUILDING: <building_name>");

        ServerCommunication.setToken("1");
        JSONObject obj = new JSONObject();
        assertEquals("OK", ServerCommunication.sendAddBuilding(obj));
        httpClientMock.verify().post(curl + "building?token=1").called();
    }

    @Test
    public void testSendAddBuildingNotOK() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "building?token=1").doReturn("BUILDING WITH NUMBER: <building_number> ALREADY EXISTS.");

        ServerCommunication.setToken("1");
        JSONObject obj = new JSONObject();
        assertEquals("NOT OK", ServerCommunication.sendAddBuilding(obj));
        httpClientMock.verify().post(curl + "building?token=1").called();
    }

    @Test
    public void testSendAddBuildingError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "building?token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        JSONObject obj = new JSONObject();
        assertEquals("Communication with server failed", ServerCommunication.sendAddBuilding(obj));
        httpClientMock.verify().post(curl + "building?token=1").called();
    }

    @Test
    public void testGetRoomFilter() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "filter?"
                + "blinds=a&desktop=b&projector=c&chalkBoard=d&microphone=e&smartBoard=f&"
                + "whiteBoard=g&powerSupply=h&soundInstallation=i&wheelChair=j&employee=true&minSpace=m&type=n").doReturn("rooms");

        assertEquals("rooms", ServerCommunication.getRoomFilter("a", "b", "c", "d","e","f", "g", "h", "i", "j", "true", "m", "n"));
        httpClientMock.verify().get(curl + "filter?"
                + "blinds=a&desktop=b&projector=c&chalkBoard=d&microphone=e&smartBoard=f&"
                + "whiteBoard=g&powerSupply=h&soundInstallation=i&wheelChair=j&employee=true&minSpace=m&type=n").called();
    }

    @Test
    public void testGetRoomFilterError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "filter?"
                + "blinds=a&desktop=b&projector=c&chalkBoard=d&microphone=e&smartBoard=f&"
                + "whiteBoard=g&powerSupply=h&soundInstallation=i&wheelChair=j&employee=true&minSpace=m&type=n").doReturnStatus(201);

        assertEquals("Communication with server failed", ServerCommunication.getRoomFilter("a", "b", "c", "d","e","f", "g", "h", "i", "j", "true", "m", "n"));
        httpClientMock.verify().get(curl + "filter?"
                + "blinds=a&desktop=b&projector=c&chalkBoard=d&microphone=e&smartBoard=f&"
                + "whiteBoard=g&powerSupply=h&soundInstallation=i&wheelChair=j&employee=true&minSpace=m&type=n").called();
    }

    @Test
    public void testGetBikeRenting() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "bike/1").doReturn("bike");

        assertEquals("bike", ServerCommunication.getBikeRenting("1"));
        httpClientMock.verify().get(curl + "bike/1").called();
    }

    @Test
    public void testGetBikeRentingError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "bike/1").doReturnStatus(201);

        assertEquals("Communication with server failed", ServerCommunication.getBikeRenting("1"));
        httpClientMock.verify().get(curl + "bike/1").called();
    }

    @Test
    public void testSendBikeRenting() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "bikeReservation/create?BikeId=1&token=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendBikeRenting("1"));
        httpClientMock.verify().post(curl + "bikeReservation/create?BikeId=1&token=1").called();
    }

    @Test
    public void testSendBikeRentingError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "bikeReservation/create?BikeId=1&token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.sendBikeRenting("1"));
        httpClientMock.verify().post(curl + "bikeReservation/create?BikeId=1&token=1").called();
    }

    @Test
    public void testReserveBike() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "bikeReservation/createWithTime?BikeId=1&token=1&time=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.reserveBike("1", "1"));
        httpClientMock.verify().post(curl + "bikeReservation/createWithTime?BikeId=1&token=1&time=1").called();
    }

    @Test
    public void testReserveBikeError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "bikeReservation/createWithTime?BikeId=1&token=1&time=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.reserveBike("1", "1"));
        httpClientMock.verify().post(curl + "bikeReservation/createWithTime?BikeId=1&token=1&time=1").called();
    }

    @Test
    public void testGetBikesRentedByUser() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "bikeReservation/notReturned?token=1").doReturn("bikes");

        ServerCommunication.setToken("1");
        assertEquals("bikes", ServerCommunication.getBikesRentedByUser());
        httpClientMock.verify().get(curl + "bikeReservation/notReturned?token=1").called();
    }

    @Test
    public void testGetBikesRentedByUserError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "bikeReservation/notReturned?token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.getBikesRentedByUser());
        httpClientMock.verify().get(curl + "bikeReservation/notReturned?token=1").called();
    }

    @Test
    public void testReturnBikeOK() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "bikeReservation/return?reservationId=1&token=1&building=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.returnBike("1", "1"));
        httpClientMock.verify().post(curl + "bikeReservation/return?reservationId=1&token=1&building=1").called();
    }

    @Test
    public void testReturnBikeWrong() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "bikeReservation/return?reservationId=1&token=1&building=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("WRONG", ServerCommunication.returnBike("1", "1"));
        httpClientMock.verify().post(curl + "bikeReservation/return?reservationId=1&token=1&building=1").called();
    }

    @Test
    public void testReturnBikeError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "bikeReservation/return?reservationId=1&token=1&building=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("WRONG", ServerCommunication.returnBike("1", "1"));
        httpClientMock.verify().post(curl + "bikeReservation/return?reservationId=1&token=1&building=1").called();
    }

    @Test
    public void testReturnBikeNull() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.returnBike("1", "1"));
        httpClientMock.verify().post(curl + "bikeReservation/return?reservationId=1&token=1&building=1").called();
    }

    @Test
    public void testSendChangePasswordOK() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "changePassword?oldPassword=a&newPassword=b&token=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendChangePassword("a", "b"));
        httpClientMock.verify().post(curl + "changePassword?oldPassword=a&newPassword=b&token=1").called();
    }

    @Test
    public void testSendChangePasswordNotOK() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "changePassword?oldPassword=a&newPassword=b&token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("NOT OK", ServerCommunication.sendChangePassword("a", "b"));
        httpClientMock.verify().post(curl + "changePassword?oldPassword=a&newPassword=b&token=1");
    }

    @Test
    public void testSendChangePasswordError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "changePassword?oldPassword=a&newPassword=b&token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("NOT OK", ServerCommunication.sendChangePassword("a", "b"));
        httpClientMock.verify().post(curl + "changePassword?oldPassword=a&newPassword=b&token=1").called();
    }

    @Test
    public void testSendChangePasswordNull() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.sendChangePassword("a", "b"));
        httpClientMock.verify().post(curl + "changePassword?oldPassword=a&newPassword=b&token=1").called();
    }

    @Test
    public void testSendRoom() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "room?token=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendRoom(new JSONObject()));
        httpClientMock.verify().post(curl + "room?token=1").called();
    }

    @Test
    public void testSendRoomError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "room?token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("NOT OK", ServerCommunication.sendRoom(new JSONObject()));
        httpClientMock.verify().post(curl + "room?token=1").called();
    }

    @Test
    public void testSendRoomNull() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.sendRoom(new JSONObject()));
        httpClientMock.verify().post(curl + "room?token=1").called();
    }

    @Test
    public void testSendDeleteRoom() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onDelete(curl + "room/1?token=1").doReturnStatus(200);

        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendDeleteRoom("1"));
        httpClientMock.verify().delete(curl + "room/1?token=1").called();
    }

    @Test
    public void testSendDeleteRoomError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onDelete(curl + "room/1?token=1").doReturnStatus(201);

        ServerCommunication.setToken("1");
        assertEquals("NOT OK", ServerCommunication.sendDeleteRoom("1"));
        httpClientMock.verify().delete(curl + "room/1?token=1").called();
    }

    @Test
    public void testSendDeleteRoomNull() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.sendDeleteRoom("1"));
        httpClientMock.verify().delete(curl + "room/1?token=1").called();
    }

    @Test
    public void testGetFood() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "foodProduct").doReturn("test");

        ServerCommunication.setToken("1");
        assertEquals("test", ServerCommunication.getFood());
        httpClientMock.verify().get(curl + "foodProduct").called();
    }

    @Test
    public void testGetFoodByBuilding() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "buildingFoodProduct/moreDetails?building=1").doReturn("test");

        ServerCommunication.setToken("1");
        assertEquals("test", ServerCommunication.getFoodByBuilding("1"));
        httpClientMock.verify().get(curl + "buildingFoodProduct/moreDetails?building=1").called();
    }

    @Test
    public void testGetReservationsFromDate() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "reservation/0000-00-00/0000-00-00").doReturn("[]");

        final String tempToken = ServerCommunication.getToken();
        ServerCommunication.setToken("1");
        assertEquals("[]", ServerCommunication.getReservationsByDate("0000-00-00", "0000-00-00"));
        httpClientMock.verify().get(curl + "reservation/0000-00-00/0000-00-00").called();
        ServerCommunication.setToken(tempToken);
    }

    @Test
    public void testGetReservationsFromDateErrorCode() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "reservation/0000-00-00/0000-00-00").doReturnStatus(201);

        final String tempToken = ServerCommunication.getToken();
        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.getReservationsByDate("0000-00-00", "0000-00-00"));
        httpClientMock.verify().get(curl + "reservation/0000-00-00/0000-00-00").called();
        ServerCommunication.setToken(tempToken);
    }

    @Test
    public void testGetRoomName() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "room/getName/0").doReturn("room");

        final String tempToken = ServerCommunication.getToken();
        ServerCommunication.setToken("1");
        assertEquals("room", ServerCommunication.getRoomName(0));
        httpClientMock.verify().get(curl + "room/getName/0").called();
        ServerCommunication.setToken(tempToken);
    }

    @Test
    public void testGetRoomNameErrorCode() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "room/getName/0").doReturnStatus(201);

        final String tempToken = ServerCommunication.getToken();
        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.getRoomName(0));
        httpClientMock.verify().get(curl + "room/getName/0").called();
        ServerCommunication.setToken(tempToken);
    }

    @Test
    public void testSendBuildingException() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "building/exception?token=1").doReturn("[]");

        final String tempToken = ServerCommunication.getToken();
        ServerCommunication.setToken("1");
        JSONObject obj = new JSONObject();
        Long buildingNumber = Long.valueOf(9090);
        String startDate = "2020-04-20T00:00:00";
        String endDate = "2020-04-20T23:00:00";
        obj.put("buildingNumber", buildingNumber);
        obj.put("startingDate", startDate);
        obj.put("endDate", endDate);
        assertEquals("OK", ServerCommunication.sendBuildingException(obj));
        httpClientMock.verify().post(curl + "building/exception?token=1").called();
        ServerCommunication.setToken(tempToken);
    }

    @Test
    public void testSendBuildingExceptionErrorCode() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "building/exception?token=1").doReturnStatus(201);

        final String tempToken = ServerCommunication.getToken();
        ServerCommunication.setToken("1");
        JSONObject obj = new JSONObject();
        Long buildingNumber = Long.valueOf(9090);
        String startDate = "2020-04-20T00:00:00";
        String endDate = "2020-04-20T23:00:00";
        obj.put("buildingNumber", buildingNumber);
        obj.put("startingDate", startDate);
        obj.put("endDate", endDate);
        assertEquals("NOT OK", ServerCommunication.sendBuildingException(obj));
        httpClientMock.verify().post(curl + "building/exception?token=1").called();
        ServerCommunication.setToken(tempToken);
    }
    
    @Test
    public void testSendDeleteReservation() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onDelete(curl + "reservation/1?token=1").doReturnStatus(200);
        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.sendDeleteReservation("1"));
        httpClientMock.verify().delete(curl + "reservation/1?token=1").called();
    }

    @Test
    public void testSendDeleteReservationError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onDelete(curl + "reservation/1?token=1").doReturnStatus(201);
        ServerCommunication.setToken("1");
        assertEquals("NOT OK", ServerCommunication.sendDeleteReservation("1"));
        httpClientMock.verify().delete(curl + "reservation/1?token=1").called();
    }

    @Test
    public void testSendDeleteReservationNull() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        ServerCommunication.setToken("1");
        assertEquals("Communication with server failed", ServerCommunication.sendDeleteReservation("1"));
        httpClientMock.verify().delete(curl + "reservation/1?token=1").called();
    }

    @Test
    public void testGetRoomByRoomId() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "room/getName/1").doReturn("rooms");

        assertEquals("rooms", ServerCommunication.getRoomName((long) 1));
        httpClientMock.verify().get(curl + "room/getName/1").called();
    }

    @Test
    public void testGetReservationsByUser() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "reservation/test@tudelft.nl").doReturn("reservations");
        ServerCommunication.setUsername("test@tudelft.nl");
        assertEquals("reservations", ServerCommunication.getReservationsByUser());
        httpClientMock.verify().get(curl + "reservation/test@tudelft.nl").called();
    }

    @Test
    public void testGetReservationsByUserError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "reservation/test@tudelft.nl").doReturnStatus(201);
        ServerCommunication.setUsername("test@tudelft.nl");
        assertEquals("Communication with server failed", ServerCommunication.getReservationsByUser());
        httpClientMock.verify().get(curl + "reservation/test@tudelft.nl").called();
    }

    @Test
    public void testCreateFoodOrder() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "foodOrder?token=1&order=1").doReturnStatus(200);
        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.createFoodOrder("1", false, "1"));
        httpClientMock.verify().post(curl + "foodOrder?token=1&order=1").called();
    }

    @Test
    public void testCreateFoodOrderAway() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "foodOrder?token=1&order=1&away=true").doReturnStatus(200);
        ServerCommunication.setToken("1");
        assertEquals("OK", ServerCommunication.createFoodOrder("1", true, "1"));
        httpClientMock.verify().post(curl + "foodOrder?token=1&order=1&away=true").called();
    }

    @Test
    public void testCreateFoodOrderError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onPost(curl + "foodOrder?token=1&order=1").doReturnStatus(201);
        ServerCommunication.setToken("1");
        assertEquals("WRONG", ServerCommunication.createFoodOrder("1", false, "1"));
        httpClientMock.verify().post(curl + "foodOrder?token=1&order=1").called();
    }

    @Test
    public void testGetBuildingException() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "building/exception/1").doReturn("test");
        assertEquals("test", ServerCommunication.getBuildingException(1L));
        httpClientMock.verify().get(curl + "building/exception/1").called();
    }

    @Test
    public void testGetBuildingExceptionError() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);

        httpClientMock.onGet(curl + "building/exception/1").doReturnStatus(201);
        assertEquals("Communication with the server failed", ServerCommunication.getBuildingException(1L));
        httpClientMock.verify().get(curl + "building/exception/1").called();
    }

    /*
    @Test
    public void testTemplate() {
        HttpClientMock httpClientMock = new HttpClientMock();
        ServerCommunication.setClient(httpClientMock);
    }
    */
}
