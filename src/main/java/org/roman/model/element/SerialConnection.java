package org.roman.model.element;


import org.roman.model.math.RationalFraction;

import java.util.stream.Collectors;

/**
 * Created by Roman on 11.10.2017.
 */
public class SerialConnection extends Connection {
    @Override
    public RationalFraction getResistance() {
        if (resistance.equals(RationalFraction.NULL))
            resistance = elements.stream().map(Element::getResistance).reduce(new RationalFraction(), RationalFraction::add);

        return resistance;
    }

    @Override
    public RationalFraction getVoltage() {
        if (voltage.equals(RationalFraction.NULL)) {
            voltage = getCurrent().mul(getResistance());
        }

        return voltage;
    }

    @Override
    public RationalFraction getCurrent() {
        if (current.equals(RationalFraction.NULL))
            current = getVoltage().mul(getConductance());

        return current;
    }

    @Override
    public RationalFraction getConductance() {
        if (conductance.equals(RationalFraction.NULL))
            conductance = RationalFraction.ONE.div(getResistance());

        return conductance;
    }


    @Override
    public void setVoltage(RationalFraction voltage) {
        super.setVoltage(voltage);
        setCurrent(voltage.mul(getConductance()));
    }

    @Override
    public void setCurrent(RationalFraction current) {
        super.setCurrent(current);

        for (Element element : elements) {
            element.setCurrent(current);
            element.setVoltage(current.mul(element.getResistance()));
        }
    }

    @Override
    public void normalize() {
        elements.stream()
                .filter(e -> e instanceof Idle)
                .forEach(e -> elements.remove(e));

        elements.stream()
                .filter(e -> e instanceof SerialConnection)
                .forEach(e ->
                {
                    elements.addAll(((SerialConnection) e).elements);
                    elements.remove(e);
                });
    }
}
