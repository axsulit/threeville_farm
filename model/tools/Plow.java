package model.tools;

import model.*;

public class Plow extends Tool {

    public Plow(String name, double cost, double xp) {
        super(name, cost, xp);
    }

    /**
     * Represents plowing a tile.
     *
     * @param tileNum is the tile to be plowed.
     * @param money is the wallet of the farmer.
     * @param farmerDetails is the profile of the farmer.
     * @param stats is the farmer's and farm's statistics.
     */
    public void plowTile(int tileNum, Money money, FarmerDetails farmerDetails, Stats stats) {
        System.out.println();

        for (Tile[] tile : Board.getTile()) {
            for (Tile t : tile) {
                if (t.getTileNum() == tileNum) {
                    if (t.getTileType() == TileType.UNPLOWED) {
                        t.setTileType(TileType.PLOWED);
                        stats.addTimesPlowed();
                    } else if (t.getTileType() == TileType.WITHERED) {
                        t.setOccupied(false);
                        t.setTileType(TileType.UNPLOWED);
                        money.spendObjectCoins(COST);
                    }

                    farmerDetails.addXP(XP);
                    break;
                }
            }
        }
    }
}
