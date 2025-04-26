package ru.nsu.ccfit.khudyakov.lessons.lesson6;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(10);

        Thread p1 = new Thread(new Producer(buffer));
        Thread p2 = new Thread(new Producer(buffer));

        Thread c1 = new Thread(new Consumer(buffer));
        Thread c2 = new Thread(new Consumer(buffer));
        Thread c3 = new Thread(new Consumer(buffer));

        p1.start();
        p2.start();

        c1.start();
        c2.start();
        c3.start();

        Thread.sleep(15000);

        p1.interrupt();
        p2.interrupt();

        c1.interrupt();
        c2.interrupt();
        c3.interrupt();

        p1.join();
        p2.join();
        c1.join();
        c2.join();
        c3.join();
    }
}
