package model;

public class Tool {

    protected final String NAME;

    protected final double COST;

    protected final double XP;

    public Tool(String name, double cost, double xp){
        NAME = name;
        COST = cost;
        XP = xp;
    }

    public String getNAME() {
        return NAME;
    }

    public double getCOST() {
        return COST;
    }

    public double getXP() {
        return XP;
    }
}
