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
public class MapTests {
    
    private IMap map;
    private JSONObject radarResponse;
    private JSONObject emptyRadarResponse;
    private JSONObject scanCreekResponse;
    private JSONObject scanOceanResponse;

    
    @BeforeAll
    public void initializeJSON() {
        String radarString = "{ \"cost\": 1, \"extras\": { \"range\": 2, \"found\": \"GROUND\" }, \"status\": \"OK\" }";
        radarResponse = new JSONObject(new JSONTokener(new StringReader(radarString)));
        
        String emptyRadarString = "{ \"cost\": 1, \"extras\": { \"range\": 5, \"found\": \"OUT_OF_RANGE\" }, \"status\": \"OK\" }";
        emptyRadarResponse = new JSONObject(new JSONTokener(new StringReader(emptyRadarString)));
        
        String scanCreekString = "{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [\"id\"], \"sites\": []}, \"status\": \"OK\"}";
        scanCreekResponse = new JSONObject(new JSONTokener(new StringReader(scanCreekString)));
        
        String scanOceanString = "{\"cost\": 2, \"extras\": { \"biomes\": [\"OCEAN\"], \"creeks\": [], \"sites\": []}, \"status\": \"OK\"}";
        scanOceanResponse = new JSONObject(new JSONTokener(new StringReader(scanOceanString)));
    }

    @BeforeEach
    public void createNewMap() {
        map = new Map();
    }
    
    @Test
    public void cellIsPlacedFromRadar() {

        
        // make sure a cell placed from radar is correctly placed and marked as ground
        map.placeCell(1, 1, 0, 1, radarResponse);
        Cell groundCell = map.getCell(1, 3);
        assertTrue(groundCell instanceof GroundCell);
    }

    @Test
    public void oceanCellsArePlacedFromRadar() {

        // cells placed from radar should be marked as ocean until the given distance away
        map.placeCell(1, 1, 0, 1, emptyRadarResponse);
        Cell oceanCell;
        for (int i=0;i < 4;i++) {
            oceanCell = map.getCell(1, i + 2);
            assertTrue(oceanCell instanceof OceanCell);
        }
        
    }

    @Test
    public void outOfRangeCellIsNull() {

        // cells placed from radar should be marked as ocean until the given distance away
        map.placeCell(1, 1, 0, 1, emptyRadarResponse);
        Cell nullCell = map.getCell(1,6);
        assertTrue(nullCell == null);
        
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