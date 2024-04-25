package Biometrics;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;


import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

public class CEntityForm extends JFrame {


    public static JPanel textPanel = new JPanel();
    public static JLayeredPane mainPane = new JLayeredPane();

    public static JButton closeTextPanelButton = new JButton();
    public static JButton closeImagePanelButton = new JButton();
    public static JButton encryptButton = new JButton();
    public static JButton backButton = new JButton();
    public static double[] arrayCoordinatesFinger = new double[0];
    public static String text;
    public static boolean presenceOfAnEncryptionkey = false;
    public static boolean presenceOfEncodedText = false;
    public static File selectedTxtFile;
    public static int menuSwitch = 0;
    public static JProgressBar progress = new JProgressBar();
    public static JLabel percents = new JLabel("0");
    public static JLabel closedLock = new JLabel();
    public static JLabel openLock = new JLabel();

    private static final String INIT_VECTOR = "qwertyuiopasdfgh";
    String publicKey = "[$$AAx@df*jk%$Al";
    CFingerPrint cFingerPrint = new CFingerPrint();

    public CEntityForm() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        JTextArea textArea = new JTextArea();

        JScrollPane scrollTxt = new JScrollPane(textArea);

        JButton openEncryptMenuButton = new JButton();
        JButton openDecryptMenuButton = new JButton();
        JButton saveFileButton = new JButton();
        JButton fingerChooserButton = new JButton();
        JButton txtFileChooserButton = new JButton();
        JButton encryptButton = new JButton();
        JButton decryptButton = new JButton();
        JButton exitButton = new JButton();

        BJPanel fingerImagePanel = new BJPanel();

        JLabel background = new JLabel();
        JLabel circle = new JLabel();
        JLabel guard = new JLabel();

        Font bigFontTR = new Font("rungthep", Font.TYPE1_FONT, 13);

        FingerChooser fingerChooser = new FingerChooser(mainPane, fingerChooserButton, fingerImagePanel);
        TxtFileChooser txtFileChooser = new TxtFileChooser(mainPane, txtFileChooserButton, textArea);
        SaveEncryptFile saveEncryptFile = new SaveEncryptFile(mainPane);
        SaveDecryptFile saveDecryptFile = new SaveDecryptFile(mainPane);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        final int[] percentCounter = {0};
        int x = Toolkit.getDefaultToolkit().getScreenSize().width / 3;
        int y = Toolkit.getDefaultToolkit().getScreenSize().height / 4;
        setLocation(x, y);

        setLayout(null);
        setResizable(false);
        setSize(516, 535);

        fingerImagePanel.setLayout(null);
        textPanel.setLayout(null);
        mainPane.setLayout(null);

        background.setSize(500, 500);

        progress.setUI(new ProgressCircleUI());
        progress.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        progress.setStringPainted(true);
        progress.setFont(progress.getFont().deriveFont(24f));
        progress.setOpaque(false);
        progress.setForeground(Color.BLUE);

        /*Label icons*/
        background.setIcon(new ImageIcon(ImageIO.read(new File("src/mainMenu.png"))));
        guard.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/guard.png"))));
        closedLock.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/closedLock.png"))));
        openLock.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/openLock.png"))));
        circle.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/circle.png"))));
        /*Label icons*/

        /*Components bounds*/
        mainPane.setBounds(0, 0, 930, 600);

        progress.setBounds(37, 434, 140, 140);
        circle.setBounds(14, 410, 180, 180);
        closedLock.setBounds(45, 445, 40, 40);
        openLock.setBounds(45, 445, 40, 40);
        guard.setBounds(67, 484, 75, 43);

        fingerImagePanel.setBounds(223, 117, 327, 357);
        percents.setBounds(100, 454, 75, 43);
        openEncryptMenuButton.setBounds(147, 132, 206, 50);
        openDecryptMenuButton.setBounds(147, 222, 206, 50);
        exitButton.setBounds(147, 312, 206, 50);
        encryptButton.setBounds(0, 116, 205, 50);
        fingerChooserButton.setBounds(0, 177, 205, 50);
        txtFileChooserButton.setBounds(0, 238, 205, 50);
        saveFileButton.setBounds(0, 299, 205, 50);
        backButton.setBounds(0, 360, 205, 50);
        decryptButton.setBounds(0, 116, 205, 50);
        textPanel.setBounds(555, 117, 350, 357);
        closeImagePanelButton.setBounds(208, 478, 53, 30);
        closeTextPanelButton.setBounds(548, 478, 53, 30);
        scrollTxt.setBounds(5, 0, 327, 355);
        textArea.setBounds(5, 5, 350, 355);
        /*Components bounds*/

        textArea.setOpaque(false);
        scrollTxt.setOpaque(false);
        fingerImagePanel.setOpaque(false);
        textPanel.setOpaque(false);

        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);


        scrollTxt.setBorder(null);
        scrollTxt.setOpaque(false);
        scrollTxt.getViewport().setOpaque(false);

        /*Components visible*/
        fingerImagePanel.setVisible(false);
        textPanel.setVisible(false);
        backButton.setVisible(false);
        saveFileButton.setVisible(false);
        encryptButton.setVisible(false);
        decryptButton.setVisible(false);
        fingerChooserButton.setVisible(false);
        txtFileChooserButton.setVisible(false);
        openLock.setVisible(false);
        closedLock.setVisible(false);
        progress.setVisible(false);
        circle.setVisible(false);
        guard.setVisible(false);
        percents.setVisible(false);
        closeImagePanelButton.setVisible(false);
        closeTextPanelButton.setVisible(false);
        /*Components visible*/

        add(mainPane);
        add(background, new Integer(1));
        mainPane.add(progress, new Integer(1));
        mainPane.add(percents, new Integer(1000));
        mainPane.add(guard, new Integer(1000));
        mainPane.add(closedLock, new Integer(1000));
        mainPane.add(openLock, new Integer(1000));
        mainPane.add(circle, new Integer(22));
        mainPane.add(exitButton);
        mainPane.add(openEncryptMenuButton, new Integer(2));
        mainPane.add(openDecryptMenuButton, new Integer(2));
        mainPane.add(backButton, new Integer(2));
        mainPane.add(saveFileButton, new Integer(2));
        mainPane.add(decryptButton, new Integer(2));
        mainPane.add(encryptButton, new Integer(2));
        mainPane.add(textPanel, new Integer(2));
        mainPane.add(fingerImagePanel, new Integer(2));
        mainPane.add(closeTextPanelButton, new Integer(100));
        mainPane.add(closeImagePanelButton);
        mainPane.add(fingerChooserButton);
        mainPane.add(txtFileChooserButton);
        textPanel.add(scrollTxt, new Integer(0));

        /*button appearance*/

        /*close Text Panel Button*/
        closeTextPanelButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/stash.png"))));
        closeTextPanelButton.setOpaque(false);
        closeTextPanelButton.setBorder(null);
        closeTextPanelButton.setContentAreaFilled(false);
        closeTextPanelButton.setFocusPainted(false);
        /*close Text Panel Button*/

        /*close Image Panel Button*/
        closeImagePanelButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/stash.png"))));
        closeImagePanelButton.setOpaque(false);
        closeImagePanelButton.setBorder(null);
        closeImagePanelButton.setContentAreaFilled(false);
        closeImagePanelButton.setFocusPainted(false);
        /*close Image Panel Button*/

        /*exit Button*/
        exitButton.setText("       Выход                       ");
        exitButton.setFocusPainted(false);
        exitButton.setFont(bigFontTR);
        exitButton.setBorder(null);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/exit.png"))));
        exitButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                exitButton.setContentAreaFilled(true);
                exitButton.setBackground(Color.white);
            }
        });
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setContentAreaFilled(false);
            }
        });
        /*exit Button*/

        /*open Decrypt Menu Button*/
        openDecryptMenuButton.setText("     Дешифровать            ");
        openDecryptMenuButton.setFocusPainted(false);
        openDecryptMenuButton.setFont(bigFontTR);
        openDecryptMenuButton.setBorder(null);
        openDecryptMenuButton.setOpaque(false);
        openDecryptMenuButton.setContentAreaFilled(false);
        openDecryptMenuButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/openLock.png"))));
        openDecryptMenuButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                openDecryptMenuButton.setContentAreaFilled(true);
                openDecryptMenuButton.setBackground(Color.white);
            }
        });
        openDecryptMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                openDecryptMenuButton.setContentAreaFilled(false);
            }
        });
        /*open Decrypt Menu Button*/

        /*open Encrypt Menu Button*/
        openEncryptMenuButton.setText("     Зашифровать            ");
        openEncryptMenuButton.setFocusPainted(false);
        openEncryptMenuButton.setFont(bigFontTR);
        openEncryptMenuButton.setBorder(null);
        openEncryptMenuButton.setOpaque(false);
        openEncryptMenuButton.setContentAreaFilled(false);
        openEncryptMenuButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/closedLock.png"))));
        openEncryptMenuButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                openEncryptMenuButton.setContentAreaFilled(true);
                openEncryptMenuButton.setBackground(Color.white);
            }
        });
        openEncryptMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                openEncryptMenuButton.setContentAreaFilled(false);
            }
        });
        /*open Encrypt Menu Button*/

        /*back Button*/
        backButton.setText("    Назад                         ");
        fingerImagePanel.setVisible(false);
        backButton.setFocusPainted(false);
        backButton.setFont(bigFontTR);
        backButton.setBorder(null);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/back.png"))));
        backButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                backButton.setContentAreaFilled(true);
                backButton.setBackground(Color.white);
            }
        });
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                backButton.setContentAreaFilled(false);
            }
        });
        /*back Button*/

        /*decrypt Button*/
        decryptButton.setText("  Дешифровать              ");
        decryptButton.setFocusPainted(false);
        decryptButton.setFont(bigFontTR);
        decryptButton.setBorder(null);
        decryptButton.setOpaque(false);
        decryptButton.setContentAreaFilled(false);
        decryptButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/decrypt.png"))));
        decryptButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                decryptButton.setContentAreaFilled(true);
                decryptButton.setBackground(Color.white);
            }
        });
        decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                decryptButton.setContentAreaFilled(false);
            }
        });
        /*decrypt Button*/

        /*encrypt Button*/
        encryptButton.setText("  Зашифровать              ");
        encryptButton.setFocusPainted(false);
        encryptButton.setFont(bigFontTR);
        encryptButton.setBorder(null);
        encryptButton.setOpaque(false);
        encryptButton.setContentAreaFilled(false);
        encryptButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/encrypt.png"))));
        encryptButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                encryptButton.setContentAreaFilled(true);
                encryptButton.setBackground(Color.white);
            }
        });
        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                encryptButton.setContentAreaFilled(false);
            }
        });
        /*encrypt Button*/

        /*finger Chooser Button*/

        fingerChooserButton.setText("   Выбрать отпечаток    ");
        fingerChooserButton.setFocusPainted(false);
        fingerChooserButton.setFont(bigFontTR);
        fingerChooserButton.setBorder(null);
        fingerChooserButton.setOpaque(false);
        fingerChooserButton.setContentAreaFilled(false);
        fingerChooserButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/finger.png"))));
        fingerChooserButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                fingerChooserButton.setContentAreaFilled(true);
                fingerChooserButton.setBackground(Color.white);
            }
        });
        fingerChooserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                fingerChooserButton.setContentAreaFilled(false);
            }
        });
        /*finger Chooser Button*/

        /*txt File Chooser Button*/

        txtFileChooserButton.setText("   Выбрать текст            ");
        txtFileChooserButton.setFocusPainted(false);
        txtFileChooserButton.setFont(bigFontTR);
        txtFileChooserButton.setBorder(null);
        txtFileChooserButton.setOpaque(false);
        txtFileChooserButton.setContentAreaFilled(false);
        txtFileChooserButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/text.png"))));
        txtFileChooserButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                txtFileChooserButton.setContentAreaFilled(true);
                txtFileChooserButton.setBackground(Color.white);
            }
        });
        txtFileChooserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                txtFileChooserButton.setContentAreaFilled(false);
            }
        });
        /*txt File Chooser Button*/

        /*save File Button*/
        saveFileButton.setText("   Cохранить                  ");
        saveFileButton.setFocusPainted(false);
        saveFileButton.setFont(bigFontTR);
        saveFileButton.setBorder(null);
        saveFileButton.setOpaque(false);
        saveFileButton.setContentAreaFilled(false);
        saveFileButton.setIcon(new ImageIcon(ImageIO.read(new File("src/Content/save.png"))));
        saveFileButton.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                saveFileButton.setContentAreaFilled(true);
                saveFileButton.setBackground(Color.white);
            }
        });
        saveFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                saveFileButton.setContentAreaFilled(false);
            }
        });
        /*save File Button*/

        /*button appearance*/

        /*button Listeners*/
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(1);
            }
        });
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setSize(510, 500);
                percents.setText("0");
                percents.setBounds(100, 454, 75, 43);
                background.setSize(500, 500);
                progress.setValue(0);
                try {
                    background.setIcon(new ImageIcon(ImageIO.read(new File("src/mainMenu.png"))));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                textArea.setText("");
                exitButton.setVisible(true);
                presenceOfAnEncryptionkey = false;
                presenceOfEncodedText = false;
                closeTextPanelButton.setVisible(false);
                closeImagePanelButton.setVisible(false);
                openDecryptMenuButton.setVisible(true);
                openEncryptMenuButton.setVisible(true);
                fingerImagePanel.setVisible(false);
                txtFileChooserButton.setVisible(false);
                fingerChooserButton.setVisible(false);
                textPanel.setVisible(false);
                encryptButton.setVisible(false);
                decryptButton.setVisible(false);
                saveFileButton.setVisible(false);
                backButton.setVisible(false);
                openLock.setVisible(false);
                closedLock.setVisible(false);
                progress.setVisible(false);
                circle.setVisible(false);
                guard.setVisible(false);
                percents.setVisible(false);

            }
        });

        openEncryptMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuSwitch = 1;
                setSize(916, 620);
                background.setSize(900, 600);
                try {
                    background.setIcon(new ImageIcon(ImageIO.read(new File("src/background.png"))));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                openDecryptMenuButton.setVisible(false);
                openEncryptMenuButton.setVisible(false);
                exitButton.setVisible(false);
                txtFileChooserButton.setVisible(true);
                fingerChooserButton.setVisible(true);
                textPanel.setVisible(true);
                encryptButton.setVisible(true);
                saveFileButton.setVisible(true);
                backButton.setVisible(true);
                openLock.setVisible(true);
                progress.setVisible(true);
                circle.setVisible(true);
                guard.setVisible(true);
                percents.setVisible(true);
            }
        });

        openDecryptMenuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menuSwitch = 0;
                setSize(916, 620);
                background.setSize(900, 600);
                try {
                    background.setIcon(new ImageIcon(ImageIO.read(new File("src/background.png"))));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                openDecryptMenuButton.setVisible(false);
                openEncryptMenuButton.setVisible(false);
                exitButton.setVisible(false);
                txtFileChooserButton.setVisible(true);
                fingerChooserButton.setVisible(true);
                textPanel.setVisible(true);
                decryptButton.setVisible(true);
                saveFileButton.setVisible(true);
                backButton.setVisible(true);
                closedLock.setVisible(true);
                progress.setVisible(true);
                circle.setVisible(true);
                guard.setVisible(true);
                percents.setVisible(true);
            }
        });

        saveFileButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (menuSwitch == 0) {
                        saveDecryptFile.create();
                    }
                    if (menuSwitch == 1) {
                        saveEncryptFile.create();
                    }

                } catch (Exception exception) {

                }
            }
        });

        scrollTxt.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                closeTextPanelButton.repaint();
            }
        });


        decryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!presenceOfAnEncryptionkey && !presenceOfEncodedText) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать отпечаток пальца и текст для дешифрвоки");
                }
                if (presenceOfAnEncryptionkey && !presenceOfEncodedText) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать текст для дешифрвоки");
                }
                if (!presenceOfAnEncryptionkey && presenceOfEncodedText) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать отпечаток пальца");
                }
                if (presenceOfAnEncryptionkey && presenceOfEncodedText) {

                    openLock.setVisible(false);
                    closedLock.setVisible(true);
                    StringBuilder stringBuilder = new StringBuilder();
                    StringBuilder textBuilder = new StringBuilder();
                    String qwe = "";
                    percentCounter[0] = 0;
                    progress.setValue(percentCounter[0]);
                    percents.setText("0");
                    boolean foundFinger = false;
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(selectedTxtFile));
                        char ch;
                        int c;
                        int counter = 0;
                        while ((c = reader.read()) != -1) {
                            ch = (char) c;
                            if (!foundFinger) {
                                if (ch == '®') {
                                    counter++;
                                    if (counter == 3) {
                                        foundFinger = true;
                                        counter = 0;
                                    }
                                    continue;
                                }
                                stringBuilder.append(ch);
                            }
                            if (foundFinger) {
                                textBuilder.append(ch);
                            }
                        }
                        reader.close();

                    } catch (IOException xe) {

                        xe.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException xe) {
                                xe.printStackTrace();
                            }
                        }
                    }
                    if (foundFinger) {

                        qwe = String.valueOf(stringBuilder);

                        try {
                            qwe = AESdecrypt(publicKey, String.valueOf(stringBuilder));
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }

                        char[] arrayCharsWithCoordinates = qwe.toCharArray();
                        StringBuilder fingerBuilder = new StringBuilder();
                        ArrayList<Double> listFingerCoordinates = new ArrayList<Double>();

                        for (int i = 0; i < arrayCharsWithCoordinates.length; i++) {

                            if (arrayCharsWithCoordinates[i] == ';') {
                                listFingerCoordinates.add(Double.parseDouble(fingerBuilder.toString()));
                                fingerBuilder.setLength(0);
                                continue;
                            }
                            fingerBuilder.append(arrayCharsWithCoordinates[i]);
                        }

                        double[] arrayCoordinatesFingerFromEncryptFile = new double[listFingerCoordinates.size()];

                        for (int i = 0; i < arrayCoordinatesFingerFromEncryptFile.length; i++) {
                            arrayCoordinatesFingerFromEncryptFile[i] = listFingerCoordinates.get(i);
                        }

                        int result = cFingerPrint.Match(arrayCoordinatesFinger, arrayCoordinatesFingerFromEncryptFile, 65, false);

                        new java.util.Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                percentCounter[0]++;
                                progress.setValue(percentCounter[0]);
                                percents.setText(percentCounter[0] + "%");
                                URL soundbyte = null;
                                if (percentCounter[0] == 80) {

                                    try {
                                        soundbyte = new File("src/Content/good.wav").toURI().toURL();
                                    } catch (MalformedURLException malformedURLException) {
                                        malformedURLException.printStackTrace();
                                    }
                                    java.applet.AudioClip clip = java.applet.Applet.newAudioClip(soundbyte);
                                    clip.play();
                                    closedLock.setVisible(false);
                                    openLock.setVisible(true);
                                }
                                if (percentCounter[0] >= result) {
                                    if (result >= 80) {
                                        try {
                                            text = textBuilder.toString();
                                            text = AESdecrypt(publicKey, text);

                                            String[] items = text.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");
                                            int[] results = new int[items.length];

                                            for (int j = 0; j < items.length; j++) {
                                                try {
                                                    results[j] = Integer.parseInt(items[j]);
                                                } catch (NumberFormatException nfe) {
                                                    //NOTE: write something here if you need to recover from formatting errors
                                                }
                                            }

                                            text = decrypt(results, Arrays.toString(arrayCoordinatesFingerFromEncryptFile));
                                            textArea.setText(text);
                                            JOptionPane.showMessageDialog(null, "Текст дешифрован");
                                        } catch (Exception exception) {
                                            exception.printStackTrace();
                                        }
                                    } else {
                                        try {
                                            soundbyte = new File("src/Content/failure.wav").toURI().toURL();
                                        } catch (MalformedURLException malformedURLException) {
                                            malformedURLException.printStackTrace();
                                        }
                                        java.applet.AudioClip clip = java.applet.Applet.newAudioClip(soundbyte);
                                        clip.play();
                                        JOptionPane.showMessageDialog(null, "Отпечаток пальца не походит");
                                    }

                                    cancel();
                                }


                            }
                        }, 0, 40);
                    } else {
                        JOptionPane.showMessageDialog(null, "Отпечаток пальца не обнаружен");
                    }

                }
            }
        });
        encryptButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!presenceOfAnEncryptionkey && !presenceOfEncodedText) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать отпечаток пальца и текст для шифрования");
                }
                if (presenceOfAnEncryptionkey && !presenceOfEncodedText) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать текст для шифрования");
                }
                if (!presenceOfAnEncryptionkey && presenceOfEncodedText) {
                    JOptionPane.showMessageDialog(null, "Необходимо выбрать отпечаток пальца");
                }
                if (presenceOfAnEncryptionkey && presenceOfEncodedText) {
                    percentCounter[0] = 0;
                    progress.setValue(0);
                    percents.setText("0");
                    openLock.setVisible(true);
                    closedLock.setVisible(false);
                    System.out.println(Arrays.toString(arrayCoordinatesFinger));
                    new java.util.Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            percentCounter[0]++;
                            progress.setValue(percentCounter[0]);
                            percents.setText(percentCounter[0] + "%");
                            if (percentCounter[0] == 10) {
                                percents.setBounds(96, 454, 75, 43);
                            }
                            if (percentCounter[0] == 100) {
                                percents.setBounds(94, 454, 75, 43);
                            }
                            if (percentCounter[0] == 80) {
                                closedLock.setVisible(false);
                                openLock.setVisible(true);
                            }
                            if (percentCounter[0] == 100) {

                                try {
                                    URL soundbyte = null;
                                    soundbyte = new File("src/Content/good.wav").toURI().toURL();
                                    java.applet.AudioClip clip = java.applet.Applet.newAudioClip(soundbyte);
                                    clip.play();
                                } catch (MalformedURLException malformedURLException) {
                                    malformedURLException.printStackTrace();
                                }
                                try {


                                    closedLock.setVisible(false);
                                    openLock.setVisible(true);
                                    String qwe;
                                    String temp = Arrays.toString(arrayCoordinatesFinger);
                                    int keys[];

                                    try {

                                        keys = encrypt(text, Arrays.toString(arrayCoordinatesFinger));
                                        text = Arrays.toString(keys);
                                        text = AESencrypt(publicKey, text);
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }


                                    textArea.setText(text);
                                    closedLock.setVisible(true);
                                    openLock.setVisible(false);
                                    JOptionPane.showMessageDialog(mainPane, "Текст успешно зашифрован");
                                    URL soundbyte = null;
                                } catch (Exception e) {

                                    URL soundbyte = null;
                                    try {
                                        soundbyte = new File("src/Content/failure.wav").toURI().toURL();
                                    } catch (MalformedURLException malformedURLException) {
                                        malformedURLException.printStackTrace();
                                    }

                                    java.applet.AudioClip clip = java.applet.Applet.newAudioClip(soundbyte);
                                    clip.play();
                                    JOptionPane.showMessageDialog(mainPane, "Текст не удалось зашифровать");

                                }
                            }
                            if (percentCounter[0] == 100) {
                                cancel();
                            }


                        }
                    }, 0, 40);

                }

            }

        });
        closeImagePanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                presenceOfAnEncryptionkey = false;
                progress.setValue(0);
                percents.setText("0");
                percents.setBounds(100, 454, 75, 43);
                closeImagePanelButton.setVisible(false);

                if (menuSwitch == 0) {
                    closedLock.setVisible(true);
                    openLock.setVisible(false);
                    progress.setValue(0);
                }
                if (menuSwitch == 1) {
                    closedLock.setVisible(false);
                    openLock.setVisible(true);
                    progress.setValue(0);
                }
                closeImagePanelButton.setVisible(false);
                fingerImagePanel.setVisible(false);
            }
        });
        closeTextPanelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                presenceOfEncodedText = false;
                progress.setValue(0);
                percents.setText("0");
                percents.setBounds(100, 454, 75, 43);
                closeTextPanelButton.setVisible(false);
                if (menuSwitch == 0) {
                    closedLock.setVisible(true);
                    openLock.setVisible(false);
                    progress.setValue(0);
                }
                if (menuSwitch == 1) {
                    closedLock.setVisible(false);
                    openLock.setVisible(true);
                    progress.setValue(0);
                }
                textArea.setText("");
            }
        });

        txtFileChooserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                txtFileChooser.create();
            }
        });
        fingerChooserButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    fingerChooser.create();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        /*button Listeners*/

        /*end class*/
    }

    public static int[] encrypt(String str, String key) {
        int[] output = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            int o = (Integer.valueOf(str.charAt(i)) ^ Integer.valueOf(key.charAt(i % (key.length() - 1)))) + '0';
            output[i] = o;
        }
        return output;
    }

    private static int[] string2Arr(String str) {
        String[] sarr = str.split(",");
        int[] out = new int[sarr.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = Integer.valueOf(sarr[i]);
        }
        return out;
    }

    private static String decrypt(int[] input, String key) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length; i++) {

            output.append((char) ((input[i] - 48) ^ (int) key.charAt(i % (key.length() - 1))));

        }
        return output.toString();
    }

    public static String AESencrypt(String key, String text) throws Exception {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return new String(HexBin.encode(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8))));
        } catch (Throwable cause) {
            System.out.println(cause.getMessage());
        }
        return null;
    }

    public static String AESdecrypt(String key, String text) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec((INIT_VECTOR.getBytes(StandardCharsets.UTF_8)));
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return new String(cipher.doFinal(HexBin.decode(String.valueOf(text.toCharArray()))));
    }

}


//End Class entity