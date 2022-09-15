package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tech.grasshopper.filter.ExtentRestAssuredFilter;

public class Utils {
	int  rowCount = 0;
	JsonPath js;
	String baseUrl = System.getProperty("baseUrl");  // Should send the base url from CLI
	public static RequestSpecification requestBase; // If we don't make it static for each run requestBase will be
													// considered
	// null for each run..and logging will be replaced each time.

	// Utility to get the RequestSpecifications and also get the logging filter
	// initiated
	
	public RequestSpecification RequestSpecification() throws IOException {

		if (requestBase == null) {

			// RestAssured.baseURI=getGlobalProperties("BaseURL"); //The BaseUrl is derived
			// from global properties file
			PrintStream log = new PrintStream(new FileOutputStream("logs.txt")); // Creates a new txt file
			requestBase = new RequestSpecBuilder().setBaseUri(baseUrl)
					.addHeader("x-access-token", getGlobalProperties("x-access-token"))
					.addFilter(RequestLoggingFilter.logRequestTo(log)) // add Filter is used to add Request logging into
																		// a desired file
					.addFilter(ResponseLoggingFilter.logResponseTo(log)) // add Filter is used to add Response logging
																			// into a desired file
					.setContentType(ContentType.JSON).build();

			return requestBase;
		}

		return requestBase;

	}

	// Utility to get the Global properties from global.properties file
	public static String getGlobalProperties(String propertyName) throws IOException {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(new File("src\\test\\java\\resources\\global.properties"));
		prop.load(fis);
		return prop.getProperty(propertyName);
	}

	// Utility to get the Json Path
	public String getJsonPath(Response response, String key) {

		String resp = response.asString();
		js = new JsonPath(resp);

		return js.get(key).toString();

	}

	// to Implement the Excel Integration - Excel driven Data Testing

	public ArrayList<String> getDataFromExcel(String testCaseName, String sheetName, String excelFilePath)
			throws IOException {

		FileInputStream filepath = new FileInputStream(new File(excelFilePath));
		ArrayList<String> a = new ArrayList<String>();
		XSSFWorkbook workbook = new XSSFWorkbook(filepath);
		int NoOfSheets = workbook.getNumberOfSheets();
		int k = 0;
		int column = 0;
		for (int i = 0; i < NoOfSheets; i++) {
			if (workbook.getSheetAt(i).getSheetName().equalsIgnoreCase(sheetName)) {

				XSSFSheet requiredSheet = workbook.getSheetAt(i);
				Iterator<Row> row = requiredSheet.iterator();
				Row firstRow = row.next();

				Iterator<Cell> firstRowCellIterator = firstRow.cellIterator();

				while (firstRowCellIterator.hasNext()) {
					Cell value = firstRowCellIterator.next();
					if (value.getStringCellValue().equalsIgnoreCase("TestCases")) {
						column = k;
					}

					k++;
				}

				while (row.hasNext()) {
					Row r = row.next();
					if (r.getCell(column).getStringCellValue().equalsIgnoreCase(testCaseName)) {
						Iterator<Cell> cv = r.cellIterator(); // creating cell iterator to iterate the row
						while (cv.hasNext()) {
							Cell c = cv.next(); // Incrementing the cell and assigning the value to "c"
							if (c.getCellType() == CellType.STRING) { // if the cell value is String, add it to array

								a.add(c.getStringCellValue());

							} else { // if the cell value is number
								a.add(NumberToTextConverter.toText(c.getNumericCellValue()));
							}
						}
					}
				}
				rowCount = requiredSheet.getLastRowNum();
				// System.out.println("This is the first row count "+rowCount);
			}
		}
		return a;
	}
	
	
      // To fetch Data from database 
		public static String[][] fetchDataFromDB(String query) throws SQLException, IOException {
		
		//Connection conn = DriverManager.getConnection(getGlobalProperties("dataBaseconnectionUrl")
		//		, getGlobalProperties("dataBaseUsername"), getGlobalProperties("dataBasePassword"));
			
		DBConnectionManager DBInstance = DBConnectionManager.getInstance(getGlobalProperties("dataBaseconnectionUrl")
				, getGlobalProperties("dataBaseUsername"), getGlobalProperties("dataBasePassword"));
		Connection conn  = DBInstance.getConnection();
		Statement s = conn.createStatement();
		ResultSet rs = s.executeQuery(query);
		
		//To get total number of column returned
		int columnCount = rs.getMetaData().getColumnCount();
		
		//To get total number of rows returned for the Query
		rs.last();   //this will move rs to last row
		int lastRowCount = rs.getRow(); // this will give the index of last Row
		rs.beforeFirst();  // this will bring it back to the first record..
		
		//Create an Object
		String[][] result = new String[lastRowCount][columnCount];
		int i=0;
		while(rs.next()) {
			for(int j=0;j<columnCount;j++) {
				
				result[i][j] = rs.getString(j+1);
								
			}
			i=i+1;
			}
		return result;
	
	}
}
