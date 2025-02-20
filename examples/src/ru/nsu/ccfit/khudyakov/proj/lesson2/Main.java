package ru.nsu.ccfit.khudyakov.proj.lesson2;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        {
            System.out.println("test 1");
            Set<Foo> set = new HashSet<>();

            Foo f1 = new Foo(10, 20);
            Foo f2 = new Foo(10, 20);
            set.add(f1);
            set.add(f2);

            System.out.println(set.size());
            System.out.println(f1.equals(f2));
        }

        {
            System.out.println("test 2");
            Set<Foo> set = new TreeSet<>();

            set.add(new Foo(10, 20));
            set.add(new Foo(10, 50));
            set.add(new Foo(20, 10));
            set.add(new Foo(5, 25));
            set.add(new Foo(30, 20));

            for (Foo foo : set) {
                System.out.println(foo);
            }
        }

        {
            System.out.println("test 3");
            ArrayList<Foo> list = new ArrayList<>();
            list.add(new Foo(10, 20));
            list.add(new Foo(10, 50));
            System.out.println(list.indexOf(new Foo(10, 50)));
        }

        {
            System.out.println("test 4");
            LinkedList<Foo> list = new LinkedList<>();
            list.add(new Foo(10, 20));
            list.add(new Foo(10, 50));
            System.out.println(list.indexOf(new Foo(10, 50)));
        }

        {
            System.out.println("test 5");
            Map<Integer, Foo> map = new TreeMap<>();
            map.put(1, new Foo(10, 20));
            map.put(3, new Foo(5, 50));
            map.put(2, new Foo(20, 10));

            for (Map.Entry<Integer, Foo> e : map.entrySet()) {
                System.out.println(e.getValue());
            }
        }

    }
}
