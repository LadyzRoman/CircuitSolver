package org.roman.controller;

import org.roman.model.Circuit;
import org.roman.model.element.Element;
import org.roman.model.element.Source;
import org.roman.view.window.WindowView;

import java.util.List;

public class MainController
{
    Circuit circuit;
    WindowView view;

    public MainController(WindowView view)
    {
        this.view = view;
        this.circuit = new Circuit(view.getSize());
        this.view.setController(this);
    }

    public void resize(int size)
    {
        this.circuit = new Circuit(circuit, size);
    }

    public void setElement(Element element, int node1, int node2)
    {
        circuit.connectNodes(element, circuit.getNode(node1), circuit.getNode(node2));
        if (element instanceof Source)
            circuit.setSource((Source) element);

        view.repaint();
    }



    public void calculate()
    {
        try
        {
            List<Element> element = circuit.calculateReactions();
            view.repaint();
        }
        catch (Exception e)
        {
            view.showError(e.getMessage());
        }
    }
}
