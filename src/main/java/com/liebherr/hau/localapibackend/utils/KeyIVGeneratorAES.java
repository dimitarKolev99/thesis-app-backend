package com.liebherr.hau.localapibackend.utils;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;

@Component
public class KeyIVGeneratorAES {

    public static final Logger LOGGER = LoggerFactory.getLogger(KeyIVGeneratorAES.class);

    public void gen() throws Exception {
        String password = "96ZD6-09G54-VM5KT-3Q2SV-D53ZC";
        byte[] salt = "salt".getBytes();
        int iterations = 10000;
        int keyLength = 32;
        int ivLength = 16;

        // Generate key using PBKDF2 with HMAC SHA-256
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength * 8);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // Generate IV using PBKDF2 with HMAC SHA-256
        spec = new PBEKeySpec(password.toCharArray(), salt, 20000, ivLength * 8);
        byte[] iv = factory.generateSecret(spec).getEncoded();

        // Print key and IV
        LOGGER.debug("Key: {}", String.valueOf(Hex.encodeHex(keySpec.getEncoded())));
        LOGGER.debug("IV: {}", String.valueOf(Hex.encodeHex(iv)));
    }

    public KeySpec genKey(String password) throws Exception {
        byte[] salt = "salt".getBytes(StandardCharsets.UTF_8);
        int iterations = 10000;
        int keyLength = 32;

        // Generate key using PBKDF2 with HMAC SHA-256
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength * 8);
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");

        // Print key
        LOGGER.debug("Key: {}", String.valueOf(Hex.encodeHex(keySpec.getEncoded())));

        return keySpec;
    }

    public byte[] genIv(String password) throws Exception {
        byte[] salt = "salt".getBytes(StandardCharsets.UTF_8);
        int ivLength = 16;

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        // Generate IV using PBKDF2 with HMAC SHA-256
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 20000, ivLength * 8);
        byte[] iv = factory.generateSecret(spec).getEncoded();

        // Print IV
        LOGGER.debug("IV: {}", String.valueOf(Hex.encodeHex(iv)));

        return iv;
    }
}