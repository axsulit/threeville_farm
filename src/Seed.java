/**
 * The Seed class represents the different seeds that can be bought and planted by the farmer on the farm lot.
 */
public class Seed {
    /**
     * Contains the name of the seed.
     */
    private String name;
    /**
     * Contains the type of crop the seed is.
     */
    private CropType cropType;
    /**
     * Determines whether the seed can be bought at the store.
     */
    private boolean cropAvailable;
    /**
     * Contains the number of days before the seed can be harvested.
     */
    private int harvestTime;
    /**
     * Contains the number of times the plant needs to be watered for it to grow successfully.
     */
    private int waterNeeded;
    /**
     * Contains the additional number of times the plant can be watered for higher earnings per produce.
     */
    private int waterBonus;
    /**
     * Contains the number of times the plant needs to be fertilized for it to grow successfully.
     */
    private int fertilizerNeeded;
    /**
     * Contains the additional number of times the plant can be fertilized for higher earnings per produce.
     */
    private int fertilizerBonus;
    /**
     * Contains the number of experience points the farmer can earn per harvest of a seed.
     */
    private double xpYield;
    /**
     * Determines the expected minimum produce per plant on harvest.
     */
    private int expectedMinProduce;
    /**
     * Determines the expected maximum produce per plant on harvest.
     */
    private int expectedMaxProduce;
    /**
     * Determines the cost of the seed.
     */
    private double seedCost;
    /**
     * Determines the base selling price of the plant per produce on harvest day.
     */
    private double baseSPPerProduce;

    /**
     * Initializes the attributes of the Seed class.
     *
     * @param name contains the name of the seed.
     * @param cropType contains the type of crop the seed is.
     * @param cropAvailable determines whether the seed can be bought at the store.
     * @param harvestTime contains the number of days before the seed can be harvested.
     * @param waterNeeded contains the number of times the plant needs to be watered for it to grow successfully.
     * @param waterBonus contains the additional number of times the plant can be watered for higher earnings per produce.
     * @param fertilizerNeeded contains the number of times the plant needs to be fertilized for it to grow successfully.
     * @param fertilizerBonus Contains the number of times the plant needs to be fertilized for it to grow successfully.
     * @param xpYield contains the number of experience points the farmer can earn per harvest of a seed.
     * @param expectedMinProduce determines the expected minimum produce per plant on harvest.
     * @param expectedMaxProduce determines the expected maximum produce per plant on harvest.
     * @param seedCost determines the cost of the seed.
     * @param baseSPPerProduce determines the base selling price of the plant per produce on harvest day.
     */
    public Seed(String name, CropType cropType, boolean cropAvailable, int harvestTime, int waterNeeded, int waterBonus, int fertilizerNeeded, int fertilizerBonus, double xpYield, int expectedMinProduce, int expectedMaxProduce, double seedCost, double baseSPPerProduce){
        this.name = name;
        this.cropType = cropType;
        this.cropAvailable = cropAvailable;
        this.harvestTime = harvestTime;
        this.waterNeeded = waterNeeded;
        this.waterBonus = waterBonus;
        this.fertilizerNeeded = fertilizerNeeded;
        this.fertilizerBonus = fertilizerBonus;
        this.xpYield = xpYield;
        this.expectedMinProduce = expectedMinProduce;
        this.expectedMaxProduce = expectedMaxProduce;
        this.seedCost = seedCost;
        this.baseSPPerProduce = baseSPPerProduce;
    }

    /**
     * Default constructor.
     */
    public Seed(){}

    /**
     * The getter method of the name attribute.
     *
     * @return the name of the seed
     */
    public String getName() {
        return name;
    }

    /**
     * The getter method of the cropAvailable attribute.
     *
     * @return whether the seed can be bought at the store.
     */
    public boolean isCropAvailable() {
        return cropAvailable;
    }

    /**
     * The getter method of the waterNeeded attribute.
     *
     * @return the number of times the plant needs to be watered for it to grow successfully.
     */
    public int getWaterNeeded() {
        return waterNeeded;
    }

    /**
     * The getter method of the waterBonus attribute.
     *
     * @return the additional number of times the plant can be watered for higher earnings per produce.
     */
    public int getWaterBonus() {
    return waterBonus;
}

    /**
     * The getter method of the fertilizerBonus attribute.
     *
     * @return the number of times the plant needs to be fertilized for it to grow successfully.
     */
    public int getFertilizerBonus() {
        return fertilizerBonus;
    }

    /**
     * The getter method of the fertilizerNeeded attribute.
     *
     * @return  the number of times the plant needs to be fertilized for it to grow successfully.
     */
    public int getFertilizerNeeded() { // DO NOT EDIT
        return fertilizerNeeded;
    }

    /**
     * The getter method of the cropType attribute.
     *
     * @return the type of crop the seed is.
     */
    public CropType getCropType() {
        return cropType;
    }

    /**
     * The getter method of the harvestTime attribute.
     *
     * @return  the number of days before the seed can be harvested.
     */
    public int getHarvestTime() {
        return harvestTime;
    }

    /**
     * The getter method of the xpYield attribute.
     *
     * @return the number of experience points the farmer can earn per harvest of a seed.
     */
    public double getXpYield() {
        return xpYield;
    }

    /**
     * The getter method of the expectedMinProduce attribute.
     *
     * @return the expected minimum produce per plant on harvest.
     */
    public int getExpectedMinProduce() {
        return expectedMinProduce;
    }

    /**
     * The getter method of the expectedMaxProduce attribute.
     *
     * @return the expected maximum produce per plant on harvest.
     */
    public int getExpectedMaxProduce() {
        return expectedMaxProduce;
    }

    /**
     * The getter method of the seedCost attribute.
     *
     * @return the cost of the seed.
     */
    public double getSeedCost() {
        return seedCost;
    }

    /**
     * The getter method of the baseSPPerProduce attribute.
     *
     * @return the base selling price of the plant per produce on harvest day.
     */
    public double getBaseSPPerProduce() {
        return baseSPPerProduce;
    }

    /**
     * The setter method of the waterBonus attribute.
     *
     * @param waterBonus is the water bonus of the seed.
     */
    public void setWaterBonus(int waterBonus) {
        this.waterBonus = waterBonus;
    }

    /**
     * The setter method of the fertilizerBonus attribute.
     *
     * @param fertilizerBonus is the fertilizer bonus of the seed.
     */
    public void setFertilizerBonus(int fertilizerBonus) {
        this.fertilizerBonus = fertilizerBonus;
    }
}