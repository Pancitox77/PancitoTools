package git.pancitox77.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import git.pancitox77.utils.Resources;

/**
 * Utilidades para el manejo de archivos JSON.
 * Utiliza la librería Gson.
 * Provee funciones de lectura y escritura, tanto para objetos como para arrays.
 * 
 * Excepto por los métodos "..abs()", el resto de métodos usan {@link Resources#getJson(String)}
*/
public class JsonTools {

    private JsonTools(){}

    /**
     * Lee un archivo Json y devuelve el contenido en formato de string
     * @param fileName Nombre del archivo, sin extensión
     * @return String. Contenido del archivo json, o null si hay un error de sintáxis o el arhivo no existe.
     * @see #readAbs(String)
     * @see #readArray(String)
     * @see #readObject(String)
      */
    public static String read(String fileName){
        return readAbs(Resources.getJson(fileName));
    }

    /**
     * Lee un archivo Json y devuelve el contenido como string
     * @param path La ruta absoluta al archivo, con extensión
     * @return String. Contenido del archivo json, o null si hay un error de sintáxis o el arhivo no existe.
     * @see #read(String)
     * @see #readArray(String)
     * @see #readObject(String)
      */
    public static String readAbs(String path) {
        try (Scanner scanner = new Scanner(new File(path))) {
            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lee el contenido de un archivo Json y lo devuelve en formato JsonArray.
     * Acortador para "new Gson().fromJson(read(path), JsonArray.class)"
     * @param fileName Nombre del archivo en el que leer, sin extensión
     * @return JsonArray. Objeto JsonArray correspondiente al contenido del archivo
     * @see #readAbsArray(String)
      */
    public static JsonArray readArray(String fileName) {
        return new Gson().fromJson(read(fileName), JsonArray.class);
    }

    /**
     * Lee el contenido de un archivo Json y lo devuelve en formato JsonArray.
     * Acortador para "new Gson().fromJson(readAbs(path), JsonArray.class)"
     * @param path La ruta absoluta al archivo, con extensión
     * @return JsonArray. Objeto JsonArray correspondiente al contenido del archivo
     * @see #readArray(String)
      */
    public static JsonArray readAbsArray(String path) {
        return new Gson().fromJson(readAbs(path), JsonArray.class);
    }

    /**
     * Lee el contenido de un archivo Json y lo devuelve en formato JsonObject.
     * Acortador para "new Gson().fromJson(read(path), JsonObject.class)"
     * @param fileName Nombre del archivo en el que leer, sin extensión
     * @return JsonObject Objeto JsonObject correspondiente al contenido del archivo
     * @see #readAbsObject(String)
      */
    public static JsonObject readObject(String fileName){
        return new Gson().fromJson(read(fileName), JsonObject.class);
    }

    /**
     * Lee el contenido de un archivo Json y lo devuelve en formato JsonObject.
     * Acortador para "new Gson().fromJson(readAbs(path), JsonObject.class)"
     * @param path La ruta absoluta al archivo, con extensión
     * @return JsonObject. Objeto JsonObject correspondiente al contenido del archivo
     * @see #readObject(String)
      */
    public static JsonObject readAbsObject(String path) {
        return new Gson().fromJson(readAbs(path), JsonObject.class);
    }

    /**
     * Escribe un elemento json en un archivo .json. Sobreescribe el contenido.
     * @param fileName Nombre del archivo en el que escribir, sin extensión
     * @param element El elemento a escribir en el archivo
     * @param prettyJson Si es true, transformar el elemento a str usando {@link #prettyJson(Object)}
      */
    public static void writeElement(String fileName, JsonElement element, boolean prettyJson){
        try (FileWriter writer = new FileWriter(Resources.getJson(fileName))) {
            String json = prettyJson ? prettyJson(element) : new Gson().toJson(element);
            writer.write(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Escribe un elemento json en la ruta absoluta. Sobreescribe el contenido.
     * @param absPath Ruta absoluta en la que escribir. Esta función no agrega el ".json" al final de la ruta.
     * @param element El elemento a escribir en el archivo
     * @param prettyJson Si es true, transformar el elemento a str usando {@link #prettyJson(Object)}
      */
    public static void writeAbs(String absPath, JsonElement element, boolean prettyJson) {
        try (FileWriter writer = new FileWriter(absPath)) {
            String json = prettyJson ? prettyJson(element) : new Gson().toJson(element);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Convierte un objeto en un json string formateado
     * @param <T> Tipo de objeto
     * @param obj El objeto a formatear
     * @return La representación JSON en string
      */
    public static <T> String prettyJson(T obj){
        return new GsonBuilder().setPrettyPrinting().create().toJson(obj);
    }

    /**
     * Crea un nuevo array con todos los elementos de A, sobreescritos por B.
     * <br>
     * Si A.keyField == B.keyField, se escribe el obj B.
     * <p>
     * Ten en cuenta que B sobreescribe a A.
     * @param a El array a sobreescribir
     * @param b El array que sobreescribe a A
     * @param keyField Campo que sirve para saber si se debe sobreescribir o no, por ejemplo, el ID o el nombre.
     * @return Un nuevo array con los resultados
      */
    public static JsonArray overrideArray(JsonArray a, JsonArray b, String keyField) {
        JsonArray result = a.deepCopy();
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                JsonObject aObj = a.get(i).getAsJsonObject();
                JsonObject bObj = b.get(j).getAsJsonObject();

                boolean bFieldSameAField = aObj.get(keyField).equals(bObj.get(keyField));
                if (bFieldSameAField)
                    result.set(i, bObj);
            }
        }
        return result;
    }

    /**
     * Convierte un objeto a su representación en JSON
     * @param <T> Tipo de objeto
     * @param obj El objeto a convertir
     * @return JSON, como string del objeto
      */
    public static <T> String toJson(T obj){
        return new Gson().toJson(obj);
    }

    /**
     * Convierte una representación JSON a objeto
     * @param <T> Tipo de objeto
     * @param json Representación JSON
     * @param clazz Clase a la que convertir el json
     * @return El objeto
      */
    public static <T> T fromJson(String json, Class<T> clazz){
        return new Gson().fromJson(json, clazz);
    }
}
