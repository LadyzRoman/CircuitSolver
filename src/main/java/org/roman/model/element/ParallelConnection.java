package org.roman.model.element;


/**
 * Created by Roman on 11.10.2017.
 */
public class ParallelConnection extends Connection
{
    @Override
    public double getResistance()
    {
        if (resistance == 0)
            resistance = 1 / getConductance();

        return resistance;
    }

    @Override
    public double getVoltage()
    {
        if (voltage == 0)
            voltage = getCurrent() * getResistance();

        return voltage;
    }

    @Override
    public double getCurrent()
    {
        if (current == 0)
            current = getVoltage() * getConductance();

        return current;
    }

    @Override
    public double getConductance()
    {
        if (conductance == 0)
            for (Element element : elements)
                conductance += element.getConductance();

        return conductance;
    }

    @Override
    public void setVoltage(double voltage)
    {
        super.setVoltage(voltage);
        for (Element element : elements)
        {
            element.setVoltage(voltage);
            element.setCurrent(voltage * element.getConductance());
        }

        if (current == 0)
            super.setCurrent(getCurrent());
    }

    @Override
    public void setCurrent(double current)
    {
        super.setCurrent(current);
        setVoltage(current * getResistance());
    }


    @Override
    public void normalize()
    {
        for (Element element : elements)
        {
            element.normalize();
        }
    }
}
