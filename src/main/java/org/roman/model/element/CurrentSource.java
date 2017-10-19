package org.roman.model.element;

import org.roman.model.math.RationalFraction;

/**
 * Created by Roman on 09.10.2017.
 */
public class CurrentSource extends Source
{

    public CurrentSource(RationalFraction current)
    {
        this.current = current;
    }

    public CurrentSource(CurrentSource currentSource)
    {
        super(currentSource);
        current = currentSource.current;
    }


    @Override
    public String toString()
    {
        return "(  -->  )";
    }

    @Override
    public CurrentSource copy()
    {
        return new CurrentSource(this);
    }

    @Override
    public Element getReplacement()
    {
        return null;
    }
}
