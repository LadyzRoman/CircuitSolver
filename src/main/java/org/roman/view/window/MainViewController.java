package org.roman.view.window;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.roman.view.window.element.ElementType;

public class MainViewController
{
    private WindowView mainApp;

    @FXML
    private RadioButton idle;

    @FXML
    private RadioButton r;

    @FXML
    private RadioButton voltageSource;

    @FXML
    private RadioButton currentSource;

    private ToggleGroup toggleGroup;


    public MainViewController()
    {
    }

    @FXML
    private void initialize()
    {
        toggleGroup = new ToggleGroup();
        idle.setUserData(ElementType.IDLE);
        r.setUserData(ElementType.R);
        voltageSource.setUserData(ElementType.VOLTAGE);
        currentSource.setUserData(ElementType.CURRENT);


        idle.setToggleGroup(toggleGroup);
        r.setToggleGroup(toggleGroup);
        voltageSource.setToggleGroup(toggleGroup);
        currentSource.setToggleGroup(toggleGroup);

        idle.fire();
    }

    public ElementType getCurrentElement()
    {
        return (ElementType) toggleGroup.getSelectedToggle().getUserData();
    }

    @FXML
    public void calculateCircuit()
    {
        mainApp.calculateCircuit();
    }

    public void setMainApp(WindowView mainApp)
    {
        this.mainApp = mainApp;
    }
}
