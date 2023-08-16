package com.liebherr.hau.localapibackend.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

@Component
public class KeyPairUtils {
    public void genRSAKeyPairAndSaveToFile() {
        genRSAKeyPairAndSaveToFile(2048, "");
    }/*from  ww w  .j  a v  a  2 s  . co  m*/

    public void genRSAKeyPairAndSaveToFile(String dir) {
        genRSAKeyPairAndSaveToFile(2048, dir);
    }

    public void genRSAKeyPairAndSaveToFile(int keyLength, String dir) {
        KeyPair keyPair = genRSAKeyPair(keyLength);

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream(dir
                    + "rsaPublicKey"));
            dos.write(publicKey.getEncoded());
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        try {
            dos = new DataOutputStream(new FileOutputStream(dir
                    + "rsaPrivateKey"));
            dos.write(privateKey.getEncoded());
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null)
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public KeyPair genRSAKeyPair() {
        return genRSAKeyPair(2048);
    }

    public KeyPair genRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance("RSA");
            keyPairGenerator.initialize(keyLength);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
