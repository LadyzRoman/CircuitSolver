package org.roman.view.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.roman.controller.MainController;
import org.roman.model.element.*;
import org.roman.model.math.RationalFraction;
import org.roman.view.window.element.ElementWrapper;
import org.roman.view.window.element.PrintElementManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WindowView extends Application
{

    private CircuitCanvas canvas;
    private MainViewController buttonController;
    private PrintElementManager printManager;
    private List<ElementWrapper> printingElements = new ArrayList<>();
    private MainController controller;


    public static void main(String[] args)
    {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage)
    {

        try
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("Main.fxml"));
            BorderPane pane = loader.load();

            primaryStage.setScene(new Scene(pane));

            canvas = new CircuitCanvas(400, 400);
            pane.setCenter(canvas);
            primaryStage.setResizable(true);

            canvas.setMouseEvent(v ->
            {
                if (v.getButton() == MouseButton.PRIMARY)
                {
                    connectClosestPoints(v.getX(), v.getY());
                }
                else if (v.getButton() == MouseButton.SECONDARY)
                {
                    severClosestPoints(v.getX(), v.getY());
                }

            });

            buttonController = loader.getController();
            buttonController.setMainApp(this);
            printManager = new PrintElementManager(canvas);
            canvas.initPoints();
            controller = new MainController(this);


            repaint();
            primaryStage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    private void severClosestPoints(double x, double y)
    {
        Point2D point = new Point2D(x, y);

        List<Point2D> closestPoints = canvas.getPoints().stream().sorted(Comparator.comparing(point::distance)).limit(2).collect(Collectors.toList());

        Optional<ElementWrapper> el = printingElements.stream()
                .filter(wr -> (wr.getP1().equals(closestPoints.get(0)) && wr.getP2().equals(closestPoints.get(1)))
                || (wr.getP1().equals(closestPoints.get(1)) && wr.getP2().equals(closestPoints.get(0))))
                .findFirst();

        if (el.isPresent())
        {
            printingElements.remove(el.get());
            controller.setElement(null, canvas.getPoints().indexOf(closestPoints.get(0)), canvas.getPoints().indexOf(closestPoints.get(1)));
        }


    }

    private void connectClosestPoints(double x, double y)
    {
        Point2D point = new Point2D(x, y);
        List<Point2D> closestPoints = canvas.getPoints()
                .stream()
                .sorted(Comparator.comparing(point::distance))
                .limit(2)
                .collect(Collectors.toList());

        if (printingElements.stream()
                .anyMatch(wr -> (wr.getP1().equals(closestPoints.get(0)) && wr.getP2().equals(closestPoints.get(1)))
                        || (wr.getP1().equals(closestPoints.get(1)) && wr.getP2().equals(closestPoints.get(0)))))
        return;

        Element element = getElement();
        ElementWrapper wr = new ElementWrapper(element, buttonController.getCurrentElement(), closestPoints.get(0), closestPoints.get(1));
        wr.setManager(printManager);
        printingElements.add(wr);
        controller.setElement(element, canvas.getPoints().indexOf(closestPoints.get(0)), canvas.getPoints().indexOf(closestPoints.get(1)));
    }

    public void setController(MainController controller)
    {
        this.controller = controller;
    }


    private Element getElement()
    {
        switch (buttonController.getCurrentElement())
        {
            case IDLE:
                return new Idle();
            case R:
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Resistance");
                dialog.setContentText("Enter resistance value");
                dialog.setHeaderText(null);

                Optional<String> result = dialog.showAndWait();

                return result.map(s -> new R(RationalFraction.parseRF(s))).orElseGet(null);
            case VOLTAGE:
                dialog = new TextInputDialog();
                dialog.setTitle("Voltage");
                dialog.setContentText("Enter voltage value");
                dialog.setHeaderText(null);

                result = dialog.showAndWait();

                return result.map(s -> new VoltageSource(RationalFraction.parseRF(s))).orElseGet(null);
            case CURRENT:
                dialog = new TextInputDialog();
                dialog.setTitle("Current");
                dialog.setContentText("Enter current value");
                dialog.setHeaderText(null);

                result = dialog.showAndWait();

                return result.map(s -> new CurrentSource(RationalFraction.parseRF(s))).orElseGet(null);
        }

        return null;
    }


    public void calculateCircuit()
    {
        controller.calculate();
    }

    public int getSize()
    {
        return canvas.getPoints().size();
    }

    public void repaint()
    {
        canvas.clear();
        canvas.print();
        printingElements.forEach(ElementWrapper::draw);

    }

    public void showError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error!");
        alert.setContentText(message);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
}
