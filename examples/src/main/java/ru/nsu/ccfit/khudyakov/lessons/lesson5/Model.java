package ru.nsu.ccfit.khudyakov.lessons.lesson5;

import java.util.Optional;

public class Model extends Observable<SignalColor> {

    private Integer curSignalNumber = 1;

    public void update() {
        curSignalNumber = curSignalNumber % SignalColor.values().length + 1;

        Optional<SignalColor> newColor = SignalColor.fromOrder(curSignalNumber);
        notify(newColor.orElseThrow());

        //SignalColor.fromOrder(curSignalNumber).ifPresent(this::notify);
    }

}
