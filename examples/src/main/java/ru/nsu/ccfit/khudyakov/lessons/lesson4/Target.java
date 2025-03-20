package ru.nsu.ccfit.khudyakov.lessons.lesson4;

public class Target {

    private final Dependency dependency;

    public Target(Dependency dependency) {
        this.dependency = dependency;
    }

    public int foo() {
        try {
            return dependency.bar(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
