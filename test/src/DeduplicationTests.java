import com.aaronshaver.ConsoleOutput;
import org.junit.Assert;
import org.junit.Test;

public class DeduplicationTests {

    @Test
    public void testWriteStartMessage() {
        ConsoleOutput console = new ConsoleOutput();
        Assert.assertEquals("Deduplicating...", console.writeStartMessage());
    }

    @Test
    public void testWriteBadArgumentsMessage() {
        ConsoleOutput console = new ConsoleOutput();
        Assert.assertEquals("Please use one argument, after the .jar file and a space, pointing to .JSON file",
                console.writeBadArgumentsMessage());
    }
}
