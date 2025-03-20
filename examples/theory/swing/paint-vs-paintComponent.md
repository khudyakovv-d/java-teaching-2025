# Разница между `paint()` и `paintComponent()` в Swing

## 1. `paint()`
`paint(Graphics g)` — это метод, унаследованный от `Component`, который отвечает за полную перерисовку компонента. Включает:
- **Фон компонента**
- **Границы компонента**
- **Содержимое компонента**

### Основные особенности:
- Вызывается системой при необходимости обновить компонент (изменение размера, сворачивание/разворачивание окна и т. д.).
- Включает вызовы `paintComponent()`, `paintBorder()` и `paintChildren()`.
- Нельзя переопределять в компонентах, унаследованных от `JComponent`.

### Пример `paint()` в `AWT` (не рекомендуется в `Swing`):
```java
@Override
public void paint(Graphics g) {
    super.paint(g);
    g.setColor(Color.RED);
    g.fillRect(50, 50, 100, 100);
}
```

## 2. `paintComponent()`
`paintComponent(Graphics g)` — это метод, предназначенный **специально для пользовательского рисования в Swing**.

### Основные особенности:
- Используется в `JComponent` (`JPanel`, `JButton`, `JLabel` и др.).
- Отвечает только за **отрисовку содержимого**, не затрагивает фон и границы.
- Нужно **всегда вызывать `super.paintComponent(g)`** для корректного обновления компонента.

### Пример `paintComponent()` в `Swing` (правильный способ рисования):
```java
import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Очищает фон
        g.setColor(Color.BLUE);
        g.fillOval(50, 50, 100, 100);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("paintComponent Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.add(new CustomPanel());
        frame.setVisible(true);
    }
}
```

## 3. Сравнение `paint()` vs `paintComponent()`
| Метод              | Где используется       | Что делает |
|-------------------|----------------------|------------|
| `paint()`        | `Component` (AWT)     | Полностью обновляет компонент (фон, границы, содержимое) |
| `paintComponent()` | `JComponent` (Swing)  | Рисует только содержимое компонента, не затрагивает фон и границы |

## 4. Когда использовать `paintComponent()`?
Используйте `paintComponent()` в `JComponent`, когда нужно:
- Отрисовать кастомную графику (фигуры, изображения, линии).
- Обновлять UI без артефактов (благодаря двойной буферизации).
- Создать пользовательские визуальные эффекты (например, анимации).

## 5. Заключение
- **В Swing `paintComponent()` заменяет `paint()` для кастомного рисования.**
- **Никогда не переопределяйте `paint()` в `JComponent`, используйте `paintComponent()`.**
- **Обязательно вызывайте `super.paintComponent(g)` в `paintComponent()`, чтобы избежать графических ошибок.**

🎨 **Теперь вы знаете, как правильно рисовать в Swing! 🚀**