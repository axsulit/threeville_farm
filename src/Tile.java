/**
 * The Tile class represents an individual lot of land in the farm lot.
 */
public class Tile {
    /**
     * Stores the seed to be planted and the plant to be harvested.
     */
    private Plant plant = new Plant();
    /**
     * Contains the ID number of the tile.
     */
    private int tileNum;
    /**
     * Determines whether any entity occupies a tile.
     */
    private boolean occupied = false;
    /**
     * Determines the status of the tile land.
     */
    private TileType tileType = TileType.UNPLOWED;

    /**
     * Default constructor.
     */
    public Tile(){}

    /**
     * The getter method for the plant attribute.
     *
     * @return the reference to the plant of a tile.
     */
    public Plant getPlant() {
        return plant;
    }

    /**
     * The setter method for the plant attribute.
     *
     * @param plant is the plant to occupy a tile.
     */
    public void setPlant(Plant plant){
        this.plant = plant;
    }

    /**
     * The getter method for the tileNum attribute.
     *
     * @return the ID number associated with a tile.
     */
    public int getTileNum() {
        return tileNum;
    }

    /**
     * The setter method for tileNum attribute.
     *
     * @param tileNum is the ID number of a tile to be accessed.
     */
    public void setTileNum(int tileNum) {
        this.tileNum = tileNum;
    }

    /**
     * The getter method of occupied attribute.
     *
     * @return the value of occupied that shows whether a tile contains any game entities such as rocks and plants.
     */
    public boolean isOccupied() {
        return occupied;
    }

    /**
     * The setter method of occupied attribute.
     *
     * @param occupied contains the new value of occupied that shows whether a tile contains any game entities.
     */
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    /**
     * The getter method of the tileType attribute.
     *
     * @return the status of a tile.
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * The setter method of the tileType attribute.
     * @param tileType contains the new status of a tile.
     */
    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }
}
