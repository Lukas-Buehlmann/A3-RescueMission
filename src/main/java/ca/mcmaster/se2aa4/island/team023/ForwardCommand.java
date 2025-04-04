package ca.mcmaster.se2aa4.island.team023;

public class ForwardCommand extends Command{

    public ForwardCommand(DroneActions droneActions) {
        super(droneActions);
    }

    public void execute() {
        this.action = this.droneActions.forward();
    }
}

