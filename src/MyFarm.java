/*********************************************************************************************************
 This is to certify that this project is our own work, based on our personal efforts in studying and
 applying the concepts learned. We have constructed the functions and their respective algorithms and
 corresponding code by ourselves. The Program was run, tested, and debugged by our own efforts. We further
 certify that we have not copied in part or whole or otherwise plagiarized the work of other students
 and/or persons.

 Sulit, Anne Gabrielle M. - 12112871 - S15
 Torio, Ysobella D. - 12172030 - S15
 *********************************************************************************************************/
import java.util.*;

/**
 * The MyFarm class is ThreeVille Farm's Driver class that implements a console-based farming simulator
 * where the player acts as the sole farmer (and manager) of their own farm.
 *
 * @author Anne Sulit, Ysobella Torio
 *
 */
public class MyFarm {
    /**
     * Determines whether the farmer can continue playing ThreeVille Farm.
     */
    private static boolean isRunning = true;
    /**
     * Determines whether the farmer (player) has entered the second main nested loop in main() for the first time.
     */
    private static boolean firstRun = true;
    /**
     * Contains the number of vacant tiles in the farm lot.
     */
    private static int vacancy = 50;
    /**
     * Contains the current day number.
     */
    private static int day = 1;

    /**
     * Decrements the value of the vacancy attribute.
     */
    public static void decreaseVacancy(){
        vacancy--;
    }

    /**
     * Increments the value of the vacancy attribute.
     */
    public static void increaseVacancy(){
        vacancy++;
    }

    /**
     * The getter method of the day attribute.
     * @return the value of day.
     */
    public static int getDay(){
        return day;
    }

    /**
     * Increments the value of the day attribute.
     */
    public static void updateDay(){
        day++;
    }

    /**
     * Sole constructor. (For invocation by subclass
     * constructors, typically implicit.)
     */
    public MyFarm() { }

    /**
     * This method determines whether the farmer can keep playing by checking for the size
     * of the seed list in the farmer's inventory and the number of withered plants on the farm lot.
     *
     * @param seed This contains the list of seeds the farmer has in their inventory.
     * @param money This contains the Objectcoins of the farmer.
     *
     * @return the updated value of isRunning.
     */
    public static boolean isGameRunning(HashMap<String, Integer> seed, Money money){
        int seedSize = seed.size(),   // Number of Seeds in Inventory
                witheredPlantsCount = 0; // Number of Withered Plants

        // If program enters 2nd nested loop for the first time.
        if(firstRun){
            seedSize = 1;
            firstRun = false;
        } else {
            // Loops through all tiles to get number of withered plants.
            for(Tile[] tile: Board.getTile()){
                for(Tile t: tile){
                    if(t.getTileType()==TileType.WITHERED)
                        witheredPlantsCount++;
                }
            }
        }

        isRunning = !((seedSize == 0 && vacancy == 50 && money.getObjectCoins() < 5.0) || witheredPlantsCount == 50);
        return isRunning;
    }

    /**
     * This is the main method which makes use of isGameRunning method and methods of other classes.
     * @param args Unused.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // Used for Input
        Board board = new Board(); // Farm Lot of MyFarm
        Store store = new Store(); // Contains Seeds and Fertilizers that can be bought
        Farmer farmer = new Farmer(); // The Player

        String choice = "", // Used for String input
                nickname;   // Farmer's nickname
        int taskNum = 0,    // Task to be Accomplished
                tileNum,    // Tile number to be accessed
                witheredPlantsCount = 0; // Number of withered crops
        boolean newGame = true; // Determines whether the farmer (player) opts to start a new game or exit the program.

        // Outermost Loop; Responsible for driving the entire game.
        do {
            // Introduction
            System.out.println("THREEVILLE FARM\nDeveloped by: Anne Sulit & Ysobella Torio\n");

            // Select Game Mode
            System.out.println("Farm Modes:\n\t[E]asy (No Rocks) \n\t[H]ard (Scattered Rocks) \n");
            do{
                System.out.print("Select Mode: ");
                choice = input.nextLine().toUpperCase().trim();
            } while(!choice.equals("E") && !choice.equals("H"));

            // If Player opts to have rocks in farm lot
            if(choice.equals("H")){
                System.out.println("\nGenerating farmland...\n");
                System.out.print("Enter your file name: ");
                String fileName = input.nextLine();
                board.readFileInput(fileName);
            }

            System.out.println();

            // Prompt to get input for nickname of Farmer
            do{
                System.out.print("Welcome to your new farm! What would you like us to call you?: ");
                nickname = input.nextLine();
            } while(nickname.isEmpty());

            farmer.getFarmerDetails().setNickname(nickname); // Send nickname to farmerDetails

            // Starting Info UI
            System.out.println("\nGreat! Before you start, here are some things you need to know: " +
                    "\n\tCurrent Title: " + farmer.getFarmerDetails().getFarmerType() +
                    "\n\tCurrent Level: " + farmer.getFarmerDetails().getLevel() + " ("+farmer.getFarmerDetails().getPlayerXP()+" XP)" +
                    "\n\tIn Your Wallet: " + farmer.getMoney().getObjectCoins() + " Objectcoins");

            System.out.println();

            // Prompt to see starting farm lot
            do{
                System.out.print("Now, would you like to see your starting farm lot, " + farmer.getFarmerDetails().getNickname() + " [Y/N]? : ");
                choice = input.nextLine().toUpperCase().trim();
            } while(!choice.equals("Y") && !choice.equals("N"));

            if (choice.equals("Y")) {
                board.printBoard();
            }

            // Main Loop; While Player does not meet the conditions for ending the game.
            while(isGameRunning(farmer.getInventory().getSeeds(), farmer.getMoney())) {

                // Loops through all tiles to get number of withered plants.
                for(Tile[] tile: Board.getTile()){
                    for(Tile t: tile){
                        if(t.getTileType()==TileType.WITHERED)
                            witheredPlantsCount++;
                    }
                }

                System.out.println("\nIt's DAY "+ day + "!");

                // Alerts the player if a crop has withered.
                if(witheredPlantsCount == 1){
                    System.out.println("\nOh no! A crop has withered!");
                    witheredPlantsCount = 0;
                } else if(witheredPlantsCount > 1){
                    System.out.println("\nOh no! Your crops have withered!");
                    witheredPlantsCount = 0;
                }

                // UI to show what tasks can be accomplished
                System.out.println("""
                        \nHere's what you can do:
                          ╰ FARM                                 ╰ STORE
                             [1] View Farm Lot                      [6] Buy Seeds
                             [2] View Tile                          [7] Buy Fertilizer
                             [3] Prepare Land
                             [4] Plant Seeds / Fertilizer        ╰ FARMER
                             [5] Harvest Crops                      [8] View Statistics
                                                                    [9] View Inventory
                          ╰ [A]dvance to Next Day                   [0] Register Farmer Type       \n""");

                // Get user input on what task to do
               do{
                    System.out.print("What would you like to do?: ");
                    choice = input.nextLine().toUpperCase().trim();

                   // Input validation
                    if(choice.equals("A"))
                        taskNum = 11;
                    else if(choice.equals("") || choice.equals("11"))
                        taskNum = 10;
                    else {
                        try{
                            taskNum = Integer.parseInt(choice);
                        } catch(Exception e){
                            System.out.println("Invalid input.");
                            taskNum = 10;
                        }
                    }

                } while((taskNum < 0 || taskNum > 11) || taskNum == 10);

                // Do task indicated by player
                switch (taskNum) {
                    // Display Farm Lot
                    case 1 -> board.printBoard();
                    // Display Tile
                    case 2 -> {
                        board.printBoard();
                        System.out.println();

                        // Prompt to input tile number to access.
                        do {
                            System.out.print("Select a Tile to view its information: ");
                            choice = input.nextLine().trim();

                            // Input Validation
                            try{
                                tileNum = Integer.parseInt(choice);
                            }
                            catch(Exception e){
                                System.out.println("Invalid input.");
                                tileNum = 0;
                            }
                        } while (tileNum < 1 || tileNum > 50);

                        board.printTile(tileNum); // Call method to access Tile information
                    }
                    // Prepare Land
                    case 3 -> {
                        // Wallet content UI & Tools UI Prompt
                        board.printBoard();
                        System.out.println("\nYou have " + farmer.getMoney().getObjectCoins() + " OC in your wallet.\n\nHere's your tools:");
                        farmer.getInventory().displayInventory(false);

                        // Get user input
                        farmer.selectInventoryItem(false);
                    }
                    // Plant Seeds
                    case 4 -> {
                        if (farmer.getInventory().getSeeds().size() == 0 && vacancy == 50)
                            System.out.println("Buy some seeds first to access this task option!");
                        else {
                            board.printBoard();
                            // Wallet content UI & Tools UI Prompt
                            System.out.println("\nYou have " + farmer.getMoney().getObjectCoins() + " OC in your wallet.\n\nHere's your tools & inventory:");
                            farmer.getInventory().displayInventory(true);

                            // Get user input
                            farmer.selectInventoryItem(true);
                        }
                    }
                    // Harvest Crops
                    case 5 -> {
                        board.printBoard();
                        farmer.harvestCrop();
                    }
                    // Buy Seeds
                    case 6 -> {
                        store.display(nickname); // Display store content
                        System.out.println("\nYou have " + farmer.getMoney().getObjectCoins() + " OC in your wallet.\n"); // Display Wallet content

                        // Prompt to buy anything
                        do {
                            System.out.print("Would you like to buy anything [Y/N]?: ");
                            choice = input.nextLine().toUpperCase().trim();
                        } while (!choice.equals("Y") && !choice.equals("N"));

                        switch (choice) {
                            case "Y":
                                farmer.buySeed(store, farmer.getFarmerDetails());
                                break;
                            case "N":
                                break;
                        }
                    }
                    // Buy Fertilizer
                    case 7 -> farmer.buyFertilizer(store);
                    // View Farm Statistics
                    case 8 -> {
                        farmer.getStats().displayStats(farmer);
                        System.out.println("\nKeep the grind going, " + farmer.getFarmerDetails().getNickname() + "!");
                    }
                    // View Inventory
                    case 9 -> farmer.getInventory().displayInventory(true);
                    // Register Farmer Type - TODO for MCO2
                    case 0 -> {
                        System.out.println("\nREGISTER FARMER TYPE");
                        System.out.printf("| ID | %20S | %5S | %16S |\n", "Farmer Type", "Level", "Registration Fee");
                        System.out.printf("| 01 | %20S | %5S | %16S |\n", "Registered Farmer", "5", "200");
                        System.out.printf("| 02 | %20S | %5S | %16S |\n", "Distinguished Farmer", "10", "300");
                        System.out.printf("| 03 | %20S | %5S | %16S |\n", "Legendary Farmer", "15", "400");

                        do {
                            System.out.print("Would you like to register? [Y/N]: ");
                            choice = input.nextLine().toUpperCase().trim();
                        } while (!choice.equals("Y") && !choice.equals("N"));

                        if(choice.equals("Y")){
                            do {
                                System.out.print("Enter ID: ");
                                choice = input.nextLine();
                            } while(Integer.parseInt(choice) < 1 || Integer.parseInt(choice) > 3);

                            switch (Integer.parseInt(choice)) {
                                case 1 -> {
                                    if(farmer.getFarmerDetails().getLevel() >= 5) {
                                        if(farmer.getMoney().getObjectCoins() >= 200) {
                                            farmer.getFarmerDetails().registerFarmerType(FarmerType.Registered_Farmer);
                                            farmer.getFarmerDetails().getFarmerBenefits().setEarningBonus(1);
                                            farmer.getFarmerDetails().getFarmerBenefits().setBuyingBonus(-1);
                                            farmer.getFarmerDetails().getFarmerBenefits().setWaterBonusLimit(0);
                                            farmer.getFarmerDetails().getFarmerBenefits().setFertilizerBonusLimit(0);

                                            store.bonusIncrease(farmer.getFarmerDetails().getFarmerBenefits().getWaterBonusLimit(), farmer.getFarmerDetails().getFarmerBenefits().getFertilizerBonusLimit());

                                            farmer.getMoney().spendObjectCoins(200);
                                            System.out.println("\n200 OC has been charged from your wallet.");
                                            System.out.println("Remaining balance: " + farmer.getMoney().getObjectCoins());
                                        }
                                        else{
                                            System.out.println("You do not have enough Object Coins in your wallet!");
                                        }
                                    }
                                    else{
                                        System.out.println("You haven't reached the required level to be a Registered Farmer yet!");
                                    }
                                }
                                case 2 -> {
                                    if(farmer.getFarmerDetails().getLevel() >= 10) {
                                        if(farmer.getMoney().getObjectCoins() >= 300) {
                                            farmer.getFarmerDetails().registerFarmerType(FarmerType.Distinguished_Farmer);
                                            farmer.getFarmerDetails().getFarmerBenefits().setEarningBonus(2);
                                            farmer.getFarmerDetails().getFarmerBenefits().setBuyingBonus(-2);
                                            farmer.getFarmerDetails().getFarmerBenefits().setWaterBonusLimit(1);
                                            farmer.getFarmerDetails().getFarmerBenefits().setFertilizerBonusLimit(0);

                                            store.bonusIncrease(farmer.getFarmerDetails().getFarmerBenefits().getWaterBonusLimit(), farmer.getFarmerDetails().getFarmerBenefits().getFertilizerBonusLimit());

                                            farmer.getMoney().spendObjectCoins(300);
                                            System.out.println("\n300 OC has been charged from your wallet.");
                                            System.out.println("Remaining balance: " + farmer.getMoney().getObjectCoins());
                                        }
                                        else{
                                            System.out.println("You do not have enough Object Coins in your wallet!");
                                        }
                                    }
                                    else{
                                        System.out.println("You haven't reached the required level to be a Distinguished Farmer yet!");
                                    }
                                }
                                case 3 -> {
                                    if(farmer.getFarmerDetails().getLevel() >= 15) {
                                        if(farmer.getMoney().getObjectCoins() >= 400) {
                                            farmer.getFarmerDetails().registerFarmerType(FarmerType.Legendary_Farmer);
                                            farmer.getFarmerDetails().getFarmerBenefits().setEarningBonus(4);
                                            farmer.getFarmerDetails().getFarmerBenefits().setBuyingBonus(-3);
                                            farmer.getFarmerDetails().getFarmerBenefits().setWaterBonusLimit(2);
                                            farmer.getFarmerDetails().getFarmerBenefits().setFertilizerBonusLimit(1);

                                            store.bonusIncrease(farmer.getFarmerDetails().getFarmerBenefits().getWaterBonusLimit(), farmer.getFarmerDetails().getFarmerBenefits().getFertilizerBonusLimit());

                                            farmer.getMoney().spendObjectCoins(400);
                                            System.out.println("\n400 OC has been charged from your wallet.");
                                            System.out.println("Remaining balance: " + farmer.getMoney().getObjectCoins());
                                        }
                                        else{
                                            System.out.println("You do not have enough Object Coins in your wallet!");
                                        }
                                    }
                                    else{
                                        System.out.println("You haven't reached the required level to be a Legendary Farmer yet!");
                                    }
                                }
                            }
                        }
                    }
                    // Advance to next Day
                    case 11 ->  {
                        System.out.println("\nDAY " + day + " has ended!");
                        day++;

                        // Update Harvest status
                        for(Tile[] tile: Board.getTile()){
                            for(Tile t: tile){
                                t.getPlant().isToHarvest(t, farmer.getStats());

                                // If plant just withered, display Prompt
                                if(t.getTileType()==TileType.WITHERED){
                                    if(day-1==t.getPlant().getDayToHarvest())
                                        System.out.println("The plant in Tile " + t.getTileNum() + " just withered...");
                                }
                            }
                        }
                    }
                }

                System.out.print("\nPress ENTER to continue... ");
                try { input.nextLine(); }
                catch(Exception ignored){}
            }

            // Game Over UI
            System.out.println("\nGAME OVER!");
            farmer.getStats().displayStats(farmer);

            System.out.println();

            // Prompt to Start New Game
            do{
                System.out.print("Would you like to start a new game [Y/N]?");
                choice = input.nextLine().toUpperCase().trim();
            } while(!choice.equals("Y") && !choice.equals("N"));

            // Reset values of variables and objects if new game will start
            if(choice.equals("Y")){
                newGame = true;
                isRunning = true;
                firstRun = true;
                vacancy = 50;
                day = 1;
                board = new Board();
                farmer = new Farmer();

                System.out.println();
            }
            else {
                newGame = false;
                input.close();
            }
        } while (newGame);

        // Outro
        System.out.println("\nThanks for playing THREEVILLE FARM!\nDeveloped by Anne Sulit & Ysobella Torio\n\nClosing program...");
    }
}