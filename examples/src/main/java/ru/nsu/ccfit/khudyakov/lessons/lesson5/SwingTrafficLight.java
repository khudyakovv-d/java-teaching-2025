package ru.nsu.ccfit.khudyakov.lessons.lesson5;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class SwingTrafficLight implements AbstractTrafficLight, Observer<SignalColor> {

    private static final int SIGNAL_SIZE = 200;
    private final Map<SignalColor, SwingSignal> signals = new LinkedHashMap<>();
    private final JFrame frame = new JFrame();

    public SwingTrafficLight(Controller controller) {
        SwingUtilities.invokeLater(() -> {
            frame.setSize(new Dimension(SIGNAL_SIZE, SIGNAL_SIZE * 4));
            frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
            frame.getContentPane().setBackground(Color.BLACK);

            signals.put(SignalColor.RED, new SwingSignal(SIGNAL_SIZE, Color.RED));
            signals.put(SignalColor.YELLOW, new SwingSignal(SIGNAL_SIZE, Color.YELLOW));
            signals.put(SignalColor.GREEN, new SwingSignal(SIGNAL_SIZE, Color.GREEN));
            signals.forEach((k, v) -> frame.add(v));
            signals.get(SignalColor.RED).on();

            JButton switcher = new JButton();
            switcher.setBackground(Color.WHITE);
            switcher.setText("SWITCH");
            switcher.addActionListener(e -> controller.update());
            frame.getContentPane().add(switcher);

            frame.setVisible(true);
        });
    }

    @Override
    public void update(SignalColor color) {
        signals.forEach((k, v) -> {
            if (k == color) {
                v.on();
            } else {
                v.off();
            }
        });
    }

}
