package resources;

public enum ApiResources {

	getFacilityMaster("/admin_api_get_facility_master"),
	dashboardUserAggregate("/admin_api_dashboard_user_aggregate"),
	getUserList("/admin_api_get_user_list"),
	ValidateOTP("/admin_api_validateotp");
	
	
	private String resource;
	ApiResources(String resource){
		this.resource = resource;
	}
	
	public String getResource() {
		return resource;
	}
	


}
