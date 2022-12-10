import model.TileType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JLayeredPane.DEFAULT_LAYER;

public class ThreeVilleGUI extends JFrame {
    private final JButton[] START_BUTTONS; // play & exit
    private final JButton[] GAME_MODES; // easy & hard
    private final JButton SET_NAME_BUTTON; // set name
    private final JButton GENERATE_BUTTON; // generate rocks
    private final JButton[] INVENTORY_SLOTS; // tools, seeds, fertilizer

    private JButton btnShop;
    private final JLayeredPane BASE; // serve as the base of the components that will be shown.
    private final JLayeredPane INVENTORY; // holds the items in the inventory.
    private final Dimension DEF_FRAME_SIZE; //  holds the default frame size of this GameGUI object.
    private final Dimension DEFAULT_SIZE; //  the default size that fills the inside with the frame.
    private final Color TRANSPARENT; // holds a transparent color. Mostly used by the containers in the game.
    private final JPanel[] BACKGROUNDS; // holds the backgrounds for the whole frame at the earlier parts of the game

    private JPanel CENTER_CONTAINER;
    private final JPanel LEFT_CONTAINER;
    private final JPanel RIGHT_CONTAINER;
    private final JPanel LOWER_CONTAINER;
    private final JPanel FARMLOT_CONTAINER;

    private final FarmTile[][] TILES;
    private JTextField txtField;
    private JTextField txtNickname;

    private Font DEFAULT_FONT;

    private JFrame popupNickname = new JFrame();
    private JFrame popupRocks = new JFrame();
    private JFrame store = new JFrame();

    private JLabel tileInfo;
    private JLabel statInfo; // should hold new balance, gained xp, new xp
    private JLabel promptHolder;

    private JLabel farmerName;

    public JLabel getFarmerName() {
        return farmerName;
    }

    public JLabel getFarmerXP() {
        return farmerXP;
    }

    public JLabel getFarmerType() {
        return farmerType;
    }

    public JLabel getObjectCoins() {
        return objectCoins;
    }

    public JLabel getDayNum() {
        return dayNum;
    }

    private JLabel farmerXP;
    private JLabel farmerType;
    private JLabel objectCoins;

    private JLabel dayNum;
    private JButton btnNextDay;

    public JFrame getStoreFrame() {
        return storeFrame;
    }

    private JFrame storeFrame;

    private String seedToPlant;

    private ActionListener boardListener;


    public ThreeVilleGUI(){
        store = new JFrame("Store");
        seedToPlant = "";
        statInfo = new JLabel();
        promptHolder = new JLabel();

        farmerName = new JLabel();
        farmerXP = new JLabel();
        farmerType = new JLabel();
        objectCoins = new JLabel();

        dayNum = new JLabel();
        btnNextDay = new JButton("End Day");



        //Set Default Font
        try{
            DEFAULT_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("assets/04B_03__.TTF")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(DEFAULT_FONT);
        }catch (IOException | FontFormatException ignored){}

        TRANSPARENT = new Color(0, 0, 0, 0); // transparent background

        DEF_FRAME_SIZE = new Dimension(1400, 778); // allowance of dimension for the frame
        DEFAULT_SIZE = new Dimension((int)DEF_FRAME_SIZE.getWidth() - 16, (int)DEF_FRAME_SIZE.getHeight() - 40);

        BASE = new JLayeredPane();  // base for all.set

        BACKGROUNDS = new JPanel[7]; // TODO: home screen, gamemode, nickname, filename, main screen, seed inventory, shop
        BACKGROUNDS[0] = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D bg = (Graphics2D) g;
                bg.drawImage(new ImageIcon("assets/img/home-bg.png").getImage(), 0, 0, DEF_FRAME_SIZE.width, DEF_FRAME_SIZE.height, null);
            }
        };

        BACKGROUNDS[1] = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D bg = (Graphics2D) g;
                bg.drawImage(new ImageIcon("assets/enterNameFrame.png").getImage(), 0, 0, 700, 600, null);
            }
        };

        TILES = new FarmTile[10][5];

        BACKGROUNDS[2] = new JPanel();
        BACKGROUNDS[3] = new JPanel();
        BACKGROUNDS[4] = new JPanel();
        BACKGROUNDS[5] = new JPanel();

        START_BUTTONS = new JButton[2];
        START_BUTTONS[0] = new JButton(); // play
        START_BUTTONS[1] = new JButton(); // exit

        GAME_MODES = new JButton[2];
        GAME_MODES[0] = new JButton(); // Easy
        GAME_MODES[1] = new JButton(); // Hard

        SET_NAME_BUTTON = new JButton("Proceed"); // set name
        GENERATE_BUTTON = new JButton("Generate"); // generate rocks
        txtField = new JTextField();
        txtNickname = new JTextField();


        INVENTORY = new JLayeredPane() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D bg = (Graphics2D) g;
                bg.drawImage(new ImageIcon("assets/img/inventory-panel-bg.png").getImage(), 0, 0, 900,130, null);
            }
        };

        INVENTORY_SLOTS = new JButton[6];

        CENTER_CONTAINER = new JPanel();
        LEFT_CONTAINER = new JPanel();
        RIGHT_CONTAINER = new JPanel();
        LOWER_CONTAINER = new JPanel();
        FARMLOT_CONTAINER = new JPanel();

        popupNickname = new JFrame();
        popupRocks = new JFrame();

        tileInfo = new JLabel();

        setFarmLot();
        setBase();
        setBackgrounds();
        setTransparentContainers();
        setInventory();
        setStartingScreen();
        setFrame();

        // add the base to this frame
        this.add(BASE, BorderLayout.CENTER);

        // add components to the base
    }

    public void setStoreScreen(ActionListener seed){
        //make new jframe
        JPanel panel = new JPanel();
        storeFrame = new JFrame("Store");
        storeFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        storeFrame.setSize(200,300);
        panel.setSize(200,300);
        storeFrame.setResizable(false);
        storeFrame.setLocationRelativeTo(null); // center of screen
        storeFrame.setLayout(null);

        JButton[] seeds = new JButton[8];

        for(int i = 0; i < 8; i++){
            seeds[i] = new JButton();
            seeds[i].setPreferredSize(new Dimension(100,25));
            seeds[i].setFocusable(false);
            seeds[i].setOpaque(false);
            seeds[i].addActionListener(seed);
            panel.add(seeds[i]);
        }


        seeds[0].setText("Turnip");
        seeds[1].setText("Carrot");
        seeds[2].setText("Potato");
        seeds[3].setText("Rose");
        seeds[4].setText("Tulip");
        seeds[5].setText("Sunflower");
        seeds[6].setText("Mango");
        seeds[7].setText("Apple");

        seeds[0].setActionCommand("Turnip");
        seeds[1].setActionCommand("Carrot");
        seeds[2].setActionCommand("Potato");
        seeds[3].setActionCommand("Rose");
        seeds[4].setActionCommand("Tulip");
        seeds[5].setActionCommand("Sunflower");
        seeds[6].setActionCommand("Mango");
        seeds[7].setActionCommand("Apple");

        storeFrame.add(panel);
        storeFrame.setVisible(true);
        //add action listener


        //on button click close jframe

    }

    private void setStartingScreen() {
        setStartButtons();

        BASE.add(START_BUTTONS[0]);
        BASE.add(START_BUTTONS[1]);
        BASE.add(BACKGROUNDS[0], DEFAULT_LAYER);
    }

    private void setStartButtons(){
        //Play
        START_BUTTONS[0].setIcon(new ImageIcon("assets/img/play.png"));
        START_BUTTONS[0].setFocusable(false);
        START_BUTTONS[0].setOpaque(false);
        START_BUTTONS[0].setFont(DEFAULT_FONT);
        START_BUTTONS[0].setBorder(new EmptyBorder(0,0,0,0));
        START_BUTTONS[0].setForeground(TRANSPARENT);
        START_BUTTONS[0].setBackground(TRANSPARENT);

        START_BUTTONS[0].setBounds((DEF_FRAME_SIZE.width/2)-136, (DEF_FRAME_SIZE.height/2)-75, 272, 75);
        START_BUTTONS[0].setVisible(true);

        //Exit
        START_BUTTONS[1].setIcon(new ImageIcon("assets/img/exit.png"));
        START_BUTTONS[1].setFocusable(false);
        START_BUTTONS[1].setOpaque(false);
        START_BUTTONS[1].setFont(DEFAULT_FONT);
        START_BUTTONS[1].setBorder(new EmptyBorder(0,0,0,0));
        START_BUTTONS[1].setForeground(TRANSPARENT);
        START_BUTTONS[1].setBackground(TRANSPARENT);
        START_BUTTONS[1].setBounds((DEF_FRAME_SIZE.width/2)-136, (DEF_FRAME_SIZE.height/2)+50, 272, 75);
        START_BUTTONS[1].setVisible(true);
    }

    public void setGameModeScreen(){
        BASE.removeAll();

        setGameModeButtons();

        JPanel center = new JPanel();
        center.setBorder(new EmptyBorder(200, 0, 600, 0));
        center.setLayout(new FlowLayout());
        center.setLayout(new FlowLayout());
        center.setBackground(new Color(0xc0d470));
        center.setPreferredSize(new Dimension( DEF_FRAME_SIZE.width, DEF_FRAME_SIZE.height));
        center.setBounds(0,0,DEF_FRAME_SIZE.width, DEF_FRAME_SIZE.height);

        center.add(GAME_MODES[0]);

        JLabel prompt = new JLabel(new ImageIcon("assets/chooseGameMode.png"));
        center.add(prompt);
        center.add(GAME_MODES[1]);
        center.setVisible(true);

        BASE.add(center);
    }

    private void setGameModeButtons(){
        //Easy
        GAME_MODES[0].setActionCommand("EASY");
        GAME_MODES[0].setIcon(new ImageIcon("assets/img/easy.png"));
        GAME_MODES[0].setFocusable(false);
        GAME_MODES[0].setOpaque(false);
        GAME_MODES[0].setFont(DEFAULT_FONT);
        GAME_MODES[0].setBackground(TRANSPARENT);
        GAME_MODES[0].setBounds((DEF_FRAME_SIZE.width/2)-200, (DEF_FRAME_SIZE.height/2)-75, 200, 75);
        GAME_MODES[0].setBorder(new EmptyBorder(0, 0, 0, 30));
        GAME_MODES[0].setVisible(true);
        //Hard
        GAME_MODES[1].setActionCommand("HARD");
        GAME_MODES[1].setIcon(new ImageIcon("assets/img/hard.png"));
        GAME_MODES[1].setFocusable(false);
        GAME_MODES[1].setOpaque(false);
        GAME_MODES[1].setFont(DEFAULT_FONT);
        GAME_MODES[1].setBackground(TRANSPARENT);
        GAME_MODES[1].setBounds((DEF_FRAME_SIZE.width/2)-200, (DEF_FRAME_SIZE.height/2)+50, 200, 75);
        GAME_MODES[1].setBorder(new EmptyBorder(0, 30, 0, 0));
        GAME_MODES[1].setVisible(true);
    }

    public JLabel getPromptHolder() {
        return promptHolder;
    }

    public JLabel getTileInfo() {
        return tileInfo;
    }

    public JLabel getStatInfo() {
        return statInfo;
    }

    public void setMainScreen(){
        BASE.removeAll();

        //LEFT_CONTAINER.add(new JPanel());
        LEFT_CONTAINER.setBackground(new Color(0xc1cd7b));
        //LEFT_CONTAINER.setBackground(Color.blue);
        LEFT_CONTAINER.setBounds(0,0,250,250);
        LEFT_CONTAINER.setLayout(new BoxLayout(LEFT_CONTAINER, BoxLayout.Y_AXIS));
        LEFT_CONTAINER.setPreferredSize(new Dimension(440, 500));

        LEFT_CONTAINER.add(farmerName);
        LEFT_CONTAINER.add(farmerXP);
        LEFT_CONTAINER.add(farmerType);
        LEFT_CONTAINER.add(objectCoins);

        LEFT_CONTAINER.add(tileInfo);
        LEFT_CONTAINER.add(promptHolder);
        LEFT_CONTAINER.add(statInfo);

        //LEFT_CONTAINER.add(FARMER_PANEL);

        // Center
        CENTER_CONTAINER.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        CENTER_CONTAINER.setBackground(new Color(0xf2dab9));
        CENTER_CONTAINER.setPreferredSize(new Dimension(680, 795));
        //setFarmLot();
        CENTER_CONTAINER.add(FARMLOT_CONTAINER);

        // Right
        RIGHT_CONTAINER.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        RIGHT_CONTAINER.setBackground(new Color(0xc1cd7b));
        //RIGHT_CONTAINER.setBackground(Color.pink);
        RIGHT_CONTAINER.setPreferredSize(new Dimension(440, 500));
        btnNextDay.setPreferredSize(new Dimension(100, 50));
        btnNextDay.setActionCommand("NextDay");
        RIGHT_CONTAINER.add(dayNum);
        RIGHT_CONTAINER.add(btnNextDay);

        // Bottom
        LOWER_CONTAINER.setBackground(new Color(0xc1cd7b));
        LOWER_CONTAINER.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        LOWER_CONTAINER.setPreferredSize(new Dimension(DEF_FRAME_SIZE.width, 150) );
        INVENTORY.setOpaque(true);
        INVENTORY.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 25));
        INVENTORY.setPreferredSize(new Dimension(900, 130));
        LOWER_CONTAINER.add(INVENTORY);
        LOWER_CONTAINER.add(btnShop);

        BACKGROUNDS[2].add(LEFT_CONTAINER, BorderLayout.WEST);
        BACKGROUNDS[2].add(RIGHT_CONTAINER, BorderLayout.EAST);
        BACKGROUNDS[2].add(CENTER_CONTAINER, BorderLayout.CENTER);
        BACKGROUNDS[2].add(LOWER_CONTAINER, BorderLayout.SOUTH);

        BASE.add(BACKGROUNDS[2], 1);
    }

    private void setFarmLot(){

        FARMLOT_CONTAINER.setLayout(new GridLayout(10,5, 3, 3));
        FARMLOT_CONTAINER.setSize(1000, 600);
        FARMLOT_CONTAINER.setBackground(TRANSPARENT);
        FARMLOT_CONTAINER.setOpaque(false);

        int tileNum = 1;
        for(int row_ctr = 0; row_ctr < 10; row_ctr++){
            for(int col_ctr = 0; col_ctr < 5; col_ctr++){
                TILES[row_ctr][col_ctr] = new FarmTile(tileNum++);

                FARMLOT_CONTAINER.add(TILES[row_ctr][col_ctr]);
            }
        }
    }

    public void updateTileStatus(int tileNum, TileType status){
        for(int row_ctr = 0; row_ctr < 10; row_ctr++){
            for(int col_ctr = 0; col_ctr < 5; col_ctr++){

                if(TILES[row_ctr][col_ctr].tileNum == tileNum){
                    TILES[row_ctr][col_ctr].setText(String.valueOf(status));
                    break;
                }
            }
        }
    }

    private class FarmTile extends JButton {
        private int tileNum;

        public FarmTile(int tileNum) {
            this.tileNum = tileNum;
            setActionCommand(String.valueOf(tileNum));
            setText("UNPLOWED");
            setName(String.valueOf(tileNum));
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(91,58));
            setFocusable(false);
            setContentAreaFilled(false);
            setBackground(Color.pink);
            setBorder(BorderFactory.createEmptyBorder());
            setOpaque(false);
        }
    }

    private void setInventory(){
        // 0 - Plow, 1 - Pickaxe, 2 - Shovel, 3 - Watering Can , 4- Fertilizer, 5 - Seeds
        for(int ctr = 0; ctr < 6; ctr++){

            JLabel icon = new JLabel(new ImageIcon("assets/img/tools/"+ctr+".png"));

            INVENTORY_SLOTS[ctr] = new JButton();
            //INVENTORY_SLOTS[ctr].setIcon(new ImageIcon("assets/img/default-inventory-bg.png"));

            INVENTORY_SLOTS[ctr].setLayout(new BorderLayout());
            //INVENTORY_SLOTS[ctr].add(icon, BorderLayout.CENTER);

            INVENTORY_SLOTS[ctr].setFocusable(false);
            INVENTORY_SLOTS[ctr].setBackground(new Color(0xB09358));
            INVENTORY_SLOTS[ctr].setBorder(BorderFactory.createEmptyBorder());
            INVENTORY_SLOTS[ctr].setPreferredSize(new Dimension(100,50));

            INVENTORY.add(INVENTORY_SLOTS[ctr],0);
        }
        INVENTORY_SLOTS[0].setText("Seeds");
        INVENTORY_SLOTS[1].setText("Fertilizer");
        INVENTORY_SLOTS[2].setText("Watering Can");
        INVENTORY_SLOTS[3].setText("Shovel");
        INVENTORY_SLOTS[4].setText("Pickaxe");
        INVENTORY_SLOTS[5].setText("Plow");

        btnShop = new JButton("Shop");
        btnShop.setFocusable(false);
        btnShop.setOpaque(false);
        btnShop.setBackground(TRANSPARENT);
        btnShop.setBorder(new EmptyBorder(0,15,20,0));
        btnShop.setPreferredSize(new Dimension(90,100));
    }

    public JFrame getPopupNickname() {
        return popupNickname;
    }

    public JTextField getTxtField() {
        return txtField;
    }

    public void setTxtField(JTextField txtField) {
        this.txtField = txtField;
    }

    public JTextField getTxtNickname() {
        return txtNickname;
    }

    public void setTxtNickname(JTextField txtNickname) {
        this.txtNickname = txtNickname;
    }

    public void setNicknamePrompt(){
        //Frame
        popupNickname.setTitle("model.Farmer Nickname"); // PopUp Title
        popupNickname.setIconImage(new ImageIcon("assets/img/logo.png").getImage()); // Icon
        popupNickname.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        popupNickname.setSize(715,635);
        popupNickname.setResizable(false); // not resizable
        popupNickname.setBackground(new Color(0xdcb98a));
        popupNickname.setLocationRelativeTo(null); // center of screen
        popupNickname.setLayout(null);

        //Label & TextField
        JLabel textPrompt = new JLabel("What should we call you? ");
        textPrompt.setBounds(50, 50, 200, 30);
        txtNickname.setBounds(50, 100, 200, 30);
        SET_NAME_BUTTON.setActionCommand("Proceed");
        SET_NAME_BUTTON.setIcon(new ImageIcon("assets/store/confirmPurchaseBttn.png"));

        SET_NAME_BUTTON.setFocusable(false);
        SET_NAME_BUTTON.setOpaque(false);
        SET_NAME_BUTTON.setFont(DEFAULT_FONT);
        SET_NAME_BUTTON.setBackground(TRANSPARENT);
        SET_NAME_BUTTON.setBorderPainted(false);

        SET_NAME_BUTTON.setBounds(50, 150, 300, 120); // 50, 150, 100, 30
        SET_NAME_BUTTON.addActionListener(e -> setMainScreen());
        popupNickname.add(SET_NAME_BUTTON);
        popupNickname.add(textPrompt);
        popupNickname.add(txtNickname);
        popupNickname.add(BACKGROUNDS[1]);
        popupNickname.setVisible(true);
    }

    public void setFileNameScreen(){
        //Frame
        popupRocks.setTitle("Entering Hard Mode..."); // PopUp Title
        popupRocks.setIconImage(new ImageIcon("assets/img/logo.png").getImage()); // Icon
        popupRocks.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        popupRocks.setSize(718,624);
        popupRocks.setResizable(false); // not resizable
        popupRocks.setLocationRelativeTo(null); // center of screen
        popupRocks.setLayout(null);

        //Label & TextField
        JLabel textPrompt = new JLabel("Enter File name: ");
        textPrompt.setBounds(50, 50, 200, 30);
        txtField.setBounds(50, 100, 200, 30);
        GENERATE_BUTTON.setActionCommand("Generate");
        GENERATE_BUTTON.setBounds(50, 150, 100, 30);
        popupRocks.add(GENERATE_BUTTON);
        popupRocks.add(textPrompt);
        popupRocks.add(txtField);
        popupRocks.setVisible(true);
    }

    // This method sets the layout, bounds and background of the BASE attribute
    private void setBase() {
        BASE.setLayout(null);
        BASE.setBounds(0, 0, (int)DEFAULT_SIZE.getWidth(), (int)DEFAULT_SIZE.getHeight());
        BASE.setBackground(TRANSPARENT);
    }

    // This method sets the layout and bounds of the BACKGROUNDS attribute.
    private void setBackgrounds() {
        BACKGROUNDS[0].setLayout(new BorderLayout());
        BACKGROUNDS[0].setBounds(0, 0, (int)DEFAULT_SIZE.getWidth(), (int)DEFAULT_SIZE.getHeight());

        BACKGROUNDS[1].setLayout(new BorderLayout());
        BACKGROUNDS[1].setBounds(0, 0, 730, 630);

        BACKGROUNDS[2].setLayout(new BorderLayout());
        BACKGROUNDS[2].setBounds(0, 0, DEF_FRAME_SIZE.width, DEF_FRAME_SIZE.height);
    }

    // This method sets the layout, background and size of the CENTER, LEFT, RIGHT, LOWER CONTAINERS
    private void setTransparentContainers() {
        // set transparent upper container
        CENTER_CONTAINER.setLayout(new FlowLayout(FlowLayout.CENTER, 50,0));
        CENTER_CONTAINER.setBackground(TRANSPARENT);

        // set transparent lower container
        LOWER_CONTAINER.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        LOWER_CONTAINER.setBackground(TRANSPARENT);

        // set transparent right container
        RIGHT_CONTAINER.setLayout(new BorderLayout());
        RIGHT_CONTAINER.setBackground(TRANSPARENT);

        // set transparent left container
        LEFT_CONTAINER.setLayout(new BorderLayout());
        LEFT_CONTAINER.setBackground(TRANSPARENT);

    }

    //      This method sets the frame
    private void setFrame() {
        // setup this frame
        this.setTitle("ThreeVille"); // title for the window
        this.setIconImage(new ImageIcon("assets/img/logo.png").getImage()); // icon for the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEF_FRAME_SIZE);
        this.setResizable(false); // not resizable
        this.setLocationRelativeTo(null); // center of screen
        this.setLayout(new BorderLayout());
        this.setVisible(true);
    }

    public JFrame getFrame(){
        return this;
    }

    public void setListeners(MouseListener start, MouseListener gameMode, ActionListener popupButtons, ActionListener inventory, ActionListener board, ActionListener seeds, ActionListener btn) {
        START_BUTTONS[0].addMouseListener(start); // listener for the play button
        START_BUTTONS[1].addMouseListener(start); // listener for the exit button
        GAME_MODES[0].addMouseListener(gameMode); // listener for the easy mode
        GAME_MODES[1].addMouseListener(gameMode); // listener for the hard mode
        GENERATE_BUTTON.addActionListener(popupButtons); // listener for the generate button
        SET_NAME_BUTTON.addActionListener(popupButtons); // listener for the set name button
        for(int ctr = 0; ctr < 6; ctr++){
            INVENTORY_SLOTS[ctr].addActionListener(inventory);
        }

        for(int row_ctr = 0; row_ctr < 10; row_ctr++){
            for(int col_ctr = 0; col_ctr < 5; col_ctr++){
                TILES[row_ctr][col_ctr].addActionListener(board);
            }
        }

        btnNextDay.addActionListener(btn);

        btnShop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setStoreScreen(seeds);
            }
        });


        // TODO: [model.Inventory slots, shop], tiles, upgrade status button, advance to next day
    }

    public JButton getBtnPlay() {
        return START_BUTTONS[0];
    }

    public JButton getBtnExit() {
        return START_BUTTONS[1];
    }

    public JButton getBtnEasy() {
        return GAME_MODES[0];
    }

    public JButton getBtnHard() {
        return GAME_MODES[1];
    }

    public JButton getBtnSetName() {
        return SET_NAME_BUTTON;
    }

    public JButton getBtnGenerate() {
        return GENERATE_BUTTON;
    }

    public JButton getPlow() {
        return INVENTORY_SLOTS[5];
    }

    public JButton getPickaxe() {
        return INVENTORY_SLOTS[4];
    }

    public JButton getShovel() {
        return INVENTORY_SLOTS[3];
    }

    public JButton getWateringCan() {
        return INVENTORY_SLOTS[2];
    }

    public JButton getFertilizer() {
        return INVENTORY_SLOTS[1];
    }

    public JButton getSeeds() {
        return INVENTORY_SLOTS[0];
    }

    public JButton[] getINVENTORY_SLOTS() {
        return INVENTORY_SLOTS;
    }

    public JButton getBtnShop() {
        return btnShop;
    }
}
