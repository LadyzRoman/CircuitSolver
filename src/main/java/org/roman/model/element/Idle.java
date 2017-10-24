package org.roman.model.element;

import org.roman.model.util.ParallelConnectionRule;

/**
 * Created by Roman on 17.10.2017.
 */
public class Idle extends Element
{
    public Idle()
    {
        this.parallelConnectionRule = ParallelConnectionRule.REPLACE_ALL;
    }

    @Override
    public String toString()
    {
        return "--------";
    }


}
