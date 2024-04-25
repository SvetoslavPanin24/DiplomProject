package Biometrics;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class CMain {

  public CMain() {
  }
  public static void main(String[] args) throws IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, URISyntaxException, FontFormatException, InvalidAlgorithmParameterException {
    CEntityForm classFormMain = new  CEntityForm();

    classFormMain.setVisible(true);

  }
}