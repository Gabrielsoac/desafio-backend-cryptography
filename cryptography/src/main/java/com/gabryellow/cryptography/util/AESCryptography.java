package com.gabryellow.cryptography.util;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AESCryptography {

    public static String encrypt(String data, SecretKeySpec key){

        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, key);

            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch(Exception e){
            throw new RuntimeException("Encrypted Error");
        }

    }

    public static String decrypt(String encryptedData, SecretKeySpec key) {

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, key);

            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decodedData));
        } catch (Exception e){
            throw new RuntimeException("Decrypted Error");
        }
    }
}
