package model;

/**
 * The model.Plant class represents the plant that grows from a seed planted on a tile.
 */
public class Plant {
    /**
     * Contains the details of the seed.
     */
    private Seed seedDetails;
    /**
     * Contains when the seed was planted.
     */
    private int dayPlanted;
    /**
     * Contains when the plant should be harvested.
     */
    private int dayToHarvest;
    /**
     * Determines whether the plant is ready to be harvested.
     */
    private boolean toHarvest = false;
    /**
     * Contains the number of times the plant was watered.
     */
    private int timesWatered = 0;
    /**
     * Contains the number of times the plant was fertilized.
     */
    private int timesFertilized;
    /**
     * Contains the actual number of produce when to harvest a plant.
     */
    private int actualProduceNum;

    /**
     * Initializes the attributes of the model.Plant class.
     *
     * @param dayPlanted is when the seed was planted.
     * @param dayToHarvest is when the plant should be harvested.
     * @param timesWatered is the number of times the plant was watered.
     * @param timesFertilized is the number of times the plant was fertilized.
     * @param actualProduceNum is the actual number of produce when to harvest a plant.
     */
    public Plant(int dayPlanted, int dayToHarvest, int timesWatered, int timesFertilized, int actualProduceNum){
        Seed seedDetails = new Seed();
        this.dayPlanted = dayPlanted;
        this.dayToHarvest = dayToHarvest;
        this.timesWatered = timesWatered;
        this.timesFertilized = timesFertilized;
        this.actualProduceNum = actualProduceNum;
    }

    /**
     * Default Constructor.
     */
    public Plant(){}

    /**
     * Displays the statistics of a plant on the tile.
     */
    public void displayPlantStats(){
        System.out.println(" model.Seed Name: " + getSeedDetails().getName()+
                "\n Day Planted: " + this.getDayPlanted() +
                "\n Day To Harvest: " + this.getDayToHarvest() +
                "\n Water Needs: " + getSeedDetails().getWaterNeeded() +
                "\n No. of Times Watered: " + this.getTimesWatered() +
                "\n Fertilizer Needs: " + getSeedDetails().getFertilizerNeeded() +
                "\n No. of Time Fertilized: " + this.getTimesFertilized());

        if(toHarvest)
            System.out.println(" Produce to collect: " + this.getActualProduceNum());
    }

    /**
     * Checks whether a plant is ready to be harvested.
     *
     * @param tile is where the plant is planted.
     * @param stats is the farmer's and farm's statistics.
     */
    public void isToHarvest(Tile tile, Stats stats){
//        if(tile.isOccupied() && tile.getTileType()!=model.TileType.HAS_ROCK){
//            // If harvest day is reached and minimum requirements have been met
//            if(MyFarm.getDay() == this.dayToHarvest && this.timesWatered >= getSeedDetails().getWaterNeeded() && this.timesFertilized >= getSeedDetails().getFertilizerNeeded()){
//                this.toHarvest = true;
//                tile.setTileType(model.TileType.TO_HARVEST);
//            }
//            // If harvest day is reached and minimum requirements have not been met
//            else if(MyFarm.getDay() == this.dayToHarvest && (this.timesWatered < getSeedDetails().getWaterNeeded() || this.timesFertilized < getSeedDetails().getFertilizerNeeded())){
//                this.toHarvest = false;
//                tile.setTileType(model.TileType.WITHERED);
//                stats.addWitheredCrops();
//                MyFarm.increaseVacancy();
//            }
//            // If harvest day has passed
//            else if(MyFarm.getDay() > this.dayToHarvest){
//                this.toHarvest = false;
//                tile.setTileType(model.TileType.WITHERED);
//                stats.addWitheredCrops();
//                MyFarm.increaseVacancy();
//            }
//
//            setActualProduceNum(toHarvest);
//        }
    }

    /**
     * The getter method of the seedDetails attribute.
     *
     * @return the details of the seed.
     */
    public Seed getSeedDetails() {
        return seedDetails;
    }

    /**
     * The setter method of the seedDetails attribute.
     *
     * @param seedDetails is the details of the seed to be planted.
     */
    public void setSeedDetails(Seed seedDetails) {
        this.seedDetails = seedDetails;
    }

    /**
     * The getter method of the dayPlanted attribute.
     *
     * @return when the seed was planted.
     */
    public int getDayPlanted() {
        return dayPlanted;
    }

    /**
     * The setter method of the dayPlanted attribute.
     *
     * @param dayPlanted is when the seed was planted.
     */
    public void setDayPlanted(int dayPlanted) {
        this.dayPlanted = dayPlanted;
    }

    /**
     * The getter method of the dayToHarvest attribute.
     *
     * @return when the plant should be harvested.
     */
    public int getDayToHarvest() {
        return dayToHarvest;
    }

    /**
     * The setter method of the dayToHarvest attribute.
     *
     * @param dayToHarvest is when the plant should be harvested.
     */
    public void setDayToHarvest(int dayToHarvest) {
        this.dayToHarvest = dayToHarvest;
    }

    /**
     * The getter method of timesWatered attribute.
     *
     * @return the number of times the plant was watered.
     */
    public int getTimesWatered() {
        return timesWatered;
    }

    /**
     * The setter method of timesWatered attribute.
     *
     * @param timesWatered is the number of times the plant was watered.
     */
    public void setTimesWatered(int timesWatered) {
        this.timesWatered = timesWatered;
    }

    /**
     * The getter method of the timesFertilized attribute.
     *
     * @return the number of times the plant was fertilized.
     */
    public int getTimesFertilized() {
        return timesFertilized;
    }

    /**
     * The setter method of the timesFertilized attribute.
     *
     * @param timesFertilized is the number of times the plant was fertilized.
     */
    public void setTimesFertilized(int timesFertilized) {
        this.timesFertilized = timesFertilized;
    }

    /**
     * The getter method of the actualProduceNum attribute.
     *
     * @return the actual number of produce when to harvest a plant.
     */
    public int getActualProduceNum() {
        return actualProduceNum;
    }

    /**
     * The setter method of the actualProduceNum attribute.
     *
     * @param toHarvest determines whether the plant is ready to be harvested.
     */
    private void setActualProduceNum(boolean toHarvest) {
        // If ready for harvest, generate random number from range determined by expectedMinProduce and expectedMaxProduce from the model.Seed class
        if(toHarvest){
            if(getSeedDetails().getExpectedMinProduce()==getSeedDetails().getExpectedMaxProduce())
                this.actualProduceNum = seedDetails.getExpectedMaxProduce();
            else
                this.actualProduceNum = (int) Math.floor((Math.random() * (getSeedDetails().getExpectedMaxProduce() - getSeedDetails().getExpectedMinProduce() + 1) + getSeedDetails().getExpectedMinProduce()));
        }
        else
            this.actualProduceNum = 0;
    }
}
