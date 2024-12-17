package git.pancitox77.ui.transitions;

import javafx.animation.Transition;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class StringPropertyTransition extends Transition {

    private final Duration defaultDuration = Duration.seconds(1);
    private final StringProperty property;
    private final String fromValue;
    private final String toValue;

    public StringPropertyTransition(StringProperty property, String startValue, String endValue) {
        this.property = property;
        this.fromValue = startValue;
        this.toValue = endValue;
        setCycleDuration(defaultDuration);
    }

    public StringPropertyTransition(StringProperty property, String startValue, String endValue, Duration duration) {
        this.property = property;
        this.fromValue = startValue;
        this.toValue = endValue;
        setCycleDuration(duration);
    }

    @Override
    protected void interpolate(double frac) {
        // Calculamos cuántos caracteres mostrar hasta el momento actual de la animación
        int totalChars = toValue.length() - fromValue.length();
        int length = (int) (fromValue.length() + totalChars * frac);

        String interpolatedString = toValue.substring(0, length);
        property.setValue(interpolatedString);
    }
}
