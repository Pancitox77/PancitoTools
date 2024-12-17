package git.pancitox77.utils;

public class Strings {

    private Strings(){}

    /**
     * Convierte a mayúsculas la primera letra del string
     * @param str
     * @return Resultado. Ejemplo: "hOlA Mundo" -> "Hola mundo"
      */
    public static String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Convierte a mayúsculas la primera letra de cada palabra del string
     * @param str
     * @return Resultado. Ejemplo: "hOlA Mundo" -> "Hola Mundo"
      */
    public static String title(String str) {
        String[] words = str.split(" ");
        String[] res = new String[words.length];

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            res[i] = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
        }
        
        return String.join(" ", res);
    }
}
