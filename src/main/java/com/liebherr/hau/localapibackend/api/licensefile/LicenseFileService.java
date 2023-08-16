package com.liebherr.hau.localapibackend.api.licensefile;

import com.google.gson.Gson;
import com.liebherr.hau.localapibackend.api.licensefile.dto.LicenseFile;
import com.liebherr.hau.localapibackend.api.licensefile.dto.request.LicenseFileRequest;
import com.liebherr.hau.localapibackend.utils.AESUtil;
import com.liebherr.hau.localapibackend.utils.KeyIVGeneratorAES;
import com.liebherr.hau.localapibackend.utils.PemUtils;
import com.liebherr.hau.localapibackend.utils.ReadPemKeyPairUtils;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.UUID;

@Service
public class LicenseFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LicenseFileService.class);

    @Autowired
    KeyIVGeneratorAES generatorAES;

    @Autowired
    ReadPemKeyPairUtils pemKeyPairUtils;

    @Autowired
    PemUtils pemUtils;


    public ResponseEntity<Resource> makeLicense(LicenseFileRequest licenseFileRequest) throws Exception {
        String email = licenseFileRequest.getEmail();
        String serialNumber = licenseFileRequest.getSerialNumber();
        String voucherCode = licenseFileRequest.getVoucherCode();

        LicenseFile licenseFile = new LicenseFile();
        licenseFile.setEmail(email);
        licenseFile.setSerialNumber(serialNumber);
        licenseFile.setVoucherCode(voucherCode);

        Gson gson = new Gson();
        String licenseFileString = gson.toJson(licenseFile);

//        String dataBase64 = Base64.getEncoder()
//                .encodeToString(licenseFileString.getBytes());

        //aescbc
//        String password = serialNumber; //serial number
//
//        String fileName = "password.txt";
//        try {
//            FileWriter writer = new FileWriter(fileName);
//            writer.write(password);
//            writer.close();
//            System.out.println("Successfully wrote to file.");
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }

//        byte[] salt = AESUtil.getRandomSalt(); // the agreed shared secret - salt

//        byte[] salt = Files.readAllBytes(Paths.get("salt.txt"));
//        FileWriter saltWriter = new FileWriter("salt.txt");
//        saltWriter.write(salt);
//
//        saltWriter.close();

//        SecretKey key = AESUtil.getKeyFromPassword(password, salt); // key = sha256(applianceSerial, salt)

//        SecretKey key = AESUtil.generateKey(128);

//        FileOutputStream fileOutputStream = new FileOutputStream("myKeyFile.bin");
//        try (ObjectOutputStream oout = new ObjectOutputStream(fileOutputStream)) {
//            oout.writeObject(key.getEncoded());
//        }
//        SecretKey key = AESUtil.generateKey(128);
//        IvParameterSpec ivParameterSpec = AESUtil.generateIv();

        KeySpec keySpec = generatorAES.genKey(serialNumber);
        byte[] iv = generatorAES.genIv(serialNumber);

        String input = licenseFileString;
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        String algorithm = "AES/CBC/PKCS5Padding";
        byte[] cipherText = AESUtil.encrypt(algorithm, input, (SecretKey) keySpec, ivParameterSpec);

        LOGGER.debug("CIPHER: {}", String.valueOf(Hex.encodeHex(cipherText)));

        FileOutputStream outputStream = new FileOutputStream("license.encrypted");
//            outputStream.write(cipherText.getBytes());
        outputStream.write(cipherText);
        // Open the file for reading
//        FileInputStream fileInputStream = new FileInputStream("myKeyFile.bin");
//        try (ObjectInputStream oin = new ObjectInputStream(fileInputStream)) {
        // Read the key from the file
//            byte[] encodedKey = (byte[]) oin.readObject();
//            SecretKey keySpec = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//
//            byte[] iv = AESUtil.generateIv();
//            LOGGER.debug("KEY: {}", String.valueOf(Hex.encodeHex(encodedKey))); // 579f7843dc8d89635aa5ed1cfba7e753
//            LOGGER.debug("IV: {}", String.valueOf(Hex.encodeHex(iv))); // 5f6d17a784d2f07957c66fea1579c16d

//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }


//        IvParameterSpec ivParameterSpec = AESUtil.generateIv();

//        String encrpytedDataBase64String = AESUtil.encryptPasswordBased(licenseFileString, key, ivParameterSpec); // data = aescbc_encrypt(json_stringify(licenseInfo), key)


//
//        Cipher encryptCipher = Cipher.getInstance("RSA");
//        encryptCipher.init(Cipher.ENCRYPT_MODE, privateKey);
//
//        byte[] secretMessageBytes = licenseFileString.getBytes(StandardCharsets.UTF_8);
//        byte[] encryptedMessageBytes = encryptCipher.doFinal(secretMessageBytes); // Signature signature = pk_sign(sha256(data), private-key)
//
//        String signatureStringBase64 = Base64.getEncoder()
//                .encodeToString(encryptedMessageBytes);

        //Signing
//        Signature sig = Signature.getInstance("SHA256withRSA");
//        sig.initSign(pair.getPrivate());
//        sig.update(licenseFileString.getBytes(StandardCharsets.UTF_8));
//
//        byte[] signature = sig.sign();
//
//        String signatureStringBase64 = Base64.getEncoder()
//                .encodeToString(signature);
//
//        String finalString = encrpytedDataBase64String + "." + signatureStringBase64;

        //Let's sign our message

//        ECPrivateKey privateKey = pemKeyPairUtils.readPrivateKey(new File("private.pem"));

        PrivateKey privateKey = pemUtils.readPrivateKeyFromFile("license/ec-key-1.pem", "EC");

        Signature sig = Signature.getInstance("SHA256withECDSA");
        sig.initSign(privateKey);
        sig.update(licenseFileString.getBytes(StandardCharsets.UTF_8));

        byte[] signature = sig.sign();

        FileOutputStream outputStream2 = new FileOutputStream("license.signed");
        outputStream2.write(licenseFileString.getBytes(StandardCharsets.UTF_8));

        FileOutputStream outputStream1 = new FileOutputStream("license.signed.sig");
        outputStream1.write(signature);

        String encryptedLicense = encryptToBase64(licenseFileString, serialNumber);
        String signedLicense = signToBase64(licenseFileString);

        String finalString = encryptedLicense + "." + signedLicense;

        File file = new File("myLicense.lic");
        Resource resource = null;

        try {
            FileWriter myWriter = new FileWriter("myLicense.txt");
            myWriter.write(finalString);

            resource = new UrlResource(file.toURI());

            myWriter.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEncryptedLicense(LicenseFileRequest licenseFileRequest) throws Exception {
        String email = licenseFileRequest.getEmail();
        String serialNumber = licenseFileRequest.getSerialNumber();
        String voucherCode = licenseFileRequest.getVoucherCode();

        LicenseFile licenseFile = new LicenseFile();
        licenseFile.setEmail(email);
        licenseFile.setSerialNumber(serialNumber);
        licenseFile.setVoucherCode(voucherCode);

        Gson gson = new Gson();
        String licenseFileString = gson.toJson(licenseFile);

//        KeySpec keySpec = generatorAES.genKey(serialNumber);
//        byte[] iv = generatorAES.genIv(serialNumber);

//        String input = licenseFileString;
//        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
//        String algorithm = "AES/CBC/PKCS5Padding";
//        byte[] cipherText = AESUtil.encrypt(algorithm, input, (SecretKey) keySpec, ivParameterSpec);

//        LOGGER.debug("CIPHER: {}", String.valueOf(Hex.encodeHex(cipherText)));

        String encryptedLicense = encryptToBase64(licenseFileString, serialNumber);
        String signedLicense = signToBase64(licenseFileString);

        return encryptedLicense + "." + signedLicense;
    }

    public ResponseEntity<Resource> getLicenseResourceFromDb(String id) {
//        VoucherCodeSerialNumberLicense vcsn = voucherCodeSerialNumberLicenseRepository.findById(UUID.fromString(id)).orElseThrow();

        String encryptedLicense = "";

        File file = new File("myLicense.lic");
        Resource resource = null;

        try {
            FileWriter myWriter = new FileWriter("myLicense.lic");
            myWriter.write(encryptedLicense);

            resource = new UrlResource(file.toURI());

            myWriter.close();

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String encryptToBase64(String licenseFileString, String serialNumber) throws Exception {
        KeySpec keySpec = generatorAES.genKey(serialNumber);
        byte[] iv = generatorAES.genIv(serialNumber);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        String algorithm = "AES/CBC/PKCS5Padding";
        byte[] cipherText = AESUtil.encrypt(algorithm, licenseFileString, (SecretKey) keySpec, ivParameterSpec);

        LOGGER.debug("CIPHER: {}", String.valueOf(Hex.encodeHex(cipherText)));

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String signToBase64(String licenseFileString) throws Exception {

        PrivateKey privateKey = pemUtils.readPrivateKeyFromFile("license/ec-key-1.pem", "EC");

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(licenseFileString.getBytes(StandardCharsets.UTF_8));

        Signature sig = Signature.getInstance("SHA256withECDSA");
        sig.initSign(privateKey);
        sig.update(hash);

        byte[] signature = sig.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    public void encryptFile(String licenseFileString, String serialNumber) throws Exception {
        KeySpec keySpec = generatorAES.genKey(serialNumber);
        byte[] iv = generatorAES.genIv(serialNumber);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        String algorithm = "AES/CBC/PKCS5Padding";
        byte[] cipherText = AESUtil.encrypt(algorithm, licenseFileString, (SecretKey) keySpec, ivParameterSpec);

        LOGGER.debug("CIPHER: {}", String.valueOf(Hex.encodeHex(cipherText)));

        FileOutputStream outputStream = new FileOutputStream("license.encrypted");
//            outputStream.write(cipherText.getBytes());
        outputStream.write(cipherText);
    }

    public void signFile(String licenseFileString) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        PrivateKey privateKey = pemUtils.readPrivateKeyFromFile("license/ec-key-1.pem", "EC");

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(licenseFileString.getBytes(StandardCharsets.UTF_8));

        Signature sig = Signature.getInstance("SHA256withECDSA");
        sig.initSign(privateKey);
        sig.update(hash);

        byte[] signature = sig.sign();

        FileOutputStream outputStream2 = new FileOutputStream("license.signed");
        outputStream2.write(licenseFileString.getBytes(StandardCharsets.UTF_8));

        FileOutputStream outputStream1 = new FileOutputStream("license.signed.sig");
        outputStream1.write(signature);
    }
}
