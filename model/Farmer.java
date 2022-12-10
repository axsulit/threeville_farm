package model;

import java.util.Scanner;

/**
 * The model.Farmer class represents the player.
 */
public class Farmer {
    /**
     * Used for user input.
     */
    private Scanner input = new Scanner(System.in);
    /**
     * Contains the profile of the farmer.
     */
    private FarmerDetails farmerDetails;
    /**
     * Contains the backpack/inventory items of the farmer.
     */
    private Inventory inventory;
    /**
     * Contains the wallet of the farmer.
     */
    private Money money;
    /**
     * Contains the statistics of the farmer.
     */
    private Stats stats;

    /**
     * Instantiates the model.Farmer class' attributes.
     */
    public Farmer(){
        this.farmerDetails = new FarmerDetails();
        this.inventory = new Inventory();
        this.money = new Money();
        this.stats = new Stats();
    }

    /**
     * The getter method of the farmerDetails attribute.
     *
     * @return the reference to the farmerDetails associated with the instance of model.Farmer.
     */
    public FarmerDetails getFarmerDetails(){
        return farmerDetails;
    }

    /**
     * The getter method of the inventory attribute.
     *
     * @return the reference to the inventory associated with the instance of model.Farmer.
     */
    public Inventory getInventory(){
        return inventory;
    }

    /**
     * The getter method of the money attribute.
     *
     * @return the reference to the money associated with the instance of model.Farmer.
     */
    public Money getMoney(){
        return money;
    }

    /**
     * The getter method of the stats attribute.
     *
     * @return the reference to the stats associated with the instance of model.Farmer.
     */
    public model.Stats getStats() {
        return stats;
    }

    /**
     * Represents the farmer plowing a tile using a plow.
     */
    public void plow(int tileNum){
        getInventory().useTool("Plow", tileNum, this.money, this.farmerDetails, this.stats);
    }

    /**
     * Represents the farmer watering a tile using a watering can.
     */
    public void water(int tileNum){
        getInventory().useTool("WateringCan", tileNum, this.money, this.farmerDetails, this.stats);
    }

    /**
     * Represents the farmer mining a rock on a tile using a pickaxe.
     */
    public void mine(int tileNum){
        getInventory().useTool("Pickaxe", tileNum, this.money, this.farmerDetails, this.stats);
    }

    /**
     * Represents the farmer digging up a tile using a shovel.
     */
    public void dig(int tileNum){
        getInventory().useTool("Shovel", tileNum, this.money, this.farmerDetails, this.stats);
    }


    /**
     * Represents the farmer planting a seed on a tile.
     *
     * @param seed is the seed to be planted.
     */
    public void plantSeed(int tileNo, String seed, Seed seedInf, int day){
       int tileNum = tileNo; // model.Tile number to plant seed on
        Store store = new Store(); // Store where seeds are bought
        Seed seedInfo = seedInf; // seed to

        // Find tile
        for(model.Tile[] tile: model.Board.getTile()){
            for(model.Tile t: tile){
                if(t.getTileNum()==tileNum){
                    // Check if tile is occupied
                        if((seedInfo.getCropType() == model.CropType.FRUIT_TREE)){
                            if((tileNum % 10 == 1 || tileNum % 10 == 5 || tileNum % 10 == 6 || tileNum % 10 == 0) ||
                                    (tileNum > 1 && tileNum < 5) || (tileNum > 46 && tileNum < 50)) {
                                //do nothing
                            }
//                            else if(outerTileOccupied){ // outer tiles are occupied
//                                System.out.println("This space is too small for a fruit tree!");
//                            }
                            else{
                                t.getPlant().setSeedDetails(seedInfo);
                                t.getPlant().setDayPlanted(day);
                                t.getPlant().setDayToHarvest(day + t.getPlant().getSeedDetails().getHarvestTime());
                                t.setTileType(TileType.HAS_PLANT);
                                t.setOccupied(true);
                                stats.addTimesPlanted();

                            }
                        }
                        else {
                            t.getPlant().setSeedDetails(seedInfo);
                            t.getPlant().setDayPlanted(day);
                            t.getPlant().setDayToHarvest(day + t.getPlant().getSeedDetails().getHarvestTime());
                            t.setTileType(TileType.HAS_PLANT);
                            t.setOccupied(true);
                            stats.addTimesPlanted();
                        }
                        break;
                    }
                }
            }
    }

    /**
     * Represents the farmer buying fertilizer from the store.
     *
     * @param store is where the farmer buys fertilizers.
     */
    public void buyFertilizer(Store store){
        store.sellFertilizer(this.money, this.inventory);
    }
//
//    /**
//     * Represents the farmer using fertilizer on a plant.
//     */
//    public void useFertilizer(){
//        getInventory().useFertilizerUnit(this.farmerDetails, this.stats);
//    }
//
//    /**
//     * Represents the farmer harvesting a successfully grown plant.
//     */
//    public void harvestCrop(){
//        int harvestCtr = 0, // The number of crops ready to be harvested
//                tileNum = 0; // The tile of plant to harvest
//        String choice = ""; // Used for input and data validation
//        double harvestPrice, // Initial harvest price
//                harvestPriceTotal, // final harvest price
//                waterBonus, // water bonus
//                fertilizerBonus; // fertilizer bonus
//
//        // Count the number of plants to harvest
//        for(model.Tile[] tile: model.Board.getTile()){
//            for(model.Tile t: tile)
//                if(t.getTileType() == model.TileType.TO_HARVEST)
//                    harvestCtr++;
//        }
//
//        System.out.println();
//
//        // Check if there are plants to harvest
//        if(harvestCtr == 0)
//            System.out.println("There are no crops ready for harvesting yet!");
//        else {
//            System.out.println("You have " + this.getMoney().getObjectCoins() + " OC in your wallet.\n");
//
//            // Prompt to get input on which tile to harvest
//            do{
//                System.out.print("Select a model.Tile to Harvest the Produce: ");
//                    choice = input.nextLine().trim();
//                // Data validation
//                try{
//                    tileNum = Integer.parseInt(choice);
//                }catch(Exception e){
//                    System.out.println("Invalid input.");
//                    tileNum = 0;
//                }
//            }while(tileNum < 1 || tileNum > 50);
//
//            // Find tile
//            for(model.Tile[] tile: model.Board.getTile()){
//                for(model.Tile t: tile){
//                    if(t.getTileNum() == tileNum){
//                        // If tile is ready to harvest
//                        if(t.getTileType() == model.TileType.TO_HARVEST){
//                            t.setTileType(model.TileType.UNPLOWED);
//                            t.setOccupied(false);
//                            MyFarm.increaseVacancy();
//
//                            model.Plant p = t.getPlant(); // get plant from tile
//                            double xp = t.getPlant().getSeedDetails().getXpYield(); // get corresponding xp per produce
//
//                            // Computation of harvest price total
//                            harvestPrice = p.getActualProduceNum() * (p.getSeedDetails().getBaseSPPerProduce() + getFarmerDetails().getFarmerBenefits().getEarningBonus());
//                            waterBonus = harvestPrice * 0.2 * (p.getTimesWatered() - 1);
//                            fertilizerBonus = harvestPrice * 0.5 * p.getTimesFertilized();
//                            harvestPriceTotal = harvestPrice + waterBonus + fertilizerBonus;
//
//                            if(p.getSeedDetails().getCropType() == model.CropType.FLOWER){
//                                harvestPriceTotal *= 1.1;
//                            }
//
//                            System.out.println("\nYou are harvesting " + p.getActualProduceNum() + " " + p.getSeedDetails().getName() + "/s. ");
//
//                            System.out.println("Base Price:\t" + p.getSeedDetails().getBaseSPPerProduce() + " (" + p.getActualProduceNum() + ")" + "\n" +
//                                    "Water bonus:\t" + waterBonus + "\n" +
//                                    "Fertilizer bonus:\t" + fertilizerBonus + "\n" +
//                                    "Earning bonus:\t" + getFarmerDetails().getFarmerBenefits().getEarningBonus() + "\n");
//
//                            System.out.println("\nYou just earned a total of " + harvestPriceTotal + " OC!");
//                            getMoney().addObjectCoins(harvestPriceTotal);
//
//                            this.stats.setProductsHarvested(p.getActualProduceNum() + this.stats.getProductsHarvested()); // Update products harvested
//
//                            this.farmerDetails.addXP(xp);
//
//                            t.setPlant(new model.Plant());
//                        }
//                        else if(t.getTileType() == model.TileType.HAS_PLANT)
//                            System.out.println("The plant is not yet ready to be harvested!");
//                        else if(t.getTileType() == model.TileType.WITHERED )
//                            System.out.println("The plant cannot be harvested anymore...");
//                        else
//                            System.out.println("There is no plant on this tile!");
//                        break;
//                    }
//                }
//            }
//        }
//    }
}
