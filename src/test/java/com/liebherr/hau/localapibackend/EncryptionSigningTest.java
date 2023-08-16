//package com.liebherr.hau.localapibackend;
//
//import com.google.gson.Gson;
//import org.apache.tomcat.util.http.fileupload.FileUtils;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.crypto.*;
//import javax.crypto.spec.IvParameterSpec;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.*;
//import java.security.spec.ECGenParameterSpec;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//
//@SpringBootTest
//class EncryptionSigningTest {
//
////    @Autowired
////    KeyPairUtils keyPairUtils;
//
//    @Test
//    void givenString_whenEncrypt_thenSuccess()
//            throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
//            BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException {
//
////        String input = "baeldung";
////        SecretKey key = AESUtil.generateKey(128);
////        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
////        String algorithm = "AES/CBC/PKCS5Padding";
////        String cipherText = AESUtil.encrypt(algorithm, input, key, ivParameterSpec);
////        String plainText = AESUtil.decrypt(algorithm, cipherText, key, ivParameterSpec);
////        Assertions.assertEquals(input, plainText);
//    }
//
//    @Test
//    void givenPassword_whenEncrypt_thenSuccess()
//            throws InvalidKeySpecException, NoSuchAlgorithmException,
//            IllegalBlockSizeException, InvalidKeyException, BadPaddingException,
//            InvalidAlgorithmParameterException, NoSuchPaddingException, SignatureException, IOException {
//
//
////        String email = "test@email.com";
////
////        String serialNumber = "serial";
////
////        String voucherCode = "voucher";
////
////        LicenseFile licenseFile = new LicenseFile();
////        licenseFile.setEmail(email);
////        licenseFile.setSerialNumber(serialNumber);
////        licenseFile.setVoucherCode(voucherCode);
////
////        Gson gson = new Gson();
////        String licenseFileString = gson.toJson(licenseFile);
//
//        //AESCBC
////        byte[] salt = AESUtil.getRandomSalt();
////        byte[] saltReadFromFile = Files.readAllBytes(Paths.get("salt.txt"));
////        IvParameterSpec ivParameterSpec = AESUtil.generateIv();
////        SecretKey key = AESUtil.getKeyFromPassword(serialNumber,saltReadFromFile);
////
////        String cipherText = AESUtil.encryptPasswordBased(licenseFileString, key, ivParameterSpec);
////
////        String decryptedCipherText = AESUtil.decryptPasswordBased(
////                cipherText, key, ivParameterSpec);
//        //
//
////        keyPairUtils.genRSAKeyPairAndSaveToFile();
//
//
////        PublicKey publicKey =
////                KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Files.readAllBytes(Paths.get("rsaPublicKey"))));
////
////        PrivateKey privateKey = KeyFactory.getInstance("RSA")
////                .generatePrivate(new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get("rsaPrivateKey"))));
//
////        //ECDSA
////        KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
////        generator.initialize(new ECGenParameterSpec("secp256r1"), new SecureRandom());
////        KeyPair pair = generator.generateKeyPair();
////        PrivateKey privateKey = pair.getPrivate();
////        PublicKey publicKey = pair.getPublic();
////
////        String pubkey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
////        String prvkey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
////
////        Cipher encryptCipher = Cipher.getInstance("RSA");
////        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
////
////        Cipher decryptionCipher = Cipher.getInstance("RSA");
////        decryptionCipher.init(Cipher.DECRYPT_MODE, publicKey);
////
////        byte[] secretMessageBytes = licenseFileString.getBytes(StandardCharsets.UTF_8);
////
////        MessageDigest md = MessageDigest.getInstance("SHA-256");
////        byte[] licenseStringHashed = md.digest(secretMessageBytes);
////
////        byte[] encryptedMessageBytes = encryptCipher.doFinal(licenseStringHashed); // Signature signature = pk_sign(sha256(data), private-key)
//
////        String signatureStringBase64 = Base64.getEncoder()
////                .encodeToString(encryptedMessageBytes);
//
//        //Let's sign our message
////        Signature privateSignature = Signature.getInstance("SHA256withECDSA");
////        privateSignature.initSign(privateKey);
////        privateSignature.update(licenseFileString.getBytes(StandardCharsets.UTF_8));
////
////        byte[] signature = privateSignature.sign();
////
////        String signatureStringBase64 = Base64.getEncoder()
////                .encodeToString(signature);
////
////        //Let's check the signature
////        byte[] signatureStringDecodedBytes = Base64.getDecoder().decode(signatureStringBase64);
////
////        Signature publicSignature = Signature.getInstance("SHA256withECDSA");
////        publicSignature.initVerify(publicKey);
////
////
////        publicSignature.update(licenseFileString.getBytes(StandardCharsets.UTF_8));
////        boolean isCorrect = publicSignature.verify(signatureStringDecodedBytes);
//
//        //
//
////        String finalString = cipherText + "." + signatureStringBase64;
////
////
////        Assertions.assertEquals(licenseFileString, decryptedCipherText);
////        Assertions.assertTrue(isCorrect);
//    }
//}
