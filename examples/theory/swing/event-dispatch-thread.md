# Event Dispatch Thread (EDT) в Swing

## 1. Что такое Event Dispatch Thread (EDT)?
`Event Dispatch Thread` (EDT) — это **главный поток обработки событий в Swing**. Он отвечает за:
- Обработку пользовательских событий (клики, ввод с клавиатуры, перемещение мыши).
- Обновление графического интерфейса.
- Вызовы методов `paintComponent()` и `repaint()`.

**Swing использует однонитевую модель**, поэтому все изменения UI **должны выполняться в EDT**, чтобы избежать проблем с многопоточностью.

---

## 2. Почему важно обновлять графику в EDT?
Если обновлять UI **из другого потока**, могут возникнуть следующие проблемы:
1. **Гонка потоков (`Race Condition`)** – несколько потоков изменяют UI одновременно, вызывая неожиданные ошибки.
2. **Артефакты отрисовки** – некорректное обновление элементов интерфейса.
3. **Краш программы** – попытка доступа к UI-компоненту из другого потока может привести к `IllegalStateException`.

Пример **НЕПРАВИЛЬНОГО** обновления UI из фонового потока:
```java
new Thread(() -> {
    JFrame frame = new JFrame("Wrong EDT Example");
    frame.setSize(400, 300);
    frame.setVisible(true); // ❌ Ошибка! UI создается вне EDT
}).start();
```

✅ **Используем `SwingUtilities.invokeLater()` для безопасного обновления UI.**

---

## 3. Как правильно работать с EDT?
### **1. Использование `SwingUtilities.invokeLater()`**
Метод `invokeLater()` ставит задачу в очередь EDT **асинхронно**.
```java
SwingUtilities.invokeLater(() -> {
    JFrame frame = new JFrame("Correct EDT Example");
    frame.setSize(400, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
});
```
✅ **Гарантирует, что код выполняется в EDT.**

### **2. Использование `SwingUtilities.invokeAndWait()`**
Метод `invokeAndWait()` ставит задачу в очередь EDT **с ожиданием завершения**.
```java
SwingUtilities.invokeAndWait(() -> {
    System.out.println("Этот код выполняется в EDT и блокирует текущий поток!");
});
```
⚠️ **Использовать с осторожностью! Может вызвать Deadlock, если вызывается из EDT.**

### **3. Проверка, выполняется ли код в EDT**
Перед выполнением кода можно проверить, находимся ли мы в EDT:
```java
if (SwingUtilities.isEventDispatchThread()) {
    System.out.println("Код выполняется в EDT");
} else {
    System.out.println("Код выполняется в другом потоке");
}
```

---

## 4. Как выполнять долгие задачи, не блокируя EDT?
Если выполнить **тяжёлую операцию** в EDT (например, загрузку данных), UI **зависнет**.

### ❌ **Неправильный пример (UI зависнет)**
```java
button.addActionListener(e -> {
    for (int i = 0; i < 1_000_000_000; i++); // Имитация долгой работы
    System.out.println("Загрузка завершена!");
});
```
✅ Используем `SwingWorker` для фоновых операций.

### ✅ **Правильный пример с `SwingWorker`**
```java
SwingWorker<Void, Void> worker = new SwingWorker<>() {
    @Override
    protected Void doInBackground() throws Exception {
        Thread.sleep(3000); // Имитация загрузки данных
        return null;
    }
    
    @Override
    protected void done() {
        System.out.println("Загрузка завершена!");
    }
};
worker.execute();
```
✅ `doInBackground()` выполняется **в фоновом потоке**.
✅ `done()` вызывается **в EDT** для обновления UI.

---

## 5. Итог
- **EDT управляет всеми операциями с UI в Swing.**
- **Нельзя обновлять UI из других потоков.**
- **Используем `SwingUtilities.invokeLater()` для безопасного обновления UI.**
- **Тяжёлые операции выполняем в `SwingWorker`, чтобы не блокировать UI.**

🚀 **Теперь ты знаешь, как правильно работать с EDT и избегать ошибок в Swing!**

