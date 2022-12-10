import model.*;
import model.tools.Plow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Scanner;

/**
 * The GameController class is responsible for executing the gameplay. It
 * implements listeners for the GUI and makes the corresponding changes on
 * both the model and the GUI depending on how the players interact with
 * the GUI. It follows the MVC architecture.
 *
 * @author Anne Gabrielle Sulit
 * @author Ysobella Torio
 */
public class ThreeVilleController {
    private static ThreeVilleGUI gui;
    private static ThreeVilleModel model;
    private static boolean isToolSelected;
    private static String toolSelected;
    private static String seedToPlant;

    public ThreeVilleController(ThreeVilleModel model, ThreeVilleGUI gui) {
        this.gui = gui;
        this.model = model;
        isToolSelected = false;
        toolSelected = "";

        setGameVariables();
        gui.setListeners(new StartListener(), new ModeListener(), new PopupListener(), new InventoryListener(), new BoardListener(), new SeedListener());
    }

    public static void setGameVariables(){
        gui.getFarmerName().setText("Farmer Name: Hakdog");
        gui.getFarmerType().setText("Farmer Type: " + String.valueOf(model.getFarmer().getFarmerDetails().getFarmerType()));
        gui.getFarmerXP().setText("Farmer XP: " + String.valueOf(model.getFarmer().getFarmerDetails().getPlayerXP()));
        gui.getObjectCoins().setText("OC"+ String.valueOf(model.getFarmer().getMoney().getObjectCoins()));
    }

    public void getSeed(String str){
        gui.getPromptHolder().setText(str);
    }
    private class SeedListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            getSeed(e.getActionCommand());

            String string = e.getActionCommand();

            if(e.getActionCommand().equals("Turnip")){
                gui.getPromptHolder().setText("\nYou will plant a turnip. Select a tile...\n");
            }
        }
    }
    private class InventoryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 0 - Plow, 1 - Pickaxe, 2 - Shovel, 3 - Watering Can , 4- Fertilizer, 5 - Seeds
            if (e.getSource()==gui.getPlow()){
                gui.getPromptHolder().setText("\nYou equipped a plow. Select a Tile... \n");
                toolSelected = "Plow";
            } else if (e.getSource()==gui.getPickaxe()){
                gui.getPromptHolder().setText("You equipped a pickaxe. Select a Tile... \n");
                toolSelected = "Pickaxe";
            } else if (e.getSource()==gui.getShovel()){
                gui.getPromptHolder().setText("You equipped a shovel. Select a Tile... \n");
                toolSelected = "Shovel";
            } else if (e.getSource()==gui.getWateringCan()){
                gui.getPromptHolder().setText("You equipped a watering can. Select a Tile... \n");
                toolSelected = "Watering Can";
            } else if (e.getSource()==gui.getFertilizer()){
                gui.getPromptHolder().setText("You equipped a fertilizer. Select a Tile... \n");
                toolSelected = "Fertilizer";
            }

            isToolSelected = true;
        }
    }

    private static class BoardListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            int tileNum = Integer.parseInt(e.getActionCommand());
            Tile tile = model.getTile(tileNum);

            if(!isToolSelected){
                // Print tileInfo
                String textToAdd = "";
                for(Tile[] atile: Board.getTile()){
                    for(Tile t: atile){
                        if(t.getTileNum() == tileNum){
                            textToAdd = "Tile "+tileNum+": " + t.getTileType();
                            break;
                        }
                    }
                }
                //model.getTile(Integer.parseInt(e.getActionCommand()));
                gui.getTileInfo().setText(textToAdd);
            }
            else {

                switch (toolSelected) {
                    case "Plow" -> {
                        // Check if Tile is unplowed
                        if(tile.getTileType() == TileType.UNPLOWED){
                            gui.getPromptHolder().setText("You plowed tile " + tileNum);
                            model.getFarmer().plow(tileNum);
                        } else if(tile.getTileType() == TileType.WITHERED){
                            if(model.getFarmer().getMoney().getObjectCoins() < model.getToolCost("PLow"))
                                gui.getPromptHolder().setText("You don't have enough ObjectCoins to plow a withered crop!");
                            else
                                gui.getPromptHolder().setText("Removed withered plant from Tile " + tileNum);
                        } else
                            gui.getPromptHolder().setText("You cannot plow this tile!");
                    }
                    case "Pickaxe" -> {
                        if(tile.getTileType() == TileType.HAS_ROCK) {
                            if(model.getFarmer().getMoney().getObjectCoins() < model.getToolCost("Pickaxe"))
                                gui.getPromptHolder().setText("You don't have enough ObjectCoins to mine this tile!");
                            else{
                                gui.getPromptHolder().setText("You mined tile " + e.getActionCommand());
                                model.getFarmer().mine(Integer.parseInt(e.getActionCommand()));
                            }
                        } else
                            gui.getPromptHolder().setText("There are no rocks to mine!");
                    }
                    case "Shovel" -> {
                        if(tile.getTileType()!= TileType.HAS_ROCK){
                            if(model.getFarmer().getMoney().getObjectCoins() < model.getToolCost("Shovel"))
                                gui.getPromptHolder().setText("You don't have enough ObjectCoins to dig this tile!");
                            else{
                                gui.getPromptHolder().setText("You dug tile " + e.getActionCommand());
                                model.getFarmer().dig(Integer.parseInt(e.getActionCommand()));
                            }
                        } else
                            gui.getPromptHolder().setText("You can't dig a rock! Use a pickaxe next time...");
                    }
                    case "Watering Can" -> {
                        if(tile.getTileType() == TileType.HAS_PLANT){
                            // Allow tile to be watered if current number of times watered is less than the limit
                            if(tile.getPlant().getTimesWatered()<tile.getPlant().getSeedDetails().getWaterNeeded()+tile.getPlant().getSeedDetails().getWaterBonus())
                                model.getFarmer().water(tileNum);
                            else{
                                model.getFarmer().water(tileNum);
                                gui.getPromptHolder().setText("You watered tile " + tileNum);
                            }
                        }
                        else if(tile.getTileType() == TileType.TO_HARVEST)
                            gui.getPromptHolder().setText("This tile is about to be harvested!");
                        else if(tile.getTileType() == TileType.WITHERED)
                            gui.getPromptHolder().setText("You cannot water a withered plant...");
                        else
                            gui.getPromptHolder().setText("Tile has no seed planted. It cannot be watered!");
                    }
                    case "Fertilizer" -> {
                        if(tile.getTileType()== TileType.HAS_PLANT){
                            // If times fertilized is less than max with bonus limit
                            if(tile.getPlant().getTimesFertilized()<tile.getPlant().getSeedDetails().getFertilizerNeeded()+tile.getPlant().getSeedDetails().getFertilizerBonus()){
                                gui.getPromptHolder().setText("You fertilized tile " + tileNum);
                                tile.getPlant().setTimesFertilized(tile.getPlant().getTimesFertilized()+1);
                            } else
                                gui.getPromptHolder().setText("This tile has already reached the fertilizer limit!");
                        } else if(tile.getTileType() == TileType.TO_HARVEST)
                            gui.getPromptHolder().setText("This tile is about to be harvested!");
                        else if(tile.getTileType() == TileType.WITHERED)
                            gui.getPromptHolder().setText("You cannot fertilize a withered plant...");
                        else
                            gui.getPromptHolder().setText("Tile has no seed planted. It cannot be fertilized!");
                    }
                    case "Seeds" -> {

                    }
                }

                setGameVariables();
                gui.updateTileStatus(Integer.parseInt(e.getActionCommand()), model.getTile(Integer.parseInt(e.getActionCommand())).getTileType());
                isToolSelected = false;
            }
        }
    }

    private class StartListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //do nothing
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource()==gui.getBtnPlay()) {
                gui.getBtnPlay().setContentAreaFilled(false);
                gui.getBtnPlay().setIcon(new ImageIcon("assets/img/selected-play.png"));
            } else if(e.getSource()==gui.getBtnExit()) {
                gui.getBtnExit().setContentAreaFilled(false);
                gui.getBtnExit().setIcon(new ImageIcon("assets/img/selected-exit.png"));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //do nothing
            if(e.getSource()==gui.getBtnPlay()) {
                gui.getBtnPlay().setContentAreaFilled(false);
                gui.getBtnPlay().setIcon(new ImageIcon("assets/img/play.png"));

                gui.setGameModeScreen();

            } else if(e.getSource()==gui.getBtnExit()) {
                gui.getBtnExit().setContentAreaFilled(false);
                gui.getBtnExit().setIcon(new ImageIcon("assets/img/exit.png"));

                System.exit(0);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource()==gui.getBtnPlay()) {
                gui.getBtnPlay().setContentAreaFilled(false);
                gui.getBtnPlay().setIcon(new ImageIcon("assets/img/selected-play.png"));
            } else if(e.getSource()==gui.getBtnExit()) {
                gui.getBtnExit().setContentAreaFilled(false);
                gui.getBtnExit().setIcon(new ImageIcon("assets/img/selected-exit.png"));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource()==gui.getBtnPlay()) {
                gui.getBtnPlay().setContentAreaFilled(false);
                gui.getBtnPlay().setIcon(new ImageIcon("assets/img/play.png"));
            } else if(e.getSource()==gui.getBtnExit()) {
                gui.getBtnExit().setContentAreaFilled(false);
                gui.getBtnExit().setIcon(new ImageIcon("assets/img/exit.png"));
            }
        }
    }

    private class ModeListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            //do nothing
            if(e.getSource()==gui.getBtnEasy()) {
            } else if(e.getSource()==gui.getBtnHard()) {
                gui.setFileNameScreen();
                if(e.getSource()==gui.getBtnGenerate()){
                    JFileChooser fileChooser = new JFileChooser();
                    int response = fileChooser.showSaveDialog(null); // select file to save

                    if(response == JFileChooser.APPROVE_OPTION){
                        File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        System.out.println(file);
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource()==gui.getBtnEasy()) {
                gui.getBtnEasy().setContentAreaFilled(false);
                gui.getBtnEasy().setIcon(new ImageIcon("assets/img/selected-easy.png"));
            } else if(e.getSource()==gui.getBtnHard()) {
                gui.getBtnHard().setContentAreaFilled(false);
                gui.getBtnHard().setIcon(new ImageIcon("assets/img/selected-hard.png"));
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //do nothing
            if(e.getSource()==gui.getBtnEasy()) {
                gui.getBtnEasy().setContentAreaFilled(false);
                gui.getBtnEasy().setIcon(new ImageIcon("assets/img/easy.png"));

                gui.repaint();
               // gui.setNicknamePrompt();

                gui.setMainScreen();

            } else if(e.getSource()==gui.getBtnHard()) {
                gui.getBtnHard().setContentAreaFilled(false);
                gui.getBtnHard().setIcon(new ImageIcon("assets/img/hard.png"));

                gui.setFileNameScreen();
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource()==gui.getBtnEasy()) {
                gui.getBtnEasy().setContentAreaFilled(false);//
                gui.getBtnEasy().setIcon(new ImageIcon("assets/img/selected-easy.png"));
            } else if(e.getSource()==gui.getBtnHard()) {
                gui.getBtnHard().setContentAreaFilled(false);//
                gui.getBtnHard().setIcon(new ImageIcon("assets/img/selected-hard.png"));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(e.getSource()==gui.getBtnEasy()) {
                gui.getBtnEasy().setIcon(new ImageIcon("assets/img/easy.png"));
            } else if(e.getSource()==gui.getBtnHard()) {
                gui.getBtnHard().setContentAreaFilled(false);
                gui.getBtnHard().setIcon(new ImageIcon("assets/img/hard.png"));
            }
        }
    }

    private class PopupListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==gui.getBtnGenerate()){
                String filename = gui.getTxtField().getText();

                try{
                    File file = new File(filename);
                    Scanner scanner = new Scanner(file);

                    // Initialize model.Board with rocks
                    for(int row = 0; row < 10; row++){
                        for(int col = 0; col < 5; col++){
                            System.out.println(scanner.nextLine());
                            if (scanner.nextLine().equals("1")) {
                                model.getBoard().getTile()[row][col].setTileType(TileType.HAS_ROCK);
                                model.getBoard().getTile()[row][col].setOccupied(true);
                            }
                        }
                    }
                } catch (Exception f){
                    f.getStackTrace();
                }

                gui.setMainScreen();

            } else if(e.getSource() == gui.getBtnSetName()){
                model.getFarmer().getFarmerDetails().setNickname(gui.getTxtNickname().getText());
                System.out.println(model.getFarmer().getFarmerDetails().getNickname());

               // gui.getPopupNickname().dispose();
                gui.getFrame();
                gui.setMainScreen();
            }
        }
    }

    private class StoreListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            //do nothing
        }

        @Override
        public void mousePressed(MouseEvent e) {
//            if(e.getSource()==gui.getBtnEasy()) {
//                gui.getBtnEasy().setContentAreaFilled(false);
//                gui.getBtnEasy().setIcon(new ImageIcon("assets/img/selected-easy.png"));
//            } else if(e.getSource()==gui.getBtnHard()) {
//                gui.getBtnHard().setContentAreaFilled(false);
//                gui.getBtnHard().setIcon(new ImageIcon("assets/img/selected-hard.png"));
//            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
//            //do nothing
//            if(e.getSource()==gui.getBtnEasy()) {
//                gui.getBtnEasy().setContentAreaFilled(false);
//                gui.getBtnEasy().setIcon(new ImageIcon("assets/img/easy.png"));
//
//                gui.repaint();
//                gui.setNicknamePrompt();
//
//            } else if(e.getSource()==gui.getBtnHard()) {
//                gui.getBtnHard().setContentAreaFilled(false);
//                gui.getBtnHard().setIcon(new ImageIcon("assets/img/hard.png"));
//
//                gui.setFileNameScreen();
//            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            if(e.getSource()==gui.getBtnEasy()) {
//                gui.getBtnEasy().setContentAreaFilled(false);//
//                gui.getBtnEasy().setIcon(new ImageIcon("assets/img/selected-easy.png"));
//            } else if(e.getSource()==gui.getBtnHard()) {
//                gui.getBtnHard().setContentAreaFilled(false);//
//                gui.getBtnHard().setIcon(new ImageIcon("assets/img/selected-hard.png"));
//            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            if(e.getSource()==gui.getBtnEasy()) {
//                gui.getBtnEasy().setIcon(new ImageIcon("assets/img/easy.png"));
//            } else if(e.getSource()==gui.getBtnHard()) {
//                gui.getBtnHard().setContentAreaFilled(false);
//                gui.getBtnHard().setIcon(new ImageIcon("assets/img/hard.png"));
//            }
        }
    }
}
