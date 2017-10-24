package org.roman.view.window;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;


public class CircuitCanvas extends Pane
{
    public final static int GAP = 40;
    private final Canvas canvas;
    private List<Point2D> points = new ArrayList<>();

    public CircuitCanvas(double width, double height) {
        canvas = new Canvas(width, height);
        getChildren().add(canvas);
    }



    public Canvas getCanvas()
    {
        return canvas;
    }

    public void print()
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        points.forEach(p -> gc.fillOval(p.getX(), p.getY(), 2,2));
    }

    @Override
    protected void layoutChildren()
    {
        super.layoutChildren();
        double x = snappedLeftInset();
        double y = snappedTopInset();
        double h = snapSize(getHeight());
        double w = snapSize(getWidth());
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        canvas.setHeight(h);
        canvas.setWidth(w);
        initPoints();
        print();
    }

    void initPoints()
    {
        points.clear();
        for (int i = 0; i < canvas.getHeight(); i+=GAP)
            for (int j =0; j < canvas.getWidth(); j+=GAP)
                points.add(new Point2D(j, i));
    }

    public void setMouseEvent(EventHandler<MouseEvent> value) {
        canvas.setOnMouseClicked(value);
    }

    public List<Point2D> getPoints()
    {
        return points;
    }

    public GraphicsContext getGraphicsContext()
    {
        return canvas.getGraphicsContext2D();
    }

    public void clear()
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITESMOKE);

        gc.fillRect(0,0, getWidth(), getHeight());
    }
}
