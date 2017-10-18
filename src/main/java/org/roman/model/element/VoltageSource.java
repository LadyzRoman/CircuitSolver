package org.roman.model.element;

/**
 * Created by Roman on 09.10.2017.
 */
public class VoltageSource extends Source
{
    public VoltageSource(int voltage)
    {
        this.voltage = voltage;
    }

    public VoltageSource(VoltageSource voltageSource)
    {
        super(voltageSource);
        voltage = voltageSource.voltage;
    }


    @Override
    public String toString()
    {
        return "(  + -  )";
    }

    @Override
    public VoltageSource copy()
    {
        return new VoltageSource(this);
    }

    @Override
    public Element getReplacement()
    {
        return new Idle();
    }
}
