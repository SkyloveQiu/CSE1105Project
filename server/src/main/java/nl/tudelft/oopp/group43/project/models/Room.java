package nl.tudelft.oopp.group43.project.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Room {

    private String roomName;
    private String buildingName;
    private ResultSet result;
    private String plainTextData;


    /**
     * Describes a room from a certain building.
     *
     * @param buildingName The name of the building
     * @param roomName     The roomName
     * @throws SQLException just an exception
     */
    public Room(String buildingName, String roomName) throws SQLException {

        this.buildingName = buildingName;
        this.roomName = roomName;
        plainTextData = "";
        //include name verification for room - prevent sql injection

        Statement st = null;

        String query = "SELECT * FROM movies WHERE scenes_in_db =" + Integer.parseInt(buildingName);

        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/starwars", "postgres", "aura5090")) {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {

                int id = rs.getInt("id");
                System.out.println("DEBUG id: " + id + '\n');
                String title = rs.getString("title");
                System.out.println("DEBUG title: " + title + '\n');
                int scenesInDB = rs.getInt("scenes_in_db");
                int scenesInMovie = rs.getInt("scenes_in_movies");

                plainTextData += id + " " + title + " " + scenesInDB + " " + scenesInMovie + "   |||   ";


            }
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        } finally {
            if (st != null) {
                st.close();
            }
        }

    }

    /**
     * Gets the room Name.
     *
     * @return the roomName
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Gets the building Name.
     *
     * @return the buildingName
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * Gets the result of the query.
     *
     * @return the ResultSet of the query
     */
    public ResultSet getResult() {
        return result;
    }

    /**
     * Nothing to describe here.
     *
     * @return plain text
     */
    public String getPlainTextData() {
        return plainTextData;
    }
}
