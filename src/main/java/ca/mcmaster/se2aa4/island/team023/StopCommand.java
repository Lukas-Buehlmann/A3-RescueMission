package ca.mcmaster.se2aa4.island.team023;

public class StopCommand extends Command {
    public StopCommand(DroneActions droneActions) {
        super(droneActions);
    }

    public void execute() {
        this.action = this.droneActions.stop();
    }
}
