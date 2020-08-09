package il.co.ilrd.iot_servlets;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonObject;

public class ProductDetails {
	private String model;
	private String email;
	private Integer product_id;
	
	public static ProductDetails getProductDetails(ResultSet resultSet) {
		ProductDetails details = null;
		
		try {
			if (resultSet.next()) {
				details = new ProductDetails();
				details.model = resultSet.getString("model");
				details.email = resultSet.getString("email");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return details;
	}
	
	public static ProductDetails getProductDetails(String email, JsonObject obj) {
		ProductDetails details = new ProductDetails();

		details.model = obj.get("model").getAsString();
		if (obj.get("product_id") != null) {
			details.product_id = obj.get("product_id").getAsInt();
		}
		
		details.email = email;
		// i need to check it because it comes from the client..
		return details;
	}
	
	public static JsonObject toJson(ProductDetails obj) {
		JsonObject json = new JsonObject();
		
		json.addProperty("model", obj.model);

		return json;
	}

	public Integer getProduct_id() {
		return product_id;
	}

	public String getModel() {
		return model;
	}
	
	public String getEmail() {
		return email;
	}

}
