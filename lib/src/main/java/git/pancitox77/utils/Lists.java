package git.pancitox77.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/** Herramientas para listas */
public class Lists {
    
    private Lists(){}

    /**
     * Comprueba si el valor está en la lista
     * @param <T> T tipo de la lista
     * @param value El valor a buscar
     * @param list La lista de valores en la que buscar
     * @return true si está, false si no
     */
    public static <T> boolean in(T value, T[] list) {
        for (T element : list) {
            if (element.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Comprueba si el valor está en la lista
     * @param <T> tipo de la lista
     * @param value El valor a buscar
     * @param list La lista de valores en la que buscar
     * @return true si está, false si no
     */
    public static <T> boolean in(T value, List<T> list) {
        return list.contains(value);
    }

    /**
     * Comprueba si el valor es el último en la lista
     * @param <T> El tipo de la lista
     * @param element El elemento a verificar
     * @param elements La lista
     * @return true, si el elemento es igual al último elemento en la lista
     */
    public static <T> boolean isLast(T element, T[] elements){
        return element == elements[elements.length - 1];
    }

    /**
     * Comprueba si el valor es el último en la lista
     * @param <T> El tipo de la lista
     * @param element El elemento a verificar
     * @param elements La lista
     * @return true, si el elemento es igual al último elemento en la lista
     */
    public static <T> boolean isLast(T element, Collection<T> elements){
        int lastIndex = elements.size() - 1;
        return element == elements.toArray()[lastIndex];
    }

    /**
     * Comprueba si el valor es el último en la lista
     * @param <T> El tipo de la lista
     * @param element El elemento a verificar
     * @param elements La lista
     * @return true, si el elemento es igual al último elemento en la lista
     */
    public static <T> boolean isLast(T element, List<T> elements){
        return element == elements.get(elements.size() - 1);
    }

    /**
     * Comprueba si el valor es el último en el map
     * Como Map usa un Set para guardar sus entradas, este método
     * puede ser impreciso
     * @param <K> El tipo de llave del mapa
     * @param <V> El tipo de valor del mapa
     * @param key El elemento a verificar
     * @param map El map
     * @return true, si el elemento es igual al último elemento en el map
     */
    public static <K, V> boolean isLast(Entry<K, V> key, Map<K, V> map) {
        Object[] entries = map.entrySet().toArray();
        return key.equals(entries[map.size() - 1]);
    }

    /**
     * Convierte una lista es una cadena de caracteres
     * @param <T> El tipo de elemento
     * @param elements La lista a convertir.
     * @param separator Cadena que se va a colocar entre elementos
     * @param includeBrackets Si es verdadero, se incluirá "[" y "]" al inicio y al final, respectivamente
     * @return Representación de la lista, como cadena de caracteres.
      */
    public static <T> String listToString(T[] elements, String separator, boolean includeBrackets) {
        StringBuilder sb = new StringBuilder();

        // Apertura
        if (includeBrackets)
            sb.append("[");

        // Elementos
        for(T element: elements) {
            sb.append(element);

            if (!isLast(element, elements))
                sb.append(separator);
        }

        // Cierre
        if (includeBrackets)
            sb.append("]");

        return sb.toString();
    }

    /**
     * Convierte una cadena de caracteres a una lista.
     * El string puede contener corchetes. Se usa el formato de {@link #stringToList(String, String, Class)},
     * pero a la inversa.
     * En caso de error, lanza un RuntimeException.
     * @param <T> El tipo de la lista
     * @param str El string a convertir
     * @param separator String que separa cada elemento
     * @param clazz La clase a la que convertir el string
     * @return La lista
      */
    public static <T> List<T> stringToList(String str, String separator, Class<T> clazz) throws RuntimeException {
        // Remover corchetes ("[]") si los tiene
        boolean hasOpenBracket = str.startsWith("[");
        boolean hasCloseBracket = str.endsWith("]");

        int from = hasOpenBracket ? 1 : 0;
        int to = hasCloseBracket ? str.length() - 1 : str.length();
        str = str.substring(from, to);

        // Convertir el string a lista de string
        String[] elements = str.split(separator);
        List<T> list = new ArrayList<>(elements.length);

        // Convertir cada string al tipo indicado
        for (String element: elements) {
            try {
                list.add(clazz.getConstructor(String.class).newInstance(element));
            }
            catch (Exception e) {
                throw new RuntimeException("Unable to cast string to " + clazz, e);
            }
        }
        return list;
    }

    /**
     * Ordena una lista de strings por orden alfabético
     * @param list La lista a ordenar
     * @return La lista ordenada (copia de la original)
      */
    public static List<String> sort(List<String> list){
        List<String> copy = List.copyOf(list);

        copy.sort(String::compareTo);
        return copy;
    }
}
