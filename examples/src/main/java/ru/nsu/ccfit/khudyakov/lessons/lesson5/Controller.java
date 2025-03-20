package ru.nsu.ccfit.khudyakov.lessons.lesson5;

public class Controller {

    private final Model model;

    public Controller() {
        this.model = new Model();
        SwingTrafficLight trafficLight = new SwingTrafficLight(this);
        this.model.addObserver(trafficLight);
    }

    public void update() {
        model.update();
    }

}
