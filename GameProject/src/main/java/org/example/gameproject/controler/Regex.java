package org.example.gameproject.controler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static Boolean checkEmail(String email) throws Exception{
        String regex = "[a-zA-Z0-9.-]+@(gmail|email|yahoo)+\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches())
            return true;
        else {
            throw new Exception(" password is invalid! ");
        }
    }
    static public boolean checkPassword(String password) throws Exception{
        String regex = "(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])[A-Za-z0-9]{5,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (matcher.matches()) {
            return true;
        } else
            throw new Exception(" password is not valid");
    }




}
