package tests;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;

import static helpers.Requests.getPath;
import static helpers.Requests.postSmth;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class FirstTest extends BaseTest {

    @Test(dataProvider = "provider")
    public void testMe(String port, String path) {
        System.err.println(port);
        getPath(path);
    }

    @Test(dataProvider = "provider")
    public void checkSmth(String inputHash, List<String> expectedHashes) {
        HashMap<String, String> query = new HashMap<>();
        query.put("Hash-code", inputHash);
        JsonPath response = postSmth(query);
        response.prettyPrint();

        List<String> hashCodes = response.getList("Hash-code");
        Assert.assertEquals(hashCodes.size(), expectedHashes.size(), "expected size of hash codes");

        assertThat(hashCodes, containsInAnyOrder(expectedHashes.toArray()));
    }

    @DataProvider
    private Object[][] provider() {
        return new Object[][]{
                {"2", asList("3", "4")},
                {"1", asList("2", "3", "4")}
        };
    }
}