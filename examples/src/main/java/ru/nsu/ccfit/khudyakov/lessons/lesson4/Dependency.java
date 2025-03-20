package ru.nsu.ccfit.khudyakov.lessons.lesson4;

public class Dependency {

    public int bar(int millis) throws InterruptedException {
        Thread.sleep(millis);
        return millis;
    }

}
