import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BingoCardsPanel extends JPanel implements MouseListener, KeyListener {
    private static Random rand;
    private static final JTextField field = new JTextField(20);
    private static final JButton startButton = new JButton();
    private static int gameState;
    private static final int SEED = 0, PLAY_BINGO = 1, FINISH = 2;
    private static BingoBalls balls;
    private static int currentBall;
    private static int currentGrid = 0;
    private static int numDays, numRounds, numWinners;
    private static ArrayList<BingoGrid> grids = new ArrayList<>();
    private static ArrayList<BingoGrid> winGrids = new ArrayList<BingoGrid>();
    private static String path;
    public BingoCardsPanel () {
        gameState = SEED;
        startButton.setBounds(300,300, 100, 100);
        startButton.setText("click me to start playing bingo");
        add(startButton);
        startButton.addMouseListener(this);
        addMouseListener(this);
        setVisible(true);
    }

    public void paint (Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        switch (gameState) {
            case SEED:
                break;
            case PLAY_BINGO:
                calculate();
                drawGrids(g);
                paintBall(g);
                drawButtons(g);
                System.out.println("winGrids size: " + winGrids.size());
                break;
            case FINISH:
                drawGrids(g);
                paintBall(g);
                drawButtons(g);
                g.setFont(new Font("Helvetica", Font.BOLD, 70));
                g.drawString("DONE", 520, 500);
                break;
        }
    }

    public void calculate () {
        for (int i = 0; i < grids.size(); i++) {
            if (grids.get(i).isWin() < 0) {
                grids.get(i).fillBall(currentBall);
            } if (!grids.get(i).getWin() && grids.get(i).isWin() > 0 && winGrids.size() < numWinners) {
                winGrids.add(grids.get(i));
                grids.get(i).win();
                grids.get(i).setRoundOfWin(numRounds);
            }
        }
        for (int i = 0; i < grids.size(); i++) {
            System.out.print(grids.get(i).getRoundOfWin() + " ");
        }
        System.out.println("round: " + numRounds);
        if (winGrids.size() >= numWinners) {
            gameState = FINISH;
            BingoGrid.setDraws(numDays, numRounds);
            for (int i = 0; i < grids.size(); i++) {
                grids.get(i).setDayOfWin();
            }
            repaint();
        }
    }
    public void drawGrids (Graphics g) {
        grids.get(currentGrid).buildBingoFrame(g);
        grids.get(currentGrid).fillBingo(g);
        grids.get(currentGrid).drawBall(g);
        grids.get(currentGrid).showWinTime(g);
    }
    public void paintBall(Graphics g) {
        g.setFont(new Font("Helvetica", Font.PLAIN, 25));
        if (currentBall >= 1 && currentBall <= 15) {
            g.drawString("B - " + Integer.toString(currentBall), 320, 660);
        } else if (currentBall >= 16 && currentBall <= 30) {
            g.drawString("I - " + Integer.toString(currentBall), 320, 660);
        } else if (currentBall >= 31 && currentBall <= 45) {
            g.drawString("N - " + Integer.toString(currentBall), 320, 660);
        } else if (currentBall >= 46 && currentBall <= 60) {
            g.drawString("G - " + Integer.toString(currentBall), 320, 660);
        } else if (currentBall >= 51 && currentBall <= 75) {
            g.drawString("O - " + Integer.toString(currentBall), 320, 660);
        } if (currentBall > 0) {
            g.setColor(Color.red);
            g.drawOval(315, 610, 80, 80);
        }
        if (grids.get(currentGrid).isWin() > 0) {
            grids.get(currentGrid).drawWinLine(g);
            g.setFont(new Font("Helvetica", Font.BOLD, 100));
            g.setColor(Color.blue);
            g.drawString("YOU WIN", 25, 350);
        }
    }
    public void drawButtons (Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 600, 300, 100);
        g.fillRect(500, 150, 150, 75);

        g.fillRect(520, 350, 50, 75);
        g.fillRect(600, 350, 50, 75);

        g.fillRect(700, 150, 150, 75);
        g.fillRect(700, 250, 150, 75);

        g.setFont(new Font("Helvetica", Font.PLAIN, 30));
        g.setColor(Color.black);
        g.drawString("Click for new ball", 20, 650);
        g.setFont(new Font("Helvetica", Font.PLAIN, 20));
        g.drawString("Change card", 505, 190);
        g.drawString("See bingo balls", 705, 190);
        g.drawString("See bingo cards", 705, 290);
        g.setFont(new Font("Helvetica", Font.BOLD, 40));
        g.drawString("<", 530, 390);
        g.drawString(">", 610, 390);
        if (gameState == FINISH) {
            g.setColor(Color.CYAN);
            g.fillRect(500, 250, 150, 75);
            g.setColor(Color.black);
            g.setFont(new Font("Helvetica", Font.PLAIN, 20));
            g.drawString("Print Cards", 505, 290);
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (e.getSource() == startButton) {
            String s = (String) JOptionPane.showInputDialog(this,
                    "Please enter the game number:", null);
            int i = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Please enter the number of cards:", null));
            numDays = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Please enter the number of days (1-5):", null));
            numWinners = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Please enter the number of winners:", null));
            path = JOptionPane.showInputDialog(this,
                    "Please enter the preferred path:", null);
            path += JOptionPane.showInputDialog(this,
                    "Please enter the file name:", null);
            rand = new Random(Integer.parseInt(s));
            startButton.setVisible(false);
            revalidate();
            repaint();

            gameState = PLAY_BINGO;
            for (int j = 0; j < i; j++) {
                grids.add(new BingoGrid(rand, j, Integer.parseInt(s)));
                grids.get(j).fillGrid();
                rand = grids.get(j).getRand();
            }
            balls = new BingoBalls(grids.get(grids.size() - 1).getRand());
        } else if (gameState == PLAY_BINGO) {

            if (x >= 0 && x <= 300 && y >= 600 && y <= 700) {
                numRounds++;
                currentBall = balls.getBall();
                repaint();
            } else if (x >= 500 && x <= 650 && y >= 150 && y <= 225) {
                String s = JOptionPane.showInputDialog(this,
                        "Enter the new card number: ", null);
                if (Integer.parseInt(s) >= grids.size() || Integer.parseInt(s) < 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number");
                } else {
                    currentGrid = Integer.parseInt(s);
                }
                repaint();
            } else if (x >= 520 && x <= 570 && y >= 350 && y <= 425) {
                if (currentGrid == 0) {
                    currentGrid = grids.size() - 1;
                } else {
                    currentGrid--;
                }
                repaint();
            } else if (x >= 600 && x <= 650 && y >= 350 && y <= 425) {
                if (currentGrid == grids.size() - 1) {
                    currentGrid = 0;
                } else {
                    currentGrid++;
                }
                repaint();
            } else if (x >= 700 && x <= 850 && y >= 150 && y <= 225) {
                String s = "";
                ArrayList<Integer> arr = BingoBalls.getNums();
                for (int i = 0; i < arr.size(); i++) {
                    s += arr.get(i) + " ";
                }
                JOptionPane.showMessageDialog(this, s);
            } else if (x >= 700 && x <= 850 && y >= 250 && y <= 325) {
                String s = "";
                for (int i = 0; i < winGrids.size(); i++) {
                    s += winGrids.get(i) + " ";
                }
                JOptionPane.showMessageDialog(this, s);
            }

        } else if (gameState == FINISH) {
            if (x >= 500 && x <= 650 && y >= 150 && y <= 225) {
                String s = JOptionPane.showInputDialog(this,
                        "Enter the new card number: ", null);
                if (Integer.parseInt(s) >= grids.size() || Integer.parseInt(s) < 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number");
                } else {
                    currentGrid = Integer.parseInt(s);
                }
                repaint();
            } else if (x >= 500 && x <= 650 && y >= 250 && y <= 325) {
                try {
                    printCards();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } else if (x >= 520 && x <= 570 && y >= 350 && y <= 425) {
                if (currentGrid == 0) {
                    currentGrid = grids.size() - 1;
                } else {
                    currentGrid--;
                }
                repaint();
            } else if (x >= 600 && x <= 650 && y >= 350 && y <= 425) {
                if (currentGrid == grids.size() - 1) {
                    currentGrid = 0;
                } else {
                    currentGrid++;
                }
                repaint();
            } else if (x >= 700 && x <= 850 && y >= 150 && y <= 225) {
                String s = "";
                ArrayList<Integer> arr = BingoBalls.getNums();
                for (int i = 0; i < arr.size(); i++) {
                    s += arr.get(i) + " ";
                }
                JOptionPane.showMessageDialog(this, s);
            } else if (x >= 700 && x <= 850 && y >= 250 && y <= 325) {
                String s = "";
                for (int i = 0; i < winGrids.size(); i++) {
                    s += winGrids.get(i).getId() + " ";
                    if ((i+1) % 20 == 0) {
                        s += "\n";
                    }
                }
                JOptionPane.showMessageDialog(this, s);
            }
        }

    }

    public void printCards () throws IOException {
        /*
        File folder = new File("cards");
        for (int i = 0; i < grids.size(); i++) {
            folder.
        }

         */
        File file = new File(path);
        boolean bool = file.mkdirs();
        System.out.println(path);
        if (bool) {
            System.out.println("LETS GO");
        } else {
            System.out.println("bruh");
        }
        for (int i = 0; i < grids.size()/4; i++) {
            /*
            File f = new File(path + "\\" + i + ".jpg");
            if (!f.exists()) {
                f.createNewFile();
                ImageIO.write(grids.get(i).getImage(), "jpg", f);
                System.out.println(path + "\\" + i + ".jpg " + bool);
            }
             */
            File f = new File(path + "\\" + i + ".jpg");
            if (!f.exists()) {
                f.createNewFile();
                BufferedImage i1 = grids.get(4*i).getImage();
                BufferedImage i2 = grids.get(4*i+1).getImage();
                BufferedImage i3 = grids.get(4*i+2).getImage();
                BufferedImage i4 = grids.get(4*i+3).getImage();
                BufferedImage finalImage = new BufferedImage(i1.getWidth()*2, i1.getHeight()*2, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = finalImage.createGraphics();
                g.drawImage(i1, 0, 0, null);
                g.drawImage(i2, i1.getWidth(), 0, null);
                g.drawImage(i3, 0, i1.getHeight(), null);
                g.drawImage(i4, i1.getWidth(), i1.getHeight(), null);
                ImageIO.write(finalImage, "jpg", f);
                System.out.println(path + "\\" + i + ".jpg " + bool);
            }

        }

    }
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
