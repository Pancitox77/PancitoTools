package git.pancitox77.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import git.pancitox77.files.ReadOnlyResources;
import git.pancitox77.utils.Translator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import lombok.Data;

@Data
public class ViewChanger {

    protected ResourceBundle bundle = Translator.getMessageBundle();
    protected Consumer<Object> onViewLoaded = _ -> {};
    protected List<String> stylesheets = new ArrayList<>();


    public boolean hasBundle() {
        return bundle != null;
    }

    public FXMLLoader getLoader(URL url) {
        FXMLLoader loader;
        if (hasBundle()) {
            loader = new FXMLLoader(url, bundle);
        } else {
            loader = new FXMLLoader(url);
        }
        return loader;
    }

    public void applyStylesheets(Scene scene) {
        for (String stylesheet : stylesheets) {
            scene.getStylesheets().add(stylesheet);
        }
    }

    public <T> void notifyViewLoaded(T data) {
        if (onViewLoaded != null) {
            onViewLoaded.accept(data);
        }
    }

    public void addStylesheet(String stylesheet) {
        stylesheets.add(stylesheet);
    }

    /**
     * Resuelve el nombre de la hoja de estilos.
     * Si el nombre de la hoja de estilos no es absoluto, se busca en la carpeta de recursos.
     * Si el nombre de la hoja de estilos es absoluto, se usa tal cual.
     * @param styleName el nombre de la hoja de estilos
      */
    public void resolveStylesheet(String styleName) {
        if (styleName.startsWith("file:") || styleName.startsWith("/")) {
            stylesheets.add(styleName);
        } else {
            String path = ReadOnlyResources.getStyle(styleName);
            stylesheets.add(path);
        }
    }

    /**
     * Resuelve una lista de nombres de hojas de estilos.
     * @param styleNames la lista de nombres de hojas de estilos
     * @see #resolveStylesheet(String)
      */
    public void resolveStylesheets(List<String> styleNames) {
        for (String styleName : styleNames) {
            resolveStylesheet(styleName);
        }
    }
}
