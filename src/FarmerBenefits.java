/**
 * The FarmerBenefits class represents the benefits the farmer has based on their title as determined in FarmerType.
 */
public class FarmerBenefits {
    /**
     * Additional earnings the farmer gains when harvesting a plant.
     */
    private double earningBonus;
    /**
     * Reduction in seed cost when the farmer buys seeds in the Store.
     */
    private double buyingBonus;
    /**
     * Increase in the number of times the farmer can water a plant.
     */
    private int waterBonusLimit;
    /**
     * Increase in the number of times the farmer can fertilize a plant.
     */
    private int fertilizerBonusLimit;


    /**
     * Initializes the attributes of the FarmerBenefits class.
     *
     * @param earningBonus is the additional earnings the farmer gains when harvesting a plant.
     * @param buyingBonus is the reduction in seed cost when the farmer buys seeds in the Store.
     * @param waterBonusLimit is the increase in the number of times the farmer can water a plant.
     * @param fertilizerBonusLimit is the increase in the number of times the farmer can fertilize a plant.
     */
    public FarmerBenefits(double earningBonus, double buyingBonus, int waterBonusLimit, int fertilizerBonusLimit){
        this.earningBonus = earningBonus;
        this.buyingBonus = buyingBonus;
        this.waterBonusLimit = waterBonusLimit;
        this.fertilizerBonusLimit = fertilizerBonusLimit;
    }

    /**
     * The getter method of earningBonus attribute.
     *
     * @return the additional earnings the farmer gains when harvesting a plant.
     */
    public double getEarningBonus() {
        return earningBonus;
    }

    /**
     * The setter method of earningBonus attribute.
     *
     * @param earningBonus the new additional earnings the farmer gains when harvesting a plant.
     */
    public void setEarningBonus(double earningBonus) {
        this.earningBonus = earningBonus;
    }

    /**
     * The getter method of buyingBonus attribute.
     *
     * @return the reduction in seed cost when the farmer buys seeds in the Store.
     */
    public double getBuyingBonus() {
        return buyingBonus;
    }

    /**
     * The setter method of buyingBonus attribute.
     *
     * @param buyingBonus is the new reduction in seed cost when the farmer buys seeds in the Store.
     */
    public void setBuyingBonus(double buyingBonus) {
        this.buyingBonus = buyingBonus;
    }

    /**
     * The getter method of waterBonusLimit attribute.
     *
     * @return the increase in the number of times the farmer can water a plant.
     */
    public int getWaterBonusLimit() {
        return waterBonusLimit;
    }

    /**
     * The setter method of waterBonusLimit attribute.
     *
     * @param waterBonusLimit is the new increase in the number of times the farmer can water a plant.
     */
    public void setWaterBonusLimit(int waterBonusLimit) {
        this.waterBonusLimit = waterBonusLimit;
    }

    /**
     * The getter method of fertilizerBonusLimit attribute.
     *
     * @return the increase in the number of times the farmer can fertilize a plant.
     */
    public int getFertilizerBonusLimit() {
        return fertilizerBonusLimit;
    }

    /**
     * The setter method of fertilizerBonusLimit attribute.
     *
     * @param fertilizerBonusLimit is the new increase in the number of times the farmer can fertilize a plant.
     */
    public void setFertilizerBonusLimit(int fertilizerBonusLimit) {
        this.fertilizerBonusLimit = fertilizerBonusLimit;
    }
}
