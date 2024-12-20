package git.pancitox77.ui.nodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.NonNull;

public class RadarChart extends Canvas {

    public static final float DEFAULT_SIZE = 200f;
    private static final Color DEFAULT_VALUE_ZONE_COLOR = new Color(0, 0, 1, 0.6);
    private static final Color DEFAULT_EDGE_CIRCLE_COLOR = Color.BLACK;

    private Map<String, Float> values = new HashMap<>();
    private Point2D center;
    private float radius;
    private int edgesCount = 0;
    private int rings = 1;

    private Color valueZoneColor = DEFAULT_VALUE_ZONE_COLOR;
    private float edgeToLabelDistance = 20f;
    private float edgeSize = 0f;
    private Color edgeCircleColor = DEFAULT_EDGE_CIRCLE_COLOR;
    private Font labelFont = Font.getDefault();
    private List<Point2D> edgePoints;

    public RadarChart(@NonNull Map<String, Float> values) {
        this(values, 1, DEFAULT_SIZE);
    }

    public RadarChart(@NonNull Map<String, Float> values, int rings) {
        this(values, rings, DEFAULT_SIZE);
    }

    public RadarChart(Map<String, Float> values, int rings, float size) {
        super(size, size);
        validateValues(values);
        this.values = values;
        this.rings = rings;

        calculateMargin(size, values.keySet());
        initializeChart(size);
        draw();
    }

    private void validateValues(Map<String, Float> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("No se pueden crear gráficos de araña vacíos");
        }
    }

    private void calculateMargin(float size, Set<String> keys) {
        double maxLabelWidth = 0;
        double maxLabelHeight = 0;
        for (String label : keys) {
            Bounds bounds = reportSize(label, labelFont);
            maxLabelWidth = Math.max(maxLabelWidth, bounds.getWidth());
            maxLabelHeight = Math.max(maxLabelHeight, bounds.getHeight());
        }

        double margin = Math.max(maxLabelWidth, maxLabelHeight) + edgeToLabelDistance;
        setWidth(size + margin * 2);
        setHeight(size + margin * 2);
    }

    private void initializeChart(float size) {
        center = new Point2D(getWidth() / 2, getHeight() / 2);
        edgesCount = values.size();
        radius = size / 2;
        edgePoints = calculateEdgePoints(radius);
    }

    private List<Point2D> calculateEdgePoints(float radii) {
        List<Point2D> points = new ArrayList<>(edgesCount);
        float currentAngle = 0;
        float degreesStep = 360f / edgesCount;

        for (int j = 0; j < edgesCount; j++) {
            float angle = currentAngle + (degreesStep / 2);
            points.add(polarToCartesian(center, radii, angle));
            currentAngle += degreesStep;
        }
        return points;
    }

    private void draw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFont(labelFont);
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.moveTo(center.getX(), center.getY());

        drawEdgesConnections(gc);
        drawRings(gc, rings);
        drawValues(gc);
    }

    private void drawEdgesConnections(GraphicsContext gc) {
        if (edgesCount < 1) return;

        for (Point2D pointPos : edgePoints) {
            connect(gc, center, pointPos);
            drawEdgeCircle(gc, pointPos);
        }
    }

    private void drawEdgeCircle(GraphicsContext gc, Point2D pointPos) {
        if (edgeSize > 0) {
            Paint prevFill = gc.getFill();
            gc.setFill(edgeCircleColor);
            gc.fillOval(pointPos.getX() - edgeSize / 2, pointPos.getY() - edgeSize / 2, edgeSize, edgeSize);
            gc.setFill(prevFill);
        }
    }

    private void drawRings(GraphicsContext gc, int rings) {
        if (rings < 1) return;

        float separation = radius / rings;
        for (int i = 0; i < rings; i++) {
            float radii = separation * (i + 1);
            gc.strokeOval(center.getX() - radii, center.getY() - radii, radii * 2, radii * 2);
        }
    }

    private void drawValues(GraphicsContext gc) {
        Set<Map.Entry<String, Float>> entries = this.values.entrySet();
        List<Point2D> valuesPoints = new ArrayList<>(entries.size());

        float currentAngle = 0;
        float degressStep = 360 / edgesCount; // en grados (°)

        for (Map.Entry<String, Float> entry : entries) {
            String text = entry.getKey();
            float value = entry.getValue();
            float angle = currentAngle + (degressStep / 2);
            float radii = radius * value;

            drawLabel(gc, text, angle);

            Point2D valuePoint = polarToCartesian(center, radii, angle);
            valuesPoints.add(valuePoint);

            currentAngle += degressStep;
        }

        drawFilledRing(gc, valuesPoints, valueZoneColor);
    }

    private void drawFilledRing(GraphicsContext gc, List<Point2D> points, Color fillColor) {
        Paint prevColor = gc.getFill();
        gc.setFill(fillColor);

        double[] xs = new double[points.size()];
        double[] ys = new double[points.size()];
        for (int i = 0; i < points.size(); i++) {
            Point2D point = points.get(i);
            xs[i] = point.getX();
            ys[i] = point.getY();
        }

        gc.fillPolygon(xs, ys, points.size());

        gc.setFill(prevColor);
    }

    private void drawLabel(GraphicsContext gc, String labelText, float angle) {
        // Calcular la distancia desde el centro considerando el margen y los radios
        float edgeRadius = edgeSize / 2; // Si hay círculos en los bordes
        float margin = edgeToLabelDistance;
        double labelRadius = radius + edgeRadius + margin;
    
        // Obtener la posición cartesiana para el texto
        Point2D labelPoint = polarToCartesian(center, (float) labelRadius, angle);
    
        // Centrar el texto con base en su tamaño
        Bounds textBounds = reportSize(labelText, labelFont);
        double textWidth = textBounds.getWidth();
        double textHeight = textBounds.getHeight();
        double x = labelPoint.getX() - (textWidth / 2);
        double y = labelPoint.getY() + (textHeight / 4); // Ajuste para centrado vertical
    
        // Dibujar el texto en las coordenadas ajustadas
        gc.fillText(labelText, x, y);
    }

    private Point2D polarToCartesian(Point2D center, float radius, float angle) {
        double radians = Math.toRadians(angle);
        double x = center.getX() + radius * Math.cos(radians);
        double y = center.getY() + radius * Math.sin(radians);
        return new Point2D(x, y);
    }

    public Bounds reportSize(String text, Font font) {
        Text t = new Text(text);
        t.setFont(font);
        return t.getLayoutBounds();
    }

    private void connect(GraphicsContext gc, Point2D from, Point2D to) {
        double x1 = from.getX();
        double y1 = from.getY();
        double x2 = to.getX();
        double y2 = to.getY();
        gc.strokeLine(x1, y1, x2, y2);
    }

    // Getters and Setters for properties
    public void setEdgeSize(float edgeSize) {
        if (edgeSize < 0) {
            throw new IllegalArgumentException("El tamaño del borde no puede ser negativo");
        }
        this.edgeSize = edgeSize;
        draw();
    }

    public void setValueZoneColor(Color color) {
        this.valueZoneColor = color;
        draw();
    }

    public void setEdgeCircleColor(Color color) {
        this.edgeCircleColor = color;
        draw();
    }

    public void setLabelFont(Font font) {
        this.labelFont = font;
        draw();
    }
}