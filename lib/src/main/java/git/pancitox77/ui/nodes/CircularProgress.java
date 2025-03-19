package git.pancitox77.ui.nodes;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * Nodo de progreso circular.
 * El nodo funciona como una barra de progreso que forma un círculo a
 * medida que se "llena" (el progreso avanza).
 * 
 * <br /> <br />
 * 
 * El usuario introduce el progreso (valor entre 0 y 1) y se dibuja el círculo.
 * <br /> <br />
 * 
 * El círculo de progreso inicia y termina en la parte superior del canvas. <br /> <br />
 * Si se desea otro comportamiento, lo recomendable es escalar el nodo.
 * 
 * <br /> <br />
 * 
 * Algunas opciones configurables son:<br /> <br />
 * - el progreso actual <br /> <br />
 * - el ancho de la línea del círculo <br /> <br />
 * - booleano indicando si se debe o no dibujar el círculo de fondo
 * 
 * <br /> <br />
 * 
 * Por defecto, el constructor del nodo crea un canvas de 100x100, con un ancho
 * de línea de 10px, dibujando el círculo de fondo.
*/
public class CircularProgress extends Canvas {
    
    public static final int DEFAULT_WIDTH = 100;
    public static final int DEFAULT_HEIGHT = 100;
    public static final int DEFAULT_STROKE_WIDTH = 10;

    private DoubleProperty progressProperty = new SimpleDoubleProperty(0);
    private DoubleProperty strokeWidthProperty = new SimpleDoubleProperty(10);
    private BooleanProperty drawBackgroundProperty = new SimpleBooleanProperty(true);

    /* Constructores */

    /**
     * Crea el nodo con los valores por defecto:<br /> <br />
     * - tamaño 100x100<br /> <br />
     * - ancho de línea 10px<br /> <br />
     * - dibujar círculo de fondo
    */
    public CircularProgress() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_STROKE_WIDTH);
    }

    /**
     * Crea el nodo con el tamaño y fondo por defecto:<br /> <br />
     * - tamaño 100x100<br /> <br />
     * - dibuja el círculo de fondo<br /> <br />
     * @param strokeWidth ancho de la línea de los círculos.
      */
    public CircularProgress(int strokeWidth) {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT, strokeWidth);
    }

    /**
     * Crea el nodo con el ancho de línea y fondo por defecto:<br /> <br />
     * - ancho de línea de 10px <br /> <br />
     * - dibuja el círculo de fondo <br /> <br />
     * Siempre se dibuja un círculo, por lo que se tomará el menor
        valor entre el ancho y el alto. No se dibuja un óvalo.
     * @param width Ancho
     * @param height Alto
      */
    public CircularProgress(double width, double height) {
        this(width, height, DEFAULT_STROKE_WIDTH);
    }

    public CircularProgress(double width, double height, int strokeWidth) {
        super(width, height);
        this.strokeWidthProperty.set(strokeWidth);
        progressProperty.addListener(_ -> draw());
        draw();
    }

    /* Propiedades */

    public DoubleProperty progressProperty() { return progressProperty; }
    public void setProgress(DoubleProperty progress) { progressProperty = progress; }
    public void setProgress(double progress) { progressProperty.set(Math.clamp(progress, 0, 1)); }
    public double getProgress() { return progressProperty.get(); }
    
    public DoubleProperty strokeWidthProperty() { return strokeWidthProperty; }
    public void setStrokeWidth(DoubleProperty strokeWidth) { strokeWidthProperty = strokeWidth; }
    public void setStrokeWidth(double strokeWidth) { strokeWidthProperty.set(strokeWidth); }
    public double getStrokeWidth() { return strokeWidthProperty.get(); }

    public BooleanProperty drawBackgroundProperty() { return drawBackgroundProperty; }
    public void setDrawBackground(BooleanProperty drawBackground) { drawBackgroundProperty = drawBackground; }
    public void setDrawBackground(boolean drawBackground) { drawBackgroundProperty.set(drawBackground); }
    public boolean getDrawBackground() { return drawBackgroundProperty.get(); }

    /**
     * Según el progreso, devuelve un color que va entre el rojo (progreso 0)
     * y el verde (progreso 1).
     * @param progress Progreso actual
     * @return Color un color entre el rojo y el verde
      */
    private Color getColor(double progress) {
        double r = 1 - progress;
        double g = progress;
        double b = 0;
        return Color.color(r, g, b);
    }

    /**
     * Se encarga de dibujar tanto el círculo de fondo (si está habilitado)
     * como el círculo de progreso.
    */
    private void draw() {
        double width = getWidth();
        double height = getHeight();
        double centerX = width / 2;
        double centerY = height / 2;
        double radius = Math.min(width, height) / 2 - 10; // Ajustar el radio para dejar espacio
        double progress = getProgress();
        double strokeWidth = getStrokeWidth();
        boolean drawBackground = getDrawBackground();

        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);

        // Fondo del círculo
        if (drawBackground) {
            gc.setStroke(Color.LIGHTGRAY);
            gc.setLineWidth(strokeWidth);
            gc.strokeOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }

        // Arco del progreso
        gc.setStroke(getColor(progress));
        gc.setLineWidth(strokeWidth);
        double angle = progress * 360;
        gc.strokeArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 90, -angle, ArcType.OPEN);
    }
}