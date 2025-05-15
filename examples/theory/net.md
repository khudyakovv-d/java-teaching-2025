## 🔧 Сетевые основы

### 📶 Модель OSI (Open Systems Interconnection)

Модель OSI — концептуальная схема, описывающая, как данные передаются по сети. Включает 7 уровней:

| Уровень          | Назначение                                 | Примеры                  |
| ---------------- | ------------------------------------------ | ------------------------ |
| 7. Прикладной    | Взаимодействие с приложениями пользователя | HTTP, FTP, DNS           |
| 6. Представления | Форматирование данных, шифрование          | TLS, JPEG, JSON          |
| 5. Сеансовый     | Управление сессиями                        | RPC, NetBIOS             |
| 4. Транспортный  | Доставка сообщений между хостами           | **TCP**, **UDP**         |
| 3. Сетевой       | Определение маршрута                       | **IP**, ICMP             |
| 2. Канальный     | Физическая передача кадров                 | Ethernet, PPP            |
| 1. Физический    | Фактическая передача битов                 | Кабель, Wi-Fi, Bluetooth |

🔹 *Для Java сокетов важны в первую очередь уровни 3 и 4 — IP и TCP/UDP.*

---

### 📦 Что такое пакет

**Сетевой пакет** — это единица данных, передаваемая по сети. Он состоит из:

* **Заголовка (header)** — содержит служебную информацию (например, адрес отправителя и получателя, тип протокола, длину и т.д.)
* **Полезной нагрузки (payload)** — сами данные, которые передаются

Пакеты создаются и обрабатываются на каждом уровне стека протоколов. Каждый уровень добавляет свой заголовок:

* IP-заголовок (сетевой уровень)
* TCP/UDP-заголовок (транспортный уровень)

Например:

* TCP-заголовок включает номера портов, флаги управления (`SYN`, `ACK`), контрольную сумму и т.д.
* UDP-заголовок содержит только номер порта источника, назначения, длину и контрольную сумму

Чем меньше заголовок — тем быстрее передача, но меньше контроля.

---

### 🌐 IP-адреса

IP-адрес (например, `192.168.0.101`) — это уникальный адрес устройства в сети.

* IPv4: 4 байта (например, `192.168.1.1`)
* IPv6: 16 байт (например, `2001:0db8:85a3::8a2e:0370:7334`)
* IP отвечает за доставку пакета **куда** нужно.

---

### 🔌 Порты

Порт — это логический канал на устройстве.

* 1–1023: системные порты (например, 80 для HTTP)
* 1024–49151: зарегистрированные порты
* 49152–65535: динамические/приватные порты

Пример: соединение к `192.168.0.1:8080` — это запрос на устройство `192.168.0.1` и порт `8080`.

---

## 🧱 Протоколы транспортного уровня: TCP и UDP

### ⚙️ TCP (Transmission Control Protocol)

**Надёжный, ориентированный на соединение**.

#### Особенности:

* Устанавливается соединение (трёхстороннее рукопожатие)
* Гарантия доставки, порядка, целостности
* Управление перегрузкой
* Медленнее, но безопаснее

#### Применение:

* HTTP/HTTPS
* FTP
* Email (SMTP, POP3, IMAP)

---

### ⚡ UDP (User Datagram Protocol)

**Простой, быстрый, без установки соединения**.

#### Особенности:

* Нет установки соединения
* Нет гарантии доставки или порядка
* Нет подтверждения получения
* Меньше накладных расходов

#### Как работает:

* Просто отправляется датаграмма (пакет) по адресу и порту
* Получатель может даже не существовать

#### Применение:

* Онлайн-игры
* Видеозвонки, VoIP
* DNS-запросы

---

### 🆚 Сравнение TCP и UDP

| Характеристика        | TCP                      | UDP                 |
| --------------------- | ------------------------ | ------------------- |
| Соединение            | Да (установление сессии) | Нет                 |
| Надёжность            | Да (гарантия доставки)   | Нет                 |
| Порядок пакетов       | Гарантирован             | Не гарантирован     |
| Скорость              | Ниже из-за контроля      | Выше                |
| Размер заголовка      | Больше (\~20 байт)       | Меньше (\~8 байт)   |
| Примеры использования | Веб, email, файлы        | Игры, стриминг, DNS |

---

## 🧩 Сокеты в Java

Сокеты позволяют приложениям обмениваться данными через сеть. В Java для этого используются классы из пакета `java.net`.

### 🔸 Основные классы TCP

* `ServerSocket` — для сервера
* `Socket` — для клиента (и внутри сервера для общения с клиентом)

#### 🔍 Конструкторы TCP-сокетов

**ServerSocket**:

* `ServerSocket(int port)` — создаёт сервер, слушающий указанный порт.
* `ServerSocket(int port, int backlog)` — с максимальной очередью подключений.
* `ServerSocket(int port, int backlog, InetAddress bindAddr)` — слушает указанный интерфейс.

**Socket**:

* `Socket(String host, int port)` — подключается к удалённому хосту и порту.
* `Socket(InetAddress address, int port)` — аналогично, но с IP-адресом.
* `Socket()` + `connect(SocketAddress endpoint)` — позволяет вручную задать таймауты и настройки перед подключением.

### 🧾 Потоки ввода/вывода, блокирующее чтение

При работе с TCP-сокетами часто используют:

* `PrintWriter` — для удобной отправки текстовых данных (оборачивает `OutputStream`, можно использовать `println`, `printf`)
* `Scanner` — для удобного построчного чтения входящего потока (`InputStream`)

#### Блокирующее поведение

Методы чтения, такие как `Scanner.nextLine()` или `BufferedReader.readLine()`, **блокируют выполнение**, пока не получат данные или соединение не будет закрыто.

Это означает, что если клиент ничего не отправил, то поток "зависнет" в ожидании данных:

```java
Scanner in = new Scanner(socket.getInputStream());
String line = in.nextLine(); // блокирующее чтение
```

Аналогично с `BufferedReader.readLine()`. Чтобы избежать зависания, можно использовать многопоточность или задать таймауты с помощью `socket.setSoTimeout(milliseconds)`.

#### Пример использования Scanner и PrintWriter:

```java
Scanner in = new Scanner(socket.getInputStream());
PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

out.println("Введите ваше имя:");
String name = in.nextLine();
System.out.println("Пользователь ввёл: " + name);
```

---

### 🧪 Примеры TCP и UDP сокетов

#### ✅ TCP-сервер

```java
import java.net.*;
import java.io.*;

public class TcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Ожидание клиента...");
        Socket socket = serverSocket.accept();

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Привет, клиент!");
        String clientMessage = in.readLine();
        System.out.println("Клиент говорит: " + clientMessage);

        socket.close();
        serverSocket.close();
    }
}
```

#### ✅ TCP-клиент

```java
import java.net.*;
import java.io.*;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String messageFromServer = in.readLine();
        System.out.println("Сервер сказал: " + messageFromServer);

        out.println("Привет, сервер!");

        socket.close();
    }
}
```

#### 🔄 UDP-сервер

```java
import java.net.*;

public class UdpServer {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(1234);
        byte[] buffer = new byte[1024];

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);

        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Получено: " + received);

        socket.close();
    }
}
```

#### 🔄 UDP-клиент

```java
import java.net.*;

public class UdpClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress address = InetAddress.getByName("localhost");

        String message = "Привет, сервер!";
        byte[] buffer = message.getBytes();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 1234);
        socket.send(packet);

        socket.close();
    }
}
```

---

## 🧳 Передача объектов по сети и сериализация

Java поддерживает передачу объектов через сокеты с помощью механизма **сериализации** — преобразования объекта в поток байтов.

### 📦 Что такое сериализация

Чтобы объект можно было передать по сети или сохранить в файл, он должен реализовывать интерфейс `java.io.Serializable`.

```java
import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return name + ", " + age;
    }
}
```

### ✅ Требования для сериализации

1. **Интерфейс **`Serializable`****

    * Класс должен явно реализовывать `java.io.Serializable`. Этот интерфейс маркерный и не содержит методов.

2. **Все поля объекта должны быть сериализуемыми**

    * Если какое-либо поле не сериализуемо, его нужно либо сделать `transient`, либо исключить из сериализации другим способом.

3. **Доступность классов при десериализации**

    * Класс, используемый при десериализации, должен быть доступен в ClassPath и совпадать по имени и пакету с тем, что использовался при сериализации.

4. **Совместимость версий**

    * При необходимости можно добавить поле `private static final long serialVersionUID`, чтобы контролировать совместимость между версиями классов:
    ```java
    private static final long serialVersionUID = 1L;
    ```

### 🧬 Как выглядит сериализованный объект

Сериализованный объект в Java — это бинарный поток байтов, включающий:

1. **Магическая сигнатура** (4 байта) — `0xACED` (начало сериализованного потока)
2. **Версия сериализации** — 2 байта (обычно `0x0005`)
3. **Описание класса**: имя, поля, UID, информация о типах
4. **Значения полей**: сериализованные данные в порядке объявления
5. **Конец объекта**

Пример сериализованного объекта (в hex):

```
AC ED 00 05 73 72 00 0C 50 65 72 73 6F 6E ...
```

Чтобы посмотреть структуру сериализованного объекта:

* Используй `hexdump`, `xxd` или `SerialVer` для анализа
* Можно сохранить в файл и загрузить обратно:

```java
ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("person.ser"));
out.writeObject(person);
out.close();
```

---

### 🔽 Отправка объекта (TCP-клиент)

```java
import java.io.*;
import java.net.*;

public class ObjectSender {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

        Person person = new Person("Alice", 30);
        out.writeObject(person);

        out.close();
        socket.close();
    }
}
```

### 🔼 Приём объекта (TCP-сервер)

```java
import java.io.*;
import java.net.*;

public class ObjectReceiver {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket socket = serverSocket.accept();

        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Person received = (Person) in.readObject();
        System.out.println("Получен объект: " + received);

        in.close();
        socket.close();
        serverSocket.close();
    }
}
```

### 🛠 Работа с ObjectOutputStream и ObjectInputStream

Классы `ObjectOutputStream` и `ObjectInputStream` предоставляют простой способ записи и чтения объектов в потоках. Они являются частью `java.io` и работают поверх `OutputStream` и `InputStream` соответственно.

#### 🔽 ObjectOutputStream

Используется для сериализации объектов (запись в поток).

**Основные методы:**

* `writeObject(Object obj)` — записывает сериализуемый объект
* `flush()` — очищает буфер и отправляет все данные

**Пример:**

```java
ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
out.writeObject(person);
out.flush();
out.close();
```

#### 🔼 ObjectInputStream

Используется для десериализации объектов (чтение из потока).

**Основные методы:**

* `readObject()` — читает объект из потока и возвращает его как `Object`
* Требуется приведение типа (casting)

**Пример:**

```java
ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
Person received = (Person) in.readObject();
in.close();
```

**Важно:**

* Потоки должны открываться в правильном порядке: сначала `ObjectOutputStream`, потом `ObjectInputStream`
* Несоблюдение порядка может привести к зависаниям из-за обмена служебной информацией Java в начале потока

---

## 🧾 Сериализация в XML

Помимо бинарной сериализации, Java поддерживает сериализацию объектов в формат XML с использованием `JAXB` (Java Architecture for XML Binding).

### ✅ Требования к классу:

1. Класс должен иметь публичный конструктор без аргументов
2. Класс и поля должны быть аннотированы:

    * `@XmlRootElement` — указывает корневой элемент XML
    * `@XmlElement` — указывает сериализуемое поле

### 📦 Пример класса

```java
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
    private String name;
    private int age;

    public Person() {} // обязательный конструктор

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @XmlElement
    public String getName() { return name; }

    @XmlElement
    public int getAge() { return age; }
}
```

### 🔽 Сериализация в XML

```java
import jakarta.xml.bind.*;
import java.io.*;

Person person = new Person("Alice", 30);
StringWriter writer = new StringWriter();
JAXBContext context = JAXBContext.newInstance(Person.class);
Marshaller marshaller = context.createMarshaller();
marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
marshaller.marshal(person, writer);

System.out.println(writer.toString());
```

### 🔼 Десериализация из XML

```java
StringReader reader = new StringReader(xmlString);
Unmarshaller unmarshaller = context.createUnmarshaller();
Person restored = (Person) unmarshaller.unmarshal(reader);
```

### ⚠️ Особенности

* JAXB работает только с классами с пустым конструктором и геттерами/сеттерами
* Хорошо подходит для совместимости с другими системами и языками
* В Java 11+ необходимо добавить зависимости на `jakarta.xml.bind` вручную

### 🧰 Работа с DOM и JAXB

Для более гибкой работы с XML в Java можно использовать DOM API: `DocumentBuilderFactory` и `DocumentBuilder`. Это позволяет вручную строить, изменять и разбирать XML-документы.

#### 📘 DocumentBuilderFactory и DocumentBuilder

* `DocumentBuilderFactory` — фабрика для создания `DocumentBuilder`
* `DocumentBuilder` — создаёт или разбирает XML в дерево DOM (`org.w3c.dom.Document`)

**Пример парсинга XML-файла:**

```java
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;

File file = new File("person.xml");
DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = factory.newDocumentBuilder();
Document doc = builder.parse(file);

Node nameNode = doc.getElementsByTagName("name").item(0);
System.out.println("Имя: " + nameNode.getTextContent());
```

#### 📦 Связь с JAXB

Хотя JAXB сам по себе не требует работы с DOM напрямую, в ряде случаев можно объединить их:

* **Парсинг XML вручную с DOM**, затем передача содержимого в JAXB
* **Получение DOM-дерева из JAXB** через `Marshaller.marshal(Object, org.w3c.dom.Node)`
* **Интеграция с редакторами XML или генерацией документации**

JAXB = удобный способ привязки Java-классов к XML,
DOM API = ручной контроль за структурой и содержимым XML.

---