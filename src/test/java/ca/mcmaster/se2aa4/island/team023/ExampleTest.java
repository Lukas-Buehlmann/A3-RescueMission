package ca.mcmaster.se2aa4.island.team023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;
import org.json.JSONObject;
import org.json.JSONTokener;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


@TestInstance(Lifecycle.PER_CLASS)
public class ExampleTest {

    private Aircraft drone;
    private IMap map;
    private JSONObject radarResponse;
    private JSONObject scanCreekResponse;
    private JSONObject scanOceanResponse;

    @BeforeEach
    public void createDrone() {
        drone = new Drone("E", 7000);
        map = new Map();
    }

    @BeforeAll
    public void initializeJSON() {
        String radarString = "{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }";
        radarResponse = new JSONObject(new JSONTokener(new StringReader(radarString)));
        
        String scanCreekString = "{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [\"id\"], \"sites\": []}, \"status\": \"OK\"}";
        scanCreekResponse = new JSONObject(new JSONTokener(new StringReader(scanCreekString)));
        
        String scanOceanString = "{\"cost\": 2, \"extras\": { \"biomes\": [\"OCEAN\"], \"creeks\": [], \"sites\": []}, \"status\": \"OK\"}";
        scanOceanResponse = new JSONObject(new JSONTokener(new StringReader(scanOceanString)));
    }

    
    // -------------- START OF NEW TEST CASES -------------------

    @Test
    public void cellIsPlacedFromRadar() {

        
        // make sure a cell placed from radar is correctly placed and marked as ground
        map.placeCell(1, 1, 0, 1, radarResponse);
        Cell groundCell = map.getCell(1, 3);
        assertTrue(groundCell instanceof GroundCell);
    }

    @Test
    public void cellIsPlacedFromScan() {

        // make sure a cell placed from scan is correctly placed and marked as ocean
        map.placeCell(5, 5, 0, 1, scanOceanResponse);
        Cell oceanCell = map.getCell(5, 5);
        assertTrue(oceanCell instanceof OceanCell);
    }

    
    @Test
    public void creekIsPlacedFromScan() {

        // make sure a cell placed from scan is correctly placed and marked as a creek
        map.placeCell(10, 10, 0, 1, scanCreekResponse);
        Cell groundCell = map.getCell(10, 10);
        assertTrue(groundCell instanceof DetailedGroundCell);
    }

}
