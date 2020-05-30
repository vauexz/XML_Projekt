import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddRentWindow extends JFrame {
    JFrame me;
    Window window;
    JTextField rentDate;
    JTextField returnTo;
    JTextField returnDate;
    JComboBox booksBox;
    JComboBox usersBox;

    AddRentWindow(Window window) {
        super("Dodaj wypożyczenie");
        this.me = this;
        this.window = window;
        setLayout(null);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        NodeList booksNode = BibliotekaDOM.document.getElementsByTagName("ksiazka");
        NodeList usersNode = BibliotekaDOM.document.getElementsByTagName("czytelnik");

        String[] books = new String[booksNode.getLength()];
        String[] users = new String[usersNode.getLength()];

        for (int i = 0; i < booksNode.getLength(); i++) {
            String title = ((Element)booksNode.item(i)).getElementsByTagName("tytul").item(0).getFirstChild().getNodeValue();
            books[i] = title;
        }
        for (int i = 0; i < usersNode.getLength(); i++) {
            String name = ((Element)usersNode.item(i)).getElementsByTagName("imie").item(0).getFirstChild().getNodeValue();
            String surname = ((Element)usersNode.item(i)).getElementsByTagName("nazwisko").item(0).getFirstChild().getNodeValue();
            users[i] = name + " " + surname;
        }

        JLabel bookLabel = new JLabel("Książka");
        bookLabel.setLocation(100, 20);
        bookLabel.setSize(100, 30);
        add(bookLabel);
        booksBox = new JComboBox(books);
        booksBox.setSelectedIndex(0);
        booksBox.setSize(200, 30);
        booksBox.setLocation(100, 50);
        add(booksBox);

        JLabel usersLabel = new JLabel("Osoba");
        usersLabel.setLocation(100, 90);
        usersLabel.setSize(100, 20);
        add(usersLabel);
        usersBox = new JComboBox(users);
        usersBox.setSelectedIndex(0);
        usersBox.setSize(200, 30);
        usersBox.setLocation(100, 110);
        add(usersBox);

        rentDate = new JTextField();
        rentDate.setSize(120, 30);
        rentDate.setLocation(10, 190);
        add(rentDate);

        returnTo = new JTextField();
        returnTo.setSize(120, 30);
        returnTo.setLocation(140, 190);
        add(returnTo);


        returnDate = new JTextField();
        returnDate.setSize(120, 30);
        returnDate.setLocation(270, 190);
        add(returnDate);


        JLabel rentDateLabel = new JLabel("Wypozyczenie*");
        rentDateLabel.setLocation(10, 160);
        rentDateLabel.setSize(120, 30);
        add(rentDateLabel);

        JLabel returnToLabel = new JLabel("Termin*");
        returnToLabel.setLocation(140, 160);
        returnToLabel.setSize(120, 30);
        add(returnToLabel);

        JLabel returnLabel = new JLabel("Data zwrotu");
        returnLabel.setLocation(270, 160);
        returnLabel.setSize(120, 30);
        add(returnLabel);

        JLabel info = new JLabel("Data w formacie RRRR-MM-DD");
        JButton addBtn = new JButton("Dodaj");
        addBtn.addActionListener(new AddRent());
        addBtn.setSize(80, 30);
        addBtn.setLocation(110, 300);
        add(addBtn);

        JButton cancelBtn = new JButton("Anuluj");
        cancelBtn.setLocation(210, 300);
        cancelBtn.setSize(80, 30);
        cancelBtn.addActionListener(new Cancel());
        add(cancelBtn);
    }
    public boolean checkData() {
        String rentDateS = rentDate.getText();
        String returnToS = returnTo.getText();
        String returnDateS = returnTo.getText();

        if (rentDateS.length() == 10) {
            int year = Integer.parseInt(rentDateS.substring(0, 4));
            int month = Integer.parseInt(rentDateS.substring(5, 7));
            int day = Integer.parseInt(rentDateS.substring(8, 10));
            if (rentDateS.charAt(4) != '-' || rentDateS.charAt(7) != '-')
                return false;
            if (year < 1970)
                return false;
            if (month < 1 || month > 12)
                return false;
            if (day < 1 || day > 31)
                return false;
        } else
            return false;

        if (returnToS.length() == 10) {
            int year = Integer.parseInt(returnToS.substring(0, 4));
            int month = Integer.parseInt(returnToS.substring(5, 7));
            int day = Integer.parseInt(returnToS.substring(8, 10));
            if (returnToS.charAt(4) != '-' || returnToS.charAt(7) != '-')
                return false;
            if (year < 1970)
                return false;
            if (month < 1 || month > 12)
                return false;
            if (day < 1 || day > 31)
                return false;
        } else
            return false;

        if (rentDateS.compareTo(returnToS) > 0)
            return false;

        if (returnDateS != "") {
            if (returnDateS.length() == 10) {
                int year = Integer.parseInt(returnDateS.substring(0, 4));
                int month = Integer.parseInt(returnDateS.substring(5, 7));
                int day = Integer.parseInt(returnDateS.substring(8, 10));
                if (returnDateS.charAt(4) != '-' || returnDateS.charAt(7) != '-')
                    return false;
                if (year < 1970)
                    return false;
                if (month < 1 || month > 12)
                    return false;
                if (day < 1 || day > 31)
                    return false;
            } else
                return false;

            if (rentDateS.compareTo(returnDateS) > 0)
                return false;
        }
        return true;

    }

    public class AddRent implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            if (checkData()) {

                String bookID = "", userID = "";

                NodeList booksNode = BibliotekaDOM.document.getElementsByTagName("ksiazka");
                NodeList usersNode = BibliotekaDOM.document.getElementsByTagName("czytelnik");

                for (int i = 0; i < booksNode.getLength(); i++) {
                    String title = ((Element)booksNode.item(i)).getElementsByTagName("tytul").item(0).getFirstChild().getNodeValue();
                    if (title.equals(booksBox.getSelectedItem().toString())) {
                        bookID = ((Element)booksNode.item(i)).getAttribute("id");
                        break;
                    }
                }
                for (int i = 0; i < usersNode.getLength(); i++) {
                    String name = ((Element)usersNode.item(i)).getElementsByTagName("imie").item(0).getFirstChild().getNodeValue();
                    String surname = ((Element)usersNode.item(i)).getElementsByTagName("nazwisko").item(0).getFirstChild().getNodeValue();
                    String data = name + " " + surname;
                    if (data.equals(usersBox.getSelectedItem().toString())) {
                        userID = ((Element)usersNode.item(i)).getAttribute("id");
                        break;
                    }
                }

                Element newRent = BibliotekaDOM.document.createElement("wypozyczenie");
                newRent.setAttribute("czytelnik_id", userID);
                newRent.setAttribute("ksiazka_id", bookID);

                Element rDate = BibliotekaDOM.document.createElement("data_wypozyczenia");
                rDate.appendChild(BibliotekaDOM.document.createTextNode(rentDate.getText()));
                newRent.appendChild(rDate);
                Element retToDate = BibliotekaDOM.document.createElement("data_do");
                retToDate.appendChild(BibliotekaDOM.document.createTextNode(returnTo.getText()));
                newRent.appendChild(retToDate);
                if (returnDate.getText().length() != 0) {
                    Element retDate = BibliotekaDOM.document.createElement("data_zwrotu");
                    retDate.appendChild(BibliotekaDOM.document.createTextNode(returnDate.getText()));
                    newRent.appendChild(retDate);
                }
                BibliotekaDOM.document.getElementsByTagName("wypozyczenia").item(0).appendChild(newRent);
                JOptionPane.showMessageDialog(window, "Wypozyczenie dodane");
                window.setScene(new RentsPanel(window));
                me.dispose();
            } else {
                JOptionPane.showMessageDialog(window, "Niepoprawne dane");
                me.setVisible(true);
            }
        }
    }

    public class Cancel implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            me.dispose();
        }
    }

}
