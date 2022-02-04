import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

class RSA {
    public static void main() throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //generate RSA key pair
        KeyPair pair = generateKP();
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();

        String message = JOptionPane.showInputDialog ("What is your message?");

        byte[] encryptedMessage = encryptMessage(publicKey, message);

        //encode with base64 alphabet for readability in storage (like in a DB)
        String readableEncryptedMsg = Base64.getEncoder().encodeToString(encryptedMessage);

        String decryptedMessage = decryptMessage(encryptedMessage, privateKey);

        JOptionPane.showMessageDialog(null, decryptedMessage);
    }

    public static KeyPair generateKP(){
        KeyPairGenerator kpGen;
        try {
            kpGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        kpGen.initialize(2048); //keysize
        return kpGen.generateKeyPair();
    }

    public static byte[] encryptMessage(PublicKey publicKey, String message) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        //a Cipher object for encryption with our public key
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        //now we encrypt the message:
        //change message into bytes
        byte[] secretMessageBytes = message.getBytes(StandardCharsets.UTF_8);
        return encryptCipher.doFinal(secretMessageBytes);
    }
    public static String decryptMessage(byte[] encryptedMessageBytes, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //another Cipher but made for decryption mode & with private key
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decryptedMessageBytes = decryptCipher.doFinal(encryptedMessageBytes);
        return new String(decryptedMessageBytes, StandardCharsets.UTF_8);
    }
}