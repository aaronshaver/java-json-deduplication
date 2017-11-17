import com.aaronshaver.JsonHelpers;
import com.aaronshaver.Main;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class ArgumentsTests {

    @Test
    public void testTooFewArguments() {
        String[] args = new String[0];
        Assert.assertFalse(Main.isRequiredNumArguments(args));
    }

    @Test
    public void testTooManyArguments() {
        String[] args = new String[2];
        Assert.assertFalse(Main.isRequiredNumArguments(args));
    }

    @Test
    public void testNumArgumentsHappyPath() {
        String[] args = new String[1];
        Assert.assertTrue(Main.isRequiredNumArguments(args));
    }
}