package model;

import java.io.File;
import java.util.Scanner;

/**
 * The model.Board class represents the Farm Lot as it consists of tiles from the Tiles class. Its dimension is 10 x 5, which means there are fifty tiles in one board.
 *
 * @author Ysobella Torio, Anne Sulit
 */
public class Board {
    /**
     * Contains the fixed length of the board.
     */
    private static final int LENGTH = 10;
    /**
     * Contains the fixed width of the board.
     */
    private static final int WIDTH = 5;
    /**
     * Contains all the tiles of the model.Tile class in the model.Board farm lot.
     */
    private static Tile[][] tile = new Tile[LENGTH][WIDTH];

    /**
     * Instantiates all the elements of model.Tile[][] and sets the tile number(tileNum) of all tiles.
     */
    public Board(){
        int tileNum = 1;
        for(int row_ctr = 0; row_ctr < LENGTH; row_ctr++){
            for(int col_ctr = 0; col_ctr < WIDTH; col_ctr++){
                tile[row_ctr][col_ctr] = new Tile();
                tile[row_ctr][col_ctr].setTileNum(tileNum++);
            }
        }
    }


    /**
     * Gets an input file of filename specified by the user to generate rocks in the farm lot.
     *
     * @param fileName is the file name of farm lot generated to read.
     */
    public void readFileInput(String fileName){
        int row, // model.Board Row
            col; // model.Board Col

        // Prompt to get file name
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            System.out.println("Generating rocks...");

            // Initialize model.Board with rocks
            for(row = 0; row < LENGTH; row++){
                for(col = 0; col < WIDTH; col++){
                    if (scanner.nextLine().equals("1")) {
                        tile[row][col].setTileType(TileType.HAS_ROCK);
                        tile[row][col].setOccupied(true);
                    }
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            // Get new file name
            Scanner sc = new Scanner(System.in);
            System.out.println("File does not exist! Please try again.");
            System.out.print("Enter file name: ");
            String name = sc.nextLine();
            e.getStackTrace();
            readFileInput(name);
        }
    }

    /**
     * Gets the value of model.Tile[][].
     *
     * @return the 10x5 board <code>tile</code>.
     */
    public static Tile[][] getTile() {
        return tile;
    }
}
