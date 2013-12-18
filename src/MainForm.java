import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * User: yifancai
 * Date: 12/16/13
 * Time: 5:20 PM
 */
public class MainForm extends JFrame {
    private JButton searchButton;
    private JTextField searchTextField;
    private JTable resultTable;
    private JPanel rootPanel;

    public MainForm() {
        super("Good Carry?");

        setContentPane(rootPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        searchTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                System.out.println("insert...");
                if (!searchButton.isEnabled())
                    searchButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                System.out.println("delete...");
                if (searchButton.isEnabled() && searchTextField.getDocument().getLength() == 0)
                    searchButton.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                System.out.println("change...");
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("click search...");

                IReporter reporter = new SimpleReporter();
                GameDataCollector collector = new GameDataCollector(reporter, searchTextField.getText());
                collector.run();
            }
        });
    }

    public static void main(String[] args) {
        MainForm mf = new MainForm();
        mf.setVisible(true);
    }
}
