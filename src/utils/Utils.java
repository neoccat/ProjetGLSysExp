package utils;

public class Utils {

    /**
     * Simple méthode pour tester si une chaine de caractères est castable en entier
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if(null == str )
            return false;
        
        try {
            Integer d = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
