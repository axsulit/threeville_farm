package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Store class represents the place where the farmer can buy seeds and fertilizers.
 */
public class Store {
    /**
     * Contains the list of seeds that the farmer can buy.
     */
    private final ArrayList<Seed> SEEDS = new ArrayList<>();
    /**
     * Used to get input from the user.
     */
    private Scanner input = new Scanner(System.in);

    /**
     * Initializes the list of Seeds to add in the seeds attribute.
     */
    public Store(){
        //Veggies
        SEEDS.add(new Seed("Turnip", CropType.VEGGIE, true, 2, 1, 2, 0, 1, 5.0, 1, 2, 5.0, 6.0 ));
        SEEDS.add(new Seed("Carrot", CropType.VEGGIE, true,3, 1, 2, 0, 1, 7.5, 1, 2, 10.0, 9.0 ));
        SEEDS.add(new Seed("Potato", CropType.VEGGIE, true, 5, 3, 4, 1, 2, 12.5, 1, 10, 20.0, 3.0 ));
        //Flowers
        SEEDS.add(new Seed("Rose", CropType.FLOWER, true,1, 1, 2, 0, 1, 2.5, 1, 1, 5.0, 5.0 ));
        SEEDS.add(new Seed("Tulip", CropType.FLOWER, true,2, 2, 3, 0, 1, 5.0, 1, 1, 10.0, 9.0 ));
        SEEDS.add(new Seed("Sunflower", CropType.FLOWER, true, 3, 2, 3, 1, 2, 7.5, 1, 1, 20.0, 19.0 ));
        //Fruit Trees
        SEEDS.add(new Seed("Mango", CropType.FRUIT_TREE, true, 10, 7, 7, 4, 4, 25.0, 5, 15, 100.0, 8.0 ));
        SEEDS.add(new Seed("Apple", CropType.FRUIT_TREE, true, 10, 7, 7, 5, 5, 25.0, 10, 15, 200.0, 5.0));
    }

    /**
     * Displays the list of available seeds that the farmer can buy.
     *
     * @param nickname is the nickname of the farmer.
     */
    public void display(String nickname){
        int ctr = 1; // ID Number of Seed
        // Greeting
        System.out.print("\nWelcome to the PROG3 Farm Store, " + nickname+ "! \nWe sell all sorts of seeds that you can grow in your farm. You can see our inventory below: \n");

        System.out.println();

        // Heading
        System.out.printf("| %-3S | %-15S | %-15S | %-18S | %-20S | %-25S | %-10S | %-20S | %-20S | %-15S | %-15S |\n", "ID", "Seed Name", "Seed Type", "Harvest Time(days)","Water Needed (Bonus)", "Fertilizer Needed (Bonus)", "XP Yield", "No. of Produce (Min)", "No. of Produce (Max)", "Seed Cost", "Selling Price");

        // Content of table
        for(Seed c: SEEDS){
            if(c.isCropAvailable())
                System.out.printf("| %-3S | %-15s | %-15s | %-18S | %S (%S%-16S | %S (%S%-21S | %-10S | %-20S | %-20S | %-15S | %-15S |\n", ctr++, c.getName(), c.getCropType(), c.getHarvestTime(),c.getWaterNeeded(), c.getWaterBonus(), ")", c.getFertilizerNeeded(), c.getFertilizerBonus(), ")", c.getXpYield(), c.getExpectedMinProduce(), c.getExpectedMaxProduce(), c.getSeedCost(), c.getBaseSPPerProduce());
        }
    }

    /**
     * This method drives the operations needed in order for the Store class to sell seeds to the farmer.
     *
     * @param money is the wallet of the farmer.
     * @param seeds is the list of seeds the farmer has in their inventory.
     */
    public void sellSeed(Money money, HashMap<String, Integer> seeds, FarmerDetails farmerDetails){
        HashMap<String, Integer> cart = new HashMap<>(); // The list of seeds the farmer wants to buy.
        int seedNum = 0; // The ID Number of the Seed -> repurposed as the number of seeds the farmer wants to buy.
        String cropName = "", // The Name of the Seed
                choice = "Y", // The farmer's choice to keep buying seeds
                seedNumtxt = ""; // Used as initial input for seedNum for input validation
        double cropPrice = 0.0,  // Seed cost
                totalPrice = 0.0; // Total cost

        System.out.println();

        while(choice.equals("Y")){
            // Indicate Seed ID Number
            do{
                System.out.print("Input the ID Number of the Seed you would like to purchase: ");
                seedNumtxt = input.nextLine().trim();

                try{
                    seedNum = Integer.parseInt(seedNumtxt);
                }
                catch(Exception e){
                    System.out.println("Invalid input.");
                    seedNum = 0;
                }
            } while(seedNum < 1 || seedNum > 8);

            // Identify Seed Name - only case 1 will work to cater to updated MP Specs
            switch (seedNum) {
                case 1 -> cropName = "Turnip";
                case 2 -> cropName = "Carrot";
                case 3 -> cropName = "Potato";

                case 4 -> cropName = "Rose";
                case 5 -> cropName = "Tulip";
                case 6 -> cropName = "Sunflower";

                case 7 -> cropName = "Mango";
                case 8 -> cropName = "Apple";
            }

            // User input on how many seeds to buy
            do{
                System.out.print("How many seeds of the crop " + cropName + " would you like to buy?: ");
                seedNumtxt = input.nextLine().trim();

                // Data validation
                try{
                    seedNum = Integer.parseInt(seedNumtxt);
                }
                catch(Exception e){
                    System.out.println("Invalid input.");
                    seedNum = -1;
                }
            } while (seedNum < 0);

            // Check if current seed order already exists
            if(cart.containsKey(cropName)) {
                for (Map.Entry<String, Integer> cartItem : cart.entrySet()) {
                    if (cartItem.getKey().equals(cropName)) {
                        // Update seed number count
                        cartItem.setValue(cartItem.getValue() + seedNum);
                        break;
                    }
                }
            } else
                cart.put(cropName, seedNum);

            // Compute for total Price
            for(Map.Entry<String, Integer> cartItem: cart.entrySet()){
                // Get price of current seed
                for (Seed c : this.SEEDS) {
                    if (c.getName().equals(cartItem.getKey())) {
                        cropPrice = c.getSeedCost();
                        break;
                    }
                }

                    totalPrice += ((cartItem.getValue() * cropPrice) + farmerDetails.getFarmerBenefits().getBuyingBonus());
            }

            // Check if Farmer has enough Objectcoins to buy current order
            if((cropPrice * seedNum) > money.getObjectCoins() || totalPrice > money.getObjectCoins()){
                // Prompt to cancel or restart order
                do{
                    System.out.println("\nSorry, you do not have enough ObjectCoins in your wallet to proceed with this transaction.\n");
                    System.out.print("Would you like to [R]estart this transaction or [C]ancel this transaction? : ");
                    choice = input.nextLine().toUpperCase().trim();
                } while (!choice.equals("C") && !choice.equals("R"));

                if(choice.equals("C"))
                    break;
                else{
                    choice = "Y";
                    cart.remove(cropName);
                    totalPrice = 0.0;
                    System.out.println();
                }
            }
            else {
                System.out.println("\n" + cropName + "\t(" + seedNum + ") " + cropPrice);
                System.out.println("Buying bonus:\t" + farmerDetails.getFarmerBenefits().getBuyingBonus());
                System.out.println("\nYour total is: " + totalPrice + " Objectcoins.");

                // Prompt to buy more seeds
                do{
                    System.out.print("\nWould you like to buy anything else [Y/N]?: ");
                    choice = input.nextLine().toUpperCase().trim();
                } while(!choice.equals("Y") && !choice.equals("N"));

                if(choice.equals("Y"))
                    totalPrice = 0.0;
            }
        }

        // Check if Farmer has enough Objectcoins to buy all seeds
        if(totalPrice <= money.getObjectCoins()){
            System.out.println("\nYou will be charged a total of " + totalPrice + " Objectcoins.");
            money.spendObjectCoins(totalPrice);
            seeds.putAll(cart);
        }
    }

    /**
     * This method drives the operations needed in order for the Store class to sell fertilizers to the farmer.
     *
     * @param money is the wallet of the farmer.
     * @param inventory is the list of seeds the farmer has in their inventory.
     */
    public void sellFertilizer(Money money, Inventory inventory){
        int fertilizerNum; // The number of fertilizers the farmer wants to buy.
        String choice = ""; // The farmer's choice to buy fertilizer.

        System.out.println("\nYou have " + money.getObjectCoins() + " OC in your wallet.\n");

        // Prompt to buy fertilizer
        do{
            System.out.print("1 Fertilizer Unit costs 10 OC. \nWould you like to purchase some [Y/N]? : ");
            choice = input.nextLine().toUpperCase().trim();
        }while(!choice.equals("Y") && !choice.equals("N"));

        System.out.println();

        if(choice.equals("Y")){
            // Prompt to get number of fertilizers to sell
            do{
                System.out.print("How many fertilizers would you like to buy? : ");
                choice = input.nextLine().trim();

                try{
                    fertilizerNum = Integer.parseInt(choice);
                }
                catch(Exception e){
                    System.out.println("Invalid input.");
                    fertilizerNum = 0;
                }
            } while (fertilizerNum < 1);

            // Check if farmer has enough Objectcoins to buy all the fertilizers they want to buy.
            if((fertilizerNum * 10.0) > money.getObjectCoins()){
                System.out.println("Sorry, you do not have enough ObjectCoins in your wallet to proceed with this transaction.");
            }
            else{
                inventory.addFertilizerUnits(fertilizerNum);
                System.out.println("\nYou will be charged a total of " + fertilizerNum * 10 + " Objectcoins.");
                money.spendObjectCoins(fertilizerNum * 10.0);

            }

        } else
            System.out.println("Thanks for stopping by!");
    }


    public void bonusIncrease(int waterBonusIncrease, int fertilizerBonusIncrease){
        for(Seed s: SEEDS){
            s.setWaterBonus(s.getWaterBonus() + waterBonusIncrease);
            s.setFertilizerBonus(s.getFertilizerBonus() + fertilizerBonusIncrease);
        }
    }

    /**
     * The getter method of the seeds attribute.
     * @return the list of seeds the store is selling.
     */
    public ArrayList<Seed> getSEEDS() {
        return SEEDS;
    }
}
