package ru.nsu.ccfit.khudyakov.proj.lesson1;

public class Main {

    public static void main(String[] args) {
        Printer printer = new Printer();
        print(printer);
    }

    public static void print(Printer printer) {
        printer.print("Hello, World!");
    }

}