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

    @Test
    public void checkHeadingTurnLeft() {
        Heading heading = new Heading("E");
        heading.turnCounterClockwise();
        assertTrue(heading.getHeadingState() == Heading.HeadingStates.N);
    }

    @Test
    public void checkHeadingTurnRight() {
        Heading heading = new Heading("E");
        heading.turnClockwise();
        assertTrue(heading.getHeadingState() == Heading.HeadingStates.S);
    }

    @Test
    public void checkHeadingNextPoint() {
        Heading heading = new Heading("E");
        Point<Integer> nextP = new Point<Integer>(1, 0);
        assertTrue(heading.getHeadingState().getNextPoint().equals(nextP));
    }

    @Test
    public void checkPointEquality() {
        Point<Integer> p1 = new Point<Integer>(1, 0);
        Point<Integer> p2 = new Point<Integer>(1, 0);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void checkPointAddition() {
        Point<Integer> p1 = new Point<Integer>(1, 0);
        Point<Integer> p2 = new Point<Integer>(5, -2);
        Point<Integer> res = new Point<Integer>(6, -2);
        assertTrue(res.equals(Point.addInts(p1, p2)));
    }

    @Test
    public void checkRadarCellPlacement() {

        // make sure a cell placed from radar is correctly placed and marked as ground
        map.placeCell(1, 1, 0, 1, radarResponse);
        Cell groundCell = map.getCell(1, 3);
        assertTrue(groundCell instanceof GroundCell);
    }

    @Test
    public void checkScanOceanPlacement() {

        // make sure a cell placed from scan is correctly placed and marked as ocean
        map.placeCell(5, 5, 0, 1, scanOceanResponse);
        Cell oceanCell = map.getCell(5, 5);
        assertTrue(oceanCell instanceof OceanCell);
    }

    
    @Test
    public void checkScanCreekPlacement() {

        // make sure a cell placed from scan is correctly placed and marked as a creek
        map.placeCell(10, 10, 0, 1, scanCreekResponse);
        Cell groundCell = map.getCell(10, 10);
        assertTrue(groundCell instanceof DetailedGroundCell);
    }

}
