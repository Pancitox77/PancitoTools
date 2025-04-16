package git.pancitox77.ui;

import java.net.URL;

import git.pancitox77.files.ReadOnlyResources;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;


public class Views {
    
    @Getter @Setter private static ViewChanger viewChanger = new ViewChanger();
    @Getter @Setter private static Stage currentStage;

    private Views() {}

    /**
     * Carga un archivo FXMl y establece su controlador.
     * Usado para nodos personalizados que contienen la vista en un FXML.
     * @param <T> el tipo de controlador
     * @param nodeName la ruta (relativa a resources/) del archivo FXML
     * @param controller el controlador a establecer
      */
    @SneakyThrows
    public static <T> void loadFXML(String nodeName, T controller) {
        FXMLLoader loader = new FXMLLoader(ReadOnlyResources.getView("nodes/" + nodeName), viewChanger.getBundle());
        loader.setController(controller);
        loader.setRoot(controller);
        loader.load();
    }


    /**
     * Carga un archivo FXMl y lo establece como vista actual.
     * Usa 'viewChanger' para cambiar la vista (transiciones, stylesheets, etc.)
     * @param viewName Nombre de la vista (sin extensión). Relativo a {@link ReadOnlyResources.getViewsDir()}
      */
    @SneakyThrows
    public static <T> void showView(String viewName) {
        if (currentStage == null) {
            return;
        }
        
        URL url = ReadOnlyResources.getView(viewName);
        FXMLLoader loader = viewChanger.getLoader(url);

        Parent root = loader.load();
        Scene scene = new Scene(root);

        currentStage.setScene(scene);

        // Notificar al controllador
        T controller = loader.getController();
        viewChanger.notifyViewLoaded(controller);

        // Aplicar estilos
        viewChanger.applyStylesheets(scene);

        currentStage.show();
    }
}
