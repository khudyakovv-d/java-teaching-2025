package ru.nsu.ccfit.khudyakov.lessons.lesson6;

import java.util.Random;
import java.util.logging.Logger;

public class Producer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Producer.class.getName());

    private static final Random RAND = new Random();

    private static final int DELAY = 300;

    private final Buffer buffer;


    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            int value = RAND.nextInt();
            buffer.produce(value);

            LOGGER.info("Produced " + value);

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                LOGGER.info("Producer interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }
}
