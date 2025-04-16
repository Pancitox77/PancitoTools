package git.pancitox77.files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

public class Resources {
    
    @Getter @Setter private static String appName;

    private Resources() {}

    @SneakyThrows
    public static Path getAppDir() {
        if (appName == null) {
            throw new IllegalStateException("App name not set. Use Resources.setAppName(String appName) before calling getAppDir().");
        }

        String userHome = System.getProperty("user.home");
        Path appDir = Path.of(userHome, ".config", appName);

        if (!appDir.toFile().exists()) {
            Files.createDirectories(appDir);
        }
        return appDir;
    }

    public static Path getAppDir(String subdir) {
        return getAppDir().resolve(subdir);
    }

    @SneakyThrows
    /**
     * Copia un archivo a la carpeta de la aplicación.
     * Si la carpeta no existe, se crea.
     * Si el archivo ya existe y override es false, no se copia.
     * Si el archivo ya existe y override es true, se reemplaza.
     * @param copyFile el archivo a copiar
     * @param subpath la subruta dentro de la carpeta de la aplicación
     * @param override indica si se debe sobrescribir el archivo si ya existe
      */
    public static void copyToAppDir(Path copyFile, String subpath, boolean override) {
        File file = getAppDir(subpath).toFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            file = new File(file, copyFile.getFileName().toString());
        }
        if (file.exists() && !override) {
            return;
        }
        Files.copy(copyFile, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}
