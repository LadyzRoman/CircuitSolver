package org.roman.model;


import org.roman.model.element.Element;
import org.roman.model.element.Source;
import org.roman.model.graph.Graph;
import org.roman.model.graph.Link;
import org.roman.model.graph.Node;
import org.roman.model.math.RationalFraction;
import org.roman.view.console.Formatter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Roman on 09.10.2017.
 */
public class Circuit extends Graph
{
    private List<Source> sources = new ArrayList<>();
    protected List<Element> elements = new ArrayList<>();


    public Circuit(int size)
    {
        super(size);
    }

    public Circuit(Circuit circuit, int size)
    {
        super(size);
        for (Element element : circuit.getElements())
        {
            connectNodes(element.copy(), getNode(element.getSource().getIndex()), getNode(element.getDestination().getIndex()));
        }
    }

    public void setSource(Source source)
    {
        sources.add(source);
    }

    @Override
    public void connectNodes(Link link, Node source, Node dependent)
    {
        if (link != null)
        {
            super.connectNodes(link, source, dependent);
            elements.add((Element) link);
        }
    }

    public List<Element> calculateReactions()
    {
        elements.stream()
                .filter(e -> !sources.contains(e))
                .forEach(e ->
                {
                    e.setCurrent(new RationalFraction());
                    e.setVoltage(new RationalFraction());
                });

        sources.forEach(this::calculatePartialReactions);

        return elements;
    }

    private void calculatePartialReactions(Source source)
    {
        OneSourceCircuit circuit = copyOfThis(source);

        circuit.connectResistance();
        circuit.calculate();

        circuit.elements.forEach(this::addReaction);


        System.out.println(new Formatter(circuit).getPrettyView());

    }

    private void addReaction(Element element)
    {
        if (!elements.contains(element) || sources.contains(element)) return;

        elements.stream().filter(element::equals).forEach( e ->
        {
            e.setVoltage(e.getVoltage().add(element.getVoltage()));
            e.setCurrent(e.getCurrent().add(element.getCurrent()));
        });
    }

    private OneSourceCircuit copyOfThis(Source source)
    {
        OneSourceCircuit circuit = new OneSourceCircuit(getSize());
        for (Element element: elements)
        {
            if (sources.contains(element) && !element.equals(source))
            {
                circuit.connectNodes(((Source) element).getReplacement(), circuit.getNode(element.getSource().getIndex()), circuit.getNode(element.getDestination().getIndex()));
                continue;
            }
            else if (element.equals(source))
                circuit.setIdle(source.copy());

            circuit.connectNodes(element.copy(), circuit.getNode(element.getSource().getIndex()), circuit.getNode(element.getDestination().getIndex()));
        }
        return circuit;
    }

    public List<Element> getElements()
    {
        return elements;
    }
}
