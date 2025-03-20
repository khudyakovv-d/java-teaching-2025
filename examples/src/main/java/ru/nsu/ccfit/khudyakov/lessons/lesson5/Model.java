package ru.nsu.ccfit.khudyakov.lessons.lesson5;

public class Model extends Observable<SignalColor> {

    private Integer curSignalNumber = 1;

    public void update() {
        curSignalNumber = curSignalNumber % SignalColor.values().length + 1;
        SignalColor.fromOrder(curSignalNumber).ifPresent(this::notify);
    }

}
