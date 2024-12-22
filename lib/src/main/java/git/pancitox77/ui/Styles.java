package git.pancitox77.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class Styles {
    
    private Styles() {}

    public static Border border(Paint stroke) {
        return new Border(
                new BorderStroke(stroke, BorderStrokeStyle.SOLID, null, null));
    }

    public static Border border(Paint stroke, double radii){
        return new Border(
            new BorderStroke(stroke, BorderStrokeStyle.SOLID, new CornerRadii(radii), null));
    }

    public static Border border(Paint stroke, BorderStrokeStyle borderStyle){
        return new Border(
            new BorderStroke(stroke, borderStyle, null, null));
    }

    public static Border border(Paint stroke, BorderStrokeStyle borderStyle, double radii){
        return new Border(
            new BorderStroke(stroke, borderStyle, new CornerRadii(radii), null));
    }

    public static Border border(BorderStroke borderStroke) {
        return new Border(borderStroke);
    }

    public static Background background(Paint stroke){
        return new Background(
            new BackgroundFill(stroke, null, null));
    }

    public static Background background(Paint stroke, double radii){
        return new Background(
            new BackgroundFill(stroke, new CornerRadii(radii), null));
    }

    public static Background backgroundInsets(Paint stroke, double insets){
        return new Background(
            new BackgroundFill(stroke, null, new Insets(insets)));
    }

    public static Background background(Paint stroke, Insets insets){
        return new Background(
            new BackgroundFill(stroke, null, insets));
    }

    public static Background background(Paint stroke, double radii, double insets){
        return new Background(
            new BackgroundFill(stroke, new CornerRadii(radii), new Insets(insets)));
    }

    public static Background background(Paint stroke, double radii, Insets insets){
        return new Background(
            new BackgroundFill(stroke, new CornerRadii(radii), insets));
    }
}
