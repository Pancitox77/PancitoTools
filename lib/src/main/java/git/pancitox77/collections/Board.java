package git.pancitox77.collections;

import java.util.List;

/**
 * Estructura que representa un tablero. Es similar a una tabla, pero
 * se diferencia en que se accede a los elementos usando índices enteros
 * en lugar de claves, y que los datos se almacenan en un doble array.
*/
public interface Board<V> {
    
    void put(int row, int column, V value);
    V get(int row, int column);
    V remove(int row, int column);
    boolean contains(int row, int column);
    boolean contains(V value);
    boolean isEmpty();
    int rowSize();
    int columnSize();
    List<List<V>> values();
    void clear();
}
