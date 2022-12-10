import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Farmer class represents the player.
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
     * Instantiates the Farmer class' attributes.
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
     * @return the reference to the farmerDetails associated with the instance of Farmer.
     */
    public FarmerDetails getFarmerDetails(){
        return farmerDetails;
    }

    /**
     * The getter method of the inventory attribute.
     *
     * @return the reference to the inventory associated with the instance of Farmer.
     */
    public Inventory getInventory(){
        return inventory;
    }

    /**
     * The getter method of the money attribute.
     *
     * @return the reference to the money associated with the instance of Farmer.
     */
    public Money getMoney(){
        return money;
    }

    /**
     * The getter method of the stats attribute.
     *
     * @return the reference to the stats associated with the instance of Farmer.
     */
    public Stats getStats() {
        return stats;
    }

    /**
     * Gets user input from the farmer on what item to get from their inventory.
     *
     * @param selectOtherOptions determines whether the list of seeds and fertilizers should be shown.
     */
    public void selectInventoryItem(boolean selectOtherOptions){
        String choice = ""; // Used for user input and data validation

        int slotNum = -1, // Selected Slot Number
                maxSlot = 4, // Max Default Slots (Tools)
                seedNum = 0, // Number of seeds
                seedMaxSlot = 0, // Last Slot for Seeds
                fertilizerSlot = 0; // Fertilizer Slot

        // Compute for other slot numbers to show seeds and fertilizers.
        if(selectOtherOptions){
            seedNum = getInventory().getSeeds().size();
            seedMaxSlot = 4 + seedNum;
            fertilizerSlot = seedMaxSlot + 1;
            maxSlot = fertilizerSlot;
        }

        System.out.println();

        // Prompt to select inventory item to use.
        do {
            System.out.print("Select an item from your inventory to use: ");
            choice = input.nextLine().toUpperCase().trim();

            // data validation
            if(choice.equals("E"))
                return;
            else if(choice.equals(""))
                slotNum = 0;
            else{
                try{
                    slotNum = Integer.parseInt(choice);
                } catch(Exception e) {
                    System.out.println("Invalid input.");
                    slotNum = 0;
                }
            }
        } while(slotNum < 1 || slotNum > maxSlot);

        System.out.println();

        // Call method based on tool
        switch (slotNum) {
            case 1 -> plow(); // Plow
            case 2 -> water(); // Watering Can
            case 3 -> mine(); // Pickaxe
            case 4 -> dig();// Shovel
            default -> {
                // If seed or fertilizer is selected
                if(selectOtherOptions){
                    if(slotNum==fertilizerSlot) // Fertilizer
                        useFertilizer();
                    else{ // Seeds
                        slotNum-=4;

                        int ctr = 1; // Control number to get slot in inventory

                        for(Map.Entry<String, Integer> seed :getInventory().getSeeds().entrySet()){
                            if(ctr==slotNum){
                                plantSeed(seed);
                                break;
                            }
                            else
                                ctr++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Represents the farmer plowing a tile using a plow.
     */
    public void plow(){
        getInventory().useTool("Plow", this.money, this.farmerDetails, this.stats);
    }

    /**
     * Represents the farmer watering a tile using a watering can.
     */
    public void water(){
        getInventory().useTool("WateringCan", this.money, this.farmerDetails, this.stats);
    }

    /**
     * Represents the farmer mining a rock on a tile using a pickaxe.
     */
    public void mine(){
        getInventory().useTool("Pickaxe", this.money, this.farmerDetails, this.stats);
    }

    /**
     * Represents the farmer digging up a tile using a shovel.
     */
    public void dig(){
        getInventory().useTool("Shovel", this.money, this.farmerDetails, this.stats);
    }

    /**
     * Represents the farmer buying seeds at a Store.
     *
     * @param store is where the farmer buys their seeds.
     */
    public void buySeed(Store store, FarmerDetails farmerDetails){
        HashMap<String, Integer> seeds = new HashMap<>(); // List of seeds to store in inventory

        store.sellSeed(this.money, seeds, farmerDetails); // Buy seeds from store

        // Move all newly bought seeds to inventory
        for(Map.Entry<String, Integer> seed: seeds.entrySet()){
            getInventory().addSeeds(seed.getKey(), seed.getValue());
        }
    }

    /**
     * Represents the farmer planting a seed on a tile.
     *
     * @param seed is the seed to be planted.
     */
    public void plantSeed(Map.Entry<String, Integer> seed){
       int tileNum; // Tile number to plant seed on
        Store store = new Store(); // Store where seeds are bought
        Seed seedInfo = null; // seed to

       // Prompt to get input from user on what tile to plant seed on
        do{
            System.out.print("What tile would you like to plant the "+seed.getKey()+ " seed on?: ");
            try{
                tileNum = input.nextInt();
            } catch(Exception e){
                System.out.println("Invalid input.");
                tileNum = 0;
            }
        } while(tileNum < 1 || tileNum > 50);

        // Get seed information
        for(Seed crop : store.getSEEDS()){
            if(crop.getName().equals(seed.getKey())){
                seedInfo = crop;
                break;
            }
        }

        System.out.println();

        int topTile = tileNum - 5, // Tile directly above the chosen tile
                bottomTile = tileNum + 5; // Tile directly below the chosen tile
        boolean outerTileOccupied = false; // Indicates if one of the outer tiles is occupied

        // Checking if one of the outer tiles is occupied
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){
                if(t.getTileNum() == tileNum - 5 || t.getTileNum() == tileNum + 5 || t.getTileNum() == tileNum + 1 || t.getTileNum() == tileNum - 1 || // top, bottom, right, left
                        t.getTileNum() == topTile - 1 || t.getTileNum() == topTile + 1 || // upper diagonals
                        t.getTileNum() == bottomTile - 1 || t.getTileNum() == bottomTile + 1){ // lower diagonals
                    if(t.isOccupied()){
                        outerTileOccupied = true;
                        break;
                    }
                }
            }
        }

        // Find tile
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){
                if(t.getTileNum()==tileNum){
                    // Check if tile is occupied
                    if(t.isOccupied()){
                        System.out.println("This tile is already occupied!");
                        break;
                    } else if(t.getTileType().equals(TileType.UNPLOWED)){
                        System.out.println("You cannot plant on an unplowed tile!");
                        break;
                    } else {
                        if((seedInfo.getCropType() == CropType.FRUIT_TREE)){
                            if((tileNum % 10 == 1 || tileNum % 10 == 5 || tileNum % 10 == 6 || tileNum % 10 == 0) ||
                                    (tileNum > 1 && tileNum < 5) || (tileNum > 46 && tileNum < 50)) {
                                System.out.println("You cannot plant a fruit tree on the edge of the land!");
                            }
                            else if(outerTileOccupied){ // outer tiles are occupied
                                System.out.println("This space is too small for a fruit tree!");
                            }
                            else{
                                System.out.println("Planting seed...");

                                t.getPlant().setSeedDetails(seedInfo);
                                t.getPlant().setDayPlanted(MyFarm.getDay());
                                t.getPlant().setDayToHarvest(MyFarm.getDay() + t.getPlant().getSeedDetails().getHarvestTime());
                                t.setTileType(TileType.HAS_PLANT);
                                t.setOccupied(true);
                                MyFarm.decreaseVacancy();
                                stats.addTimesPlanted();

                                System.out.println("Seed Planted!");
                                System.out.printf(" Day planted: %d\n Day to Harvest: %d \n", t.getPlant().getDayPlanted(), t.getPlant().getDayToHarvest());
                                getInventory().useSeed(seed.getKey());
                            }
                        }
                        else {
                            System.out.println("Planting seed...");

                            t.getPlant().setSeedDetails(seedInfo);
                            t.getPlant().setDayPlanted(MyFarm.getDay());
                            t.getPlant().setDayToHarvest(MyFarm.getDay() + t.getPlant().getSeedDetails().getHarvestTime());
                            t.setTileType(TileType.HAS_PLANT);
                            t.setOccupied(true);
                            MyFarm.decreaseVacancy();
                            stats.addTimesPlanted();

                            System.out.println("Seed Planted!");
                            System.out.printf(" Day planted: %d\n Day to Harvest: %d \n", t.getPlant().getDayPlanted(), t.getPlant().getDayToHarvest());
                            getInventory().useSeed(seed.getKey());
                        }
                        break;
                    }
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

    /**
     * Represents the farmer using fertilizer on a plant.
     */
    public void useFertilizer(){
        getInventory().useFertilizerUnit(this.farmerDetails, this.stats);
    }

    /**
     * Represents the farmer harvesting a successfully grown plant.
     */
    public void harvestCrop(){
        int harvestCtr = 0, // The number of crops ready to be harvested
                tileNum = 0; // The tile of plant to harvest
        String choice = ""; // Used for input and data validation
        double harvestPrice, // Initial harvest price
                harvestPriceTotal, // final harvest price
                waterBonus, // water bonus
                fertilizerBonus; // fertilizer bonus

        // Count the number of plants to harvest
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile)
                if(t.getTileType() == TileType.TO_HARVEST)
                    harvestCtr++;
        }

        System.out.println();

        // Check if there are plants to harvest
        if(harvestCtr == 0)
            System.out.println("There are no crops ready for harvesting yet!");
        else {
            System.out.println("You have " + this.getMoney().getObjectCoins() + " OC in your wallet.\n");

            // Prompt to get input on which tile to harvest
            do{
                System.out.print("Select a Tile to Harvest the Produce: ");
                    choice = input.nextLine().trim();
                // Data validation
                try{
                    tileNum = Integer.parseInt(choice);
                }catch(Exception e){
                    System.out.println("Invalid input.");
                    tileNum = 0;
                }
            }while(tileNum < 1 || tileNum > 50);

            // Find tile
            for(Tile[] tile: Board.getTile()){
                for(Tile t: tile){
                    if(t.getTileNum() == tileNum){
                        // If tile is ready to harvest
                        if(t.getTileType() == TileType.TO_HARVEST){
                            t.setTileType(TileType.UNPLOWED);
                            t.setOccupied(false);
                            MyFarm.increaseVacancy();

                            Plant p = t.getPlant(); // get plant from tile
                            double xp = t.getPlant().getSeedDetails().getXpYield(); // get corresponding xp per produce

                            // Computation of harvest price total
                            harvestPrice = p.getActualProduceNum() * (p.getSeedDetails().getBaseSPPerProduce() + getFarmerDetails().getFarmerBenefits().getEarningBonus());
                            waterBonus = harvestPrice * 0.2 * (p.getTimesWatered() - 1);
                            fertilizerBonus = harvestPrice * 0.5 * p.getTimesFertilized();
                            harvestPriceTotal = harvestPrice + waterBonus + fertilizerBonus;

                            if(p.getSeedDetails().getCropType() == CropType.FLOWER){
                                harvestPriceTotal *= 1.1;
                            }

                            System.out.println("\nYou are harvesting " + p.getActualProduceNum() + " " + p.getSeedDetails().getName() + "/s. ");

                            System.out.println("Base Price:\t" + p.getSeedDetails().getBaseSPPerProduce() + " (" + p.getActualProduceNum() + ")" + "\n" +
                                    "Water bonus:\t" + waterBonus + "\n" +
                                    "Fertilizer bonus:\t" + fertilizerBonus + "\n" +
                                    "Earning bonus:\t" + getFarmerDetails().getFarmerBenefits().getEarningBonus() + "\n");

                            System.out.println("\nYou just earned a total of " + harvestPriceTotal + " OC!");
                            getMoney().addObjectCoins(harvestPriceTotal);

                            this.stats.setProductsHarvested(p.getActualProduceNum() + this.stats.getProductsHarvested()); // Update products harvested

                            this.farmerDetails.addXP(xp);

                            t.setPlant(new Plant());
                        }
                        else if(t.getTileType() == TileType.HAS_PLANT)
                            System.out.println("The plant is not yet ready to be harvested!");
                        else if(t.getTileType() == TileType.WITHERED )
                            System.out.println("The plant cannot be harvested anymore...");
                        else
                            System.out.println("There is no plant on this tile!");
                        break;
                    }
                }
            }
        }
    }
}
