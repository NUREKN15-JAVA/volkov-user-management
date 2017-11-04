package ua.nure.usermanagement.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class TextManager {
    private static final String BUNDLE_NAME = "intern_text";

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private TextManager(){

    }

    public static String getString(String key) {
        try{
            return BUNDLE.getString(key);
        } catch (MissingResourceException e){
            return "!"+key+"!";
        }
    }
}
