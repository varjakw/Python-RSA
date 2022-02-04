import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

class RSA {

    private static String privateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCYcC3FU3Q5bMbXBeJcsduAfWIqORlwc0idbhJLsZAAedO/265rhKiwDSFy/L0/rpFUtSG8aT8AUd4EIHCgf2T/Lp0UJANNZhrWbp6v2U1pkvPwKWlX1DvAuosEXyHKa9uso5pzh+Jl8nB0MK5aKT41jclrcLC66ZAmJrkRG/LROZmDecHBQZrY5Cr4zQcAWXTN1JbBQhyz62GtrGs5UXKftkgzlgi70w4gVxJav1TZjz1KuT0SXJwwdQJKVseuYEzZZLut/FTLd5mqw1f4yMvFjJk1thYrmocR7N2GAiwVLu7HGVUYHs7aFtayOEeJp1/hjAbMQ5TUjBrCU6+nhjYJAgMBAAECggEADP8Xb+jvEkxLYZ6/T17ck4ZdHbNzj0pM6Rsu1SRE3j2Ex6k/pCwRw7zC2s5ghjLochvlTe59MBZKwvhPEef6oOUFAGgXnfw0Eo8o2V5CAtvED3hAYnwWQhbFbnB77Ymnl371Vp9BtqZmXwmde9NjWeaGQzI+5xitsQsBIUvMNnk8fQm6qI2tMUwW6ez2fp+9vLhAWl0uGbGqR6yLiM4y+p7HumzfDo9vZym0CPvkaPDzb4aclayt3SXMfqmgdTLkz2g3VRkQJj8qlc/GI/6hpdfHG+VVPX50yrf8JuBhfnBjd/9bEkanr1Ke4EM+hlXjdWrOD0FNQ2UtFKLwZd1mQQKBgQDOhjy/ztqJmx5V5/vQEOCNbS0+muuTJ7RlZos+EsxXZRXUmCDPyW0syjEE2WNaRUkSF8yQieedhonGgEaD+g9YSezh/WcmnlvjxMkr3f5a38+pmGnE5izMYwbDc7umJudgi1//1D2bTvBl0IiLilTOjYsoNnnN5Y+fm1/cl177EQKBgQC89O9/+awOSY7ASYVYlW6UG5X0IH6yd7j9unsU8XoBdylV4sRNec8lO27HB+cmxuKF/Ek9wiafsaeIdw+vrdcJfBwF42vGFvbhuQ+AiEHJXs/KrahD2tmuzwbGZNAAbfnDaJv8Aisa8Orqh6bDmWhXCRTM/SI/faONX3F/aTrbeQKBgHn0MvNhBxOzEdrQvpZDIRbinZEWkn23OIOYZiklmJ0TCC0eGGIbI+kLDJ88b7ripx+UBixkAu22gK1DGmDczSrpnIRmyv63DLQXhGPvF0A6YbpYUjz/XvzijR/lu62F06PjKbE8d5S6rKKy3a7o5OOEGeSJJqw7HvC1I4pzqJbBAoGAGFoAm5+TmuvD5+gRtU7cx12ev62IZnujbZ+gyRwtuw6eVyE4ZzuxhHzaU/AizA6JlvbigYEJ9T6x9pZRgTSIKALEocWjWA4RlxmAc/IbeFYttVMYAlasAylPGDZZ0BRcQeCzaZDl+TzfKYZ96WtV1RGoipp81W3drBSGzgLDwRECgYBcM+yv5y/HPIK6mAjypatVj98J0yrDgG72QGimuX/NEJ1eoTqhTZVd/RuTgUaTkfIeGjcXQyMujFqthCLbQdATRXiAeCXKLPVcniPcsPdkLT1y2jQyzL3tKtMjou8R47kd17S33AIyN+Xemzgxnqo1aJ42JeSupS4MsiLtiDc3Mw==";
    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmHAtxVN0OWzG1wXiXLHbgH1iKjkZcHNInW4SS7GQAHnTv9uua4SosA0hcvy9P66RVLUhvGk/AFHeBCBwoH9k/y6dFCQDTWYa1m6er9lNaZLz8ClpV9Q7wLqLBF8hymvbrKOac4fiZfJwdDCuWik+NY3Ja3CwuumQJia5ERvy0TmZg3nBwUGa2OQq+M0HAFl0zdSWwUIcs+thraxrOVFyn7ZIM5YIu9MOIFcSWr9U2Y89Srk9ElycMHUCSlbHrmBM2WS7rfxUy3eZqsNX+MjLxYyZNbYWK5qHEezdhgIsFS7uxxlVGB7O2hbWsjhHiadf4YwGzEOU1IwawlOvp4Y2CQIDAQAB";

    public static PublicKey getPublicKey(String b64PublicKey) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PublicKey publicKey = null;
        //public key generated in X509 format so we use X509 keyspec to convert to RSA key
        //because we have base64 encoded key
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(b64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            //using keyfactory to recreate instance of public key
            return publicKey;
    }

    public static PrivateKey getPrivateKey(String b64PrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(b64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        keyFactory = KeyFactory.getInstance("RSA");
        privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    public static byte[] encryptMessage(String message, String publicKey) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
       // PublicKey public = getPublicKey.();

        //a Cipher object for encryption with our public key
        Cipher encryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));

        //now we encrypt the message:
        //change message into bytes then encrypt
        return encryptCipher.doFinal(message.getBytes());
    }

    public static String decryptMessage(byte[] encryptedMessageBytes, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        //another Cipher but made for decryption mode & with private key
        Cipher decryptCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(decryptCipher.doFinal(encryptedMessageBytes));
    }

    public static String decryptMessage(String data, String b64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        return decryptMessage(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(b64PrivateKey));
    }

    public static void main(String[] args) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, IOException {

        byte[] encryptedMessageBytes;

        int selection;
        Object[] options = { "Encrypt a Message", "Decrypt a Message" , "Quit" };
        JPanel panel = new JPanel();
        panel.add(new JLabel("RSA Encryption"));

        selection = JOptionPane.showOptionDialog(null, "Main Menu", "RSA Encryption",

                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,

                null, options, options[0]);

        System.out.println(selection);

        switch (selection) {
            case 0: //encrypt a message
                String plaintext = JOptionPane.showInputDialog ("What is your message?");
                String encryptedString = Base64.getEncoder().encodeToString(encryptMessage(plaintext, publicKey));
                System.out.print("Encrypted Msg: " + encryptedString);

                JTextArea ta2 = new JTextArea(10, 10);
                ta2.setText(encryptedString);
                ta2.setWrapStyleWord(true);
                ta2.setLineWrap(true);
                ta2.setCaretPosition(0);
                ta2.setEditable(false);
                JOptionPane.showMessageDialog(null, new JScrollPane(ta2), "Encrypted Text", JOptionPane.INFORMATION_MESSAGE);
                main(args);
                break;
            case 1: //decrypt message
                String ciphertext = JOptionPane.showInputDialog ("What is your message?");
                String decryptedString = RSA.decryptMessage(ciphertext, privateKey);
                System.out.println(decryptedString);

                JTextArea ta = new JTextArea(10, 10);
                ta.setText(decryptedString);
                ta.setWrapStyleWord(true);
                ta.setLineWrap(true);
                ta.setCaretPosition(0);
                ta.setEditable(false);
                JOptionPane.showMessageDialog(null, new JScrollPane(ta), "Decrypted Text", JOptionPane.INFORMATION_MESSAGE);
                main(args);
                break;
        }
    }
}