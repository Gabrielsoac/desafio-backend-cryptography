package com.gabryellow.cryptography.util;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
public class AESCryptography {

    @Value("${key.value}")
    private String injectedKey;

    private static String key;

    @PostConstruct
    public void init() {
        key = injectedKey;
    }

    public static String encrypt(String data){

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        try{
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, secretKey);

            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch(Exception e){
            throw new RuntimeException("Encrypted Error");
        }

    }

    public static String decrypt(String encryptedData) {

        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, secretKey);

            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decodedData));
        } catch (Exception e){
            throw new RuntimeException("Decrypted Error");
        }
    }
}
