package cms341.message_encryptor;

/**
 * Created by michael on 11/28/16.
 */

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;


class Cryptor {
    private Cipher cipher;

    public Cryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        cipher = Cipher.getInstance("AES");

    }


    public String encryptText(String text, String key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key aes = new SecretKeySpec(key.getBytes(), "AES");

        cipher.init(Cipher.ENCRYPT_MODE, aes);
        byte[] encryptedMessage = cipher.doFinal(text.getBytes());

        return Base64.encodeToString(encryptedMessage, Base64.DEFAULT);
    }

    public String decryptText(String text, String key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Key aes = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, aes);

        byte[] decryptedMessage = cipher.doFinal(Base64.decode(text, Base64.DEFAULT));

        return new String(decryptedMessage);
    }
}
