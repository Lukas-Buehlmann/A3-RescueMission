package ca.mcmaster.se2aa4.island.team023;

public class TurnCommand extends Command {

    private boolean isLeftTurn;

    public TurnCommand(DroneActions droneActions, boolean isLeftTurn) {
        super(droneActions);
        this.isLeftTurn = isLeftTurn;
    }

    public void execute() {
        if (isLeftTurn) this.action = this.droneActions.turnLeft();
        else this.action = this.droneActions.turnRight();
    }
}
