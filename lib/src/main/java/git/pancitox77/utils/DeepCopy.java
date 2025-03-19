package git.pancitox77.utils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import git.pancitox77.classes.FastByteArrayOutputStream;
import lombok.SneakyThrows;

public class DeepCopy {

    private DeepCopy() {}

    @SneakyThrows
    /**
     * Realiza una copia profunda de un objeto.
     * Para esto, serializa y deserializa el objeto.
     * Para reducir el consumo de recursos se usa clases custom de OutputStream e
     * InputStream, sin embargo, el proceso sigue siendo lento no se recomienda
     * abusar de esto.
     * 
     * @param orig Objeto original
     * @return Copia profunda del objeto
     */
    public static Object copy(Object orig) {
        // Escribe la salida del objeto en un stream
        FastByteArrayOutputStream fbos = new FastByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(fbos);
        out.writeObject(orig);
        out.flush();
        out.close();

        // Obtiene los datos del stream de entrada y devuelve el objeto leído
        ObjectInputStream in = new ObjectInputStream(fbos.getInputStream());
        return in.readObject();
    }
}
