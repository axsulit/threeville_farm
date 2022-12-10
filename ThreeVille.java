/**
 * This class is the driver class for the program ThreeVille.
 * This executes the game itself and handles the game mechanics.
 *
 * @author Anne Gabrielle Sulit
 * @author Ysobella Torio
 */
public class ThreeVille {
    public static void main (String[] args) {
        ThreeVilleModel gameBoard = new ThreeVilleModel();
        ThreeVilleGUI GUI = new ThreeVilleGUI();
        ThreeVilleController controller = new ThreeVilleController(gameBoard, GUI);
    }
}
