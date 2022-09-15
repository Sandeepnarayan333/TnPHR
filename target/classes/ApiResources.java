package resources;

public enum ApiResources {

	getFacilityMaster("/admin_api_get_facility_master"),
	dashboardUserAggregate("/admin_api_dashboard_user_aggregate"),
	getUserList("/admin_api_get_user_list"),
	ValidateOTP("/admin_api_validateotp"),
	APIShopAggregate("/admin_api_dashboard_shop_aggregate"),
	APIStreetAggregate("/admin_api_dashboard_street_aggregates");
	
	
	private String resource;
	ApiResources(String resource){
		this.resource = resource;
	}
	
	public String getResource() {
		return resource;
	}
	


}
