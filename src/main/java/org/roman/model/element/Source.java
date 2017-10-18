package org.roman.model.element;


/**
 * Created by Roman on 11.10.2017.
 */
public abstract class Source extends Element
{
    public Source(Source element)
    {
        super(element);
    }

    public Source()
    {
    }

    public abstract Element getReplacement();
}
