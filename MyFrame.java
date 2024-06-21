import java.awt.Color;
import javax.swing.*;

public class MyFrame extends JFrame {
    private int W, H;

    public MyFrame() {
        super();
        H = 800;
        W = 500;
        setWindowSize(W, H);
    }

    public MyFrame(int width, int height) {
        super();
        setWindowSize(width, height);
    }

    public MyFrame(String title, int width, int height) {
        super(title);
        setWindowSize(width, height);
    }

    public MyFrame(String title, int width, int height, boolean visible) {
        super(title);
        setWindowSize(width, height);
        setVisible(visible);
    }

    public void setWindowSize(int width, int height) {
        H = height;
        W = width;
        setSize(width, height);
    }

    public void setMyFrame(String title, int width, int height) {
        setTitle(title);
        setWindowSize(width, height);
    }

    public void setMyFrame(String title, int width, int height, boolean visible) {
        setMyFrame(title, width, height);
        setVisible(visible);
    }

    public void setMyFrame(String title, int width, int height, boolean visible, int closeOperation) {
        setMyFrame(title, width, height, visible);
        setDefaultCloseOperation(closeOperation);
    }

    public void setMyFrame(String title, int width, int height, boolean visible, int closeOperation,
            boolean resizable) {
        setMyFrame(title, width, height, visible, closeOperation);
        setResizable(resizable);
    }

    public void setBackgroundColor(int red, int green, int blue, int opacity) {
        getContentPane().setBackground(new Color(red, green, blue, opacity));
    }

    public JPanel setBackgroundImage(String file) {
        JPanel panelBG = new JPanel();
        JLabel img = new JLabel(new ImageIcon(file));
        panelBG.add(img);
        return panelBG;
    }
}
