package ca.mcmaster.se2aa4.island.team023;

public class CellFactory {
    public Cell getCell(int x, int y, String[] creeks, String[] sites, String cellType) {
        
        if (cellType == null) return null;

        if (cellType.equalsIgnoreCase("OCEAN")) return new OceanCell(x, y);
        if (cellType.equalsIgnoreCase("GROUND")) return new GroundCell(x, y);
        if (cellType.equalsIgnoreCase("DETAILEDGROUND")) return new DetailedGroundCell(x, y, creeks, sites);

        return null;
    }
}

