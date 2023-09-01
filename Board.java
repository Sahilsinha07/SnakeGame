package snakeGame;

import javax.swing.*;
import java.awt.*; // importing this to add colors to the file
import java.awt.event.*; // importing this for timer function 

public class Board extends JPanel implements ActionListener {
    // jpanel is a sub part of frame and action listener is a interface to count
    // actions and we have to overide all the abstract methods present in a
    // interface as they are just declared and not defined.
    private Image apple;
    private Image dot;
    private Image head;
    private int dots; // gloabally stating the dots. one for head 2 for body when game starts
    private final int ALL_DOTS = 1600; // ik variable le liya ur uski value frame kae area ki eqaul krdi kuki vo to
                                       // kabhi change nahi hoga
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    private final int dot_size = 10;
    private final int RANDOM_VALUE = 29;
    private int apple_x;
    private int apple_y;
    private Timer timer;
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    Board() {
        addKeyListener(new TAdapter()); // making a object of the inner class to initilaize it.
        setBackground(Color.BLACK); // seting background color to black
        setFocusable(true); // focusing on the frame so the game can be played directly
        setPreferredSize(new Dimension(400, 400)); // here we are setting the size of the frame and making anew object
                                                   // dimension
        loadImages(); // this is to load images on the frame
        initGame(); // initializing the game
    }

    public void loadImages() {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("snakeGame/apple.png")); // importing all the three
                                                                                            // images
        // using ImageIcon class which is
        // present in Jframe
        apple = i1.getImage(); // storing the images as we have gloablly declared the image using aws class and
                               // picked it up from the system using swing class.
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("snakeGame/head.png"));
        head = i2.getImage();
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("snakeGame/dot.png"));
        dot = i3.getImage();

    }

    public void initGame() {
        dots = 3; // one for head two for body
        for (int i = 0; i < dots; i++) {
            // this for loop is to place images on the frame
            y[i] = 50;
            x[i] = 50 - i * dot_size;

        }
        locateApple(); // this function is made to make the apple paint on the frame
        timer = new Timer(140, this); // here we have declared timer we the delay of 140 milliseconds
        timer.start(); // timer starts with this function
    }

    public void locateApple() {
        // through this function we will be randomly generating apple on the frame
        // anywhere
        int r = (int) (Math.random() * RANDOM_VALUE); // we have done type castimg as the value was in float and we have
                                                      // changed it into integer. math.random is used to gernerate
                                                      // random number
        apple_x = r * dot_size;
        int q = (int) (Math.random() * RANDOM_VALUE);
        apple_y = q * dot_size; // storing the values in a gloabal declared variable
    }

    public void paintComponent(Graphics g) { // passing a graphic clas method to paint images on the screen
        super.paintComponent(g); // done with the help of super function
        draw(g);
    }

    public void draw(Graphics g) { // ye method h ya function h abhi clear nhi hae.. get back to this point
        // this is used to draw all the three images on the frame
        if (inGame) {
            g.drawImage(apple, apple_x, apple_y, this); // we are drawing apple image at a random position using draw
                                                        // function
            for (int i = 0; i < dots; i++) { // this loop is to draw head and body
                if (i == 0) {
                    g.drawImage(head, x[i], y[i], this); // this function is used to draw the image of the head at the
                                                         // given
                                                         // coordinates and tne passed value is this
                } else {
                    g.drawImage(dot, x[i], y[i], this); // this function is used to draw the image of the body at the
                                                        // given
                                                        // coordinates and the passed value is this
                }
            }
            Toolkit.getDefaultToolkit().sync(); // intializing it with the help of default function
        } else {
            gameOver(g); // this is a object and will call a function when game is over
        }
    }

    public void gameOver(Graphics g) {
        String msg = "GAME OVER!!!";
        Font font = new Font("SANS SERIF", Font.BOLD, 14); // here we are setting the size , bold of the font
        FontMetrics metrices = getFontMetrics(font); // we are making a object of font metrics
        g.setColor(Color.ORANGE);
        g.setFont(font);
        g.drawString(msg, (400 - metrices.stringWidth(msg)) / 2, 400 / 2); // here we are painting the string on the
                                                                           // frame and the string should be in center
                                                                           // so we have used metrics.stringWidth which
                                                                           // is used to calculate the widhth size of
                                                                           // the string
    }

    public void move() {
        for (int i = dots; i > 0; i--) { // for the movement of the dots
            x[i] = x[i - 1]; // jo pechla element hae vo agle element k jagh pe aa jaiega after movement
            y[i] = y[i - 1]; // this loop will only move the body that is dots and not the head.
        }
        if (leftDirection) {
            x[0] = x[0] - dot_size; // these all the if statements are for movements checks. where the snake head is
                                    // moving
            // remember we have just moved head with the help of this as the body will be
            // moving with the help of for loop.
        }

        if (rightDirection) {
            x[0] = x[0] + dot_size;
        }
        if (upDirection) {
            y[0] = y[0] - dot_size;
        }
        if (downDirection) {
            y[0] = y[0] + dot_size;
        }

    }

    public void checkApple() {
        // we will just check the head of the snake and apple coordinates that if they
        // collide or not
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++; // length of the snake increased and vo apple to vo kha chuaka hae so we need
                    // another apple
            locateApple(); // again spawn the apple
        }

    }

    public void checkCollision() {
        // this function is made to check when the snake gets hit with boundary or by
        // itself
        for (int i = dots; i > 0; i--) { // this loop is to detect if the head of the snake gets collided with the body
                                         // this is the first condition for the game to get over
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                inGame = false;
            }
        }
        // second contion is borders. if the snake moves out of border then we will but
        // this conditions
        if (x[0] > 400) {
            // this will check if the snake head is colided with border or not
            inGame = false;
        }
        if (x[0] < 0) {
            inGame = false;
        }
        if (y[0] > 400) {
            inGame = false;
        }
        if (y[0] < 0) {
            inGame = false;
        }
        if (inGame == false) {
            timer.stop();
        }
    }

    public void actionPerformed(ActionEvent ae) { // override of the abstract method actionPerformed as it is just
        // declared and not defined
        if (inGame) {
            checkApple();
            // this is to check whether the head of the snake had a collision with the apple
            // coordinates
            checkCollision();
            move();
            // for the movement of the snake
            // Jaise hi move calss ka object milega waise hi uska constructor call hoga joki
            // movement batiega and then repaint will refresh the screen
        }
        repaint(); // this funcion is same as pack and is used to refresh
    }

    public class TAdapter extends KeyAdapter { // this is a inner class . class kae andar class.
        // this class has to be initialized when the code run else ye ignore ho jaiega
        // iske neeche ka function to apne aap call ho jaiega so make a object of this
        // class
        public void keyPressed(KeyEvent e) { // this is a method which is called from the java.awt.event.* . mere hisab
                                             // sa ye methods hote hae
            int key = e.getKeyCode(); // this is a syntax to get the key pressed on the keyboard
            if (key == KeyEvent.VK_A && (!rightDirection)) { // this is to check which key is pressed if left is
                                                             // pressed
                                                             // then code is written
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                // we will not be making right direction as false because the snake will not go
                // back directly, it has to take a turn
            }
            if (key == KeyEvent.VK_D && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_W && (!downDirection)) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if (key == KeyEvent.VK_S && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }

        }

    }
}

// NOTE-----> all the public void locateApple(); type of these are functions not
// methods
