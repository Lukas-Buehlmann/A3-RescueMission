package ca.mcmaster.se2aa4.island.team023;

public class ScanCommand extends Command {
    public ScanCommand(DroneActions droneActions) {
        super(droneActions);
    }

    public void execute() {
        this.action = this.droneActions.scan();
    }
}
