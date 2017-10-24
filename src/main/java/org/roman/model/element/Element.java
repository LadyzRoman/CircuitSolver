package org.roman.model.element;

import javafx.scene.canvas.GraphicsContext;
import org.roman.model.math.RationalFraction;
import org.roman.model.util.CurrentDirection;
import org.roman.model.util.ParallelConnectionRule;
import org.roman.model.util.VoltagePolarity;
import org.roman.model.graph.Link;

/**
 * Created by Roman on 09.10.2017.
 */
public class Element extends Link
{
    protected RationalFraction resistance;
    protected RationalFraction voltage;
    protected RationalFraction current;
    protected RationalFraction conductance;
    protected VoltagePolarity polariry;
    protected CurrentDirection direction;
    protected ParallelConnectionRule parallelConnectionRule;

    private static int globalIndex = 0;
    protected int index;


    public Element()
    {
        voltage = new RationalFraction();
        current = new RationalFraction();
        resistance = new RationalFraction();
        conductance = new RationalFraction();
        index = globalIndex + 1;
        globalIndex++;
    }

    public Element(Element element)
    {
        index = element.index;
        resistance = element.resistance;
        conductance = element.conductance;
        current = new RationalFraction();
        voltage = new RationalFraction();
    }

    public RationalFraction getResistance()
    {
        return resistance;
    }

    public void setResistance(RationalFraction resistance)
    {
        this.resistance = resistance;
    }

    public RationalFraction getVoltage()
    {
        return voltage;
    }

    public void setVoltage(RationalFraction voltage)
    {
        this.voltage = voltage;
    }

    public RationalFraction getCurrent()
    {
        return current;
    }

    public void setCurrent(RationalFraction current)
    {
        this.current = current;
    }

    public RationalFraction getConductance()
    {
        return conductance;
    }

    public void setConductance(RationalFraction conductance)
    {
        this.conductance = conductance;
    }

    public void normalize()
    {

    }

    public ParallelConnectionRule getParallelConnectionRule()
    {
        return parallelConnectionRule;
    }

    public void setParallelConnectionRule(ParallelConnectionRule parallelConnectionRule)
    {
        this.parallelConnectionRule = parallelConnectionRule;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Element element = (Element) o;

        return index == element.index;
    }


    @Override
    public int hashCode()
    {
        return index;
    }


    public Element copy()
    {
        return new Element(this);
    }
}
