package org.roman.model.element;

import org.roman.model.element.Element;
import org.roman.model.graph.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        elements.addAll(linksToNode.stream()
                .map(l -> (Element) l)
                .collect(Collectors.toList()));
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
