package Biometrics;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.metal.MetalFileChooserUI;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;

public final class SaveEncryptFile implements FocusListener, PropertyChangeListener {

    public JLayeredPane mainPane;
    private final JFileChooser chooser = new JFileChooser();
    private final MyChooserUI myUI = new MyChooserUI(chooser);
    private final KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

    public SaveEncryptFile(JLayeredPane mainPane) {
        this.mainPane = mainPane;

        chooser.setApproveButtonText("Сохранить");

    }

    public void create() throws Exception {
        chooser.setCurrentDirectory(new File("src/Decrypt"));
        String publicKey = "[$$AAx@df*jk%$Al";

        int index = chooser.showOpenDialog(mainPane);
        if (index == JFileChooser.APPROVE_OPTION) {
            String stringWithCoordinates = "";
            StringBuilder stringBuilder = new StringBuilder();
            try (FileWriter writer = new FileWriter(chooser.getSelectedFile() + ".txt")) {

                for (int i = 0; i < CEntityForm.arrayCoordinatesFinger.length; i++) {
                    stringBuilder.append((CEntityForm.arrayCoordinatesFinger[i]));
                    stringBuilder.append(";");

                }

                stringWithCoordinates = String.valueOf(stringBuilder);

                stringWithCoordinates = CEntityForm.AESencrypt(publicKey, stringWithCoordinates);


                assert stringWithCoordinates != null;
                writer.write(stringWithCoordinates);

                writer.write("®®®");
                writer.write(CEntityForm.text);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

//            System.out.println(CEntityForm.decrypt(s.toString().getBytes(StandardCharsets.UTF_8)));
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
