package org.roman.model.element;

import org.roman.model.util.CurrentDirection;
import org.roman.model.util.ParallelConnectionRule;
import org.roman.model.util.VoltagePolarity;
import org.roman.model.graph.Link;

/**
 * Created by Roman on 09.10.2017.
 */
public class Element extends Link
{
    protected double resistance;
    protected double voltage;
    protected double current;
    protected double conductance;
    protected VoltagePolarity polariry;
    protected CurrentDirection direction;
    protected int index;
    protected ParallelConnectionRule parallelConnectionRule;
    private static int globalIndex = 0;


    public Element()
    {
        index = globalIndex + 1;
        globalIndex++;
    }

    public Element(Element element)
    {
        index = element.index;
        resistance = element.resistance;
        conductance = element.conductance;
        current = 0;
        voltage = 0;
    }

    public double getResistance()
    {
        return resistance;
    }

    public void setResistance(double resistance)
    {
        this.resistance = resistance;
    }

    public double getVoltage()
    {
        return voltage;
    }

    public void setVoltage(double voltage)
    {
        this.voltage = voltage;
    }

    public double getCurrent()
    {
        return current;
    }

    public void setCurrent(double current)
    {
        this.current = current;
    }

    public double getConductance()
    {
        return conductance;
    }

    public void setConductance(double conductance)
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
