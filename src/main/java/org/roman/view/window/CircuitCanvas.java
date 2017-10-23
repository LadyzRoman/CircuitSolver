package org.roman.view.window;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class CircuitCanvas extends Pane
{
    public final static int GAP = 40;
    private final Canvas canvas;


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
     /*   for (int i = 0; i < canvas.getHeight(); i+=GAP)
            for (int j =0; j < canvas.getWidth(); j+=GAP)
            {
                gc.fillOval(i, j, 2,2);
            }*/
     gc.setFill(Color.WHITE);
     gc.fillRect(0,0, canvas.getWidth(), canvas.getHeight());
     gc.strokeRect(1,1,canvas.getWidth()-2,canvas.getHeight()-2);
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
        print();

    }
}
