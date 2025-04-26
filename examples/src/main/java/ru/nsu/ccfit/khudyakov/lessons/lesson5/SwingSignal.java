package ru.nsu.ccfit.khudyakov.lessons.lesson5;

import javax.swing.*;
import java.awt.*;

public class SwingSignal extends JPanel implements AbstractSignal {

    private final int size;
    private final Color initColor;
    private Color curColor;

    public SwingSignal(int size, Color initColor) {
        this.size = size;
        this.initColor = initColor;
        this.curColor = Color.BLACK;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        this.setBackground(Color.BLACK);
        graphics.setColor(curColor);
        graphics.fillOval(0, 0, size, size);
    }

    @Override
    public void off() {
        curColor = Color.BLACK;
        repaint();
        //SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    public void on() {
        curColor = initColor;
        repaint();
    }

}
