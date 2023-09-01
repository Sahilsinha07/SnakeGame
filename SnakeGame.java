package snakeGame;

import java.util.*;
import javax.swing.*;

public class SnakeGame extends JFrame {
    SnakeGame() {

        super("Snake Game"); // used to give title and call the contructor of the parent class must be the
                             // first line
        setResizable(false); // restricting the resizing ability of the frame to false
        add(new Board()); // calling the constructor from the Board.java file. add function is there to
                          // add eveerything on the frame
        pack(); // used to refresh and render things. refresh frame even when open
        // setSize(400, 400); // we will be setting size of the screen in board it is
        // more suitable as board is made there only
        setLocationRelativeTo(null); // used to set the size of the frame realtive to the screeen size
        setVisible(true);

    }

    public static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {
        new SnakeGame();
    }
}
