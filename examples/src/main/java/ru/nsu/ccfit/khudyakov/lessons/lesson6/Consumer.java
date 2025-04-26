package ru.nsu.ccfit.khudyakov.lessons.lesson6;

import java.util.logging.Logger;

public class Consumer implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Consumer.class.getName());

    private static final int DELAY = 500;

    private final Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Integer val = buffer.consume();

            LOGGER.info(String.format("Consumed %d", val));

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException e) {
                LOGGER.info("Consumer interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

}
