package ru.nsu.ccfit.khudyakov.proj.lesson2;

import java.util.Objects;

public class Foo implements Comparable<Foo> {

    private int x;
    private int y;

    public Foo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Foo foo = (Foo) o;
        return x == foo.x && y == foo.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public int compareTo(Foo o) {
        return this.x - o.getX();
    }

    @Override
    public String toString() {
        return "Foo{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

}
