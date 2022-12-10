import java.util.Scanner;

/**
 * The WateringCan class represents a watering can.
 */
class WateringCan {
    /**
     * Contains the name of the tool.
     */
    private final String NAME = "Watering Can";
    /**
     * Contains the cost to use the tool.
     */
    private final double COST = 0.0;
    /**
     * Contains the number of experience points to be earned when the tool is used successfully.
     */
    private final double XP = 0.5;

    /**
     * Represents watering a plant.
     *
     * @param tileNum is the tile number to be watered.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    void waterPlant(int tileNum, FarmerDetails farmerDetails, Stats stats){
        System.out.println();

        // Find Tile
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){
                if(t.getTileNum()==tileNum){
                    if(t.getTileType() == TileType.HAS_PLANT){
                        // Allow tile to be watered if current number of times watered is less than the limit
                        if(t.getPlant().getTimesWatered()<t.getPlant().getSeedDetails().getWaterNeeded()+t.getPlant().getSeedDetails().getWaterBonus()){
                            System.out.println("Watering Tile " + tileNum);
                            t.getPlant().setTimesWatered(t.getPlant().getTimesWatered()+1);
                            System.out.println("Watered Tile " + tileNum);
                            stats.addTimesWatered();

                            // add Farmer XP
                            farmerDetails.addXP(XP);
                        }
                        else
                            System.out.println("This tile has already reached the water limit!");
                    }
                    else if(t.getTileType() == TileType.TO_HARVEST)
                        System.out.println("This tile is about to be harvested!");
                    else if(t.getTileType() == TileType.WITHERED)
                        System.out.println("You cannot water a withered plant...");
                    else
                        System.out.println("Tile has no seed planted. It cannot be watered!");

                    break;
                }
            }
        }
    }
}

/**
 * The Plow class represents a plow.
 */
class Plow {
    /**
     * Contains the name of the tool.
     */
    private final String NAME = "Plow";
    /**
     * Contains the cost to use the tool.
     */
    private final double COST = 2.0;
    /**
     * Contains the number of experience points to be earned when the tool is used successfully.
     */
    private final double XP = 0.5;

    /**
     * Represents plowing a tile.
     *
     * @param tileNum is the tile to be plowed.
     * @param money is the wallet of the farmer.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    void plowTile(int tileNum, Money money, FarmerDetails farmerDetails, Stats stats){
        System.out.println();

        // Find Tile
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){

                if(t.getTileNum()==tileNum){
                    // Check if Tile is unplowed
                    if(t.getTileType() == TileType.UNPLOWED){
                        System.out.println("Plowing Tile " + tileNum);
                        t.setTileType(TileType.PLOWED);
                        System.out.println("Plowed Tile " + tileNum);
                        stats.addTimesPlowed();

                        // add Farmer XP
                        farmerDetails.addXP(XP);
                    }
                    else if(t.getTileType() == TileType.WITHERED){
                        if(money.getObjectCoins() < COST)
                            System.out.println("You don't have enough ObjectCoins to plow a withered crop!");
                        else{
                            t.setTileType(TileType.UNPLOWED);
                            System.out.println("Removed withered plant from Tile " + tileNum);
                            stats.addTimesPlowed();
                            t.setOccupied(false);
                            MyFarm.increaseVacancy();

                            // spend ObjectCoins
                            money.spendObjectCoins(COST);

                            // add Farmer XP
                            farmerDetails.addXP(XP);
                        }
                    }
                    else
                        System.out.println("You cannot plow this tile!");
                    break;
                }
            }
        }
    }
}

/**
 * The Pickaxe class represents a pickaxe.
 */
class Pickaxe{
    /**
     * Contains the name of the tool.
     */
    private final String NAME = "Pickaxe";
    /**
     * Contains the cost to use the tool.
     */
    private final double COST = 50.0;
    /**
     * Contains the number of experience points to be earned when the tool is used successfully.
     */
    private final double XP = 15.0;

    /**
     * Represents mining a rock.
     *
     * @param tileNum is the tile to be plowed.
     * @param money is the wallet of the farmer.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    void removeRocks(int tileNum, Money money, FarmerDetails farmerDetails, Stats stats){
        System.out.println();

        // Find Tile
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){

                if(t.getTileNum()==tileNum){
                    // Check if tile has rock
                    if(t.getTileType() == TileType.HAS_ROCK) {

                        if(money.getObjectCoins() < COST)
                            System.out.println("You don't have enough ObjectCoins to mine this tile!");

                        else{
                            System.out.println("Removing rock from Tile " + tileNum);
                            t.setTileType(TileType.UNPLOWED);
                            System.out.println("Removed rock from Tile " + tileNum);

                            t.setOccupied(false);
                            // spend ObjectCoins
                            money.spendObjectCoins(COST);

                            stats.addRocksMined();

                            // add Farmer XP
                            farmerDetails.addXP(XP);
                        }
                    }
                    else
                        System.out.println("There are no rocks to mine!");
                    break;
                }
            }
        }
    }
}

/**
 * The Shovel class represents a shovel.
 */
class Shovel{
    /**
     * Contains the name of the tool.
     */
    private final String NAME = "Shovel";
    /**
     * Contains the cost to use the tool.
     */
    private final double COST = 7.0;
    /**
     * Contains the number of experience points to be earned when the tool is used successfully.
     */
    private final double XP = 2.0;

    /**
     * Represents digging a tile.
     *
     * @param tileNum is the tile to be plowed.
     * @param money is the wallet of the farmer.
     * @param farmerDetails is the profile of the farmer.
     */
    void digTile(int tileNum, Money money, FarmerDetails farmerDetails){
        System.out.println();

        // Find Tile
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){

                if(t.getTileNum()==tileNum){
                    // Check if Tile is not a Rock
                    if(t.getTileType()!= TileType.HAS_ROCK){

                        if(money.getObjectCoins() < COST)
                            System.out.println("You don't have enough ObjectCoins to dig this tile!");
                        else{
                            System.out.println("Digging Tile " + tileNum);
                            t.setTileType(TileType.UNPLOWED);
                            System.out.println("Dug Tile " + tileNum);

                            // spend ObjectCoins
                            money.spendObjectCoins(COST);

                            // add Farmer XP
                            farmerDetails.addXP(XP);

                            if(t.isOccupied()){
                                t.setOccupied(false);
                                MyFarm.increaseVacancy();
                            }

                            break;
                        }
                    } else{
                        System.out.println("You can't dig a rock! Use a pickaxe next time...");
                        money.spendObjectCoins(COST);
                    }
                }
            }
        }
    }
}

/**
 * The Tools class represents the tools the farmer can use to prepare the farm lot.
 */
public class Tools {
    /**
     * Represents the plow tool.
     */
    private final Plow plow;
    /**
     * Represents the watering can tool.
     */
    private final WateringCan wateringCan;
    /**
     * Represents the pickaxe tool.
     */
    private final Pickaxe pickaxe;
    /**
     * Represents the shovel tool.
     */
    private final Shovel shovel;
    /**
     * Used for user input.
     */
    private final Scanner input = new Scanner(System.in);

    /**
     * Instantiates the attributes of the Tools class.
     */
    public Tools(){
        this.plow = new Plow();
        this.wateringCan = new WateringCan();
        this.pickaxe = new Pickaxe();
        this.shovel = new Shovel();
    }

    /**
     * The getter method for the wateringCan tool.
     *
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    public void getWateringCan(FarmerDetails farmerDetails, Stats stats){
        int tile; // The tile number to water
        String choice = ""; // Used for input and data validation

        // Prompt to get what tile to water
        do{
            System.out.print("What tile would you like to water?: ");
            choice = input.nextLine().trim();
            // Input validation
            try{
                tile = Integer.parseInt(choice);
            } catch (Exception e){
                System.out.println("Invalid input.");
                tile = 0;
            }
        } while(tile < 1 || tile > 50);

        wateringCan.waterPlant(tile, farmerDetails, stats);
    }

    /**
     * The getter method for the plow tool.
     *
     * @param money is the farmer's wallet.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    public void getPlow(Money money, FarmerDetails farmerDetails, Stats stats){
        int tile; // The tile number to plow
        String choice = ""; // Used for input and data validation

        // Prompt to get what tile to plow
        do{
            System.out.print("What tile would you like to plow?: ");
            choice = input.nextLine().trim();
            // Input validation
            try{
                tile = Integer.parseInt(choice);
            } catch (Exception e){
                System.out.println("Invalid input.");
                tile = 0;
            }
        } while(tile < 1 || tile > 50);

        plow.plowTile(tile, money, farmerDetails, stats);
    }

    /**
     * The getter method for the pickaxe tool.
     *
     * @param money is the farmer's wallet.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    public void getPickaxe(Money money, FarmerDetails farmerDetails, Stats stats){
        int tile; // The tile number to mine
        String choice = ""; // Used for input and data validation

        // Prompt to get what tile to mine
        do{
            System.out.print("What tile would you like to mine?: ");
            choice = input.nextLine().trim();
            // Input validation
            try{
                tile = Integer.parseInt(choice);
            } catch (Exception e){
                System.out.println("Invalid input.");
                tile = 0;
            }
        } while(tile < 1 || tile > 50);

        pickaxe.removeRocks(tile, money, farmerDetails, stats);
    }

    /**
     * The getter method for the shovel tool.
     *
     * @param money is the farmer's wallet.
     * @param farmerDetails is the profile of the farmer.
     */
    public void getShovel(Money money, FarmerDetails farmerDetails){
        int tile; // The tile number to dig
        String choice = ""; // Used for input and data validation

        // Prompt to get what tile to dig
        do{
            System.out.print("What tile would you like to dig?: ");
            choice = input.nextLine().trim();
            // Input validation
            try{
                tile = Integer.parseInt(choice);
            } catch (Exception e){
                System.out.println("Invalid input.");
                tile = 0;
            }
        } while(tile < 1 || tile > 50);

        shovel.digTile(tile, money, farmerDetails);
    }
}
