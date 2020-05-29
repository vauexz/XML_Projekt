import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

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

        JTable table = new JTable(data, columnNames);
        table.getColumnModel().getColumn(0).setMinWidth(90);
        table.getColumnModel().getColumn(0).setMaxWidth(90);

        table.getColumnModel().getColumn(2).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setMaxWidth(70);

        table.setRowHeight(35);
        table.setFillsViewportHeight(true);

        setLayout(new BorderLayout());
        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);


        Menu menu = new Menu(window);
        add(menu, BorderLayout.PAGE_END);

        JButton addBook = new JButton("Dodaj książkę");
        menu.add(addBook);
    }
}
