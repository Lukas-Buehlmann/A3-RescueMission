package ca.mcmaster.se2aa4.island.team023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(Lifecycle.PER_CLASS)
public class HeadingTests {
    @Test
    public void headingTurnsLeft() {
        Heading heading = new Heading("E");
        heading.turnCounterClockwise();
        assertTrue(heading.getHeadingState() == Heading.HeadingStates.N);
    }

    @Test
    public void headingTurnsRight() {
        Heading heading = new Heading("E");
        heading.turnClockwise();
        assertTrue(heading.getHeadingState() == Heading.HeadingStates.S);
    }

    @Test
    public void headingHasPointAhead() {
        Heading heading = new Heading("E");
        Point<Integer> nextP = new Point<Integer>(1, 0);
        assertTrue(heading.getHeadingState().getNextPoint().equals(nextP));
    }
}