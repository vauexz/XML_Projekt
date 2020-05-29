
import javax.swing.*;

public class Window extends JFrame {

    private final int width = 900;
    private final int height = 700;
    private JPanel currentScene = null;

    public Window(String title) {
        super(title);
        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setScene(new FileChoosePanel(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void setScene(JPanel scene) {
        if (currentScene != null)
            remove(currentScene);
        currentScene = scene;
        add(scene);
        refresh();
    }

    public void refresh() {
        invalidate();
        validate();
        repaint();
    }

}
