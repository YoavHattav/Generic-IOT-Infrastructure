package il.co.ilrd.iot_servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class Products
 */
@WebServlet("/products")
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;
    ProductsCrud prodCrud;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Products() {
    	System.out.println("const");
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	prodCrud = new ProductsCrud("jdbc:mysql://localhost:3306/GenericIOT", "root", "ct,h kvmkhj");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String token = request.getHeader("token");
		if (token == null) {
			out.println(Status.INVALID_TOKEN);
			return;
		}
		String email = TokenManager.getEmail(token);
		JsonObject json = prodCrud.read(email);
		
		if (json == null) {
			out.println(Status.INVALID_PARAMS.getDescription());
			return;
		}

		response.setStatus(Status.OK.getCode());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(json);
		out.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("post...start");
		PrintWriter out = response.getWriter();
		String jsonString;
		JsonObject json;
		System.out.println("post...");
		String token = request.getHeader("token");
		if (token == null) {
			out.println(Status.INVALID_TOKEN);
			return;
		}
		
		try {
			// FIXME close the reader
			BufferedReader reader = request.getReader();
			jsonString = reader.lines().collect(Collectors.joining());
			json =  JsonParser.parseString(jsonString).getAsJsonObject();
		} catch (Exception e) {
			throw new IOException("Error parsing JSON request string");
		}
		ProductDetails details = ProductDetails.getProductDetails(TokenManager.getEmail(token), json);
		
		if (details == null) {
			out.println(Status.INVALID_PARAMS.getDescription());
		}
		System.out.println(details.getEmail());
		System.out.println(details.getModel());
		out.println(prodCrud.create(details).getDescription());
		out.flush();
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String token = request.getHeader("token");
		String jsonString;
		JsonObject json;
		Integer product_id;
		if (token == null) {
			out.println(Status.INVALID_TOKEN.getDescription());
			return;
		}
		try {
			// FIXME close the reader
			BufferedReader reader = request.getReader();
			jsonString = reader.lines().collect(Collectors.joining());
			System.out.println(jsonString);
			json =  JsonParser.parseString(jsonString).getAsJsonObject();
			
			product_id = json.get("product_id").getAsInt();
		} catch (Exception e) {
			throw new IOException("Error parsing JSON request string");
		}
		String email = TokenManager.getEmail(token);
		
		out.println(prodCrud.delete(email, product_id).getDescription());
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String token = request.getHeader("token");
		PrintWriter out = response.getWriter();
		if (token == null) {
			out.println(Status.INVALID_TOKEN.getDescription());
			return;
		}
		response.setContentType("application/json");
		String jsonString;
		JsonObject json;
		
		try {
			// FIXME close the reader
			BufferedReader reader = request.getReader();
			jsonString = reader.lines().collect(Collectors.joining());
			json =  JsonParser.parseString(jsonString).getAsJsonObject();
		} catch (Exception e) {
			// crash and burn
			throw new IOException("Error parsing JSON request string");
		}
		 
		ProductDetails details = ProductDetails.getProductDetails(token, json);
		
		if (details == null) {
			out.println(Status.INVALID_PARAMS.getDescription());
		}
		out.println(prodCrud.update(TokenManager.getEmail(token), details).getDescription());
		out.close();
	}

}
