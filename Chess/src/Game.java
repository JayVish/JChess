import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework.  This
 * framework is very effective for turn-based games.  We STRONGLY 
 * recommend you review these lecture slides, starting at slide 8, 
 * for more details on Model-View-Controller:  
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard.  The GameBoard will
 * it will instantiate a TicTacToe object to serve as the game's model.
 */
public class Game implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Chess");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
//        Font f = status.getFont();
//        status.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        status_panel.add(status);

        // Game board
        JPanel game = new JPanel();
        final GameBoard board = new GameBoard(status, game);
        frame.add(game, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });
        control_panel.add(undo);

        String instructionsText = "This game follows the standard rules of 2-player chess. " +
                "When you click on a piece, it will show you your available moves. The king will " +
                "be highlighted with a red circle when under check. When there's a checkmate," +
                "the relevant king will be highlighted with a red square. The bottom" +
                " bar will show the status of the game at all times. " +
                "The undo button will reverse whatever move was made. Note " +
                "that pawns will be automatically promoted into queens for convenience." +
                "The reset button will restart the game from the beginning state." +
                "Have fun playing!";

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JTextArea ta = new JTextArea(instructionsText, 2, 40);
                ta.setWrapStyleWord(true);
                ta.setLineWrap(true);
                ta.setOpaque(false);
                ta.setBorder(null);
                ta.setEditable(false);
                ta.setFocusable(false);
                JOptionPane.showMessageDialog(frame, ta, "Instructions", JOptionPane.INFORMATION_MESSAGE);
                //JOptionPane.showMessageDialog(frame, instructionsText);
            }
        });
        control_panel.add(instructions);


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}