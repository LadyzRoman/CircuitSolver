package org.roman.model.graph;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Roman on 09.10.2017.
 */
public class Node
{
    private List<Link> links = new ArrayList<>();
    private int index;

    public Node(int index)
    {
        this.index = index;
    }

    public void setLink(Link link)
    {
        links.add(link);
    }

    public void setLink(Link link, int index)
    {

        links.add(index, link);
    }


    public void removeLink(Link link)
    {
        links.remove(link);

    }

    public List<Link> getLinks()
    {
        return links;
    }

    public List<Link> getLinksToNode(Node node)
    {
        List<Link> result = new ArrayList<>();
        for (Link link : links)
        {
            if (link.getConnectedNode(this).equals(node))
                result.add(link);
        }

        return result;
    }


    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }
}
