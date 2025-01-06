package git.pancitox77.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import git.pancitox77.utils.Resources;
import lombok.SneakyThrows;

/**
 * Clase usada para controlar archivos de propiedades (.properties).
 * Las rutas de archivos de propiedades se guardan en {@link git.pancitox77.utils.Resources}.
 * 
 * Las propiedades de "app.properties" sobreescriben a las de "default.properties".
*/
public class Environment {
    private static String defaultPropertiesName = "default.properties";
    private static String appPropertiesName = "app.properties";

    private Environment(){}

    /**
     * Obtiene las propiedades almacenadas en "app.properties" + las de "default.properties".
     * app.properties sobreescribe las propiedades de default.properties.
     * Las propiedades se almacenan en resources/env/ .
     * @return Las propiedades en cuestión.
      */
    @SneakyThrows
    public static Properties getProperties(){
        // Cargar propiedades por defecto
        Properties defaultProperties = new Properties();

        File file = Resources.getPropertyFile(defaultPropertiesName);
        try (FileInputStream fileIn = new FileInputStream(file)) {
            defaultProperties.load(fileIn);
            
        } catch (Exception e){ e.printStackTrace(); }
        
        
        // Cargar propiedades de la aplicación (sobreescribir propiedades por defecto)
        file = Resources.getPropertyFile(appPropertiesName);
        Properties appProperties = new Properties(defaultProperties);

        try (FileInputStream in = new FileInputStream(file)) {
            appProperties.load(in);
            
        } catch (Exception e){ e.printStackTrace(); }

        return appProperties;
    }

    /**
     * Obtener el valor de una propiedad de "app.properties" (o "default.properties" si no la hay).
     * Atajo para PropertiesHandler.getProperties().getProperty(key).
     * @param key Nombre de la propiedad a buscar
     * @return El valor de la propiedad buscada, o null si no la hay.
      */
    public static String getAppProperty(String key){
        return getProperties().getProperty(key);
    }

    /**
     * Cambiar o crear una propiedad de app.properties.
     * El cambio es consistente (se guarda en el archivo).
     * @param key Llave o nombre de la propiedad
     * @param value Nuevo valor de la propiedad
      */
    public static void setAppProperty(String key, String value){
        File f = Resources.getPropertyFile(appPropertiesName);

        try (FileOutputStream fileOut = new FileOutputStream(f)) {
            Properties properties = getProperties();
            properties.setProperty(key, value);

            // Guardar
            properties.store(fileOut, value);

        } catch (Exception e){ e.printStackTrace(); }
    }

    /**
     * Obtiene la propiedad de la app como un string.
     * <p>
     * Alias de {@code Environment.getAppProperty(key)}
     * 
     * @param key Llave o nombre de la propiedad
     * @return El valor de la propiedad buscada, o null si no la hay.
      */
    public static String getString(String key) {
        return getAppProperty(key);
    }

    /**
     * Obtiene la propiedad de la app como un entero.
     * <p>
     * Acortador de {@code Integer.parseInt(Environment.getAppProperty(key))}
     * 
     * @param key Llave o nombre de la propiedad
     * @return El valor de la propiedad buscada, o null si no la hay.
      */
    public static int getInt(String key) {
        return Integer.parseInt(getAppProperty(key));
    }

    /**
     * Obtiene la propiedad de la app como un boolean.
     * <p>
     * Acortador de {@code Boolean.parseBoolean(Environment.getAppProperty(key))}
     * 
     * @param key Llave o nombre de la propiedad
     * @return El valor de la propiedad buscada, o null si no la hay.
      */
    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(getAppProperty(key));
    }

    /**
     * Obtiene la propiedad de la app como una lista de strings.
     * <p>
     * Acortador de {@code Environment.getAppProperty(key).split(separator)}
     * 
     * @param key Llave o nombre de la propiedad
     * @param separator Caracter que separa un elemento de otro. Ej.: ",", " "
     * @return El valor de la propiedad buscada, o null si no la hay.
      */
    public static String[] getList(String key, String separator) {
        String value = getAppProperty(key);
        return value == null ? new String[0] : value.split(separator);
    }

    public static void setAppPropertiesName(String appPropertiesName) {
        Environment.appPropertiesName = appPropertiesName;
    }

    public static void setDefaultPropertiesName(String defaultPropertiesName) {
        Environment.defaultPropertiesName = defaultPropertiesName;
    }
}
