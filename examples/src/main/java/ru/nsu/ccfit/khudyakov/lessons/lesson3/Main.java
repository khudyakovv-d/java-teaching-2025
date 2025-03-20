package ru.nsu.ccfit.khudyakov.lessons.lesson3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (FileReader fileReader = new FileReader("file.txt");
             BufferedReader br = new BufferedReader(fileReader)) {
            System.out.println(br.readLine());
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }
}