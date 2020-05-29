import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.stream.events.Attribute;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.BeanInfo;

public class EditRentWindow extends JFrame {
    JFrame me;
    Window window;
    String bookID;
    String userID;
    JTextField rentDate;
    JTextField returnTo;
    JTextField returnDate;

    EditRentWindow(Window window, String userID, String bookID, String _rentDate, String _dateTo, String _returnDate) {
        super("Edytuj wypożyczenie");
        this.userID = userID;
        this.bookID = bookID;
        this.me = this;
        this.window = window;
        setLayout(null);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        NodeList booksNode = BibliotekaDOM.document.getElementsByTagName("ksiazka");
        NodeList usersNode = BibliotekaDOM.document.getElementsByTagName("czytelnik");

        String userName = "", bookName = "";

        for (int i = 0; i < booksNode.getLength(); i++) {
            if (((Element)booksNode.item(i)).getAttribute("id").toString().equals(bookID))
                bookName = ((Element)booksNode.item(i)).getElementsByTagName("tytul").item(0).getFirstChild().getNodeValue();
        }
        for (int i = 0; i < booksNode.getLength(); i++) {
            if (((Element)usersNode.item(i)).getAttribute("id").toString().equals(userID)) {
                userName = ((Element) usersNode.item(i)).getElementsByTagName("imie").item(0).getFirstChild().getNodeValue();
                userName += " " + ((Element) usersNode.item(i)).getElementsByTagName("nazwisko").item(0).getFirstChild().getNodeValue();

            }
        }

        JLabel bookLabel = new JLabel("Książka");
        bookLabel.setLocation(100, 20);
        bookLabel.setSize(100, 30);
        add(bookLabel);
        JLabel book = new JLabel(bookName);
        book.setSize(200, 30);
        book.setLocation(100, 50);
        add(book);

        JLabel usersLabel = new JLabel("Osoba");
        usersLabel.setLocation(100, 90);
        usersLabel.setSize(100, 20);
        add(usersLabel);
        JLabel user = new JLabel(userName);
        user.setSize(200, 30);
        user.setLocation(100, 110);
        add(user);

        rentDate = new JTextField();
        rentDate.setSize(120, 30);
        rentDate.setLocation(10, 190);
        add(rentDate);
        rentDate.setText(_rentDate);

        returnTo = new JTextField();
        returnTo.setSize(120, 30);
        returnTo.setLocation(140, 190);
        add(returnTo);
        returnTo.setText(_dateTo);

        returnDate = new JTextField();
        returnDate.setSize(120, 30);
        returnDate.setLocation(270, 190);
        add(returnDate);
        returnDate.setText(_returnDate);


        JLabel rentDateLabel = new JLabel("Wypozyczenie*");
        rentDateLabel.setLocation(10, 160);
        rentDateLabel.setSize(120, 30);
        add(rentDateLabel);

        JLabel returnToLabel = new JLabel("Termin*");
        returnToLabel.setLocation(140, 160);
        returnToLabel.setSize(120, 30);
        add(returnToLabel);

        JLabel returnLabel = new JLabel("Data zwrotu*");
        returnLabel.setLocation(270, 160);
        returnLabel.setSize(120, 30);
        add(returnLabel);

        JLabel info = new JLabel("Data w formacie RRRR-MM-DD");
        JButton editBtn = new JButton("OK");
        editBtn.addActionListener(new EditRent());
        editBtn.setSize(80, 30);
        editBtn.setLocation(110, 300);
        add(editBtn);

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

    public class EditRent implements ActionListener {
        public void actionPerformed(ActionEvent action) {
           if (checkData()) {
                NodeList rents = (BibliotekaDOM.document.getElementsByTagName("wypozyczenie"));

                for (int i = 0; i < rents.getLength(); i++) {
                    if (bookID.equals(((Element)(rents.item(i))).getAttribute("ksiazka_id").toString())
                            && userID.equals(((Element)(rents.item(i))).getAttribute("czytelnik_id").toString())) {

                        Element rent = (Element)rents.item(i);
                        ((Element)rent.getElementsByTagName("data_wypozyczenia").item(0)).setTextContent(rentDate.getText());
                        ((Element)rent.getElementsByTagName("data_do").item(0)).setTextContent(returnTo.getText());

                        NodeList returnNode = rent.getElementsByTagName("data_zwrotu");
                        String returnS = returnDate.getText();
                        if (returnNode.getLength() == 0 && returnS.length() == 0)
                            ; // nie dodalismy daty ani nie mamy węzła w pliku
                        else if (returnNode.getLength() == 0 && returnS.length() != 0) {
                            Element newDate = BibliotekaDOM.document.createElement("data_zwrotu");
                            newDate.setTextContent(returnS);
                            rent.appendChild(newDate);
                        } else if (returnNode.getLength() != 0 && returnS.length() == 0) {
                            rent.removeChild(rent.getElementsByTagName("data_zwrotu").item(0));
                        } else {
                            rent.getElementsByTagName("data_zwrotu").item(0).setTextContent(returnS);
                        }
                    }
                }

                JOptionPane.showMessageDialog(window, "Wypozyczenie zmienione");
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
