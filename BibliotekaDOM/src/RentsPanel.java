import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class RentsPanel extends JPanel {
    private Window window;
    private Document document;
    RentsPanel(Window window) {
        this.window = window;
        document = BibliotekaDOM.document;
        setLayout(null);
        setSize(window.getSize());
        showData();

    }

    public void showData() {
        String columnNames[] = {"osoba", "ksiazka", "wypozyczenia", "termin", "zwrot"};

        NodeList rents = document.getElementsByTagName("wypozyczenie");
        Object[][] data = new Object[rents.getLength()][columnNames.length];


        for (int i = 0; i < rents.getLength(); i++) {
            NodeList rent = rents.item(i).getChildNodes();
            String userID = ((Element) rent).getAttributes().getNamedItem("czytelnik_id").getFirstChild().getNodeValue();
            String bookID = ((Element) rent).getAttributes().getNamedItem("ksiazka_id").getFirstChild().getNodeValue();
            String rentDate = ((Element) rent).getElementsByTagName("data_wypozyczenia").item(0).getFirstChild().getNodeValue();
            String returnTo = ((Element) rent).getElementsByTagName("data_do").item(0).getFirstChild().getNodeValue();
            String returnDate = "";
            NodeList returnD = ((Element) rent).getElementsByTagName("data_zwrotu");
            if (returnD.getLength() != 0)
                returnDate = returnD.item(0).getFirstChild().getNodeValue();

            String user = "", title = "";
            NodeList users = document.getElementsByTagName("czytelnik");
            for (int j = 0; j < users.getLength(); j++) {
                String id = ((Element)users.item(j)).getAttributes().getNamedItem("id").getFirstChild().getNodeValue();
                if (id.equals(userID)) {
                    user = ((Element)users.item(j)).getElementsByTagName("imie").item(0).getFirstChild().getNodeValue();
                    user += " " + ((Element)users.item(j)).getElementsByTagName("nazwisko").item(0).getFirstChild().getNodeValue();
                    break;
                }
            }

            NodeList books = document.getElementsByTagName("ksiazka");
            for (int j = 0; j < books.getLength(); j++) {
                String id = ((Element)books.item(j)).getAttributes().getNamedItem("id").getFirstChild().getNodeValue();
                if (id.equals(bookID)) {
                    title = ((Element)books.item(j)).getElementsByTagName("tytul").item(0).getFirstChild().getNodeValue();
                    break;
                }
            }

            Object[] row = {user + "[" + userID + "]", title + "[" + bookID + "]", rentDate, returnTo, returnDate};
            data[i] = row;
        }

        JTable table = new JTable(data, columnNames);

        table.getColumnModel().getColumn(2).setMaxWidth(100);
        table.getColumnModel().getColumn(2).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(4).setMaxWidth(100);
        table.getColumnModel().getColumn(4).setMinWidth(100);

        setLayout(new BorderLayout());
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);



        Menu menu = new Menu(window);
        add(menu, BorderLayout.PAGE_END);

        JButton addRent = new JButton("Dodaj wypozyczenie");
        menu.add(addRent);
    }

}