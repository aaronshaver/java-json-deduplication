import com.aaronshaver.JsonHelpers;
import com.aaronshaver.Main;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class DeduplicatorTests {

    @Test
    public void testInvalidJson() {
        String data = "!@#$%^&*()_+";
        Assert.assertFalse(JsonHelpers.isValidJson(data));
    }

    @Test
    public void testValidCodeChallengeJson() {
        Assert.assertTrue(JsonHelpers.isValidJson("{\"leads\":[{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"edu45238jdsnfsj23\",\"email\":\"mae@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"wabaj238238jdsnfsj23\",\"email\":\"bog@bar.com\",\"firstName\":\"Fran\",\"lastName\":\"Jones\",\"address\":\"8803 Dark St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"coo@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Jones\",\"address\":\"456 Neat St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"sel045238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"qest38238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"vug789238jdsnfsj23\",\"email\":\"foo1@bar.com\",\"firstName\":\"Blake\",\"lastName\":\"Douglas\",\"address\":\"123 Reach St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"wuj08238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"Micah\",\"lastName\":\"Valmer\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"belr28238jdsnfsj23\",\"email\":\"mae@bar.com\",\"firstName\":\"Tallulah\",\"lastName\":\"Smith\",\"address\":\"123 Water St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"bill@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"888 Mayberry St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"}]}"));
    }

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

    @Test
    public void testGetJsonFromString() {
        String data = "{}";
        Assert.assertThat(JsonHelpers.getJsonFromString(data), instanceOf(JsonObject.class));
    }

    @Test
    public void testIsValidJson() {
        Assert.assertTrue(JsonHelpers.isValidJson("{}"));
    }
}