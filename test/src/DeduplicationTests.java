import com.aaronshaver.ConsoleOutput;
import org.junit.Assert;
import org.junit.Test;

public class DeduplicationTests {

    @Test
    public void testStartMessage() {
        ConsoleOutput console = new ConsoleOutput();
        Assert.assertEquals("Deduplicating...", console.startMessage());
    }
}
