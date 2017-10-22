package org.roman.view.window;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class CircuitCanvas extends Canvas
{
    public final static int GAP = 40;

    public CircuitCanvas() {
    }

    public CircuitCanvas(double width, double height) {
        super(width, height);
    }

    public void print()
    {
        GraphicsContext gc = getGraphicsContext2D();
        for (int i = 0; i < getHeight(); i+=GAP)
            for (int j =0; j < getWidth(); j+=GAP)
            {
                gc.fillOval(i, j, 2,2);
            }
    }
}
