import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

public class Menu extends JPanel {

    Window window;
    Menu(Window window) {
        this.window = window;
        Dimension btnSize = new Dimension(150, 50);
        Point btnPosition = new Point((int)(window.getWidth() - 1.2 * btnSize.getWidth()), 30);

        JButton usersBtn = new JButton("Czytelnicy");
        usersBtn.setSize(btnSize);
        usersBtn.setLocation(btnPosition);
        add(usersBtn);
        usersBtn.addActionListener(new Users());
        btnPosition.y += btnSize.getHeight() + 20;

        JButton booksBtn = new JButton("Książki");
        booksBtn.setSize(btnSize);
        booksBtn.setLocation(btnPosition);
        add(booksBtn);
        booksBtn.addActionListener(new Books());
        btnPosition.y += btnSize.getHeight() + 20;

        JButton rentsBtn = new JButton("Wypożyczenia");
        rentsBtn.setSize(btnSize);
        rentsBtn.setLocation(btnPosition);
        add(rentsBtn);
        rentsBtn.addActionListener(new Rents());
        btnPosition.y += btnSize.getHeight() + 20;

        JButton saveBtn = new JButton("Zapisz zmiany");
        saveBtn.setSize(btnSize);
        saveBtn.setLocation(btnPosition);
        add(saveBtn);
        saveBtn.addActionListener(new Save());
        btnPosition.y += btnSize.getHeight() + 20;
    }

    public class Users implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            window.setScene(new UsersPanel(window));
        }
    }

    public class Rents implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            window.setScene(new RentsPanel(window));
        }
    }

    public class Books implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            window.setScene(new BooksPanel(window));
        }
    }

    public class Save implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            try {
                LSSerializer domWriter = BibliotekaDOM.impl.createLSSerializer();
                BibliotekaDOM.config = domWriter.getDomConfig();
                BibliotekaDOM.config.setParameter("xml-declaration", Boolean.TRUE);

                LSOutput dOut = BibliotekaDOM.impl.createLSOutput();
                dOut.setEncoding("latin2");
                dOut.setByteStream(new FileOutputStream(BibliotekaDOM.xmlFile));

                domWriter.write(BibliotekaDOM.document, dOut);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
