package org.roman.view.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowView extends Application
{

    public static void main(String[] args)
    {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage)
    {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("Main.fxml"));
            BorderPane pane = (BorderPane) loader.load();

            primaryStage.setScene(new Scene(pane));

            CircuitCanvas canvas = new CircuitCanvas(300,300);

            pane.setCenter(canvas);



            primaryStage.show();



            canvas.print();
 /*       Pane rootPane = new Pane();

        CircuitCanvas canvas = new CircuitCanvas(800, 800);

        rootPane.getChildren().addAll(canvas);

        canvas.toFront();

        Scene scene = new Scene(rootPane, 800, 800);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);



        primaryStage.show();

        //canvas.printNodes();

        //GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.print();


        //gc.fillOval(100,100,20,20);*/
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
