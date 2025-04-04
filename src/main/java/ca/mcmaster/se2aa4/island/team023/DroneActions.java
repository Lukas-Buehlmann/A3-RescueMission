package ca.mcmaster.se2aa4.island.team023;

import org.json.JSONObject;

import java.lang.NullPointerException;

import java.util.ArrayDeque;
import java.util.Queue;

import ca.mcmaster.se2aa4.island.team023.Heading.HeadingStates;

public class DroneActions {

    private Drone drone;
    private Queue<Command> commandQueue = new ArrayDeque<>();

    private boolean echoLeft = false;
    private boolean echoForward = false;
    private boolean echoRight = false;
    private boolean scan = false;
    private boolean leftTurn = false;
    private boolean rightTurn = false;
    private boolean flyForward = true;


    public DroneActions(Drone drone) {
        if (drone == null) throw new NullPointerException();
        this.drone = drone;
    }

    public JSONObject nextAction(){

        if (!commandQueue.isEmpty()) {
            Command nextCommand = commandQueue.remove();
            nextCommand.execute();
            return nextCommand.action;
        }

        // queue up actions based on flags. Always done in order: echo -> scan -> turns -> forward
        if (echoLeft) addRadarCommand(drone.getHeading().getHeadingState().prev());
        if (echoForward) addRadarCommand(drone.getHeading().getHeadingState());
        if (echoRight) addRadarCommand(drone.getHeading().getHeadingState().next());
        if (scan) addScanCommand();
        if (leftTurn) addLeftTurnCommand();
        if (rightTurn) addRightTurnCommand();
        if (flyForward) addForwardCommand();

        Command nextCommand = commandQueue.remove();
        nextCommand.execute();
        return nextCommand.action;
    }

    public void clearQueue() {
        clearCommands();
    }

    public void disableMovement() {
        echoLeft = false;
        echoForward = false;
        echoRight = false;
        scan = false;
        leftTurn = false;
        rightTurn = false;
        flyForward = false;
    }

    public void setDefaultEcho(boolean left, boolean ahead, boolean right) {
        echoLeft = left;
        echoForward = ahead;
        echoRight =  right;
    }

    public void setDefaultScan(boolean enable) {
        scan = enable;
    }

    public void setDefaultMovement(boolean leftTurn, boolean flyForward, boolean turnRight) {
        this.leftTurn = leftTurn;
        this.flyForward = flyForward;
        this.rightTurn = turnRight;
    }

    public void queueStop() {
        clearCommands();
        addStopCommand();
    }

    public void addLeftTurn() {
        addLeftTurnCommand();
    }

    public void addRightTurn() {
        addRightTurnCommand();
    }

    public void addDoubleLeft() {
        addLeftTurnCommand();
        addLeftTurnCommand();
    }
    
    public void addDoubleRight() {
        addRightTurnCommand();
        addRightTurnCommand();
    }
    
    public void addLongDoubleLeft() {
        addLeftTurnCommand();
        addForwardCommand();
        addLeftTurnCommand();
    }
    
    public void addLongDoubleRight() {
        addRightTurnCommand();
        addForwardCommand();
        addRightTurnCommand();
    }

    public void clearCommands() {
        commandQueue.clear();
    }

    public void addRadarCommand(HeadingStates direction) {
        commandQueue.add(new RadarCommand(this, direction));
    }

    public void addForwardCommand() {
        commandQueue.add(new ForwardCommand(this));
    }

    public void addLeftTurnCommand() {
        commandQueue.add(new TurnCommand(this, true));
    }
    
    public void addRightTurnCommand() {
        commandQueue.add(new TurnCommand(this, false));
    }

    public void addScanCommand() {
        commandQueue.add(new ScanCommand(this));
    }

    public void addStopCommand() {
        commandQueue.add(new StopCommand(this));
    }

    // Action to fly forward
    public JSONObject forward() {
        JSONObject action = new JSONObject();
        action.put("action", "fly");

        drone.setRelativePos(Point.addInts(drone.getRelativePos(), drone.getHeading().getHeadingState().getNextPoint()));

        return action;
    }

    public JSONObject turnLeft() {
        
        // update next position and rotate the drone heading
        drone.setRelativePos(Point.addInts(drone.getRelativePos(), drone.getHeading().getHeadingState().getNextPoint()));
        drone.getHeading().turnCounterClockwise();
        drone.setRelativePos(Point.addInts(drone.getRelativePos(), drone.getHeading().getHeadingState().getNextPoint()));

        JSONObject action = new JSONObject();
        JSONObject direction = new JSONObject();
        action.put("action", "heading");
        direction.put("direction", drone.getHeading().getHeadingState().toString());
        action.put("parameters", direction);

        return action;
    }

    public JSONObject turnRight() {

        // update next position and rotate the drone heading
        drone.setRelativePos(Point.addInts(drone.getRelativePos(), drone.getHeading().getHeadingState().getNextPoint()));
        drone.getHeading().turnClockwise();
        drone.setRelativePos(Point.addInts(drone.getRelativePos(), drone.getHeading().getHeadingState().getNextPoint()));

        JSONObject action = new JSONObject();
        JSONObject direction = new JSONObject();
        action.put("action", "heading");
        direction.put("direction", drone.getHeading().getHeadingState().toString());
        action.put("parameters", direction);

        return action;
    }

    public JSONObject radar(HeadingStates dir) {

        JSONObject action = new JSONObject();
        JSONObject direction = new JSONObject();
        action.put("action", "echo");
        direction.put("direction", dir.toString());
        action.put("parameters", direction);
        return action;
    }

    public JSONObject scan() {

        JSONObject action = new JSONObject();
        action.put("action", "scan");
        return action;
    }

    public JSONObject stop() {

        JSONObject action = new JSONObject();
        action.put("action", "stop");
        return action;
    }
    
}
