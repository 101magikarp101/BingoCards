import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class BingoGrid {
    private static Random rand;
    private static int[][] draws;
    private final int[][] grid = new int[5][5];
    private final int[][] ball = new int[5][5];
    private int id, number;
    private boolean winner = false;
    private int dayOfWin, roundOfWin;
    private static Graphics2D graphics;
    public BingoGrid (Random r, int i, int n) {
        rand = r;
        id = i;
        number = n;
    }
    public static void setDraws (int i, int rounds) {
        draws = new int[2][i];
        for (int j = 0; j < i; j++) {
            for (int k = 0; k < 2; k++) {
                draws[k][j] = rounds / (2*i);
            }
        }
        rounds = rounds % (2*i);
        for (int k = 0; k < i; k++) {
            for (int j = 0; j < 2; j++) {
                if (rounds > 0) {
                    draws[j][k]++;
                    rounds--;
                } else {
                    break;
                }
            }
        }
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < i; k++) {
                System.out.print(draws[j][k] + " ");
            }
            System.out.println();
        }
    }
    public int getId () {
        return id;
    }
    public void win () {
        winner = true;
    }
    public boolean getWin() {
        return winner;
    }
    public void setRoundOfWin (int i) {
        roundOfWin = i;
    }
    public void setDayOfWin () {
        int count = 0;
        for (int i = 0; i < draws[0].length; i++) {
            for (int j = 0; j < draws.length; j++) {
                count += draws[j][i];
                if (roundOfWin <= count) {
                    dayOfWin = i + 1;
                    break;
                }
            }
        }
    }
    public int getRoundOfWin () {
        return roundOfWin;
    }
    public int getDayOfWin () {
        return dayOfWin;
    }
    public void fillGrid () {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i <= 4; i++) {
            for (int k = 1+15*(i); k <= 15*(i+1); k++) {
                arr.add(k);
            }
            for (int j = 0; j <= 4; j++) {
                if (!(i == 2 && j == 2)) {
                    grid[i][j] = arr.remove(rand.nextInt(arr.size()));
                }
            }
            arr.clear();
        }
        if (isWin() > 0 && roundOfWin != 0) {
            System.out.println("ALERT");
        }
        /*
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(grid[j][i] + " ");
            }
            System.out.println();
        }
        System.out.println(id + " " + number);

         */
    }
    public void fillBall (int currentBall) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] == currentBall) {
                    ball[j][i] = 1;
                }
            }
        }
    }
    public void drawBall (Graphics g) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (ball[i][j] == 1) {
                    g.drawOval(100 * (j) + 15, 100 * (i + 1) + 15, 70, 70);
                }
            }
        }
    }
    public void drawWinLine (Graphics g) {
        g.setColor(Color.red);
        int winState = isWin();
        if (winState == 1) {
            g.drawLine(50, 150, 450, 150);
        } else if (winState == 2) {
            g.drawLine(50, 250, 450, 250);
        } else if (winState == 3) {
            g.drawLine(50, 350, 450, 350);
        } else if (winState == 4) {
            g.drawLine(50, 450, 450, 450);
        } else if (winState == 5) {
            g.drawLine(50, 550, 450, 550);
        } else if (winState == 6) {
            g.drawLine(50, 150, 50, 550);
        } else if (winState == 7) {
            g.drawLine(150, 150, 150, 550);
        } else if (winState == 8) {
            g.drawLine(250, 150, 250, 550);
        } else if (winState == 9) {
            g.drawLine(350, 150, 350, 550);
        } else if (winState == 10) {
            g.drawLine(450, 150, 450, 550);
        } else if (winState == 11) {
            g.drawLine(50, 150, 450, 550);
        } else if (winState == 12) {
            g.drawLine(450, 150, 50, 550);
        }
    }


    public void buildBingoFrame (Graphics g) {
        g.setFont(new Font("Helvetica", Font.BOLD, 65));
        g.drawRect(0, 0, 500, 600);
        g.drawLine(0, 100, 500, 100);
        for (int i = 1; i <= 4; i++) {
            g.drawLine(0 , 100+100*i, 500, 100+100*i);
        }
        for (int i = 1; i <= 4; i++) {
            g.drawLine(100*i, 100, 100*i, 600);
        }
        g.setColor(Color.red);
        g.drawString("B", 25, 75);
        g.drawString("I", 137, 75);
        g.drawString("N", 225, 75);
        g.drawString("G", 325, 75);
        g.drawString("O", 425, 75);
        g.setColor(Color.black);
        g.setFont(new Font("Helvetica", Font.PLAIN, 25));
        g.drawString("Game number: " + number, 500, 50);
        g.drawString("Card id: " + id, 500, 100);
    }
    public void showWinTime (Graphics g) {
        if (isWin() > 0) {
            g.drawString("Won on round " + roundOfWin + " of day " + dayOfWin, 500, 135);
        } else {
            g.drawString("Did not win", 500, 135);
        }
    }
    public void fillBingo (Graphics g) {
        g.setFont(new Font("Helvetica", Font.PLAIN, 40));
        g.setColor(Color.black);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] >= 10) {
                    g.drawString(Integer.toString(grid[i][j]), 30 + 100 * (i), 65 + 100 * (j+1));
                } else if (grid[i][j] >= 1) {
                    g.drawString(Integer.toString(grid[i][j]), 40 + 100 * (i), 65 + 100 * (j+1));
                }
            }
        }
        g.setFont(new Font("Helvetica", Font.BOLD, 30));
        g.setColor(Color.blue);
        g.drawString("FREE", 210, 360);
    }
    public int[][] getGrid () {
        return grid;
    }
    public Random getRand () {
        return rand;
    }
    public int isWin () {
        /*
        for (int i = 0; i < ball.length; i++) {
            for (int j = 0; j < ball.length; j++) {
                System.out.print(ball[i][j] + " ");
            }
            System.out.println();
        }

         */
        if (ball[0][0] == 1 && ball[0][1] == 1 && ball[0][2] == 1 && ball[0][3] == 1 && ball[0][4] == 1) {
            return 1;
        } else if (ball[1][0] == 1 && ball[1][1] == 1 && ball[1][2] == 1 && ball[1][3] == 1 && ball[1][4] == 1) {
            return 2;
        } else if (ball[2][0] == 1 && ball[2][1] == 1 && ball[2][2] == 1 && ball[2][3] == 1 && ball[2][4] == 1) {
            return 3;
        } else if (ball[3][0] == 1 && ball[3][1] == 1 && ball[3][2] == 1 && ball[3][3] == 1 && ball[3][4] == 1) {
            return 4;
        } else if (ball[4][0] == 1 && ball[4][1] == 1 && ball[4][2] == 1 && ball[4][3] == 1 && ball[4][4] == 1) {
            return 5;
        } else if (ball[0][0] == 1 && ball[1][0] == 1 && ball[2][0] == 1 && ball[3][0] == 1 && ball[4][0] == 1) {
            return 6;
        } else if (ball[0][1] == 1 && ball[1][1] == 1 && ball[2][1] == 1 && ball[3][1] == 1 && ball[4][1] == 1) {
            return 7;
        } else if (ball[0][2] == 1 && ball[1][2] == 1 && ball[2][2] == 1 && ball[3][2] == 1 && ball[4][2] == 1) {
            return 8;
        } else if (ball[0][3] == 1 && ball[1][3] == 1 && ball[2][3] == 1 && ball[3][3] == 1 && ball[4][3] == 1) {
            return 9;
        } else if (ball[0][4] == 1 && ball[1][4] == 1 && ball[2][4] == 1 && ball[3][4] == 1 && ball[4][4] == 1) {
            return 10;
        } else if (ball[0][0] == 1 && ball[1][1] == 1 && ball[2][2] == 1 && ball[3][3] == 1 && ball[4][4] == 1) {
            return 11;
        } else if (ball[0][4] == 1 && ball[1][3] == 1 && ball[2][2] == 1 && ball[3][1] == 1 && ball[4][0] == 1) {
            return 12;
        } else {
            return -1;
        }
    }
    public BufferedImage getImage () {
        BufferedImage bf = new BufferedImage(503, 630, BufferedImage.TYPE_INT_RGB);
        graphics = bf.createGraphics();
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, bf.getWidth(), bf.getHeight());
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Helvetica", Font.BOLD, 65));
        graphics.drawRect(0, 0, 500, 600);
        graphics.drawLine(0, 100, 500, 100);
        for (int i = 1; i <= 4; i++) {
            graphics.drawLine(0 , 100+100*i, 500, 100+100*i);
        }
        for (int i = 1; i <= 4; i++) {
            graphics.drawLine(100*i, 100, 100*i, 600);
        }
        graphics.setColor(Color.red);
        graphics.drawString("B", 25, 75);
        graphics.drawString("I", 137, 75);
        graphics.drawString("N", 225, 75);
        graphics.drawString("G", 325, 75);
        graphics.drawString("O", 425, 75);
        graphics.setColor(Color.black);
        graphics.setFont(new Font("Helvetica", Font.PLAIN, 40));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] >= 10) {
                    graphics.drawString(Integer.toString(grid[i][j]), 30 + 100 * (i), 65 + 100 * (j+1));
                } else if (grid[i][j] >= 1) {
                    graphics.drawString(Integer.toString(grid[i][j]), 40 + 100 * (i), 65 + 100 * (j+1));
                }
            }
        }
        graphics.setFont(new Font("Helvetica", Font.BOLD, 30));
        graphics.setColor(Color.blue);
        graphics.drawString("FREE", 210, 360);
        graphics.setFont(new Font("Helvetica", Font.PLAIN, 25));
        graphics.drawString("Game number: " + number, 20, 630);
        graphics.drawString("Card id: " + id, 300, 630);
        return bf;
    }
}
