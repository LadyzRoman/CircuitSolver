package org.roman.view.window.element;

import javafx.geometry.Point2D;
import org.roman.model.element.Element;

public class ElementWrapper
{
    Element element;
    ElementType type;
    Point2D p1;
    Point2D p2;

    PrintElementManager manager;

    public ElementWrapper(Element element, ElementType type, Point2D p1, Point2D p2)
    {
        this.element = element;
        this.type = type;
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setManager(PrintElementManager manager)
    {
        this.manager = manager;
    }

    public void draw()
    {
        manager.printElement(type, p1, p2);
    }


    public Point2D getP1()
    {
        return p1;
    }

    public Point2D getP2()
    {
        return p2;
    }
}
