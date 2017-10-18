package org.roman.view;

import org.roman.model.element.Element;

/**
 * Created by Roman on 13.10.2017.
 */
public class PrintingElement
{
    private Element element;
    private StringBuilder tab;
    private StringBuilder afterTab;
    private boolean vertTab;
    private StringBuilder left;
    private StringBuilder right;

    public boolean isNullElement()
    {
        return element == null;
    }


    public static class Samples
    {
        public final static String SINGLE_TAB =      "          ";
        public final static String AFTER_TAB =       "----------";

        public final static String START_LEFT =   "┬";
        public final static String CENTER_LEFT =  "├";
        public final static String END_LEFT =     "└";
        public final static String START_SPECIAL_LEFT =   "┼";


        public final static String START_RIGHT =  "┐";
        public final static String CENTER_SPECIAL_RIGHT = "│";
        public final static String CENTER_RIGHT = "┤";
        public final static String END_RIGHT =    "┴";

        public final static String DEFAULT =     "-";
    }

    public void rightAdd(String right)
    {
        this.right = new StringBuilder(right);
    }

    public void leftAdd(String left)
    {
        this.left = new StringBuilder(left);
    }

    public PrintingElement(Element element)
    {
        this.element = element;
        vertTab = false;
        tab = new StringBuilder();
        afterTab = new StringBuilder();
        right = new StringBuilder();
        left = new StringBuilder();
    }

    public PrintingElement(String string)
    {
        rightAdd(string);
        vertTab = false;
        tab = new StringBuilder();
        afterTab = new StringBuilder();
        left = new StringBuilder();
    }

    public void addTabs(int count)
    {
        for (int i = 0; i < count; i++)
            tab.append(Samples.SINGLE_TAB);
    }

    public void addAfterTabs(int count)
    {
        for (int i = 0; i < count - 1; i++)
            afterTab.append(Samples.AFTER_TAB);
        if (count != 0)
            afterTab.append(Samples.AFTER_TAB);
    }

    public void setVertTab(boolean vertTab)
    {
        this.vertTab = vertTab;
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();

        if (vertTab)
            res.append('\n');
        res.append(tab);
        if (element != null)
            res.append(left).append(element);
        res.append(afterTab).append(right);

        return res.toString();
    }

    public String getRight()
    {
        return right.toString();
    }

    public String getLeft()
    {
        return left.toString();
    }
}
