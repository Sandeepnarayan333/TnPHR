package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

JsonPath js;
	
	public static RequestSpecification requestBase;  //If we don't make it static for each run requestBase will be considered 
	                                   //null for each run..and logging will be replaced each time.
	
	
	//Utility to get the RequestSpecifications and also get the logging filter initiated
	public RequestSpecification RequestSpecification() throws IOException {
		
		if(requestBase==null) {
		
		//RestAssured.baseURI=getGlobalProperties("BaseURL"); //The BaseUrl is derived from global properties file
		PrintStream log = new PrintStream(new FileOutputStream("logs.txt"));  //Creates a new txt file
		requestBase = new RequestSpecBuilder().setBaseUri(getGlobalProperties("BaseURL"))
				.addHeader("x-access-token",getGlobalProperties("x-access-token"))
				.addFilter(RequestLoggingFilter.logRequestTo(log))   //add Filter is used to add Request logging into a desired file
				.addFilter(ResponseLoggingFilter.logResponseTo(log)) //add Filter is used to add Response logging into a desired file
				.setContentType(ContentType.JSON).build();
	
		return requestBase;
	}

	return requestBase;

}
	
	
	
	//Utility to get the Global properties from global.properties file
	public String getGlobalProperties(String propertyName) throws IOException {
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("src\\test\\java\\resources\\global.properties"));
		prop.load(fis);
		return prop.getProperty(propertyName);
	}
	
	
	
	
	
	//Utility to get the Json Path 
	public String getJsonPath(Response response, String key) {
		
		String resp = response.asString();
		js = new JsonPath(resp);
	
		return  js.get(key).toString();
		
	}
}
