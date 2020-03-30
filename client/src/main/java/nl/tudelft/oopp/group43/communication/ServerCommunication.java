package nl.tudelft.oopp.group43.communication;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import nl.tudelft.oopp.group43.classes.ReservationConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class ServerCommunication {

    private static final String cURL = "http://localhost:8000/";
    private static HttpClient client = HttpClient.newBuilder().build();
    private static String token = "invalid";
    private static String username = "";
    private static String firstName = "";
    private static String lastName = "";
    private static String role = "";

    /**
     * Gets the value of token.
     *
     * @return the value of token
     */
    public static String getToken() {
        return token;
    }


    /**
     * Change the actual value of the token with a new one.
     *
     * @param newToken - the new value of the token
     */
    public static void setToken(String newToken) {
        token = newToken;
    }

    /**
     * Sets the value of the HttpClient.
     * @param client the httpClient
     */

    public static void setClient(HttpClient client) {
        ServerCommunication.client = client;
    }

    /**
     * Returns the username.
     *
     * @return a String with in it the username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Sets the username for the client.
     *
     * @param newUsername the username
     */
    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    /**
     * Gets the role of the user.
     * @return the role in String format
     */
    public static String getRole() {
        return role;
    }

    /**
     * Setter for the user role.
     * @param newRole the role of the user.
     */
    public static void setRole(String newRole) {
        role = newRole;
    }

    /**
     * Getter for the name of the user.
     * @return the name of the user
     */
    public static String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the name of the user.
     * @param newName the name of the user
     */
    public static void setFirstName(String newName) {
        firstName = newName;
    }

    /**
     * Setter for the name of the user.
     * @param newName the name of the user
     */
    public static void setLastName(String newName) {
        lastName = newName;
    }

    /**
     * Getter for the name of the user.
     * @return the name of the user
     */
    public static String getLastName() {
        return lastName;
    }

    /**
     * Sends a HTTP GET request to the server.
     *
     * @param url the url for the GET request.
     * @return the HttpResponse object that was returned from the server.
     */
    private static HttpResponse<String> get(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = sendRequest(request);

        return response;
    }

    /**
     * Sends a HTTP POST request to the server.
     *
     * @param url the url for the POST request.
     * @return the HttpResponse object that was returned from the server.
     */
    private static HttpResponse<String> post(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = sendRequest(request);

        return response;
    }

    /**
     * Sends a HTTP POST request to the server.
     *
     * @param url     the url for the POST request.
     * @param body    the body of the post request.
     * @param headers the headers of the POST request.
     *                this collection must alternate as header names and header values
     * @return the HttpResponse object that was returned from the server.
     */
    private static HttpResponse<String> post(String url, String body, String... headers) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(url))
                .headers(headers)
                .build();

        HttpResponse<String> response = sendRequest(request);

        return response;
    }

    /**
     * Sends a HTTP DELETE request to the server.
     *
     * @param url the url for the DELETE request.
     * @return the HttpResponse object that was returned from the server.
     */
    private static HttpResponse<String> delete(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = sendRequest(request);

        return response;
    }

    /**
     * Sends a HTTP Request to the server.
     *
     * @param request the HTTP request.
     * @return the HttpResponse object that was returned from the server.
     */
    private static HttpResponse<String> sendRequest(HttpRequest request) {
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Retrieves all buildings from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getBuilding() {
        String url = cURL + "building";

        HttpResponse<String> response = get(url);

        if (response == null) {
            return "Communication with server failed";
        }

        return response.body();
    }

    /**
     * Retrieves all reservations from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getReservations() {
        String url = cURL + "reservation";

        HttpResponse<String> response = get(url);

        if (response == null) {
            return "Communication with server failed";
        }

        return response.body();
    }

    /**
     * Retrieves all reservations from the server by current user.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */

    public static String getReservationsByUser() {
        String url = cURL + "reservation/" + getUsername();

        HttpResponse<String> response = get(url);

        if (response == null) {
            return "Communication with server failed";
        }

        return response.body();
    }

    /**
     * Gets the rooms from the database.
     *
     * @return A String with in it a JSONArray or Object of all rooms
     */
    public static String getRooms() {
        String url = cURL + "room";

        HttpResponse<String> response = get(url);

        if (response == null) {
            return "Communication with server failed";
        }

        return response.body();
    }

    /**
     * Tries to add a new user to the User Database and receives some messages to know if the operation was successful or not.
     *
     * @param firstName - the first name of the user
     * @param lastName  - the last name of the user
     * @param username  - the email of the user
     * @param password  - the password of the user
     * @param role      -  the role of the user
     * @return a String which can have 3 values:
     *         - "Communication with server failed" if the communication with the server failed
     *         - "OK" if the user could be added to the Users Database
     *         - "Exists" if the email already exists
     */
    public static String confirmRegistration(String firstName, String lastName, String username, String password, String role) {
        String url = cURL + "registration?";
        url = url + "firstName=" + utf8EncodeValue(firstName) + "&";
        url = url + "lastName=" + utf8EncodeValue(lastName) + "&";
        url = url + "username=" + utf8EncodeValue(username) + "&";
        url = url + "password=" + utf8EncodeValue(password) + "&";
        url = url + "role=" + utf8EncodeValue(role);

        HttpResponse<String> response = post(url);

        if (response == null) {
            return "Communication with server failed";
        }

        if (response.statusCode() != 200) {
            return "Exists";
        } else {
            return "OK";
        }
    }

    /**
     * Tries to login the user and receives some messages to know if the operation was successful or not.
     * + If the operation was successful it also change the token
     *
     * @param username - the email of the user
     * @param password - the password of the user
     * @return a String which can have 3 values:
     *         - "Communication with server failed" if the communication with the server failed
     *         - "OK" if the user could be added to the Users Database
     *         - "WRONG" if the email or password were wrong
     * @throws ParseException - if something goes wrong with the JSON Parser
     */
    public static String loginToken(String username, String password) throws ParseException {
        String url = cURL + "token?";
        url = url + "username=" + utf8EncodeValue(username) + "&";
        url = url + "password=" + utf8EncodeValue(password) + "&";

        HttpResponse<String> response = post(url);

        if (response == null) {
            return "Communication with server failed";
        }

        if (response.statusCode() != 200) {
            return "WRONG";
        } else {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(response.body());
            setToken((String) obj.get("token"));

            return "OK";
        }

    }

    /**
     * Method for getting and storing the user information: name and role.
     * @return the status of obtaining the information.
     */
    public static String getUserInformation() {
        String url = cURL + "name?token=" + utf8EncodeValue(token);

        HttpResponse<String> response = post(url);

        if (response.statusCode() != 200) {
            return "ERROR OBTAINING INFO";
        } else {
            JSONParser parser = new JSONParser();
            try {
                JSONObject obj = (JSONObject) parser.parse(response.body());
                setFirstName((String) obj.get("first_name"));
                setLastName((String) obj.get("last_name"));
                setRole((String) obj.get("role"));
                //System.out.println(response.body());

                return "OK";
            } catch (ParseException e) {
                e.printStackTrace();
                System.out.println(response.body());
                return "ERROR OBTAINING INFO";
            }
        }
    }

    /**
     * Gets the rooms from a specific building from the database through the API.
     *
     * @param buildingID the ID of the building from which we want all the rooms
     * @return a String with a JSONArray in which all rooms from that building are present
     */
    public static String getRoomsFromBuilding(String buildingID) {
        String url = cURL + "room/" + buildingID;

        HttpResponse<String> response = get(url);

        if (response == null) {
            return "Communication with server failed";
        }

        return response.body();
    }

    /**
     * Sends the buildID for deleting it.
     *
     * @param buildID - the id of the building
     * @return a String which can have 2 values:
     *         - "Communication with server failed" if the communication with the server failed
     *         - "OK" if the building could be deleted from the Buildings Database
     */
    public static String sendDeleteBuilding(String buildID) {
        String url = cURL + "building/" + buildID;

        HttpResponse<String> response = delete(url);

        if (response == null) {
            return "Communication with server failed";
        }

        return "OK";

    }

    /**
     * Sends the building as a JSON Object for edit it.
     *
     * @param obj - JSON Object represents  the building
     * @return - "Communication with server failed" if the communication with the server failed
     *         - "OK" if the building could be edit from the Buildings Database
     */
    public static String sendEditBuilding(JSONObject obj) {
        String url = cURL + "building/update";

        HttpResponse<String> response = post(url, obj.toJSONString(), "Content-Type", "application/json;charset=UTF-8");

        if (response == null) {
            return "Communication with server failed";
        }

        return "OK";
    }

    /**
     * Gets all available/non-booked hours from the database using the format /{roomID}/{startDate}/{endDate}.
     *
     * @param roomID    the ID of the room
     * @param startDate the start date for the timeframe in between which we want to receive all booked hours
     * @param endDate   the end date for the timeframe in between which we want to receive all booked hours
     * @return A string array with in it for each hour of the day a "booked" or "free"
     */
    public static String[] getAvailableRoomHours(long roomID, String startDate, String endDate) {
        String url = cURL + "reservation/" + roomID + "/" + startDate + "/" + endDate;
        HttpResponse<String> response = get(url);

        String[] hours = new String[24];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = "free";
        }

        if (response != null && response.body().length() > 2 && response.statusCode() == 200) {
            try {
                JSONParser json = new JSONParser();
                JSONArray arr = (JSONArray) json.parse(response.body());

                for (int i = 0; i < arr.size(); i++) {
                    LocalDateTime hour = LocalDateTime.parse(((String) ((JSONObject) arr.get(i)).get("starting_date")).substring(0, 16), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                    hours[hour.getHour()] = "booked";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return hours;
    }

    /**
     * Books a room for the specified hours in ISO8601 format.
     *
     * @param startHour the starting hour
     * @param endHour   the ending hour
     * @return Status with success or failure
     */
    public static String reserveRoomForHour(String startHour, String endHour) {
        // form parameters
        Map<Object, Object> data = new HashMap<>();
        data.put("user", "{\"username\":\"" + username + "\"}");
        data.put("room_id", ReservationConfig.getSelectedRoom());
        data.put("starting_date", startHour);
        data.put("end_date", endHour);

        String url = cURL + "reservation";

        HttpResponse<String> response = post(url, buildFormDataFromMap(data),
                "User-Agent", "Java 11 HttpClient Bot",
                "Content-Type", "application/json");

        if (response == null) {
            return "Communication with server failed";
        }

        return response.body();
    }

    /**
     * Takes a map as input and translates the pairs to a json.
     *
     * @param data the map containing the json pairs/values
     * @return a string with the json content
     */
    private static String buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        builder.append("{");
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 1) {
                builder.append(",");
            }
            builder.append("\"" + entry.getKey().toString() + "\"");
            builder.append(":");
            if (entry.getKey().toString() == "room_id" || entry.getKey().toString() == "user") {
                builder.append(entry.getValue());
            } else {
                builder.append("\"" + entry.getValue().toString() + "\"");
            }
        }
        builder.append("}");

        System.out.println(builder.toString());
        return builder.toString();
    }

    /**
     * Sends the building as a JSON Object for add it.
     *
     * @param obj - JSON Object represents  the building
     * @return - "Communication with server failed" if the communication with the server failed
     *         - "OK" if the building could be added to the Buildings Database
     *         - "NOT OK" if the building could not be added to the Buildings Database
     */
    public static String sendAddBuilding(JSONObject obj) {
        String url = cURL + "building";

        HttpResponse<String> response = post(url, obj.toJSONString(),
                "Content-Type", "application/json;charset=UTF-8");

        if (response == null) {
            return "Communication with server failed";
        }

        //If the building already exists, then you will receive from the server the message ""BUILDING WITH NUMBER: " + newBuilding.getBuildingNumber() + " ALREADY EXISTS.";"
        //else you will receive from the server "NEW BUILDING: " + newBuilding.getBuildingName();
        if (response.body().substring(0, 1).equals("B")) {
            return "NOT OK";

        } else {
            return "OK";
        }
    }

    /**
     * Retrieves all rooms from the serve with the specified attributes.
     *
     * @param blinds            - String which represents if the room has blinds or not.
     * @param desktop           - String which represents if the room has desktop or not.
     * @param projector         - String which represents if the room has projector or not.
     * @param chalkBoard        - String which represents if the room has chalk board or not.
     * @param microphone        - String which represents if the room has microphone or not.
     * @param smartBoard        - String which represents if the room has smart board or not.
     * @param whiteBoard        - String which represents if the room has white board or not.
     * @param powerSupply       - String which represents if the room has power supply or not.
     * @param soundInstallation - String which represents if the room has soundInstallation or not.
     * @param wheelChair        - String which represents if the room has the facilities for people with wheel chair or not.
     * @param space             - String which represents the minimum space capacity of the room (nr. of people).
     * @return a String which can have 2 values:
     *         - "Communication with server failed" if the communication with the server failed.
     *         - the rooms selected by the filters.
     */
    public static String getRoomFilter(String blinds, String desktop, String projector, String chalkBoard, String microphone, String smartBoard, String whiteBoard, String powerSupply, String soundInstallation, String wheelChair, String space) {
        String url = cURL + "filter?";
        url = url + "blinds=" + blinds;
        url = url + "&desktop=" + desktop;
        url = url + "&projector=" + projector;
        url = url + "&chalkBoard=" + chalkBoard;
        url = url + "&microphone=" + microphone;
        url = url + "&smartBoard=" + smartBoard;
        url = url + "&whiteBoard=" + whiteBoard;
        url = url + "&powerSupply=" + powerSupply;
        url = url + "&soundInstallation=" + soundInstallation;
        url = url + "&wheelChair=" + wheelChair;
        url = url + "&minSpace=" + space;
        HttpResponse<String> response = get(url);

        if (response == null) {
            return "Communication with server failed";
        }

        return response.body();
    }

    /**
     * Encodes a value to standard utf8 format.
     *
     * @param value - String value that needs to be encoded
     * @return the utf8-encoded String
     */
    private static String utf8EncodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    /**
     * Gives the id of the chosen buildings and receives the list of bikes available for that building.
     *
     * @param buildingID - String which represents the id of the chosen building
     * @return a String which can have 2 values:
     *         - "Communication with server failed" if the communication with the server failed.
     *         - a list of bikes for that building.
     */
    public static String getBikeRenting(String buildingID) {
        String url = cURL + "bike/" + buildingID;
        HttpResponse<String> response = get(url);
        if (response == null) {
            return "Communication with server failed";
        }

        return response.body();
    }

    /**
     * Gives the id of the chosen bike for renting it.
     *
     * @param bikeId - String which represents the id of the chosen bike.
     * @return a String which can have 2 values:
     *        - "Communication with server failed" if the communication with the server failed.
     *        - "OK" if the server receives the information.
     */
    public static String sendBikeRenting(String bikeId) {
        String url = cURL + "bikeReservation/create?BikeId=" + bikeId + "&token=" + getToken();
        HttpResponse<String> response = post(url);
        if (response == null) {
            return "Communication with server failed";
        }

        return "OK";

    }

    /**
     * Gives the new password of the user account.
     *
     * @param oldPassword - String which represents the old password of the account
     * @param newPassword - String whcih represents the new password of the account
     * @return a String which can have 3 values:
     *         - "Communication with server failed" if the communication with the server failed.
     *         - "OK" if the server could change the password.
     *         - "NOT OK" if the server could not change the password.
     */
    public static String sendChangePassword(String oldPassword, String newPassword) {
        String url = cURL + "changePassword?oldPassword=" + oldPassword + "&newPassword=" + newPassword + "&token=" + getToken();
        HttpResponse<String> response = post(url);

        if (response == null) {
            return "Communication with server failed";
        }

        if (response.statusCode() != 200) {
            return "NOT OK";
        } else {
            return "OK";
        }
    }

    /**
     * Gives the room which has to be added or edited.
     * @param obj - JSONObject which represents the room
     * @return a String which can have 3 values:
     *        - "Communication with server failed" if the communication with the server failed.
     *        - "OK" if the server could edit/add the room
     *        - "NOT OK" if the server could edit/add room
     */
    public static String sendRoom(JSONObject obj) {
        String url = cURL + "room?token=" + getToken();
        HttpResponse<String> response = post(url, obj.toJSONString(), "Content-Type", "application/json;charset=UTF-8");

        if (response == null) {
            System.out.println("Communication with server failed");
            return "Communication with server failed";

        }

        if (response.statusCode() != 200) {
            System.out.println(response.body());
            return "NOT OK";
        } else {

            return "OK";
        }
    }

    /**
     * Gives the room which has to be delete.
     * @param index - String which represents the index of the room which has to benn deleted.
     * @return a String which can have 3 values:
     *         - "Communication with server failed" if the communication with the server failed.
     *         - "OK" if the server could delete the room
     *         - "NOT OK" if the server could delete the room
     *
     */
    public static String sendDeleteRoom(String index) {
        String url = cURL + "room/" + index + "?token=" + getToken();
        HttpResponse<String> response = delete(url);

        if (response == null) {
            System.out.println("Communication with server failed");
            return "Communication with server failed";

        }

        if (response.statusCode() != 200) {
            System.out.println(response.body());
            return "NOT OK";
        } else {

            return "OK";
        }


    }
}

