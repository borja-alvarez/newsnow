package org.example.newsnow.application.helper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {CalculateMD5Helper.class})
class CalculateMD5HelperTest {

    @Autowired
    CalculateMD5Helper calculateMD5Helper;

    @Test
    public void testCalculateMD5_validInput() throws IOException, NoSuchAlgorithmException {
        byte[] input = "Hello World".getBytes();
        String expectedMD5 = "b10a8db164e0754105b7a99be72e3fe5";

        String result = calculateMD5Helper.calculateMD5(input);

        assertEquals(expectedMD5, result);
    }

}