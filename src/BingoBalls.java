import java.util.ArrayList;
import java.util.Random;

public class BingoBalls {
    private static ArrayList<Integer> balls;
    private static ArrayList<Integer> bingoNumbers = new ArrayList<>();
    private static Random rand;
    public BingoBalls (Random r) {
        balls = new ArrayList<>();
        for (int i = 1; i <= 75; i++) {
            balls.add(i);
        }
        rand = r;
    }
    public int getBall () {
        if (balls.size() >= 1) {
            int i = balls.remove(rand.nextInt(balls.size()));
            bingoNumbers.add(i);
            return i;
        } else {
            return -1;
        }
    }
    public static ArrayList<Integer> getNums () {
        return bingoNumbers;
    }
}
