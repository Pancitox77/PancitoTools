package git.pancitox77.collections;

import java.util.Map;

/**
 * Estructura que representa una tabla, es decir, un array bidimensional.
 * Esta tabla tiene filas y columnas, y cada celda puede contener un valor.
*/
public interface Table<R, C, V> {
    
    void clear();
    V get(R row, C column);
    V put(R row, C column, V value);
    V remove(R row, C column);
    boolean containsRow(R row);
    boolean containsColumn(C column);
    boolean containsValue(V value);
    int rowSize();
    int columnSize();
    boolean isEmpty();
    Map<R, Map<C, V>> values();
    void putAll(Table<R, C, V> table);
    void putAll(Map<R, Map<C, V>> map);
}
