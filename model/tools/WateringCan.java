package model.tools;

import model.*;

public class WateringCan extends Tool {
    public WateringCan(String name, double cost, double xp) {
        super(name, cost, xp);
    }

    /**
     * Represents watering a plant.
     *
     * @param tileNum is the tile number to be watered.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    public void waterPlant(int tileNum, FarmerDetails farmerDetails, Stats stats){
        System.out.println();

        // Find Tile
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){
                if(t.getTileNum()==tileNum){
                    if(t.getTileType() == TileType.HAS_PLANT){
                        // Allow tile to be watered if current number of times watered is less than the limit
                        if(t.getPlant().getTimesWatered()<t.getPlant().getSeedDetails().getWaterNeeded()+t.getPlant().getSeedDetails().getWaterBonus()){
                            t.getPlant().setTimesWatered(t.getPlant().getTimesWatered()+1);
                            stats.addTimesWatered();

                            // add Farmer XP
                            farmerDetails.addXP(XP);
                        }
                    }

                    break;
                }
            }
        }
    }
}
