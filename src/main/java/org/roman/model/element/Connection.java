package org.roman.model.element;

import org.roman.model.element.Element;
import org.roman.model.graph.Link;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 11.10.2017.
 */
public abstract class Connection extends Element
{
    protected List<Element> elements = new ArrayList<>();

    public void addElement(Element element)
    {
        elements.add(element);
    }

    public void addElements(List<Link> linksToNode)
    {
        for (Link link : linksToNode)
            elements.add((Element) link);
    }

    public List<Element> getElements()
    {
        return elements;
    }

    public void removeElement(Element element)
    {
        elements.remove(element);
    }


}
