package model;

import model.tools.*;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The Inventory class represents the inventory or backpack of the farmer.
 */
public class Inventory {

    private Plow plow;
    private Shovel shovel;
    private WateringCan wateringCan;
    private Pickaxe pickaxe;
    /**
     * Contains the list of seeds the farmer can plant.
     */
    private HashMap<String, Integer> seeds;
    /**
     * Contains the number of fertilizer units the farmer has.
     */
    private int fertilizerUnits;
    /**
     * Used to get input from the farmer.
     */
    private Scanner input = new Scanner(System.in);

    /**
     * Initializes the attributes of the Inventory class.
     */
    public Inventory(){
        this.plow = new Plow("Plow", 2.0, 0.5);
        this.shovel = new Shovel("Shovel", 7.0, 2.0);
        this.wateringCan = new WateringCan("Watering Can", 0.0, 0.5);
        this.pickaxe = new Pickaxe("Pickaxe", 50.0, 15.0);
        this.seeds = new HashMap<String, Integer>();
        this.fertilizerUnits = 5;
     }

    /**
     * Displays the player's inventory of tools, seeds, and fertilizers.
     *
     * @param showOtherOptions determines whether to show the list of seeds and fertilizers available.
     */
    public void displayInventory(boolean showOtherOptions){
         int slots = 14;
        AtomicInteger ctr = new AtomicInteger(5);

         System.out.print("""
                 \t[1] Plow                Free | 2.0 OC (Withered crops)
                 \t[2] Watering Can        Free
                 \t[3] Pickaxe          50.0 OC
                 \t[4] Shovel            7.0 OC
                 """);

         if(showOtherOptions){
             seeds.forEach((key, value) -> System.out.println("\t["+ ctr.getAndIncrement() +"] " + key + " : " + value));
             System.out.println("\t["+ctr.getAndIncrement()+"] Fertilizer Units : " + getFertilizerUnits());
         }

         System.out.println("\t[E]xit Inventory");
     }

    /**
     * Gets the tool the farmer wants to use in the Tools class.
     *
     * @param tool is the name of the tool the farmer wants to use.
     * @param money is the wallet of the farmer.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the statistics of the farm and the farmer.
     */
    public void useTool(String tool, int tileNum, Money money, FarmerDetails farmerDetails, Stats stats) {
        switch (tool) {
            case "Plow" -> this.plow.plowTile(tileNum, money, farmerDetails, stats);
            case "WateringCan" -> this.wateringCan.waterPlant(tileNum, farmerDetails, stats);
            case "Pickaxe" -> this.pickaxe.removeRocks(tileNum, money, farmerDetails, stats);
            case "Shovel" -> this.shovel.digTile(tileNum, money, farmerDetails);
        }
    }

    /**
     * The getter method of the seeds attribute.
     *
     * @return the list and number of seeds the farmer has.
     */
     public HashMap<String, Integer> getSeeds() {
          return seeds;
     }

    /**
     * The method that adds to the current list and number of seeds the farmer has.
     *
     * @param seedName is the name of the recently acquired seed/s.
     * @param seedNum is the number of seeds the farmer has acquired.
     */
     public void addSeeds(String seedName, int seedNum) {
        boolean isFound = false;

        if(seeds.isEmpty())
            seeds.put(seedName, seedNum);
        else {
            for(Entry<String, Integer> seedVal: seeds.entrySet()){
                if(seedVal.getKey().equals(seedName)){
                    if(seedVal.getValue()==0)
                        seedVal.setValue(seedNum);
                    else
                        seedVal.setValue(seedVal.getValue()+seedNum);
                    isFound = true;
                    break;
                }
            }

            if(!isFound)
                seeds.put(seedName, seedNum);
        }
     }

    /**
     * The method that reduces the number of seeds in the farmer's inventory.
     *
     * @param seedName is the name of the seed to plant.
     */
     public void useSeed(String seedName){
         boolean isFound = false; //Whether the seed indicated is in farmer's inventory.

         // Check if seed list is empty.
         if(seeds.isEmpty())
             System.out.println("There are no seeds left!");
         else {
             // Update seed list and number
             for(Entry<String, Integer> seedVal: seeds.entrySet()){
                 if(seedVal.getKey().equals(seedName)){
                     seedVal.setValue(seedVal.getValue()-1);

                     if(seedVal.getValue()==0)
                         seeds.remove(seedName);

                     isFound = true;
                     break;
                 }
             }

             if(!isFound)
                 System.out.println("There are no " + seedName + "s left!");
         }
     }

    /**
     * The getter method of fertilizerUnits attribute.
     *
     * @return the number of fertilizer units the farmer has.
     */
    public int getFertilizerUnits() {
          return fertilizerUnits;
     }

    /**
     * The increment method of fertilizerUnits attribute.
     *
     * @param fertilizerUnits the number of newly bought fertilizer units.
     */
    public void addFertilizerUnits(int fertilizerUnits) {
          this.fertilizerUnits += fertilizerUnits;
     }

    /**
     * The decrement method of fertilizerUnits attribute.
     *
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the statistics of the farm and the farmer.
     */
     public void useFertilizerUnit(FarmerDetails farmerDetails, Stats stats) {
         double xp = 4.0; // XP to gain when farmer uses fertilizer
         int tileNum = 0; // Tile to fertilizer
         String choice = ""; // Used for input for data validation

         System.out.println();

         // Look for Tile
         for(Tile[] tile: Board.getTile()){
             for(Tile t: tile){
                 if(t.getTileNum()==tileNum) {
                     // Check if tile has plant
                     if(t.getTileType()== TileType.HAS_PLANT){
                         // If times fertilized is less than max with bonus limit
                         if(t.getPlant().getTimesFertilized()<t.getPlant().getSeedDetails().getFertilizerNeeded()+t.getPlant().getSeedDetails().getFertilizerBonus()){
                             t.getPlant().setTimesFertilized(t.getPlant().getTimesFertilized()+1);
                             this.fertilizerUnits -= 1;
                             //stats.addTimesFertilized();

                             // add Farmer XP
                             farmerDetails.addXP(xp);
                         }
                     }
                     break;
                 }
             }
         }
     }
}
