package git.pancitox77.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import git.pancitox77.classes.SignalCallback;
import lombok.Getter;

/**
 * Clase encargada de controlar señales.
 * Cuando una señal es disparada, se llama a todos los oyentes de esa señal.
 * Los oyentes no reciben argumentos ni proveen valores (interfaz: () -> {}).
  */
public class Signals {
    
    @Getter private static Map<String, List<SignalCallback>> callbacks = new HashMap<>();

    private Signals() {}

    /**
     * Agrega un oyente a una señal
     * @param signalName Nombre de la señal a escuchar
     * @param callback Función a llamar cuando se dispare la señal.
      */
    public static void register(String signalName, SignalCallback callback) {
        callbacks.computeIfAbsent(signalName, k -> new ArrayList<>());
        callbacks.get(signalName).add(callback);
    }

    /**
     * Elimina un oyente de una señal.
     * @param signalName Nombre de la señal en la que se encuentra el oyente.
     * @param callback El callback a eliminar
      */
    public static void unregister(String signalName, SignalCallback callback) {
        callbacks.getOrDefault(signalName, List.of()).remove(callback);
    }

    /**
     * Busca el oyente en todas las señales y lo elimina.
     * @param callback Callback a buscar y eliminar
     * @return True, si se encontró el callback, False sino.
      */
    public static boolean unregister(SignalCallback callback) {
        for (List<SignalCallback> cl : callbacks.values()) {
            if (cl.contains(callback)) {
                cl.remove(callback);
                return true;
            }
        }
        return false;
    }

    /**
     * Dispara una señal, notificando a todos los suscriptores.
     * @param signalName Nombre de la señal a disparar.
      */
    public static void trigger(String signalName) {
        callbacks
            .getOrDefault(signalName, List.of())
            .forEach(callback -> callback.call());
    }

    /**
     * Elimina todos los oyentes de una señal en específico.
     * Esta operación también elimina la llave
     * @param signalName Nombre de la señal a limpiar
      */
    public static void removeSignal(String signalName) {
        callbacks.remove(signalName);
    }

    /**
     * Elimina todos los oyentes de todas las señales
      */
    public static void clear() {
        callbacks.clear();
    }

    /**
     * Limpiar todos los oyentes de una señal específica.
     * Esta operación NO elimina la llave.
     * @param signalName Nombre de la señal a limpiar.
      */
    public static void clear(String signalName) {
        callbacks.getOrDefault(signalName, List.of()).clear();
    }
}
