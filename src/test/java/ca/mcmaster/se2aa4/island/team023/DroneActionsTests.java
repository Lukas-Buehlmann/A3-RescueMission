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
public class DroneActionsTests {
    
    private Drone drone;
    private DroneActions droneActions;
    private JSONObject radarResponse;
    private JSONObject scanResponse;

    
    @BeforeAll
    public void initializeJSON() {
        String radarAction = "{ \"action\": \"echo\", \"parameters\": { \"direction\": \"S\" } }";
        radarResponse = new JSONObject(new JSONTokener(new StringReader(radarAction)));
        
        String scanAction = "{ \"action\": \"scan\" }";
        scanResponse = new JSONObject(new JSONTokener(new StringReader(scanAction)));
    }

    @BeforeEach
    public void createDroneActionObject() {
        drone = new Drone("E", 7000);
        droneActions = new DroneActions(drone);
    }
    
    @Test
    public void sendRadarPingRight() {
        droneActions.disableMovement();
        droneActions.setDefaultEcho(false, false, true);
        JSONObject givenAction = droneActions.nextAction();
        assertTrue(givenAction.toString().equals(radarResponse.toString()));
    }
    
    @Test
    public void sendScanAction() {
        droneActions.disableMovement();
        droneActions.setDefaultScan(true);
        JSONObject givenAction = droneActions.nextAction();
        assertTrue(givenAction.toString().equals(scanResponse.toString()));
    }

    @Test
    public void dronePositionIncrementsWithForward() {
        droneActions.disableMovement();
        droneActions.setDefaultMovement(false, true, false);
        droneActions.nextAction();
        assertTrue(drone.getRelativePos().equals(new Point<Integer>(1, 0)));
    }

    @Test
    public void dronePositionIncrementsWithRightTurn() {
        droneActions.disableMovement();
        droneActions.setDefaultMovement(false, false, true);
        droneActions.nextAction();
        assertTrue(drone.getRelativePos().equals(new Point<Integer>(1, 1)));
    }
    
    @Test
    public void dronePositionIsUnchangedWithScan() {
        droneActions.disableMovement();
        droneActions.setDefaultScan(true);
        droneActions.nextAction();
        assertTrue(drone.getRelativePos().equals(new Point<Integer>(0, 0)));
    }

}