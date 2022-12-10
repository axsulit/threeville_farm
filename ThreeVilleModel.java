import model.Board;
import model.Farmer;
import model.Tile;
import model.Tool;
import model.tools.Plow;

public class ThreeVilleModel {
    private Farmer farmer;
    private Board board;
    private Tool tool;

    public ThreeVilleModel(){
        farmer = new Farmer();
        board = new Board();
    }

    public Farmer getFarmer() {
        return farmer;
    }

    public Board getBoard() {
        return board;
    }

    public Tile getTile(int tileNum){
        Tile lot = new Tile();

        for(Tile[] tile: Board.getTile()){
            for(Tile t: tile){
                if(t.getTileNum() == tileNum){
                    lot = t;
                    break;
                }
            }
        }
        return lot;
    }

    public double getToolCost(String tool){
        double cost = 0.0;

        switch(tool){
            case "Plow" -> cost = 2.0;
            case "Pickaxe" -> cost = 50.0;
            case "Shovel" -> cost = 7.0;
            case "Watering Can" -> cost = 0.0;
        }

        return cost;
    }
}
