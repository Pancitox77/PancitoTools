package git.pancitox77.ui.transitions;

import javafx.animation.Transition;
import javafx.beans.value.WritableValue;
import javafx.util.Duration;


/**
 * Crea una transición que cambia el valor de una propiedad (numérica)
 * a través del tiempo.
 * @param <T> El tipo de número que se va a cambiar.
*/
public class NumberPropertyTransition<T extends Number> extends Transition {

    private final Duration defaultDuration = Duration.seconds(1);
    private final WritableValue<T> property;
    private final T fromValue, toValue;

    public NumberPropertyTransition(WritableValue<T> property, T startValue, T endValue) {
        this.property = property;
        this.fromValue = startValue;
        this.toValue = endValue;
        setCycleDuration(defaultDuration);
    }

    public NumberPropertyTransition(WritableValue<T> property, T startValue, T endValue, Duration duration) {
        this.property = property;
        this.fromValue = startValue;
        this.toValue = endValue;
        setCycleDuration(duration);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void interpolate(double frac) {
        // Calculamos el valor intermedio entre fromValue y toValue
        double delta = toValue.doubleValue() - fromValue.doubleValue();
        double newValue = fromValue.doubleValue() + delta * frac;

        // Establecemos el nuevo valor en la propiedad
        property.setValue((T) (Number) newValue);
    }
}
