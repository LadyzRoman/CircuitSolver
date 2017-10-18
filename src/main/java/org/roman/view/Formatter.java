package org.roman.view;

import org.roman.model.OneSourceCircuit;
import org.roman.model.element.Element;
import org.roman.model.element.ParallelConnection;
import org.roman.model.element.SerialConnection;
import org.roman.model.graph.Link;
import org.roman.model.graph.Node;

import java.util.*;

/**
 * Created by Roman on 13.10.2017.
 */
public class Formatter
{
    private OneSourceCircuit circuit;

    private PrintingElement[][] elements;


    public Formatter(OneSourceCircuit circuit)
    {
        this.circuit = circuit;
        elements = new PrintingElement[20][20];
    }

    public String getPrettyView() throws Exception
    {
        if (circuit.getSize() != 2)
            throw new Exception("Format exception");
        Node node = circuit.getNode(0);

        Coordinate coordinate = new Coordinate(0, 0);

        for (Link link : node.getLinks())
        {
            coordinate = prepareElement((Element) link, coordinate);
            coordinate.pos++;
        }

        return prettyView();

    }


    private String prettyView()
    {
        StringBuilder sb = new StringBuilder();
        boolean status = false; // состояние параллельного соединения, если true то мы в нем, иначе false

        for (int i = 0; i < 10; i++)
        {
            boolean prevElement = false;
            int prevPos = 0;

            for (int j = 0; j < 10; j++)
            {
                if (elements[i][j] != null)
                {
                    if (!prevElement)
                    {
                        if (status)
                        {
                            if (elements[i + 1][j] != null)
                                elements[i][j].leftAdd(PrintingElement.Samples.CENTER_LEFT);
                            else
                            {
                                status = false;
                                elements[i][j].leftAdd(PrintingElement.Samples.END_LEFT);
                            }
                        }
                        else
                        {
                            if (elements[i + 1][j] != null)
                            {
                                status = true;
                                elements[i][j].leftAdd(PrintingElement.Samples.START_LEFT);
                            }
                            else if (elements[i][j].getLeft().equals(""))
                                elements[i][j].leftAdd(PrintingElement.Samples.DEFAULT);
                        }

                        prevElement = true;
                        prevPos = j;
                        elements[i][j].addTabs(j);
                        elements[i][j].setVertTab(true);
                        sb.append(elements[i][j]);
                    }
                    else
                    {
                        if (!status && elements[i + 1][j] != null && i > 0 && elements[i - 1][j] != null && elements[i - 1][j].isNullElement())
                        {
                            status = true;
                            elements[i][j].leftAdd(PrintingElement.Samples.START_SPECIAL_LEFT);
                        }
                        else if (!status && elements[i + 1][j] != null)
                        {
                            status = true;
                            elements[i][j].leftAdd(PrintingElement.Samples.START_LEFT);
                        }
                        else if (elements[i][j].getLeft().equals(""))
                            elements[i][j].leftAdd(PrintingElement.Samples.DEFAULT);

                        elements[i][j].addAfterTabs(j - prevPos - 1);
                        prevPos = j;
                        elements[i][j].setVertTab(false);
                        sb.append(elements[i][j]);
                    }

                }
            }
        }

        return sb.toString();
    }

    private Coordinate prepareElement(Element element, Coordinate coordinate)
    {
        if (element instanceof SerialConnection)
            return prepareSerialConnection((SerialConnection) element, coordinate);

        else if (element instanceof ParallelConnection)

            return prepareParallelConnection((ParallelConnection) element, coordinate);


        else
        {
            PrintingElement el = new PrintingElement(element);
            if (elements[coordinate.line][coordinate.pos] != null)
            {
                el.leftAdd(elements[coordinate.line][coordinate.pos].getRight());
            }
            elements[coordinate.line][coordinate.pos] = el;


            return coordinate;

        }
    }


    private Coordinate prepareSerialConnection(SerialConnection connection, Coordinate coordinate)
    {
        for (Element element : connection.getElements())
        {
            coordinate = prepareElement(element, coordinate);
            coordinate.pos++;
        }

        return coordinate;
    }

    private Coordinate prepareParallelConnection(ParallelConnection connection, Coordinate coordinate)
    {

        Iterator<Element> iterator = connection.getElements().iterator();

        int startLine = coordinate.line;
        int pos = coordinate.pos;
        Coordinate res = new Coordinate(coordinate);

        while (iterator.hasNext())
        {
            Element element = iterator.next();

            if (!iterator.hasNext())
            {

                res = prepareElement(element, res);
            }
            else
            {
                res = prepareElement(element, res);

                res.line++;

            }

            pos = Math.max(pos, res.pos);
            res.pos = coordinate.pos;

        }
        res.pos = pos;

        if (pos == coordinate.pos)
            pos++;

        for (int i = startLine; i <= res.line; i++)
        {

            PrintingElement el = new PrintingElement(i == startLine ? PrintingElement.Samples.START_RIGHT :
                    i == res.line ? PrintingElement.Samples.END_RIGHT : PrintingElement.Samples.CENTER_RIGHT);

            if (elements[i][pos - 1] != null && elements[i][pos - 1].isNullElement())
                el = new PrintingElement("        " + PrintingElement.Samples.CENTER_SPECIAL_RIGHT);
            elements[i][pos] = el;
        }

        return res;
    }


    private class Coordinate
    {
        int pos;
        int line;

        Coordinate(int pos, int line)
        {
            this.pos = pos;
            this.line = line;
        }

        Coordinate(Coordinate coordinate)
        {
            pos = coordinate.pos;
            line = coordinate.line;
        }
    }

}
