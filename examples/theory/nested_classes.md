
# Вложенные классы и лямбда-выражения в Java

## 1. Виды вложенных классов с примерами

### Static Nested Class

```java
public class Outer {
    static class StaticNested {
        void display() {
            System.out.println("Static nested class");
        }
    }

    public static void main(String[] args) {
        StaticNested nested = new StaticNested(); // нет необходимости создавать Outer
        nested.display();
    }
}
```

- Это вложенный класс с модификатором `static`.
- Не имеет доступа к нестатическим членам внешнего класса напрямую.
- Используется для логически связанных классов, которым не нужен доступ к экземпляру внешнего класса.

---

### Inner Class

```java
public class Outer {
    private String message = "Hello from Outer";

    class Inner {
        void show() {
            System.out.println(message); // доступ к private полю
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        Inner inner = outer.new Inner();
        inner.show();
    }
}
```

- Внутренний класс имеет доступ ко всем членам внешнего класса, включая private.
- Требует создания экземпляра внешнего класса.

---

### Local Class

```java
public class Outer {
    void run() {
        String localVar = "Local variable"; // effectively final

        class Local {
            void print() {
                System.out.println(localVar); // доступ к переменной метода
            }
        }

        Local local = new Local();
        local.print();
    }
}
```

- Объявляется внутри метода.
- Может обращаться только к `effectively final` переменным окружающего метода.

---

### Anonymous Class

```java
public class Outer {
    void execute() {
        Runnable r = new Runnable() {
            public void run() {
                System.out.println("Anonymous class running");
            }
        };
        r.run();
    }
}
```

- Не имеет имени.
- Часто используется для одноразовой реализации интерфейса или абстрактного класса.
- Может определять собственные поля и методы.

---

### Lambda Expression

```java
public class Outer {
    void execute() {
        Runnable r = () -> System.out.println("Lambda running");
        r.run();
    }
}
```

- Лаконичная форма записи реализации функционального интерфейса.
- Не создаёт нового класса, используется `invokedynamic`.

---

## 2. К каким переменным имеет доступ каждый вид вложенных классов

| Тип класса          | Доступ к членам внешнего класса | Доступ к локальным переменным метода |
|---------------------|----------------------------------|---------------------------------------|
| Static Nested       | Только к `static`                | ❌                                     |
| Inner               | Все (включая `private`)          | ❌                                     |
| Local               | Все                              | ✅ Только `effectively final`          |
| Anonymous           | Все                              | ✅ Только `effectively final`          |
| Lambda              | Все                              | ✅ Только `effectively final`          |

---

## 3. Про effectively final

**Effectively final** — переменная, которой **однажды присвоено значение**, и оно **не изменяется** в дальнейшем.

**Пример (OK):**
```java
void test() {
    int number = 5;

    Runnable r = () -> System.out.println(number); // number - effectively final
    r.run();
}
```

**Пример (Ошибка):**
```java
void test() {
    int number = 5;
    number = 6; // теперь переменная не effectively final

    Runnable r = () -> System.out.println(number); // ошибка компиляции
}
```

**Почему это важно:**
- В анонимных, локальных классах и лямбдах переменные копируются в поля.
- Если переменная могла бы изменяться, это вызвало бы проблемы согласованности.

---

## 4. Внутреннее устройство вложенных классов в Java

### 🔹 Общие принципы

- Все вложенные классы (кроме лямбд) реализуются как отдельные `.class` файлы.
- Имена файлов: `Outer$Inner.class`, `Outer$1.class`, `Outer$1Local.class` и т.д.
- Компилятор добавляет:
   - ссылку на внешний экземпляр (`this$0`), если нужно
   - поля для захваченных переменных (в local/anonymous)
   - конструкторы, принимающие внешние ссылки и значения

---

### 🧱 Static Nested Class

- Имя файла: `Outer$StaticNested.class`
- Нет ссылки на `Outer`, доступ только к static-членам

```java
public class Outer {
    static int count = 0;

    static class StaticNested {
        void show() {
            System.out.println("Count: " + count);
        }
    }
}
```

---

### 🧱 Inner Class

- Имя файла: `Outer$Inner.class`
- Есть поле `Outer this$0`, конструктор принимает `Outer`
- Имеет доступ ко всем членам внешнего класса

```java
public class Outer {
    private String msg = "Hi";

    class Inner {
        void print() {
            System.out.println(msg);
        }
    }
}
```

**Упрощённый байткод:**
```java
class Outer$Inner {
    final Outer this$0;

    Outer$Inner(Outer outer) {
        this.this$0 = outer;
    }
}
```

---

### 🧱 Local Class

- Имя файла: `Outer$1Local.class`
- Ссылка на внешний класс + копии effectively final переменных

```java
void method() {
    int x = 10;

    class Local {
        void print() {
            System.out.println(x);
        }
    }

    new Local().print();
}
```

---

### 🧱 Anonymous Class

- Имя файла: `Outer$1.class`, `Outer$2.class`, ...
- Нет имени, создаётся при компиляции
- Содержит ссылки на внешние переменные и this$0

```java
Runnable r = new Runnable() {
    public void run() {
        System.out.println("Hello");
    }
};
```

---

### 📌 Сравнительная таблица

| Класс            | .class имя         | Имеет `this$0` | Захватывает переменные | Использует effectively final |
|------------------|---------------------|----------------|-------------------------|-------------------------------|
| Static Nested    | `Outer$StaticNested`| ❌             | ❌                      | ❌                            |
| Inner            | `Outer$Inner`       | ✅             | ❌                      | ❌                            |
| Local            | `Outer$1Local`      | ✅             | ✅                      | ✅                            |
| Anonymous        | `Outer$1`           | ✅             | ✅                      | ✅                            |


---

## 5. Внутреннее устройство лямбд

- Лямбды реализованы через `invokedynamic`.
- Ссылки на метод передаются в `LambdaMetafactory.metafactory`.
- Генерация экземпляра интерфейса происходит в рантайме.

**Пример байткода (`javap -c`):**
```java
0: invokedynamic #0, run()Ljava/lang/Runnable;
```

**Пояснение:**
- JVM вызывает `LambdaMetafactory.metafactory`, создаёт реализацию интерфейса и возвращает объект.
- Это позволяет JVM оптимизировать код и избежать создания `.class` файлов.

---

## 6. Пример использования `this` в анонимном классе и лямбде

### Пример:

```java
public class Demo {
    String name = "Outer";

    void runExamples() {
        Runnable anon = new Runnable() {
            String name = "Anonymous";
            public void run() {
                System.out.println("Anon this.name: " + this.name); // "Anonymous"
                System.out.println("Anon this.class: " + this.getClass().getName());
            }
        };

        Runnable lambda = () -> {
            System.out.println("Lambda this.name: " + this.name); // "Outer"
            System.out.println("Lambda this.class: " + this.getClass().getName());
        };

        anon.run();
        System.out.println("---");
        lambda.run();
    }

    public static void main(String[] args) {
        new Demo().runExamples();
    }
}
```

### Объяснение:

- В **анонимном классе** `this` указывает на сам анонимный объект. Поэтому `this.name` ссылается на поле `"Anonymous"`.
- В **лямбде** `this` указывает на внешний экземпляр класса `Demo`. Поэтому `this.name` возвращает `"Outer"`.

Это подчёркивает ключевое различие между лямбдами и анонимными классами: **лямбда не создаёт новой области видимости**, а анонимный класс — создаёт.



## 7. Когда можно использовать лямбда-выражения

### ✅ Условия:

1. **Функциональный интерфейс**  
   Интерфейс должен иметь **ровно один абстрактный метод** (например, `Runnable`, `Consumer<T>`, `Function<T,R>` и т.д.).

2. **Не нужно переопределять equals/hashCode/toString**  
   Лямбда — безымянна и не имеет своего типа. Не годится, если нужно сравнение или сериализация.

3. **Не нужно внутреннее состояние**  
   Лямбда не может иметь поля и собственные методы. Только реализация одного метода.

4. **Не нужно использовать `this` для ссылки на саму лямбду**  
   Внутри лямбды `this` ссылается на внешний класс, в отличие от анонимного класса.

5. **Код простой и короткий**  
   Лямбда читается легче, когда логика компактная. Для сложной логики лучше использовать именованный класс.

---

### 💡 Примеры использования:

- **Коллекции**:
```java
list.stream().filter(s -> s.startsWith("A")).forEach(System.out::println);
```

- **Сортировка**:
```java
Collections.sort(list, (a, b) -> a.length() - b.length());
```

- **Обработчики событий**:
```java
button.addActionListener(e -> System.out.println("Clicked"));
```

- **Потоки и задачи**:
```java
executor.submit(() -> doWork());
```

---

### ❌ Когда не стоит использовать лямбду:

- Нужно реализовать несколько методов
- Нужно использовать `this` как ссылку на текущий объект
- Требуется состояние (например, счётчик)
- Нужно сериализовать объект
- Хочется логировать тип через `getClass().getName()`

---

### 🧠 Итог:

| Можно использовать лямбду, если…                                 | Да/Нет |
|-------------------------------------------------------------------|--------|
| Интерфейс — функциональный (один абстрактный метод)              | ✅     |
| Нужна лаконичная, одноразовая логика                             | ✅     |
| Не требуется внутреннее состояние                                | ✅     |
| Нужно имя/доступ к `this` как к объекту лямбды                   | ❌     |
| Хочешь переопределить equals/hashCode                            | ❌     |


## 8. Резюме

- Вложенные классы делятся на 4 типа: static nested, inner, local и anonymous.
- Все они (кроме лямбд) компилируются в отдельные `.class` файлы.
- Inner, local, anonymous имеют доступ к членам внешнего класса и могут захватывать effectively final переменные.
- Лямбды используют `invokedynamic` и не создают класс-файл, но требуют те же ограничения по переменным.
- Использование `effectively final` — ключевое для понимания области видимости и жизненного цикла переменных.

Этот документ охватывает все ключевые аспекты вложенных классов и лямбд в Java с примерами и техническими деталями.
