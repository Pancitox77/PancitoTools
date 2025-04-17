package git.pancitox77.collections;

import java.io.Serializable;
import java.util.ArrayList;

public class HashBoard<V> extends AbstractBoard<V> implements Serializable, Cloneable {

    private static final long serialVersionUID = 126131457567651L;
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    public HashBoard(int rows, int columns, float loadFactor) {
        this.elements = new ArrayList<>(rows);
        for (int i = 0; i < columns; i++) {
            this.elements.add(new ArrayList<>());
        }
    }
    

    public HashBoard() {
        this(DEFAULT_CAPACITY, DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }


    @Override
    public void put(int row, int column, V value) {
        if (row < 0 || column < 0) {
            throw new IndexOutOfBoundsException("Row and column must be non-negative");
        }

        // Si la fila no existe, crearla
        while (elements.size() <= row) {
            elements.add(new ArrayList<>());
        }

        // Si la columna no existe, crearla
        while (elements.get(row).size() <= column) {
            elements.get(row).add(null);
        }

        // Asignar el valor
        elements.get(row).set(column, value);
        totalSize++;
    }

    @Override
    public V remove(int row, int column) {
        if (row < 0 || column < 0) {
            throw new IndexOutOfBoundsException("Row and column must be non-negative");
        }

        if (row >= elements.size() || column >= elements.get(row).size()) {
            return null;
        }

        V value = elements.get(row).get(column);
        elements.get(row).set(column, null);
        totalSize--;

        return value;
    }

    @Override
    public void clear() {
        elements.clear();
        totalSize = 0;
        rowSize = 0;
        columnSize = 0;
    }
}
