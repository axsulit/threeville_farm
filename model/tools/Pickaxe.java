package model.tools;

import model.*;

public class Pickaxe extends Tool {
    public Pickaxe(String name, double cost, double xp) {
        super(name, cost, xp);
    }

    /**
     * Represents mining a rock.
     *
     * @param tileNum is the tile to be plowed.
     * @param money is the wallet of the farmer.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    public void removeRocks(int tileNum, Money money, FarmerDetails farmerDetails, Stats stats){
        System.out.println();

        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){
                if(t.getTileNum()==tileNum){
                    t.setTileType(TileType.UNPLOWED);
                    t.setOccupied(false);
                    // spend ObjectCoins
                    money.spendObjectCoins(COST);
                    stats.addRocksMined();
                    // add Farmer XP
                    farmerDetails.addXP(XP);
                }
                else
                    System.out.println("There are no rocks to mine!");
                break;
                }
            }
        }
    }


    // mhieee hello need ayusin yung pagread ng file for rocks :(
