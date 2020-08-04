package il.co.ilrd.iot_servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ProductsCrud {
Connection myCon;
	
	public ProductsCrud(String url, String user, String password) {
		try {
			myCon = DriverManager.getConnection(url, user, password);
			myCon.setAutoCommit(false);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() throws Exception {
		myCon.close();
	}

	public Status create(ProductDetails obj) {
		PreparedStatement stmt = null;
		try {
			
			stmt = myCon.prepareStatement("INSERT INTO Products (email, model) VALUES (?, ?)");
			stmt.setString(1, obj.getEmail());
			stmt.setString(2, obj.getModel());
			stmt.execute();
			myCon.commit();
		} catch (SQLException e) {
			try {
				myCon.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return Status.FAILED;
			}
			e.printStackTrace();
			return Status.FAILED;
		}
		return Status.OK;
	}

	public JsonObject read(String email) {
		PreparedStatement stmt = null;		
		ResultSet resultSet = null;
		System.out.println(myCon);
		JsonArray jsonArray = new JsonArray();
		JsonObject json = new JsonObject();
		try {
			stmt = myCon.prepareStatement("SELECT * FROM Products WHERE email = ?");
			stmt.setString(1, email);
			resultSet = stmt.executeQuery();
			while (resultSet.next()) {
			JsonObject jsonObj = new JsonObject();
			jsonObj.addProperty("productId", resultSet.getInt("product_id"));
			jsonObj.addProperty("productModel", resultSet.getString("model"));			
			jsonArray.add(jsonObj);		
		}

		json.add("products", jsonArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return json;
	}

	public Status update(String email, ProductDetails details) {
		PreparedStatement stmt = null;
		try {
			stmt = myCon.prepareStatement("UPDATE Products set model = ? where product_id = ? AND email = ?");
			stmt.setString(1, details.getModel());
			stmt.setInt(2, details.getProduct_id());
			stmt.setString(3, email);
			
			if (stmt.executeUpdate() == 0) {
				return Status.NO_CHANGES_MADE;
			}
			myCon.commit();
		} catch (SQLException e) {
			try {
				myCon.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
		return Status.OK;
	}

	public Status delete(String email, Integer product_id) {
		PreparedStatement stmt = null;
		try {
			stmt = myCon.prepareStatement("DELETE FROM Products WHERE product_id = ? AND email = ?");
			stmt.setInt(1, product_id);
			stmt.setString(2, email);
			if (stmt.executeUpdate() == 0) {
				return Status.EMAIL_NOT_FOUND;
			}
			myCon.commit();
		} catch (SQLException e) {
			try {
				myCon.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
				return Status.FAILED;
			}
			e.printStackTrace();
			return Status.FAILED;
		}
		
		return Status.OK;
	}
}
