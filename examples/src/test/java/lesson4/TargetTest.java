package lesson4;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.nsu.ccfit.khudyakov.lessons.lesson4.Dependency;
import ru.nsu.ccfit.khudyakov.lessons.lesson4.Target;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TargetTest {

    @Test
    public void foo_barWithoutMock_expectedFiveSecond() {
        Target target = new Target(new Dependency());
        int expected = 5000;
        int res = target.foo();
        assertEquals(expected, res);
    }

    @Test
    public void foo_barWithMock_expectedSecond() throws InterruptedException {
        int expectedTimeout = 1000;

        Dependency dependency = Mockito.mock(Dependency.class);
        when(dependency.bar(anyInt())).thenReturn(expectedTimeout);
        Target target = new Target(dependency);

        int res = target.foo();
        verify(dependency).bar(5000);
        assertEquals(expectedTimeout, res);
    }

}