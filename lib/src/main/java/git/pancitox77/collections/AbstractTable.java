package git.pancitox77.collections;

import java.util.HashMap;
import java.util.Map;


/**
 * Esqueleto para la creación de tablas.
 * Esta clase implementa la interfaz {@link Table} y proporciona
 * implementaciones por defecto para algunos de sus métodos.
*/
public abstract class AbstractTable<R, C, V> implements Table<R, C, V> {

    protected Map<R, Map<C, V>> elements;

    protected AbstractTable(Map<R, Map<C, V>> table) {
        this.elements = table;
    }

    protected AbstractTable() {
        this.elements = new HashMap<>();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear() not supported.");
    }

    @Override
    public int columnSize() {
        return elements.size() == 0 ? 0 : elements.values().iterator().next().size();
    }

    @Override
    public boolean containsColumn(C column) {
        for (Map<C, V> map : elements.values()) {
            if (map.containsKey(column)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsRow(R row) {
        return elements.containsKey(row);
    }

    @Override
    public boolean containsValue(V value) {
        for (Map<C, V> map : elements.values()) {
            if (map.containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(R row, C column) {
        if (elements.containsKey(row)) {
            Map<C, V> map = elements.get(row);
            if (map != null) {
                return map.get(column);
            }
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public V put(R row, C column, V value) {
        throw new UnsupportedOperationException("put() not supported.");
    }

    @Override
    public void putAll(Table<R, C, V> table) {
        throw new UnsupportedOperationException("putAll() not supported.");
    }

    @Override
    public void putAll(Map<R, Map<C, V>> map) {
        throw new UnsupportedOperationException("putAll() not supported.");
    }

    @Override
    public V remove(R row, C column) {
        throw new UnsupportedOperationException("remove() not supported.");
    }

    @Override
    public int rowSize() {
        return elements.size();
    }

    @Override
    public Map<R, Map<C, V>> values() {
        return elements;
    }
}
