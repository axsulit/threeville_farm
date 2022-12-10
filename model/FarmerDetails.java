package model;

/**
 * The model.FarmerDetails class represents the profile of the farmer.
 */
public class FarmerDetails {
    /**
     * Contains the nickname of the farmer.
     */
    private String nickname;
    /**
     * Contains the in-game level of the farmer.
     */
    private int level;
    /**
     * Contains the total number of experience points the farmer has.
     */
    private double playerXP;
    /**
     * Contains the (milestone) title of the farmer.
     */
    private FarmerType farmerType;
    /**
     * Contains the benefits the farmer has based on their title.
     */
    private FarmerBenefits farmerBenefits;

    /**
     * Initializes the attributes of model.FarmerDetails Class.
     */
    public FarmerDetails(){
        this.nickname = "";
        this.level = 0;
        this.playerXP = 0;
        this.farmerType = FarmerType.FARMER;
        this.farmerBenefits = new FarmerBenefits(0.0, 0.0, 0, 0);
    }

    /**
     * The getter method of nickname attribute.
     *
     * @return the current nickname of the model.Farmer.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * The setter method of nickname attribute.
     *
     * @param nickname the chosen nickname of the farmer.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * The getter method of level attribute.
     *
     * @return the current level of the farmrer.
     */
    public int getLevel() {
        return level;
    }

    /**
     * The method increases the level of the player
     *
     * @param level is the updated level of the player.
     */
    public void updateLevel(int level){
        if(this.level != level)
            System.out.println("Well done! You reached Level " + (this.level+1) + "!");
        this.level = level;
    }

    /**
     * The getter method of playerXP attribute.
     *
     * @return the current number of experience points the farmer has.
     */
    public double getPlayerXP() {
        return playerXP;
    }

    /**
     * The setter method of playerXP attribute. This method is also responsible for identifying whether the farmer has advanced to a new level.
     *
     * @param playerXP is the number of experience points the farmer has gained.
     */
    public void addXP(double playerXP){
        this.playerXP += playerXP;
        System.out.println("You gained " + playerXP + " XP!");
        System.out.println("New Current XP: "+ this.playerXP);

        this.updateLevel((int) (Math.round(this.playerXP)/100));
    }

    /**
     * The getter method of farmerType attribute.
     *
     * @return the milestone title of the farmer.
     */
    public FarmerType getFarmerType() {
        return farmerType;
    }

    /**
     * The setter method of farmerType attribute.
     *
     * @param farmerType is the new milestone title of the farmer.
     */
    public void registerFarmerType(FarmerType farmerType) {
        this.farmerType = farmerType;
    }

    /**
     * The getter method of farmerBenefits subclass.
     *
     * @return the instance of the model.FarmerBenefits class associated with the current model.FarmerDetails class.
     */
    public FarmerBenefits getFarmerBenefits() {
        return farmerBenefits;
    }
}
