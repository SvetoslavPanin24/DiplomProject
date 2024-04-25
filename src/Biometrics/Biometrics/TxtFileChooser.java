package Biometrics;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalFileChooserUI;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public final class TxtFileChooser implements FocusListener, PropertyChangeListener {
    JFrame frame = new JFrame();

    public JButton txtFileChooserButton;

    JLayeredPane mainPane;
    JTextArea textArea;

    private final JFileChooser chooser = new JFileChooser() {
        protected JDialog createDialog(Component parent) throws HeadlessException {
            JDialog dialog = super.createDialog(parent);
            dialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
            return dialog;
        }
    };
    private final MyChooserUI myUI = new MyChooserUI(chooser);
    private final KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");


    public TxtFileChooser(JLayeredPane mainPane, JButton txtFileChooserButton, JTextArea textArea) {
        this.mainPane = mainPane;
        this.txtFileChooserButton = txtFileChooserButton;

        this.textArea = textArea;
        chooser.setCurrentDirectory(new File("src/Encrypt"));
        chooser.setFileFilter(filter);
        myUI.installUI(chooser);

    }


    public void create() {

        int index = chooser.showOpenDialog(mainPane);
        if (index == JFileChooser.APPROVE_OPTION) {
            CEntityForm.selectedTxtFile = chooser.getSelectedFile();
            textArea.setText("");
            textArea.repaint();
            try {
                  textArea.read(new FileReader(chooser.getSelectedFile().getAbsolutePath()), null);
                CEntityForm.text = textArea.getText();
                CEntityForm.presenceOfEncodedText = true;
                CEntityForm.closeTextPanelButton.setVisible(true);
                CEntityForm.progress.setValue(0);
                CEntityForm.percents.setText("0");
                CEntityForm.percents.setBounds(100, 454, 75, 43);

                if (CEntityForm.menuSwitch == 0) {
                    CEntityForm.closedLock.setVisible(true);
                    CEntityForm.openLock.setVisible(false);
                    CEntityForm.progress.setValue(0);
                }
                if (CEntityForm.menuSwitch == 1) {
                    CEntityForm.closedLock.setVisible(false);
                    CEntityForm.openLock.setVisible(true);
                    CEntityForm.progress.setValue(0);
                }

            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(mainPane, "Файл не найден");
            }

            CEntityForm.closeTextPanelButton.repaint();
        }

    }


    @Override
    public void focusGained(FocusEvent e) {
        System.out.println("ApproveButton gained focus.");
    }

    @Override
    public void focusLost(FocusEvent e) {
        System.out.println("ApproveButton lost focus.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent e) {


        Object o = e.getNewValue();
        InputMap map = chooser.getInputMap(
                JFileChooser.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        if (o instanceof JButton) {
            if ("focusOwner".equals(e.getPropertyName())) {
                JButton b = (JButton) o;
                String s = b.getText();
                boolean inApproved = b == myUI.getApproveButton(chooser);
                if (!(s == null || "".equals(s) || inApproved)) {
                    map.put(enterKey, "cancelSelection");
                } else {
                    map.put(enterKey, "approveSelection");
                }
            }
        }
    }

    private static class MyChooserUI extends MetalFileChooserUI {

        public MyChooserUI(JFileChooser b) {
            super(b);
        }

        @Override
        protected JButton getApproveButton(JFileChooser fc) {
            return super.getApproveButton(fc);
        }


    }

}
