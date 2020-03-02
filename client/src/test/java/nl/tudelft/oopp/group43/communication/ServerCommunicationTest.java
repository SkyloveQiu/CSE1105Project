package nl.tudelft.oopp.group43.communication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;


public class ServerCommunicationTest {

    @Test
    public void testGetBuildings() {
        assertNotNull(ServerCommunication.getBuilding());
    }

}
