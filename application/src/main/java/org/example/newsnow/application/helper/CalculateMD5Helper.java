package org.example.newsnow.application.helper;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Helper class to calculate the MD5 checksum of a file.
 */
@Component
public class CalculateMD5Helper {


/**
     * Calculates the MD5 checksum of the given input bytes.
     *
     * @param inputBytes the input bytes to calculate the MD5 checksum for
     * @return the MD5 checksum as a hexadecimal string
     * @throws NoSuchAlgorithmException if the MD5 algorithm is not available
     */
    public String calculateMD5(byte[] inputBytes) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] md5Bytes = md.digest(inputBytes);

        StringBuilder hexString = new StringBuilder();
        for (byte b : md5Bytes) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();
    }

}
