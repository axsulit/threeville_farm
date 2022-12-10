/**
 * Indicates the status of a Tile.
 */

public enum TileType {
    /**
     * Does not contain any rocks or seed and has not been plowed yet.
     */
    UNPLOWED(1),
    /**
     * Contains rocks that prevents the user from doing any other task on the tile.
     */
    HAS_ROCK(2),
    /**
     * Allows a seed to be planted.
     */
    PLOWED(3),
    /**
     * Contains a seed.
     */
    HAS_PLANT(4),
    /**
     * Contains a plant ready to be harvested.
     */
    TO_HARVEST(5),
    /**
     * Contains a plant that has passed its harvest day.
     */
    WITHERED(6);


    /**
     * Contains the value of a tile type.
      */
    private int value;

    /**
     * Initializes a corresponding int value for tile types.
     */
    private TileType(int value){
        this.value = value;
    }
}
