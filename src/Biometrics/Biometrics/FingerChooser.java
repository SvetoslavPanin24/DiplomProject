package Biometrics;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.KeyboardFocusManager;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.plaf.metal.MetalFileChooserUI;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class FingerChooser implements FocusListener, PropertyChangeListener {

    public JLayeredPane mainPane;
    public JButton fingerChooserButton;

    BJPanel fingerImagePanel;
    private CFingerPrint finger = new CFingerPrint();
    private BufferedImage fingerImage = new BufferedImage(finger.FP_IMAGE_WIDTH, finger.FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private final JFileChooser chooser = new JFileChooser();
    private final MyChooserUI myUI = new MyChooserUI(chooser);
    private final KeyStroke enterKey = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
    FileNameExtensionFilter filter = new FileNameExtensionFilter("png & bmp", "png", "bmp");


    public FingerChooser(JLayeredPane mainPane, JButton fingerChooserButton, BJPanel fingerImagePanel) {
        this.mainPane = mainPane;
        this.fingerChooserButton = fingerChooserButton;
        this.fingerImagePanel = fingerImagePanel;
        chooser.setCurrentDirectory(new File("src/FingerPrintPic"));
        chooser.setFileFilter(filter);
        myUI.installUI(chooser);
    }


    public void create() throws IOException {
        int index = chooser.showOpenDialog(mainPane);
        if (index == JFileChooser.APPROVE_OPTION) {
            try {


                fingerImage = ImageIO.read(new File(chooser.getSelectedFile().getAbsolutePath()));
                fingerImagePanel.setBufferedImage(fingerImage);
                //Send image for skeletinization
                finger.setFingerPrintImage(fingerImage);
                CEntityForm.arrayCoordinatesFinger = finger.getFingerPrintTemplate();
                CEntityForm.presenceOfAnEncryptionkey = true;
                //See what skeletinized image looks like
                fingerImage = finger.getFingerPrintImageDetail();
                fingerImagePanel.setBufferedImage(fingerImage);
                fingerImagePanel.setVisible(true);
                CEntityForm.closeImagePanelButton.setVisible(true);
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
