/**
 * The Stats class represents the statistics of the farm and the farmer.
 */
public class Stats {

    /**
     * Contains the number of times the farmer (player) has planted seeds.
     */
    private int timesPlanted = 0;
    /**
     * Contains the number of times the farmer (player) has plowed a tile.
     */
    private int timesPlowed = 0;
    /**
     * Contains the number of times the farmer (player) has watered a plant.
     */
    private int timesWatered = 0;
    /**
     * Contains the number of times the farmer (player) has mined rocks.
     */
    private int rocksMined = 0;
    /**
     *  Contains the number of times the farmer (player) has fertilized plants.
     */
    private int timesFertilized = 0;
    /**
     * Contains the number of times the farmer (player) has failed to harvest a plant on time.
     */
    private int witheredCrops = 0;
    /**
     * Contains the number of products the farmer (player) has successfully harvested.
     */
    private int productsHarvested = 0;

    /**
     * Sole constructor.
     */
    public Stats(){}

    /**
     * Increments the number of times the farmer (player) has plowed a tile.
     */
    public void addTimesPlowed() {
        ++timesPlowed;
    }

    /**
     * Increments the number of times the farmer (player) has watered a tile.
     */
    public void addTimesWatered() {
        ++timesWatered;
    }

    /**
     * Increments the number of times the farmer (player) has fertilized a plant.
     */
    public void addTimesFertilized() {
        ++timesFertilized;
    }

    /**
     * Increments the number of times the farmer (player) has mined a rock.
     */
    public void addRocksMined() {
        ++rocksMined;
    }

    /**
     * Increments the number of times the farmer (player) has planted a seed.
     */
    public void addTimesPlanted() {
        ++timesPlanted;
    }

    /**
     * Increments the number of times the farmer (player) has failed to harvest a plant on time.
     */
    public void addWitheredCrops() {
        ++witheredCrops;
    }

    /**
     * The getter method for timesPlowed attribute.
     *
     * @return the number of times the farmer has plowed in the farm lot.
     */
    public int getTimesPlowed() {
        return timesPlowed;
    }

    /**
     * The getter method for timesWatered attribute.
     *
     * @return the number of times the farmer has watered plants in the farm lot.
     */
    public int getTimesWatered() {
        return timesWatered;
    }

    /**
     * The getter method of timesFertilized attribute.
     *
     * @return the number of times the farmer has fertilized plants in the farm lot.
     */
    public int getTimesFertilized() {
        return timesFertilized;
    }

    /**
     * The getter method of rocksMined attribute.
     *
     * @return the number of times the farmer has mined rocks in the farm lot.
     */
    public int getRocksMined() {
        return rocksMined;
    }

    /**
     * The getter method of timesPlanted attribute.
     *
     * @return the number of times the farmer has planted seeds in the farm lot.
     */
    public int getTimesPlanted() {
        return timesPlanted;
    }

    /**
     * The getter method of witheredCrops attribute.
     *
     * @return the number of times the farmer has neglected plants on their harvest day.
     */
    public int getWitheredCrops() {
        return witheredCrops;
    }

    /**
     * The getter method of productsHarvested attribute.
     *
     * @return the number of produce the farmer has successfully harvested.
     */
    public int getProductsHarvested() {
        return productsHarvested;
    }

    /**
     * The setter method of productsHarvested attribute.
     *
     * @param productsHarvested is the updated number of total products Harvested.
     */
    public void setProductsHarvested(int productsHarvested) {
        this.productsHarvested = productsHarvested;
    }

    /**
     * Displays the farm and farmer statistics.
     *
     * @param farmer represents the player and its subclassses.
     */
    public void displayStats(Farmer farmer){
        System.out.println("\nSTATISTICS\n");
        System.out.println("╰ FARMER");
        System.out.println("\tLevel: " + farmer.getFarmerDetails().getLevel());
        System.out.println("\tXP: " + farmer.getFarmerDetails().getPlayerXP());
        System.out.println("\n╰ FARM");
        System.out.println("\tRocks Mined: " + rocksMined);
        System.out.println("\tTiles Plowed: " + timesPlowed);
        System.out.println("\tTimes Planted: " + timesPlanted);
        System.out.println("\tTimes Watered: " + timesWatered);
        System.out.println("\tTimes Fertilized: " + timesFertilized);
        System.out.println("\tProducts Harvested: " + productsHarvested);
        System.out.println("\tCrops Withered: " + witheredCrops);
    }
}
