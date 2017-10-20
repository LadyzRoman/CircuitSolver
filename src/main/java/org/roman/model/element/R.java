package org.roman.model.element;

import org.roman.model.math.RationalFraction;
import org.roman.model.util.ParallelConnectionRule;

/**
 * Created by Roman on 09.10.2017.
 */
public class R extends Element
{

    public R(RationalFraction resistance)
    {
        this.resistance = resistance;
        this.conductance = RationalFraction.ONE.div(resistance);
        this.parallelConnectionRule = ParallelConnectionRule.REGULAR;
    }

    public R(R r)
    {
        super(r);
        this.parallelConnectionRule = ParallelConnectionRule.REGULAR;
    }

    @Override
    public String toString()
    {
        return String.format("[R%s =%-3.3s]", index, resistance);
    }

    @Override
    public R copy()
    {
        return new R(this);
    }

}
