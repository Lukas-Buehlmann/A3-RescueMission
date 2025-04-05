package ca.mcmaster.se2aa4.island.team023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(Lifecycle.PER_CLASS)
public class PointTests {
    @Test
    public void pointsAreCompared() {
        Point<Integer> p1 = new Point<Integer>(1, 0);
        Point<Integer> p2 = new Point<Integer>(1, 0);
        assertTrue(p1.equals(p2));
    }

    @Test
    public void pointsAreAdded() {
        Point<Integer> p1 = new Point<Integer>(1, 0);
        Point<Integer> p2 = new Point<Integer>(5, -2);
        Point<Integer> res = new Point<Integer>(6, -2);
        assertTrue(res.equals(Point.addInts(p1, p2)));
    }

}