# Разница между `JFrame` и `JPanel` в Swing

## 1. `JFrame`
`JFrame` — это **главное окно приложения** в Swing, которое содержит все остальные компоненты.

### Основные особенности `JFrame`:
- Используется как **основное окно** приложения.
- Имеет **панель содержимого (`ContentPane`)**, куда добавляются компоненты.
- Поддерживает **меню (`JMenuBar`)**, заголовок окна, кнопки закрытия/сворачивания.
- Может быть **развернутым, свернутым, изменяемым по размеру**.

### Пример использования `JFrame`:
```java
import javax.swing.*;

public class JFrameExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JFrame Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
```

---

## 2. `JPanel`
`JPanel` — это **контейнер для группировки компонентов**, который не может существовать отдельно от окна (`JFrame`).

### Основные особенности `JPanel`:
- Используется для **организации компонентов** внутри `JFrame`.
- Может содержать **другие `JPanel`** для вложенной компоновки.
- **Не является окном**, не имеет заголовка и кнопок управления.
- Поддерживает **кастомную отрисовку** через `paintComponent(Graphics g)`.

### Пример использования `JPanel`:
```java
import javax.swing.*;
import java.awt.*;

public class JPanelExample {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JPanel Example");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(new JButton("Click Me"));
        
        frame.add(panel);
        frame.setVisible(true);
    }
}
```

---

## 3. Сравнение `JFrame` и `JPanel`

| Свойство          | `JFrame`                          | `JPanel`                      |
|-------------------|--------------------------------|------------------------------|
| Является окном   | ✅ Да                           | ❌ Нет                         |
| Может существовать отдельно | ✅ Да                           | ❌ Нет, нужен `JFrame`         |
| Поддерживает `paintComponent()` | ❌ Нет (использует `paint()`) | ✅ Да (кастомная отрисовка)    |
| Содержит `ContentPane` | ✅ Да                           | ❌ Нет                         |
| Используется для | Главного окна приложения     | Организации компонентов      |

---

## 4. Когда использовать `JFrame`, а когда `JPanel`?
✅ **Используйте `JFrame`**, когда нужно создать **главное окно** приложения.  
✅ **Используйте `JPanel`**, когда нужно **группировать элементы внутри окна**.

---

## 5. Заключение
- `JFrame` — это **главное окно**, в котором размещаются элементы интерфейса.
- `JPanel` — это **контейнер**, который используется внутри `JFrame`.
- `JPanel` позволяет **кастомно рисовать через `paintComponent()`**, в отличие от `JFrame`.