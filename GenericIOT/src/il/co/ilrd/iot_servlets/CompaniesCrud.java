package il.co.ilrd.iot_servlets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompaniesCrud {
	Connection myCon;
	
	public CompaniesCrud(String url, String user, String password) {
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

	public Status create(CompanyDetails obj) {
		PreparedStatement stmt = null;
		try {
			String sqlQuery = "INSERT INTO Companies (company_name, email, password, address) VALUES ('" + obj.getCompany_name() + 
					"', '" + obj.getEmail() + 
					"', '" + obj.getPassword() + 
					"', '" + obj.getAddress() + "')";
			stmt = myCon.prepareStatement(sqlQuery);
			stmt.execute();
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

	public CompanyDetails read(String email) {
		System.out.println(email + " in companiesCrud read method");
		PreparedStatement stmt = null;		
		ResultSet resultSet = null;
		System.out.println(myCon);
		try {
			stmt = myCon.prepareStatement("SELECT * FROM Companies WHERE email = ?");
			stmt.setString(1, email);
			resultSet = stmt.executeQuery();
			System.out.println("did i get here?");

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return CompanyDetails.getCompanyDetails(resultSet);
	}

	public Status update(String email, CompanyDetails details) {
		PreparedStatement stmt = null;
		try {
			stmt = myCon.prepareStatement("UPDATE Companies set company_name = ? , address = ? where email = ?");
			stmt.setString(1, details.getCompany_name());
			stmt.setString(2, details.getAddress());
			stmt.setString(3, email);
			
			if (stmt.executeUpdate() == 0) {
				return Status.EMAIL_NOT_FOUND;
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
// FIXME delete the company's products as well.
	public Status delete(String email) {
		PreparedStatement stmt = null;
		try {
			stmt = myCon.prepareStatement("DELETE FROM Companies WHERE email = ?");
			stmt.setString(1, email);
			
			if (stmt.executeUpdate() == 0) {
				return Status.EMAIL_NOT_FOUND;
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
}
