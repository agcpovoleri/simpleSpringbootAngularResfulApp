package com.example.forumapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SecurityUtils {

    static Logger logger = LoggerFactory.getLogger(SecurityUtils.class);

    private static int PASSWORD_SIZE = 8;

    public static String getUUID(){
        return UUID.randomUUID().toString();
    }

    public static String generateMd5(String passwordToHash) {

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String randomPassword(){

        String possibleValues = "abcdef1234567890";

        List<String> items = Arrays.asList(possibleValues.split(""));
        Set<List<String>> seen = new HashSet<List<String>>();
        for (int i = 0; i < 10; ++i) {
            do {
                Collections.shuffle(items);
            } while (!seen.add(items));

        }

        return "".join("",items.subList(0, PASSWORD_SIZE));
    }

}
