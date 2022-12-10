package model.tools;

import model.*;

public class Shovel extends Tool {
    public Shovel(String name, double cost, double xp) {
        super(name, cost, xp);
    }

    /**
     * Represents digging a tile.
     *
     * @param tileNum is the tile to be plowed.
     * @param money is the wallet of the farmer.
     * @param farmerDetails is the profile of the farmer.
     */
    public void digTile(int tileNum, Money money, FarmerDetails farmerDetails){
        System.out.println();

        // Find Tile
        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){

                if(t.getTileNum()==tileNum){
                    if(t.getTileType()!= TileType.HAS_ROCK) {
                        t.setTileType(TileType.UNPLOWED);
                        // spend ObjectCoins
                        money.spendObjectCoins(COST);
                        // add Farmer XP
                        farmerDetails.addXP(XP);

                        if (t.isOccupied()) {
                            t.setOccupied(false);
                            //MyFarm.increaseVacancy();
                        }
                        break;
                    } else{
                        money.spendObjectCoins(COST);
                    }
                }
                }
            }
        }
    }

