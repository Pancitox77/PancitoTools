package git.pancitox77.ui.transitions;

import java.util.function.Consumer;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Transiciones combinadas comúnes.
 * 
 * Estas transiciones pueden usarse tanto para nodos como para escenas (debido
 * a que estas heredan de Node).
 */
public class Transitions {

    private Transitions() {}

    /**
     * Oculta un nodo y luego muestra otro nodo, cambiando sus opacidades.
     * Al finalizar la animación, llama al callback onFinished.
     * 
     * @param duration   Cuánto dura la transición total. Tanto ocultar como mostrar
     *                   el nodo toma la
     *                   mitad de este tiempo.
     * @param node1      El nodo a ocultar
     * @param node2      El nodo a mostrar
     * @param onFinished Callback que se va a llamar cuando termine la transición.
     *                   Recibe un ActionEvent de la transición de mostrar (fade-in)
     * @see #fadeTransition(Duration, Node, Node)
     */
    public static void fadeTransition(Duration duration, Node node1, Node node2, Consumer<ActionEvent> onFinished) {
        FadeTransition fadeOut = new FadeTransition(duration.divide(2), node1);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(duration.divide(2), node2);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeIn.setOnFinished(ev -> onFinished.accept(ev));
        fadeOut.setOnFinished(_ -> fadeIn.play());
        fadeOut.play();
    }

    /**
     * Oculta un nodo y luego muestra otro nodo, cambiando sus opacidades.
     * 
     * @param duration   Cuánto dura la transición total. Tanto ocultar como mostrar
     *                   el nodo toma la
     *                   mitad de este tiempo.
     * @param node1      El nodo a ocultar
     * @param node2      El nodo a mostrar
     * @see #fadeTransition(Duration, Node, Node, Consumer)
     */
    public static void fadeTransition(Duration duration, Node node1, Node node2) {
        FadeTransition fadeOut = new FadeTransition(duration.divide(2), node1);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(duration.divide(2), node2);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        fadeOut.setOnFinished(_ -> fadeIn.play());
        fadeOut.play();
    }
}
