import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSParser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileChoosePanel extends JPanel {
    final String inputXSD = "/home/s_n/Desktop/XML_Projekt/biblioteka.xsd";
    private Window window;
    private static Boolean documentOK = true;
    private JLabel error;
    private JButton btn;

    FileChoosePanel(Window window) {
        this.window = window;
        setLayout(null);
        setSize(window.getSize());

        JLabel label = new JLabel("Wybierz plik XML zgodny z biblioteka.xsd");
        label.setSize(300, 30);
        label.setLocation(window.getWidth() / 2 - label.getWidth() / 2, (int)(0.3 * window.getHeight()));
        add(label);

        btn = new JButton("<html><center>Wybierz<br/>plik</center></html>");
        btn.setSize(100, 40);
        btn.setLocation(window.getWidth() / 2 - btn.getWidth() / 2, label.getLocation().y + label.getHeight());
        btn.addActionListener(new ChooseFile());
        add(btn);

        error = new JLabel("");
        error.setSize(220, 30);
        error.setLocation(window.getWidth() / 2 - error.getWidth() / 2, btn.getLocation().y + btn.getHeight() + 10);
        add(error);

    }

    public class ChooseFile implements ActionListener {
        public void actionPerformed(ActionEvent action) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML", "xml");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(window);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                BibliotekaDOM.xmlFile = chooser.getSelectedFile().getPath();
                try {
                    System.setProperty(DOMImplementationRegistry.PROPERTY,"org.apache.xerces.dom.DOMXSImplementationSourceImpl");

                    DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
                    BibliotekaDOM.impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
                    LSParser builder  = BibliotekaDOM.impl.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
                    BibliotekaDOM.config = builder.getDomConfig();
                    DOMErrorHandler errorHandler = getErrorHandler();
                    BibliotekaDOM.config.setParameter("error-handler", errorHandler);
                    BibliotekaDOM.config.setParameter("validate", Boolean.TRUE);
                    BibliotekaDOM.config.setParameter("schema-type","http://www.w3.org/2001/XMLSchema");
                    BibliotekaDOM.config.setParameter("schema-location", inputXSD);
                    BibliotekaDOM.document = builder.parseURI(BibliotekaDOM.xmlFile);

                    if (documentOK)
                        window.setScene(new RentsPanel(window));
                    else
                        error.setText("Nieprawidłowy dokument XML");
                    documentOK = true;
                } catch(Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

    }

    public static DOMErrorHandler getErrorHandler() {
        return new DOMErrorHandler() {
            public boolean handleError(DOMError error) {
                short severity = error.getSeverity();
                if (severity == error.SEVERITY_ERROR) {
                    System.out.println("[dom3-error]: " + error.getMessage());
                    documentOK = false;
                }
                if (severity == error.SEVERITY_WARNING) {
                    documentOK = false;
                    System.out.println("[dom3-warning]: " + error.getMessage());
                }
                if (severity == error.SEVERITY_FATAL_ERROR) {
                    documentOK = false;
                    System.out.println("[dom3-fatal-error]: "
                            + error.getMessage());
                }
                return true;
            }
        };
    }
}
