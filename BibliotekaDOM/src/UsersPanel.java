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

            NodeList contactNode = ((Element)user).getElementsByTagName("kontakt");
            String email = ((Element)contactNode.item(0)).getElementsByTagName("email").item(0).getFirstChild().getNodeValue();
            String phone = ((Element)contactNode.item(0)).getElementsByTagName("telefon").item(0).getFirstChild().getNodeValue();

            NodeList addressNode = ((Element)user).getElementsByTagName("adres");
            String city = ((Element)addressNode.item(0)).getElementsByTagName("miasto").item(0).getFirstChild().getNodeValue();
            String postalCode = ((Element)addressNode.item(0)).getElementsByTagName("miasto").item(0).getAttributes().getNamedItem("kod_pocztowy").getFirstChild().getNodeValue();
            String street = ((Element)addressNode.item(0)).getElementsByTagName("ulica").item(0).getFirstChild().getNodeValue();
            String hNumber = ((Element)addressNode.item(0)).getElementsByTagName("nr_domu").item(0).getFirstChild().getNodeValue();

            NodeList aNumberN = ((Element)addressNode.item(0)).getElementsByTagName("nr_mieszkania");
            Integer aNumber = null;
            if (aNumberN.getLength() != 0)
                aNumber = Integer.parseInt(aNumberN.item(0).getFirstChild().getNodeValue());


            String address = "<html>" + postalCode + " " + city + ", <br/>" + street + " " + hNumber + (aNumber != null ? "/" + aNumber : "") + "</html>";

            Object[] row = {userID, name + " " + surname, birthDate, phone, email, address};
            data[i] = row;
        }

        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setMinWidth(90);
        table.getColumnModel().getColumn(0).setMaxWidth(90);
        table.getColumnModel().getColumn(1).setMinWidth(140);
        table.getColumnModel().getColumn(1).setMaxWidth(140);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(2).setMaxWidth(110);
        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.setRowHeight(35);
        table.setFillsViewportHeight(true);

        setLayout(new BorderLayout());
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);

        JButton del = new JButton("Usuń");
        JButton edit = new JButton("Zmień");


        Menu menu = new Menu(window);
        add(menu, BorderLayout.PAGE_END);

        JButton addUser = new JButton("Dodaj osobę");
        menu.add(addUser);
    }
}
