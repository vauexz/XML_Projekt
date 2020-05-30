import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsersPanel extends JPanel {
    private Window window;
    private Document document;
    private JTable table;

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

        table = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        add(table, BorderLayout.WEST);
        table.setPreferredSize(new Dimension(window.getWidth() - 150, window.getHeight()));

        JButton del = new JButton("Usuń");
        del.addActionListener(new Delete());
        JButton add = new JButton("Dodaj");
        add.addActionListener(new Add());

        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension( 150,window.getHeight()));
        menu.add(add);
        menu.add(del);
        menu.setBackground(Color.WHITE);
        add(menu, BorderLayout.EAST);
        add(new Menu(window), BorderLayout.PAGE_END);
    }
    public class Delete implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(window, "Wybierz osobe do usunięcia");
                return;
            }
            String id = table.getValueAt(row, 0).toString();
            NodeList users = document.getElementsByTagName("czytelnik");
            for (int i = 0; i < users.getLength(); i++) {
                String u_id = ((Element)users.item(i)).getAttribute("id");
                if (u_id.equals(id)) {
                    boolean canBeDel = true;
                    NodeList rents = document.getElementsByTagName("wypozyczenie");
                    for (int j = 0; j < rents.getLength();j++) {
                        String userID = ((Element) rents.item(j)).getAttributes().getNamedItem("czytelnik_id").getFirstChild().getNodeValue();
                        if (userID.equals(u_id)) {
                            canBeDel = false;
                            break;
                        }
                    }
                    if (canBeDel) {
                        Node user = users.item(i);
                        user.getParentNode().removeChild(user);
                        JOptionPane.showMessageDialog(window, "Osoba usunięta");
                        window.setScene(new UsersPanel(window));

                    } else {
                        JOptionPane.showMessageDialog(window, "Nie można usunąć osoby powiązanej z wypożyczeniem");
                    }
                }
            }
        }
    }
    public class Add implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            new AddUserWindow(window);
        }
    }
}
