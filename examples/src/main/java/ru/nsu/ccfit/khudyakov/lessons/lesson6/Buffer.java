package ru.nsu.ccfit.khudyakov.lessons.lesson6;

import java.util.LinkedList;

public class Buffer {

    private final LinkedList<Integer> list = new LinkedList<>();

    private final int maxSize;

    public Buffer(int maxSize) {
        this.maxSize = maxSize;
    }

    public void produce(int value) {
        synchronized (list) {
            try {
                while (list.size() == maxSize) {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }

                    list.wait();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            list.addFirst(value);
            list.notifyAll();
        }
    }

    public Integer consume() {
        synchronized (list) {
            try {
                while (list.isEmpty()) {
                    list.wait();
                }

                if (Thread.currentThread().isInterrupted()) {
                    return null;
                }

                var val = list.removeLast();
                list.notifyAll();
                return val;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
    }
}
