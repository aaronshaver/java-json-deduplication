import com.aaronshaver.JsonHelpers;
import com.google.gson.JsonObject;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;

public class JsonTests {

    private final String oneEmailDupe = "{\"leads\":[{\"_id\":\"yh8fe8ywe7t345\",\"email\":\"aaa@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"e54te0987tje4t834\",\"email\":\"aaa@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"}]}";

    @Test
    public void testInvalidJson() {
        String data = "!@#$%^&*()_+";
        Assert.assertFalse(JsonHelpers.isValidJson(data));
    }

    @Test
    public void testValidJsonCodeChallengeJson() {
        String jsonCodeChallenge = "{\"leads\":[{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"edu45238jdsnfsj23\",\"email\":\"mae@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"wabaj238238jdsnfsj23\",\"email\":\"bog@bar.com\",\"firstName\":\"Fran\",\"lastName\":\"Jones\",\"address\":\"8803 Dark St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"coo@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Jones\",\"address\":\"456 Neat St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"sel045238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"qest38238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"vug789238jdsnfsj23\",\"email\":\"foo1@bar.com\",\"firstName\":\"Blake\",\"lastName\":\"Douglas\",\"address\":\"123 Reach St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"wuj08238jdsnfsj23\",\"email\":\"foo@bar.com\",\"firstName\":\"Micah\",\"lastName\":\"Valmer\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"belr28238jdsnfsj23\",\"email\":\"mae@bar.com\",\"firstName\":\"Tallulah\",\"lastName\":\"Smith\",\"address\":\"123 Water St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"bill@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"888 Mayberry St\",\"entryDate\":\"2014-05-07T17:33:20+00:00\"}]}";
        Assert.assertTrue(JsonHelpers.isValidJson(jsonCodeChallenge));
    }

    @Test
    public void testGetJsonFromString() {
        String data = "{\"thing\":[{\"a\":\"first letter\"},{\"b\":\"second letter\"}]}";
        Assert.assertThat(JsonHelpers.getJsonObjectFromString(data), instanceOf(JsonObject.class));
    }

    @Test
    public void testIsValidJson() {
        Assert.assertTrue(JsonHelpers.isValidJson("{}"));
    }

    @Test
    public void testHasDupesFalseForEmptyJson() {
        String data = "{}";
        JsonObject json = JsonHelpers.getJsonObjectFromString(data);
        Assert.assertEquals(false, JsonHelpers.hasDupes(json));
    }

    @Test
    public void testHasDupesForIdDupes() {
        String data = "{\"leads\":[{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"aaa@bar.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"jkj238238jdsnfsj23\",\"email\":\"zzz@bar.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"}]}\n";
        JsonObject json = JsonHelpers.getJsonObjectFromString(data);
        Assert.assertEquals(true, JsonHelpers.hasDupes(json));
    }

    @Test
    public void testHasDupesForEmailDupes() {
        JsonObject json = JsonHelpers.getJsonObjectFromString(oneEmailDupe);
        Assert.assertEquals(true, JsonHelpers.hasDupes(json));
    }

    @Test
    public void testZeroEntitiesCount() {
        JsonObject json = JsonHelpers.getJsonObjectFromString("{}");
        Assert.assertEquals(0, JsonHelpers.getEntitiesCount(json));
    }

    @Test
    public void test2EntitiesCount() {
        JsonObject json = JsonHelpers.getJsonObjectFromString(oneEmailDupe);
        Assert.assertEquals(2, JsonHelpers.getEntitiesCount(json));
    }

    @Test
    public void testRemove1EmailDupeFrom2Entries() {
        JsonObject json = JsonHelpers.getJsonObjectFromString(oneEmailDupe);
        Assert.assertEquals(2, JsonHelpers.getEntitiesCount(json));
        JsonObject deduped_json = JsonHelpers.dedupe(json);
        Assert.assertEquals(1, JsonHelpers.getEntitiesCount(deduped_json));
        Assert.assertEquals(false, JsonHelpers.hasDupes(deduped_json));
    }

    @Test
    public void testRemove1IdDupeFrom2Entries() {
        String oneIdDupe = "{\"leads\":[{\"_id\":\"e54te0987tje4t834\",\"email\":\"aaa@aaa.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"e54te0987tje4t834\",\"email\":\"zzz@zzz.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"}]}";
        JsonObject json = JsonHelpers.getJsonObjectFromString(oneIdDupe);
        Assert.assertEquals(2, JsonHelpers.getEntitiesCount(json));
        JsonObject deduped_json = JsonHelpers.dedupe(json);
        Assert.assertEquals(1, JsonHelpers.getEntitiesCount(deduped_json));
        Assert.assertEquals(false, JsonHelpers.hasDupes(deduped_json));
    }

    @Test
    public void testRemove0DupesFrom2UniqueEntries() {
        String twoUniques = "{\"leads\":[{\"_id\":\"hn83q4htq34t\",\"email\":\"aaa@aaa.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"qxgemh9uqegerghu\",\"email\":\"zzz@zzz.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"}]}";
        JsonObject json = JsonHelpers.getJsonObjectFromString(twoUniques);
        Assert.assertEquals(2, JsonHelpers.getEntitiesCount(json));
        JsonObject deduped_json = JsonHelpers.dedupe(json);
        Assert.assertEquals(2, JsonHelpers.getEntitiesCount(deduped_json));
        Assert.assertEquals(false, JsonHelpers.hasDupes(deduped_json));
    }

    @Test
    public void testRemove1IdDupeFrom3Entries() {
        String oneIdDupeThreeEntries = "{\"leads\":[{\"_id\":\"hn83q4htq34t\",\"email\":\"aaa@aaa.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"qxgemh9uqegerghu\",\"email\":\"zzz@zzz.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"qxgemh9uqegerghu\",\"email\":\"eee@eee.com\",\"firstName\":\"Joe\",\"lastName\":\"Schmoe\",\"address\":\"55 South Hampton St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"}]}";
        JsonObject json = JsonHelpers.getJsonObjectFromString(oneIdDupeThreeEntries);
        Assert.assertEquals(3, JsonHelpers.getEntitiesCount(json));
        JsonObject deduped_json = JsonHelpers.dedupe(json);
        Assert.assertEquals(2, JsonHelpers.getEntitiesCount(deduped_json));
        Assert.assertEquals(false, JsonHelpers.hasDupes(deduped_json));
    }

    @Test
    public void testRemove2Dupes1IdDupe1EmailDupeFrom7Entries() {
        String twoDupeTypes7Entries = "{\"leads\":[{\"_id\":\"unique-mjcg5ju94mnju954g\",\"email\":\"unique-yyy@yyy.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"unique-hn83q4htq34t\",\"email\":\"dupe-aaa@aaa.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"dupe-qxgemh9uqegerghu\",\"email\":\"unique-zzz@zzz.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2014-05-07T17:31:20+00:00\"},{\"_id\":\"unique-4jm59ujmn45t9un\",\"email\":\"unique-www@wwww.com\",\"firstName\":\"Aaron\",\"lastName\":\"Shaver\",\"address\":\"456 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"dupe-qxgemh9uqegerghu\",\"email\":\"unique-eee@eee.com\",\"firstName\":\"Joe\",\"lastName\":\"Schmoe\",\"address\":\"55 South Hampton St\",\"entryDate\":\"2014-05-07T17:32:20+00:00\"},{\"_id\":\"unique-hgreunh84u3u8h43\",\"email\":\"dupe-aaa@aaa.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"},{\"_id\":\"unique-dfjkh2384h72345h\",\"email\":\"unique-hhh@hhh.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2014-05-07T17:30:20+00:00\"}]}";
        JsonObject json = JsonHelpers.getJsonObjectFromString(twoDupeTypes7Entries);
        Assert.assertEquals(7, JsonHelpers.getEntitiesCount(json));
        JsonObject deduped_json = JsonHelpers.dedupe(json);
        Assert.assertEquals(5, JsonHelpers.getEntitiesCount(deduped_json));
        Assert.assertEquals(false, JsonHelpers.hasDupes(deduped_json));
    }

    @Test
    public void testRemoveFirstOlder() {
        String dupeFirstAndOlder = "{\"leads\":[{\"_id\":\"dupe-hn83q4htq34t\",\"email\":\"aaa@aaa.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2016-01-01T17:30:20+00:00\"},{\"_id\":\"dupe-hn83q4htq34t\",\"email\":\"zzz@zzz.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2017-07-07T17:31:20+00:00\"}]}";
        JsonObject json = JsonHelpers.getJsonObjectFromString(dupeFirstAndOlder);
        JsonObject deduped_json = JsonHelpers.dedupe(json);
        Assert.assertEquals(false, JsonHelpers.hasDupes(deduped_json));
        String jsonString = JsonHelpers.getStringFromJson(deduped_json);
        Assert.assertTrue(jsonString.contains("zzz@zzz.com"));
        Assert.assertFalse(jsonString.contains("aaa@aaa.com"));
    }

    @Test
    public void testRemoveSecondOlder() {
        String dupeSecondOlder = "{\"leads\":[{\"_id\":\"dupe-hn83q4htq34t\",\"email\":\"aaa@aaa.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2017-01-01T17:30:20+00:00\"},{\"_id\":\"dupe-hn83q4htq34t\",\"email\":\"zzz@zzz.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2012-07-07T17:31:20+00:00\"}]}";
        JsonObject json = JsonHelpers.getJsonObjectFromString(dupeSecondOlder);
        JsonObject deduped_json = JsonHelpers.dedupe(json);
        Assert.assertEquals(false, JsonHelpers.hasDupes(deduped_json));
        String jsonString = JsonHelpers.getStringFromJson(deduped_json);
        Assert.assertTrue(jsonString.contains("aaa@aaa.com"));
        Assert.assertFalse(jsonString.contains("zzz@zzz.com"));
    }

    @Test
    public void testRemoveEarlierEntryWhenDatesAreEqual() {
        String dupeDatesEqual = "{\"leads\":[{\"_id\":\"dupe-hn83q4htq34t\",\"email\":\"aaa@aaa.com\",\"firstName\":\"John\",\"lastName\":\"Smith\",\"address\":\"123 Street St\",\"entryDate\":\"2017-01-01T17:30:20+00:00\"},{\"_id\":\"dupe-hn83q4htq34t\",\"email\":\"zzz@zzz.com\",\"firstName\":\"Ted\",\"lastName\":\"Masters\",\"address\":\"44 North Hampton St\",\"entryDate\":\"2017-01-01T17:30:20+00:00\"}]}";
        JsonObject json = JsonHelpers.getJsonObjectFromString(dupeDatesEqual);
        JsonObject deduped_json = JsonHelpers.dedupe(json);
        Assert.assertEquals(false, JsonHelpers.hasDupes(deduped_json));
        String jsonString = JsonHelpers.getStringFromJson(deduped_json);
        Assert.assertTrue(jsonString.contains("zzz@zzz.com"));
        Assert.assertFalse(jsonString.contains("aaa@aaa.com"));
    }
}