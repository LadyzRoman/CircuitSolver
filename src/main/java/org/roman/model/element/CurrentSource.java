package org.roman.model.element;

/**
 * Created by Roman on 09.10.2017.
 */
public class CurrentSource extends Source
{

    public CurrentSource(int current)
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
