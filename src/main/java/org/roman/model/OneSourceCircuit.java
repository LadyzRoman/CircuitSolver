package org.roman.model;

import org.roman.model.element.Element;
import org.roman.model.element.ParallelConnection;
import org.roman.model.element.SerialConnection;
import org.roman.model.graph.Link;
import org.roman.model.graph.Node;
import org.roman.model.math.RationalFraction;
import org.roman.model.util.ParallelConnectionRule;

import java.util.Arrays;


/**
 * Created by Roman on 17.10.2017.
 */
public class OneSourceCircuit extends Circuit
{
    private Element idle;

    public void calculate()
    {
        if (!idle.getCurrent().equals(RationalFraction.NULL))
            getNode(0).getLinks().stream()
                    .filter(e -> !e.equals(idle))
                    .forEach(e -> ((Element) e).setCurrent(idle.getCurrent()));

        else if (!idle.getVoltage().equals(RationalFraction.NULL))
            getNode(0).getLinks().stream()
                    .filter(e -> !e.equals(idle))
                    .forEach(e -> ((Element) e).setVoltage(idle.getVoltage()));
    }

    public OneSourceCircuit(int size)
    {
        super(size);
    }


    private void removeUnnessesaryElements()
    {
        Arrays.stream(nodes)
                .filter( n -> !n.getLinks().contains(idle))
                .forEach(this::removeNode);
    }

    private void removeGaps()
    {
        Arrays.stream(nodes)
                .filter( n -> n.getLinks().size() == 1)
                .filter( n -> !n.getLinks().contains(idle))
                .forEach(this::removeNode);
    }


    public void connectResistance()
    {
        boolean change = true;

        while (change)
        {
            change = false;

            for (int i = 0; i < getSize(); i++)
            {
                Node node = getNode(i);

                if (node.getLinks().size() == 2 && !node.getLinks().contains(idle))
                {
                    if (createSerialConnection(node))
                    {
                        change = true;
                        i--;
                    }
                }
            }

            for (int i = 0; i < getSize(); i++)
            {
                Node node = getNode(i);

                if (node.getLinks().size() > 2)
                {
                    if (createParallelConnection(node))
                    {
                        change = true;
                        break;
                    }
                }
            }

            removeGaps();
        }

        removeUnnessesaryElements();
        removeEmptyNodes();
        unpackSerialConnection();
    }


    private void unpackSerialConnection()
    {
        if (getSize() > 0)
            getNode(0).getLinks().forEach( n -> ((Element)n).normalize());
    }

    private boolean createSerialConnection(Node node)
    {
        SerialConnection connection = new SerialConnection();

        Link[] links;
        links = node.getLinks().toArray(new Link[2]);

        Node connected = links[0].getConnectedNode(node);
        if (connected.getLinksToNode(node).size() == 1)
        {
            connection.addElements(node.getLinks());
            Node first = links[0].getConnectedNode(node);
            Node second = links[1].getConnectedNode(node);

            connectNodes(connection, first, second);

            replaceLink(null, first, node);
            replaceLink(null, second, node);
            removeNode(node);
            return true;
        }

        return false;
    }


    private boolean createParallelConnection(Node node)
    {
        Element connection = new ParallelConnection();

        for (Link link : node.getLinks())
        {
            Node connected = link.getConnectedNode(node);
            int size = connected.getLinksToNode(node).size();

            if (size > 1)
            {
                if (connected.getLinksToNode(node).contains(idle) && size > 2)
                {
                    connected.getLinks().remove(idle);
                    ((ParallelConnection) connection).addElements(connected.getLinksToNode(node));
                    Node dependent = link.getConnectedNode(node);

                    connection = ((ParallelConnection) connection).getElements().stream()
                            .filter( n -> n.getParallelConnectionRule() == ParallelConnectionRule.REPLACE_ALL)
                            .findFirst().orElse(connection);

                    replaceLink(connection, node, dependent);
                    connectNodes(idle, node, dependent);
                }
                else if (!connected.getLinksToNode(node).contains(idle))
                {
                    ((ParallelConnection) connection).addElements(connected.getLinksToNode(node));

                    connection = ((ParallelConnection) connection).getElements().stream()
                            .filter( n -> n.getParallelConnectionRule() == ParallelConnectionRule.REPLACE_ALL)
                            .findFirst().orElse(connection);

                    replaceLink(connection, node, link.getConnectedNode(node));
                }
                else
                    return false;

                return true;
            }

        }
        return false;

    }

    public void setIdle(Element idle)
    {
        this.idle = idle;
    }

}
