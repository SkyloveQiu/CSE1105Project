package nl.tudelft.oopp.group43.communication;

import java.net.ContentHandler;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();
    private static String token = "invalid";
    private static final String cURL = "http://localhost:8000/";


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
     *          - "Communication with server failed" if the communication with the server failed
     *          - "OK" if the user could be added to the Users Database
     *          - "Exists" if the email already exists
     */
    public static String confirmRegistration(String firstName, String lastName, String username, String password, String role) {

        String url = cURL + "registration?";
        url = url + "firstName=" + firstName + "&";
        url = url + "lastName=" + lastName + "&";
        url = url + "username=" + username + "&";
        url = url + "password=" + password + "&";
        url = url + "role=" + role;
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
     *          - "Communication with server failed" if the communication with the server failed
     *          - "OK" if the user could be added to the Users Database
     *          - "WRONG" if the email or password were wrong
     * @throws ParseException - if something goes wrong with the JSON Parser
     */
    public static String loginToken(String username, String password) throws ParseException {
        String url = cURL + "token?";
        url = url + "username=" + username + "&";
        url = url + "password=" + password + "&";
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
     * @param buildID - the id of the building
     * @returna String which can have 3 values:
     *          - "Communication with server failed" if the communication with the server failed
     *          - "OK" if the building could be deleted from the Buildings Database
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
     * @param obj - JSON Object represents  the building
     * @return - "Communication with server failed" if the communication with the server failed
     *         - "OK" if the building could be deleted from the Buildings Database
     */
    public static String sendEditBuilding(JSONObject obj) {
        String url = cURL + "building";
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
}

