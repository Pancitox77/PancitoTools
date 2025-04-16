package git.pancitox77.files;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.text.MessageFormat;

import javafx.scene.image.Image;
import javafx.scene.text.Font;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * Clase encargada de la gestión de recursos de solo lectura.
 * Almacena las rutas de las carpetas comúnes usadas por todas las clases de
 * esta librería que trabajan con archivos.
 * ! Es importante indicar las rutas antes de llamar a cualquier otra clase.
 * 
 * Las rutas son relativas, es decir, todas las carpetas se ubican en la carpeta
 * "resources/" del proyecto.
 */
public class ReadOnlyResources {
    private static final String RELATIVE_PATH_FORMAT = "/{0}/{1}";
    @Getter @Setter private static String viewsDir = "fxml";
    @Getter @Setter private static String stylesDir = "css";
    @Getter @Setter private static String imagesDir = "img";
    @Getter @Setter private static String fontsDir = "fonts";
    @Getter @Setter private static String jsonDir = "json";
    @Getter @Setter private static String translationsBaseName = "lang/messages";

    private ReadOnlyResources() {}

    /**
     * Obtiene una url de resources/viewsDir/name.fxml, listo para usar con
     * FXMLoader
     * 
     * @param name Nombre del archivo, sin extensión (se le agrega ".fxml"
     *             internamente)
     * @return Objeto URL a la ruta de la vista.
     */
    public static URL getView(String name) {
        String relativePath = formatPath(viewsDir, name + ".fxml");
        return ReadOnlyResources.class.getResource(relativePath);
    }

    /**
     * Obtiene una archivo css de resources/stylesDir/name.css
     * 
     * @param name Nombre del archivo, sin extensión (se le agrega ".css"
     *             internamente)
     * @return Ruta externa al archivo
     */
    public static String getStyle(String name) {
        String relativePath = formatPath(stylesDir, name + ".css");
        return ReadOnlyResources.class.getResource(relativePath).toExternalForm();
    }

    /**
     * Obtiene una imágen de resources/imagesDir/name
     * 
     * @param name Nombre de la imágen, con extensión
     * @return Objeto Image del archivo indicado
     */
    public static Image getImage(String name) {
        String relativePath = formatPath(imagesDir, name);
        return new Image(ReadOnlyResources.class.getResourceAsStream(relativePath));
    }


    /**
     * Obtiene una fuente de resources/fontsDir/name
     * 
     * @param fontName Nombre de la fuente, debe incluir extensión
     * @param size Tamaño de la fuente
     * @return Objeto Font del archivo indicado
      */
    @SneakyThrows
    public static Font getFont(String fontName, double size) {
        String relativePath = formatPath(fontsDir, fontName);
        FileInputStream fis = new FileInputStream(relativePath);

        Font font = Font.loadFont(fis, size);
        fis.close();
        return font;
    }

    /**
     * Obtiene una json de resources/jsonDir/name.json
     * 
     * @param name Nombre del json, sin extensión (se le agrega ".json"
     *             internamente)
     * @return Ruta al json
     */
    public static String getJson(String name) {
        String relativePath = formatPath(jsonDir, name + ".json");
        return ReadOnlyResources.class.getResource(relativePath).getPath();
    }

    /**
     * Obtiene la ruta a un archivo dentro de resources/dir/fileName
     * @param dir Carpeta dentro de resources/. No debe incluir "/" ni al inicio ni al final
     * @param fileName Nombre del archivo, con extensión
     * @return La ruta absoluta al archivo
      */
    public static String getPath(String dir, String fileName) {
        String relativePath = formatPath(dir, fileName);
        return ReadOnlyResources.class.getResource(relativePath).getPath();
    }

    /**
     * Obtiene la ruta absoluta a una carpeta dentro de resources/dir
     * @param dir Nombre de la carpeta a buscar
     * @return La ruta absoluta a la carpeta
      */
    public static String getPath(String dir) {
        return ReadOnlyResources.class.getResource(dir).getPath();
    }

    /**
     * Obtiene la URL a un archivo dentro de resources/dir/fileName
     * @param dir Carpeta dentro de resources/. No debe incluir "/" ni al inicio ni al final
     * @param fileName Nombre del archivo, con extensión
     * @return URL absoluta al archivo
      */
    public static URL getUrl(String dir, String fileName) {
        String relativePath = formatPath(dir, fileName);
        return ReadOnlyResources.class.getResource(relativePath);
    }

    public static File getFile(String dir, String fileName) {
        String relativePath = formatPath(dir, fileName);
        return new File(ReadOnlyResources.class.getResource(relativePath).getPath());
    }

    public static File getFile(String relativePath) {
        return new File(ReadOnlyResources.class.getResource(relativePath).getPath());
    }

    /**
     * Obtiene la ruta relativa a partir de la carpeta y el archivo.
     * El formato es "/{dir}/{file}".
     * Se eliminan los "/" al inicio y al final del dir en caso de que los tenga.
     * @param dir La carpeta dentro de resources/. Puede empezar o terminar con "/" sin errores.
     * @param file Debe incluir la extensión.
     * @return
      */
    public static String formatPath(String dir, String file) {
        int start = dir.startsWith("/") ? 1 : 0;
        int end = dir.endsWith("/") ? dir.length() - 1 : dir.length();

        String formattedDir = dir.substring(start, end);
        return MessageFormat.format(RELATIVE_PATH_FORMAT, formattedDir, file);
    }
}
