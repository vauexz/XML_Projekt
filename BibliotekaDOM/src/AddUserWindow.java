import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AddUserWindow extends JFrame {

    JFrame me;
    Window window;
    JTextField nameField;
    JTextField surnameField;
    JTextField emailField;
    JTextField phoneField;
    JTextField birthDateField;
    JTextField postalCodeField;
    JTextField cityField;
    JTextField streetField;
    JTextField hNumberField;
    JTextField aNumberField;
    JTextField genderField;


    AddUserWindow(Window window) {
        super("Dodaj osobe");
        this.me = this;
        this.window = window;
        setLayout(null);
        setSize(450, 550);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

        JLabel namelabel = new JLabel("Imie");
        namelabel.setSize(100, 30);
        namelabel.setLocation(30, 20);
        nameField = new JTextField();
        nameField.setSize(100, 30);
        nameField.setLocation(namelabel.getLocation().x, namelabel.getLocation().y + 30);
        add(namelabel);
        add(nameField);

        JLabel surnamelabel = new JLabel("Nazwisko");
        surnamelabel.setSize(100, 30);
        surnamelabel.setLocation(30, 80);
        surnameField = new JTextField();
        surnameField.setSize(100, 30);
        surnameField.setLocation(surnamelabel.getLocation().x, surnamelabel.getLocation().y + 30);
        add(surnamelabel);
        add(surnameField);

        JLabel emaillabel = new JLabel("Email");
        emaillabel.setSize(100, 30);
        emaillabel.setLocation(30, 140);
        emailField = new JTextField();
        emailField.setSize(100, 30);
        emailField.setLocation(emaillabel.getLocation().x, emaillabel.getLocation().y + 30);
        add(emaillabel);
        add(emailField);

        JLabel phonelabel = new JLabel("Telefon");
        phonelabel.setSize(100, 30);
        phonelabel.setLocation(30, 200);
        phoneField = new JTextField();
        phoneField.setSize(100, 30);
        phoneField.setLocation(phonelabel.getLocation().x, phonelabel.getLocation().y + 30);
        add(phonelabel);
        add(phoneField);

        JLabel datelabel = new JLabel("Data urodzenia");
        datelabel.setSize(200, 30);
        datelabel.setLocation(30, 260);
        birthDateField = new JTextField();
        birthDateField.setSize(100, 30);
        birthDateField.setLocation(datelabel.getLocation().x, datelabel.getLocation().y + 30);
        add(datelabel);
        add(birthDateField);

        JLabel postallabel = new JLabel("Kod pocztowy");
        postallabel.setSize(100, 30);
        postallabel.setLocation(150, 20);
        postalCodeField = new JTextField();
        postalCodeField.setSize(80, 30);
        postalCodeField.setLocation(postallabel.getLocation().x, postallabel.getLocation().y + 30);
        add(postallabel);
        add(postalCodeField);

        JLabel citylabel = new JLabel("Miasto");
        citylabel.setSize(100, 30);
        citylabel.setLocation(270, 20);
        cityField = new JTextField();
        cityField.setSize(100, 30);
        cityField.setLocation(citylabel.getLocation().x, citylabel.getLocation().y + 30);
        add(citylabel);
        add(cityField);

        JLabel streetlabel = new JLabel("Ulica");
        streetlabel.setSize(100, 30);
        streetlabel.setLocation(150, 80);
        streetField = new JTextField();
        streetField.setSize(100, 30);
        streetField.setLocation(streetlabel.getLocation().x, streetlabel.getLocation().y + 30);
        add(streetlabel);
        add(streetField);

        JLabel hlabel = new JLabel("nr domu");
        hlabel.setSize(100, 30);
        hlabel.setLocation(255, 80);
        hNumberField = new JTextField();
        hNumberField.setSize(60, 30);
        hNumberField.setLocation(hlabel.getLocation().x, hlabel.getLocation().y + 30);
        add(hlabel);
        add(hNumberField);

        JLabel alabel = new JLabel("nr mieszkania");
        alabel.setSize(100, 30);
        alabel.setLocation(320, 80);
        aNumberField = new JTextField();
        aNumberField.setSize(60, 30);
        aNumberField.setLocation(alabel.getLocation().x, alabel.getLocation().y + 30);
        add(alabel);
        add(aNumberField);

        JLabel genderLabel = new JLabel("k / m");
        genderLabel.setSize(100, 30);
        genderLabel.setLocation(30, 320);
        genderField = new JTextField();
        genderField.setSize(100, 30);
        genderField.setLocation(genderLabel.getLocation().x, genderLabel.getLocation().y + 30);
        add(genderLabel);
        add(genderField);

        JButton addBtn = new JButton("OK");
        addBtn.addActionListener(new AddUser());
        addBtn.setSize(80, 30);
        addBtn.setLocation(120, 450);
        add(addBtn);

        JButton cancelBtn = new JButton("Anuluj");
        cancelBtn.setLocation(250, 450);
        cancelBtn.setSize(80, 30);
        cancelBtn.addActionListener(new Cancel());
        add(cancelBtn);

    }

    public class AddUser implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            if (!checkData()) {
                JOptionPane.showMessageDialog(window, "Niepoprawne dane");
                me.setVisible(true);
                return;
            }
            String name = nameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String postalCode = postalCodeField.getText();
            String city = cityField.getText();
            String street = streetField.getText();
            String bDate = birthDateField.getText();
            String gender = genderField.getText().toLowerCase();
            String hNumber = hNumberField.getText();
            String aNumber = aNumberField.getText();

            Element user = BibliotekaDOM.document.createElement("czytelnik");
            user.setAttribute("id", generateUserID());

            Element nam = BibliotekaDOM.document.createElement("imie");
            nam.setAttribute("plec", gender);
            nam.appendChild(BibliotekaDOM.document.createTextNode(name));
            user.appendChild(nam);

            Element surnam = BibliotekaDOM.document.createElement("nazwisko");
            surnam.appendChild(BibliotekaDOM.document.createTextNode(surname));
            user.appendChild(surnam);

            Element address = BibliotekaDOM.document.createElement("adres");

            Element cit =  BibliotekaDOM.document.createElement("miasto");
            cit.setAttribute("kod_pocztowy", postalCode);
            cit.appendChild(BibliotekaDOM.document.createTextNode(city));
            address.appendChild(cit);

            Element stree =  BibliotekaDOM.document.createElement("ulica");
            stree.appendChild(BibliotekaDOM.document.createTextNode(street));
            address.appendChild(stree);

            Element house =  BibliotekaDOM.document.createElement("nr_domu");
            house.appendChild(BibliotekaDOM.document.createTextNode(hNumber));
            address.appendChild(house);

            if (aNumber.length() != 0) {
                Element apart =  BibliotekaDOM.document.createElement("nr_mieszkania");
                apart.appendChild(BibliotekaDOM.document.createTextNode(aNumber));
                address.appendChild(apart);
            }
            user.appendChild(address);

            Element contact = BibliotekaDOM.document.createElement("kontakt");

            Element emai =  BibliotekaDOM.document.createElement("email");
            emai.appendChild(BibliotekaDOM.document.createTextNode(email));
            contact.appendChild(emai);

            Element pho =  BibliotekaDOM.document.createElement("telefon");
            pho.appendChild(BibliotekaDOM.document.createTextNode(phone));
            contact.appendChild(pho);
            user.appendChild(contact);

            Element birth =  BibliotekaDOM.document.createElement("data_urodzenia");
            birth.appendChild(BibliotekaDOM.document.createTextNode(bDate));
            contact.appendChild(birth);
            user.appendChild(birth);

            BibliotekaDOM.document.getElementsByTagName("czytelnicy").item(0).appendChild(user);
            JOptionPane.showMessageDialog(window, "Osoba dodana");
            window.setScene(new UsersPanel(window));
        }
    }

    public class Cancel implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            me.dispose();
        }
    }

    boolean checkData() {

        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String postalCode = postalCodeField.getText();
        String city = cityField.getText();
        String street = streetField.getText();
        String bDate = birthDateField.getText();
        String gender = genderField.getText().toLowerCase();
        String hNumber = hNumberField.getText();
        String aNumber = aNumberField.getText();

        if (aNumber.length() != 0)
            try {
                Integer.parseInt(aNumber);
            } catch (Exception e) {
                return false;
            }

        try {
            Integer.parseInt(hNumber);
        } catch (Exception e) {
            return false;
        }

        if (gender.equals("k") || gender.equals("m"))
            ;
        else
            return false;

        if (street.length() < 1)
            return false;

        if (city.length() < 1)
            return false;

        if (name.length() < 1)
            return false;

        if (surname.length() < 1)
            return false;

        if (!phone.matches("[0-9]{9,11}"))
            return false;

        if (!postalCode.matches("\\d{2}-\\d{3}"))
            return false;

        if (!email.matches("\\w+@\\w+\\.\\w+"))
            return false;

        if (bDate.length() == 10) {
            int year = Integer.parseInt(bDate.substring(0, 4));
            int month = Integer.parseInt(bDate.substring(5, 7));
            int day = Integer.parseInt(bDate.substring(8, 10));
            if (bDate.charAt(4) != '-' || bDate.charAt(7) != '-')
                return false;
            if (year < 1970)
                return false;
            if (month < 1 || month > 12)
                return false;
            if (day < 1 || day > 31)
                return false;
        } else
            return false;

        return true;
    }

    public String generateUserID() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String ALPHABET = alphabet.toUpperCase();
        Random r = new Random();
        String ID = "C";
        ID += alphabet.charAt(r.nextInt(alphabet.length()));
        ID += "_";
        ID += ((Integer)r.nextInt(10)).toString();
        ID += ((Integer)r.nextInt(10)).toString();
        ID += ((Integer)r.nextInt(10)).toString();
        ID += ((Integer)r.nextInt(10)).toString();
        ID += ALPHABET.charAt(r.nextInt(ALPHABET.length()));
        ID += alphabet.charAt(r.nextInt(alphabet.length()));
        return ID;
    }
}
