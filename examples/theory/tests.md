**Презентация: Тестирование в Java с JUnit и Mockito**

---

# **1. Введение в тестирование**

## **Что такое юнит-тесты?**

Юнит-тесты (Unit Tests) — это тесты, которые проверяют **отдельные модули (юниты) кода в изоляции**. Обычно такими
модулями являются **методы** или **классы**, и тестирование проводится без зависимостей от внешних компонентов, таких
как база данных, файловая система или сети.

## **Зачем нужны юнит-тесты?**

- **Раннее выявление ошибок** – тесты помогают обнаружить баги ещё на этапе разработки.
- **Уверенность в коде** – при внесении изменений тесты подтверждают, что существующая функциональность не сломана (
  регрессия).
- **Документация кода** – тесты показывают, как должны работать методы и классы.
- **Снижение затрат на исправление ошибок** – баги, найденные в юнит-тестах, дешевле исправлять, чем на продакшене.
- **Автоматизация** – тесты можно запускать автоматически (например, в CI/CD), обеспечивая контроль качества.

## **Юнит-тесты vs Интеграционные тесты**

| Характеристика                     | Юнит-тесты                                     | Интеграционные тесты                    |
|------------------------------------|------------------------------------------------|-----------------------------------------|
| **Что тестируют?**                 | Один класс или метод                           | Взаимодействие нескольких компонентов   |
| **Изоляция**                       | Да, зависимости мокаются                       | Нет, работают с реальными зависимостями |
| **Скорость выполнения**            | Быстро (миллисекунды)                          | Медленно (секунды и выше)               |
| **Цель**                           | Проверить правильность работы отдельного юнита | Проверить, как модули работают вместе   |
| **Использование внешних ресурсов** | Нет (обычно моки вместо БД, API)               | Да (например, реальная БД, API)         |

### **Когда использовать?**

- **Юнит-тесты** – писать для каждого отдельного класса, чтобы быстро проверять логику.
- **Интеграционные тесты** – использовать для проверки работы нескольких модулей вместе.

Пример:

- Юнит-тест проверяет, что метод `calculateTax()` правильно считает налог.
- Интеграционный тест проверяет, что при оплате заказа система корректно вызывает `calculateTax()`, обращается к БД и
  передаёт данные в платежную систему.

## **Почему тесты особенно важны в долгосрочной инкрементальной разработке?**

В **инкрементальной разработке** код постоянно изменяется: добавляются новые функции, рефакторится старая логика,
исправляются баги. Без тестов сложно убедиться, что **нововведения не ломают уже работающий функционал**.

### **Ключевые причины, почему тесты необходимы при долгосрочной разработке:**

### 1. **Предотвращение регрессий**

- Чем больше изменений в коде, тем выше риск, что сломается что-то старое.
- Юнит-тесты **автоматически проверяют** существующую функциональность после каждого изменения.
- Если тест "упал", значит, разработчик сразу видит проблему, пока она не ушла в продакшн.

### 2. **Облегчение рефакторинга**

- Код требует изменений со временем: оптимизация, улучшение архитектуры, переход на новые технологии.
- При наличии тестов **можно смело рефакторить код**, не боясь, что что-то сломается.

### 3. **Документация поведения системы**

- Тесты можно рассматривать как **живую документацию**: они показывают, **как должны работать методы и классы**.

### 4. **Облегчение командной работы**

- В команде несколько разработчиков работают с разными частями кода.
- Автоматические тесты сразу покажут, если новое изменение несовместимо со старой логикой.

### 5. **Интеграция в CI/CD**

- В современных проектах код **автоматически тестируется при каждом коммите**.
- Если тест "падает", код не будет слит в основную ветку, предотвращая баги.

---

# **2. Подключение JUnit в Gradle**

JUnit 5 (Jupiter) — актуальная версия, которая используется в современных проектах.

Если у вас **Gradle на Groovy (build.gradle)**, добавьте зависимости в секцию `dependencies`:

```groovy
dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.0'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.0'
}
```

Если у вас **Gradle на Kotlin DSL (build.gradle.kts)**:

```kotlin
dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}
```

**Настройка плагина (опционально):**

```groovy
test {
    useJUnitPlatform()
}
```

В Kotlin DSL:

```kotlin
tasks.test {
    useJUnitPlatform()
}
```

### **Проверка работы**

После добавления зависимостей создайте тест в `src/test/java`:

```java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SampleTest {
    @Test
    void testSum() {
        int a = 2, b = 3;
        assertEquals(5, a + b, "Сумма должна быть 5");
    }
}
```

Запустите тесты командой:

```
./gradlew test
```

---

# **3. JUnit**

Аннотации JUnit позволяют эффективно управлять тестами и их жизненным циклом. Аннотации помогают:

- Определять тесты (`@Test`).
- Настраивать окружение перед и после тестов (`@BeforeEach`, `@AfterEach`, `@BeforeAll`, `@AfterAll`).
- Отключать тесты (`@Disabled`).
- Группировать тесты (`@Nested`).
- Запускать тесты несколько раз (`@RepeatedTest`).
- Параметризовать тесты (`@ParameterizedTest`).

## **3.1. @Test — основной маркер тестов**

Аннотация `@Test` указывает, что метод является тестом.

🔹 **Пример:**

```java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathTest {
    @Test
    void testAddition() {
        int result = 2 + 3;
        assertEquals(5, result, "Сумма должна быть 5");
    }
}
```

---

## **3.2. @BeforeEach и @AfterEach — настройка перед и после каждого теста**

- `@BeforeEach` выполняется **перед каждым тестом** (инициализация ресурсов).
- `@AfterEach` выполняется **после каждого теста** (очистка ресурсов).

---

## **3.3. @BeforeAll и @AfterAll — настройка перед и после всех тестов**

- `@BeforeAll` запускается **один раз перед всеми тестами** (например, подключение к БД).
- `@AfterAll` запускается **один раз после всех тестов** (например, закрытие БД).

🔹 **Пример:**

```java
import org.junit.jupiter.api.*;

class GlobalSetupTest {
    @BeforeAll
    static void init() {
        System.out.println("Один раз перед всеми тестами");
    }

    @Test
    void test1() {
        System.out.println("Тест 1");
    }

    @Test
    void test2() {
        System.out.println("Тест 2");
    }

    @AfterAll
    static void cleanup() {
        System.out.println("Один раз после всех тестов");
    }
}
```

> **Важно!** `@BeforeAll` и `@AfterAll` должны быть **static**.

---

## **3.4. @DisplayName — задание названия теста**

Позволяет задавать читаемые названия тестов.

🔹 **Пример:**

```java
import org.junit.jupiter.api.*;

class DisplayNameTest {
    @Test
    @DisplayName("Проверка сложения двух чисел")
    void testSum() {
        int result = 2 + 2;
        Assertions.assertEquals(4, result);
    }
}
```

---

## **3.5. @Disabled — отключение тестов**

Если тест временно не нужен, его можно отключить аннотацией `@Disabled`.

🔹 **Пример:**

```java
import org.junit.jupiter.api.*;

class DisabledTest {
    @Test
    void testEnabled() {
        System.out.println("Этот тест выполняется");
    }

    @Test
    @Disabled("Этот тест временно отключен")
    void testDisabled() {
        System.out.println("Этот тест не запустится");
    }
}
```

---

## **3.6. @RepeatedTest — повторение теста**

Позволяет запустить тест **несколько раз**.

🔹 **Пример:**

```java
import org.junit.jupiter.api.*;

class RepeatedExampleTest {
    @RepeatedTest(3)
    void repeatedTest(RepetitionInfo info) {
        System.out.println("Выполняется тест #" + info.getCurrentRepetition());
    }
}
```

---

## **3.7. @ParameterizedTest — параметризованные тесты**

Позволяет запускать тест с разными входными данными.

🔹 **Пример использования `@ValueSource`:**

```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParameterizedExampleTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 4, 6, 8})
        // Четные числа
    void testIsEven(int number) {
        assertTrue(number % 2 == 0, number + " не является четным числом");
    }
}
```

---

# **4. Как работает JUnit изнутри**

JUnit — это фреймворк для тестирования кода на Java, который обеспечивает удобный механизм запуска тестов, проверки их результатов и генерации отчетов.

## **4.1. Жизненный цикл теста в JUnit**
Когда JUnit запускает тест, он выполняет следующие шаги:
1. Создаёт новый экземпляр тестового класса.
2. Выполняет методы, аннотированные `@BeforeAll` (один раз перед всеми тестами, если тесты выполняются в одном классе).
3. Для каждого теста выполняются:
   - `@BeforeEach` (инициализация перед тестом).
   - Сам тестовый метод, помеченный `@Test`.
   - `@AfterEach` (действия после теста).
4. После выполнения всех тестов в классе выполняются методы, аннотированные `@AfterAll` (если тесты выполняются в одном классе).

## **4.2. Архитектура JUnit**
JUnit состоит из нескольких ключевых компонентов:
- **JUnit Platform** – общая платформа для запуска тестов.
- **JUnit Jupiter** – новый API для написания тестов (JUnit 5).
- **JUnit Vintage** – поддержка тестов, написанных на JUnit 3 и 4.

Когда JUnit запускает тест, он использует **Test Engine**, который:
1. Обнаруживает классы с тестами.
2. Анализирует аннотации и определяет порядок выполнения.
3. Запускает тестовые методы и собирает результаты.
4. Генерирует отчеты.

## **4.3. Параллельное выполнение тестов**
По умолчанию JUnit выполняет тесты **последовательно**, но начиная с JUnit 5.3 появилась поддержка **параллельного выполнения тестов**.

### **4.3.1. Включение параллельного исполнения**
JUnit позволяет запускать тесты параллельно, если добавить в файл `junit-platform.properties`:
```
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent
```

Можно также управлять этим через код:
```java
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.api.Test;

class ParallelTest {
    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test1() {
        System.out.println("Тест 1 выполняется потоком: " + Thread.currentThread().getName());
    }

    @Test
    @Execution(ExecutionMode.CONCURRENT)
    void test2() {
        System.out.println("Тест 2 выполняется потоком: " + Thread.currentThread().getName());
    }
}
```

📌 **Вывод в консоли (пример):**
```
Тест 1 выполняется потоком: pool-1-thread-1
Тест 2 выполняется потоком: pool-1-thread-2
```

### **4.3.2. Управление стратегией выполнения**
JUnit позволяет задавать стратегию выполнения тестов:
- `ExecutionMode.SAME_THREAD` – запуск в одном потоке.
- `ExecutionMode.CONCURRENT` – выполнение в нескольких потоках.

Также можно настроить **уровень параллельности** в `junit-platform.properties`:
```
junit.jupiter.execution.parallel.config.strategy=fixed
junit.jupiter.execution.parallel.config.fixed.parallelism=4
```
Это запустит тесты **в 4 потоках**.

## **4.4. Порядок выполнения тестов в JUnit 5**

В **JUnit 5** (JUnit Jupiter) тесты **по умолчанию выполняются в неопределенном порядке**. Это означает, что JUnit **не гарантирует** последовательность выполнения тестов внутри одного класса.

### **Основные принципы порядка выполнения тестов в JUnit 5:**
- По умолчанию JUnit **не упорядочивает тесты** – они могут выполняться в любом порядке.
- Тесты **не зависят друг от друга**, поэтому выполнение каждого теста должно быть **изолированным**.
- Можно **явно указать порядок тестов**, используя аннотацию `@TestMethodOrder`.

---

## **4.4.1. Стандартный порядок (по умолчанию)**
Если порядок не задан, тесты выполняются **в случайном порядке**, что помогает выявлять **скрытые зависимости** между тестами.

### **Пример:**
```java
import org.junit.jupiter.api.Test;

class DefaultOrderTest {
    @Test
    void testA() {
        System.out.println("Тест A");
    }

    @Test
    void testB() {
        System.out.println("Тест B");
    }

    @Test
    void testC() {
        System.out.println("Тест C");
    }
}
```
📌 В разных запусках порядок может быть разным.

---

## **4.4.2. Явное задание порядка тестов с `@TestMethodOrder`**
JUnit 5 позволяет задать порядок выполнения тестов с помощью аннотации `@TestMethodOrder`.

### **Порядок по аннотации `@Order(n)`**
Можно задать порядок выполнения тестов с помощью `@Order(n)`.

### **Пример:**
```java
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderedTest {
    @Test
    @Order(2)
    void secondTest() {
        System.out.println("Тест 2");
    }

    @Test
    @Order(1)
    void firstTest() {
        System.out.println("Тест 1");
    }

    @Test
    @Order(3)
    void thirdTest() {
        System.out.println("Тест 3");
    }
}
```
📌 Вывод в консоли будет всегда:
```
Тест 1  
Тест 2  
Тест 3  
```

---

## **4.4.3. Порядок на основе имен методов (`MethodOrderer.MethodName`)**
JUnit может запускать тесты **в алфавитном порядке их названий**.

### **Пример:**
```java
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.MethodName.class)
class AlphabeticalOrderTest {
    @Test
    void testC() {
        System.out.println("Тест C");
    }

    @Test
    void testA() {
        System.out.println("Тест A");
    }

    @Test
    void testB() {
        System.out.println("Тест B");
    }
}
```
📌 Вывод всегда будет:
```
Тест A  
Тест B  
Тест C  
```

---

## **4.4.4. Порядок на основе случайного значения (`MethodOrderer.Random`)**
Если нужно перемешивать тесты **при каждом запуске**, можно использовать `MethodOrderer.Random.class`.

### **Пример:**
```java
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.Random.class)
class RandomOrderTest {
    @Test
    void test1() { System.out.println("Тест 1"); }

    @Test
    void test2() { System.out.println("Тест 2"); }

    @Test
    void test3() { System.out.println("Тест 3"); }
}
```
📌 Порядок выполнения **каждый раз разный**.

---

## **4.4.5. Итоговая таблица порядка выполнения тестов**

| Порядок тестов | Аннотация | Особенности |
|---------------|----------|-------------|
| **По умолчанию** | ❌ (нет) | Произвольный порядок, может меняться в разных запусках |
| **По `@Order(n)`** | `@TestMethodOrder(MethodOrderer.OrderAnnotation.class)` | Выполняется в заданном порядке |
| **Алфавитный** | `@TestMethodOrder(MethodOrderer.MethodName.class)` | Выполняется в порядке имен методов |
| **Случайный** | `@TestMethodOrder(MethodOrderer.Random.class)` | Каждый раз новый порядок |

---

## **4.4.6. Какой порядок использовать?**
✅ **По умолчанию** – если тесты изолированы и порядок неважен.  
✅ **По `@Order(n)`** – если важен строгий порядок выполнения.  
✅ **Случайный (`Random`)** – если нужно выявить скрытые зависимости.  
✅ **Кастомный порядок** – если есть специфические требования.

🚀 Если тесты зависят друг от друга – это **антипаттерн**. Тесты должны быть **независимыми**!

---

# **5. Введение в Mockito**
Mockito — это мощная библиотека для **мокирования зависимостей** в юнит-тестах. Она позволяет **имитировать поведение объектов**, чтобы тестировать класс **изолированно** от его зависимостей.

Mockito:
- Позволяет тестировать код без реальных зависимостей.  
- Упрощает написание юнит-тестов.  
- Помогает тестировать классы, взаимодействующие с базами данных, API и внешними сервисами.  
- Позволяет проверять, какие методы вызываются и с какими параметрами.  
- Упрощает разработку тестируемого кода за счёт изоляции тестируемого объекта.

---

## **5.2. Подключение Mockito в Gradle**
**Файл `build.gradle`**:
```groovy
dependencies {
    testImplementation 'org.mockito:mockito-core:5.11.0'
    testImplementation 'org.mockito:mockito-junit-jupiter:5.11.0'
}
```

**Файл `build.gradle.kts` (Kotlin DSL)**:
```kotlin
dependencies {
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
}
```

---

## **5.3. Создание моков**
Mockito позволяет создавать моки несколькими способами.

### **5.3.1. Создание мока через `Mockito.mock(Class.class)`**
```java
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

interface Database {
    String getData();
}

class ServiceTest {
    @Test
    void testWithMock() {
        Database mockDb = Mockito.mock(Database.class);
        when(mockDb.getData()).thenReturn("Mocked Data");

        String result = mockDb.getData();
        System.out.println(result); // Выведет: Mocked Data
    }
}
```

### **5.3.2. Создание моков с аннотацией `@Mock`**
```java
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

interface Database {
    String getData();
}

class MockAnnotationTest {
    @Mock
    Database database;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMock() {
        when(database.getData()).thenReturn("Mocked Response");
        System.out.println(database.getData()); // Mocked Response
    }
}
```
---

## **5.3.3. Инициализация моков перед каждым тестом**

Если моки создаются в методе `@BeforeEach` (JUnit 5) или `@Before` (JUnit 4), то они будут **инициализироваться перед каждым тестом заново**.

### **Пример 1: Инициализация перед каждым тестом**
```java
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ExampleTest {
    
    @Mock
    private Database database;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Инициализация моков перед каждым тестом
    }

    @Test
    void test1() {
        when(database.getData()).thenReturn("Mocked Data");
        System.out.println(database.getData()); // Выведет: Mocked Data
    }

    @Test
    void test2() {
        when(database.getData()).thenReturn("Another Mocked Data");
        System.out.println(database.getData()); // Выведет: Another Mocked Data
    }
}
```
📌 **Что здесь происходит?**
- Перед **каждым** тестом (`@Test`) **Mockito создаёт новый мок**.
- Данные моков **не сохраняются между тестами**.
- Это полезно, если моки **должны быть чистыми** для каждого теста.

---

## **5.3.4. Инициализация моков один раз для класса**

Если моки создаются в методе `@BeforeAll` (JUnit 5) или `@BeforeClass` (JUnit 4), то они **инициализируются один раз** и **используются во всех тестах**.

### **Пример 2: Инициализация один раз для всего класса**
```java
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class ExampleTest {
    
    @Mock
    private static Database database;

    @BeforeAll
    static void setup() {
        MockitoAnnotations.openMocks(ExampleTest.class); // Инициализация моков один раз
    }

    @Test
    void test1() {
        when(database.getData()).thenReturn("Mocked Data");
        System.out.println(database.getData()); // Выведет: Mocked Data
    }

    @Test
    void test2() {
        System.out.println(database.getData()); // Может вывести: Mocked Data (если не изменено)
    }
}
```
📌 **Что здесь происходит?**
- **Мок создаётся один раз** и **используется во всех тестах**.
- Значения **могут сохраняться** между тестами (если не сбрасывать вручную).
- Полезно, если мок **ресурсоёмкий** (например, заглушка базы данных) и не нужно его пересоздавать перед каждым тестом.

---

## **5.3.5. Использование `@ExtendWith(MockitoExtension.class)` (JUnit 5)**

Если тест использует **JUnit 5**, можно подключить `MockitoExtension`, и тогда **моки будут автоматически инициализироваться перед каждым тестом**.

### **Пример 3: Автоматическая инициализация перед каждым тестом**
```java
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Автоматическая инициализация моков
class ExampleTest {
    
    @Mock
    private Database database;

    @InjectMocks
    private Service service;

    @Test
    void test1() {
        when(database.getData()).thenReturn("Mocked Data");
        System.out.println(database.getData()); // Выведет: Mocked Data
    }

    @Test
    void test2() {
        System.out.println(database.getData()); // Выведет: null (мок переинициализируется)
    }
}
```
📌 **Что здесь происходит?**
- `MockitoExtension` автоматически **инициализирует моки перед каждым тестом**.
- Можно использовать **`@Mock` + `@InjectMocks`**, без `MockitoAnnotations.openMocks(this)`.

---

## **5.3.6. Вывод**

| Способ инициализации | Когда создаётся мок? | Где использовать? |
|----------------------|---------------------|------------------|
| `Mockito.mock(Class.class)` | Внутри теста (каждый раз заново) | Для точечного создания моков |
| `@Mock + MockitoAnnotations.openMocks(this);` | **Перед каждым тестом** (`@BeforeEach`) | Если моки должны быть чистыми для каждого теста |
| `@Mock + @BeforeAll` | **Один раз для всего класса** | Если моки должны сохраняться между тестами |
| `@ExtendWith(MockitoExtension.class)` (JUnit 5) | **Перед каждым тестом автоматически** | Удобный способ без `@BeforeEach` |

### **Когда что использовать?**
✅ **Перед каждым тестом (`@BeforeEach`)** – когда каждый тест должен **иметь чистый мок**, не зависящий от других.  
✅ **Один раз для класса (`@BeforeAll`)** – если **инициализация моков занимает много ресурсов** и их не нужно пересоздавать.  
✅ **`@ExtendWith(MockitoExtension.class)` (JUnit 5)** – **удобный автоматический вариант**, если не хочется вручную инициализировать моки.

---

## **5.4. Stub-ирование поведения моков**
### **5.4.1. thenReturn**
Метод возвращает заранее заданное значение.
```java
when(database.getData()).thenReturn("Mocked Value");
```

### **5.4.2. thenThrow**
Можно эмулировать выброс исключения.
```java
when(database.getData()).thenThrow(new RuntimeException("Ошибка!"));
```

### **5.4.3. thenAnswer (гибкая логика)**
Если нужно вычислять значение динамически:
```java
when(database.getData()).thenAnswer(invocation -> "Dynamic Data " + System.currentTimeMillis());
```

---

## **5.5. Проверка вызова методов (`verify`)**
### **5.5.1. Проверяем, что метод вызвался**
```java
verify(database).getData();
```

### **5.5.2. Проверяем количество вызовов**
```java
verify(database, times(2)).getData();
```

### **5.5.3. Проверяем, что метод не вызывался**
```java
verify(database, never()).getData();
```

### **5.5.4. Проверяем вызов с конкретными аргументами**
```java
verify(database).saveData(eq("Test Data"));
```

---

## **5.6. @InjectMocks — внедрение моков**
```java
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

interface Database {
    String getData();
}

class Service {
    private final Database database;
    Service(Database database) {
        this.database = database;
    }
    String process() {
        return database.getData() + " Processed";
    }
}

class InjectMocksTest {
    @Mock
    Database database;

    @InjectMocks
    Service service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testService() {
        when(database.getData()).thenReturn("Mocked DB Data");
        String result = service.process();
        System.out.println(result); // Mocked DB Data Processed
    }
}
```

---

## **5.7. ArgumentCaptor — перехват аргументов**
```java
import org.mockito.ArgumentCaptor;

ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
verify(database).saveData(captor.capture());
String capturedValue = captor.getValue();
System.out.println("Captured: " + capturedValue);
```

---

## **5.8. Мокирование статических методов**
```java
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

class Utils {
    static long getTime() {
        return System.currentTimeMillis();
    }
}

class StaticMockTest {
    @Test
    void testStaticMethod() {
        try (MockedStatic<Utils> mockedStatic = mockStatic(Utils.class)) {
            mockedStatic.when(Utils::getTime).thenReturn(1000L);
            long time = Utils.getTime();
            System.out.println(time); // 1000
        }
    }
}
```

---

