package ca.mcmaster.se2aa4.island.team023;

import org.json.JSONObject;

public abstract class Command {

    public DroneActions droneActions;
    protected JSONObject action;

    public Command(DroneActions droneActions) {
        this.droneActions = droneActions;
    }

    public JSONObject getAction() {
        return action;
    }

    public abstract void execute();
}
