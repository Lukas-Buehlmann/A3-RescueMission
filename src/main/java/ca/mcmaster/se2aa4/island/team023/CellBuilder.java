package ca.mcmaster.se2aa4.island.team023;

public class CellBuilder {

    private CellFactory cellFactory = new CellFactory();

    private int x;
    private int y;
    private String[] creeks;
    private String[] sites;
    private String cellType;

    public CellBuilder simple(int x, int y, String cellType) {
        this.x = x;
        this.y = y;
        this.cellType = cellType;
        return this;
    }

    public CellBuilder detailed(int x, int y, String[] creeks, String[] sites, String cellType) {
        this.x = x;
        this.y = y;
        this.creeks = creeks;
        this.sites = sites;
        this.cellType = cellType;
        return this;
    }

    public Cell build() {
        return cellFactory.getCell(x, y, creeks, sites, cellType);
    }
}
