package org.roman.model.graph;


import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Roman on 09.10.2017.
 */
public class Graph
{
    protected Node[] nodes;

    public Graph(int size)
    {
        nodes = new Node[size];
        IntStream.range(0, size)
                .forEach( i -> nodes[i] = new Node(i));
    }

    public int getSize()
    {
        return nodes.length;
    }

    public Node getNode(int index)
    {
        if (index < 0 || index > getSize())
            throw new IndexOutOfBoundsException();

        return nodes[index];
    }


    public void connectNodes(Link link, Node source, Node dependent)
    {
        if (link != null)
            link.connect(source, dependent);
    }


    protected void removeNode(Node node)
    {
        for (int i = 0; i < node.getLinks().size(); i++)
        {
            Link link = node.getLinks().get(i);
            replaceLink(null, link.getConnectedNode(node), node);
        }

        Node[] nodes = new Node[getSize() - 1];

        int offset = 0;
        for (int i = 0; i < getSize(); i++)
        {
            if (!node.equals(this.nodes[i]))
            {
                nodes[i - offset] = this.nodes[i];
                nodes[i - offset].setIndex(i - offset);
            }
            else
                offset++;
        }

        this.nodes = nodes;
    }


    /* replaced all links of this nodes to new one*/
    protected void replaceLink(Link link, Node source, Node dependent)
    {
        for (Link lnk : source.getLinksToNode(dependent))
        {
            source.removeLink(lnk);
            dependent.removeLink(lnk);
            lnk.removeConnection();
        }

        connectNodes(link, source, dependent);
    }

    protected void removeEmptyNodes()
    {
        Arrays.stream(nodes)
                .filter( n -> n.getLinks().isEmpty())
                .forEach(this::removeNode);
    }

}
