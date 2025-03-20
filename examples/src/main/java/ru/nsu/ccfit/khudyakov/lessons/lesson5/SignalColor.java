package ru.nsu.ccfit.khudyakov.lessons.lesson5;

import java.util.Arrays;
import java.util.Optional;

public enum SignalColor {
    RED(1),
    YELLOW(2),
    GREEN(3);

    final int order;

    SignalColor(int order) {
        this.order = order;
    }

    public static Optional<SignalColor> fromOrder(int order) {
        return Arrays.stream(values())
            .filter(c -> c.order == order)
            .findFirst();
    }

    public int getOrder() {
        return order;
    }

}
