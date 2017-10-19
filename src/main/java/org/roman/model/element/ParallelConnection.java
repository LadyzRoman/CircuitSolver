package org.roman.model.element;


import org.roman.model.math.RationalFraction;

/**
 * Created by Roman on 11.10.2017.
 */
public class ParallelConnection extends Connection
{
    @Override
    public RationalFraction getResistance()
    {
        if (resistance.equals(RationalFraction.NULL))
            resistance = RationalFraction.ONE.div(getConductance());

        return resistance;
    }

    @Override
    public RationalFraction getVoltage()
    {
        if (voltage.equals(RationalFraction.NULL))
            voltage = getCurrent().mul(getResistance());

        return voltage;
    }

    @Override
    public RationalFraction getCurrent()
    {
        if (current.equals(RationalFraction.NULL))
            current = getVoltage().mul(getConductance());

        return current;
    }

    @Override
    public RationalFraction getConductance()
    {
        if (conductance.equals(RationalFraction.NULL))
            conductance = elements.stream().map(Element::getConductance).reduce(new RationalFraction(), RationalFraction::add);

        return conductance;
    }

    @Override
    public void setVoltage(RationalFraction voltage)
    {
        super.setVoltage(voltage);
        for (Element element : elements)
        {
            element.setVoltage(voltage);
            element.setCurrent(voltage.mul(element.getConductance()));
        }

        if (!current.equals(RationalFraction.NULL))
            super.setCurrent(getCurrent());
    }

    @Override
    public void setCurrent(RationalFraction current)
    {
        super.setCurrent(current);
        setVoltage(current.mul(getResistance()));
    }


    @Override
    public void normalize()
    {
        elements.forEach(Element::normalize);
    }
}
