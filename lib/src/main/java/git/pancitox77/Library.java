/*
 * This source file was generated by the Gradle 'init' task
 */
package git.pancitox77;

import git.pancitox77.ui.transitions.NumberPropertyTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Library {
    public boolean someLibraryMethod() {
        return true;
    }

    public void opacityTransition() {
        DoubleProperty opacity = new SimpleDoubleProperty(0);

        NumberPropertyTransition<Number> transition = new NumberPropertyTransition<>(
            opacity,
            0,
            1
        );
        transition.setOnFinished(_ -> System.out.println("Opacidad final: " + opacity.get()));
        transition.play();
    }
}
