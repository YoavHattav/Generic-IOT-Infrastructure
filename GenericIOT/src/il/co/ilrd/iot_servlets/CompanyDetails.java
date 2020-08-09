package il.co.ilrd.iot_servlets;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

public class CompanyDetails {
	private Integer company_id;
	private String company_name;
	private String address;
	private String password;
	private String email;
	
	public static CompanyDetails getCompanyDetails(ResultSet resultSet) {
		CompanyDetails details = null; 
		
		try {
			if (resultSet.next()) {
				details =  new CompanyDetails();
				details.company_name = resultSet.getString("company_name");
				details.address = resultSet.getString("address");
				details.password = resultSet.getString("password");
				details.email = resultSet.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return details;
	}
	
	public static CompanyDetails getCompanyDetails(JsonObject obj) {
		CompanyDetails details = new CompanyDetails();

		details.company_name = obj.get("company_name").getAsString();
		details.address = obj.get("address").getAsString();
		details.password = obj.get("password").getAsString();
		details.email = obj.get("email").getAsString();
		// i need to check it because it comes from the client..
		return details;
	}
	
	public static JsonObject toJson(CompanyDetails obj) {
		JsonObject json = new JsonObject();
		
		json.addProperty("email", obj.email);
		json.addProperty("address", obj.address);
		json.addProperty("company_name", obj.company_name);

		return json;
	}
	
	public Integer getCompany_id() {
		return company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public String getAddress() {
		return address;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	
}
