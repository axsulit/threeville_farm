package model;

/**
 * The Money class represents the wallet of the Farmer.
 */
public class Money {
    /**
     * The number of in-game currency or coins that the farmer has in their wallet at the moment.
     */
    private double objectCoins;

    /**
     * Initializes the number of objectCoins to 100.
     */
    public Money(){
        this.objectCoins = 100.0;
    }

    /**
     * The getter method of objectCoins attribute.
     *
     * @return the number of Objectcoins that the farmer has.
     */
    public double getObjectCoins() {
        return objectCoins;
    }

    /**
     * Adds to the total number of Objectcoins the farmer already has.
     *
     * @param objectCoins is the number of Objectcoins the farmer has earned.
     */
    public void addObjectCoins(double objectCoins) {
        this.objectCoins += objectCoins;

        System.out.printf("New Balance: %.1f OC\n", + this.objectCoins);
    }

    /**
     * Subtracts the total number of Objectcoins the farmer already has.
     *
     * @param objectCoins is the number of Objectcoins the farmer has spent.
     */
    public void spendObjectCoins(double objectCoins) {
        this.objectCoins -= objectCoins;

        System.out.printf("New Balance: %.1f OC\n", + this.objectCoins);
    }
}
