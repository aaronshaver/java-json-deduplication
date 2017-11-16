import com.aaronshaver.JsonHelpers;
import org.junit.Assert;
import org.junit.Test;

public class DeduplicatorTests {

    @Test
    public void testInvalidJson() {
        JsonHelpers json = new JsonHelpers();
        String data = "!@#$%^&*()_+";
        Assert.assertFalse(json.isValidJson(data));
    }

    @Test
    public void testValidEmptyJson() {
        JsonHelpers json = new JsonHelpers();
        String data = "{}";
        Assert.assertTrue(json.isValidJson(data));
    }

    @Test
    public void testValidCodeChallengeJson() {
        JsonHelpers json = new JsonHelpers();
        Assert.assertTrue(json.isValidJson("{\"leads\":[{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"edu45238jdsnfsj23\",\"email\":\"mae@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"wabaj238238jdsnfsj23\",\"email\":\"bog@bar.com\",\"firstName\":\"Fran\",\"lastName\":\"Jones\",\"address\":\"8803 Dark St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"coo@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Jones\",\"address\":\"456 Neat St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"sel045238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"qest38238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"vug789238jdsnfsj23\",\"email\":\"foo1@bar.com\",\"firstName\":\"Blake\",\"lastName\":\"Douglas\",\"address\":\"123 Reach St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"wuj08238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"Micah\",\"lastName\":\"Valmer\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"belr28238jdsnfsj23\",\"email\":\"mae@bar.com\",\"firstName\":\"Tallulah\",\"lastName\":\"Smith\",\"address\":\"123 Water St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"bill@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"888 Mayberry St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"}]}"));
    }
}