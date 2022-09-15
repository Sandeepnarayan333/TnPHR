package resources;

public class PayLoad {

	
	public String facilityMasterPayLoad() {
		
		String payLoad1 = "{\r\n"
				+ "    \"USER_ID\": \"0a95ed91-6e5d-4ffe-b947-f1547becc0cb\",\r\n"
				+ "    \"USER_PHR_ROLE\": \"STATE_ADMIN\",\r\n"
				+ "    \"USER_FACILITY_ID\": \"a32fbc4f-8b08-4d56-b4e8-12c0be8db625\",\r\n"
				+ "    \"GOVT_DEPARTMENT\": \"Health\"\r\n"
				+ "}";
		return payLoad1;
	}
	
	
	public String validateOTPPayLoad(String number,String otp) {
		
		return"{\r\n"
				+ "    \"mobile_number\": \""+number+"\",\r\n"
				+ "    \"otp\": \""+ otp +"\"\r\n"
				+ "}";
	}
	
	public String getUserListPayLoad(String userId,String phrRole,String userFacilityId) {
		
		return "{\r\n"
				+ "    \"USER_ID\": \""+userId+"\",\r\n"
				+ "    \"USER_PHR_ROLE\": \""+phrRole+"\",\r\n"
				+ "    \"USER_FACILITY_ID\": \""+userFacilityId+"\",\r\n"
				+ "    \"LIMIT\": 10,\r\n"
				+ "    \"OFFSET\": 0,\r\n"
				+ "    \"FILTERS\": {\r\n"
				+ "        \"USER_NAME\": null,\r\n"
				+ "        \"MOBILE_NUMBER\": null,\r\n"
				+ "        \"DISTRICT_ID\": null,\r\n"
				+ "        \"BLOCK_ID\": null,\r\n"
				+ "        \"PHR_ROLE\": null,\r\n"
				+ "        \"FACILITY_ID\": null,\r\n"
				+ "        \"ROLE\": null\r\n"
				+ "    }\r\n"
				+ "}";
		
	}
	
	
	
	
	public String PayLoadForShopStreetAggregator(String userId,String phrRole,String userFacilityId) {
		return "{\r\n"
				+ "    \"USER_ID\": \""+userId+"\",\r\n"
				+ "    \"USER_PHR_ROLE\": \""+phrRole+"\",\r\n"
				+ "    \"USER_FACILITY_ID\": \""+userFacilityId+"\",\r\n"
				+ "    \"FILTERS\": {\r\n"
				+ "        \"DISTRICT_ID\": null,\r\n"
				+ "        \"BLOCK_ID\": null\r\n"
				+ "    }\r\n"
				+ "}";
		
	}
}