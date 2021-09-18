import javax.swing.*;

public class BingoCards extends JFrame {
    private static final int WIDTH = 875;
    private static final int HEIGHT = 730;
    public static JLabel label = new JLabel("Bingo balls: ");
    public BingoCards (String s) {
        super(s);
        setResizable(true);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BingoCardsPanel a = new BingoCardsPanel();
        a.add(label);
        add(a);
        setVisible(true);
    }
}
