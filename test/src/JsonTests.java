import com.aaronshaver.JsonHelpers;
import com.aaronshaver.Main;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class JsonTests {

    private final String jsonCodeChallenge = "{\"leads\":[{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"edu45238jdsnfsj23\",\"email\":\"mae@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"wabaj238238jdsnfsj23\",\"email\":\"bog@bar.com\",\"firstName\":\"Fran\",\"lastName\":\"Jones\",\"address\":\"8803 Dark St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"coo@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Jones\",\"address\":\"456 Neat St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"sel045238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"qest38238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"vug789238jdsnfsj23\",\"email\":\"foo1@bar.com\",\"firstName\":\"Blake\",\"lastName\":\"Douglas\",\"address\":\"123 Reach St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"wuj08238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"Micah\",\"lastName\":\"Valmer\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"belr28238jdsnfsj23\",\"email\":\"mae@bar.com\",\"firstName\":\"Tallulah\",\"lastName\":\"Smith\",\"address\":\"123 Water St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"bill@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"888 Mayberry St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"}]}";

    @Test
    public void testInvalidJson() {
        String data = "!@#$%^&*()_+";
        Assert.assertFalse(JsonHelpers.isValidJson(data));
    }

    @Test
    public void testValidJsonCodeChallengeJson() {
        Assert.assertTrue(JsonHelpers.isValidJson(jsonCodeChallenge));
    }

    @Test
    public void testGetJsonFromString() {
        String data = "{\"thing\":[{\"a\":\"first letter\"},{\"b\":\"second letter\"}]}";
        Assert.assertThat(JsonHelpers.getJsonFromString(data), instanceOf(JsonObject.class));
    }

    @Test
    public void testIsValidJson() {
        Assert.assertTrue(JsonHelpers.isValidJson("{}"));
    }

    @Test
    public void testHasDupesFalseForEmptyJson() {
        String data = "{}";
        JsonObject json = JsonHelpers.getJsonFromString(data);
        Assert.assertEquals(false, JsonHelpers.hasDupes(json));
    }

    @Test
    public void testHasDupesForIdDupes() {
        String data = "{\"leads\":[{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"aaa@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"zzz@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"}]}\n";
        JsonObject json = JsonHelpers.getJsonFromString(data);
        Assert.assertEquals(true, JsonHelpers.hasDupes(json));
    }

    @Test
    public void testHasDupesForEmailDupes() {
        String data = "{\"leads\":[{\"_id\":\"yh8fe8ywe7t345\",\"email\":\"aaa@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"e54te0987tje4t834\",\"email\":\"aaa@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"}]}";
        JsonObject json = JsonHelpers.getJsonFromString(data);
        Assert.assertEquals(true, JsonHelpers.hasDupes(json));
    }
}