import javax.crypto.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.ArrayList;
import java.util.Base64;

public class EncryptionDecryption {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    EncryptionDecryption(){
        generateKey();
    }

    private void generateKey(){
        try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");

            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> encrypt(ArrayList<String> list) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        ArrayList<String> resList = new ArrayList<>();
        for(int i = 0; i < list.size(); ++i) {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] StrBytes = list.get(i).getBytes(StandardCharsets.UTF_8);
            byte[] encrypted = cipher.doFinal(StrBytes);
            resList.add(Base64.getEncoder().encodeToString(encrypted));
        }
        return resList;
    }

    public ArrayList<String> decrypt(ArrayList<String> list) {
        ArrayList<String> resList = new ArrayList<>();
        for(int i = 0; i < list.size(); ++i) {
            byte[] encrypted = Base64.getDecoder().decode(list.get(i));
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] decrypted = cipher.doFinal(encrypted);
                resList.add(new String(decrypted, StandardCharsets.UTF_8));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resList;
    }
}
