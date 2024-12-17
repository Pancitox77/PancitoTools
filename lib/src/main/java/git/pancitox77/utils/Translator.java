package git.pancitox77.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.Getter;
import lombok.NonNull;

/**
 * Clase traductora para gesttionar internacionalización.
 * Las traducciones se obtienen del objeto "messageBundle", es decir, no se traduce
 * utilizando un servicio como Google Traductor, sino que el mismo usuario escribe las traducciones.
 */
public class Translator {

    /**
     * Indica de qué archivo extraer las traducciones.
     */
    @Getter private static Locale locale = Locale.getDefault();

    /**
     * La ruta al archivo de traducciones.
     * Por defecto, la ruta es "resources/i18n/messages...", siendo "messages" el nombre del archivo .properties.
     */
    @Getter private static String translationPath = "i18n/messages";

    /**
     * El paquete de traducciones, obtenido luego de cargar el locale y la ruta de traducciones.
     */
    @Getter private static ResourceBundle messageBundle = ResourceBundle.getBundle(translationPath, locale);

    private Translator(){}

    /**
     * Método setter para el locale y la ruta de traducciones.
     * Llamar a este método también recarga el paquete de traducciones
     * 
     * @param locale          El nuevo locale para la aplicación
     * @param translationPath La nueva ruta al archivo de traducciones.
     */
    public static void loadMessageBundle(@NonNull Locale locale, @NonNull String translationPath) {
        Translator.locale = locale;
        Translator.translationPath = translationPath;
        messageBundle = ResourceBundle.getBundle(translationPath, locale);
    }

    /**
     * Cambia el locale y recarga el paquete de traducciones, manteniendo la misma ruta
     * 
     * @param locale
     * @see #loadLocale(String)
      */
    public static void loadLocale(@NonNull Locale locale) {
        Translator.locale = locale;
        messageBundle = ResourceBundle.getBundle(translationPath, locale);
    }

    /**
     * Cambia el locale y recarga el paquete de traducciones, manteniendo la misma ruta.
     * 
     * @param localeCode Código del idioma. Ej.: "es", "en", "fr".
     * @see #loadLocale(Locale)
      */
    public static void loadLocale(@NonNull String localeCode) {
        loadLocale(Locale.forLanguageTag(localeCode));
    }

    /**
     * Cambia la ruta y recarga el paquete de traducciones, manteniendo el mismo locale
     * @param translationPath
      */
    public static void loadPath(@NonNull String translationPath) {
        Translator.translationPath = translationPath;
        messageBundle = ResourceBundle.getBundle(translationPath, locale);
    }

    /**
     * Method to retrieve a translated string from the resource bundle.
     * Método para obtener un string traducido del paquete de traducciones.
     * 
     * @param str La llave del string a buscar
     * @return El string traducido
     */
    public static String tr(String str) {
        return messageBundle.getString(str);
    }

    /**
     * Traduce una serie de opciones.
     * Traduce en secuencia: "seccion.opcion_a", "seccion.opcion_b", ..
     * 
     * @param section La categoría en la que se encuentra cada opción
     * @param options Lista de cada variante a comprobar.
     *                La cadena completa por opción es "section.option_a/.option_b/etc"
     *                Es decir, sección + "." + opción, por cada opción
     * @return arr
     */
    public static String[] trList(String section, String[] options) {
        String[] translatedOptions = new String[options.length];
        for (int i = 0; i < options.length; i++) {
            translatedOptions[i] = tr(section + "." + options[i]);
        }
        return translatedOptions;
    }

    /**
     * Compara un string con otro string traducido.
     * Atajo de: value.equals(Translator.tr(key))
     * 
     * @param value El valor a comparar
     * @param key   La llave para obtener el string traducido
     * @return Compara ambos strings con un .equals
     */
    public static boolean isLike(String value, String key) {
        return value.equals(tr(key));
    }
}