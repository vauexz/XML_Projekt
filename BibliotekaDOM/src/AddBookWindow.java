import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.Random;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookWindow extends JFrame {
    JFrame me;
    Element book;
    Window window;
    Boolean edit = false;

    JTextField titleField;
    JTextField yearField;
    JTextField pagesField;
    JTextField authorsField;
    JComboBox typeField;

    String[] bookTypes = {
            "Kryminał", "Horror", "Przygodowe", "Opowiadania","Poezja", "Historyczne"
    };
    JTextField pHouseField;

    public AddBookWindow(Window window) {  // dodanie książki
        super("Dodaj książkę");
        this.me = this;
        this.window = window;
        setLayout(null);
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel titleL = new JLabel("Tytul");
        JLabel authorsL = new JLabel("Autor");
        JLabel yearL = new JLabel("Rok wydania");
        JLabel pagesL = new JLabel("Liczba stron");
        JLabel pHouseL = new JLabel("Wydawnictwo");
        titleField = new JTextField();
        pHouseField = new JTextField();
        authorsField = new JTextField();
        pagesField = new JTextField();
        yearField = new JTextField();
        typeField = new JComboBox();


        titleL.setSize(100, 30);
        authorsL.setSize(300, 30);
        yearL.setSize(100, 30);
        pagesL.setSize(100, 30);
        pHouseL.setSize(100, 30);

        titleL.setLocation(20, 20);
        add(titleL);
        titleField.setSize(200, 30);
        titleField.setLocation(20, 50);
        add(titleField);

        authorsL.setLocation(20, 90);
        add(authorsL);
        authorsField.setSize(200, 30);
        authorsField.setLocation(20, 120);
        add(authorsField);

        pHouseL.setLocation(20, 160);
        add(pHouseL);
        pHouseField.setSize(200, 30);
        pHouseField.setLocation(20, 190);
        add(pHouseField);

        for (int i = 0; i < bookTypes.length; i++)
            typeField.addItem(bookTypes[i]);
        typeField.setSize(200, 30);
        typeField.setLocation(20, 240);
        add(typeField);

        yearL.setLocation(240, 20);
        add(yearL);
        yearField.setSize(100, 30);
        yearField.setLocation(240, 50);
        add(yearField);

        pagesL.setLocation(240, 90);
        add(pagesL);
        pagesField.setSize(100, 30);
        pagesField.setLocation(240, 120);
        add(pagesField);

        JButton addBtn = new JButton("OK");
        addBtn.addActionListener(new AddBook());
        addBtn.setSize(80, 30);
        addBtn.setLocation(110, 300);
        add(addBtn);

        JButton cancelBtn = new JButton("Anuluj");
        cancelBtn.setLocation(210, 300);
        cancelBtn.setSize(80, 30);
        cancelBtn.addActionListener(new Cancel());
        add(cancelBtn);
    }

    public AddBookWindow(Window window, String bookID) { // edycja książki
        this(window);
        edit = true;
        me.setTitle("Modyfikacja książki");
        NodeList books = BibliotekaDOM.document.getElementsByTagName("ksiazka");
        for (int i = 0; i < books.getLength(); i++) {
            if (((Element)(books.item(i))).getAttribute("id").equals(bookID)) {
                this.book = (Element)books.item(i);
                break;
            }
        }
        titleField.setText(book.getElementsByTagName("tytul").item(0).getFirstChild().getNodeValue());
        pHouseField.setText(book.getElementsByTagName("wydawnictwo").item(0).getFirstChild().getNodeValue());
        yearField.setText(book.getElementsByTagName("rok_wydania").item(0).getFirstChild().getNodeValue());
        pagesField.setText(book.getElementsByTagName("liczba_stron").item(0).getFirstChild().getNodeValue());

        NodeList authorsNode =  book.getElementsByTagName("autor");
        String authors = authorsNode.item(0).getFirstChild().getNodeValue();

        for (int i = 1; i < authorsNode.getLength(); i++)
            authors += ", " + authorsNode.item(i).getFirstChild().getNodeValue();
        authorsField.setText(authors);

        int j = 0;
        for (; j < bookTypes.length; j++) {
            if (bookTypes[j].equals(book.getElementsByTagName("kategoria").item(0).getFirstChild().getNodeValue()))
                break;
        }
        typeField.setSelectedIndex(j);
    }



    public class AddBook implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            if (!checkData()) {
                JOptionPane.showMessageDialog(window, "Niepoprawne dane");
                me.setVisible(true);
                return;
            }
            if (edit) {
                book.getElementsByTagName("tytul").item(0).setTextContent(titleField.getText());
                book.getElementsByTagName("wydawnictwo").item(0).setTextContent(pHouseField.getText());
                book.getElementsByTagName("rok_wydania").item(0).setTextContent(yearField.getText());
                book.getElementsByTagName("liczba_stron").item(0).setTextContent(pagesField.getText());
                book.getElementsByTagName("autor").item(0).setTextContent(authorsField.getText());
                book.getElementsByTagName("kategoria").item(0).setTextContent(bookTypes[typeField.getSelectedIndex()]);
                JOptionPane.showMessageDialog(window, "Dane zmienione");
                window.setScene(new BooksPanel(window));
                me.dispose();
            } else {
                Element newBook = BibliotekaDOM.document.createElement("ksiazka");
                newBook.setAttribute("id", generateBookID());

                Element title = BibliotekaDOM.document.createElement("tytul");
                title.appendChild(BibliotekaDOM.document.createTextNode(titleField.getText()));
                newBook.appendChild(title);

                Element author = BibliotekaDOM.document.createElement("autor");
                author.appendChild(BibliotekaDOM.document.createTextNode(authorsField.getText()));
                newBook.appendChild(author);

                Element year = BibliotekaDOM.document.createElement("rok_wydania");
                year.appendChild(BibliotekaDOM.document.createTextNode(yearField.getText()));
                newBook.appendChild(year);

                Element pages = BibliotekaDOM.document.createElement("liczba_stron");
                pages.appendChild(BibliotekaDOM.document.createTextNode(pagesField.getText()));
                newBook.appendChild(pages);

                Element pHouse = BibliotekaDOM.document.createElement("wydawnictwo");
                pHouse.appendChild(BibliotekaDOM.document.createTextNode(pHouseField.getText()));
                newBook.appendChild(pHouse);

                Element cat = BibliotekaDOM.document.createElement("kategoria");
                cat.appendChild(BibliotekaDOM.document.createTextNode(bookTypes[typeField.getSelectedIndex()]));
                newBook.appendChild(cat);

                NodeList books = BibliotekaDOM.document.getElementsByTagName("ksiazki");
                books.item(0).appendChild(newBook);
                JOptionPane.showMessageDialog(window, "Dane dodane");
                window.setScene(new BooksPanel(window));
                me.dispose();
            }

        }
    }

    public boolean checkData() {
        if (authorsField.getText().length() < 1)
            return false;
        if (titleField.getText().length() < 1)
            return false;
        if (pHouseField.getText().length() < 1)
            return false;
        if (yearField.getText().length() < 1)
            return false;
        if (pagesField.getText().length() < 1)
            return false;
        try {
            Integer.parseInt(yearField.getText());
            Integer.parseInt(pagesField.getText());
        } catch (Exception e) {
            return false;
        }
        return true;
    }



    public String generateBookID() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random r = new Random();
        String ID = "K_";
        ID += ((Integer)r.nextInt(10)).toString();
        ID += ((Integer)r.nextInt(10)).toString();
        ID += alphabet.charAt(r.nextInt(alphabet.length()));
        ID += ((Integer)r.nextInt(10)).toString();
        ID += ((Integer)r.nextInt(10)).toString();
        return ID;
    }

    public class Cancel implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            me.dispose();
        }
    }





}
