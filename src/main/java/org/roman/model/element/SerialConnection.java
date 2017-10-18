package org.roman.model.element;


/**
 * Created by Roman on 11.10.2017.
 */
public class SerialConnection extends Connection
{
    @Override
    public double getResistance()
    {
        if (resistance == 0)
            resistance = elements.stream().mapToDouble(Element::getResistance).sum();

        return resistance;
    }

    @Override
    public double getVoltage()
    {
        if (voltage == 0)
        {
            voltage = getCurrent() * getResistance();
        }

        return voltage;
    }

    @Override
    public double getCurrent()
    {
        if (current == 0)
            current =  getVoltage() * getConductance();

        return current;
    }

    @Override
    public double getConductance()
    {
        if (conductance == 0)
            conductance = 1 / getResistance();

        return conductance;
    }


    @Override
    public void setVoltage(double voltage)
    {
        super.setVoltage(voltage);
        setCurrent(voltage * getConductance());
    }

    @Override
    public void setCurrent(double current)
    {
        super.setCurrent(current);

        for (Element element : elements)
        {
            element.setCurrent(current);
            element.setVoltage(current * element.getResistance());
        }
    }

    @Override
    public void normalize()
    {
        for (int i = 0; i < elements.size(); i++)
        {
            Element element = elements.get(i);
            if (element instanceof SerialConnection)
            {
                elements.addAll(((SerialConnection) element).elements);
                elements.remove(element);
                i--;
            }
            else if (element instanceof Idle)
            {
                elements.remove(element);
                i--;
            }
        }
    }
}
