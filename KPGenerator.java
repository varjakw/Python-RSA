//Key Pair Generation

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.util.Base64;

public class KPGenerator {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KPGenerator() throws NoSuchAlgorithmException {
        //generate RSA key pair
        KeyPairGenerator kpGen= KeyPairGenerator.getInstance("RSA");
        kpGen.initialize(2048); //keysize
        KeyPair pair = kpGen.generateKeyPair();

        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void writeToFile(String path, byte[] key) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();
        FileOutputStream stream = new FileOutputStream(file);
        stream.write(key);
        stream.flush();
        stream.close();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        KPGenerator KPGenerator = new KPGenerator();
        KPGenerator.writeToFile("RSA/publicKey", KPGenerator.getPublicKey().getEncoded());
        KPGenerator.writeToFile("RSA/privateKey", KPGenerator.getPrivateKey().getEncoded());
        System.out.println(Base64.getEncoder().encodeToString(KPGenerator.getPublicKey().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(KPGenerator.getPrivateKey().getEncoded()));
    }

}
