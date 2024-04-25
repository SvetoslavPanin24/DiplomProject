package Biometrics;

import javax.swing.*;
import javax.swing.plaf.metal.MetalFileChooserUI;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class SaveDecryptFile implements FocusListener, PropertyChangeListener {

    public JLayeredPane mainPane;
    private final JFileChooser chooser = new JFileChooser();
    private final MyChooserUI myUI = new MyChooserUI(chooser);
    private final KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

    public SaveDecryptFile(JLayeredPane mainPane) {
        this.mainPane = mainPane;

        chooser.setApproveButtonText("Сохранить");

    }

    public void create() throws Exception {
        chooser.setCurrentDirectory(new File("src/Decrypt"));
        String publicKey = "[$$AAx@df*jk%$Al";

        int index = chooser.showOpenDialog(mainPane);
        if (index == JFileChooser.APPROVE_OPTION) {

            try (FileWriter writer = new FileWriter(chooser.getSelectedFile() + ".txt")) {

                writer.write(CEntityForm.text);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
