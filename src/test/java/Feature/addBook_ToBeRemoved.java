package Feature;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import resources.Utils;

public class addBook_ToBeRemoved extends Utils {

	@Test
	public void addNewBook() throws IOException {
		
		
		for(int i=1;i<=5;i++) {
		
			ArrayList data = getDataFromExcel("TC00"+i+"", "RestAssured", "C:\\Users\\sandeep\\Desktop\\TestData.xlsx");

			HashMap<String, Object> jsonAsMap = new HashMap<String, Object>();
			jsonAsMap.put("name", data.get(1));
			jsonAsMap.put("isbn", data.get(2));
			jsonAsMap.put("aisle", data.get(3));
			jsonAsMap.put("author", data.get(4));

			// To add nested json into HashMap
			// HashMap<String,Object> jsp2 = new HashMap<String, Object>();
			// jsp2.put("lat","1324");
			// jsp2.put("lng","234");
			// Now to add this HashMap to "jsonAsMap" object
			// jsonAsMap.put("location",jsp2);

			RestAssured.baseURI = "http://216.10.245.166";
			String resp = given().header("Content-Type", "application/json").body(jsonAsMap).when()
					      .post("Library/Addbook.php").then().assertThat()
					      .statusCode(200)
					      .extract().response().asString();

			JsonPath path = new JsonPath(resp);
			String id = path.get("ID");
			System.out.println(id);
		}
	}
}
