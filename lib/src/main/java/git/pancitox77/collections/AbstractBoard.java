package git.pancitox77.collections;

import java.util.List;

/**
 * Esqueleto para la implementación de un tablero.
 * Esta clase implementa funcionalidad básica de un tablero.
 * Por defecto, los métodos modificadores lanzan UnsupportedOperationException.
*/
public abstract class AbstractBoard<V> implements Board<V> {

    protected List<List<V>> elements;
    protected int rowSize = 0;
    protected int columnSize = 0;
    protected int totalSize = 0;

    protected AbstractBoard() {}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == null ? false :
                (obj instanceof Board<?> board ? this.elements.equals(board.values()) : false);
    }

    @Override
    public boolean contains(int row, int column) {
        if (row < 0 || row >= rowSize || column < 0 || column >= columnSize) {
            return false;
        }
        return elements.get(row).get(column) != null;
    }

    @Override
    public boolean contains(V value) {
        if (value == null) {
            return false;
        }
        for (List<V> row : elements) {
            if (row.contains(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(int row, int column) {
        if (row < 0 || row >= rowSize || column < 0 || column >= columnSize) {
            return null;
        }
        return elements.get(row).get(column);
    }

    @Override
    public boolean isEmpty() {
        return totalSize <= 0;
    }

    @Override
    public void put(int row, int column, V value) {
        throw new UnsupportedOperationException("put() not supported");
    }

    @Override
    public V remove(int row, int column) {
        throw new UnsupportedOperationException("remove() not supported");
    }

    @Override
    public int rowSize() {
        return rowSize;
    }

    @Override
    public int columnSize() {
        return columnSize;
    }

    @Override
    public List<List<V>> values() {
        return elements;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("clear() not supported");
    }
}
