import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class UsersPanel extends JPanel {
    private Window window;
    private Document document;

    UsersPanel(Window window) {
        this.window = window;
        document = BibliotekaDOM.document;
        setLayout(null);
        setSize(window.getSize());
        showData();

    }

    public void showData() {
        String columnNames[] = {"id", "osoba", "data urodzenia", "telefon", "email", "adres"};

        NodeList users = document.getElementsByTagName("czytelnik");
        Object[][] data = new Object[users.getLength()][columnNames.length];


        for (int i = 0; i < users.getLength(); i++) {
            NodeList user = users.item(i).getChildNodes();
            String userID = ((Element) user).getAttributes().getNamedItem("id").getFirstChild().getNodeValue();
            String name = ((Element) user).getElementsByTagName("imie").item(0).getFirstChild().getNodeValue();
            String surname = ((Element) user).getElementsByTagName("nazwisko").item(0).getFirstChild().getNodeValue();
            String birthDate = ((Element) user).getElementsByTagName("data_urodzenia").item(0).getFirstChild().getNodeValue();
            NodeList contactNode = ((Element) user).getElementsByTagName("kontakt");

            Object[] row = {userID, name + " " + surname, birthDate, "1111", "1111", "aaaa"};
            data[i] = row;
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        table.setMaximumSize(new Dimension(700, 300));
        setLayout(new BorderLayout());
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);
        TableColumn column = null;

        add(new Menu(window), BorderLayout.PAGE_END);
    }
}
