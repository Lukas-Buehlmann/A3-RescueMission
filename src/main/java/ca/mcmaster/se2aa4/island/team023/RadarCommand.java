package ca.mcmaster.se2aa4.island.team023;

import ca.mcmaster.se2aa4.island.team023.Heading.HeadingStates;


public class RadarCommand extends Command {

    private HeadingStates dir;

    public RadarCommand(DroneActions droneActions, HeadingStates direction) {
        super(droneActions);
        dir = direction;
    }

    public void execute() {
        this.action = this.droneActions.radar(dir);
    }
}
