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
     * Sets the username for the client.
     *
     * @param newUsername the username
     */
    public static void setUsername(String newUsername) {
        username = newUsername;
    }

    /**
     * Returns the username.
     * @return a String with in it the username
     */
    public static String getUsername() {
        return username;
    }


    /**
     * Retrieves all buildings from the server.
     *
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getBuilding() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(cURL + "building")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        System.out.println(response.body());
        return response.body();
    }

    /**
     * Gets the rooms from the database.
     *
     * @return A String with in it a JSONArray or Object of all rooms
     */
    public static String getRooms() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(cURL + "room")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        System.out.println(response.body());
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
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString("")).uri(URI.create(url)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
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
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString("")).uri(URI.create(url)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
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
     * Gets the rooms from a specific building from the database through the API.
     *
     * @param buildingID the ID of the building from which we want all the rooms
     * @return a String with a JSONArray in which all rooms from that building are present
     */
    public static String getRoomsFromBuilding(String buildingID) {
        String url = cURL + "room/" + buildingID;

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        System.out.println(response.body());
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

        HttpRequest request = HttpRequest.newBuilder().DELETE().uri(URI.create(url)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
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
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(obj.toJSONString())).uri(URI.create(url)).setHeader("Content-Type", "application/json;charset=UTF-8").build();
        HttpResponse<String> response = null;
        
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
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

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpResponse<String> response = null;

        String[] hours = new String[24];
        for (int i = 0; i < hours.length; i++) {
            hours[i] = "free";
        }

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return hours;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }

        if (response.body().length() > 2 && response.statusCode() == 200) {
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
        System.out.println(response.body());
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

        HttpRequest request = HttpRequest.newBuilder()
                .POST(buildFormDataFromMap(data))
                .uri(URI.create(url))
                .setHeader("User-Agent", "Java 11 HttpClient Bot") // add request header
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }

        System.out.println(response.body());
        return response.body();
    }

    /**
     * Takes a map as input and translates the pairs to a json.
     *
     * @param data the map containing the json pairs/values
     * @return Returns a BodyPublisher containing the http POST content
     */
    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
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
        return HttpRequest.BodyPublishers.ofString(builder.toString());
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
        HttpRequest request = HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(obj.toJSONString())).uri(URI.create(url)).setHeader("Content-Type", "application/json;charset=UTF-8").build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String s = response.body();
        } catch (Exception e) {
            e.printStackTrace();
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
     * @param blinds - String which represents if the room has blinds or not.
     * @param desktop - String which represents if the room has desktop or not.
     * @param projector - String which represents if the room has projector or not.
     * @param chalkBoard - String which represents if the room has chalk board or not.
     * @param microphone - String which represents if the room has microphone or not.
     * @param smartBoard - String which represents if the room has smart board or not.
     * @param whiteBoard - String which represents if the room has white board or not.
     * @param powerSupply - String which represents if the room has power supply or not.
     * @param soundInstallation - String which represents if the room has soundInstallation or not.
     * @param wheelChair - String which represents if the room has the facilities for people with wheel chair or not.
     * @param space - String which represents the minimum space capacity of the room (nr. of people).
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
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        System.out.println(response.body());
        return response.body();
    }

    /**
     * Encodes a value to standard utf8 format.
     * @param value - String value that needs to be encoded
     * @return the utf8-encoded String
     */
    private static String utf8EncodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}

