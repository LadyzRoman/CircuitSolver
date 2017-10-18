package org.roman.model.graph;



/**
 * Created by Roman on 11.10.2017.
 */
public class Link
{
    private Node source;
    private Node destination;

    public void connect(Node source, Node destination)
    {
        this.source = source;
        this.destination = destination;

        this.source.setLink(this, 0);
        this.destination.setLink(this);
    }

    public Node getConnectedNode(Node node)
    {
        return source.equals(node) ? destination : source;
    }

    public void removeConnection()
    {
        source = null;
        destination = null;
    }


    public Node getSource()
    {
        return source;
    }

    public Node getDestination()
    {
        return destination;
    }
}
