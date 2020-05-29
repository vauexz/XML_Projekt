import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RentsPanel extends JPanel {
    private Window window;
    private Document document;
    private JTable table;
    RentsPanel(Window window) {
        this.window = window;
        document = BibliotekaDOM.document;
        setLayout(null);
        setSize(window.getSize());
        showData();
    }

    public void showData() {
        String columnNames[] = {"osoba_id", "osoba", "ksiazka_id", "ksiazka", "wypozyczenie", "termin", "zwrot"};

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

            Object[] row = {userID, user, bookID, title, rentDate, returnTo, returnDate};
            data[i] = row;
        }

        table = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setMinWidth(80);
        table.getColumnModel().getColumn(0).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMaxWidth(80);
        table.getColumnModel().getColumn(2).setMinWidth(80);

        table.getColumnModel().getColumn(4).setMaxWidth(95);
        table.getColumnModel().getColumn(4).setMinWidth(95);
        table.getColumnModel().getColumn(5).setMaxWidth(95);
        table.getColumnModel().getColumn(5).setMinWidth(95);
        table.getColumnModel().getColumn(6).setMaxWidth(95);
        table.getColumnModel().getColumn(6).setMinWidth(95);
        table.setPreferredSize(new Dimension(window.getWidth() - 150, window.getHeight()));

        setLayout(new BorderLayout());
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);



        JButton del = new JButton("Usuń");
        del.addActionListener(new Delete());
        JButton edit = new JButton("Zmień");
        edit.addActionListener(new Edit());
        JButton add = new JButton("Dodaj");
        add.addActionListener(new Add());

        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension( 150,window.getHeight()));
        menu.add(add);
        menu.add(edit);
        menu.add(del);
        menu.setBackground(Color.WHITE);
        add(menu, BorderLayout.EAST);
        add(new Menu(window), BorderLayout.PAGE_END);
    }
    public class Delete implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            System.out.println("lecim");
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(window, "Wybierz wypozyczenie do usunięcia");
                return;
            }
            String userID = table.getValueAt(row, 0).toString();
            String bookID = table.getValueAt(row, 2).toString();
            String rDate = table.getValueAt(row, 4).toString();
            NodeList rents = document.getElementsByTagName("wypozyczenie");

            for (int i = 0; i < rents.getLength(); i++) {
                String u_id = ((Element)rents.item(i)).getAttribute("czytelnik_id");
                String b_id = ((Element)rents.item(i)).getAttribute("ksiazka_id");
                String r_date = ((Element)rents.item(i)).getElementsByTagName("data_wypozyczenia").item(0).getFirstChild().getNodeValue();

                if (userID.equals(u_id) && bookID.equals(b_id) && rDate.equals(r_date)) {
                    Node rent = rents.item(i);
                    rent.getParentNode().removeChild(rent);
                    JOptionPane.showMessageDialog(window, "Wypożyczenie usunięte");
                    window.setScene(new RentsPanel(window));
                    break;
                }
            }

        }
    }
    public class Edit implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            System.out.println("edytuj");
        }
    }
    public class Add implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            System.out.println("dodaj");
        }
    }
}