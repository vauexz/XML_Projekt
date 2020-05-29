import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BooksPanel extends JPanel {
    private Window window;
    private Document document;

    BooksPanel(Window window) {
        this.window = window;
        document = BibliotekaDOM.document;
        setLayout(null);
        setSize(window.getSize());
        showData();

    }

    public void showData() {
        String columnNames[] = {"id", "tytul", "rok", "autor", "wydawnictwo", "kategoria"};

        NodeList books = document.getElementsByTagName("ksiazka");
        Object[][] data = new Object[books.getLength()][columnNames.length];


        for (int i = 0; i < books.getLength(); i++) {
            NodeList book = books.item(i).getChildNodes();
            String bookID = ((Element) book).getAttributes().getNamedItem("id").getFirstChild().getNodeValue();
            String title = ((Element) book).getElementsByTagName("tytul").item(0).getFirstChild().getNodeValue();
            String year = ((Element) book).getElementsByTagName("rok_wydania").item(0).getFirstChild().getNodeValue();
            String pHouse = ((Element) book).getElementsByTagName("wydawnictwo").item(0).getFirstChild().getNodeValue();
            String category = ((Element) book).getElementsByTagName("kategoria").item(0).getFirstChild().getNodeValue();

            NodeList authorNode = ((Element) book).getElementsByTagName("autor");
            String author = "<html>" + authorNode.item(0).getFirstChild().getNodeValue();
            for (int j = 1; j < authorNode.getLength(); j++)
                author += "<br/>" + authorNode.item(j).getFirstChild().getNodeValue();
            author += "</html>";

            Object[] row = {bookID, title, year, author, pHouse, category};
            data[i] = row;
        }

        JTable table = new JTable(data, columnNames) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setMinWidth(90);
        table.getColumnModel().getColumn(0).setMaxWidth(90);

        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setMaxWidth(70);

        table.setRowHeight(35);
        table.setFillsViewportHeight(true);

        setLayout(new BorderLayout());
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);
        table.setPreferredSize(new Dimension(window.getWidth() - 150, window.getHeight()));

        JButton del = new JButton("Usuń");
        del.addActionListener(new Delete());
        JButton edit = new JButton("Zmień");
        del.addActionListener(new Edit());
        JButton add = new JButton("Dodaj");
        del.addActionListener(new Add());

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
            System.out.println("usun");
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