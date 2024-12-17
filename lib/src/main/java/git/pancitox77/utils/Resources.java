package git.pancitox77.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

/**
 * Clase encargada de la gestión de recursos.
 * Almacena las rutas de las carpetas comúnes usadas por todas las clases de
 * esta
 * librería que trabajan con archivos.
 * ! Es importante indicar las rutas antes de llamar a cualquier otra clase.
 * 
 * Las rutas son relativas, es decir, todas las carpetas se ubican en la carpeta
 * "resources/" del proyecto.
 * 
 * Además, si se desea navegar entre escenas usado el método
 * {@link #showView(String)} y sus variantes
 * se debe cambiar la propiedad {@link #currentStage} para que funcione.
 */
public class Resources {
    private static final String RELATIVE_PATH_FORMAT = "/{0}/{1}";
    @Getter @Setter private static String viewsDir = "fxml";
    @Getter @Setter private static String stylesDir = "css";
    @Getter @Setter private static String imagesDir = "img";
    @Getter @Setter private static String fontsDir = "fonts";
    @Getter @Setter private static String jsonDir = "json";
    @Getter @Setter private static String propertiesDir = "env";
    @Getter @Setter private static Optional<Stage> currentStage = Optional.empty();

    private Resources() {}

    /**
     * Cambia la vista (escena fxml completa) por la indicada.
     * 
     * ! No hace nada si no se especificó {@link #currentStage}
     * 
     * No usa ningún ResourceBundle de traducciones, tampoco recibe controladores.
     * 
     * @param viewName Nombre de la vista, sin extensión
     * @see #showView(String, Consumer)
     * @see #showView(String, ResourceBundle)
     * @see #showView(String, ResourceBundle, Consumer)
      */
    @SneakyThrows
    public static void showView(String viewName) {
        if (!currentStage.isPresent()) {
            return;
        }
        
        Parent root = FXMLLoader.load(getView(viewName));
        Scene scene = new Scene(root);

        Stage stage = currentStage.get();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la vista (escena fxml completa) por la indicada.
     * 
     * ! No hace nada si no se especificó {@link #currentStage}
     * 
     * Usa el bundle indicado.
     * Ten en cuenta que puedes usar el bundle de Translator con {@code Translator.getMessageBundle()}
     * 
     * No recibe controladores.
     * 
     * @param viewName Nombre de la vista, sin extensión
     * @param bundle   Bundle de traducciones
     * @see #showView(String)
     * @see #showView(String, Consumer)
     * @see #showView(String, ResourceBundle, Consumer)
      */
    @SneakyThrows
    public static void showView(String viewName, ResourceBundle bundle) {
        if (!currentStage.isPresent()) {
            return;
        }
        
        Parent root = FXMLLoader.load(getView(viewName), bundle);
        Scene scene = new Scene(root);

        Stage stage = currentStage.get();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la vista (escena fxml completa) por la indicada.
     * 
     * ! No hace nada si no se especificó {@link #currentStage}
     * 
     * Luego de cargar la vista y antes de mostrarla, se llama a `controllerCallback`,
     * que debe ser un método que recibe el controlador de la vista.
     * Se asume que T es la clase del controlador de la vista.
     * 
     * No usa ningún ResourceBundle de traducciones.
     * 
     * @param viewName Nombre de la vista, sin extensión
     * @param controllerCallback Callback que se llama con el controlador de la vista
     * @see #showView(String)
     * @see #showView(String, ResourceBundle)
     * @see #showView(String, ResourceBundle, Consumer)
      */
    @SneakyThrows
    public static <T> void showView(String viewName, Consumer<T> controllerCallback) {
        if (!currentStage.isPresent()) {
            return;
        }
        
        FXMLLoader loader = new FXMLLoader(getView(viewName));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = currentStage.get();
        stage.setScene(scene);

        T controller = loader.getController();
        controllerCallback.accept(controller);

        stage.show();
    }

    /**
     * Cambia la vista (escena fxml completa) por la indicada.
     * 
     * ! No hace nada si no se especificó {@link #currentStage}
     * 
     * Usa el bundle indicado.
     * Ten en cuenta que puedes usar el bundle de Translator con {@code Translator.getMessageBundle()}
     * 
     * Luego de cargar la vista y antes de mostrarla, se llama a `controllerCallback`,
     * que debe ser un método que recibe el controlador de la vista.
     * Se asume que T es la clase del controlador de la vista.
     * 
     * No usa ningún ResourceBundle de traducciones.
     * 
     * @param viewName Nombre de la vista, sin extensión
     * @param bundle Bundle de traducciones
     * @param controllerCallback Callback que se llama con el controlador de la vista
     * @see #showView(String)
     * @see #showView(String, Consumer)
     * @see #showView(String, ResourceBundle)
      */
    @SneakyThrows
    public static <T> void showView(String viewName, ResourceBundle bundle, Consumer<T> controllerCallback) {
        if (!currentStage.isPresent()) {
            return;
        }
        
        FXMLLoader loader = new FXMLLoader(getView(viewName), bundle);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = currentStage.get();
        stage.setScene(scene);

        T controller = loader.getController();
        controllerCallback.accept(controller);

        stage.show();
    }

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
        return Resources.class.getResource(relativePath);
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
        return Resources.class.getResource(relativePath).toExternalForm();
    }

    /**
     * Obtiene una imágen de resources/imagesDir/name
     * 
     * @param name Nombre de la imágen, con extensión
     * @return Objeto Image del archivo indicado
     */
    public static Image getImage(String name) {
        String relativePath = formatPath(imagesDir, name);
        return new Image(Resources.class.getResourceAsStream(relativePath));
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
        return Resources.class.getResource(relativePath).getPath();
    }

    public static File getPropertyFile(String name) {
        String relativePath = formatPath(propertiesDir, name);
        return new File(Resources.class.getResource(relativePath).getPath());
    }

    /**
     * Obtiene la ruta a un archivo dentro de resources/dir/fileName
     * @param dir Carpeta dentro de resources/. No debe incluir "/" ni al inicio ni al final
     * @param fileName Nombre del archivo, con extensión
     * @return La ruta absoluta al archivo
      */
    public static String getPath(String dir, String fileName) {
        String relativePath = formatPath(dir, fileName);
        return Resources.class.getResource(relativePath).getPath();
    }

    /**
     * Obtiene la ruta absoluta a una carpeta dentro de resources/dir
     * @param dir Nombre de la carpeta a buscar
     * @return La ruta absoluta a la carpeta
      */
    public static String getPath(String dir) {
        return Resources.class.getResource(dir).getPath();
    }

    /**
     * Obtiene la URL a un archivo dentro de resources/dir/fileName
     * @param dir Carpeta dentro de resources/. No debe incluir "/" ni al inicio ni al final
     * @param fileName Nombre del archivo, con extensión
     * @return URL absoluta al archivo
      */
    public static URL getUrl(String dir, String fileName) {
        String relativePath = formatPath(dir, fileName);
        return Resources.class.getResource(relativePath);
    }

    public static File getFile(String dir, String fileName) {
        String relativePath = formatPath(dir, fileName);
        return new File(Resources.class.getResource(relativePath).getPath());
    }

    /**
     * Copia un archivo en la ruta especificada
     * 
     * @param fileToCopy El archivo a copiar
     * @param targetPath Dónde se debe copiar. La ruta es absoluta y debe incluir
     *                   extensión
     * @param override   Indica si, en caso de existir, se debe sobreescribir el
     *                   archivo
     */
    public static void copyFile(File fileToCopy, String targetPath, boolean override) {
        URI uri = fileToCopy.toURI();
        Path target = Path.of(targetPath);
        try (InputStream in = uri.toURL().openStream()) {
            if (override)
                Files.copy(in, target, StandardCopyOption.COPY_ATTRIBUTES, StandardCopyOption.REPLACE_EXISTING);
            else
                Files.copy(in, target, StandardCopyOption.COPY_ATTRIBUTES);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Copia un archivo externo dentro de los archivos internos del programa
     * 
     * @param fileToCopy El archivo externo a copiar
     * @param dir        La carpeta en la que se debe pegar
     * @param name       Nombre del nuevo archivo (con extensión)
     * @param override   Indica si, en caso de existir, se debe sobreescribir el
     *                   archivo
     */
    public static void copyToInternFiles(File fileToCopy, String dir, String name, boolean override) {
        copyFile(fileToCopy, getPath(dir, name), override);
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
