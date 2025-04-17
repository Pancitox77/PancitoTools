package git.pancitox77.collections;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HashTable<R,C,V> extends AbstractTable<R,C,V> implements Serializable, Cloneable {

    @Override
    protected Object clone() {
        HashTable<R,C,V> table = new HashTable<>();
        table.putAll(this);
        return table;
    }

    @Override
    public V put(R row, C column, V value) {
        if (elements.containsKey(row)) {
            Map<C, V> map = elements.get(row);
            if (map.containsKey(column)) {
                return map.put(column, value);
            } else {
                map.put(column, value);
                return null;
            }
        } else {
            Map<C, V> map = new HashMap<>();
            map.put(column, value);
            elements.put(row, map);
            return null;
        }
    }

    @Override
    public V remove(R row, C column) {
        if (elements.containsKey(row)) {
            Map<C, V> map = elements.get(row);
            if (map != null) {
                return map.remove(column);
            }
        }
        return null;
    }
    
    @Override
    public void putAll(Table<R, C, V> table) {
        Map<R,Map<C, V>> map = table.values();
        for (R row : map.keySet()) {
            for (C column : map.get(row).keySet()) {
                V value = table.get(row, column);
                put(row, column, value);
            }
        }
    }

    @Override
    public void putAll(Map<R, Map<C, V>> map) {
        for (R row : map.keySet()) {
            Map<C, V> innerMap = map.get(row);
            if (innerMap != null) {
                for (C column : innerMap.keySet()) {
                    V value = innerMap.get(column);
                    put(row, column, value);
                }
            }
        }
    }

    @Override
    public void clear() {
        elements.clear();
    }
}
