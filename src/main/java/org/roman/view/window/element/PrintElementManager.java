package org.roman.view.window.element;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.roman.view.window.CircuitCanvas;

public class PrintElementManager
{
    CircuitCanvas canvas;
    Paint stroke;

    public PrintElementManager(CircuitCanvas canvas)
    {
        this.canvas = canvas;
        stroke = Color.BLACK;
    }

    public void printElement(ElementType element, Point2D p1, Point2D p2)
    {
        switch (element)
        {
            case IDLE:
                printIdle(p1, p2);
                break;
            case R:
                printR(p1, p2);
                break;
            case CURRENT:
                printCurrent(p1, p2);
                break;
            case VOLTAGE:
                printVoltage(p1, p2);
                break;
        }
    }

    private void printVoltage(Point2D p1, Point2D p2)
    {
        GraphicsContext gc = canvas.getGraphicsContext();
        gc.setStroke(stroke);
        gc.setFill(Color.WHITE);

        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        if (p1.getX() == p2.getX())
        {
            double x = p1.getX() - 10;
            double y = Math.min(p1.getY(), p2.getY()) + 10;

            gc.fillOval(x,y,20,20);
            gc.strokeOval(x,y,20,20);

            gc.strokeText("+ \n -", x, y);

        }
        else
        {
            double x = Math.min(p1.getX(), p2.getX()) + 10;
            double y = p1.getY() - 10;


            gc.fillOval(x,y,20,20);
            gc.strokeOval(x,y,20,20);

            gc.strokeText("+ -", x + 2, y + 12);
        }
    }

    private void printCurrent(Point2D p1, Point2D p2)
    {
        GraphicsContext gc = canvas.getGraphicsContext();
        gc.setStroke(stroke);
        gc.setFill(Color.WHITE);

        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        if (p1.getX() == p2.getX())
        {
            double x = p1.getX() - 10;
            double y = Math.min(p1.getY(), p2.getY()) + 10;

            gc.fillOval(x,y,20,20);
            gc.strokeOval(x,y,20,20);
        }
        else
        {
            double x = Math.min(p1.getX(), p2.getX()) + 10;
            double y = p1.getY() - 10;

            gc.fillOval(x,y,20,20);
            gc.strokeOval(x,y,20,20);
        }
    }

    private void printR(Point2D p1, Point2D p2)
    {
        GraphicsContext gc = canvas.getGraphicsContext();
        gc.setStroke(stroke);
        gc.setFill(Color.WHITE);

        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        if (p1.getX() == p2.getX())
        {
            double x = p1.getX() - 6;
            double y = Math.min(p1.getY(), p2.getY()) + 7;

            gc.fillRect(x,y,12,26);
            gc.strokeRect(x,y,12,26);

        }
        else
        {
            double x = Math.min(p1.getX(), p2.getX()) + 7;
            double y = p1.getY() - 6;


            gc.fillRect(x,y,26,12);
            gc.strokeRect(x,y,26,12);
        }
    }

    private void printIdle(Point2D p1, Point2D p2)
    {
        GraphicsContext gc = canvas.getGraphicsContext();

        gc.setStroke(stroke);
        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
}
