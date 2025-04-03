
## Дополнение: Функциональные интерфейсы

### ✅ Что такое функциональный интерфейс?

**Функциональный интерфейс** — это интерфейс, который содержит **ровно один абстрактный метод**.  
Такие интерфейсы используются с **лямбда-выражениями**.

---

### 🧪 Пример

```java
@FunctionalInterface
interface Greeting {
    void sayHello();
}

Greeting g = () -> System.out.println("Hello!");
g.sayHello(); // Hello!
```

---

### 📛 Аннотация `@FunctionalInterface`

Аннотация не обязательна, но:

- Помогает компилятору следить за структурой интерфейса
- Генерирует ошибку при добавлении второго абстрактного метода

```java
@FunctionalInterface
interface Converter {
    int convert(String input);
    // void another(); // ❌ ошибка компиляции
}
```

---

### ✅ Что МОЖНО в функциональном интерфейсе

- Один абстрактный метод
- `default` методы
- `static` методы
- Методы из `Object` (`toString`, `equals`, `hashCode`)

```java
@FunctionalInterface
interface MyFunc {
    void run();

    default void info() {
        System.out.println("Info");
    }

    static void help() {
        System.out.println("Static helper");
    }
}
```

---

### 📚 Примеры встроенных функциональных интерфейсов

| Интерфейс           | Метод             | Описание                                |
|---------------------|------------------|------------------------------------------|
| `Runnable`          | `void run()`     | Без аргументов, ничего не возвращает     |
| `Supplier<T>`       | `T get()`        | Возвращает значение                      |
| `Consumer<T>`       | `void accept(T)` | Принимает значение, ничего не возвращает |
| `Function<T, R>`    | `R apply(T)`     | Преобразует один тип в другой            |
| `Predicate<T>`      | `boolean test(T)`| Проверка условия                         |
| `UnaryOperator<T>`  | `T apply(T)`     | Преобразование в рамках одного типа      |
| `BinaryOperator<T>` | `T apply(T, T)`  | Операция над двумя значениями одного типа |

---

### 💬 Примеры использования

**Consumer:**
```java
Consumer<String> printer = s -> System.out.println("Hi " + s);
printer.accept("Alice"); // Hi Alice
```

**Predicate:**
```java
Predicate<Integer> isEven = n -> n % 2 == 0;
System.out.println(isEven.test(4)); // true
```

**Function:**
```java
Function<String, Integer> toLength = str -> str.length();
System.out.println(toLength.apply("hello")); // 5
```

---

### 🛠 Создание собственного функционального интерфейса

```java
@FunctionalInterface
public interface Calculator {
    int compute(int a, int b);
}
```

**Использование:**
```java
Calculator add = (a, b) -> a + b;
System.out.println(add.compute(2, 3)); // 5
```

---

### 🧠 Зачем они нужны?

- Позволяют использовать лямбда-выражения и method reference
- Необходимы для Stream API, Optional, CompletableFuture и др.
- Делают код короче, читаемее и декларативнее

---

