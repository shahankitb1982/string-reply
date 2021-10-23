package com.beta.replyservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@Service
public class MessageServices {

    public static final Logger LOGGER = LoggerFactory.getLogger(MessageServices.class);

    /**
     * To validate string
     *
     * @param str
     * @return
     */
    public Boolean validate(String str) {
        // To check first 2 digits and then later -
        if (Character.isDigit(str.charAt(0)) && Character.isDigit(str.charAt(1)) && str.charAt(2) == '-') {
            return true;
        }
        return false;
    }

    /**
     * Manipulate data and return result.
     *
     * @param message
     * @return
     */
    public String getData(String message) {
        String result = "";
        String[] rulesString = message.split("-");
        List<String> sList = Arrays.asList(rulesString);
        if (sList.size() == 2) {
            String rule = sList.get(0);
            String msg = sList.get(1);

            Integer firstDigit = Integer.valueOf(rule.substring(0, 1));
            Integer secondDigit = Integer.valueOf(rule.substring(1, 2));

            LOGGER.debug("Rule : {}, Message : {}, First : {}, Second : {}", rule, msg, firstDigit, secondDigit);

            if (firstDigit == 1 && secondDigit == 1) {
                result = message;
            } else if (firstDigit == 1 && secondDigit == 2) {
                String reverseStr = reverseString(msg);
                result = getMd5(reverseStr);
            } else if (firstDigit == 2 && secondDigit == 1) {
                String encodedStr = getMd5(msg);
                result = reverseString(encodedStr);
            } else if (firstDigit == 2 && secondDigit == 2) {
                String encodedStr = getMd5(msg);
                result = getMd5(encodedStr);
            } else {
                result = "Invalid input";
            }
        }

        return result;
    }

    /**
     * To reverse string.
     *
     * @param str
     * @return
     */
    public String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * To convert String to MD5 hash.
     *
     * @param str
     * @return
     */
    public String getMd5(String str) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(str.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
