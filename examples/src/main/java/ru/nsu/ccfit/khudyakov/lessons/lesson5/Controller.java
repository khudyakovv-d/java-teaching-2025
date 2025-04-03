package ru.nsu.ccfit.khudyakov.lessons.lesson5;

public class Controller {

    private final Model model;

    public Controller() {
        this.model = new Model();
        this.model.addObserver(new SwingTrafficLight(this));
        this.model.addObserver(new SwingTrafficLight(this));
        this.model.addObserver(new SwingTrafficLight(this));
        this.model.addObserver(new SwingTrafficLight(this));
        this.model.addObserver(new SwingTrafficLight(this));
    }

    public void update() {
        model.update();
    }

}
