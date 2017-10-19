package org.roman.view;

import org.roman.model.*;
import org.roman.model.element.*;
import org.roman.model.math.RationalFraction;

import java.util.Scanner;

/**
 * Created by Roman on 09.10.2017.
 */
public class SimpleView
{
    private Scanner sc;


    public static void main(String[] args) throws Exception
    {
        SimpleView view = new SimpleView();
        Circuit circuit = view.createCircuit();

        circuit.calculateReactions();

        System.out.println();

        for (Element element : circuit.getElements())
            System.out.format("%s : i = %s ; u = %s\n" , element , element.getCurrent() , element.getVoltage());
    }

    public SimpleView()
    {
        this.sc = new Scanner(System.in);
    }

    public Circuit createCircuit()
    {

        System.out.println("Введите кол-во узлов");
        int size = sc.nextInt();
        if (!sc.hasNext())
            System.out.println("Введите кол-во элементов");
        int count = sc.nextInt();
        sc.nextLine();
        Circuit circuit = new Circuit(size);

        for (int i = 0; i < count; i++)
        {
            try
            {
                addElement(circuit);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                i--;
            }

        }

        return circuit;
    }


    public void addElement(Circuit circuit) throws Exception
    {
        if (!sc.hasNext())
            System.out.println("Введите элемент (vs|cs|r) source destination reaction");
        String line = sc.nextLine();

        String[] elementInfo = line.split(" ");

        int source = Integer.parseInt(elementInfo[1]);
        int dest = Integer.parseInt(elementInfo[2]);
        RationalFraction reaction = RationalFraction.parseRF(elementInfo[3]);
        Element element;

        switch (elementInfo[0])
        {
            case "r":
            case "R":
                element = new R(reaction);
                break;
            case "cs":
            case "CS":
                element = new CurrentSource(reaction);
                circuit.setSource((Source) element);
                break;
            case "vs":
            case "VS":
                element = new VoltageSource(reaction);
                circuit.setSource((Source) element);
                break;
            default:
                throw new Exception("wrong format");
        }

        circuit.connectNodes(element, circuit.getNode(source - 1), circuit.getNode(dest - 1));
    }
}
